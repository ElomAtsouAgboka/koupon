import { IDeal } from 'app/shared/model/deal.model';
import { ICommande } from 'app/shared/model/commande.model';

export interface ICoupon {
    id?: number;
    refCoupon?: string;
    dateAchat?: string;
    dateUtilisation?: string;
    estCadeaux?: boolean;
    deal?: IDeal;
    commande?: ICommande;
}

export class Coupon implements ICoupon {
    constructor(
        public id?: number,
        public refCoupon?: string,
        public dateAchat?: string,
        public dateUtilisation?: string,
        public estCadeaux?: boolean,
        public deal?: IDeal,
        public commande?: ICommande
    ) {
        this.estCadeaux = this.estCadeaux || false;
    }
}
