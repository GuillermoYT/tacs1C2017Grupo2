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
var UserService = (function () {
    function UserService(http) {
        this.http = http;
        this.resp = false;
    }
    UserService.prototype.actorFavorito = function (idActor) {
        var _this = this;
        var url = "http://localhost:8080/usuarios/2/actorFavorito/" + idActor;
        this.http.get(url).
            subscribe(function (response) {
            _this.resp = response.json();
            console.log(response.json());
        });
        return this.resp;
    };
    UserService.prototype.marcarFavorito = function (idActor) {
        var headers = new http_1.Headers;
        headers.append('Content-Type', 'application/json');
        var url = "http://localhost:8080/usuarios/2/favorito/" + idActor;
        this.http.put(url, "");
        console.log("macarFavorito: " + idActor);
    };
    UserService.prototype.handleError = function (error) {
        console.error('Error retrieving favorite', error);
        return Promise.reject(error.message || error);
    };
    return UserService;
}());
UserService = __decorate([
    core_1.Injectable(),
    __metadata("design:paramtypes", [http_1.Http])
], UserService);
exports.UserService = UserService;
//# sourceMappingURL=user.service.js.map