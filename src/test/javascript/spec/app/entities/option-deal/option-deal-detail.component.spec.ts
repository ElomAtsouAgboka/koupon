/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KouponTestModule } from '../../../test.module';
import { OptionDealDetailComponent } from 'app/entities/option-deal/option-deal-detail.component';
import { OptionDeal } from 'app/shared/model/option-deal.model';

describe('Component Tests', () => {
    describe('OptionDeal Management Detail Component', () => {
        let comp: OptionDealDetailComponent;
        let fixture: ComponentFixture<OptionDealDetailComponent>;
        const route = ({ data: of({ optionDeal: new OptionDeal(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KouponTestModule],
                declarations: [OptionDealDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(OptionDealDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OptionDealDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.optionDeal).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
