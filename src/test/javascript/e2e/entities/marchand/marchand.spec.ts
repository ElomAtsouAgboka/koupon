/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MarchandComponentsPage, MarchandDeleteDialog, MarchandUpdatePage } from './marchand.page-object';

const expect = chai.expect;

describe('Marchand e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let marchandUpdatePage: MarchandUpdatePage;
    let marchandComponentsPage: MarchandComponentsPage;
    let marchandDeleteDialog: MarchandDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Marchands', async () => {
        await navBarPage.goToEntity('marchand');
        marchandComponentsPage = new MarchandComponentsPage();
        await browser.wait(ec.visibilityOf(marchandComponentsPage.title), 5000);
        expect(await marchandComponentsPage.getTitle()).to.eq('kouponApp.marchand.home.title');
    });

    it('should load create Marchand page', async () => {
        await marchandComponentsPage.clickOnCreateButton();
        marchandUpdatePage = new MarchandUpdatePage();
        expect(await marchandUpdatePage.getPageTitle()).to.eq('kouponApp.marchand.home.createOrEditLabel');
        await marchandUpdatePage.cancel();
    });

    it('should create and save Marchands', async () => {
        const nbButtonsBeforeCreate = await marchandComponentsPage.countDeleteButtons();

        await marchandComponentsPage.clickOnCreateButton();
        await promise.all([
            marchandUpdatePage.setNomMarchandInput('nomMarchand'),
            marchandUpdatePage.setPrenomMarchandInput('prenomMarchand'),
            marchandUpdatePage.setTelPrincipaleInput('telPrincipale'),
            marchandUpdatePage.setTelSecondaireInput('telSecondaire'),
            marchandUpdatePage.setEmailPrincipaleInput('emailPrincipale'),
            marchandUpdatePage.setEmailSecondaireInput('emailSecondaire')
        ]);
        expect(await marchandUpdatePage.getNomMarchandInput()).to.eq('nomMarchand');
        expect(await marchandUpdatePage.getPrenomMarchandInput()).to.eq('prenomMarchand');
        expect(await marchandUpdatePage.getTelPrincipaleInput()).to.eq('telPrincipale');
        expect(await marchandUpdatePage.getTelSecondaireInput()).to.eq('telSecondaire');
        expect(await marchandUpdatePage.getEmailPrincipaleInput()).to.eq('emailPrincipale');
        expect(await marchandUpdatePage.getEmailSecondaireInput()).to.eq('emailSecondaire');
        const selectedNewsletter = marchandUpdatePage.getNewsletterInput();
        if (await selectedNewsletter.isSelected()) {
            await marchandUpdatePage.getNewsletterInput().click();
            expect(await marchandUpdatePage.getNewsletterInput().isSelected()).to.be.false;
        } else {
            await marchandUpdatePage.getNewsletterInput().click();
            expect(await marchandUpdatePage.getNewsletterInput().isSelected()).to.be.true;
        }
        await marchandUpdatePage.save();
        expect(await marchandUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await marchandComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Marchand', async () => {
        const nbButtonsBeforeDelete = await marchandComponentsPage.countDeleteButtons();
        await marchandComponentsPage.clickOnLastDeleteButton();

        marchandDeleteDialog = new MarchandDeleteDialog();
        expect(await marchandDeleteDialog.getDialogTitle()).to.eq('kouponApp.marchand.delete.question');
        await marchandDeleteDialog.clickOnConfirmButton();

        expect(await marchandComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
