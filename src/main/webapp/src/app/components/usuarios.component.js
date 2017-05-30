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
var usuario_service_1 = require(".././usuario.service");
var UsuariosComponent = (function () {
    function UsuariosComponent(usuarioService, router) {
        this.usuarioService = usuarioService;
        this.router = router;
    }
    UsuariosComponent.prototype.getUsers = function () {
        var _this = this;
        this.usuarioService
            .getUsuarios()
            .then(function (usuarios) { return _this.usuarios = usuarios; });
    };
    UsuariosComponent.prototype.onSelect = function (usuario) {
        this.selectedUsuario = usuario;
    };
    UsuariosComponent.prototype.verDetalle = function () {
        this.router.navigate(['/detail', this.selectedUsuario.id]);
    };
    UsuariosComponent.prototype.ngOnInit = function () {
        this.getUsers();
    };
    return UsuariosComponent;
}());
UsuariosComponent = __decorate([
    core_1.Component({
        selector: 'listaUsuarios',
        template: "\n    <h2>Usuarios del Sistema</h2>\n    <ul>\n        <li *ngFor=\"let usuario of usuarios\" (click)=\"onSelect(usuario)\"\n      [class.selected]=\"usuario === selectedUsuario\">\n        <span>{{usuario.username}}</span>\n        </li>\n    </ul>\n    <div *ngIf=\"selectedUsuario\">\n        <h2>\n        Usuario {{selectedUsuario.username | uppercase}}\n        </h2>\n        <button (click)=\"verDetalle()\">Ver Detalle</button>\n    </div>\n    ",
        styles: ["\n    .selected {\n    background-color: #CFD8DC !important;\n    color: white;\n    }\n    "]
    }),
    __metadata("design:paramtypes", [usuario_service_1.UsuarioService,
        router_1.Router])
], UsuariosComponent);
exports.UsuariosComponent = UsuariosComponent;
//# sourceMappingURL=usuarios.component.js.map