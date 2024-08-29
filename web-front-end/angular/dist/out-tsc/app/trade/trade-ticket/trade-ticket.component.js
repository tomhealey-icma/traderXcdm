import { __decorate } from "tslib";
import { Component, Input, Output, EventEmitter } from '@angular/core';
let TradeTicketComponent = class TradeTicketComponent {
    constructor() {
        this.create = new EventEmitter();
        this.cancel = new EventEmitter();
        this.selectedCompany = undefined;
        this.filteredStocks = [];
    }
    ngOnInit() {
        this.ticket = {
            quantity: 0,
            accountId: this.account?.id || 0,
            side: 'Buy',
            security: ''
        };
        this.filteredStocks = this.stocks;
    }
    onSelect(e) {
        console.log('Selected value: ', e.value);
        this.ticket.security = e.item.ticker;
    }
    onBlur() {
        if (this.selectedCompany)
            return;
        this.ticket.security = '';
    }
    onCreate() {
        if (!this.ticket.security || !this.ticket.quantity) {
            console.warn('Either security is not selected or quanity is not set!');
            return;
        }
        console.log('create tradeTicket', this.ticket);
        this.create.emit(this.ticket);
    }
    onCancel() {
        this.cancel.emit();
    }
};
__decorate([
    Input()
], TradeTicketComponent.prototype, "stocks", void 0);
__decorate([
    Input()
], TradeTicketComponent.prototype, "account", void 0);
__decorate([
    Output()
], TradeTicketComponent.prototype, "create", void 0);
__decorate([
    Output()
], TradeTicketComponent.prototype, "cancel", void 0);
TradeTicketComponent = __decorate([
    Component({
        selector: 'app-trade-ticket',
        templateUrl: './trade-ticket.component.html',
        styleUrls: ['./trade-ticket.component.scss']
    })
], TradeTicketComponent);
export { TradeTicketComponent };
//# sourceMappingURL=trade-ticket.component.js.map