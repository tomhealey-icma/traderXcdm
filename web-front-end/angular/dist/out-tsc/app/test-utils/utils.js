import { faker } from '@faker-js/faker';
import { State, Side } from '../model/trade.model';
export function sleep(delay = 0) {
    return new Promise((re) => setTimeout(re, delay));
}
export function createUser() {
    return {
        fullName: faker.name.firstName(),
        email: faker.internet.email(),
        department: faker.commerce.department(),
        logonId: faker.random.alphaNumeric(5),
        employeeId: faker.random.alpha(5),
        photoUrl: 'testurl'
    };
}
export function createAccount() {
    return {
        displayName: faker.company.name(),
        id: faker.datatype.number()
    };
}
export function createStock() {
    return {
        companyName: faker.company.name(),
        ticker: faker.random.alpha(4)
    };
}
export function createTrade() {
    return {
        created: faker.date.recent(),
        id: faker.random.alpha(5),
        state: State.Pending,
        side: Side.Buy,
        ...createPosition()
    };
}
export function createPosition() {
    return {
        accountid: faker.datatype.number(),
        quantity: faker.datatype.number(100),
        security: faker.random.alpha(4),
        updated: faker.date.recent()
    };
}
//# sourceMappingURL=utils.js.map