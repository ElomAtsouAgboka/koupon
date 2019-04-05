import { Moment } from 'moment';
import { IOptionDeal } from 'app/shared/model/option-deal.model';
import { ICoupon } from 'app/shared/model/coupon.model';
import { IAvis } from 'app/shared/model/avis.model';
import { IMenu } from 'app/shared/model/menu.model';
import { ICategorie } from 'app/shared/model/categorie.model';
import { ICommerce } from 'app/shared/model/commerce.model';

export interface IDeal {
    id?: number;
    refDeal?: string;
    titreDeal?: string;
    descriptionDeal?: string;
    prixReduitDeal?: string;
    prixNormalDeal?: number;
    pcReductionDeal?: number;
    photoDealUn?: string;
    photoDealDeux?: string;
    photoDealTrois?: string;
    photoDealQuatre?: string;
    photoDealCinq?: string;
    photoDealSix?: string;
    photoDealSpet?: string;
    photoDealHuit?: string;
    photoDealNeuf?: string;
    photoDealDix?: string;
    photoMinDealUn?: string;
    photoMinDealDeux?: string;
    photoMinDealTrois?: string;
    photoMinDealQuatre?: string;
    photoMinDealCinq?: string;
    photoMinDealSix?: string;
    photoMinDealSpet?: string;
    photoMinDealHuit?: string;
    photoMinDealNeuf?: string;
    photoMinDealDix?: string;
    descPointFortDeal?: string;
    detailsOffreDeal?: string;
    conditionsDeal?: string;
    estLimite?: boolean;
    estEpuise?: boolean;
    dateCreationDeal?: Moment;
    dateClotureDeal?: Moment;
    optiondeals?: IOptionDeal[];
    coupons?: ICoupon[];
    avis?: IAvis[];
    menus?: IMenu[];
    categories?: ICategorie[];
    commerce?: ICommerce;
}

export class Deal implements IDeal {
    constructor(
        public id?: number,
        public refDeal?: string,
        public titreDeal?: string,
        public descriptionDeal?: string,
        public prixReduitDeal?: string,
        public prixNormalDeal?: number,
        public pcReductionDeal?: number,
        public photoDealUn?: string,
        public photoDealDeux?: string,
        public photoDealTrois?: string,
        public photoDealQuatre?: string,
        public photoDealCinq?: string,
        public photoDealSix?: string,
        public photoDealSpet?: string,
        public photoDealHuit?: string,
        public photoDealNeuf?: string,
        public photoDealDix?: string,
        public photoMinDealUn?: string,
        public photoMinDealDeux?: string,
        public photoMinDealTrois?: string,
        public photoMinDealQuatre?: string,
        public photoMinDealCinq?: string,
        public photoMinDealSix?: string,
        public photoMinDealSpet?: string,
        public photoMinDealHuit?: string,
        public photoMinDealNeuf?: string,
        public photoMinDealDix?: string,
        public descPointFortDeal?: string,
        public detailsOffreDeal?: string,
        public conditionsDeal?: string,
        public estLimite?: boolean,
        public estEpuise?: boolean,
        public dateCreationDeal?: Moment,
        public dateClotureDeal?: Moment,
        public optiondeals?: IOptionDeal[],
        public coupons?: ICoupon[],
        public avis?: IAvis[],
        public menus?: IMenu[],
        public categories?: ICategorie[],
        public commerce?: ICommerce
    ) {
        this.estLimite = this.estLimite || false;
        this.estEpuise = this.estEpuise || false;
    }
}
