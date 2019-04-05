import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeDeCommerce } from 'app/shared/model/type-de-commerce.model';

@Component({
    selector: 'jhi-type-de-commerce-detail',
    templateUrl: './type-de-commerce-detail.component.html'
})
export class TypeDeCommerceDetailComponent implements OnInit {
    typeDeCommerce: ITypeDeCommerce;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeDeCommerce }) => {
            this.typeDeCommerce = typeDeCommerce;
        });
    }

    previousState() {
        window.history.back();
    }
}
