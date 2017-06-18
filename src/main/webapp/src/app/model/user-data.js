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
var UserData = (function () {
    function UserData() {
        this.setId('0');
    }
    UserData.prototype.setToken = function (unString) {
        this.token = unString;
    };
    UserData.prototype.setUsername = function (unNombre) {
        this.username = unNombre;
    };
    UserData.prototype.setId = function (numero) {
        this.id = numero;
    };
    UserData.prototype.setAdmin = function (valor) {
        this.admin = valor;
    };
    UserData.prototype.getToken = function () {
        return this.token;
    };
    UserData.prototype.getUsername = function () {
        return this.username;
    };
    UserData.prototype.getId = function () {
        return this.id;
    };
    UserData.prototype.isAdmin = function () {
        return this.admin;
    };
    UserData.prototype.clear = function () {
        this.token = null;
        this.username = null;
        this.admin = false;
        this.id = '0';
    };
    return UserData;
}());
UserData = __decorate([
    core_1.Injectable(),
    __metadata("design:paramtypes", [])
], UserData);
exports.UserData = UserData;
//# sourceMappingURL=user-data.js.map