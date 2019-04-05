import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMenu } from 'app/shared/model/menu.model';
import { MenuService } from './menu.service';
import { IDeal } from 'app/shared/model/deal.model';
import { DealService } from 'app/entities/deal';

@Component({
    selector: 'jhi-menu-update',
    templateUrl: './menu-update.component.html'
})
export class MenuUpdateComponent implements OnInit {
    menu: IMenu;
    isSaving: boolean;

    menus: IMenu[];

    deals: IDeal[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected menuService: MenuService,
        protected dealService: DealService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ menu }) => {
            this.menu = menu;
        });
        this.menuService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMenu[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMenu[]>) => response.body)
            )
            .subscribe((res: IMenu[]) => (this.menus = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.menu.id !== undefined) {
            this.subscribeToSaveResponse(this.menuService.update(this.menu));
        } else {
            this.subscribeToSaveResponse(this.menuService.create(this.menu));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMenu>>) {
        result.subscribe((res: HttpResponse<IMenu>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMenuById(index: number, item: IMenu) {
        return item.id;
    }

    trackDealById(index: number, item: IDeal) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
