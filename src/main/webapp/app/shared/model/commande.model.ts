import { Moment } from 'moment';
import { ICoupon } from 'app/shared/model/coupon.model';
import { IMembre } from 'app/shared/model/membre.model';

export interface ICommande {
    id?: number;
    refCommande?: string;
    dateCommande?: Moment;
    coupons?: ICoupon[];
    membre?: IMembre;
}

export class Commande implements ICommande {
    constructor(
        public id?: number,
        public refCommande?: string,
        public dateCommande?: Moment,
        public coupons?: ICoupon[],
        public membre?: IMembre
    ) {}
}
