import { IDeal } from 'app/shared/model/deal.model';

export interface IOptionDeal {
    id?: number;
    libOptionDeal?: string;
    prixNormalOptionDeal?: number;
    prixReductionOptionBonPlan?: number;
    pcReductionOptionBonPlan?: number;
    deal?: IDeal;
}

export class OptionDeal implements IOptionDeal {
    constructor(
        public id?: number,
        public libOptionDeal?: string,
        public prixNormalOptionDeal?: number,
        public prixReductionOptionBonPlan?: number,
        public pcReductionOptionBonPlan?: number,
        public deal?: IDeal
    ) {}
}
