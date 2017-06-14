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
var movie_list_service_1 = require("./../movie-list.service");
var user_data_1 = require("./../model/user-data");
var MovieListDetailComponent = (function () {
    function MovieListDetailComponent(movieListService, route, location, userData) {
        this.movieListService = movieListService;
        this.route = route;
        this.location = location;
        this.userData = userData;
    }
    MovieListDetailComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params
            .switchMap(function (params) { return _this.movieListService.getMovieList(params['id']); })
            .subscribe(function (movielist) { _this.movieList = movielist; });
        if (this.userData.isAdmin()) {
            //admin
            this.isAdmin = true;
        }
        else {
            //user
            this.isAdmin = false;
        }
    };
    MovieListDetailComponent.prototype.deleteMovieFromList = function (movielistId, movieId) {
        var _this = this;
        this.movieListService.deleteMovieFromList(movielistId, movieId).then(function (res) { _this.ngOnInit(); });
    };
    MovieListDetailComponent.prototype.verRankingMovieList = function (idML) {
        var _this = this;
        this.movieListService.getRankingMovieList(idML).then(function (resp) {
            _this.ranking = resp;
        });
    };
    return MovieListDetailComponent;
}());
MovieListDetailComponent = __decorate([
    core_1.Component({
        selector: 'movie-list-detail',
        template: "\n    <div *ngIf=\"movieList\" class=\"center-align\">\n      <div class=\"card-panel teal lighten-2 black-text\">\n        <h2>{{movieList.nombre}} </h2>\n        <span *ngIf=\"isAdmin\">Propietario: {{movieList.ownerId}}</span>\n      </div>\n\n      <button (click)=\"verRankingMovieList(movieList.id)\" class=\"btn waves-effect black-text\">Ver Ranking Actores</button>\n      <li *ngFor=\"let arank of ranking\">\n        <div class=\"row\">\n          <div class=\"card horizontal teal lighten-2\">\n            <div class=\"card-stacked\">\n            <div class=\"card-content\">\n      \t\t\t\t<span class=\"card-title center-align\"><a class=\"blue-text text-darken-4\" [routerLink]=\"['/actor/', arank.movieActor.id]\">{{arank.movieActor.name}}:  {{arank.cantRepeticiones}}</a></span>\n            </div>\n            </div>\n          </div>\n        </div>\n      </li>\n      <div class=\"card-panel teal lighten-2 black-text\">\n        <h3>Peliculas:</h3>\n        <div class=\"container\">\n        <table class=\"centered\">\n        <thead>\n        <tr>\n          <th>Nombre</th>\n          <th>A\u00F1o</th>\n        </tr>\n        </thead>\n        <tbody>\n        <tr *ngFor=\"let pelicula of movieList.listaPeliculas\">\n          <td><a class=\"blue-text text-darken-4\" [routerLink]=\"['/pelicula', pelicula.id]\">{{pelicula.nombre}}</a></td>\n          <td>{{pelicula.anioEstreno}}</td>\n          <a button (click)=\"deleteMovieFromList(movieList.id, pelicula.id)\" class=\"btn-floating waves-effect waves-light red\"><i class=\"material-icons\">-</i></a>\n          </tr>\n        </tbody>\n        </table>\n        </div>\n      </div>\n\n    </div>\n  "
    }),
    __metadata("design:paramtypes", [movie_list_service_1.MovieListService, router_1.ActivatedRoute, common_1.Location, user_data_1.UserData])
], MovieListDetailComponent);
exports.MovieListDetailComponent = MovieListDetailComponent;
//# sourceMappingURL=movie-list-detail.component.js.map