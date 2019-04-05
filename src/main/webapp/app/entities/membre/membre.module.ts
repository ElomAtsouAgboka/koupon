import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { KouponSharedModule } from 'app/shared';
import {
    MembreComponent,
    MembreDetailComponent,
    MembreUpdateComponent,
    MembreDeletePopupComponent,
    MembreDeleteDialogComponent,
    membreRoute,
    membrePopupRoute
} from './';

const ENTITY_STATES = [...membreRoute, ...membrePopupRoute];

@NgModule({
    imports: [KouponSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [MembreComponent, MembreDetailComponent, MembreUpdateComponent, MembreDeleteDialogComponent, MembreDeletePopupComponent],
    entryComponents: [MembreComponent, MembreUpdateComponent, MembreDeleteDialogComponent, MembreDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KouponMembreModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
