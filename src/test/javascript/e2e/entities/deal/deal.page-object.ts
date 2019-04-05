import { element, by, ElementFinder } from 'protractor';

export class DealComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-deal div table .btn-danger'));
    title = element.all(by.css('jhi-deal div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class DealUpdatePage {
    pageTitle = element(by.id('jhi-deal-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    refDealInput = element(by.id('field_refDeal'));
    titreDealInput = element(by.id('field_titreDeal'));
    descriptionDealInput = element(by.id('field_descriptionDeal'));
    prixReduitDealInput = element(by.id('field_prixReduitDeal'));
    prixNormalDealInput = element(by.id('field_prixNormalDeal'));
    pcReductionDealInput = element(by.id('field_pcReductionDeal'));
    photoDealUnInput = element(by.id('field_photoDealUn'));
    photoDealDeuxInput = element(by.id('field_photoDealDeux'));
    photoDealTroisInput = element(by.id('field_photoDealTrois'));
    photoDealQuatreInput = element(by.id('field_photoDealQuatre'));
    photoDealCinqInput = element(by.id('field_photoDealCinq'));
    photoDealSixInput = element(by.id('field_photoDealSix'));
    photoDealSpetInput = element(by.id('field_photoDealSpet'));
    photoDealHuitInput = element(by.id('field_photoDealHuit'));
    photoDealNeufInput = element(by.id('field_photoDealNeuf'));
    photoDealDixInput = element(by.id('field_photoDealDix'));
    photoMinDealUnInput = element(by.id('field_photoMinDealUn'));
    photoMinDealDeuxInput = element(by.id('field_photoMinDealDeux'));
    photoMinDealTroisInput = element(by.id('field_photoMinDealTrois'));
    photoMinDealQuatreInput = element(by.id('field_photoMinDealQuatre'));
    photoMinDealCinqInput = element(by.id('field_photoMinDealCinq'));
    photoMinDealSixInput = element(by.id('field_photoMinDealSix'));
    photoMinDealSpetInput = element(by.id('field_photoMinDealSpet'));
    photoMinDealHuitInput = element(by.id('field_photoMinDealHuit'));
    photoMinDealNeufInput = element(by.id('field_photoMinDealNeuf'));
    photoMinDealDixInput = element(by.id('field_photoMinDealDix'));
    descPointFortDealInput = element(by.id('field_descPointFortDeal'));
    detailsOffreDealInput = element(by.id('field_detailsOffreDeal'));
    conditionsDealInput = element(by.id('field_conditionsDeal'));
    estLimiteInput = element(by.id('field_estLimite'));
    estEpuiseInput = element(by.id('field_estEpuise'));
    dateCreationDealInput = element(by.id('field_dateCreationDeal'));
    dateClotureDealInput = element(by.id('field_dateClotureDeal'));
    menuSelect = element(by.id('field_menu'));
    categorieSelect = element(by.id('field_categorie'));
    commerceSelect = element(by.id('field_commerce'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setRefDealInput(refDeal) {
        await this.refDealInput.sendKeys(refDeal);
    }

    async getRefDealInput() {
        return this.refDealInput.getAttribute('value');
    }

    async setTitreDealInput(titreDeal) {
        await this.titreDealInput.sendKeys(titreDeal);
    }

    async getTitreDealInput() {
        return this.titreDealInput.getAttribute('value');
    }

    async setDescriptionDealInput(descriptionDeal) {
        await this.descriptionDealInput.sendKeys(descriptionDeal);
    }

    async getDescriptionDealInput() {
        return this.descriptionDealInput.getAttribute('value');
    }

    async setPrixReduitDealInput(prixReduitDeal) {
        await this.prixReduitDealInput.sendKeys(prixReduitDeal);
    }

    async getPrixReduitDealInput() {
        return this.prixReduitDealInput.getAttribute('value');
    }

    async setPrixNormalDealInput(prixNormalDeal) {
        await this.prixNormalDealInput.sendKeys(prixNormalDeal);
    }

    async getPrixNormalDealInput() {
        return this.prixNormalDealInput.getAttribute('value');
    }

    async setPcReductionDealInput(pcReductionDeal) {
        await this.pcReductionDealInput.sendKeys(pcReductionDeal);
    }

    async getPcReductionDealInput() {
        return this.pcReductionDealInput.getAttribute('value');
    }

    async setPhotoDealUnInput(photoDealUn) {
        await this.photoDealUnInput.sendKeys(photoDealUn);
    }

    async getPhotoDealUnInput() {
        return this.photoDealUnInput.getAttribute('value');
    }

    async setPhotoDealDeuxInput(photoDealDeux) {
        await this.photoDealDeuxInput.sendKeys(photoDealDeux);
    }

    async getPhotoDealDeuxInput() {
        return this.photoDealDeuxInput.getAttribute('value');
    }

    async setPhotoDealTroisInput(photoDealTrois) {
        await this.photoDealTroisInput.sendKeys(photoDealTrois);
    }

    async getPhotoDealTroisInput() {
        return this.photoDealTroisInput.getAttribute('value');
    }

    async setPhotoDealQuatreInput(photoDealQuatre) {
        await this.photoDealQuatreInput.sendKeys(photoDealQuatre);
    }

    async getPhotoDealQuatreInput() {
        return this.photoDealQuatreInput.getAttribute('value');
    }

    async setPhotoDealCinqInput(photoDealCinq) {
        await this.photoDealCinqInput.sendKeys(photoDealCinq);
    }

    async getPhotoDealCinqInput() {
        return this.photoDealCinqInput.getAttribute('value');
    }

    async setPhotoDealSixInput(photoDealSix) {
        await this.photoDealSixInput.sendKeys(photoDealSix);
    }

    async getPhotoDealSixInput() {
        return this.photoDealSixInput.getAttribute('value');
    }

    async setPhotoDealSpetInput(photoDealSpet) {
        await this.photoDealSpetInput.sendKeys(photoDealSpet);
    }

    async getPhotoDealSpetInput() {
        return this.photoDealSpetInput.getAttribute('value');
    }

    async setPhotoDealHuitInput(photoDealHuit) {
        await this.photoDealHuitInput.sendKeys(photoDealHuit);
    }

    async getPhotoDealHuitInput() {
        return this.photoDealHuitInput.getAttribute('value');
    }

    async setPhotoDealNeufInput(photoDealNeuf) {
        await this.photoDealNeufInput.sendKeys(photoDealNeuf);
    }

    async getPhotoDealNeufInput() {
        return this.photoDealNeufInput.getAttribute('value');
    }

    async setPhotoDealDixInput(photoDealDix) {
        await this.photoDealDixInput.sendKeys(photoDealDix);
    }

    async getPhotoDealDixInput() {
        return this.photoDealDixInput.getAttribute('value');
    }

    async setPhotoMinDealUnInput(photoMinDealUn) {
        await this.photoMinDealUnInput.sendKeys(photoMinDealUn);
    }

    async getPhotoMinDealUnInput() {
        return this.photoMinDealUnInput.getAttribute('value');
    }

    async setPhotoMinDealDeuxInput(photoMinDealDeux) {
        await this.photoMinDealDeuxInput.sendKeys(photoMinDealDeux);
    }

    async getPhotoMinDealDeuxInput() {
        return this.photoMinDealDeuxInput.getAttribute('value');
    }

    async setPhotoMinDealTroisInput(photoMinDealTrois) {
        await this.photoMinDealTroisInput.sendKeys(photoMinDealTrois);
    }

    async getPhotoMinDealTroisInput() {
        return this.photoMinDealTroisInput.getAttribute('value');
    }

    async setPhotoMinDealQuatreInput(photoMinDealQuatre) {
        await this.photoMinDealQuatreInput.sendKeys(photoMinDealQuatre);
    }

    async getPhotoMinDealQuatreInput() {
        return this.photoMinDealQuatreInput.getAttribute('value');
    }

    async setPhotoMinDealCinqInput(photoMinDealCinq) {
        await this.photoMinDealCinqInput.sendKeys(photoMinDealCinq);
    }

    async getPhotoMinDealCinqInput() {
        return this.photoMinDealCinqInput.getAttribute('value');
    }

    async setPhotoMinDealSixInput(photoMinDealSix) {
        await this.photoMinDealSixInput.sendKeys(photoMinDealSix);
    }

    async getPhotoMinDealSixInput() {
        return this.photoMinDealSixInput.getAttribute('value');
    }

    async setPhotoMinDealSpetInput(photoMinDealSpet) {
        await this.photoMinDealSpetInput.sendKeys(photoMinDealSpet);
    }

    async getPhotoMinDealSpetInput() {
        return this.photoMinDealSpetInput.getAttribute('value');
    }

    async setPhotoMinDealHuitInput(photoMinDealHuit) {
        await this.photoMinDealHuitInput.sendKeys(photoMinDealHuit);
    }

    async getPhotoMinDealHuitInput() {
        return this.photoMinDealHuitInput.getAttribute('value');
    }

    async setPhotoMinDealNeufInput(photoMinDealNeuf) {
        await this.photoMinDealNeufInput.sendKeys(photoMinDealNeuf);
    }

    async getPhotoMinDealNeufInput() {
        return this.photoMinDealNeufInput.getAttribute('value');
    }

    async setPhotoMinDealDixInput(photoMinDealDix) {
        await this.photoMinDealDixInput.sendKeys(photoMinDealDix);
    }

    async getPhotoMinDealDixInput() {
        return this.photoMinDealDixInput.getAttribute('value');
    }

    async setDescPointFortDealInput(descPointFortDeal) {
        await this.descPointFortDealInput.sendKeys(descPointFortDeal);
    }

    async getDescPointFortDealInput() {
        return this.descPointFortDealInput.getAttribute('value');
    }

    async setDetailsOffreDealInput(detailsOffreDeal) {
        await this.detailsOffreDealInput.sendKeys(detailsOffreDeal);
    }

    async getDetailsOffreDealInput() {
        return this.detailsOffreDealInput.getAttribute('value');
    }

    async setConditionsDealInput(conditionsDeal) {
        await this.conditionsDealInput.sendKeys(conditionsDeal);
    }

    async getConditionsDealInput() {
        return this.conditionsDealInput.getAttribute('value');
    }

    getEstLimiteInput() {
        return this.estLimiteInput;
    }
    getEstEpuiseInput() {
        return this.estEpuiseInput;
    }
    async setDateCreationDealInput(dateCreationDeal) {
        await this.dateCreationDealInput.sendKeys(dateCreationDeal);
    }

    async getDateCreationDealInput() {
        return this.dateCreationDealInput.getAttribute('value');
    }

    async setDateClotureDealInput(dateClotureDeal) {
        await this.dateClotureDealInput.sendKeys(dateClotureDeal);
    }

    async getDateClotureDealInput() {
        return this.dateClotureDealInput.getAttribute('value');
    }

    async menuSelectLastOption() {
        await this.menuSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async menuSelectOption(option) {
        await this.menuSelect.sendKeys(option);
    }

    getMenuSelect(): ElementFinder {
        return this.menuSelect;
    }

    async getMenuSelectedOption() {
        return this.menuSelect.element(by.css('option:checked')).getText();
    }

    async categorieSelectLastOption() {
        await this.categorieSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async categorieSelectOption(option) {
        await this.categorieSelect.sendKeys(option);
    }

    getCategorieSelect(): ElementFinder {
        return this.categorieSelect;
    }

    async getCategorieSelectedOption() {
        return this.categorieSelect.element(by.css('option:checked')).getText();
    }

    async commerceSelectLastOption() {
        await this.commerceSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async commerceSelectOption(option) {
        await this.commerceSelect.sendKeys(option);
    }

    getCommerceSelect(): ElementFinder {
        return this.commerceSelect;
    }

    async getCommerceSelectedOption() {
        return this.commerceSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class DealDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-deal-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-deal'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
