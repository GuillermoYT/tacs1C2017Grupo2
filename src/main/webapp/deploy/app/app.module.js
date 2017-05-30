"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var platform_browser_1 = require("@angular/platform-browser");
var forms_1 = require("@angular/forms");
var http_1 = require("@angular/http");
var app_routing_module_1 = require("./app-routing.module");
var pelicula_service_1 = require("./pelicula.service");
var actor_service_1 = require("./actor.service");
var app_component_1 = require("./app.component");
var movie_detail_component_1 = require("./components/movie-detail.component");
var peliculas_component_1 = require("./components/peliculas.component");
var actor_component_1 = require("./components/actor.component");
var movie_list_component_1 = require("./components/movie-list.component");
var movie_list_service_1 = require("./movie-list.service");
var movie_list_detail_component_1 = require("./components/movie-list-detail.component");
var login_component_1 = require("./components/login.component");
var register_component_1 = require("./components/register.component");
var alert_component_1 = require("./components/alert.component");
var alert_service_1 = require("./alert.service");
var usuarios_component_1 = require("./components/usuarios.component");
var usuario_service_1 = require("./usuario.service");
var usuario_detail_component_1 = require("./components/usuario-detail.component");
var ranking_favoritos_component_1 = require("./components/ranking-favoritos.component");
var user_data_1 = require("./model/user-data");
var AppModule = (function () {
    function AppModule() {
    }
    return AppModule;
}());
AppModule = __decorate([
    core_1.NgModule({
        imports: [
            platform_browser_1.BrowserModule,
            forms_1.FormsModule,
            http_1.HttpModule,
            app_routing_module_1.AppRoutingModule
        ],
        declarations: [
            app_component_1.AppComponent,
            login_component_1.LoginComponent,
            register_component_1.RegisterComponent,
            alert_component_1.AlertComponent,
            movie_detail_component_1.MovieDetailComponent,
            peliculas_component_1.PeliculasComponent,
            actor_component_1.ActorComponent,
            movie_list_component_1.MovieListComponent,
            movie_list_detail_component_1.MovieListDetailComponent,
            usuarios_component_1.UsuariosComponent,
            usuario_detail_component_1.UsuarioDetailComponent,
            ranking_favoritos_component_1.RankingFavoritosComponent
        ],
        providers: [
            pelicula_service_1.PeliculaService,
            actor_service_1.ActorService,
            usuario_service_1.UsuarioService,
            movie_list_service_1.MovieListService,
            alert_service_1.AlertService,
            user_data_1.UserData
        ],
        bootstrap: [app_component_1.AppComponent]
    })
], AppModule);
exports.AppModule = AppModule;
//# sourceMappingURL=app.module.js.map