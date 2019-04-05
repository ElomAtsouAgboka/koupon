/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MembreComponentsPage, MembreDeleteDialog, MembreUpdatePage } from './membre.page-object';

const expect = chai.expect;

describe('Membre e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let membreUpdatePage: MembreUpdatePage;
    let membreComponentsPage: MembreComponentsPage;
    let membreDeleteDialog: MembreDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Membres', async () => {
        await navBarPage.goToEntity('membre');
        membreComponentsPage = new MembreComponentsPage();
        await browser.wait(ec.visibilityOf(membreComponentsPage.title), 5000);
        expect(await membreComponentsPage.getTitle()).to.eq('kouponApp.membre.home.title');
    });

    it('should load create Membre page', async () => {
        await membreComponentsPage.clickOnCreateButton();
        membreUpdatePage = new MembreUpdatePage();
        expect(await membreUpdatePage.getPageTitle()).to.eq('kouponApp.membre.home.createOrEditLabel');
        await membreUpdatePage.cancel();
    });

    it('should create and save Membres', async () => {
        const nbButtonsBeforeCreate = await membreComponentsPage.countDeleteButtons();

        await membreComponentsPage.clickOnCreateButton();
        await promise.all([
            membreUpdatePage.setLoginMemberInput('loginMember'),
            membreUpdatePage.setNomMembreInput('nomMembre'),
            membreUpdatePage.setPrenomMembreInput('prenomMembre'),
            membreUpdatePage.setDateDeNaissanceInput('2000-12-31'),
            membreUpdatePage.setEmailMembreInput('emailMembre')
        ]);
        expect(await membreUpdatePage.getLoginMemberInput()).to.eq('loginMember');
        expect(await membreUpdatePage.getNomMembreInput()).to.eq('nomMembre');
        expect(await membreUpdatePage.getPrenomMembreInput()).to.eq('prenomMembre');
        expect(await membreUpdatePage.getDateDeNaissanceInput()).to.eq('2000-12-31');
        expect(await membreUpdatePage.getEmailMembreInput()).to.eq('emailMembre');
        const selectedSouscrireMailPerso = membreUpdatePage.getSouscrireMailPersoInput();
        if (await selectedSouscrireMailPerso.isSelected()) {
            await membreUpdatePage.getSouscrireMailPersoInput().click();
            expect(await membreUpdatePage.getSouscrireMailPersoInput().isSelected()).to.be.false;
        } else {
            await membreUpdatePage.getSouscrireMailPersoInput().click();
            expect(await membreUpdatePage.getSouscrireMailPersoInput().isSelected()).to.be.true;
        }
        await membreUpdatePage.save();
        expect(await membreUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await membreComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Membre', async () => {
        const nbButtonsBeforeDelete = await membreComponentsPage.countDeleteButtons();
        await membreComponentsPage.clickOnLastDeleteButton();

        membreDeleteDialog = new MembreDeleteDialog();
        expect(await membreDeleteDialog.getDialogTitle()).to.eq('kouponApp.membre.delete.question');
        await membreDeleteDialog.clickOnConfirmButton();

        expect(await membreComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
