import { __decorate } from "tslib";
import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { environment } from 'main/environments/environment';
let UserService = class UserService {
    constructor(http) {
        this.http = http;
        this.baseUrl = environment.peopleUrl;
        this.httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json'
            })
        };
    }
    getUsers(searchText) {
        return this.http.get(`${this.baseUrl}/People/GetMatchingPeople`, {
            headers: this.httpOptions.headers,
            params: { SearchText: searchText, Take: '10' }
        }).pipe(map(response => response.people || []), catchError((error) => {
            console.error(error);
            return throwError(() => error);
        }));
    }
};
UserService = __decorate([
    Injectable({
        providedIn: 'root'
    })
], UserService);
export { UserService };
//# sourceMappingURL=user.service.js.map