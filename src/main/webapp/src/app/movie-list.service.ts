import { Injectable } from '@angular/core';
import { Http,Headers,RequestOptions,Response  } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { Pelicula } from './model/pelicula';
import { MovieDetail } from './model/movie-detail';
import { MovieList } from './model/movie-list';
import { ActorFavorito } from './model/actor-favorito';
import { RankingActor } from './model/RankingActor';
@Injectable()
export class MovieListService {
	results:Object[];
	encontro:boolean;
	loading:boolean;

	baseUrl = 'http://ruta-rest-tacs.7e14.starter-us-west-2.openshiftapps.com';

  createMovieList(nombre: string, user: string){
	  let headers = new Headers({ 'Content-Type': 'application/json;charset=utf-8' });
	  let options = new RequestOptions({ headers: headers });
	  let url = this.baseUrl+`/movielists?user=${user}`;
	  this.http.post(url, nombre , options)
		  .toPromise()
	      .catch(this.handleError);
  }

  addMovieToList(idList: number, idMovie: number){
	  let headers = new Headers({ 'Content-Type': 'application/json;charset=utf-8' });
	  let options = new RequestOptions({ headers: headers });
	  let url = this.baseUrl+`/movielists/${idList}/${idMovie}`;

	  this.http.put(url, options)
		  .toPromise()
	      .catch(this.handleError);
  }

  deleteMovieFromList(movielistId: string, movieId: number): Promise<number>{
	  let headers = new Headers({ 'Content-Type': 'application/json' });
	  let url = this.baseUrl+`/movielists/${movielistId}`;
	  let body= JSON.stringify({
		    ids: [movieId]
	  });
	  let options = new RequestOptions({ headers: headers, body: body });

	  return this.http.delete(url, options).toPromise().then(res => res.status).catch(this.handleError);
  }

  //devuelve una movielist por id
  getMovieList(id: string): Promise<MovieList> {
    let url = this.baseUrl+`/movielists/${id}`;
    return this.http.get(url)
      .toPromise()
      .then(response => response.json() as MovieList)
      .catch(this.handleError);
  }

  //devuelve todas las movielists
  getMovieLists(): Promise<MovieList[]> {
	return this.http.get(this.baseUrl+'/movielists')
      .toPromise()
      .then(response => response.json() as MovieList[])
      .catch(this.handleError);
  }

  //devuelve las movielists de un user
  getMovieListsByUser(ownerId: string): Promise<MovieList[]>{
	let url = this.baseUrl+`/movielists/search?ownerId=${ownerId}`;
		return this.http.get(url)
			      .toPromise()
			      .then(response => response.json() as MovieList[])
			      .catch(this.handleError);
  }

  //devuelve los actores favoritos de un user
  getActoresFavoritos(userId: string): Promise<ActorFavorito[]>{
	let url = this.baseUrl+`/usuarios/${userId}/actoresFavoritos`;
		return this.http.get(url)
			      .toPromise()
			      .then(response => response.json() as ActorFavorito[])
			      .catch(this.handleError);
  }

  //devuelve las peliculas con mas de un actor favorito de un user
  getPeliculasVariosActoresFavoritos(userId: string): Promise<Pelicula[]>{
	  let url = this.baseUrl+`/peliculas/actoresFavoritos/${userId}`;
			return this.http.get(url)
				      .toPromise()
				      .then(response => response.json() as Pelicula[])
				      .catch(this.handleError);
  }

  //devuelve la interseccion de peliculas entre dos listas
  getInterseccion(list1: string, list2: string) {
	  let promise = new Promise((resolve, reject) => {
		    let url = this.baseUrl+`/movielists/compare?list1=${list1}&list2=${list2}`;
		    this.http.get(url)
		      .toPromise()
		      .then(
		        res => { // Success
		          this.results = res.json() as Pelicula[];
		          if(this.results.length==0){
		        	  this.encontro=false;
		          }else{
		        	  this.encontro=true;
		          }
		          resolve();
		        },
		        msg =>{ //Error
		        	reject(msg);
		        }
		      );
		  });
	  return promise;
  }

	getRankingMovieList(idML: string): Promise<RankingActor[]>{
		let url = this.baseUrl+`/movielists/actoresRepetidos/${idML}`;
		return this.http.get(url).toPromise().then( res => res.json() as RankingActor[])
						.catch(this.handleError);
	}


  private handleError (error: Response | any) {
	  let errMsg: string;
	  if (error instanceof Response) {
	    const body = error.json() || '';
	    const err = body.error || JSON.stringify(body);
	    errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
	  } else {
	    errMsg = error.message ? error.message : error.toString();
	  }
	  console.error(errMsg);
	  return Promise.reject(errMsg);
	}

  constructor(private http: Http) {
	  this.results = [];
	  this.loading = false;
	  this.encontro = true;
  }
}
