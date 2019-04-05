import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITypeDeCommerce } from 'app/shared/model/type-de-commerce.model';

type EntityResponseType = HttpResponse<ITypeDeCommerce>;
type EntityArrayResponseType = HttpResponse<ITypeDeCommerce[]>;

@Injectable({ providedIn: 'root' })
export class TypeDeCommerceService {
    public resourceUrl = SERVER_API_URL + 'api/type-de-commerces';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/type-de-commerces';

    constructor(protected http: HttpClient) {}

    create(typeDeCommerce: ITypeDeCommerce): Observable<EntityResponseType> {
        return this.http.post<ITypeDeCommerce>(this.resourceUrl, typeDeCommerce, { observe: 'response' });
    }

    update(typeDeCommerce: ITypeDeCommerce): Observable<EntityResponseType> {
        return this.http.put<ITypeDeCommerce>(this.resourceUrl, typeDeCommerce, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITypeDeCommerce>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITypeDeCommerce[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITypeDeCommerce[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
