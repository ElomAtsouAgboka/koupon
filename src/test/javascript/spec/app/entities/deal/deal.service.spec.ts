/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { DealService } from 'app/entities/deal/deal.service';
import { IDeal, Deal } from 'app/shared/model/deal.model';

describe('Service Tests', () => {
    describe('Deal Service', () => {
        let injector: TestBed;
        let service: DealService;
        let httpMock: HttpTestingController;
        let elemDefault: IDeal;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(DealService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Deal(
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                0,
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                false,
                false,
                currentDate,
                currentDate
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateCreationDeal: currentDate.format(DATE_FORMAT),
                        dateClotureDeal: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Deal', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dateCreationDeal: currentDate.format(DATE_FORMAT),
                        dateClotureDeal: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateCreationDeal: currentDate,
                        dateClotureDeal: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Deal(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Deal', async () => {
                const returnedFromService = Object.assign(
                    {
                        refDeal: 'BBBBBB',
                        titreDeal: 'BBBBBB',
                        descriptionDeal: 'BBBBBB',
                        prixReduitDeal: 'BBBBBB',
                        prixNormalDeal: 1,
                        pcReductionDeal: 1,
                        photoDealUn: 'BBBBBB',
                        photoDealDeux: 'BBBBBB',
                        photoDealTrois: 'BBBBBB',
                        photoDealQuatre: 'BBBBBB',
                        photoDealCinq: 'BBBBBB',
                        photoDealSix: 'BBBBBB',
                        photoDealSpet: 'BBBBBB',
                        photoDealHuit: 'BBBBBB',
                        photoDealNeuf: 'BBBBBB',
                        photoDealDix: 'BBBBBB',
                        photoMinDealUn: 'BBBBBB',
                        photoMinDealDeux: 'BBBBBB',
                        photoMinDealTrois: 'BBBBBB',
                        photoMinDealQuatre: 'BBBBBB',
                        photoMinDealCinq: 'BBBBBB',
                        photoMinDealSix: 'BBBBBB',
                        photoMinDealSpet: 'BBBBBB',
                        photoMinDealHuit: 'BBBBBB',
                        photoMinDealNeuf: 'BBBBBB',
                        photoMinDealDix: 'BBBBBB',
                        descPointFortDeal: 'BBBBBB',
                        detailsOffreDeal: 'BBBBBB',
                        conditionsDeal: 'BBBBBB',
                        estLimite: true,
                        estEpuise: true,
                        dateCreationDeal: currentDate.format(DATE_FORMAT),
                        dateClotureDeal: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dateCreationDeal: currentDate,
                        dateClotureDeal: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Deal', async () => {
                const returnedFromService = Object.assign(
                    {
                        refDeal: 'BBBBBB',
                        titreDeal: 'BBBBBB',
                        descriptionDeal: 'BBBBBB',
                        prixReduitDeal: 'BBBBBB',
                        prixNormalDeal: 1,
                        pcReductionDeal: 1,
                        photoDealUn: 'BBBBBB',
                        photoDealDeux: 'BBBBBB',
                        photoDealTrois: 'BBBBBB',
                        photoDealQuatre: 'BBBBBB',
                        photoDealCinq: 'BBBBBB',
                        photoDealSix: 'BBBBBB',
                        photoDealSpet: 'BBBBBB',
                        photoDealHuit: 'BBBBBB',
                        photoDealNeuf: 'BBBBBB',
                        photoDealDix: 'BBBBBB',
                        photoMinDealUn: 'BBBBBB',
                        photoMinDealDeux: 'BBBBBB',
                        photoMinDealTrois: 'BBBBBB',
                        photoMinDealQuatre: 'BBBBBB',
                        photoMinDealCinq: 'BBBBBB',
                        photoMinDealSix: 'BBBBBB',
                        photoMinDealSpet: 'BBBBBB',
                        photoMinDealHuit: 'BBBBBB',
                        photoMinDealNeuf: 'BBBBBB',
                        photoMinDealDix: 'BBBBBB',
                        descPointFortDeal: 'BBBBBB',
                        detailsOffreDeal: 'BBBBBB',
                        conditionsDeal: 'BBBBBB',
                        estLimite: true,
                        estEpuise: true,
                        dateCreationDeal: currentDate.format(DATE_FORMAT),
                        dateClotureDeal: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateCreationDeal: currentDate,
                        dateClotureDeal: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Deal', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
