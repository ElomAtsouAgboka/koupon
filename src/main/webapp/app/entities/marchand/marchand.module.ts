import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { KouponSharedModule } from 'app/shared';
import {
    MarchandComponent,
    MarchandDetailComponent,
    MarchandUpdateComponent,
    MarchandDeletePopupComponent,
    MarchandDeleteDialogComponent,
    marchandRoute,
    marchandPopupRoute
} from './';

const ENTITY_STATES = [...marchandRoute, ...marchandPopupRoute];

@NgModule({
    imports: [KouponSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MarchandComponent,
        MarchandDetailComponent,
        MarchandUpdateComponent,
        MarchandDeleteDialogComponent,
        MarchandDeletePopupComponent
    ],
    entryComponents: [MarchandComponent, MarchandUpdateComponent, MarchandDeleteDialogComponent, MarchandDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KouponMarchandModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
