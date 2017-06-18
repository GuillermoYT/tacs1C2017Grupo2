import { SummaryActor } from './summary-actor';
import { Rol } from './rol';

export class Usuario {
  id: string;
  username: string;
  password: string;
  rol: Rol;
  actoresFavoritos: SummaryActor[];
  ultimaSesion: number;
}
