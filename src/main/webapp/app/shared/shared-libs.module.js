"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var common_1 = require("@angular/common");
var forms_1 = require("@angular/forms");
var ng_bootstrap_1 = require("@ng-bootstrap/ng-bootstrap");
var ng_jhipster_1 = require("ng-jhipster");
var ngx_infinite_scroll_1 = require("ngx-infinite-scroll");
var angular_fontawesome_1 = require("@fortawesome/angular-fontawesome");
var core_2 = require("@ngx-translate/core");
var AgileMindSharedLibsModule = /** @class */ (function () {
    function AgileMindSharedLibsModule() {
    }
    AgileMindSharedLibsModule = __decorate([
        core_1.NgModule({
            exports: [
                forms_1.FormsModule,
                common_1.CommonModule,
                ng_bootstrap_1.NgbModule,
                ng_jhipster_1.NgJhipsterModule,
                ngx_infinite_scroll_1.InfiniteScrollModule,
                angular_fontawesome_1.FontAwesomeModule,
                forms_1.ReactiveFormsModule,
                core_2.TranslateModule,
            ],
        })
    ], AgileMindSharedLibsModule);
    return AgileMindSharedLibsModule;
}());
exports.AgileMindSharedLibsModule = AgileMindSharedLibsModule;
