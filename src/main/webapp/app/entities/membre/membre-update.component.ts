import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { IMembre } from 'app/shared/model/membre.model';
import { MembreService } from './membre.service';

@Component({
    selector: 'jhi-membre-update',
    templateUrl: './membre-update.component.html'
})
export class MembreUpdateComponent implements OnInit {
    membre: IMembre;
    isSaving: boolean;
    dateDeNaissanceDp: any;

    constructor(protected membreService: MembreService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ membre }) => {
            this.membre = membre;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.membre.id !== undefined) {
            this.subscribeToSaveResponse(this.membreService.update(this.membre));
        } else {
            this.subscribeToSaveResponse(this.membreService.create(this.membre));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMembre>>) {
        result.subscribe((res: HttpResponse<IMembre>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
