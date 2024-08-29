import { __decorate } from "tslib";
import { Injectable } from '@angular/core';
var Themes;
(function (Themes) {
    Themes["ProfessionalLight"] = "professional-light";
    Themes["ProfessionalDark"] = "professional-dark";
})(Themes || (Themes = {}));
let ThemeService = class ThemeService {
    constructor() {
        this.currentTheme = Themes.ProfessionalDark;
    }
    switchTheme() {
        console.log('theme service');
        this.currentTheme = this.currentTheme === Themes.ProfessionalDark ? Themes.ProfessionalLight : Themes.ProfessionalDark;
        document.documentElement.className = this.currentTheme;
        const themeTag = document.querySelector('#theme-tag');
        themeTag.href = `${this.currentTheme}.css`;
    }
};
ThemeService = __decorate([
    Injectable({
        providedIn: 'root'
    })
], ThemeService);
export { ThemeService };
//# sourceMappingURL=theme.service.js.map