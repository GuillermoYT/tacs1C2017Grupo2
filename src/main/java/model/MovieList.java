package model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;

public class MovieList {
	
	@Id
	private String id;
	private String nombre;
	private String ownerId; //Usuario
	private List<Pelicula> listaPeliculas;

	public MovieList() {
		listaPeliculas = new ArrayList<Pelicula>();
	}
	
	public MovieList(String unNombre, String unUserId) {
		nombre = unNombre;
		ownerId = unUserId;
		listaPeliculas = new ArrayList<Pelicula>();
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<Pelicula> getListaPeliculas() {
		return listaPeliculas;
	}
	public String getOwnerId() {
		return ownerId;
	}

	public void setUserId(String userId) {
		this.ownerId = userId;
	}

	public void addPelicula(Pelicula p) {
		//Tomo los distintos para no repetir peliculas
		listaPeliculas = listaPeliculas
			    .stream()
			    .filter(peli -> !(peli.id==p.id))
			    .collect(Collectors.toList());
		
		listaPeliculas.add(p);
	}
	
	//Devuelve una lista de peliculas que se encuentran en el objeto actual y el recibido por parametro
	public List<Pelicula> interseccion(MovieList movielist){
		List<Pelicula> interseccion = new ArrayList<Pelicula>();
		
		boolean esta = false;
		
		for (Pelicula peli : listaPeliculas) {
			Iterator<Pelicula> itPelis = movielist.getListaPeliculas().iterator(); 
			while (itPelis.hasNext() && !esta) {
				Pelicula peliAux = (Pelicula) itPelis.next();
				if (peli.getId() == peliAux.getId()){
					esta=true;
					interseccion.add(peli);
				}
			}
			esta=false;
		}
		return interseccion;
	}

}
