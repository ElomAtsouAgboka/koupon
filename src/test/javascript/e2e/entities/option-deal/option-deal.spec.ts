/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OptionDealComponentsPage, OptionDealDeleteDialog, OptionDealUpdatePage } from './option-deal.page-object';

const expect = chai.expect;

describe('OptionDeal e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let optionDealUpdatePage: OptionDealUpdatePage;
    let optionDealComponentsPage: OptionDealComponentsPage;
    let optionDealDeleteDialog: OptionDealDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load OptionDeals', async () => {
        await navBarPage.goToEntity('option-deal');
        optionDealComponentsPage = new OptionDealComponentsPage();
        await browser.wait(ec.visibilityOf(optionDealComponentsPage.title), 5000);
        expect(await optionDealComponentsPage.getTitle()).to.eq('kouponApp.optionDeal.home.title');
    });

    it('should load create OptionDeal page', async () => {
        await optionDealComponentsPage.clickOnCreateButton();
        optionDealUpdatePage = new OptionDealUpdatePage();
        expect(await optionDealUpdatePage.getPageTitle()).to.eq('kouponApp.optionDeal.home.createOrEditLabel');
        await optionDealUpdatePage.cancel();
    });

    it('should create and save OptionDeals', async () => {
        const nbButtonsBeforeCreate = await optionDealComponentsPage.countDeleteButtons();

        await optionDealComponentsPage.clickOnCreateButton();
        await promise.all([
            optionDealUpdatePage.setLibOptionDealInput('libOptionDeal'),
            optionDealUpdatePage.setPrixNormalOptionDealInput('5'),
            optionDealUpdatePage.setPrixReductionOptionBonPlanInput('5'),
            optionDealUpdatePage.setPcReductionOptionBonPlanInput('5'),
            optionDealUpdatePage.dealSelectLastOption()
        ]);
        expect(await optionDealUpdatePage.getLibOptionDealInput()).to.eq('libOptionDeal');
        expect(await optionDealUpdatePage.getPrixNormalOptionDealInput()).to.eq('5');
        expect(await optionDealUpdatePage.getPrixReductionOptionBonPlanInput()).to.eq('5');
        expect(await optionDealUpdatePage.getPcReductionOptionBonPlanInput()).to.eq('5');
        await optionDealUpdatePage.save();
        expect(await optionDealUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await optionDealComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last OptionDeal', async () => {
        const nbButtonsBeforeDelete = await optionDealComponentsPage.countDeleteButtons();
        await optionDealComponentsPage.clickOnLastDeleteButton();

        optionDealDeleteDialog = new OptionDealDeleteDialog();
        expect(await optionDealDeleteDialog.getDialogTitle()).to.eq('kouponApp.optionDeal.delete.question');
        await optionDealDeleteDialog.clickOnConfirmButton();

        expect(await optionDealComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
