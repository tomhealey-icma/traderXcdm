import { TestBed } from '@angular/core/testing';
import { TradeComponent } from './trade.component';
import { FormsModule } from '@angular/forms';
import { AlertModule } from 'ngx-bootstrap/alert';
import { ModalModule } from 'ngx-bootstrap/modal';
import { AccountService } from '../service/account.service';
import { MockAccountService, MockSymbolService, accounts } from '../test-utils/mocks.service';
import { SymbolService } from '../service/symbols.service';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { Side } from '../model/trade.model';
import { DropdownModule } from '../dropdown/dropdown.module';
describe('TradeComponent', () => {
    let component;
    let fixture;
    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [
                TradeComponent
            ],
            imports: [
                FormsModule,
                DropdownModule,
                ModalModule.forRoot(),
                AlertModule.forRoot()
            ],
            providers: [
                {
                    provide: AccountService,
                    useClass: MockAccountService
                },
                {
                    provide: SymbolService,
                    useClass: MockSymbolService
                }
            ],
            schemas: [CUSTOM_ELEMENTS_SCHEMA]
        }).compileComponents();
    });
    beforeEach(() => {
        fixture = TestBed.createComponent(TradeComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });
    it('should create', () => {
        expect(component).toBeTruthy();
    });
    it('should call symbol service to create ticket on call', async () => {
        const ticket = {
            accountId: 1,
            quantity: 10,
            security: 'abc',
            side: Side.Buy
        };
        spyOn(component.symbolService, 'createTicket').and.callThrough();
        spyOn(component, 'closeTicket');
        component.createTradeTicket(ticket);
        expect(component.symbolService.createTicket).toHaveBeenCalledWith(ticket);
        expect(component.closeTicket).toHaveBeenCalled();
    });
    it('should get accounts and stocks on init', () => {
        spyOn(component.accountService, 'getAccounts').and.callThrough();
        spyOn(component.symbolService, 'getStocks').and.callThrough();
        component.ngOnInit();
        expect(component.accountService.getAccounts).toHaveBeenCalled();
        expect(component.accounts.length).toEqual(5);
        expect(component.symbolService.getStocks).toHaveBeenCalled();
        expect(component.stocks.length).toEqual(5);
    });
    it('should open and close ticket on click', async () => {
        component.accountModel = accounts[0];
        spyOn(component, 'openTicket');
        spyOn(component, 'closeTicket');
        fixture.nativeElement.querySelector('#createTicketBtn').click();
        expect(component.openTicket).toHaveBeenCalled();
        // TODO: need to check how to test bootstrap modal in jasmine
        // fixture.nativeElement.click();
        // expect(component.closeTicket).toHaveBeenCalled();
    });
});
//# sourceMappingURL=trade.component.spec.js.map