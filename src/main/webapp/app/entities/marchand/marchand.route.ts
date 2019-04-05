import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Marchand } from 'app/shared/model/marchand.model';
import { MarchandService } from './marchand.service';
import { MarchandComponent } from './marchand.component';
import { MarchandDetailComponent } from './marchand-detail.component';
import { MarchandUpdateComponent } from './marchand-update.component';
import { MarchandDeletePopupComponent } from './marchand-delete-dialog.component';
import { IMarchand } from 'app/shared/model/marchand.model';

@Injectable({ providedIn: 'root' })
export class MarchandResolve implements Resolve<IMarchand> {
    constructor(private service: MarchandService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMarchand> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Marchand>) => response.ok),
                map((marchand: HttpResponse<Marchand>) => marchand.body)
            );
        }
        return of(new Marchand());
    }
}

export const marchandRoute: Routes = [
    {
        path: '',
        component: MarchandComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponApp.marchand.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MarchandDetailComponent,
        resolve: {
            marchand: MarchandResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponApp.marchand.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MarchandUpdateComponent,
        resolve: {
            marchand: MarchandResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponApp.marchand.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MarchandUpdateComponent,
        resolve: {
            marchand: MarchandResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponApp.marchand.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const marchandPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MarchandDeletePopupComponent,
        resolve: {
            marchand: MarchandResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponApp.marchand.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
