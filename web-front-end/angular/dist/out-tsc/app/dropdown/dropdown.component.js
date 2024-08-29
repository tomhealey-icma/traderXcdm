import { __decorate } from "tslib";
import { Component, EventEmitter, Input, Output } from "@angular/core";
let id = 0;
let DropdownComponent = class DropdownComponent {
    constructor() {
        this.itemKey = 'label';
        this.selectedItemChange = new EventEmitter();
        this.placeholder = "Please select an item";
        this.defaultComparator = (src, target) => src === target;
    }
    ngOnInit() {
        this.selectionComparator = this.selectionComparator || this.defaultComparator;
        const uid = id++;
        this.drpId = 'drp' + uid;
        this.drpBtnId = 'drpbtn' + uid;
    }
    onItemClick(item) {
        if (!this.selectionComparator(this.selectedItem, item)) {
            this.selectedItemChange.emit(item);
        }
    }
};
__decorate([
    Input()
], DropdownComponent.prototype, "items", void 0);
__decorate([
    Input()
], DropdownComponent.prototype, "itemKey", void 0);
__decorate([
    Input()
], DropdownComponent.prototype, "selectedItem", void 0);
__decorate([
    Input()
], DropdownComponent.prototype, "selectionComparator", void 0);
__decorate([
    Output()
], DropdownComponent.prototype, "selectedItemChange", void 0);
__decorate([
    Input()
], DropdownComponent.prototype, "placeholder", void 0);
DropdownComponent = __decorate([
    Component({
        selector: 'app-ngx-dropdown',
        templateUrl: './dropdown.component.html'
    })
], DropdownComponent);
export { DropdownComponent };
//# sourceMappingURL=dropdown.component.js.map