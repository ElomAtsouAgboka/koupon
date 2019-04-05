import { ICommerce } from 'app/shared/model/commerce.model';

export interface ITypeDeCommerce {
    id?: number;
    nomTypeDeCommerce?: string;
    commerce?: ICommerce[];
}

export class TypeDeCommerce implements ITypeDeCommerce {
    constructor(public id?: number, public nomTypeDeCommerce?: string, public commerce?: ICommerce[]) {}
}
