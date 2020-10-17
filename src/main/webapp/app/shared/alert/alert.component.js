"use strict";
var __assign = (this && this.__assign) || function () {
    __assign = Object.assign || function(t) {
        for (var s, i = 1, n = arguments.length; i < n; i++) {
            s = arguments[i];
            for (var p in s) if (Object.prototype.hasOwnProperty.call(s, p))
                t[p] = s[p];
        }
        return t;
    };
    return __assign.apply(this, arguments);
};
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var AlertComponent = /** @class */ (function () {
    function AlertComponent(alertService) {
        this.alertService = alertService;
        this.alerts = [];
    }
    AlertComponent.prototype.ngOnInit = function () {
        this.alerts = this.alertService.get();
    };
    AlertComponent.prototype.setClasses = function (alert) {
        var _a;
        var classes = { 'jhi-toast': Boolean(alert.toast) };
        if (alert.position) {
            return __assign(__assign({}, classes), (_a = {}, _a[alert.position] = true, _a));
        }
        return classes;
    };
    AlertComponent.prototype.ngOnDestroy = function () {
        this.alertService.clear();
    };
    AlertComponent.prototype.close = function (alert) {
        var _a, _b;
        // NOSONAR can be removed after https://github.com/SonarSource/SonarJS/issues/1930 is resolved
        (_b = (_a = alert).close) === null || _b === void 0 ? void 0 : _b.call(_a, this.alerts); // NOSONAR
    };
    AlertComponent = __decorate([
        core_1.Component({
            selector: 'jhi-alert',
            template: " <div class=\"alerts\" role=\"alert\">\n    <div *ngFor=\"let alert of alerts\" [ngClass]=\"setClasses(alert)\">\n      <ngb-alert *ngIf=\"alert && alert.type && alert.msg\" [type]=\"alert.type\" (close)=\"close(alert)\">\n        <pre [innerHTML]=\"alert.msg\"></pre>\n      </ngb-alert>\n    </div>\n  </div>",
        })
    ], AlertComponent);
    return AlertComponent;
}());
exports.AlertComponent = AlertComponent;
