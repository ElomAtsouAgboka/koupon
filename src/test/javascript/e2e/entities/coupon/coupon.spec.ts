/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CouponComponentsPage, CouponDeleteDialog, CouponUpdatePage } from './coupon.page-object';

const expect = chai.expect;

describe('Coupon e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let couponUpdatePage: CouponUpdatePage;
    let couponComponentsPage: CouponComponentsPage;
    let couponDeleteDialog: CouponDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Coupons', async () => {
        await navBarPage.goToEntity('coupon');
        couponComponentsPage = new CouponComponentsPage();
        await browser.wait(ec.visibilityOf(couponComponentsPage.title), 5000);
        expect(await couponComponentsPage.getTitle()).to.eq('kouponApp.coupon.home.title');
    });

    it('should load create Coupon page', async () => {
        await couponComponentsPage.clickOnCreateButton();
        couponUpdatePage = new CouponUpdatePage();
        expect(await couponUpdatePage.getPageTitle()).to.eq('kouponApp.coupon.home.createOrEditLabel');
        await couponUpdatePage.cancel();
    });

    it('should create and save Coupons', async () => {
        const nbButtonsBeforeCreate = await couponComponentsPage.countDeleteButtons();

        await couponComponentsPage.clickOnCreateButton();
        await promise.all([
            couponUpdatePage.setRefCouponInput('refCoupon'),
            couponUpdatePage.setDateAchatInput('dateAchat'),
            couponUpdatePage.setDateUtilisationInput('dateUtilisation'),
            couponUpdatePage.dealSelectLastOption(),
            couponUpdatePage.commandeSelectLastOption()
        ]);
        expect(await couponUpdatePage.getRefCouponInput()).to.eq('refCoupon');
        expect(await couponUpdatePage.getDateAchatInput()).to.eq('dateAchat');
        expect(await couponUpdatePage.getDateUtilisationInput()).to.eq('dateUtilisation');
        const selectedEstCadeaux = couponUpdatePage.getEstCadeauxInput();
        if (await selectedEstCadeaux.isSelected()) {
            await couponUpdatePage.getEstCadeauxInput().click();
            expect(await couponUpdatePage.getEstCadeauxInput().isSelected()).to.be.false;
        } else {
            await couponUpdatePage.getEstCadeauxInput().click();
            expect(await couponUpdatePage.getEstCadeauxInput().isSelected()).to.be.true;
        }
        await couponUpdatePage.save();
        expect(await couponUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await couponComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Coupon', async () => {
        const nbButtonsBeforeDelete = await couponComponentsPage.countDeleteButtons();
        await couponComponentsPage.clickOnLastDeleteButton();

        couponDeleteDialog = new CouponDeleteDialog();
        expect(await couponDeleteDialog.getDialogTitle()).to.eq('kouponApp.coupon.delete.question');
        await couponDeleteDialog.clickOnConfirmButton();

        expect(await couponComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
