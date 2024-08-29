import { __decorate } from "tslib";
import { Component, Input } from '@angular/core';
let TradeBlotterComponent = class TradeBlotterComponent {
    constructor(tradeFeed, tradeService) {
        this.tradeFeed = tradeFeed;
        this.tradeService = tradeService;
        this.trades = [];
        this.pendingTrades = [];
        this.isPending = true;
        this.columnDefs = [
            {
                headerName: 'SECURITY',
                field: 'security'
            },
            {
                headerName: 'QUANTITY',
                field: 'quantity'
            },
            {
                headerName: 'SIDE',
                field: 'side'
            },
            {
                headerName: 'STATE',
                field: 'state',
                enableCellChangeFlash: true
            }
        ];
    }
    ngOnChanges(change) {
        if (change.account?.currentValue && change.account.currentValue !== change.account.previousValue) {
            const accountId = change.account.currentValue.id;
            this.isPending = true;
            this.tradeService.getTrades(accountId).subscribe((trades) => {
                this.trades = trades;
                this.processPendingTrades();
            });
            this.socketUnSubscribeFn?.();
            this.socketUnSubscribeFn = this.tradeFeed.subscribe(`/accounts/${accountId}/trades`, (data) => {
                console.log('Trade blotter feed...', data);
                this.updateTrades(data);
            });
        }
    }
    onGridReady(params) {
        console.log('trade blotter is ready...');
        this.gridApi = params.api;
        this.gridApi.sizeColumnsToFit();
    }
    getRowNodeId(data) {
        return data.id;
    }
    ngOnDestroy() {
        this.socketUnSubscribeFn?.();
    }
    processPendingTrades() {
        this.pendingTrades.forEach((tradeUpdate) => this.update(tradeUpdate));
        this.pendingTrades = [];
        this.isPending = false;
    }
    update(data) {
        const row = this.gridApi.getRowNode(data.id);
        let tradeData;
        if (row) {
            tradeData = {
                update: [Object.assign(row.data, { state: data.state })]
            };
        }
        else {
            tradeData = {
                add: [{
                        accountid: data.accountid,
                        created: data.created,
                        id: data.id,
                        quantity: data.quantity,
                        security: data.security,
                        side: data.side,
                        state: data.state,
                        updated: data.updated
                    }],
                addIndex: 0
            };
        }
        this.gridApi.applyTransaction(tradeData);
    }
    updateTrades(data) {
        if (this.isPending) {
            this.pendingTrades.push(data);
        }
        else {
            this.update(data);
        }
    }
};
__decorate([
    Input()
], TradeBlotterComponent.prototype, "account", void 0);
TradeBlotterComponent = __decorate([
    Component({
        selector: 'app-trade-blotter',
        templateUrl: 'trade-blotter.component.html'
    })
], TradeBlotterComponent);
export { TradeBlotterComponent };
//# sourceMappingURL=trade-blotter.component.js.map