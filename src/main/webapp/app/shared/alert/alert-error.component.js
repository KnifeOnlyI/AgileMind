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
var AlertErrorComponent = /** @class */ (function () {
    function AlertErrorComponent(alertService, eventManager, translateService) {
        var _this = this;
        this.alertService = alertService;
        this.eventManager = eventManager;
        this.alerts = [];
        this.errorListener = eventManager.subscribe('agileMindApp.error', function (response) {
            var errorResponse = response.content;
            _this.addErrorAlert(errorResponse.message, errorResponse.key, errorResponse.params);
        });
        this.httpErrorListener = eventManager.subscribe('agileMindApp.httpError', function (response) {
            var httpErrorResponse = response.content;
            switch (httpErrorResponse.status) {
                // connection refused, server not reachable
                case 0:
                    _this.addErrorAlert('Server not reachable', 'error.server.not.reachable');
                    break;
                case 400: {
                    var arr = httpErrorResponse.headers.keys();
                    var errorHeader_1 = null;
                    var entityKey_1 = null;
                    arr.forEach(function (entry) {
                        if (entry.toLowerCase().endsWith('app-error')) {
                            errorHeader_1 = httpErrorResponse.headers.get(entry);
                        }
                        else if (entry.toLowerCase().endsWith('app-params')) {
                            entityKey_1 = httpErrorResponse.headers.get(entry);
                        }
                    });
                    if (errorHeader_1) {
                        var entityName = translateService.instant('global.menu.entities.' + entityKey_1);
                        _this.addErrorAlert(errorHeader_1, errorHeader_1, { entityName: entityName });
                    }
                    else if (httpErrorResponse.error !== '' && httpErrorResponse.error.fieldErrors) {
                        var fieldErrors = httpErrorResponse.error.fieldErrors;
                        for (var _i = 0, fieldErrors_1 = fieldErrors; _i < fieldErrors_1.length; _i++) {
                            var fieldError = fieldErrors_1[_i];
                            if (['Min', 'Max', 'DecimalMin', 'DecimalMax'].includes(fieldError.message)) {
                                fieldError.message = 'Size';
                            }
                            // convert 'something[14].other[4].id' to 'something[].other[].id' so translations can be written to it
                            var convertedField = fieldError.field.replace(/\[\d*\]/g, '[]');
                            var fieldName = translateService.instant('agileMindApp.' + fieldError.objectName + '.' + convertedField);
                            _this.addErrorAlert('Error on field "' + fieldName + '"', 'error.' + fieldError.message, { fieldName: fieldName });
                        }
                    }
                    else if (httpErrorResponse.error !== '' && httpErrorResponse.error.message) {
                        _this.addErrorAlert(httpErrorResponse.error.message, httpErrorResponse.error.message, httpErrorResponse.error.params);
                    }
                    else {
                        _this.addErrorAlert(httpErrorResponse.error);
                    }
                    break;
                }
                case 404:
                    _this.addErrorAlert('Not found', 'error.url.not.found');
                    break;
                default:
                    if (httpErrorResponse.error !== '' && httpErrorResponse.error.message) {
                        _this.addErrorAlert(httpErrorResponse.error.message);
                    }
                    else {
                        _this.addErrorAlert(httpErrorResponse.error);
                    }
            }
        });
    }
    AlertErrorComponent.prototype.setClasses = function (alert) {
        var _a;
        var classes = { 'jhi-toast': Boolean(alert.toast) };
        if (alert.position) {
            return __assign(__assign({}, classes), (_a = {}, _a[alert.position] = true, _a));
        }
        return classes;
    };
    AlertErrorComponent.prototype.ngOnDestroy = function () {
        if (this.errorListener) {
            this.eventManager.destroy(this.errorListener);
        }
        if (this.httpErrorListener) {
            this.eventManager.destroy(this.httpErrorListener);
        }
    };
    AlertErrorComponent.prototype.addErrorAlert = function (message, key, data) {
        message = key && key !== null ? key : message;
        var newAlert = {
            type: 'danger',
            msg: message,
            params: data,
            timeout: 5000,
            toast: this.alertService.isToast(),
            scoped: true,
        };
        this.alerts.push(this.alertService.addAlert(newAlert, this.alerts));
    };
    AlertErrorComponent.prototype.close = function (alert) {
        var _a, _b;
        // NOSONAR can be removed after https://github.com/SonarSource/SonarJS/issues/1930 is resolved
        (_b = (_a = alert).close) === null || _b === void 0 ? void 0 : _b.call(_a, this.alerts); // NOSONAR
    };
    AlertErrorComponent = __decorate([
        core_1.Component({
            selector: 'jhi-alert-error',
            template: " <div class=\"alerts\" role=\"alert\">\n    <div *ngFor=\"let alert of alerts\" [ngClass]=\"setClasses(alert)\">\n      <ngb-alert *ngIf=\"alert && alert.type && alert.msg\" [type]=\"alert.type\" (close)=\"close(alert)\">\n        <pre [innerHTML]=\"alert.msg\"></pre>\n      </ngb-alert>\n    </div>\n  </div>",
        })
    ], AlertErrorComponent);
    return AlertErrorComponent;
}());
exports.AlertErrorComponent = AlertErrorComponent;
