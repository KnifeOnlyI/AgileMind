"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var shared_libs_module_1 = require("./shared-libs.module");
var find_language_from_key_pipe_1 = require("./language/find-language-from-key.pipe");
var alert_component_1 = require("./alert/alert.component");
var alert_error_component_1 = require("./alert/alert-error.component");
var login_component_1 = require("./login/login.component");
var has_any_authority_directive_1 = require("./auth/has-any-authority.directive");
var AgileMindSharedModule = /** @class */ (function () {
    function AgileMindSharedModule() {
    }
    AgileMindSharedModule = __decorate([
        core_1.NgModule({
            imports: [shared_libs_module_1.AgileMindSharedLibsModule],
            declarations: [find_language_from_key_pipe_1.FindLanguageFromKeyPipe, alert_component_1.AlertComponent, alert_error_component_1.AlertErrorComponent, login_component_1.LoginModalComponent, has_any_authority_directive_1.HasAnyAuthorityDirective],
            entryComponents: [login_component_1.LoginModalComponent],
            exports: [
                shared_libs_module_1.AgileMindSharedLibsModule,
                find_language_from_key_pipe_1.FindLanguageFromKeyPipe,
                alert_component_1.AlertComponent,
                alert_error_component_1.AlertErrorComponent,
                login_component_1.LoginModalComponent,
                has_any_authority_directive_1.HasAnyAuthorityDirective,
            ],
        })
    ], AgileMindSharedModule);
    return AgileMindSharedModule;
}());
exports.AgileMindSharedModule = AgileMindSharedModule;
