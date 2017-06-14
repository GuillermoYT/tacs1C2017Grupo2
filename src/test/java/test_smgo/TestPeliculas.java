package test_smgo;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import creacionales.UsuarioBuilder;
import model.Rol;
import model.Usuario;
import repos.RepoUsuarios;
import tacs.ActorController;
import tacs.MovieController;
public class TestPeliculas {

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = standaloneSetup(new MovieController()).build();
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
				.andExpect(jsonPath("$.cast",hasSize(99)))
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

	
	@Test
	public void testGetMovieByActorsFavorites() throws Exception {
		Rol usr = new Rol("Usuario");
		
		Usuario guille = new UsuarioBuilder("Guille").pass("1234").rol(usr).build();
		RepoUsuarios.getInstance().addUsuario(guille);

		ActorController ac = new ActorController();
		
		RepoUsuarios.getInstance().getUserById(guille.getId()).addIdActorFavorito(ac.getSumActorById(1240693));
		RepoUsuarios.getInstance().getUserById(guille.getId()).addIdActorFavorito(ac.getSumActorById(6384));
		RepoUsuarios.getInstance().getUserById(guille.getId()).addIdActorFavorito(ac.getSumActorById(1331));
		this.mockMvc.perform(get("/peliculas/actoresFavoritos/{usuario}", guille.getId()).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(status().isOk())
            	.andExpect(jsonPath("$",hasSize(5)))				
				.andDo(print());
	}
}
