import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOptionDeal } from 'app/shared/model/option-deal.model';

type EntityResponseType = HttpResponse<IOptionDeal>;
type EntityArrayResponseType = HttpResponse<IOptionDeal[]>;

@Injectable({ providedIn: 'root' })
export class OptionDealService {
    public resourceUrl = SERVER_API_URL + 'api/option-deals';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/option-deals';

    constructor(protected http: HttpClient) {}

    create(optionDeal: IOptionDeal): Observable<EntityResponseType> {
        return this.http.post<IOptionDeal>(this.resourceUrl, optionDeal, { observe: 'response' });
    }

    update(optionDeal: IOptionDeal): Observable<EntityResponseType> {
        return this.http.put<IOptionDeal>(this.resourceUrl, optionDeal, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IOptionDeal>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IOptionDeal[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IOptionDeal[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
