"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require("@angular/core");
var router_1 = require("@angular/router");
var common_1 = require("@angular/common");
require("rxjs/add/operator/switchMap");
var usuario_service_1 = require("./../usuario.service");
var alert_service_1 = require("../alert.service");
var user_data_1 = require("./../model/user-data");
var LoginComponent = (function () {
    function LoginComponent(usuarioService, alertService, route, location, router, userData) {
        this.usuarioService = usuarioService;
        this.alertService = alertService;
        this.route = route;
        this.location = location;
        this.router = router;
        this.userData = userData;
    }
    LoginComponent.prototype.doLogin = function () {
        var _this = this;
        this.route.params
            .switchMap(function (params) { return _this.usuarioService.authenticate(_this.username, _this.password); })
            .subscribe(function (result) {
            if (result) {
                _this.alertService.success("Logueo Satisfactorio");
                _this.userData.setUsername(_this.username);
                _this.router.navigate(["listaPeliculas"]);
            }
            else {
                _this.error = "Usuario o contraseña incorrecta.";
            }
        }, function (error) { return _this.error = "Usuario o contraseña incorrecta."; });
    };
    return LoginComponent;
}());
LoginComponent = __decorate([
    core_1.Component({
        selector: 'my-login',
        templateUrl: './partials/login.component.html'
    }),
    __metadata("design:paramtypes", [usuario_service_1.UsuarioService,
        alert_service_1.AlertService,
        router_1.ActivatedRoute,
        common_1.Location,
        router_1.Router,
        user_data_1.UserData])
], LoginComponent);
exports.LoginComponent = LoginComponent;
//# sourceMappingURL=login.component.js.map