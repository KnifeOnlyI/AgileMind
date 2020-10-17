"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var LoginModalComponent = /** @class */ (function () {
    function LoginModalComponent(loginService, router, activeModal, fb) {
        this.loginService = loginService;
        this.router = router;
        this.activeModal = activeModal;
        this.fb = fb;
        this.authenticationError = false;
        this.loginForm = this.fb.group({
            username: [''],
            password: [''],
            rememberMe: [false],
        });
    }
    LoginModalComponent.prototype.ngAfterViewInit = function () {
        if (this.username) {
            this.username.nativeElement.focus();
        }
    };
    LoginModalComponent.prototype.cancel = function () {
        this.authenticationError = false;
        this.loginForm.patchValue({
            username: '',
            password: '',
        });
        this.activeModal.dismiss('cancel');
    };
    LoginModalComponent.prototype.login = function () {
        var _this = this;
        this.loginService
            .login({
            username: this.loginForm.get('username').value,
            password: this.loginForm.get('password').value,
            rememberMe: this.loginForm.get('rememberMe').value,
        })
            .subscribe(function () {
            _this.authenticationError = false;
            _this.activeModal.close();
            if (_this.router.url === '/account/register' ||
                _this.router.url.startsWith('/account/activate') ||
                _this.router.url.startsWith('/account/reset/')) {
                _this.router.navigate(['']);
            }
        }, function () { return (_this.authenticationError = true); });
    };
    LoginModalComponent.prototype.register = function () {
        this.activeModal.dismiss('to state register');
        this.router.navigate(['/account/register']);
    };
    LoginModalComponent.prototype.requestResetPassword = function () {
        this.activeModal.dismiss('to state requestReset');
        this.router.navigate(['/account/reset', 'request']);
    };
    __decorate([
        core_1.ViewChild('username', { static: false })
    ], LoginModalComponent.prototype, "username", void 0);
    LoginModalComponent = __decorate([
        core_1.Component({
            selector: 'jhi-login-modal',
            templateUrl: './login.component.html',
        })
    ], LoginModalComponent);
    return LoginModalComponent;
}());
exports.LoginModalComponent = LoginModalComponent;
