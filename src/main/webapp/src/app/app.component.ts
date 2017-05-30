import { Component } from '@angular/core';
import { PeliculasComponent } from './components/peliculas.component';
import { MovieDetailComponent} from './components/movie-detail.component';
import { PeliculaService } from './pelicula.service';
import { UsuarioService } from './usuario.service';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Observable }     from 'rxjs/Observable';
import { HttpModule} from '@angular/http';
import { UserData } from './model/user-data';

@Component({
  selector: 'my-app',
  template: `
    <div class="navbar-fixed">
    <nav class="teal lighten-2">
    <div class="nav-wrapper">
       <a href="#" class="brand-logo black-text right" style="margin-right:10%">{{userData.getUsername()}} - Show Must Go On</a>
       <ul id="nav-mobile" class="left hide-on-med-and-down" style="margin-left:5%;">
         <li><a class="black-text" routerLink="/">Inicio</a></li>
         <li *ngIf="userData.getUsername()"><a class="black-text" routerLink="/misListas">Mis Listas</a></li>
         <li *ngIf="userData.isAdmin()"><a class="black-text" routerLink="/listaUsuarios">Usuarios</a></li>
         <li *ngIf="userData.isAdmin()"><a class="black-text" routerLink="/actoresFavoritos">Ranking de actores favoritos</a></li>
         <li *ngIf="!userData.getUsername()"><a class="black-text" routerLink="/login">Iniciar sesion</a></li>
         <li *ngIf="userData.getUsername()"><a class="black-text" (click)="doLogout()">Cerrar sesión</a></li>
       </ul>
    </div>
    </nav>
    </div>
    <router-outlet></router-outlet>
  `
})
export class AppComponent {
  name: string;
    ngOnInit() {
      this.name = this.userData.getUsername();
    }

    doLogout(): void {
      this.userData.clear();
      this.router.navigate(["listaPeliculas"]);
    }

    constructor(private us : UsuarioService, private route: ActivatedRoute, private userData: UserData, private router: Router){}
}
