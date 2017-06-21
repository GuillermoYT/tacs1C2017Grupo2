import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Location } from '@angular/common';
import 'rxjs/add/operator/switchMap';

import { MovieDetail } from './../model/movie-detail';
import { PeliculaService } from './../pelicula.service';

import { MovieList } from './../model/movie-list';
import { MovieListService } from './../movie-list.service';
import { RankingActor } from './../model/RankingActor';

import { UserData } from './../model/user-data';
import { UsuarioService } from './../usuario.service';


@Component({
  selector:'movie-list-detail',
  template:`
    <div *ngIf="movieList" class="center-align">
      <div class="card-panel teal lighten-2 black-text">
        <h2>{{movieList.nombre}} </h2>
        <span *ngIf="userData.admin">Propietario: {{ownerUsername}}</span>
      </div>

      <button (click)="verRankingMovieList(movieList.id)" class="btn waves-effect black-text">Ver Ranking Actores</button>
      <li *ngFor="let arank of ranking">
        <div class="row">
          <div class="card horizontal teal lighten-2">
            <div class="card-stacked">
            <div class="card-content">
      				<span class="card-title center-align"><a class="blue-text text-darken-4" [routerLink]="['/actor/', arank.movieActor.id]">{{arank.movieActor.name}}:  {{arank.cantRepeticiones}}</a></span>
            </div>
            </div>
          </div>
        </div>
      </li>
      <div class="card-panel teal lighten-2 black-text">
        <h3>Peliculas:</h3>
        <div class="container">
        <table class="centered">
        <thead>
        <tr>
          <th>Nombre</th>
          <th>Año</th>
        </tr>
        </thead>
        <tbody>
        <tr *ngFor="let pelicula of movieList.listaPeliculas">
          <td><a class="blue-text text-darken-4" [routerLink]="['/pelicula', pelicula.id]">{{pelicula.nombre}}</a></td>
          <td>{{pelicula.anioEstreno}}</td>
          <a button (click)="deleteMovieFromList(movieList.id, pelicula.id)" class="btn-floating waves-effect waves-light red"><i class="material-icons">-</i></a>
          </tr>
        </tbody>
        </table>
        </div>
      </div>

    </div>
  `
})

export class MovieListDetailComponent implements OnInit {
  movieList: MovieList;
  ranking: RankingActor[];
  ownerUsername: string;

  ngOnInit(): void {
	this.ownerUsername="";
    this.route.params
      .switchMap((params: Params) => this.movieListService.getMovieList(params['id']))
      .subscribe(movielist => {
    	  this.movieList = movielist;
    	  this.usuarioService.getUsuario(movielist.ownerId).then(resp => this.ownerUsername=resp.username);
      });
  }

  deleteMovieFromList(movielistId: string, movieId: number){
	  this.movieListService.deleteMovieFromList(movielistId, movieId).then(res => {this.ngOnInit()});
  }

	verRankingMovieList(idML: string){
		this.movieListService.getRankingMovieList(idML).then(resp => {this.ranking = resp;
		});
	}


  constructor(
		  private movieListService: MovieListService, 
		  private route: ActivatedRoute, 
		  private location: Location, 
		  private userData: UserData,
		  private usuarioService: UsuarioService) {}
}
