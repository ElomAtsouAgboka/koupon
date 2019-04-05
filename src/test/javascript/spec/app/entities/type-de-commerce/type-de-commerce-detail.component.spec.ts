/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KouponTestModule } from '../../../test.module';
import { TypeDeCommerceDetailComponent } from 'app/entities/type-de-commerce/type-de-commerce-detail.component';
import { TypeDeCommerce } from 'app/shared/model/type-de-commerce.model';

describe('Component Tests', () => {
    describe('TypeDeCommerce Management Detail Component', () => {
        let comp: TypeDeCommerceDetailComponent;
        let fixture: ComponentFixture<TypeDeCommerceDetailComponent>;
        const route = ({ data: of({ typeDeCommerce: new TypeDeCommerce(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KouponTestModule],
                declarations: [TypeDeCommerceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TypeDeCommerceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeDeCommerceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.typeDeCommerce).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
