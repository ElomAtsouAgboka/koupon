import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMembre } from 'app/shared/model/membre.model';

type EntityResponseType = HttpResponse<IMembre>;
type EntityArrayResponseType = HttpResponse<IMembre[]>;

@Injectable({ providedIn: 'root' })
export class MembreService {
    public resourceUrl = SERVER_API_URL + 'api/membres';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/membres';

    constructor(protected http: HttpClient) {}

    create(membre: IMembre): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(membre);
        return this.http
            .post<IMembre>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(membre: IMembre): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(membre);
        return this.http
            .put<IMembre>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IMembre>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IMembre[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IMembre[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(membre: IMembre): IMembre {
        const copy: IMembre = Object.assign({}, membre, {
            dateDeNaissance:
                membre.dateDeNaissance != null && membre.dateDeNaissance.isValid() ? membre.dateDeNaissance.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateDeNaissance = res.body.dateDeNaissance != null ? moment(res.body.dateDeNaissance) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((membre: IMembre) => {
                membre.dateDeNaissance = membre.dateDeNaissance != null ? moment(membre.dateDeNaissance) : null;
            });
        }
        return res;
    }
}
