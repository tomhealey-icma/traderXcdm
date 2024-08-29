import { __decorate } from "tslib";
import { Component } from '@angular/core';
import { Subject } from 'rxjs';
let TradeComponent = class TradeComponent {
    constructor(accountService, symbolService, modalService) {
        this.accountService = accountService;
        this.symbolService = symbolService;
        this.modalService = modalService;
        this.accounts = [];
        this.accountModel = undefined;
        this.stocks = [];
        this.account = new Subject();
    }
    ngOnInit() {
        this.accountService.getAccounts().subscribe((accounts) => {
            this.accounts = accounts;
            this.setAccount(this.accounts[5]);
            console.log(this.accounts);
        });
        this.symbolService.getStocks().subscribe((stocks) => this.stocks = stocks);
    }
    onAccountChange(account) {
        console.log('onAccountChange', arguments);
        account && this.setAccount(account);
    }
    getAccountName(item) {
        return item.displayName;
    }
    openTicket(template) {
        this.modalRef = this.modalService.show(template);
    }
    createTradeTicket(ticket) {
        console.log('createTradeTicket', ticket);
        this.symbolService.createTicket(ticket).subscribe((response) => {
            console.log(response);
            this.createTicketResponse = response;
        });
        this.closeTicket();
    }
    closeTicket() {
        this.modalRef?.hide();
    }
    onCloseAlert() {
        this.createTicketResponse = undefined;
    }
    setAccount(account) {
        this.accountModel = account;
        this.account.next(account);
    }
};
TradeComponent = __decorate([
    Component({
        selector: 'app-trade',
        templateUrl: './trade.component.html',
        styleUrls: ['./trade.component.scss']
    })
], TradeComponent);
export { TradeComponent };
//# sourceMappingURL=trade.component.js.map