import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { BooleanObj } from './model/boolean-obj';
import { Usuario } from './model/usuario';
import { UsuarioDetail } from './model/usuario-detail';
import { SummaryActor } from './model/summary-actor';
import { UserData } from './model/user-data';

@Injectable()
export class UsuarioService {
    constructor(private http: Http, private userData: UserData) { }

    baseUrl = 'http://ruta-rest-tacs.7e14.starter-us-west-2.openshiftapps.com';

    authenticate(username: string, password: string): Promise<boolean> {
      let url = this.baseUrl+'/auth';
      let headers = new Headers;
      headers.append('Content-Type', 'application/json');
      let body = {
        "username": username,
        "password": password
      }
      return this.http.post(url, body, headers)
        .toPromise()
        .then(response => {
          let respuesta = response.json() && response.json().token;
          if (respuesta) {
            this.userData.setToken(respuesta);
            this.userData.setId(response.json().id);
            this.userData.setAdmin(response.json().admin);
            return true;
          } else {
            return false;
          }
        })
        .catch(this.handleError);
    }

    register(username: string, password: string): Promise<boolean> {
      let url = this.baseUrl+'/usuarios';
      let headers = new Headers;
      headers.append('Content-Type', 'application/json');
      let body = {
        "username": username,
        "password": password
      }
      return this.http.post(url, body, headers)
        .toPromise()
        .then(response => {
          let respuesta = response.json();
          if (respuesta) {
            return true;
          } else {
            return false;
          }
        })
        .catch(this.handleError);
    }

    getUsuarios(): Promise<Usuario[]> {
        let url = this.baseUrl+`/usuarios`;
        return this.http.get(url)
            .toPromise()
            .then(response => response.json() as Usuario[])
            .catch(this.handleError);
    }

    getUsuario(id: string): Promise<UsuarioDetail> {
        let url = this.baseUrl+`/usuarios/${id}`;
        return this.http.get(url)
            .toPromise()
            .then(response => response.json() as UsuarioDetail)
            .catch(this.handleError);
    }

    getFavoritos(id: string): Promise<SummaryActor[]> {
        let url = this.baseUrl+`/usuarios/${id}/actoresFavoritos`;
        return this.http.get(url)
            .toPromise()
            .then(response => response.json() as SummaryActor[])
            .catch(this.handleError);
    }

    actorFavorito(idActor: number): Promise<BooleanObj>{
        let url = this.baseUrl+`/usuarios/${this.userData.id}/actorFavorito/${idActor}`;
        return this.http.get(url).toPromise()
        .then(response => response.json() as BooleanObj).catch(this.handleError);
    }

    marcarFavorito(idActor: number){
        let headers = new Headers;
        headers.append('Content-Type', 'application/json');

        let url = this.baseUrl+`/usuarios/${this.userData.id}/favorito/${idActor}`;
        this.http.put(url, null, headers ).toPromise().then()
        .catch(this.handleError);
        console.log("macarFavorito: "+idActor);
    }

    desmarcarFavorito(idActor: number){
        let url = this.baseUrl+`/usuarios/${this.userData.id}/favorito/${idActor}`;
        this.http.delete(url).toPromise().then()
        .catch(this.handleError);
        console.log("desmacarFavorito: "+idActor);
    }

    handleError(error: any): Promise<any> {
        console.error('Error retrieving usuarios', error);
        return Promise.reject(error.message || error);
    }
}
