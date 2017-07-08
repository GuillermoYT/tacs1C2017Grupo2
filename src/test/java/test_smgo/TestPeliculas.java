package test_smgo;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import creacionales.UsuarioBuilder;
import model.Pelicula;
import model.Rol;
import model.Usuario;

import tacs.ActorController;
import tacs.MovieController;
import tacs.UserController;
public class TestPeliculas {

	private UserController userController = new UserController();
	private ActorController actorController = new ActorController();
	private MovieController movieController = new MovieController();
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private MockMvc mockMvc;
	private RepoUsuarios repoU = RepoUsuarios.getInstance();

	@Before
	public void setUp() {
		this.mockMvc = standaloneSetup(new MovieController()).build();
		ReflectionTestUtils.setField(userController, "repo", repoU);
		ReflectionTestUtils.setField(actorController, "userRepo", repoU);
		ReflectionTestUtils.setField(movieController, "userRepo", repoU);
	}
	
	/*
	 * Como usuario quiero poder ver el detalle de una película. 
	 * 		Imágenes.     
	 * 		Actores.     
	 * 		Reviews.     
	 * 		Descripción.
	 */
	@Test
	public void testObtenerDetallePelicula() throws Exception {
		String id = "263115"; //Id de la pelicula LOGAN

		this.mockMvc.perform(get("/peliculas/"+id).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.id").value(263115))
				.andExpect(jsonPath("$.nombre").value("Logan"))
				.andExpect(jsonPath("$.cast",hasSize(104)))
				.andExpect(jsonPath("$.cast[0].name").value("Hugh Jackman"))
				.andExpect(jsonPath("$.cast[1].name").value("Patrick Stewart"))
				.andExpect(jsonPath("$.imagePath").value("http://image.tmdb.org/t/p/w300//9EXnebqbb7dOhONLPV9Tg2oh2KD.jpg"))
				.andExpect(jsonPath("$.descripcion").value("In the near future, a weary Logan cares for an ailing Professor X in a hideout on the Mexican border. But Logan's attempts to hide from the world and his legacy are upended when a young mutant arrives, pursued by dark forces."))
				.andDo(print());
	}

	//Como usuario quiero buscar pelicula por nombre
	@Test
	public void testBuscarPeliculas() throws Exception {
		this.mockMvc.perform(get("/peliculas")
				.param("query", "Matrix")
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].nombre").value("The Matrix"))
				.andExpect(jsonPath("$.[1].nombre").value("The Matrix Reloaded"))
				.andExpect(jsonPath("$.[2].nombre").value("The Matrix Revolutions"))
				.andDo(print());
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