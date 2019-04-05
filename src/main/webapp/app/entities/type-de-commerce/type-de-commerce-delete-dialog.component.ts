import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeDeCommerce } from 'app/shared/model/type-de-commerce.model';
import { TypeDeCommerceService } from './type-de-commerce.service';

@Component({
    selector: 'jhi-type-de-commerce-delete-dialog',
    templateUrl: './type-de-commerce-delete-dialog.component.html'
})
export class TypeDeCommerceDeleteDialogComponent {
    typeDeCommerce: ITypeDeCommerce;

    constructor(
        protected typeDeCommerceService: TypeDeCommerceService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeDeCommerceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'typeDeCommerceListModification',
                content: 'Deleted an typeDeCommerce'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-de-commerce-delete-popup',
    template: ''
})
export class TypeDeCommerceDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeDeCommerce }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TypeDeCommerceDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.typeDeCommerce = typeDeCommerce;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/type-de-commerce', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/type-de-commerce', { outlets: { popup: null } }]);
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
