import { Pelicula } from './pelicula';

export class MovieList {
	id: string;
	nombre: string;
	ownerId: number;
	listaPeliculas: Pelicula[];
}
