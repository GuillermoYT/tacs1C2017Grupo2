import { Component,OnInit } from '@angular/core';
import { Pelicula } from './../model/pelicula';
import { MovieList } from './../model/movie-list';
import { SummaryActor } from './../model/summary-actor';
import { MovieDetailComponent} from './movie-detail.component';
import { PeliculaService } from './../pelicula.service';
import { ActorService } from './../actor.service';
import { MovieListService } from './../movie-list.service';
import { UserData } from './../model/user-data';

@Component({
  selector: 'listaPeliculas',
  templateUrl: './partials/peliculas.component.html',
})
export class PeliculasComponent implements OnInit {
  movies: Pelicula[];
  baseMovies: Pelicula[]; //para el "cacheo" de peliculas populares del inicio
  actores: SummaryActor[];
  searchString: string;
  movieLists: MovieList[];

  queBuscarRadio: number;

  getPeliculas(): void {
    this.peliculaService.getMovies().then(movies => {this.movies = movies;});
  }

  searchClick(): void {
    if (this.queBuscarRadio == 1) {
      this.peliculaService.getMoviesByString(this.searchString).then(movies => {this.movies = movies; this.actores = null;});
    } else if (this.queBuscarRadio == 2) {
      this.actorService.getActorByString(this.searchString).then(actores => {this.actores = actores; this.movies = null;});
    } else {
      this.peliculaService.getMoviesByString(this.searchString).then(movies => this.movies = movies);
      this.actorService.getActorByString(this.searchString).then(actores => {this.actores = actores;});
    }
  }
  
  //muestra las listas de un usuario
  verListas(): void {	  
	  this.movieListService.getMovieListsByUser(this.userData.getId()).then(movieLists => {this.movieLists = movieLists;});
  }
  
  addToMovieList(idMovieList: number, idMovie: number): void {
	  this.movieListService.addMovieToList(idMovieList ,idMovie);
	  //Materialize.toast('Pelicula agregada', 2000) // 4000 is the duration of the toast
  }

  searchReset(): void {
    this.movies = this.baseMovies;
    this.actores = null;
    this.searchString = "";
  }

  ngOnInit(): void {
    this.getPeliculas();
	if(this.userData.getId()>0){
	    this.verListas();
	}else{
		console.log('inicie sesion para ver');
	}
  }

  constructor(private peliculaService: PeliculaService, private actorService: ActorService, private movieListService: MovieListService,  private userData: UserData) { }
}
