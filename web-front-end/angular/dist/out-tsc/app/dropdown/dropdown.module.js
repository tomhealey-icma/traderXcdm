import { __decorate } from "tslib";
import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { DropdownComponent } from "./dropdown.component";
let DropdownModule = class DropdownModule {
};
DropdownModule = __decorate([
    NgModule({
        declarations: [DropdownComponent],
        imports: [CommonModule,
            BrowserAnimationsModule,
            BsDropdownModule.forRoot()],
        exports: [DropdownComponent]
    })
], DropdownModule);
export { DropdownModule };
//# sourceMappingURL=dropdown.module.js.map