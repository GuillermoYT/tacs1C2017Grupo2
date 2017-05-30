import { Injectable } from '@angular/core';
import { Http,Headers } from '@angular/http';
import 'rxjs/add/operator/toPromise';

import { Pelicula } from './model/pelicula';
import { MovieDetail } from './model/movie-detail';
import { Actor } from './model/actor';
import { SummaryActor } from './model/summary-actor';
import { ActorFavorito } from './model/actor-favorito';
import { UsuarioService } from './usuario.service';
import { UserData } from './model/user-data';

@Injectable()
export class ActorService {

  baseUrl = 'http://ruta-rest-tacs.7e14.starter-us-west-2.openshiftapps.com';

  getActor(id: number): Promise<Actor> {
    let url = this.baseUrl+`/actores/${id}`;
    return this.http.get(url)
      .toPromise()
      .then(response =>
                        response.json() as Actor)
      .catch(this.handleError);
  }

  getActorByString(query: string): Promise<SummaryActor[]> {
    let url= this.baseUrl+`/actores?query=${query}`;
    return this.http.get(url)
      .toPromise()
      .then(response => response.json() as SummaryActor[])
      .catch(this.handleError);
  }

  getActoresFavoritos(): Promise<ActorFavorito[]> {
    let headers = new Headers({'Authorization': 'Bearer '+this.userData.getToken()});
    let url = this.baseUrl+`/actores/rankingFavoritos`;
    return this.http.get(url, {headers: headers})
      .toPromise()
      .then(response => response.json() as ActorFavorito[])
      .catch(this.handleError);
  }

  handleError(error: any): Promise<any> {
    console.error('Error retrieving movies', error);
    console.log('Error es ' + error);
    return Promise.reject(error.message || error);
  }

  constructor(private http: Http, private usuarioService: UsuarioService, private userData: UserData) {  }
}
