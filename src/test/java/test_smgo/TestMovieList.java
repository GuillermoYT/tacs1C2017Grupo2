package test_smgo;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;

import tacs.MovieController;
import tacs.MovieListController;
import util.LongsWrapper;

import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private RepoMoviesLists repoML = RepoMoviesLists.getInstance();
	
	@Before
	public void setUp() {
		ReflectionTestUtils.setField(controlMovieList, "repo", repoML);
	}
	
	@Test
	public void test01CreacionMovieLists() {		
		//No hay listas
		assertEquals(0, controlMovieList.getList().size());

		//Creo una lista para el user 1, response 200=ok
		assertEquals(200, controlMovieList.createMovielist("test", user1).getStatus());
		
		//Ahora hay listas
		assertNotEquals(0, controlMovieList.getList().size());
		
		//La lista fue creada en el user correspondiente
		assertNotNull(controlMovieList.getMovieListsByUser(user1));
		
		//La lista se creo con el nombre elegido
		assertEquals("test", controlMovieList.getMovieListsByUser(user1).get(0).getNombre());
		
		//La lista creada no tiene peliculas
		assertEquals(0, controlMovieList.getMovieListsByUser(user1).get(0).getListaPeliculas().size());
	}
	
	@Test
	public void test02AgregarPeliculas() {
		
		MovieList listaPelis = controlMovieList.getMovieListsByUser(user1).get(0);
		
		//La lista creada no tiene peliculas
		assertEquals(0, listaPelis.getListaPeliculas().size());
		
		//Pelicula agregada exitosamente, Response 200=ok
		assertEquals(200, controlMovieList.addMovieToList(listaPelis.getId(), 2l).getStatus());

		//La pelicula agregada tiene el id correcto
		assertEquals(2, listaPelis.getListaPeliculas().get(0).getId());

		//Agrego otra pelicula, Response 200=ok
		assertEquals(200,controlMovieList.addMovieToList(listaPelis.getId(), 3l).getStatus());

		//Hay dos peliculas en la lista
		assertEquals(2, listaPelis.getListaPeliculas().size());
	}
	
	@Test
	public void test03ConsultarLista() {
		String id="1";
		
		//Verifico que me devuelva una lista
		assertEquals(MovieList.class, controlMovieList.getMovieList(id).getClass());
		
		//Obtengo la lista con el id que pedi
		assertEquals(id, controlMovieList.getMovieList(id).getId());

	}

	
	@Test
	public void test04EliminarPeliculas() {
		
		String movieListId = "1";
		Long peliId = 2l;
		
		//Hay dos peliculas en la lista
		assertEquals(2, controlMovieList.getMovieList(movieListId).getListaPeliculas().size());
		
		LongsWrapper ids = new LongsWrapper();
		ids.setIds(Arrays.asList(peliId));

		//Elimino pelicula id:peliId de lista id:movieListId
		assertEquals(200, controlMovieList.deleteMovieFromUserListById(movieListId, ids).getStatus());

		//Hay una pelicula en la lista
		assertEquals(1, controlMovieList.getMovieList(movieListId).getListaPeliculas().size());

		//Se borró la pelicula con el id:2 correspondiente
		assertNotEquals(peliId.longValue(), controlMovieList.getMovieList(movieListId).getListaPeliculas().get(0).getId());
	}
	
	@Test
	public void test05CompararListas() {
		String movieListId1 = "1";
		String movieListId2 = "2";
		
		//Agrego una lista para comparar luego
		controlMovieList.createMovielist("comparar", user1);
		
		//Agrego peliculas a la nueva lista
		controlMovieList.addMovieToList(movieListId2, 2l);
		controlMovieList.addMovieToList(movieListId2, 5l);
		controlMovieList.addMovieToList(movieListId2, 9l);
		controlMovieList.addMovieToList(movieListId2, 32l);
		
		//Agrego dos peliculas a la primer lista que esten en la segunda
		controlMovieList.addMovieToList(movieListId1, 9l);
		controlMovieList.addMovieToList(movieListId1, 32l);
		
		//Hay dos peliculas en comun: 9 y 32
		assertEquals(2, controlMovieList.getMovielistComparison(movieListId1, movieListId2).size());
		
		//Corroboro que me devuelva los id: 9 y 32
		assertEquals(9, controlMovieList.getMovielistComparison(movieListId1, movieListId2).get(0).getId());
		assertEquals(32, controlMovieList.getMovielistComparison(movieListId1, movieListId2).get(1).getId());

		//Si comparo una lista consigo misma me devuelve todas las peliculas de la lista
		assertEquals(controlMovieList.getMovieList(movieListId2).getListaPeliculas().size(), 
				controlMovieList.getMovielistComparison(movieListId2, movieListId2).size());
	}
	
	@Test
	public void test06RankingActoresEnUnaLista() {
		MovieController controlMovie = new MovieController();
		
		String movieListId3 = "3";
		Long peliId = 2l;
		
		//Creo una nueva movieList sin peliculas. list id:3
		controlMovieList.createMovielist("ranking", user1);
		
		//El ranking debe estar vacio pues no hay peliculas agregadas
		assertEquals(0, controlMovieList.getRankingFromActorsByMovies(movieListId3).size());
		
		//Agrego una pelicula a la lista 3
		controlMovieList.addMovieToList(movieListId3, peliId);
		
		//cantidad de actores en la pelicula 
		int cantActores = controlMovie.getFullMovieById(peliId).getCast().size();

		//El ranking debe ser igual a la cantidad de actores de la pelicula pues solo hay una pelicula 
		assertEquals(cantActores, controlMovieList.getRankingFromActorsByMovies(movieListId3).size());
		
		//La cantidad de repeticiones del primero del ranking debe ser uno pues solo aparece una ves como todos
		assertEquals(1, controlMovieList.getRankingFromActorsByMovies(movieListId3).get(0).getCantRepeticiones());
		assertEquals(1, controlMovieList.getRankingFromActorsByMovies(movieListId3).get(cantActores-1).getCantRepeticiones());
		
		//Agrego una pelicula que solo tiene uno de los actores anteriores
		controlMovieList.addMovieToList(movieListId3, 3l);
		
		//Cantidad de actores en la pelicula id:3
		int cantActores2 = controlMovie.getFullMovieById(3l).getCast().size();
		
		//El ranking tendra en total los actores de las dos peliculas (le resto uno porque sabemos que hay solo un repetido)
		assertEquals(cantActores + cantActores2 -1, controlMovieList.getRankingFromActorsByMovies(movieListId3).size());
		
		//Ahora el 1ero del ranking estara repetido 2 veces
		assertEquals(2, controlMovieList.getRankingFromActorsByMovies(movieListId3).get(0).getCantRepeticiones());

		//El segundo aparecera una sola ves
		assertEquals(1, controlMovieList.getRankingFromActorsByMovies(movieListId3).get(1).getCantRepeticiones());

		//El ultimo tambien tendra una aparicion (le resto 2 porque el indice empieza en 0 y hay un repetido)
		assertEquals(1, controlMovieList.getRankingFromActorsByMovies(movieListId3).get(cantActores+cantActores2-2).getCantRepeticiones());

	}
	

}