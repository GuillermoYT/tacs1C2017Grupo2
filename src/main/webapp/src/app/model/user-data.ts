import { Injectable } from '@angular/core';

@Injectable()
export class UserData {
  username: string;
  token: string;
  id: number;
  admin: boolean;

  constructor() {
    this.setId(0);
  }

  setToken(unString: string): void {
    this.token = unString;
  }

  setUsername(unNombre: string): void {
    this.username = unNombre;
  }

  setId(numero: number): void {
    this.id = numero;
  }

  setAdmin(valor: boolean): void {
    this.admin = valor;
  }

  getToken(): string {
    return this.token;
  }

  getUsername(): string {
    return this.username;
  }

  getId(): number {
    return this.id;
  }

  isAdmin(): boolean {
    return this.admin;
  }

  clear(): void {
    this.token = null;
    this.username = null;
    this.admin = false;
    this.id = 0;
  }
}
