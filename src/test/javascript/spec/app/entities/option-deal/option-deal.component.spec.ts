/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KouponTestModule } from '../../../test.module';
import { OptionDealComponent } from 'app/entities/option-deal/option-deal.component';
import { OptionDealService } from 'app/entities/option-deal/option-deal.service';
import { OptionDeal } from 'app/shared/model/option-deal.model';

describe('Component Tests', () => {
    describe('OptionDeal Management Component', () => {
        let comp: OptionDealComponent;
        let fixture: ComponentFixture<OptionDealComponent>;
        let service: OptionDealService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KouponTestModule],
                declarations: [OptionDealComponent],
                providers: []
            })
                .overrideTemplate(OptionDealComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OptionDealComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OptionDealService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new OptionDeal(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.optionDeals[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
