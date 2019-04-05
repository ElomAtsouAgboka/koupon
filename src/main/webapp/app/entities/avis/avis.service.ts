import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAvis } from 'app/shared/model/avis.model';

type EntityResponseType = HttpResponse<IAvis>;
type EntityArrayResponseType = HttpResponse<IAvis[]>;

@Injectable({ providedIn: 'root' })
export class AvisService {
    public resourceUrl = SERVER_API_URL + 'api/avis';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/avis';

    constructor(protected http: HttpClient) {}

    create(avis: IAvis): Observable<EntityResponseType> {
        return this.http.post<IAvis>(this.resourceUrl, avis, { observe: 'response' });
    }

    update(avis: IAvis): Observable<EntityResponseType> {
        return this.http.put<IAvis>(this.resourceUrl, avis, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAvis>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAvis[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAvis[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
