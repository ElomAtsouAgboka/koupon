import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { KouponSharedModule } from 'app/shared';
import {
    TypeDeCommerceComponent,
    TypeDeCommerceDetailComponent,
    TypeDeCommerceUpdateComponent,
    TypeDeCommerceDeletePopupComponent,
    TypeDeCommerceDeleteDialogComponent,
    typeDeCommerceRoute,
    typeDeCommercePopupRoute
} from './';

const ENTITY_STATES = [...typeDeCommerceRoute, ...typeDeCommercePopupRoute];

@NgModule({
    imports: [KouponSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TypeDeCommerceComponent,
        TypeDeCommerceDetailComponent,
        TypeDeCommerceUpdateComponent,
        TypeDeCommerceDeleteDialogComponent,
        TypeDeCommerceDeletePopupComponent
    ],
    entryComponents: [
        TypeDeCommerceComponent,
        TypeDeCommerceUpdateComponent,
        TypeDeCommerceDeleteDialogComponent,
        TypeDeCommerceDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KouponTypeDeCommerceModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
