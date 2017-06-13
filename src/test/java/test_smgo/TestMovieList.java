package test_smgo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;

import model.MovieList;
import tacs.MovieListController;
import util.LongsWrapper;

import org.junit.runners.MethodSorters;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestMovieList {

	private MovieList movieList = new MovieList();
	private MovieListController controlMovieList = new MovieListController();
	
	@Before
	public void setUp(){
		
	}
	
	@Test
	public void test01CreacionMovieLists() {		
		//No hay listas
		assertEquals(0, controlMovieList.getList().size());

		//Creo una lista para el user 1, response 200=ok
		assertEquals(200, controlMovieList.createMovielist("test", 1).getStatus());
		
		//Ahora hay listas
		assertNotEquals(0, controlMovieList.getList().size());
		//assertEquals("lista test",controlMovieList.getMovieList(1).getNombre());
		
		//La lista fue creada en el user correspondiente
		assertNotNull(controlMovieList.getMovieListsByUser(1));
		
		//La lista se creo con el nombre elegido
		assertEquals("test", controlMovieList.getMovieListsByUser(1).get(0).getNombre());
		
		//La lista creada no tiene peliculas
		assertEquals(0, controlMovieList.getMovieListsByUser(1).get(0).getListaPeliculas().size());
	}
	
	@Test
	public void test02AgregarPeliculas() {
		
		//La lista creada no tiene peliculas
		assertEquals(0, controlMovieList.getMovieListsByUser(1).get(0).getListaPeliculas().size());
		
		//Pelicula agregada exitosamente, Response 200=ok
		assertEquals(200,controlMovieList.addMovieToList(1, 2l).getStatus());

		//La pelicula agregada tiene el id correcto
		assertEquals(2, controlMovieList.getMovieListsByUser(1).get(0).getListaPeliculas().get(0).getId());

		//Agrego otra pelicula, Response 200=ok
		assertEquals(200,controlMovieList.addMovieToList(1, 3l).getStatus());

		//Hay dos peliculas en la lista
		assertEquals(2, controlMovieList.getMovieList(1).getListaPeliculas().size());
	}
	
	@Test
	public void test03EliminarPeliculas() {
		
		//Hay dos peliculas en la lista
		assertEquals(2, controlMovieList.getMovieList(1).getListaPeliculas().size());
		
		LongsWrapper ids = new LongsWrapper();
		ids.setIds([2]);
		//Elimino pelicula id:2 de lista id:1
		assertEquals(200,controlMovieList.deleteMovieFromUserListById(1, ids));
		
		System.out.println(controlMovieList.getMovieListsByUser(1).get(0).getListaPeliculas().get(0).getId());

		//La pelicula agregada tiene el id correcto
		assertEquals(2, controlMovieList.getMovieListsByUser(1).get(0).getListaPeliculas().get(0).getId());

		//Agrego otra pelicula, Response 200=ok
		assertEquals(200,controlMovieList.addMovieToList(1, 3l).getStatus());

		
	}

}