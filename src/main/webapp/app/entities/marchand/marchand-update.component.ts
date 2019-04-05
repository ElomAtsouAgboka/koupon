import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IMarchand } from 'app/shared/model/marchand.model';
import { MarchandService } from './marchand.service';

@Component({
    selector: 'jhi-marchand-update',
    templateUrl: './marchand-update.component.html'
})
export class MarchandUpdateComponent implements OnInit {
    marchand: IMarchand;
    isSaving: boolean;

    constructor(protected marchandService: MarchandService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ marchand }) => {
            this.marchand = marchand;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.marchand.id !== undefined) {
            this.subscribeToSaveResponse(this.marchandService.update(this.marchand));
        } else {
            this.subscribeToSaveResponse(this.marchandService.create(this.marchand));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMarchand>>) {
        result.subscribe((res: HttpResponse<IMarchand>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
