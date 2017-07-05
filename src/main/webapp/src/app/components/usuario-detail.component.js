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
var movie_list_service_1 = require("../movie-list.service");
var UsuarioDetailComponent = (function () {
    function UsuarioDetailComponent(usuarioService, route, location, router, movieListService) {
        this.usuarioService = usuarioService;
        this.route = route;
        this.location = location;
        this.router = router;
        this.movieListService = movieListService;
    }
    UsuarioDetailComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params
            .switchMap(function (params) { return _this.usuarioService.getUsuario(params['id']); })
            .subscribe(function (usuario) {
            _this.usuario = usuario;
            _this.movieListService.getMovieListsByUser(usuario.id).then(function (resp) { return _this.usuario.listaMovieList = resp; });
        });
    };
    UsuarioDetailComponent.prototype.goBack = function () {
        this.location.back();
    };
    UsuarioDetailComponent.prototype.volver = function () {
        this.router.navigate(['/listaUsuarios']);
    };
    return UsuarioDetailComponent;
}());
UsuarioDetailComponent = __decorate([
    core_1.Component({
        selector: 'usuario-detail',
        template: "\n    <div *ngIf=\"usuario\">\n            <h2>{{usuario.name}} Detalle de {{usuario.username}}</h2>\n        <div>\n            <label>id: </label>{{usuario.id}}\n        </div>\n\n        <div>\n            <label>Nombre: </label> {{usuario.username}}\n        </div>\n\n        <div>\n            <label> Cantidad de Actores Favoritos: </label> {{usuario.actoresFavoritos.length}}\n        </div>\n        \n        <div>\n        \t<label>Cantidad de Listas: </label> {{usuario.listaMovieList.length}}\n        </div>\n        <P>\n        <span>Listas: </span> \n        <div>\t\n            <ul *ngFor=\"let movieList of usuario.listaMovieList\">\n            <span>+{{movieList.nombre}}</span>\n                <li *ngFor=\"let pelicula of movieList.listaPeliculas\">\n                <span>---{{pelicula.nombre}}</span>\n                </li>            \n            </ul>\n        </div>\n        \n        <BR>\n       \n        <div>\n        \t<label> Ultima Sesion: </label> {{usuario.ultimaSesion | date:'medium' }}\n        </div>\n    \n        <BR>\n        <button (click)=\"volver()\">Volver</button>\n    </div>\n    ",
        styles: ["\n\n    "]
    }),
    __metadata("design:paramtypes", [usuario_service_1.UsuarioService,
        router_1.ActivatedRoute,
        common_1.Location,
        router_1.Router,
        movie_list_service_1.MovieListService])
], UsuarioDetailComponent);
exports.UsuarioDetailComponent = UsuarioDetailComponent;
//# sourceMappingURL=usuario-detail.component.js.map