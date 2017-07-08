package test_smgo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
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
import model.Rol;
import model.SummaryActor;
import model.Usuario;
import tacs.ActorController;
import tacs.UserController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUsuario {

	private UserController userController = new UserController();
	private ActorController actorController = new ActorController();

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private RepoUsuarios repoU = RepoUsuarios.getInstance();

	@Before
	public void setUp() {
		ReflectionTestUtils.setField(userController, "repo", repoU);
		ReflectionTestUtils.setField(actorController, "userRepo", repoU);		
	}

	@Test
	public void test01CreacionUsuario() {		

		Usuario pepe = new UsuarioBuilder("Pepe").pass("1234").rol(new Rol("Usuario")).build();
		//Crear Usuario Pepe
		assertEquals(201, userController.addUser(pepe).getStatus());

		//Lista de Favoritos vacia
		assertEquals(0, userController.getActoresFavoritos(repoU.findByUsername("Pepe").getId()).size());

		//Comprobar Username
		assertEquals("Pepe", repoU.findByUsername("Pepe").getUsername());
		
		//Comprobar usuario Rol Usuario
		assertEquals("Usuario", repoU.findByUsername("Pepe").getRol().getName());
	}

	@Test
	public void test02MarcarActorFavorito() {		
		//Agregar Actor Favorito con id:10 para usuario pepe, response 200=ok
		assertEquals(200, userController.addActorFavorito(repoU.findByUsername("Pepe").getId(), 10).getStatus());

		//Verificar que user: Pepe tiene como Actor Favorito con id:10
		assertEquals(true, repoU.findByUsername("Pepe").getIdsActoresFavoritos().stream().anyMatch(fv -> fv.getId() == 10));	
	}
	
	@Test
	public void test03DesmarcarActorFavorito() {		
		//Desmarcar Actor Favorito con id:10 para usuario pepe, response 200=ok
		assertEquals(200, userController.removeActorFavorito(repoU.findByUsername("Pepe").getId(), 10).getStatus());

		//Verificar que user: Pepe NO tiene como Actor Favorito con id:10
		assertEquals(false, repoU.findByUsername("Pepe").getIdsActoresFavoritos().stream().anyMatch(fv -> fv.getId() == 10));
	}

	@Test
	public void test04UsuarioAdmin() {
		Rol unRol = new Rol("admin");
		Usuario usuario = repoU.findByUsername("Pepe");
		usuario.setRol(unRol);

		//Comprueba el rol del usuario
		assertEquals("admin",  repoU.findByUsername("Pepe").getRol().getName());
	}
	
	
	@Test
	public void test05GetUltimaSesion() throws Exception {

		Date asignedLastSesion = new Date();
		repoU.findByUsername("Pepe").setUltimaSesion(asignedLastSesion);

		//Verifica la ultima conexion
		assertEquals(asignedLastSesion,repoU.findByUsername("Pepe").getUltimaSesion());
	}
	
    //Como usuario quiero ver quienes son mis actores favoritos
    @Test
    public void testGetActoresFavoritos(){
    	
    	userController.addActorFavorito(repoU.findByUsername("Pepe").getId(), 10);
    	userController.addActorFavorito(repoU.findByUsername("Pepe").getId(), 12);
		
		List<SummaryActor> listAux = new ArrayList<SummaryActor>();
		listAux.add(actorController.getSumActorById(10));
		listAux.add(actorController.getSumActorById(12));

		assertTrue(repoU.findByUsername("Pepe").getIdsActoresFavoritos().containsAll(listAux));   	
    }
}
