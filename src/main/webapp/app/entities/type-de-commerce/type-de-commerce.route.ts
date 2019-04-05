import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TypeDeCommerce } from 'app/shared/model/type-de-commerce.model';
import { TypeDeCommerceService } from './type-de-commerce.service';
import { TypeDeCommerceComponent } from './type-de-commerce.component';
import { TypeDeCommerceDetailComponent } from './type-de-commerce-detail.component';
import { TypeDeCommerceUpdateComponent } from './type-de-commerce-update.component';
import { TypeDeCommerceDeletePopupComponent } from './type-de-commerce-delete-dialog.component';
import { ITypeDeCommerce } from 'app/shared/model/type-de-commerce.model';

@Injectable({ providedIn: 'root' })
export class TypeDeCommerceResolve implements Resolve<ITypeDeCommerce> {
    constructor(private service: TypeDeCommerceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITypeDeCommerce> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TypeDeCommerce>) => response.ok),
                map((typeDeCommerce: HttpResponse<TypeDeCommerce>) => typeDeCommerce.body)
            );
        }
        return of(new TypeDeCommerce());
    }
}

export const typeDeCommerceRoute: Routes = [
    {
        path: '',
        component: TypeDeCommerceComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'kouponApp.typeDeCommerce.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TypeDeCommerceDetailComponent,
        resolve: {
            typeDeCommerce: TypeDeCommerceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponApp.typeDeCommerce.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TypeDeCommerceUpdateComponent,
        resolve: {
            typeDeCommerce: TypeDeCommerceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponApp.typeDeCommerce.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TypeDeCommerceUpdateComponent,
        resolve: {
            typeDeCommerce: TypeDeCommerceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponApp.typeDeCommerce.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeDeCommercePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TypeDeCommerceDeletePopupComponent,
        resolve: {
            typeDeCommerce: TypeDeCommerceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponApp.typeDeCommerce.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
