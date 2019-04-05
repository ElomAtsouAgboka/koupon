import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'pays',
                loadChildren: './pays/pays.module#KouponPaysModule'
            },
            {
                path: 'ville',
                loadChildren: './ville/ville.module#KouponVilleModule'
            },
            {
                path: 'quartier',
                loadChildren: './quartier/quartier.module#KouponQuartierModule'
            },
            {
                path: 'categorie',
                loadChildren: './categorie/categorie.module#KouponCategorieModule'
            },
            {
                path: 'menu',
                loadChildren: './menu/menu.module#KouponMenuModule'
            },
            {
                path: 'type-de-commerce',
                loadChildren: './type-de-commerce/type-de-commerce.module#KouponTypeDeCommerceModule'
            },
            {
                path: 'commerce',
                loadChildren: './commerce/commerce.module#KouponCommerceModule'
            },
            {
                path: 'marchand',
                loadChildren: './marchand/marchand.module#KouponMarchandModule'
            },
            {
                path: 'deal',
                loadChildren: './deal/deal.module#KouponDealModule'
            },
            {
                path: 'option-deal',
                loadChildren: './option-deal/option-deal.module#KouponOptionDealModule'
            },
            {
                path: 'coupon',
                loadChildren: './coupon/coupon.module#KouponCouponModule'
            },
            {
                path: 'avis',
                loadChildren: './avis/avis.module#KouponAvisModule'
            },
            {
                path: 'membre',
                loadChildren: './membre/membre.module#KouponMembreModule'
            },
            {
                path: 'commande',
                loadChildren: './commande/commande.module#KouponCommandeModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KouponEntityModule {}
