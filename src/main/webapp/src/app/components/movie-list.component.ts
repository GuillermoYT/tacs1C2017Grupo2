import { Component,OnInit } from '@angular/core';

import { MovieListService } from './../movie-list.service';
import { MovieList } from './../model/movie-list';
import { ActorFavorito } from './../model/actor-favorito';
import { Pelicula } from './../model/pelicula';
import { UserData } from './../model/user-data';

@Component({
  selector: 'movie-list',
  templateUrl: './partials/movie-list.component.html',
})
export class MovieListComponent implements OnInit {
	nombreLista: string;
	idLista1: number;
	idLista2: number;
	movieLists: MovieList[];
	dropDownMovieLists: MovieList[];
	actoresFavoritos: ActorFavorito[];
	peliculasActoresFavoritos: Pelicula[];
	encontro: boolean;

//crear lista de peliculas 
crearClick(): void {
	if(this.userData.getId()>0){
		if(this.nombreLista!=''){
			this.movieListService.createMovieList(this.nombreLista, this.userData.getId());
			this.nombreLista = "Lista creada exitosamente";
			this.movieListService.getMovieListsByUser(this.userData.getId())
			.then(movieLists => {this.dropDownMovieLists = movieLists;});
		}
	}else{
		console.log('inicie sesion para ver');
	}
}

textReset(): void {
  this.nombreLista = "";
}

//movielists de un user o de todos si es admin
verListas(): void {
	this.actoresFavoritos = null;
	this.peliculasActoresFavoritos = null;
	
	if(this.userData.getId()>0){
		if(this.userData.isAdmin()){
		  //admin
		  this.movieListService.getMovieLists()
			.then(movieLists => {if(movieLists.length==0){
									this.encontro=false;
								}else{
									this.movieLists = movieLists;
									this.encontro=true;
								}});
		}else{
			//User
			this.movieListService.getMovieListsByUser(this.userData.getId())
				.then(movieLists => {if(movieLists.length==0){
										this.encontro = false;
									}else{
										this.movieLists = movieLists;
										this.encontro = true;
									}});
		}
	}else{
		console.log('inicie sesion para ver');
	}

}

//actores favoritos del usuario
verActoresFavoritos(): void {
	this.movieLists = null;
	this.peliculasActoresFavoritos = null;
	
	if(this.userData.getId()>0){
		//Para user: asignar user logueado
		this.movieListService.getActoresFavoritos(this.userData.getId())
			.then(actores => {if(actores.length==0){
								this.encontro=false;
							 }else{
								this.actoresFavoritos = actores;
								this.encontro=true;
							 }});
	}else{
		console.log('inicie sesion para ver');
	}

}

//peliculas con mas de un actor favorito
verPeliculasVariosActoresFavoritos(): void{
	this.movieLists = null;
	this.actoresFavoritos = null;
	
	if(this.userData.getId()>0){
		this.movieListService.getPeliculasVariosActoresFavoritos(this.userData.getId())
		.then(peliculas => {if(peliculas.length == 0){
								this.encontro=false;
							}else{
								this.peliculasActoresFavoritos = peliculas;
								this.encontro=true;
							}});
	}else{
		console.log('inicie sesion para ver');
	}
	
}

//interseccion de peliculas entre dos listas
verInterseccion(): void {
	if(this.idLista1==null || this.idLista2==null){
		//no selecciono 2 listas
	}else{
		this.movieListService.getInterseccion(this.idLista1, this.idLista2);
	}	
}

setLista1(idMovie: number): void {
	this.idLista1=idMovie;
}

setLista2(idMovie: number): void {
	this.idLista2=idMovie;
}

ngOnInit(): void {
	  this.encontro=true;
	  this.nombreLista='';
	  this.idLista1=null;
	  this.idLista2=null;

		if(this.userData.getId()>0){
			console.log('user: ' + this.userData.getUsername() + 'id: ' + this.userData.getId());
			this.movieListService.getMovieListsByUser(this.userData.getId())
			.then(movieLists => {this.dropDownMovieLists = movieLists;});
		}else{
			console.log('inicie sesion para ver');
		}
		
}

constructor(private movieListService: MovieListService, private userData: UserData) {
	
}

}
