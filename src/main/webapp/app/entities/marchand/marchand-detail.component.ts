import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMarchand } from 'app/shared/model/marchand.model';

@Component({
    selector: 'jhi-marchand-detail',
    templateUrl: './marchand-detail.component.html'
})
export class MarchandDetailComponent implements OnInit {
    marchand: IMarchand;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ marchand }) => {
            this.marchand = marchand;
        });
    }

    previousState() {
        window.history.back();
    }
}
