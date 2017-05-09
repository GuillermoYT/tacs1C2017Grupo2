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
require("rxjs/add/operator/switchMap");
var core_1 = require("@angular/core");
var router_1 = require("@angular/router");
var common_1 = require("@angular/common");
var usuario_service_1 = require("../usuario.service");
var UsuarioDetailComponent = (function () {
    function UsuarioDetailComponent(usuarioService, route, location) {
        this.usuarioService = usuarioService;
        this.route = route;
        this.location = location;
    }
    UsuarioDetailComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params
            .switchMap(function (params) { return _this.usuarioService.getHero(+params['id']); })
            .subscribe(function (usuario) { return _this.usuario = usuario; });
        this.route.params
            .switchMap(function (params) { return _this.usuarioService.getFavoritos(+params['id']); })
            .subscribe(function (listaFavoritos) { return _this.listaFavoritos = listaFavoritos; });
    };
    UsuarioDetailComponent.prototype.goBack = function () {
        this.location.back();
    };
    return UsuarioDetailComponent;
}());
UsuarioDetailComponent = __decorate([
    core_1.Component({
        selector: 'usuario-detail',
        template: "\n    <div *ngIf=\"usuario\">\n            <h2>{{usuario.name}} Detalle de {{usuario.username}}</h2>\n        <div>\n            <label>id: </label>{{usuario.id}}\n        </div>\n        \n        <div>\n            <label>Nombre: </label> {{usuario.username}}\n        </div>\n        <div>\n            <label>Cantidad de Listas: </label> {{usuario.cantidad}}\n        </div>\n            <div *ngFor=\"let SummaryActor of listaFavoritos\">\n               <span>{{SummaryActor.nombre}}</span> \n            </div>\n    </div>\n    ",
        styles: ["\n\n    "]
    }),
    __metadata("design:paramtypes", [usuario_service_1.UsuarioService,
        router_1.ActivatedRoute,
        common_1.Location])
], UsuarioDetailComponent);
exports.UsuarioDetailComponent = UsuarioDetailComponent;
//# sourceMappingURL=usuario-detail.component.js.map