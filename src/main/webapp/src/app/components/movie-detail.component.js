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
var pelicula_service_1 = require("./../pelicula.service");
var MovieDetailComponent = (function () {
    function MovieDetailComponent(peliculaService, route, location) {
        this.peliculaService = peliculaService;
        this.route = route;
        this.location = location;
    }
    MovieDetailComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params
            .switchMap(function (params) { return _this.peliculaService.getMovie(+params['id']); })
            .subscribe(function (pelicula) { _this.movie = pelicula; console.log("Pelicula: " + JSON.stringify(_this.movie)); });
        //    this.route.params.switchMap((params: Params) => { this.parametros = (+params['id']) });
    };
    return MovieDetailComponent;
}());
MovieDetailComponent = __decorate([
    core_1.Component({
        selector: 'movie-detail',
        template: "\n    <div *ngIf=\"movie\">\n      <h2>{{movie.nombre}} ({{movie.anioEstreno}})</h2>\n      <img src=\"{{movie.imagePath}}\"/><br/>\n      <span>{{movie.descripcion}}</span>\n\n      <h3>Actores:</h3>\n      <ul class=\"actores\">\n        <li *ngFor=\"let actor of movie.cast\">\n          <span>{{actor.name}} en el personaje de {{actor.character}}</span>\n        </li>\n      </ul>\n\n      <h3>Rese\u00F1as:</h3>\n      <ul class=\"resenas\">\n      <li *ngFor=\"let review of movie.reviews\">\n        <h5>{{review.author}} dice:</h5>\n        <span>{{review.content}}</span>\n      </li>\n      </ul>\n    </div>\n  "
    }),
    __metadata("design:paramtypes", [pelicula_service_1.PeliculaService, router_1.ActivatedRoute, common_1.Location])
], MovieDetailComponent);
exports.MovieDetailComponent = MovieDetailComponent;
//# sourceMappingURL=movie-detail.component.js.map