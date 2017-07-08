package test_smgo;

import static org.junit.Assert.assertEquals;

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
import model.Pelicula;
import model.Rol;
import model.Usuario;
import tacs.ActorController;
import tacs.MovieController;
import tacs.UserController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestMovie {

	private UserController userController = new UserController();
	private ActorController actorController = new ActorController();
	private MovieController movieController = new MovieController();
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private RepoUsuarios repoU = RepoUsuarios.getInstance();

	@Before
	public void setUp() {
		ReflectionTestUtils.setField(userController, "repo", repoU);
		ReflectionTestUtils.setField(actorController, "userRepo", repoU);
		ReflectionTestUtils.setField(movieController, "userRepo", repoU);
	}
	
	//Como usuario quiero ver si hay películas donde esté más de uno de mis actores favoritos.
	@Test
	public void testGetMovieByActorsFavorites() throws Exception {
		Usuario Tito = new UsuarioBuilder("Tito").pass("1234").rol(new Rol("Usuario")).build();
		userController.addUser(Tito).getStatus();
		
		repoU.findByUsername("Tito").addIdActorFavorito(actorController.getSumActorById(1240693));
		repoU.findByUsername("Tito").addIdActorFavorito(actorController.getSumActorById(6384));
		repoU.findByUsername("Tito").addIdActorFavorito(actorController.getSumActorById(1331));

		List<Pelicula> mvs = movieController.getMovie(repoU.findByUsername("Tito").getId());
		
		assertEquals(5, mvs.size());
	}
}
