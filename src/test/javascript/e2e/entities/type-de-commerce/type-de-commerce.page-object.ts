import { element, by, ElementFinder } from 'protractor';

export class TypeDeCommerceComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-type-de-commerce div table .btn-danger'));
    title = element.all(by.css('jhi-type-de-commerce div h2#page-heading span')).first();

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

export class TypeDeCommerceUpdatePage {
    pageTitle = element(by.id('jhi-type-de-commerce-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nomTypeDeCommerceInput = element(by.id('field_nomTypeDeCommerce'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNomTypeDeCommerceInput(nomTypeDeCommerce) {
        await this.nomTypeDeCommerceInput.sendKeys(nomTypeDeCommerce);
    }

    async getNomTypeDeCommerceInput() {
        return this.nomTypeDeCommerceInput.getAttribute('value');
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

export class TypeDeCommerceDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-typeDeCommerce-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-typeDeCommerce'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
