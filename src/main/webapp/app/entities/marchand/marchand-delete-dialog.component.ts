import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMarchand } from 'app/shared/model/marchand.model';
import { MarchandService } from './marchand.service';

@Component({
    selector: 'jhi-marchand-delete-dialog',
    templateUrl: './marchand-delete-dialog.component.html'
})
export class MarchandDeleteDialogComponent {
    marchand: IMarchand;

    constructor(protected marchandService: MarchandService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.marchandService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'marchandListModification',
                content: 'Deleted an marchand'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-marchand-delete-popup',
    template: ''
})
export class MarchandDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ marchand }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MarchandDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.marchand = marchand;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/marchand', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/marchand', { outlets: { popup: null } }]);
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
