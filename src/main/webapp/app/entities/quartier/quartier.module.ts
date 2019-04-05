import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { KouponSharedModule } from 'app/shared';
import {
    QuartierComponent,
    QuartierDetailComponent,
    QuartierUpdateComponent,
    QuartierDeletePopupComponent,
    QuartierDeleteDialogComponent,
    quartierRoute,
    quartierPopupRoute
} from './';

const ENTITY_STATES = [...quartierRoute, ...quartierPopupRoute];

@NgModule({
    imports: [KouponSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        QuartierComponent,
        QuartierDetailComponent,
        QuartierUpdateComponent,
        QuartierDeleteDialogComponent,
        QuartierDeletePopupComponent
    ],
    entryComponents: [QuartierComponent, QuartierUpdateComponent, QuartierDeleteDialogComponent, QuartierDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KouponQuartierModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
