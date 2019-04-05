import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { KouponSharedModule } from 'app/shared';
import {
    DealComponent,
    DealDetailComponent,
    DealUpdateComponent,
    DealDeletePopupComponent,
    DealDeleteDialogComponent,
    dealRoute,
    dealPopupRoute
} from './';

const ENTITY_STATES = [...dealRoute, ...dealPopupRoute];

@NgModule({
    imports: [KouponSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [DealComponent, DealDetailComponent, DealUpdateComponent, DealDeleteDialogComponent, DealDeletePopupComponent],
    entryComponents: [DealComponent, DealUpdateComponent, DealDeleteDialogComponent, DealDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KouponDealModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
