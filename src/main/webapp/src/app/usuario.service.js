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
var user_data_1 = require("./model/user-data");
var UsuarioService = (function () {
    function UsuarioService(http, userData) {
        this.http = http;
        this.userData = userData;
        this.baseUrl = 'http://ruta-rest-tacs.7e14.starter-us-west-2.openshiftapps.com';
    }
    UsuarioService.prototype.authenticate = function (username, password) {
        var _this = this;
        var url = this.baseUrl + '/auth';
        var headers = new http_1.Headers;
        headers.append('Content-Type', 'application/json');
        var body = {
            "username": username,
            "password": password
        };
        return this.http.post(url, body, headers)
            .toPromise()
            .then(function (response) {
            var respuesta = response.json() && response.json().token;
            if (respuesta) {
                _this.userData.setToken(respuesta);
                _this.userData.setId(response.json().id);
                _this.userData.setAdmin(response.json().admin);
                return true;
            }
            else {
                return false;
            }
        })
            .catch(this.handleError);
    };
    UsuarioService.prototype.register = function (username, password) {
        var url = this.baseUrl + '/usuarios';
        var headers = new http_1.Headers;
        headers.append('Content-Type', 'application/json');
        var body = {
            "username": username,
            "password": password
        };
        return this.http.post(url, body, headers)
            .toPromise()
            .then(function (response) {
            var respuesta = response.json();
            if (respuesta) {
                return respuesta.status;
            }
            else {
                return 500;
            }
        })
            .catch(this.handleError);
    };
    UsuarioService.prototype.getUsuarios = function () {
        var url = this.baseUrl + "/usuarios";
        return this.http.get(url)
            .toPromise()
            .then(function (response) { return response.json(); })
            .catch(this.handleError);
    };
    UsuarioService.prototype.getUsuario = function (id) {
        var url = this.baseUrl + ("/usuarios/" + id);
        return this.http.get(url)
            .toPromise()
            .then(function (response) { return response.json(); })
            .catch(this.handleError);
    };
    UsuarioService.prototype.getFavoritos = function (id) {
        var url = this.baseUrl + ("/usuarios/" + id + "/actoresFavoritos");
        return this.http.get(url)
            .toPromise()
            .then(function (response) { return response.json(); })
            .catch(this.handleError);
    };
    UsuarioService.prototype.actorFavorito = function (idActor) {
        var url = this.baseUrl + ("/usuarios/" + this.userData.id + "/actorFavorito/" + idActor);
        return this.http.get(url).toPromise()
            .then(function (response) { return response.json(); }).catch(this.handleError);
    };
    UsuarioService.prototype.marcarFavorito = function (idActor) {
        var headers = new http_1.Headers;
        headers.append('Content-Type', 'application/json');
        var url = this.baseUrl + ("/usuarios/" + this.userData.id + "/favorito/" + idActor);
        this.http.put(url, null, headers).toPromise().then()
            .catch(this.handleError);
        console.log("macarFavorito: " + idActor);
    };
    UsuarioService.prototype.desmarcarFavorito = function (idActor) {
        var url = this.baseUrl + ("/usuarios/" + this.userData.id + "/favorito/" + idActor);
        this.http.delete(url).toPromise().then()
            .catch(this.handleError);
        console.log("desmacarFavorito: " + idActor);
    };
    UsuarioService.prototype.handleError = function (error) {
        console.error('Error retrieving usuarios', error);
        return Promise.reject(error.message || error);
    };
    return UsuarioService;
}());
UsuarioService = __decorate([
    core_1.Injectable(),
    __metadata("design:paramtypes", [http_1.Http, user_data_1.UserData])
], UsuarioService);
exports.UsuarioService = UsuarioService;
//# sourceMappingURL=usuario.service.js.map