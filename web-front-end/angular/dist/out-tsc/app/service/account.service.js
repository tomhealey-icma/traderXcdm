import { __decorate } from "tslib";
import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { environment } from 'main/environments/environment';
let AccountService = class AccountService {
    constructor(http) {
        this.http = http;
        this.baseUrl = environment.accountUrl;
        this.httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json'
            })
        };
    }
    getAccounts() {
        return this.http.get(`${this.baseUrl}/account/`, this.httpOptions).pipe(retry(2), catchError((error) => {
            console.error(error);
            return throwError(() => error);
        }));
    }
    addAccount(account) {
        return this.http.post(`${this.baseUrl}/account/`, account, this.httpOptions).pipe(catchError((error) => {
            console.error(error);
            return throwError(() => error);
        }));
    }
    addAccountUser(accountUser) {
        return this.http.post(`${this.baseUrl}/accountuser/`, accountUser, this.httpOptions).pipe(catchError((error) => {
            console.error(error);
            return throwError(() => error);
        }));
    }
    getAccountUsers() {
        return this.http.get(`${this.baseUrl}/accountuser/`, this.httpOptions).pipe(catchError((error) => {
            console.error(error);
            return throwError(() => error);
        }));
    }
};
AccountService = __decorate([
    Injectable({
        providedIn: 'root'
    })
], AccountService);
export { AccountService };
//# sourceMappingURL=account.service.js.map