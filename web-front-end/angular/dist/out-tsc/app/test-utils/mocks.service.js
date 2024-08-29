import { of } from 'rxjs';
import { createAccount, createUser, createStock, createTrade, createPosition } from './utils';
export const accounts = Array.from({ length: 5 }, () => createAccount());
export const stocks = Array.from({ length: 5 }, () => createStock());
export const trades = Array.from({ length: 2 }, () => createTrade());
export const positions = Array.from({ length: 2 }, () => createPosition());
const users = Array.from({ length: 5 }, () => createUser());
const accountUsers = accounts.map((ac, index) => {
    const idx = Math.floor(Math.random() * (accounts.length - index));
    return { accountId: ac.id, username: users[idx].fullName };
});
export class MockAccountService {
    addAccountUser(accountUser) {
        return of(accountUser);
    }
    addAccount(account) {
        return of({ displayName: account.displayName || '', id: 1 });
    }
    getAccounts() {
        return of(accounts);
    }
    getAccountUsers() {
        return of(accountUsers);
    }
}
export class MockUserService {
    getUsers(searchText) {
        const src = [{ fullName: 'Jhon mac' }, { fullName: 'Tom san' }, { fullName: 'Merry san' }];
        return of({ people: src.filter((u) => u.fullName.indexOf(searchText) !== -1) });
    }
}
export class MockTradeService {
    getTrades(account_id) {
        return of(trades);
    }
    getPositions(account_id) {
        return of(positions);
    }
}
export class MockSymbolService {
    getStocks() {
        return of(stocks);
    }
    createTicket(ticket) {
        console.log('dummy create ticket called');
        return of({});
    }
}
export class MockTradeFeedService {
    subscribe(topic, callback) {
    }
    unSubscribe() {
    }
}
//# sourceMappingURL=mocks.service.js.map