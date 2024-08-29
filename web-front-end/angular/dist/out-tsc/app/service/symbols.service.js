import { __decorate } from "tslib";
import { Injectable } from '@angular/core';
import { throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { environment } from 'main/environments/environment';
let SymbolService = class SymbolService {
    constructor(http) {
        this.http = http;
        this.stocksUrl = `${environment.refrenceDataUrl}/stocks`;
        this.createTicketUrl = `${environment.tradesUrl}`;
    }
    getStocks() {
        return this.http.get(this.stocksUrl).pipe(retry(2), catchError(this.handleError));
    }
    createTicket(ticket) {
        return this.http.post(this.createTicketUrl, ticket).pipe(catchError(this.handleError));
    }
    handleError(error) {
        console.error(error);
        return throwError(() => error);
    }
};
SymbolService = __decorate([
    Injectable({
        providedIn: 'root'
    })
], SymbolService);
export { SymbolService };
//# sourceMappingURL=symbols.service.js.map