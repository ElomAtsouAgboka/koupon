import { element, by, ElementFinder } from 'protractor';

export class OptionDealComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-option-deal div table .btn-danger'));
    title = element.all(by.css('jhi-option-deal div h2#page-heading span')).first();

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

export class OptionDealUpdatePage {
    pageTitle = element(by.id('jhi-option-deal-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    libOptionDealInput = element(by.id('field_libOptionDeal'));
    prixNormalOptionDealInput = element(by.id('field_prixNormalOptionDeal'));
    prixReductionOptionBonPlanInput = element(by.id('field_prixReductionOptionBonPlan'));
    pcReductionOptionBonPlanInput = element(by.id('field_pcReductionOptionBonPlan'));
    dealSelect = element(by.id('field_deal'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setLibOptionDealInput(libOptionDeal) {
        await this.libOptionDealInput.sendKeys(libOptionDeal);
    }

    async getLibOptionDealInput() {
        return this.libOptionDealInput.getAttribute('value');
    }

    async setPrixNormalOptionDealInput(prixNormalOptionDeal) {
        await this.prixNormalOptionDealInput.sendKeys(prixNormalOptionDeal);
    }

    async getPrixNormalOptionDealInput() {
        return this.prixNormalOptionDealInput.getAttribute('value');
    }

    async setPrixReductionOptionBonPlanInput(prixReductionOptionBonPlan) {
        await this.prixReductionOptionBonPlanInput.sendKeys(prixReductionOptionBonPlan);
    }

    async getPrixReductionOptionBonPlanInput() {
        return this.prixReductionOptionBonPlanInput.getAttribute('value');
    }

    async setPcReductionOptionBonPlanInput(pcReductionOptionBonPlan) {
        await this.pcReductionOptionBonPlanInput.sendKeys(pcReductionOptionBonPlan);
    }

    async getPcReductionOptionBonPlanInput() {
        return this.pcReductionOptionBonPlanInput.getAttribute('value');
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

export class OptionDealDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-optionDeal-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-optionDeal'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
