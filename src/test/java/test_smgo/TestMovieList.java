package test_smgo;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;

import tacs.MovieListController;
import util.LongsWrapper;

import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import model.MovieList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestMovieList {

	private MovieListController controlMovieList = new MovieListController();
	private String user1 = "5947ef1eaa6a6600085bdc55"; //Guille
	private String user2 = "5947ef1eaa6a6600085bdc56"; //Alvaro
	private String movieList = "59482611c1a2010008e98ed6"; //Superheroes de Alvaro
	
	private RepoMoviesLists repoML = RepoMoviesLists.getInstance();
	
	@Before
	public void setUp() {
		ReflectionTestUtils.setField(controlMovieList, "repo", repoML);
	}
	
	@Test
	public void test01CreacionMovieLists() {		
		//No hay listas
		//assertEquals(0, controlMovieList.getList().size());

		//Creo una lista para el user 1, response 200=ok
		assertEquals(200, controlMovieList.createMovielist("test", user1).getStatus());
		
		//Ahora hay listas
		System.out.println(controlMovieList.getMovieListsByUser(user1));
		//assertNotEquals(0, controlMovieList.getList().size());
		
		//La lista fue creada en el user correspondiente
		assertNotNull(controlMovieList.getMovieListsByUser(user1));
		
		//La lista se creo con el nombre elegido
		assertEquals("test", controlMovieList.getMovieListsByUser(user1).get(0).getNombre());
		
		//La lista creada no tiene peliculas
		assertEquals(0, controlMovieList.getMovieListsByUser(user1).get(0).getListaPeliculas().size());
	}
	
	@Test
	public void test02AgregarPeliculas() {
		
		//La lista creada no tiene peliculas
		assertEquals(0, controlMovieList.getMovieListsByUser(user1).get(0).getListaPeliculas().size());
		
		//Pelicula agregada exitosamente, Response 200=ok
		assertEquals(200,controlMovieList.addMovieToList(movieList, 2l).getStatus());

		//La pelicula agregada tiene el id correcto
		assertEquals(2, controlMovieList.getMovieListsByUser(user1).get(0).getListaPeliculas().get(0).getId());

		//Agrego otra pelicula, Response 200=ok
		assertEquals(200,controlMovieList.addMovieToList(movieList, 3l).getStatus());

		//Hay dos peliculas en la lista
		assertEquals(2, controlMovieList.getMovieList(movieList).getListaPeliculas().size());
	}
	
	@Test
	public void test03ConsultarLista() {
		//Long id=1l;
		
		//Verifico que me devuelva una lista
		assertEquals(MovieList.class, controlMovieList.getMovieList(movieList).getClass());
		
		//Obtengo la lista con el id que pedi
		assertEquals(movieList, controlMovieList.getMovieList(movieList).getId());

	}

	
	@Test
	public void test04EliminarPeliculas() {
		
		//Hay dos peliculas en la lista
		assertEquals(2, controlMovieList.getMovieList(movieList).getListaPeliculas().size());
		
		LongsWrapper ids = new LongsWrapper();
		ids.setIds(Arrays.asList(2l));

		//Elimino pelicula id:2 de lista id:1
		assertEquals(200, controlMovieList.deleteMovieFromUserListById(movieList, ids).getStatus());

		//Hay una pelicula en la lista
		assertEquals(1, controlMovieList.getMovieList(movieList).getListaPeliculas().size());

		//Se borró la pelicula con el id:2 correspondiente
		assertNotEquals(2, controlMovieList.getMovieList(movieList).getListaPeliculas().get(0).getId());
	}
	
	/*
	@Test
	public void test05CompararListas() {
		
		//Agrego una lista y peliculas para comparar luego
		controlMovieList.createMovielist("otra", user1);
		controlMovieList.addMovieToList(2, 2l);
		controlMovieList.addMovieToList(2, 3l);
		controlMovieList.addMovieToList(movieList, 9l);
		controlMovieList.addMovieToList(2, 9l);
		controlMovieList.addMovieToList(2, 32l);
		
		//Hay dos peliculas en comun: 3 y 9
		assertEquals(2, controlMovieList.getMovielistComparison(movieList, 2).size());
		
		//Corroboro que me devuelva los id: 3 y 9
		assertEquals(3, controlMovieList.getMovielistComparison(movieList, 2).get(0).getId());
		assertEquals(9, controlMovieList.getMovielistComparison(movieList, 2).get(1).getId());

		//Si comparo una lista consigo misma me devuelve todas las peliculas de la lista
		assertEquals(controlMovieList.getMovieList(2).getListaPeliculas().size(), 
				controlMovieList.getMovielistComparison(2, 2).size());
	}
	
	@Test
	public void test06RankingActoresEnUnaLista() {
		MovieController controlMovie = new MovieController();
		
		//Creo una nueva movieList sin peliculas. list id:3
		controlMovieList.createMovielist("ranking", user1);
		
		//El ranking debe estar vacio pues no hay peliculas agregadas
		assertEquals(0, controlMovieList.getRankingFromActorsByMovies(3).size());
		
		//Agrego una pelicula a la lista 3
		controlMovieList.addMovieToList(3, 2l);
		
		//cantidad de actores en la pelicula id:2
		int cantActores = controlMovie.getFullMovieById(controlMovieList.getMovieList(3).getListaPeliculas().get(0).getId()).getCast().size();

		//El ranking debe ser igual a la cantidad de actores de la pelicula pues solo hay una pelicula 
		assertEquals(cantActores, controlMovieList.getRankingFromActorsByMovies(3).size());
		
		//La cantidad de repeticiones del primero del ranking debe ser uno pues solo aparece una ves como todos
		assertEquals(1, controlMovieList.getRankingFromActorsByMovies(3).get(0).getCantRepeticiones());
		assertEquals(1, controlMovieList.getRankingFromActorsByMovies(3).get(cantActores-1).getCantRepeticiones());
		
		//Agrego la misma pelicula a la lista 
		controlMovieList.addMovieToList(3, 2l);
		
		//Verifico que el ranking se mantenga con la misma cantidad de actores de la pelicula ya que es la misma
		assertEquals(cantActores, controlMovieList.getRankingFromActorsByMovies(3).size());
		
		//Verifico que ahora la cantidad de repeticiones sea 2 
		assertEquals(2, controlMovieList.getRankingFromActorsByMovies(3).get(0).getCantRepeticiones());
		assertEquals(2, controlMovieList.getRankingFromActorsByMovies(3).get(cantActores-1).getCantRepeticiones());
		
		//Agrego una pelicula que solo tiene uno de los actores anteriores
		controlMovieList.addMovieToList(3, 3l);
		
		//Cantidad de actores en la pelicula id:3
		int cantActores2 = controlMovie.getFullMovieById(controlMovieList.getMovieList(3).getListaPeliculas().get(2).getId()).getCast().size();
		
		//El ranking tendra en total los actores de la 1er y 3er pelicula pues la 2da es la misma que la primera
		//(le resto uno porque sabemos que hay solo un repetido)
		assertEquals(cantActores + cantActores2 -1, controlMovieList.getRankingFromActorsByMovies(3).size());
		
		//Ahora el 1ero del ranking estara repetido 3 veces
		assertEquals(3, controlMovieList.getRankingFromActorsByMovies(3).get(0).getCantRepeticiones());

		//El segundo estara repetido 2 veces
		assertEquals(2, controlMovieList.getRankingFromActorsByMovies(3).get(1).getCantRepeticiones());

		//El ultimo tendra una sola aparicion (le resto 2 porque el indice empieza en 0 y hay un repetido)
		assertEquals(1, controlMovieList.getRankingFromActorsByMovies(3).get(cantActores+cantActores2-2).getCantRepeticiones());

	}*/
	

}