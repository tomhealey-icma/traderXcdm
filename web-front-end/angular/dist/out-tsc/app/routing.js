import { AccountComponent } from './accounts/account.component';
import { PageNotFoundComponent } from './page-not-found.component';
import { TradeComponent } from './trade/trade.component';
export const routes = [
    { path: 'trade', component: TradeComponent },
    { path: 'account', component: AccountComponent },
    { path: '', redirectTo: '/trade', pathMatch: 'full' },
    { path: '**', component: PageNotFoundComponent }
];
//# sourceMappingURL=routing.js.map