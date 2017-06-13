package test_smgo;

import java.util.List;

import org.junit.Before;

import creacionales.UsuarioBuilder;
import model.MovieList;
import model.RankingActor;
import model.Rol;
import model.Usuario;
import repos.RepoMoviesLists;
import repos.RepoUsuarios;
import tacs.MovieController;
import tacs.MovieListController;

public class AbstractTest {
	private Usuario guille;
	
	@Before
	public void setUp() throws Exception {

	//	Rol adm = new Rol("Administrador");
		Rol usr = new Rol("Usuario");
	
		guille = new UsuarioBuilder("Guille").pass("1234").rol(usr).build();
		RepoUsuarios.getInstance().addUsuario(guille);
	
		MovieList rankingMovies = new MovieList("Lista A", guille.getId());
		MovieController mc = new MovieController();
	
	
		rankingMovies.addPelicula(mc.getPeliculaById((long)120));
		rankingMovies.addPelicula(mc.getPeliculaById((long)121));
		rankingMovies.addPelicula(mc.getPeliculaById((long)122));
		MovieListController mcl = new MovieListController();
	
		RepoMoviesLists.getInstance().addMovieList(rankingMovies);
		RepoUsuarios.getInstance().addUsuario(guille);
		
/*		List<RankingActor> ranking = mcl.getRankingFromActorsByMovies(rankingMovies.getId());
		ranking.forEach(ac -> System.out.println("ID: "+ ac.getMovieActor() + " -- value: "+ ac.getCantRepeticiones()));			
*/		
		
	}
}