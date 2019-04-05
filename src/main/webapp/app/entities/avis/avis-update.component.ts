import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAvis } from 'app/shared/model/avis.model';
import { AvisService } from './avis.service';
import { IDeal } from 'app/shared/model/deal.model';
import { DealService } from 'app/entities/deal';

@Component({
    selector: 'jhi-avis-update',
    templateUrl: './avis-update.component.html'
})
export class AvisUpdateComponent implements OnInit {
    avis: IAvis;
    isSaving: boolean;

    deals: IDeal[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected avisService: AvisService,
        protected dealService: DealService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ avis }) => {
            this.avis = avis;
        });
        this.dealService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDeal[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDeal[]>) => response.body)
            )
            .subscribe((res: IDeal[]) => (this.deals = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.avis.id !== undefined) {
            this.subscribeToSaveResponse(this.avisService.update(this.avis));
        } else {
            this.subscribeToSaveResponse(this.avisService.create(this.avis));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAvis>>) {
        result.subscribe((res: HttpResponse<IAvis>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackDealById(index: number, item: IDeal) {
        return item.id;
    }
}
