/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KouponTestModule } from '../../../test.module';
import { MarchandDetailComponent } from 'app/entities/marchand/marchand-detail.component';
import { Marchand } from 'app/shared/model/marchand.model';

describe('Component Tests', () => {
    describe('Marchand Management Detail Component', () => {
        let comp: MarchandDetailComponent;
        let fixture: ComponentFixture<MarchandDetailComponent>;
        const route = ({ data: of({ marchand: new Marchand(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KouponTestModule],
                declarations: [MarchandDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MarchandDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MarchandDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.marchand).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
