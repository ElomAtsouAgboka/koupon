import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ICommande } from 'app/shared/model/commande.model';
import { CommandeService } from './commande.service';
import { IMembre } from 'app/shared/model/membre.model';
import { MembreService } from 'app/entities/membre';

@Component({
    selector: 'jhi-commande-update',
    templateUrl: './commande-update.component.html'
})
export class CommandeUpdateComponent implements OnInit {
    commande: ICommande;
    isSaving: boolean;

    membres: IMembre[];
    dateCommandeDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected commandeService: CommandeService,
        protected membreService: MembreService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ commande }) => {
            this.commande = commande;
        });
        this.membreService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMembre[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMembre[]>) => response.body)
            )
            .subscribe((res: IMembre[]) => (this.membres = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.commande.id !== undefined) {
            this.subscribeToSaveResponse(this.commandeService.update(this.commande));
        } else {
            this.subscribeToSaveResponse(this.commandeService.create(this.commande));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommande>>) {
        result.subscribe((res: HttpResponse<ICommande>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMembreById(index: number, item: IMembre) {
        return item.id;
    }
}
