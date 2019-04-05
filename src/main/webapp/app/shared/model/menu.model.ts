import { IMenu } from 'app/shared/model/menu.model';
import { IDeal } from 'app/shared/model/deal.model';

export interface IMenu {
    id?: number;
    menuItem?: string;
    menuItemImg?: string;
    menus?: IMenu[];
    menuParent?: IMenu;
    deals?: IDeal[];
}

export class Menu implements IMenu {
    constructor(
        public id?: number,
        public menuItem?: string,
        public menuItemImg?: string,
        public menus?: IMenu[],
        public menuParent?: IMenu,
        public deals?: IDeal[]
    ) {}
}
