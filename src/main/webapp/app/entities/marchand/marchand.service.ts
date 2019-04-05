import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMarchand } from 'app/shared/model/marchand.model';

type EntityResponseType = HttpResponse<IMarchand>;
type EntityArrayResponseType = HttpResponse<IMarchand[]>;

@Injectable({ providedIn: 'root' })
export class MarchandService {
    public resourceUrl = SERVER_API_URL + 'api/marchands';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/marchands';

    constructor(protected http: HttpClient) {}

    create(marchand: IMarchand): Observable<EntityResponseType> {
        return this.http.post<IMarchand>(this.resourceUrl, marchand, { observe: 'response' });
    }

    update(marchand: IMarchand): Observable<EntityResponseType> {
        return this.http.put<IMarchand>(this.resourceUrl, marchand, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMarchand>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMarchand[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMarchand[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
