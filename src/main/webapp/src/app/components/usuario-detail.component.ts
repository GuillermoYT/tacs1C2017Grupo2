import 'rxjs/add/operator/switchMap';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Location } from '@angular/common';

import { UsuarioDetail } from '../model/usuario-detail';
import { UsuarioService } from '../usuario.service';
import { SummaryActor } from '../model/summary-actor';
import { MovieListService } from '../movie-list.service';


@Component({
    selector: 'usuario-detail',
    template: `
    <div *ngIf="usuario">
            <h2>{{usuario.name}} Detalle de {{usuario.username}}</h2>
        <div>
            <label>id: </label>{{usuario.id}}
        </div>

        <div>
            <label>Nombre: </label> {{usuario.username}}
        </div>

        <div>
            <label> Cantidad de Actores Favoritos: </label> {{usuario.actoresFavoritos.length}}
        </div>
        
        <div>
        	<label>Cantidad de Listas: </label> {{usuario.listaMovieList.length}}
        </div>
        <P>
        <span>Listas: </span> 
        <div>	
            <ul *ngFor="let movieList of usuario.listaMovieList">
            <span>+{{movieList.nombre}}</span>
                <li *ngFor="let pelicula of movieList.listaPeliculas">
                <span>---{{pelicula.nombre}}</span>
                </li>            
            </ul>
        </div>
        
        <BR>
       
        <div>
        	<label> Ultima Sesion: </label> {{usuario.ultimaSesion | date:'medium' }}
        </div>
    
        <BR>
        <button (click)="volver()">Volver</button>
    </div>
    `,
    styles: [`

    `]
})

export class UsuarioDetailComponent implements OnInit {
    usuario: UsuarioDetail;

    constructor(
        private usuarioService: UsuarioService,
        private route: ActivatedRoute,
        private location: Location,
        private router: Router,
        private movieListService: MovieListService,
    ) { }

    ngOnInit(): void {
        this.route.params
            .switchMap((params: Params) => this.usuarioService.getUsuario(params['id']))
            .subscribe(usuario => {
            	this.usuario = usuario;
            	this.movieListService.getMovieListsByUser(usuario.id).then(resp => this.usuario.listaMovieList=resp);
            });
    }

    goBack(): void {
        this.location.back();
    }

    volver(): void{
        this.router.navigate(['/listaUsuarios']);
    }
}
