import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOptionDeal } from 'app/shared/model/option-deal.model';
import { OptionDealService } from './option-deal.service';

@Component({
    selector: 'jhi-option-deal-delete-dialog',
    templateUrl: './option-deal-delete-dialog.component.html'
})
export class OptionDealDeleteDialogComponent {
    optionDeal: IOptionDeal;

    constructor(
        protected optionDealService: OptionDealService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.optionDealService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'optionDealListModification',
                content: 'Deleted an optionDeal'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-option-deal-delete-popup',
    template: ''
})
export class OptionDealDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ optionDeal }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(OptionDealDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.optionDeal = optionDeal;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/option-deal', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/option-deal', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
