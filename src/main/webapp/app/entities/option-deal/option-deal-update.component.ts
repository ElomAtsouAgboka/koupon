import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IOptionDeal } from 'app/shared/model/option-deal.model';
import { OptionDealService } from './option-deal.service';
import { IDeal } from 'app/shared/model/deal.model';
import { DealService } from 'app/entities/deal';

@Component({
    selector: 'jhi-option-deal-update',
    templateUrl: './option-deal-update.component.html'
})
export class OptionDealUpdateComponent implements OnInit {
    optionDeal: IOptionDeal;
    isSaving: boolean;

    deals: IDeal[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected optionDealService: OptionDealService,
        protected dealService: DealService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ optionDeal }) => {
            this.optionDeal = optionDeal;
        });
        this.dealService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDeal[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDeal[]>) => response.body)
            )
            .subscribe((res: IDeal[]) => (this.deals = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.optionDeal.id !== undefined) {
            this.subscribeToSaveResponse(this.optionDealService.update(this.optionDeal));
        } else {
            this.subscribeToSaveResponse(this.optionDealService.create(this.optionDeal));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IOptionDeal>>) {
        result.subscribe((res: HttpResponse<IOptionDeal>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackDealById(index: number, item: IDeal) {
        return item.id;
    }
}
