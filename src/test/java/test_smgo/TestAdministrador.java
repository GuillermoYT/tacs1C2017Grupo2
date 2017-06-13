package test_smgo;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import model.SummaryActor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import creacionales.UsuarioBuilder;
import model.MovieList;
import model.Rol;
import model.Usuario;
import repos.RepoMoviesLists;
import repos.RepoUsuarios;
import tacs.ActorController;
import tacs.Application;
import tacs.MovieController;
import tacs.UserController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class TestAdministrador {

	private MockMvc mockMvc;
	private MockMvc mockMvc2;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(new UserController()).build();
		this.mockMvc2 = standaloneSetup(new ActorController()).build();
		Rol usr = new Rol("Usuario");
	
		Usuario guille = new UsuarioBuilder("Guille").pass("1234").rol(usr).build();
		RepoUsuarios.getInstance().addUsuario(guille);
		
		MovieList rankingMovies = new MovieList("Lista A", guille.getId());
		MovieController mc = new MovieController();
	
	
		rankingMovies.addPelicula(mc.getPeliculaById((long)120));
		rankingMovies.addPelicula(mc.getPeliculaById((long)121));
		rankingMovies.addPelicula(mc.getPeliculaById((long)122));
//		MovieListController mcl = new MovieListController();
	
		RepoMoviesLists.getInstance().addMovieList(rankingMovies);




		//Creo un Admin
		Rol adm = new Rol("Administrador");
		Usuario admin = new UsuarioBuilder("jon").pass("1234").rol(adm).build();
		RepoUsuarios.getInstance().addUsuario(admin);
		//Creo 3 usuarios


		Usuario usr1 = new UsuarioBuilder("usr1").pass("1234").rol(adm).build();
		RepoUsuarios.getInstance().addUsuario(usr1);

		Usuario usr2 = new UsuarioBuilder("usr2").pass("1234").rol(adm).build();
		RepoUsuarios.getInstance().addUsuario(usr2);

		Usuario usr3 = new UsuarioBuilder("usr3").pass("1234").rol(adm).build();
		RepoUsuarios.getInstance().addUsuario(usr3);
		//Creo un set de actores
		SummaryActor sa1 = new SummaryActor();
		SummaryActor sa2 = new SummaryActor();
		SummaryActor sa3 = new SummaryActor();
		SummaryActor sa4 = new SummaryActor();

		//a cada usuario le asigno actores favoritos
		usr1.addIdActorFavorito(sa1);
		usr1.addIdActorFavorito(sa2);
		usr1.addIdActorFavorito(sa3);
		usr2.addIdActorFavorito(sa3);
		usr2.addIdActorFavorito(sa4);
		usr3.addIdActorFavorito(sa3);

    }

    //Como administrador quiero poder ver los siguientes datos de un usuario
    @Test
    public void testGetDetalleUsuario() throws Exception{
    	
    	this.mockMvc.perform(get("/usuarios/{id}",1).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
            	.andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").value("Guille"))
                .andExpect(jsonPath("$.listaMovieList", hasSize(1)))
                .andExpect(jsonPath("$.actoresFavoritos", hasSize(0)))
                .andExpect(jsonPath("$.ultimaSesion").isEmpty())
                .andExpect(jsonPath("$.listaMovieList[0].nombre").value("Lista A"))
                .andExpect(jsonPath("$.listaMovieList[0].listaPeliculas", hasSize(3)))
                .andDo(print());
    	
    }

	//Como administrador quiero ver el ranking de actores favoritos de todos mis usuarios
	//(los actores que han sido más veces elegidos como favoritos)
	@Test
	public void testRankingActoresFavoritos() throws Exception{

		//utilizo el endpoint que me permite conocer los actores favoritos y
			//corroboro que conincide con los puestos a mano
		this.mockMvc2.perform(get("actores/rankingFavoritos").accept(MediaType.parseMediaType("application/json")))
				.andExpect(status().isOk())
				.andDo(print());
	}
}
