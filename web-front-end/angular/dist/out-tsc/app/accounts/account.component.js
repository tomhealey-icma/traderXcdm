import { __decorate } from "tslib";
import { Component } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { debounceTime, map, switchMap } from 'rxjs/operators';
import { ButtonCellRendererComponent } from './button-renderer.component';
let AccountComponent = class AccountComponent {
    constructor(accountService) {
        this.accountService = accountService;
        this.accountBehaviorSubject = new BehaviorSubject(0);
        this.accountAddAction$ = this.accountBehaviorSubject.asObservable();
        this.selectedAccount = undefined;
        this.accountToBeUpdate = undefined;
        this.columnDefs = [
            {
                field: 'id',
                flex: 1
            },
            {
                field: 'displayName',
                flex: 2
            },
            {
                headerName: 'Update',
                cellRenderer: 'btnCellRenderer',
                cellRendererParams: {
                    clicked: (account) => this.accountToBeUpdate = account
                },
                flex: 1
            }
        ];
        this.columnDefsUser = [
            {
                field: 'accountId',
                flex: 1
            },
            {
                field: 'username',
                flex: 1
            }
        ];
        this.frameworkComponents = {
            btnCellRenderer: ButtonCellRendererComponent
        };
    }
    ngOnInit() {
        this.accounts$ = this.accountAddAction$.pipe(debounceTime(200), switchMap(() => this.accountService.getAccounts()));
        this.users$ = this.accountAddAction$.pipe(debounceTime(200), switchMap((accountId) => this.accountService.getAccountUsers().pipe(map((users) => users.filter((user) => user.accountId === accountId)))));
    }
    onUpdate(account) {
        this.accountBehaviorSubject.next(account?.id);
        this.selectedAccount = account;
    }
    onSelectionChanged() {
        const selectedRows = this.gridApi.getSelectedRows();
        this.selectedAccount = selectedRows[0];
        this.accountBehaviorSubject.next(selectedRows[0].id);
    }
    onGridReady(params) {
        this.gridApi = params.api;
    }
};
AccountComponent = __decorate([
    Component({
        selector: 'app-account',
        templateUrl: 'account.component.html',
        styleUrls: ['account.component.scss']
    })
], AccountComponent);
export { AccountComponent };
//# sourceMappingURL=account.component.js.map