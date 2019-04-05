import { element, by, ElementFinder } from 'protractor';

export class CouponComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-coupon div table .btn-danger'));
    title = element.all(by.css('jhi-coupon div h2#page-heading span')).first();

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

export class CouponUpdatePage {
    pageTitle = element(by.id('jhi-coupon-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    refCouponInput = element(by.id('field_refCoupon'));
    dateAchatInput = element(by.id('field_dateAchat'));
    dateUtilisationInput = element(by.id('field_dateUtilisation'));
    estCadeauxInput = element(by.id('field_estCadeaux'));
    dealSelect = element(by.id('field_deal'));
    commandeSelect = element(by.id('field_commande'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setRefCouponInput(refCoupon) {
        await this.refCouponInput.sendKeys(refCoupon);
    }

    async getRefCouponInput() {
        return this.refCouponInput.getAttribute('value');
    }

    async setDateAchatInput(dateAchat) {
        await this.dateAchatInput.sendKeys(dateAchat);
    }

    async getDateAchatInput() {
        return this.dateAchatInput.getAttribute('value');
    }

    async setDateUtilisationInput(dateUtilisation) {
        await this.dateUtilisationInput.sendKeys(dateUtilisation);
    }

    async getDateUtilisationInput() {
        return this.dateUtilisationInput.getAttribute('value');
    }

    getEstCadeauxInput() {
        return this.estCadeauxInput;
    }

    async dealSelectLastOption() {
        await this.dealSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async dealSelectOption(option) {
        await this.dealSelect.sendKeys(option);
    }

    getDealSelect(): ElementFinder {
        return this.dealSelect;
    }

    async getDealSelectedOption() {
        return this.dealSelect.element(by.css('option:checked')).getText();
    }

    async commandeSelectLastOption() {
        await this.commandeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async commandeSelectOption(option) {
        await this.commandeSelect.sendKeys(option);
    }

    getCommandeSelect(): ElementFinder {
        return this.commandeSelect;
    }

    async getCommandeSelectedOption() {
        return this.commandeSelect.element(by.css('option:checked')).getText();
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

export class CouponDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-coupon-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-coupon'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
