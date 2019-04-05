import { ICategorie } from 'app/shared/model/categorie.model';
import { IDeal } from 'app/shared/model/deal.model';

export interface ICategorie {
    id?: number;
    nomCategorie?: string;
    categories?: ICategorie[];
    categorieParent?: ICategorie;
    deals?: IDeal[];
}

export class Categorie implements ICategorie {
    constructor(
        public id?: number,
        public nomCategorie?: string,
        public categories?: ICategorie[],
        public categorieParent?: ICategorie,
        public deals?: IDeal[]
    ) {}
}
