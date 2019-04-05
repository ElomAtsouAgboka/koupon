import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOptionDeal } from 'app/shared/model/option-deal.model';

@Component({
    selector: 'jhi-option-deal-detail',
    templateUrl: './option-deal-detail.component.html'
})
export class OptionDealDetailComponent implements OnInit {
    optionDeal: IOptionDeal;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ optionDeal }) => {
            this.optionDeal = optionDeal;
        });
    }

    previousState() {
        window.history.back();
    }
}
