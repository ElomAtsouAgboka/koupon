/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TypeDeCommerceComponentsPage, TypeDeCommerceDeleteDialog, TypeDeCommerceUpdatePage } from './type-de-commerce.page-object';

const expect = chai.expect;

describe('TypeDeCommerce e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let typeDeCommerceUpdatePage: TypeDeCommerceUpdatePage;
    let typeDeCommerceComponentsPage: TypeDeCommerceComponentsPage;
    let typeDeCommerceDeleteDialog: TypeDeCommerceDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load TypeDeCommerces', async () => {
        await navBarPage.goToEntity('type-de-commerce');
        typeDeCommerceComponentsPage = new TypeDeCommerceComponentsPage();
        await browser.wait(ec.visibilityOf(typeDeCommerceComponentsPage.title), 5000);
        expect(await typeDeCommerceComponentsPage.getTitle()).to.eq('kouponApp.typeDeCommerce.home.title');
    });

    it('should load create TypeDeCommerce page', async () => {
        await typeDeCommerceComponentsPage.clickOnCreateButton();
        typeDeCommerceUpdatePage = new TypeDeCommerceUpdatePage();
        expect(await typeDeCommerceUpdatePage.getPageTitle()).to.eq('kouponApp.typeDeCommerce.home.createOrEditLabel');
        await typeDeCommerceUpdatePage.cancel();
    });

    it('should create and save TypeDeCommerces', async () => {
        const nbButtonsBeforeCreate = await typeDeCommerceComponentsPage.countDeleteButtons();

        await typeDeCommerceComponentsPage.clickOnCreateButton();
        await promise.all([typeDeCommerceUpdatePage.setNomTypeDeCommerceInput('nomTypeDeCommerce')]);
        expect(await typeDeCommerceUpdatePage.getNomTypeDeCommerceInput()).to.eq('nomTypeDeCommerce');
        await typeDeCommerceUpdatePage.save();
        expect(await typeDeCommerceUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await typeDeCommerceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last TypeDeCommerce', async () => {
        const nbButtonsBeforeDelete = await typeDeCommerceComponentsPage.countDeleteButtons();
        await typeDeCommerceComponentsPage.clickOnLastDeleteButton();

        typeDeCommerceDeleteDialog = new TypeDeCommerceDeleteDialog();
        expect(await typeDeCommerceDeleteDialog.getDialogTitle()).to.eq('kouponApp.typeDeCommerce.delete.question');
        await typeDeCommerceDeleteDialog.clickOnConfirmButton();

        expect(await typeDeCommerceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
