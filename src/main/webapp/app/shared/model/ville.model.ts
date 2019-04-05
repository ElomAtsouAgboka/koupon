import { IQuartier } from 'app/shared/model/quartier.model';
import { IPays } from 'app/shared/model/pays.model';

export interface IVille {
    id?: number;
    nomVille?: string;
    quartiers?: IQuartier[];
    pays?: IPays;
}

export class Ville implements IVille {
    constructor(public id?: number, public nomVille?: string, public quartiers?: IQuartier[], public pays?: IPays) {}
}
