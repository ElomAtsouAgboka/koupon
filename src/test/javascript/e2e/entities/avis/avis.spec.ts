/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AvisComponentsPage, AvisDeleteDialog, AvisUpdatePage } from './avis.page-object';

const expect = chai.expect;

describe('Avis e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let avisUpdatePage: AvisUpdatePage;
    let avisComponentsPage: AvisComponentsPage;
    let avisDeleteDialog: AvisDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Avis', async () => {
        await navBarPage.goToEntity('avis');
        avisComponentsPage = new AvisComponentsPage();
        await browser.wait(ec.visibilityOf(avisComponentsPage.title), 5000);
        expect(await avisComponentsPage.getTitle()).to.eq('kouponApp.avis.home.title');
    });

    it('should load create Avis page', async () => {
        await avisComponentsPage.clickOnCreateButton();
        avisUpdatePage = new AvisUpdatePage();
        expect(await avisUpdatePage.getPageTitle()).to.eq('kouponApp.avis.home.createOrEditLabel');
        await avisUpdatePage.cancel();
    });

    it('should create and save Avis', async () => {
        const nbButtonsBeforeCreate = await avisComponentsPage.countDeleteButtons();

        await avisComponentsPage.clickOnCreateButton();
        await promise.all([
            avisUpdatePage.setTextAvisInput('textAvis'),
            avisUpdatePage.setDerniereUtilisationCouponInput('5'),
            avisUpdatePage.dealSelectLastOption()
        ]);
        expect(await avisUpdatePage.getTextAvisInput()).to.eq('textAvis');
        expect(await avisUpdatePage.getDerniereUtilisationCouponInput()).to.eq('5');
        await avisUpdatePage.save();
        expect(await avisUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await avisComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Avis', async () => {
        const nbButtonsBeforeDelete = await avisComponentsPage.countDeleteButtons();
        await avisComponentsPage.clickOnLastDeleteButton();

        avisDeleteDialog = new AvisDeleteDialog();
        expect(await avisDeleteDialog.getDialogTitle()).to.eq('kouponApp.avis.delete.question');
        await avisDeleteDialog.clickOnConfirmButton();

        expect(await avisComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
