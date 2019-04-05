/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CommandeComponentsPage, CommandeDeleteDialog, CommandeUpdatePage } from './commande.page-object';

const expect = chai.expect;

describe('Commande e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let commandeUpdatePage: CommandeUpdatePage;
    let commandeComponentsPage: CommandeComponentsPage;
    let commandeDeleteDialog: CommandeDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Commandes', async () => {
        await navBarPage.goToEntity('commande');
        commandeComponentsPage = new CommandeComponentsPage();
        await browser.wait(ec.visibilityOf(commandeComponentsPage.title), 5000);
        expect(await commandeComponentsPage.getTitle()).to.eq('kouponApp.commande.home.title');
    });

    it('should load create Commande page', async () => {
        await commandeComponentsPage.clickOnCreateButton();
        commandeUpdatePage = new CommandeUpdatePage();
        expect(await commandeUpdatePage.getPageTitle()).to.eq('kouponApp.commande.home.createOrEditLabel');
        await commandeUpdatePage.cancel();
    });

    it('should create and save Commandes', async () => {
        const nbButtonsBeforeCreate = await commandeComponentsPage.countDeleteButtons();

        await commandeComponentsPage.clickOnCreateButton();
        await promise.all([
            commandeUpdatePage.setRefCommandeInput('refCommande'),
            commandeUpdatePage.setDateCommandeInput('2000-12-31'),
            commandeUpdatePage.membreSelectLastOption()
        ]);
        expect(await commandeUpdatePage.getRefCommandeInput()).to.eq('refCommande');
        expect(await commandeUpdatePage.getDateCommandeInput()).to.eq('2000-12-31');
        await commandeUpdatePage.save();
        expect(await commandeUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await commandeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Commande', async () => {
        const nbButtonsBeforeDelete = await commandeComponentsPage.countDeleteButtons();
        await commandeComponentsPage.clickOnLastDeleteButton();

        commandeDeleteDialog = new CommandeDeleteDialog();
        expect(await commandeDeleteDialog.getDialogTitle()).to.eq('kouponApp.commande.delete.question');
        await commandeDeleteDialog.clickOnConfirmButton();

        expect(await commandeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
