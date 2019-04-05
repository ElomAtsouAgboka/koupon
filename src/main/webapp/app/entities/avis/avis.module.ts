import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { KouponSharedModule } from 'app/shared';
import {
    AvisComponent,
    AvisDetailComponent,
    AvisUpdateComponent,
    AvisDeletePopupComponent,
    AvisDeleteDialogComponent,
    avisRoute,
    avisPopupRoute
} from './';

const ENTITY_STATES = [...avisRoute, ...avisPopupRoute];

@NgModule({
    imports: [KouponSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [AvisComponent, AvisDetailComponent, AvisUpdateComponent, AvisDeleteDialogComponent, AvisDeletePopupComponent],
    entryComponents: [AvisComponent, AvisUpdateComponent, AvisDeleteDialogComponent, AvisDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KouponAvisModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
