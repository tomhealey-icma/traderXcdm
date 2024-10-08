import { TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';
xdescribe('AppComponent', () => {
    beforeEach(async () => {
        TestBed.configureTestingModule({
            declarations: [AppComponent]
        }).compileComponents();
    });
    it('should create the app', () => {
        const fixture = TestBed.createComponent(AppComponent);
        const app = fixture.debugElement.componentInstance;
        expect(app).toBeTruthy();
    });
    it(`should have as title 'main-application'`, () => {
        const fixture = TestBed.createComponent(AppComponent);
        const app = fixture.debugElement.componentInstance;
        expect(app.title).toEqual('main-application');
    });
    it('should render title', () => {
        const fixture = TestBed.createComponent(AppComponent);
        fixture.detectChanges();
        const compiled = fixture.debugElement.nativeElement;
        expect(compiled.querySelector('.content span').textContent).toContain('main-application app is running!');
    });
});
//# sourceMappingURL=app.component.spec.js.map