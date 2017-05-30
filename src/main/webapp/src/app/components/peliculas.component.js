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
var pelicula_service_1 = require("./../pelicula.service");
var actor_service_1 = require("./../actor.service");
var movie_list_service_1 = require("./../movie-list.service");
var user_data_1 = require("./../model/user-data");
var PeliculasComponent = (function () {
    function PeliculasComponent(peliculaService, actorService, movieListService, userData) {
        this.peliculaService = peliculaService;
        this.actorService = actorService;
        this.movieListService = movieListService;
        this.userData = userData;
    }
    PeliculasComponent.prototype.getPeliculas = function () {
        var _this = this;
        this.peliculaService.getMovies().then(function (movies) { _this.movies = movies; });
    };
    PeliculasComponent.prototype.searchClick = function () {
        var _this = this;
        if (this.queBuscarRadio == 1) {
            this.peliculaService.getMoviesByString(this.searchString).then(function (movies) { _this.movies = movies; _this.actores = null; });
        }
        else if (this.queBuscarRadio == 2) {
            this.actorService.getActorByString(this.searchString).then(function (actores) { _this.actores = actores; _this.movies = null; });
        }
        else {
            this.peliculaService.getMoviesByString(this.searchString).then(function (movies) { return _this.movies = movies; });
            this.actorService.getActorByString(this.searchString).then(function (actores) { _this.actores = actores; });
        }
    };
    //muestra las listas de un usuario
    PeliculasComponent.prototype.verListas = function () {
        var _this = this;
        this.movieListService.getMovieListsByUser(this.userData.getId()).then(function (movieLists) { _this.movieLists = movieLists; });
    };
    PeliculasComponent.prototype.addToMovieList = function (idMovieList, idMovie) {
        this.movieListService.addMovieToList(idMovieList, idMovie);
        //Materialize.toast('Pelicula agregada', 2000) // 4000 is the duration of the toast
    };
    PeliculasComponent.prototype.searchReset = function () {
        this.movies = this.baseMovies;
        this.actores = null;
        this.searchString = "";
    };
    PeliculasComponent.prototype.ngOnInit = function () {
        this.getPeliculas();
        if (this.userData.getId() > 0) {
            this.verListas();
        }
        else {
            console.log('inicie sesion para ver');
        }
    };
    return PeliculasComponent;
}());
PeliculasComponent = __decorate([
    core_1.Component({
        selector: 'listaPeliculas',
        templateUrl: './partials/peliculas.component.html',
    }),
    __metadata("design:paramtypes", [pelicula_service_1.PeliculaService, actor_service_1.ActorService, movie_list_service_1.MovieListService, user_data_1.UserData])
], PeliculasComponent);
exports.PeliculasComponent = PeliculasComponent;
//# sourceMappingURL=peliculas.component.js.map