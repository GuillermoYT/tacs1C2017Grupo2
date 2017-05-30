import { Injectable } from '@angular/core';
import { Http,Headers } from '@angular/http';
import 'rxjs/add/operator/toPromise';

import { Pelicula } from './model/pelicula';
import { MovieDetail } from './model/movie-detail';

@Injectable()
export class PeliculaService {

  baseUrl = 'http://ruta-rest-tacs.7e14.starter-us-west-2.openshiftapps.com';

  getMovies(): Promise<Pelicula[]> {
    return this.http.get(this.baseUrl+'/peliculas')
      .toPromise()
      .then(response => response.json() as Pelicula[])
      .catch(this.handleError);
  }

  getMovie(id: number): Promise<MovieDetail> {
    let url = this.baseUrl+`/peliculas/${id}`;
    return this.http.get(url)
      .toPromise()
      .then(response => response.json() as MovieDetail)
      .catch(this.handleError);
  }

  getMoviesByString(query: string): Promise<Pelicula[]> {
    let url= this.baseUrl+`/peliculas?query=${query}`;
    return this.http.get(url)
      .toPromise()
      .then(response => response.json() as Pelicula[])
      .catch(this.handleError);
  }

  handleError(error: any): Promise<any> {
    console.error('Error retrieving movies', error);
    return Promise.reject(error.message || error);
  }

  constructor(private http: Http) {  }
}
