import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { KouponSharedModule } from 'app/shared';
import {
    OptionDealComponent,
    OptionDealDetailComponent,
    OptionDealUpdateComponent,
    OptionDealDeletePopupComponent,
    OptionDealDeleteDialogComponent,
    optionDealRoute,
    optionDealPopupRoute
} from './';

const ENTITY_STATES = [...optionDealRoute, ...optionDealPopupRoute];

@NgModule({
    imports: [KouponSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        OptionDealComponent,
        OptionDealDetailComponent,
        OptionDealUpdateComponent,
        OptionDealDeleteDialogComponent,
        OptionDealDeletePopupComponent
    ],
    entryComponents: [OptionDealComponent, OptionDealUpdateComponent, OptionDealDeleteDialogComponent, OptionDealDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KouponOptionDealModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
