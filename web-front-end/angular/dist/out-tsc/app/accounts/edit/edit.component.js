import { __decorate } from "tslib";
import { Component, Output, EventEmitter, Input } from '@angular/core';
let EditAccountComponent = class EditAccountComponent {
    set account(ac) {
        this._account = ac;
        if (ac?.displayName) {
            this.displayName = ac.displayName;
        }
    }
    get account() {
        return this._account;
    }
    constructor(accountService) {
        this.accountService = accountService;
        this.update = new EventEmitter();
        this.displayName = undefined;
    }
    add() {
        if (!this.displayName?.trim()) {
            return;
        }
        const account = Object.assign(this.account || {}, { displayName: this.displayName });
        this.accountService.addAccount(account).subscribe(() => {
            this.accountResponse = { success: true, msg: `Account ${account.id ? 'updated' : 'added'} successfully!` };
            this.update.emit(account);
            this.reset();
        }, (err) => {
            console.error(err);
            this.accountResponse = { success: err, msg: `There is some error!` };
        });
    }
    reset() {
        this.account = undefined;
        this.displayName = undefined;
    }
    onCloseAlert() {
        this.accountResponse = undefined;
    }
};
__decorate([
    Output()
], EditAccountComponent.prototype, "update", void 0);
__decorate([
    Input()
], EditAccountComponent.prototype, "account", null);
EditAccountComponent = __decorate([
    Component({
        selector: 'app-edit-account',
        templateUrl: 'edit.component.html',
        styleUrls: ['edit.component.scss']
    })
], EditAccountComponent);
export { EditAccountComponent };
//# sourceMappingURL=edit.component.js.map