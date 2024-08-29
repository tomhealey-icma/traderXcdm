import { __decorate } from "tslib";
import { Component, Output, EventEmitter } from '@angular/core';
let HeaderComponent = class HeaderComponent {
    constructor() {
        this.switchTheme = new EventEmitter();
    }
    ngOnInit() {
    }
};
__decorate([
    Output()
], HeaderComponent.prototype, "switchTheme", void 0);
HeaderComponent = __decorate([
    Component({
        selector: 'app-header',
        templateUrl: './header.component.html',
        styleUrls: ['./header.component.scss']
    })
], HeaderComponent);
export { HeaderComponent };
//# sourceMappingURL=header.component.js.map