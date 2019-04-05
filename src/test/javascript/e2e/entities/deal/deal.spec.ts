/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DealComponentsPage, DealDeleteDialog, DealUpdatePage } from './deal.page-object';

const expect = chai.expect;

describe('Deal e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let dealUpdatePage: DealUpdatePage;
    let dealComponentsPage: DealComponentsPage;
    let dealDeleteDialog: DealDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Deals', async () => {
        await navBarPage.goToEntity('deal');
        dealComponentsPage = new DealComponentsPage();
        await browser.wait(ec.visibilityOf(dealComponentsPage.title), 5000);
        expect(await dealComponentsPage.getTitle()).to.eq('kouponApp.deal.home.title');
    });

    it('should load create Deal page', async () => {
        await dealComponentsPage.clickOnCreateButton();
        dealUpdatePage = new DealUpdatePage();
        expect(await dealUpdatePage.getPageTitle()).to.eq('kouponApp.deal.home.createOrEditLabel');
        await dealUpdatePage.cancel();
    });

    it('should create and save Deals', async () => {
        const nbButtonsBeforeCreate = await dealComponentsPage.countDeleteButtons();

        await dealComponentsPage.clickOnCreateButton();
        await promise.all([
            dealUpdatePage.setRefDealInput('refDeal'),
            dealUpdatePage.setTitreDealInput('titreDeal'),
            dealUpdatePage.setDescriptionDealInput('descriptionDeal'),
            dealUpdatePage.setPrixReduitDealInput('prixReduitDeal'),
            dealUpdatePage.setPrixNormalDealInput('5'),
            dealUpdatePage.setPcReductionDealInput('5'),
            dealUpdatePage.setPhotoDealUnInput('photoDealUn'),
            dealUpdatePage.setPhotoDealDeuxInput('photoDealDeux'),
            dealUpdatePage.setPhotoDealTroisInput('photoDealTrois'),
            dealUpdatePage.setPhotoDealQuatreInput('photoDealQuatre'),
            dealUpdatePage.setPhotoDealCinqInput('photoDealCinq'),
            dealUpdatePage.setPhotoDealSixInput('photoDealSix'),
            dealUpdatePage.setPhotoDealSpetInput('photoDealSpet'),
            dealUpdatePage.setPhotoDealHuitInput('photoDealHuit'),
            dealUpdatePage.setPhotoDealNeufInput('photoDealNeuf'),
            dealUpdatePage.setPhotoDealDixInput('photoDealDix'),
            dealUpdatePage.setPhotoMinDealUnInput('photoMinDealUn'),
            dealUpdatePage.setPhotoMinDealDeuxInput('photoMinDealDeux'),
            dealUpdatePage.setPhotoMinDealTroisInput('photoMinDealTrois'),
            dealUpdatePage.setPhotoMinDealQuatreInput('photoMinDealQuatre'),
            dealUpdatePage.setPhotoMinDealCinqInput('photoMinDealCinq'),
            dealUpdatePage.setPhotoMinDealSixInput('photoMinDealSix'),
            dealUpdatePage.setPhotoMinDealSpetInput('photoMinDealSpet'),
            dealUpdatePage.setPhotoMinDealHuitInput('photoMinDealHuit'),
            dealUpdatePage.setPhotoMinDealNeufInput('photoMinDealNeuf'),
            dealUpdatePage.setPhotoMinDealDixInput('photoMinDealDix'),
            dealUpdatePage.setDescPointFortDealInput('descPointFortDeal'),
            dealUpdatePage.setDetailsOffreDealInput('detailsOffreDeal'),
            dealUpdatePage.setConditionsDealInput('conditionsDeal'),
            dealUpdatePage.setDateCreationDealInput('2000-12-31'),
            dealUpdatePage.setDateClotureDealInput('2000-12-31'),
            // dealUpdatePage.menuSelectLastOption(),
            // dealUpdatePage.categorieSelectLastOption(),
            dealUpdatePage.commerceSelectLastOption()
        ]);
        expect(await dealUpdatePage.getRefDealInput()).to.eq('refDeal');
        expect(await dealUpdatePage.getTitreDealInput()).to.eq('titreDeal');
        expect(await dealUpdatePage.getDescriptionDealInput()).to.eq('descriptionDeal');
        expect(await dealUpdatePage.getPrixReduitDealInput()).to.eq('prixReduitDeal');
        expect(await dealUpdatePage.getPrixNormalDealInput()).to.eq('5');
        expect(await dealUpdatePage.getPcReductionDealInput()).to.eq('5');
        expect(await dealUpdatePage.getPhotoDealUnInput()).to.eq('photoDealUn');
        expect(await dealUpdatePage.getPhotoDealDeuxInput()).to.eq('photoDealDeux');
        expect(await dealUpdatePage.getPhotoDealTroisInput()).to.eq('photoDealTrois');
        expect(await dealUpdatePage.getPhotoDealQuatreInput()).to.eq('photoDealQuatre');
        expect(await dealUpdatePage.getPhotoDealCinqInput()).to.eq('photoDealCinq');
        expect(await dealUpdatePage.getPhotoDealSixInput()).to.eq('photoDealSix');
        expect(await dealUpdatePage.getPhotoDealSpetInput()).to.eq('photoDealSpet');
        expect(await dealUpdatePage.getPhotoDealHuitInput()).to.eq('photoDealHuit');
        expect(await dealUpdatePage.getPhotoDealNeufInput()).to.eq('photoDealNeuf');
        expect(await dealUpdatePage.getPhotoDealDixInput()).to.eq('photoDealDix');
        expect(await dealUpdatePage.getPhotoMinDealUnInput()).to.eq('photoMinDealUn');
        expect(await dealUpdatePage.getPhotoMinDealDeuxInput()).to.eq('photoMinDealDeux');
        expect(await dealUpdatePage.getPhotoMinDealTroisInput()).to.eq('photoMinDealTrois');
        expect(await dealUpdatePage.getPhotoMinDealQuatreInput()).to.eq('photoMinDealQuatre');
        expect(await dealUpdatePage.getPhotoMinDealCinqInput()).to.eq('photoMinDealCinq');
        expect(await dealUpdatePage.getPhotoMinDealSixInput()).to.eq('photoMinDealSix');
        expect(await dealUpdatePage.getPhotoMinDealSpetInput()).to.eq('photoMinDealSpet');
        expect(await dealUpdatePage.getPhotoMinDealHuitInput()).to.eq('photoMinDealHuit');
        expect(await dealUpdatePage.getPhotoMinDealNeufInput()).to.eq('photoMinDealNeuf');
        expect(await dealUpdatePage.getPhotoMinDealDixInput()).to.eq('photoMinDealDix');
        expect(await dealUpdatePage.getDescPointFortDealInput()).to.eq('descPointFortDeal');
        expect(await dealUpdatePage.getDetailsOffreDealInput()).to.eq('detailsOffreDeal');
        expect(await dealUpdatePage.getConditionsDealInput()).to.eq('conditionsDeal');
        const selectedEstLimite = dealUpdatePage.getEstLimiteInput();
        if (await selectedEstLimite.isSelected()) {
            await dealUpdatePage.getEstLimiteInput().click();
            expect(await dealUpdatePage.getEstLimiteInput().isSelected()).to.be.false;
        } else {
            await dealUpdatePage.getEstLimiteInput().click();
            expect(await dealUpdatePage.getEstLimiteInput().isSelected()).to.be.true;
        }
        const selectedEstEpuise = dealUpdatePage.getEstEpuiseInput();
        if (await selectedEstEpuise.isSelected()) {
            await dealUpdatePage.getEstEpuiseInput().click();
            expect(await dealUpdatePage.getEstEpuiseInput().isSelected()).to.be.false;
        } else {
            await dealUpdatePage.getEstEpuiseInput().click();
            expect(await dealUpdatePage.getEstEpuiseInput().isSelected()).to.be.true;
        }
        expect(await dealUpdatePage.getDateCreationDealInput()).to.eq('2000-12-31');
        expect(await dealUpdatePage.getDateClotureDealInput()).to.eq('2000-12-31');
        await dealUpdatePage.save();
        expect(await dealUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await dealComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Deal', async () => {
        const nbButtonsBeforeDelete = await dealComponentsPage.countDeleteButtons();
        await dealComponentsPage.clickOnLastDeleteButton();

        dealDeleteDialog = new DealDeleteDialog();
        expect(await dealDeleteDialog.getDialogTitle()).to.eq('kouponApp.deal.delete.question');
        await dealDeleteDialog.clickOnConfirmButton();

        expect(await dealComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
