import { element, by, ElementFinder } from 'protractor';

export class CategorieComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-categorie div table .btn-danger'));
    title = element.all(by.css('jhi-categorie div h2#page-heading span')).first();

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

export class CategorieUpdatePage {
    pageTitle = element(by.id('jhi-categorie-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nomCategorieInput = element(by.id('field_nomCategorie'));
    categorieParentSelect = element(by.id('field_categorieParent'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNomCategorieInput(nomCategorie) {
        await this.nomCategorieInput.sendKeys(nomCategorie);
    }

    async getNomCategorieInput() {
        return this.nomCategorieInput.getAttribute('value');
    }

    async categorieParentSelectLastOption() {
        await this.categorieParentSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async categorieParentSelectOption(option) {
        await this.categorieParentSelect.sendKeys(option);
    }

    getCategorieParentSelect(): ElementFinder {
        return this.categorieParentSelect;
    }

    async getCategorieParentSelectedOption() {
        return this.categorieParentSelect.element(by.css('option:checked')).getText();
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

export class CategorieDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-categorie-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-categorie'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
