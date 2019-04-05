import { Moment } from 'moment';
import { ICommande } from 'app/shared/model/commande.model';

export interface IMembre {
    id?: number;
    loginMember?: string;
    nomMembre?: string;
    prenomMembre?: string;
    dateDeNaissance?: Moment;
    emailMembre?: string;
    souscrireMailPerso?: boolean;
    commandes?: ICommande[];
}

export class Membre implements IMembre {
    constructor(
        public id?: number,
        public loginMember?: string,
        public nomMembre?: string,
        public prenomMembre?: string,
        public dateDeNaissance?: Moment,
        public emailMembre?: string,
        public souscrireMailPerso?: boolean,
        public commandes?: ICommande[]
    ) {
        this.souscrireMailPerso = this.souscrireMailPerso || false;
    }
}
