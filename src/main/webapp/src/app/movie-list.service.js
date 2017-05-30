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
var http_1 = require("@angular/http");
require("rxjs/add/operator/toPromise");
require("rxjs/add/operator/catch");
require("rxjs/add/operator/map");
var MovieListService = (function () {
    function MovieListService(http) {
        this.http = http;
        this.baseUrl = 'http://ruta-rest-tacs.7e14.starter-us-west-2.openshiftapps.com';
        this.results = [];
        this.loading = false;
    }
    MovieListService.prototype.createMovieList = function (nombre, user) {
        var headers = new http_1.Headers({ 'Content-Type': 'application/json;charset=utf-8' });
        var options = new http_1.RequestOptions({ headers: headers });
        var url = this.baseUrl + ("/movielists?user=" + user);
        this.http.post(url, nombre, options)
            .toPromise()
            .catch(this.handleError);
    };
    MovieListService.prototype.addMovieToList = function (idList, idMovie) {
        var headers = new http_1.Headers({ 'Content-Type': 'application/json;charset=utf-8' });
        var options = new http_1.RequestOptions({ headers: headers });
        var url = this.baseUrl + ("/movielists/" + idList + "/" + idMovie);
        this.http.put(url, options)
            .toPromise()
            .catch(this.handleError);
    };
    MovieListService.prototype.deleteMovieFromList = function (movielistId, movieId) {
        var headers = new http_1.Headers({ 'Content-Type': 'application/json' });
        var url = this.baseUrl + ("/movielists/" + movielistId);
        var body = JSON.stringify({
            ids: [movieId]
        });
        var options = new http_1.RequestOptions({ headers: headers, body: body });
        return this.http.delete(url, options).toPromise().then(function (res) { return res.status; }).catch(this.handleError);
    };
    //devuelve una movielist por id
    MovieListService.prototype.getMovieList = function (id) {
        var url = this.baseUrl + ("/movielists/" + id);
        return this.http.get(url)
            .toPromise()
            .then(function (response) { return response.json(); })
            .catch(this.handleError);
    };
    //devuelve todas las movielists
    MovieListService.prototype.getMovieLists = function () {
        return this.http.get(this.baseUrl + '/movielists')
            .toPromise()
            .then(function (response) { return response.json(); })
            .catch(this.handleError);
    };
    //devuelve las movielists de un user
    MovieListService.prototype.getMovieListsByUser = function (ownerId) {
        var url = this.baseUrl + ("/movielists/search?ownerId=" + ownerId);
        return this.http.get(url)
            .toPromise()
            .then(function (response) { return response.json(); })
            .catch(this.handleError);
    };
    //devuelve los actores favoritos de un user
    MovieListService.prototype.getActoresFavoritos = function (userId) {
        var url = this.baseUrl + ("/usuarios/" + userId + "/actoresFavoritos");
        return this.http.get(url)
            .toPromise()
            .then(function (response) { return response.json(); })
            .catch(this.handleError);
    };
    //devuelve las peliculas con mas de un actor favorito de un user
    MovieListService.prototype.getPeliculasVariosActoresFavoritos = function (userId) {
        var url = this.baseUrl + ("/peliculas/actoresFavoritos/" + userId);
        return this.http.get(url)
            .toPromise()
            .then(function (response) { return response.json(); })
            .catch(this.handleError);
    };
    //devuelve la interseccion de peliculas entre dos listas
    MovieListService.prototype.getInterseccion = function (list1, list2) {
        var _this = this;
        var promise = new Promise(function (resolve, reject) {
            var url = _this.baseUrl + ("/movielists/compare?list1=" + list1 + "&list2=" + list2);
            _this.http.get(url)
                .toPromise()
                .then(function (res) {
                _this.results = res.json();
                resolve();
            }, function (msg) {
                reject(msg);
            });
        });
        return promise;
    };
    MovieListService.prototype.getRankingMovieList = function (idML) {
        var url = this.baseUrl + ("/movielists/actoresRepetidos/" + idML);
        return this.http.get(url).toPromise().then(function (res) { return res.json(); })
            .catch(this.handleError);
    };
    MovieListService.prototype.handleError = function (error) {
        var errMsg;
        if (error instanceof http_1.Response) {
            var body = error.json() || '';
            var err = body.error || JSON.stringify(body);
            errMsg = error.status + " - " + (error.statusText || '') + " " + err;
        }
        else {
            errMsg = error.message ? error.message : error.toString();
        }
        console.error(errMsg);
        return Promise.reject(errMsg);
    };
    return MovieListService;
}());
MovieListService = __decorate([
    core_1.Injectable(),
    __metadata("design:paramtypes", [http_1.Http])
], MovieListService);
exports.MovieListService = MovieListService;
//# sourceMappingURL=movie-list.service.js.map