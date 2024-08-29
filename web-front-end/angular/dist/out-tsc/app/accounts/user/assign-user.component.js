import { __decorate } from "tslib";
import { catchError } from 'rxjs/operators';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { map, noop, Observable, of, switchMap, tap } from 'rxjs';
let AssignUserToAccountComponent = class AssignUserToAccountComponent {
    constructor(userService, accountService) {
        this.userService = userService;
        this.accountService = accountService;
        this.accounts = [];
        this.account = undefined;
        this.user = undefined;
        this.search = undefined;
        this.update = new EventEmitter();
    }
    ngOnInit() {
        this.users$ = new Observable((observer) => {
            observer.next(this.search);
        }).pipe(switchMap((query) => {
            if (query && query.length > 2) {
                return this.userService.getUsers(query).pipe(map((data) => data || []), tap(() => noop, err => {
                    console.log(err && err.message || 'Something goes wrong');
                }), catchError(() => of([])));
            }
            return of([]);
        }));
    }
    comparatorFunction(src, target) {
        return src?.id === target?.id;
    }
    add() {
        if (!this.user || !this.account) {
            return;
        }
        const accountUser = { username: this.user.logonId, accountId: this.account.id };
        this.accountService.addAccountUser(accountUser).subscribe(() => {
            this.addUserResponse = { success: true, msg: 'User added successfully!' };
            this.update.emit(this.account);
            this.reset(true);
        }, (err) => {
            this.addUserResponse = { error: true, msg: 'There is some error!' };
            console.error(err);
        });
    }
    onSelect(event) {
        this.user = event.item;
    }
    reset(fromAdd = false) {
        this.user = undefined;
        this.search = undefined;
        // keep account sticky if we are just adding a user
        if (!fromAdd)
            this.account = undefined;
    }
    onCloseAlert() {
        this.addUserResponse = undefined;
    }
};
__decorate([
    Input()
], AssignUserToAccountComponent.prototype, "accounts", void 0);
__decorate([
    Input()
], AssignUserToAccountComponent.prototype, "account", void 0);
__decorate([
    Output()
], AssignUserToAccountComponent.prototype, "update", void 0);
AssignUserToAccountComponent = __decorate([
    Component({
        selector: 'app-assign-user',
        templateUrl: 'assign-user.component.html'
    })
], AssignUserToAccountComponent);
export { AssignUserToAccountComponent };
//# sourceMappingURL=assign-user.component.js.map