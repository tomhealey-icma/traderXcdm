import { __decorate } from "tslib";
import { Component } from '@angular/core';
let ButtonCellRendererComponent = class ButtonCellRendererComponent {
    agInit(params) {
        this.params = params;
    }
    clickHandler() {
        console.log(this.params.data);
        this.params.clicked(this.params.data);
    }
    refresh(params) {
        return false;
    }
};
ButtonCellRendererComponent = __decorate([
    Component({
        selector: 'app-btn-cell-renderer',
        template: `
      <button class="btn btn-sm btn-info" (click)="clickHandler()">Update</button>
    `
    })
], ButtonCellRendererComponent);
export { ButtonCellRendererComponent };
//# sourceMappingURL=button-renderer.component.js.map