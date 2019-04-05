/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { KouponTestModule } from '../../../test.module';
import { OptionDealUpdateComponent } from 'app/entities/option-deal/option-deal-update.component';
import { OptionDealService } from 'app/entities/option-deal/option-deal.service';
import { OptionDeal } from 'app/shared/model/option-deal.model';

describe('Component Tests', () => {
    describe('OptionDeal Management Update Component', () => {
        let comp: OptionDealUpdateComponent;
        let fixture: ComponentFixture<OptionDealUpdateComponent>;
        let service: OptionDealService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KouponTestModule],
                declarations: [OptionDealUpdateComponent]
            })
                .overrideTemplate(OptionDealUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OptionDealUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OptionDealService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new OptionDeal(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.optionDeal = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new OptionDeal();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.optionDeal = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
