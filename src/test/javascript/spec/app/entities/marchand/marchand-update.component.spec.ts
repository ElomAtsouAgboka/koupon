/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { KouponTestModule } from '../../../test.module';
import { MarchandUpdateComponent } from 'app/entities/marchand/marchand-update.component';
import { MarchandService } from 'app/entities/marchand/marchand.service';
import { Marchand } from 'app/shared/model/marchand.model';

describe('Component Tests', () => {
    describe('Marchand Management Update Component', () => {
        let comp: MarchandUpdateComponent;
        let fixture: ComponentFixture<MarchandUpdateComponent>;
        let service: MarchandService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KouponTestModule],
                declarations: [MarchandUpdateComponent]
            })
                .overrideTemplate(MarchandUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MarchandUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarchandService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Marchand(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.marchand = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Marchand();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.marchand = entity;
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
