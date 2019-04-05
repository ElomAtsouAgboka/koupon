import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { KouponSharedModule } from 'app/shared';
import {
    CouponComponent,
    CouponDetailComponent,
    CouponUpdateComponent,
    CouponDeletePopupComponent,
    CouponDeleteDialogComponent,
    couponRoute,
    couponPopupRoute
} from './';

const ENTITY_STATES = [...couponRoute, ...couponPopupRoute];

@NgModule({
    imports: [KouponSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [CouponComponent, CouponDetailComponent, CouponUpdateComponent, CouponDeleteDialogComponent, CouponDeletePopupComponent],
    entryComponents: [CouponComponent, CouponUpdateComponent, CouponDeleteDialogComponent, CouponDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KouponCouponModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
