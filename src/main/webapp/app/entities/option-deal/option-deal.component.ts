import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IOptionDeal } from 'app/shared/model/option-deal.model';
import { AccountService } from 'app/core';
import { OptionDealService } from './option-deal.service';

@Component({
    selector: 'jhi-option-deal',
    templateUrl: './option-deal.component.html'
})
export class OptionDealComponent implements OnInit, OnDestroy {
    optionDeals: IOptionDeal[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected optionDealService: OptionDealService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.optionDealService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IOptionDeal[]>) => res.ok),
                    map((res: HttpResponse<IOptionDeal[]>) => res.body)
                )
                .subscribe((res: IOptionDeal[]) => (this.optionDeals = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.optionDealService
            .query()
            .pipe(
                filter((res: HttpResponse<IOptionDeal[]>) => res.ok),
                map((res: HttpResponse<IOptionDeal[]>) => res.body)
            )
            .subscribe(
                (res: IOptionDeal[]) => {
                    this.optionDeals = res;
                    this.currentSearch = '';
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInOptionDeals();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IOptionDeal) {
        return item.id;
    }

    registerChangeInOptionDeals() {
        this.eventSubscriber = this.eventManager.subscribe('optionDealListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
