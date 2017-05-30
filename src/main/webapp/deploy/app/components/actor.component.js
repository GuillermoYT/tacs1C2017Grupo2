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
var actor_service_1 = require("./../actor.service");
var usuario_service_1 = require("./../usuario.service");
var ActorComponent = (function () {
    function ActorComponent(usuarioService, actorService, route, location) {
        this.usuarioService = usuarioService;
        this.actorService = actorService;
        this.route = route;
        this.location = location;
        this.imgUrl = "./app/image/star-off.png";
    }
    ActorComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params
            .switchMap(function (params) { return _this.actorService.getActor(+params['id']); })
            .subscribe(function (actorRes) {
            _this.actor = actorRes;
            _this.usuarioService.actorFavorito(actorRes.id).then(function (resp) {
                console.log("se recibe como respuesta: " + resp.bool);
                _this.favorite(!resp.bool, actorRes.id);
                console.log("id del actor: " + actorRes.id + "is favorite debe ser: " + resp.bool);
            });
        });
    };
    ActorComponent.prototype.favorite = function (isFav, id) {
        if (this.isFavorite == null) {
            (isFav) ? this.imgUrl = "./app/image/star-off.png" : this.imgUrl = "./app/image/star-on.png";
            console.log("cargar var in null " + this.isFavorite + " Resp service: " + isFav);
        }
        else {
            if (isFav) {
                this.imgUrl = "./app/image/star-off.png";
                this.usuarioService.desmarcarFavorito(id);
                console.log("desmarcar " + isFav + " actor con id: " + id);
            }
            else {
                this.imgUrl = "./app/image/star-on.png";
                this.usuarioService.marcarFavorito(id);
                console.log("marcar " + isFav + " actor con id: " + id);
            }
        }
        this.isFavorite = !isFav;
    };
    return ActorComponent;
}());
ActorComponent = __decorate([
    core_1.Component({
        selector: 'actor',
        template: "\n    <div *ngIf=\"actor\" class=\"center-align\">\n      <div class=\"card-panel teal lighten-2 black-text\">\n        <img (click)=\"favorite(isFavorite, actor.id)\" [src]=\"imgUrl\" style=\"cursor: pointer; float: right;\"/>\n        <h2>{{actor.nombre}}</h2>\n        <img src=\"{{actor.imagePath}}\"/><br/>\n        <h4>Lugar y fecha de nacimiento: {{actor.fechaNac}} en {{actor.lugarNac}}</h4><br/>\n        <h4>Biografia:</h4><br/>\n        <span>{{actor.biography}}</span>\n      </div>\n\n      <div class=\"card-panel teal lighten-2 black-text\">\n        <h3>Peliculas en las que aparece:</h3>\n        <div class=\"container\">\n        <table class=\"centered highlight\">\n        <thead>\n        <tr>\n          <th>Pelicula</th>\n          <th>Personaje</th>\n        </tr>\n        </thead>\n        <tbody>\n        <tr *ngFor=\"let pelicula of actor.listaPeliculas\">\n          <td><a class=\"blue-text text-darken-4\" [routerLink]=\"['/pelicula', pelicula.id]\">{{pelicula.original_title}}</a></td>\n          <td>{{pelicula.character}}</td>\n        </tr>\n        </tbody>\n        </table>\n        </div>\n      </div>\n    </div>\n  "
    }),
    __metadata("design:paramtypes", [usuario_service_1.UsuarioService, actor_service_1.ActorService, router_1.ActivatedRoute, common_1.Location])
], ActorComponent);
exports.ActorComponent = ActorComponent;
//# sourceMappingURL=actor.component.js.map