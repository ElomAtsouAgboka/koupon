import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDeal } from 'app/shared/model/deal.model';

type EntityResponseType = HttpResponse<IDeal>;
type EntityArrayResponseType = HttpResponse<IDeal[]>;

@Injectable({ providedIn: 'root' })
export class DealService {
    public resourceUrl = SERVER_API_URL + 'api/deals';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/deals';

    constructor(protected http: HttpClient) {}

    create(deal: IDeal): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(deal);
        return this.http
            .post<IDeal>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(deal: IDeal): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(deal);
        return this.http
            .put<IDeal>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IDeal>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDeal[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDeal[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(deal: IDeal): IDeal {
        const copy: IDeal = Object.assign({}, deal, {
            dateCreationDeal:
                deal.dateCreationDeal != null && deal.dateCreationDeal.isValid() ? deal.dateCreationDeal.format(DATE_FORMAT) : null,
            dateClotureDeal:
                deal.dateClotureDeal != null && deal.dateClotureDeal.isValid() ? deal.dateClotureDeal.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateCreationDeal = res.body.dateCreationDeal != null ? moment(res.body.dateCreationDeal) : null;
            res.body.dateClotureDeal = res.body.dateClotureDeal != null ? moment(res.body.dateClotureDeal) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((deal: IDeal) => {
                deal.dateCreationDeal = deal.dateCreationDeal != null ? moment(deal.dateCreationDeal) : null;
                deal.dateClotureDeal = deal.dateClotureDeal != null ? moment(deal.dateClotureDeal) : null;
            });
        }
        return res;
    }
}
