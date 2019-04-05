import { IDeal } from 'app/shared/model/deal.model';
import { ITypeDeCommerce } from 'app/shared/model/type-de-commerce.model';
import { IMarchand } from 'app/shared/model/marchand.model';

export interface ICommerce {
    id?: number;
    nomCommerce?: string;
    nomRue?: string;
    codePostale?: string;
    siteWeb?: string;
    descCommerce?: string;
    deals?: IDeal[];
    typedecommerce?: ITypeDeCommerce;
    marchand?: IMarchand;
}

export class Commerce implements ICommerce {
    constructor(
        public id?: number,
        public nomCommerce?: string,
        public nomRue?: string,
        public codePostale?: string,
        public siteWeb?: string,
        public descCommerce?: string,
        public deals?: IDeal[],
        public typedecommerce?: ITypeDeCommerce,
        public marchand?: IMarchand
    ) {}
}
