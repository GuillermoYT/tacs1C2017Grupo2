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
var usuario_service_1 = require("./usuario.service");
var user_data_1 = require("./model/user-data");
var ActorService = (function () {
    function ActorService(http, usuarioService, userData) {
        this.http = http;
        this.usuarioService = usuarioService;
        this.userData = userData;
        this.baseUrl = 'http://ruta-rest-tacs.7e14.starter-us-west-2.openshiftapps.com';
    }
    ActorService.prototype.getActor = function (id) {
        var url = this.baseUrl + ("/actores/" + id);
        return this.http.get(url)
            .toPromise()
            .then(function (response) {
            return response.json();
        })
            .catch(this.handleError);
    };
    ActorService.prototype.getActorByString = function (query) {
        var url = this.baseUrl + ("/actores?query=" + query);
        return this.http.get(url)
            .toPromise()
            .then(function (response) { return response.json(); })
            .catch(this.handleError);
    };
    ActorService.prototype.getActoresFavoritos = function () {
        var headers = new http_1.Headers({ 'Authorization': 'Bearer ' + this.userData.getToken() });
        var url = this.baseUrl + "/actores/rankingFavoritos";
        return this.http.get(url, { headers: headers })
            .toPromise()
            .then(function (response) { return response.json(); })
            .catch(this.handleError);
    };
    ActorService.prototype.handleError = function (error) {
        console.error('Error retrieving movies', error);
        console.log('Error es ' + error);
        return Promise.reject(error.message || error);
    };
    return ActorService;
}());
ActorService = __decorate([
    core_1.Injectable(),
    __metadata("design:paramtypes", [http_1.Http, usuario_service_1.UsuarioService, user_data_1.UserData])
], ActorService);
exports.ActorService = ActorService;
//# sourceMappingURL=actor.service.js.map