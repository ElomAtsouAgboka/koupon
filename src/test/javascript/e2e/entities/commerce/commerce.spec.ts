/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CommerceComponentsPage, CommerceDeleteDialog, CommerceUpdatePage } from './commerce.page-object';

const expect = chai.expect;

describe('Commerce e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let commerceUpdatePage: CommerceUpdatePage;
    let commerceComponentsPage: CommerceComponentsPage;
    let commerceDeleteDialog: CommerceDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Commerce', async () => {
        await navBarPage.goToEntity('commerce');
        commerceComponentsPage = new CommerceComponentsPage();
        await browser.wait(ec.visibilityOf(commerceComponentsPage.title), 5000);
        expect(await commerceComponentsPage.getTitle()).to.eq('kouponApp.commerce.home.title');
    });

    it('should load create Commerce page', async () => {
        await commerceComponentsPage.clickOnCreateButton();
        commerceUpdatePage = new CommerceUpdatePage();
        expect(await commerceUpdatePage.getPageTitle()).to.eq('kouponApp.commerce.home.createOrEditLabel');
        await commerceUpdatePage.cancel();
    });

    it('should create and save Commerce', async () => {
        const nbButtonsBeforeCreate = await commerceComponentsPage.countDeleteButtons();

        await commerceComponentsPage.clickOnCreateButton();
        await promise.all([
            commerceUpdatePage.setNomCommerceInput('nomCommerce'),
            commerceUpdatePage.setNomRueInput('nomRue'),
            commerceUpdatePage.setCodePostaleInput('codePostale'),
            commerceUpdatePage.setSiteWebInput('siteWeb'),
            commerceUpdatePage.setDescCommerceInput('descCommerce'),
            commerceUpdatePage.typedecommerceSelectLastOption(),
            commerceUpdatePage.marchandSelectLastOption()
        ]);
        expect(await commerceUpdatePage.getNomCommerceInput()).to.eq('nomCommerce');
        expect(await commerceUpdatePage.getNomRueInput()).to.eq('nomRue');
        expect(await commerceUpdatePage.getCodePostaleInput()).to.eq('codePostale');
        expect(await commerceUpdatePage.getSiteWebInput()).to.eq('siteWeb');
        expect(await commerceUpdatePage.getDescCommerceInput()).to.eq('descCommerce');
        await commerceUpdatePage.save();
        expect(await commerceUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await commerceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Commerce', async () => {
        const nbButtonsBeforeDelete = await commerceComponentsPage.countDeleteButtons();
        await commerceComponentsPage.clickOnLastDeleteButton();

        commerceDeleteDialog = new CommerceDeleteDialog();
        expect(await commerceDeleteDialog.getDialogTitle()).to.eq('kouponApp.commerce.delete.question');
        await commerceDeleteDialog.clickOnConfirmButton();

        expect(await commerceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
