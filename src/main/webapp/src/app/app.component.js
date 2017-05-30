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
var usuario_service_1 = require("./usuario.service");
var router_1 = require("@angular/router");
var user_data_1 = require("./model/user-data");
var AppComponent = (function () {
    function AppComponent(us, route, userData, router) {
        this.us = us;
        this.route = route;
        this.userData = userData;
        this.router = router;
    }
    AppComponent.prototype.ngOnInit = function () {
        this.name = this.userData.getUsername();
    };
    AppComponent.prototype.doLogout = function () {
        this.userData.clear();
        this.router.navigate(["listaPeliculas"]);
    };
    return AppComponent;
}());
AppComponent = __decorate([
    core_1.Component({
        selector: 'my-app',
        template: "\n    <div class=\"navbar-fixed\">\n    <nav class=\"teal lighten-2\">\n    <div class=\"nav-wrapper\">\n       <a href=\"#\" class=\"brand-logo black-text right\" style=\"margin-right:10%\">{{userData.getUsername()}} - Show Must Go On</a>\n       <ul id=\"nav-mobile\" class=\"left hide-on-med-and-down\" style=\"margin-left:5%;\">\n         <li><a class=\"black-text\" routerLink=\"/\">Inicio</a></li>\n         <li *ngIf=\"userData.getUsername()\"><a class=\"black-text\" routerLink=\"/misListas\">Mis Listas</a></li>\n         <li *ngIf=\"userData.isAdmin()\"><a class=\"black-text\" routerLink=\"/listaUsuarios\">Usuarios</a></li>\n         <li *ngIf=\"userData.isAdmin()\"><a class=\"black-text\" routerLink=\"/actoresFavoritos\">Ranking de actores favoritos</a></li>\n         <li *ngIf=\"!userData.getUsername()\"><a class=\"black-text\" routerLink=\"/login\">Iniciar sesion</a></li>\n         <li *ngIf=\"userData.getUsername()\"><a class=\"black-text\" (click)=\"doLogout()\">Cerrar sesi\u00F3n</a></li>\n       </ul>\n    </div>\n    </nav>\n    </div>\n    <router-outlet></router-outlet>\n  "
    }),
    __metadata("design:paramtypes", [usuario_service_1.UsuarioService, router_1.ActivatedRoute, user_data_1.UserData, router_1.Router])
], AppComponent);
exports.AppComponent = AppComponent;
//# sourceMappingURL=app.component.js.map