import { IDeal } from 'app/shared/model/deal.model';

export interface IAvis {
    id?: number;
    textAvis?: string;
    derniereUtilisationCoupon?: number;
    deal?: IDeal;
}

export class Avis implements IAvis {
    constructor(public id?: number, public textAvis?: string, public derniereUtilisationCoupon?: number, public deal?: IDeal) {}
}
