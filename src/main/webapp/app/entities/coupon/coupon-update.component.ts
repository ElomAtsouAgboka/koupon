import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICoupon } from 'app/shared/model/coupon.model';
import { CouponService } from './coupon.service';
import { IDeal } from 'app/shared/model/deal.model';
import { DealService } from 'app/entities/deal';
import { ICommande } from 'app/shared/model/commande.model';
import { CommandeService } from 'app/entities/commande';

@Component({
    selector: 'jhi-coupon-update',
    templateUrl: './coupon-update.component.html'
})
export class CouponUpdateComponent implements OnInit {
    coupon: ICoupon;
    isSaving: boolean;

    deals: IDeal[];

    commandes: ICommande[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected couponService: CouponService,
        protected dealService: DealService,
        protected commandeService: CommandeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ coupon }) => {
            this.coupon = coupon;
        });
        this.dealService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDeal[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDeal[]>) => response.body)
            )
            .subscribe((res: IDeal[]) => (this.deals = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.commandeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICommande[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICommande[]>) => response.body)
            )
            .subscribe((res: ICommande[]) => (this.commandes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.coupon.id !== undefined) {
            this.subscribeToSaveResponse(this.couponService.update(this.coupon));
        } else {
            this.subscribeToSaveResponse(this.couponService.create(this.coupon));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICoupon>>) {
        result.subscribe((res: HttpResponse<ICoupon>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCommandeById(index: number, item: ICommande) {
        return item.id;
    }
}
