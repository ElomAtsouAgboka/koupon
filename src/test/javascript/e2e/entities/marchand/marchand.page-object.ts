import { element, by, ElementFinder } from 'protractor';

export class MarchandComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-marchand div table .btn-danger'));
    title = element.all(by.css('jhi-marchand div h2#page-heading span')).first();

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

export class MarchandUpdatePage {
    pageTitle = element(by.id('jhi-marchand-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nomMarchandInput = element(by.id('field_nomMarchand'));
    prenomMarchandInput = element(by.id('field_prenomMarchand'));
    telPrincipaleInput = element(by.id('field_telPrincipale'));
    telSecondaireInput = element(by.id('field_telSecondaire'));
    emailPrincipaleInput = element(by.id('field_emailPrincipale'));
    emailSecondaireInput = element(by.id('field_emailSecondaire'));
    newsletterInput = element(by.id('field_newsletter'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNomMarchandInput(nomMarchand) {
        await this.nomMarchandInput.sendKeys(nomMarchand);
    }

    async getNomMarchandInput() {
        return this.nomMarchandInput.getAttribute('value');
    }

    async setPrenomMarchandInput(prenomMarchand) {
        await this.prenomMarchandInput.sendKeys(prenomMarchand);
    }

    async getPrenomMarchandInput() {
        return this.prenomMarchandInput.getAttribute('value');
    }

    async setTelPrincipaleInput(telPrincipale) {
        await this.telPrincipaleInput.sendKeys(telPrincipale);
    }

    async getTelPrincipaleInput() {
        return this.telPrincipaleInput.getAttribute('value');
    }

    async setTelSecondaireInput(telSecondaire) {
        await this.telSecondaireInput.sendKeys(telSecondaire);
    }

    async getTelSecondaireInput() {
        return this.telSecondaireInput.getAttribute('value');
    }

    async setEmailPrincipaleInput(emailPrincipale) {
        await this.emailPrincipaleInput.sendKeys(emailPrincipale);
    }

    async getEmailPrincipaleInput() {
        return this.emailPrincipaleInput.getAttribute('value');
    }

    async setEmailSecondaireInput(emailSecondaire) {
        await this.emailSecondaireInput.sendKeys(emailSecondaire);
    }

    async getEmailSecondaireInput() {
        return this.emailSecondaireInput.getAttribute('value');
    }

    getNewsletterInput() {
        return this.newsletterInput;
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

export class MarchandDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-marchand-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-marchand'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
