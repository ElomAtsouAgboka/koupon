import { element, by, ElementFinder } from 'protractor';

export class MembreComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-membre div table .btn-danger'));
    title = element.all(by.css('jhi-membre div h2#page-heading span')).first();

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

export class MembreUpdatePage {
    pageTitle = element(by.id('jhi-membre-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    loginMemberInput = element(by.id('field_loginMember'));
    nomMembreInput = element(by.id('field_nomMembre'));
    prenomMembreInput = element(by.id('field_prenomMembre'));
    dateDeNaissanceInput = element(by.id('field_dateDeNaissance'));
    emailMembreInput = element(by.id('field_emailMembre'));
    souscrireMailPersoInput = element(by.id('field_souscrireMailPerso'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setLoginMemberInput(loginMember) {
        await this.loginMemberInput.sendKeys(loginMember);
    }

    async getLoginMemberInput() {
        return this.loginMemberInput.getAttribute('value');
    }

    async setNomMembreInput(nomMembre) {
        await this.nomMembreInput.sendKeys(nomMembre);
    }

    async getNomMembreInput() {
        return this.nomMembreInput.getAttribute('value');
    }

    async setPrenomMembreInput(prenomMembre) {
        await this.prenomMembreInput.sendKeys(prenomMembre);
    }

    async getPrenomMembreInput() {
        return this.prenomMembreInput.getAttribute('value');
    }

    async setDateDeNaissanceInput(dateDeNaissance) {
        await this.dateDeNaissanceInput.sendKeys(dateDeNaissance);
    }

    async getDateDeNaissanceInput() {
        return this.dateDeNaissanceInput.getAttribute('value');
    }

    async setEmailMembreInput(emailMembre) {
        await this.emailMembreInput.sendKeys(emailMembre);
    }

    async getEmailMembreInput() {
        return this.emailMembreInput.getAttribute('value');
    }

    getSouscrireMailPersoInput() {
        return this.souscrireMailPersoInput;
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

export class MembreDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-membre-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-membre'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
