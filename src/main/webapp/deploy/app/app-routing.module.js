"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var router_1 = require("@angular/router");
var movie_detail_component_1 = require("./components/movie-detail.component");
var peliculas_component_1 = require("./components/peliculas.component");
var actor_component_1 = require("./components/actor.component");
var login_component_1 = require("./components/login.component");
var register_component_1 = require("./components/register.component");
var movie_list_component_1 = require("./components/movie-list.component");
var movie_list_detail_component_1 = require("./components/movie-list-detail.component");
var usuarios_component_1 = require("./components/usuarios.component");
var usuario_detail_component_1 = require("./components/usuario-detail.component");
var ranking_favoritos_component_1 = require("./components/ranking-favoritos.component");
var routes = [
    {
        path: '',
        component: peliculas_component_1.PeliculasComponent
    },
    {
        path: 'login',
        component: login_component_1.LoginComponent
    },
    {
        path: 'register',
        component: register_component_1.RegisterComponent
    },
    {
        path: 'listaPeliculas',
        component: peliculas_component_1.PeliculasComponent
    },
    {
        path: 'pelicula/:id',
        component: movie_detail_component_1.MovieDetailComponent
    },
    {
        path: 'actor/:id',
        component: actor_component_1.ActorComponent
    },
    {
        path: 'misListas',
        component: movie_list_component_1.MovieListComponent
    },
    {
        path: 'movielists/:id',
        component: movie_list_detail_component_1.MovieListDetailComponent
    },
    {
        path: 'listaUsuarios',
        component: usuarios_component_1.UsuariosComponent
    },
    {
        path: 'detail/:id',
        component: usuario_detail_component_1.UsuarioDetailComponent
    },
    {
        path: 'actoresFavoritos',
        component: ranking_favoritos_component_1.RankingFavoritosComponent
    }
];
var AppRoutingModule = (function () {
    function AppRoutingModule() {
    }
    return AppRoutingModule;
}());
AppRoutingModule = __decorate([
    core_1.NgModule({
        imports: [router_1.RouterModule.forRoot(routes)],
        exports: [router_1.RouterModule]
    })
], AppRoutingModule);
exports.AppRoutingModule = AppRoutingModule;
//# sourceMappingURL=app-routing.module.js.map