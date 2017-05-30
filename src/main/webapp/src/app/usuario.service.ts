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

    authenticate(username: string, password: string): Promise<boolean> {
      let url = 'http://localhost:8080/auth';
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
      let url = 'http://localhost:8080/usuarios';
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
        let url = `http://localhost:8080/usuarios`;
        return this.http.get(url)
            .toPromise()
            .then(response => response.json() as Usuario[])
            .catch(this.handleError);
    }

    getUsuario(id: number): Promise<UsuarioDetail> {
        let url = `http://localhost:8080/usuarios/${id}`;
        return this.http.get(url)
            .toPromise()
            .then(response => response.json() as UsuarioDetail)
            .catch(this.handleError);
    }

    getFavoritos(id: number): Promise<SummaryActor[]> {
        let url = `http://localhost:8080/usuarios/${id}/actoresFavoritos`;
        return this.http.get(url)
            .toPromise()
            .then(response => response.json() as SummaryActor[])
            .catch(this.handleError);
    }

    actorFavorito(idActor: number): Promise<BooleanObj>{
        let url = `http://localhost:8080/usuarios/2/actorFavorito/${idActor}`;
        return this.http.get(url).toPromise()
        .then(response => response.json() as BooleanObj).catch(this.handleError);
    }

    marcarFavorito(idActor: number){
        let headers = new Headers;
        headers.append('Content-Type', 'application/json');

        let url = `http://localhost:8080/usuarios/2/favorito/${idActor}`;
        this.http.put(url, null, headers ).toPromise().then()
        .catch(this.handleError);
        console.log("macarFavorito: "+idActor);
    }

    desmarcarFavorito(idActor: number){
        let url = `http://localhost:8080/usuarios/2/favorito/${idActor}`;
        this.http.delete(url).toPromise().then()
        .catch(this.handleError);
        console.log("desmacarFavorito: "+idActor);
    }

    handleError(error: any): Promise<any> {
        console.error('Error retrieving usuarios', error);
        return Promise.reject(error.message || error);
    }
}
