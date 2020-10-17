"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
/**
 * @whatItDoes Conditionally includes an HTML element if current user has any
 * of the authorities passed as the `expression`.
 *
 * @howToUse
 * ```
 *     <some-element *jhiHasAnyAuthority="'ROLE_ADMIN'">...</some-element>
 *
 *     <some-element *jhiHasAnyAuthority="['ROLE_ADMIN', 'ROLE_USER']">...</some-element>
 * ```
 */
var HasAnyAuthorityDirective = /** @class */ (function () {
    function HasAnyAuthorityDirective(accountService, templateRef, viewContainerRef) {
        this.accountService = accountService;
        this.templateRef = templateRef;
        this.viewContainerRef = viewContainerRef;
        this.authorities = [];
    }
    Object.defineProperty(HasAnyAuthorityDirective.prototype, "jhiHasAnyAuthority", {
        set: function (value) {
            var _this = this;
            this.authorities = typeof value === 'string' ? [value] : value;
            this.updateView();
            // Get notified each time authentication state changes.
            this.authenticationSubscription = this.accountService.getAuthenticationState().subscribe(function () { return _this.updateView(); });
        },
        enumerable: true,
        configurable: true
    });
    HasAnyAuthorityDirective.prototype.ngOnDestroy = function () {
        if (this.authenticationSubscription) {
            this.authenticationSubscription.unsubscribe();
        }
    };
    HasAnyAuthorityDirective.prototype.updateView = function () {
        var hasAnyAuthority = this.accountService.hasAnyAuthority(this.authorities);
        this.viewContainerRef.clear();
        if (hasAnyAuthority) {
            this.viewContainerRef.createEmbeddedView(this.templateRef);
        }
    };
    __decorate([
        core_1.Input()
    ], HasAnyAuthorityDirective.prototype, "jhiHasAnyAuthority", null);
    HasAnyAuthorityDirective = __decorate([
        core_1.Directive({
            selector: '[jhiHasAnyAuthority]',
        })
    ], HasAnyAuthorityDirective);
    return HasAnyAuthorityDirective;
}());
exports.HasAnyAuthorityDirective = HasAnyAuthorityDirective;
