import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IDeal } from 'app/shared/model/deal.model';
import { DealService } from './deal.service';
import { IMenu } from 'app/shared/model/menu.model';
import { MenuService } from 'app/entities/menu';
import { ICategorie } from 'app/shared/model/categorie.model';
import { CategorieService } from 'app/entities/categorie';
import { ICommerce } from 'app/shared/model/commerce.model';
import { CommerceService } from 'app/entities/commerce';

@Component({
    selector: 'jhi-deal-update',
    templateUrl: './deal-update.component.html'
})
export class DealUpdateComponent implements OnInit {
    deal: IDeal;
    isSaving: boolean;

    menus: IMenu[];

    categories: ICategorie[];

    commerce: ICommerce[];
    dateCreationDealDp: any;
    dateClotureDealDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected dealService: DealService,
        protected menuService: MenuService,
        protected categorieService: CategorieService,
        protected commerceService: CommerceService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ deal }) => {
            this.deal = deal;
        });
        this.menuService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMenu[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMenu[]>) => response.body)
            )
            .subscribe((res: IMenu[]) => (this.menus = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.categorieService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICategorie[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICategorie[]>) => response.body)
            )
            .subscribe((res: ICategorie[]) => (this.categories = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.commerceService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICommerce[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICommerce[]>) => response.body)
            )
            .subscribe((res: ICommerce[]) => (this.commerce = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.deal.id !== undefined) {
            this.subscribeToSaveResponse(this.dealService.update(this.deal));
        } else {
            this.subscribeToSaveResponse(this.dealService.create(this.deal));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeal>>) {
        result.subscribe((res: HttpResponse<IDeal>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMenuById(index: number, item: IMenu) {
        return item.id;
    }

    trackCategorieById(index: number, item: ICategorie) {
        return item.id;
    }

    trackCommerceById(index: number, item: ICommerce) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
