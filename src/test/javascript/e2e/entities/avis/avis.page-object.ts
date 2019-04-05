import { element, by, ElementFinder } from 'protractor';

export class AvisComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-avis div table .btn-danger'));
    title = element.all(by.css('jhi-avis div h2#page-heading span')).first();

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

export class AvisUpdatePage {
    pageTitle = element(by.id('jhi-avis-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    textAvisInput = element(by.id('field_textAvis'));
    derniereUtilisationCouponInput = element(by.id('field_derniereUtilisationCoupon'));
    dealSelect = element(by.id('field_deal'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setTextAvisInput(textAvis) {
        await this.textAvisInput.sendKeys(textAvis);
    }

    async getTextAvisInput() {
        return this.textAvisInput.getAttribute('value');
    }

    async setDerniereUtilisationCouponInput(derniereUtilisationCoupon) {
        await this.derniereUtilisationCouponInput.sendKeys(derniereUtilisationCoupon);
    }

    async getDerniereUtilisationCouponInput() {
        return this.derniereUtilisationCouponInput.getAttribute('value');
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

export class AvisDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-avis-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-avis'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
