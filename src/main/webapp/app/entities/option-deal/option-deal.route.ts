import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OptionDeal } from 'app/shared/model/option-deal.model';
import { OptionDealService } from './option-deal.service';
import { OptionDealComponent } from './option-deal.component';
import { OptionDealDetailComponent } from './option-deal-detail.component';
import { OptionDealUpdateComponent } from './option-deal-update.component';
import { OptionDealDeletePopupComponent } from './option-deal-delete-dialog.component';
import { IOptionDeal } from 'app/shared/model/option-deal.model';

@Injectable({ providedIn: 'root' })
export class OptionDealResolve implements Resolve<IOptionDeal> {
    constructor(private service: OptionDealService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOptionDeal> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<OptionDeal>) => response.ok),
                map((optionDeal: HttpResponse<OptionDeal>) => optionDeal.body)
            );
        }
        return of(new OptionDeal());
    }
}

export const optionDealRoute: Routes = [
    {
        path: '',
        component: OptionDealComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponApp.optionDeal.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: OptionDealDetailComponent,
        resolve: {
            optionDeal: OptionDealResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponApp.optionDeal.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: OptionDealUpdateComponent,
        resolve: {
            optionDeal: OptionDealResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponApp.optionDeal.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: OptionDealUpdateComponent,
        resolve: {
            optionDeal: OptionDealResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponApp.optionDeal.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const optionDealPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: OptionDealDeletePopupComponent,
        resolve: {
            optionDeal: OptionDealResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponApp.optionDeal.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
