/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { KouponTestModule } from '../../../test.module';
import { TypeDeCommerceUpdateComponent } from 'app/entities/type-de-commerce/type-de-commerce-update.component';
import { TypeDeCommerceService } from 'app/entities/type-de-commerce/type-de-commerce.service';
import { TypeDeCommerce } from 'app/shared/model/type-de-commerce.model';

describe('Component Tests', () => {
    describe('TypeDeCommerce Management Update Component', () => {
        let comp: TypeDeCommerceUpdateComponent;
        let fixture: ComponentFixture<TypeDeCommerceUpdateComponent>;
        let service: TypeDeCommerceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KouponTestModule],
                declarations: [TypeDeCommerceUpdateComponent]
            })
                .overrideTemplate(TypeDeCommerceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TypeDeCommerceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeDeCommerceService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TypeDeCommerce(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.typeDeCommerce = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TypeDeCommerce();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.typeDeCommerce = entity;
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
