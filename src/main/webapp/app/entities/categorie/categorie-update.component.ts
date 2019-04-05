import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICategorie } from 'app/shared/model/categorie.model';
import { CategorieService } from './categorie.service';
import { IDeal } from 'app/shared/model/deal.model';
import { DealService } from 'app/entities/deal';

@Component({
    selector: 'jhi-categorie-update',
    templateUrl: './categorie-update.component.html'
})
export class CategorieUpdateComponent implements OnInit {
    categorie: ICategorie;
    isSaving: boolean;

    categories: ICategorie[];

    deals: IDeal[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected categorieService: CategorieService,
        protected dealService: DealService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ categorie }) => {
            this.categorie = categorie;
        });
        this.categorieService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICategorie[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICategorie[]>) => response.body)
            )
            .subscribe((res: ICategorie[]) => (this.categories = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.categorie.id !== undefined) {
            this.subscribeToSaveResponse(this.categorieService.update(this.categorie));
        } else {
            this.subscribeToSaveResponse(this.categorieService.create(this.categorie));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategorie>>) {
        result.subscribe((res: HttpResponse<ICategorie>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCategorieById(index: number, item: ICategorie) {
        return item.id;
    }

    trackDealById(index: number, item: IDeal) {
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
