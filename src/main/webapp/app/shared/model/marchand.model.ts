import { ICommerce } from 'app/shared/model/commerce.model';

export interface IMarchand {
    id?: number;
    nomMarchand?: string;
    prenomMarchand?: string;
    telPrincipale?: string;
    telSecondaire?: string;
    emailPrincipale?: string;
    emailSecondaire?: string;
    newsletter?: boolean;
    commerce?: ICommerce[];
}

export class Marchand implements IMarchand {
    constructor(
        public id?: number,
        public nomMarchand?: string,
        public prenomMarchand?: string,
        public telPrincipale?: string,
        public telSecondaire?: string,
        public emailPrincipale?: string,
        public emailSecondaire?: string,
        public newsletter?: boolean,
        public commerce?: ICommerce[]
    ) {
        this.newsletter = this.newsletter || false;
    }
}
