package test_smgo;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import creacionales.UsuarioBuilder;
import model.FavoritoActor;
import model.Rol;
import model.Usuario;
import tacs.ActorController;
import tacs.MovieListController;
import tacs.UserController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAdministrador {

	private UserController userController = new UserController();
	private ActorController actorController = new ActorController();
	private MovieListController controlMovieList = new MovieListController();

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private RepoUsuarios repoU = RepoUsuarios.getInstance();	
	private RepoMoviesLists repoML = RepoMoviesLists.getInstance();

	@Before
    public void setup() {
		ReflectionTestUtils.setField(userController, "repo", repoU);
		ReflectionTestUtils.setField(actorController, "userRepo", repoU);		
		ReflectionTestUtils.setField(controlMovieList, "repo", repoML);

		//Creo 3 usuarios
		Usuario quique = new UsuarioBuilder("Quique").pass("1234").rol(new Rol("Usuario")).build();
		Usuario toto = new UsuarioBuilder("Toto").pass("1234").rol(new Rol("Usuario")).build();
		Usuario papo = new UsuarioBuilder("Papo").pass("1234").rol(new Rol("Usuario")).build();
	
		userController.addUser(quique);
		userController.addUser(toto);
		userController.addUser(papo);

		controlMovieList.createMovielist("Lista A", repoU.findByUsername("Quique").getId());
		
		controlMovieList.addMovieToList(repoML.findById("1").getId(), (long)120);
		controlMovieList.addMovieToList(repoML.findById("1").getId(), (long)121);
		controlMovieList.addMovieToList(repoML.findById("1").getId(), (long)122);
			
		//Setup como admin quiero ver el ranking de actores favoritos de todos los usuarios

		//a cada usuario le asigno actores favoritos
		userController.addActorFavorito(repoU.findByUsername("Quique").getId(), 1);
		userController.addActorFavorito(repoU.findByUsername("Quique").getId(), 2);
		userController.addActorFavorito(repoU.findByUsername("Quique").getId(), 3);
		userController.addActorFavorito(repoU.findByUsername("Quique").getId(), 4);
		userController.addActorFavorito(repoU.findByUsername("Toto").getId(), 3);
		userController.addActorFavorito(repoU.findByUsername("Toto").getId(), 4);
		userController.addActorFavorito(repoU.findByUsername("Papo").getId(), 3);

    }

    //Como administrador quiero poder ver los siguientes datos de un usuario
    @Test
    public void testGetDetalleUsuario() throws Exception{
    	//Usuario
		assertEquals("Quique",repoU.findByUsername("Quique").getUsername());
    	
    	//Ultima session
    	Date asignedLastSesion = new Date();
		repoU.findByUsername("Quique").setUltimaSesion(asignedLastSesion);
		assertEquals(asignedLastSesion,repoU.findByUsername("Quique").getUltimaSesion());

		//Cantidad de listas creadas
		assertEquals(1,controlMovieList.getMovieListsByUser(repoU.findByUsername("Quique").getId()).size());
		
		//Lista de Favoritos vacia
		assertEquals(4, userController.getActoresFavoritos(repoU.findByUsername("Quique").getId()).size());
		
		//Detalle de una lista
		assertEquals("Lista A",controlMovieList.getMovieList("1").getNombre());

		//Detalle de una lista para saber la cantidad de peliculas
		assertEquals(3,controlMovieList.getMovieList("1").getListaPeliculas().size());    	
    }

	//Como administrador quiero ver el ranking de actores favoritos de todos mis usuarios
	//(los actores que han sido más veces elegidos como favoritos)
	@Test
	public void testRankingActoresFavoritos() throws Exception{
		
		List<FavoritoActor> actoresFavoritosRanking = actorController.rankingActores();

		//1° actor del ranking
		assertEquals(3,actoresFavoritosRanking.get(0).getActor().getId());
		//2° actor del ranking
		assertEquals(4,actoresFavoritosRanking.get(1).getActor().getId());
	}
}
