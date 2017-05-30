import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Usuario } from '../model/usuario';
import { UsuarioService } from '.././usuario.service'

@Component({
    selector: 'listaUsuarios',
    template: `
    <h2>Usuarios del Sistema</h2>
    <ul>
        <li *ngFor="let usuario of usuarios" (click)="onSelect(usuario)"
      [class.selected]="usuario === selectedUsuario">
        <span>{{usuario.username}}</span>
        </li>
    </ul>
    <div *ngIf="selectedUsuario">
        <h2>
        Usuario {{selectedUsuario.username | uppercase}}
        </h2>
        <button (click)="verDetalle()">Ver Detalle</button>
    </div>
    `,
    styles: [`
    .selected {
    background-color: #CFD8DC !important;
    color: white;
    }
    `]
})

export class UsuariosComponent implements OnInit {
    usuarios: Usuario[];
    selectedUsuario: Usuario;

    constructor(
        private usuarioService: UsuarioService,
        private router: Router) { }

    getUsers(): void {
        this.usuarioService
            .getUsuarios()
            .then(usuarios => this.usuarios = usuarios);
    }

    onSelect(usuario: Usuario): void {
        this.selectedUsuario = usuario;
    }

    verDetalle(): void {
        this.router.navigate(['/detail', this.selectedUsuario.id]);
    }

    ngOnInit(): void {
        this.getUsers();
    }

}