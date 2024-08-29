import { __decorate } from "tslib";
import { Injectable } from '@angular/core';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'main/environments/environment';
let PositionService = class PositionService {
    constructor(http) {
        this.http = http;
        this.tradesUrl = `${environment.positionsUrl}/trades/`;
        this.positionsUrl = `${environment.positionsUrl}/positions/`;
    }
    getTrades(account_id) {
        return this.http.get(this.tradesUrl + account_id).pipe(catchError(this.handleError));
    }
    getPositions(account_id) {
        return this.http.get(this.positionsUrl + account_id).pipe(catchError(this.handleError));
    }
    handleError(error) {
        console.error(error);
        return throwError(() => error);
    }
};
PositionService = __decorate([
    Injectable({
        providedIn: 'root'
    })
], PositionService);
export { PositionService };
//# sourceMappingURL=position.service.js.map