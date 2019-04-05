import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ITypeDeCommerce } from 'app/shared/model/type-de-commerce.model';
import { TypeDeCommerceService } from './type-de-commerce.service';

@Component({
    selector: 'jhi-type-de-commerce-update',
    templateUrl: './type-de-commerce-update.component.html'
})
export class TypeDeCommerceUpdateComponent implements OnInit {
    typeDeCommerce: ITypeDeCommerce;
    isSaving: boolean;

    constructor(protected typeDeCommerceService: TypeDeCommerceService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ typeDeCommerce }) => {
            this.typeDeCommerce = typeDeCommerce;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.typeDeCommerce.id !== undefined) {
            this.subscribeToSaveResponse(this.typeDeCommerceService.update(this.typeDeCommerce));
        } else {
            this.subscribeToSaveResponse(this.typeDeCommerceService.create(this.typeDeCommerce));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeDeCommerce>>) {
        result.subscribe((res: HttpResponse<ITypeDeCommerce>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
