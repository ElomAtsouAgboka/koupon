import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICommerce } from 'app/shared/model/commerce.model';
import { CommerceService } from './commerce.service';
import { ITypeDeCommerce } from 'app/shared/model/type-de-commerce.model';
import { TypeDeCommerceService } from 'app/entities/type-de-commerce';
import { IMarchand } from 'app/shared/model/marchand.model';
import { MarchandService } from 'app/entities/marchand';

@Component({
    selector: 'jhi-commerce-update',
    templateUrl: './commerce-update.component.html'
})
export class CommerceUpdateComponent implements OnInit {
    commerce: ICommerce;
    isSaving: boolean;

    typedecommerces: ITypeDeCommerce[];

    marchands: IMarchand[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected commerceService: CommerceService,
        protected typeDeCommerceService: TypeDeCommerceService,
        protected marchandService: MarchandService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ commerce }) => {
            this.commerce = commerce;
        });
        this.typeDeCommerceService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITypeDeCommerce[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITypeDeCommerce[]>) => response.body)
            )
            .subscribe((res: ITypeDeCommerce[]) => (this.typedecommerces = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.marchandService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMarchand[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMarchand[]>) => response.body)
            )
            .subscribe((res: IMarchand[]) => (this.marchands = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.commerce.id !== undefined) {
            this.subscribeToSaveResponse(this.commerceService.update(this.commerce));
        } else {
            this.subscribeToSaveResponse(this.commerceService.create(this.commerce));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommerce>>) {
        result.subscribe((res: HttpResponse<ICommerce>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTypeDeCommerceById(index: number, item: ITypeDeCommerce) {
        return item.id;
    }

    trackMarchandById(index: number, item: IMarchand) {
        return item.id;
    }
}
