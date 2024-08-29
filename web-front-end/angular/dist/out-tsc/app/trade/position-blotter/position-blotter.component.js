import { __decorate } from "tslib";
import { Component, Input } from '@angular/core';
let PositionBlotterComponent = class PositionBlotterComponent {
    constructor(tradeService, tradeFeed) {
        this.tradeService = tradeService;
        this.tradeFeed = tradeFeed;
        this.positions = [];
        this.pendingPosition = [];
        this.isPending = true;
        this.columnDefs = [
            {
                field: 'security',
                headerName: 'SECURITY'
            },
            {
                headerName: 'QUANTITY',
                field: 'quantity',
                enableCellChangeFlash: true
            }
        ];
    }
    ngOnChanges(change) {
        if (change.account?.currentValue && change.account.currentValue !== change.account.previousValue) {
            const accountId = change.account.currentValue.id;
            this.isPending = true;
            this.tradeService.getPositions(accountId).subscribe((positions) => {
                this.positions = positions;
                this.processPendingPositions();
            }, () => {
                this.isPending = false;
            });
            this.socketUnSubscribeFn?.();
            this.socketUnSubscribeFn = this.tradeFeed.subscribe(`/accounts/${accountId}/positions`, (data) => {
                console.log('Position blotter feed...', data);
                this.updatePosition(data);
            });
        }
    }
    processPendingPositions() {
        this.pendingPosition.forEach((position) => this.update(position));
        this.pendingPosition = [];
        this.isPending = false;
    }
    updatePosition(data) {
        if (this.isPending) {
            this.pendingPosition.push(data);
        }
        else {
            this.update(data);
        }
    }
    update(data) {
        const row = this.gridApi.getRowNode(data.security);
        let positionData;
        if (row) {
            positionData = {
                update: [Object.assign(row.data, { quantity: data.quantity })]
            };
        }
        else {
            positionData = {
                add: [{
                        accountid: data.accountid,
                        quantity: data.quantity,
                        security: data.security,
                        updated: data.updated
                    }],
                addIndex: 0
            };
        }
        this.gridApi.applyTransaction(positionData);
    }
    onGridReady(params) {
        console.log('position blotter is ready...');
        this.gridApi = params.api;
    }
    getRowNodeId(data) {
        return data.security;
    }
    ngOnDestroy() {
        this.socketUnSubscribeFn?.();
    }
};
__decorate([
    Input()
], PositionBlotterComponent.prototype, "account", void 0);
PositionBlotterComponent = __decorate([
    Component({
        selector: 'app-position-blotter',
        templateUrl: './position-blotter.component.html',
        styleUrls: ['./position-blotter.component.scss']
    })
], PositionBlotterComponent);
export { PositionBlotterComponent };
//# sourceMappingURL=position-blotter.component.js.map