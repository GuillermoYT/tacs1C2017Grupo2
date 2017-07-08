package test_smgo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

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
import model.Rol;
import model.Usuario;
import tacs.MovieListController;
import tacs.UserController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUsuario {

	private UserController userController = new UserController();
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private RepoUsuarios repoU = RepoUsuarios.getInstance();
	
	@Before
	public void setUp() {
		ReflectionTestUtils.setField(userController, "repo", repoU);
	}
	
	@Test
	public void test01CreacionMovieLists() {		

		Usuario pepe = new UsuarioBuilder("Pepe").pass("1234").rol(new Rol("Usuario")).build();
		//Crear Usuario Pepe
		assertEquals(201, userController.addUser(pepe).getStatus());
		
		//Lista de Favoritos vacia
		assertEquals(0, userController.getActoresFavoritos(repoU.findByUsername("Pepe").getId()).size());

		//Agregar Actor Favorito con id:10 para usuario pepe, response 200=ok
		assertEquals(200, userController.addActorFavorito(repoU.findByUsername("Pepe").getId(), 10).getStatus());

		//Verificar que user: Pepe tiene como Actor Favorito con id:10
		assertEquals(true, repoU.findByUsername("Pepe").getIdsActoresFavoritos().stream().anyMatch(fv -> fv.getId() == 10));

		//Desmarcar Actor Favorito con id:10 para usuario pepe, response 200=ok
		assertEquals(200, userController.removeActorFavorito(repoU.findByUsername("Pepe").getId(), 10).getStatus());

		//Verificar que user: Pepe NO tiene como Actor Favorito con id:10
		assertEquals(false, repoU.findByUsername("Pepe").getIdsActoresFavoritos().stream().anyMatch(fv -> fv.getId() == 10));

	}
	

}
