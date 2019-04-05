import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { KouponSharedModule } from 'app/shared';
import {
    CommerceComponent,
    CommerceDetailComponent,
    CommerceUpdateComponent,
    CommerceDeletePopupComponent,
    CommerceDeleteDialogComponent,
    commerceRoute,
    commercePopupRoute
} from './';

const ENTITY_STATES = [...commerceRoute, ...commercePopupRoute];

@NgModule({
    imports: [KouponSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommerceComponent,
        CommerceDetailComponent,
        CommerceUpdateComponent,
        CommerceDeleteDialogComponent,
        CommerceDeletePopupComponent
    ],
    entryComponents: [CommerceComponent, CommerceUpdateComponent, CommerceDeleteDialogComponent, CommerceDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KouponCommerceModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
