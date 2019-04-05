/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { KouponTestModule } from '../../../test.module';
import { OptionDealDeleteDialogComponent } from 'app/entities/option-deal/option-deal-delete-dialog.component';
import { OptionDealService } from 'app/entities/option-deal/option-deal.service';

describe('Component Tests', () => {
    describe('OptionDeal Management Delete Component', () => {
        let comp: OptionDealDeleteDialogComponent;
        let fixture: ComponentFixture<OptionDealDeleteDialogComponent>;
        let service: OptionDealService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KouponTestModule],
                declarations: [OptionDealDeleteDialogComponent]
            })
                .overrideTemplate(OptionDealDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OptionDealDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OptionDealService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
