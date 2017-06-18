import { Pelicula } from './pelicula';

export class MovieList {
	id: string;
	nombre: string;
	ownerId: string;
	listaPeliculas: Pelicula[];
}
