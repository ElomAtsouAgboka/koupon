import { element, by, ElementFinder } from 'protractor';

export class CommerceComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-commerce div table .btn-danger'));
    title = element.all(by.css('jhi-commerce div h2#page-heading span')).first();

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

export class CommerceUpdatePage {
    pageTitle = element(by.id('jhi-commerce-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nomCommerceInput = element(by.id('field_nomCommerce'));
    nomRueInput = element(by.id('field_nomRue'));
    codePostaleInput = element(by.id('field_codePostale'));
    siteWebInput = element(by.id('field_siteWeb'));
    descCommerceInput = element(by.id('field_descCommerce'));
    typedecommerceSelect = element(by.id('field_typedecommerce'));
    marchandSelect = element(by.id('field_marchand'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNomCommerceInput(nomCommerce) {
        await this.nomCommerceInput.sendKeys(nomCommerce);
    }

    async getNomCommerceInput() {
        return this.nomCommerceInput.getAttribute('value');
    }

    async setNomRueInput(nomRue) {
        await this.nomRueInput.sendKeys(nomRue);
    }

    async getNomRueInput() {
        return this.nomRueInput.getAttribute('value');
    }

    async setCodePostaleInput(codePostale) {
        await this.codePostaleInput.sendKeys(codePostale);
    }

    async getCodePostaleInput() {
        return this.codePostaleInput.getAttribute('value');
    }

    async setSiteWebInput(siteWeb) {
        await this.siteWebInput.sendKeys(siteWeb);
    }

    async getSiteWebInput() {
        return this.siteWebInput.getAttribute('value');
    }

    async setDescCommerceInput(descCommerce) {
        await this.descCommerceInput.sendKeys(descCommerce);
    }

    async getDescCommerceInput() {
        return this.descCommerceInput.getAttribute('value');
    }

    async typedecommerceSelectLastOption() {
        await this.typedecommerceSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async typedecommerceSelectOption(option) {
        await this.typedecommerceSelect.sendKeys(option);
    }

    getTypedecommerceSelect(): ElementFinder {
        return this.typedecommerceSelect;
    }

    async getTypedecommerceSelectedOption() {
        return this.typedecommerceSelect.element(by.css('option:checked')).getText();
    }

    async marchandSelectLastOption() {
        await this.marchandSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async marchandSelectOption(option) {
        await this.marchandSelect.sendKeys(option);
    }

    getMarchandSelect(): ElementFinder {
        return this.marchandSelect;
    }

    async getMarchandSelectedOption() {
        return this.marchandSelect.element(by.css('option:checked')).getText();
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

export class CommerceDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-commerce-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-commerce'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
