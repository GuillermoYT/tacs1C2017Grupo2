package tacs;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import creacionales.UsuarioBuilder;
import model.MovieList;
import model.RankingActor;
import model.Rol;
import model.SummaryActor;
import model.Usuario;
import repos.RepoMoviesLists;
import repos.RepoUsuarios;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		
		ConfigHolder.getInstance();

		Rol adm = new Rol("Administrador");
		Rol usr = new Rol("Usuario");

		SummaryActor actorFavorito1 = new SummaryActor(1,"algo","Tom Hanks");
		SummaryActor actorFavorito2 = new SummaryActor(2,"algo","Tom Cruise");
		SummaryActor actorFavorito3 = new SummaryActor(3,"algo","Ricardo Darin");
		SummaryActor actorFavorito4 = new SummaryActor(4,"algo","Keanu Reeves");
		
		Usuario guille = new UsuarioBuilder("Guille").pass("1234").rol(usr).build();
		Usuario alvaro = new UsuarioBuilder("Alvaro").pass("1234").rol(adm).build();
		Usuario martin = new UsuarioBuilder("martin").pass("1234").rol(adm).build();
		Usuario julio = new UsuarioBuilder("Julio").pass("1234").rol(adm).build();
		Usuario jon = new UsuarioBuilder("Jon").pass("1234").rol(adm).build();
		
		guille.setId(0);
		alvaro.setId(1);
		martin.setId(2);
		julio.setId(3);
		jon.setId(4);
		
		guille.addIdActorFavorito(actorFavorito1);
		guille.removeIdActorFavorito(actorFavorito1);
		guille.addIdActorFavorito(actorFavorito1);
		guille.addIdActorFavorito(actorFavorito2);
		
		alvaro.addIdActorFavorito(actorFavorito2);
		julio.addIdActorFavorito(actorFavorito2);
		
		MovieList rankingMovies = new MovieList("Lista A", Long.valueOf(guille.getId()));
		MovieController mc = new MovieController();
		rankingMovies.addPelicula(mc.getPeliculaById((long)120));
		rankingMovies.addPelicula(mc.getPeliculaById((long)121));
		rankingMovies.addPelicula(mc.getPeliculaById((long)122));
		
		RepoMoviesLists.getInstance().addMovieList(rankingMovies);
		
		RepoUsuarios.getInstance().addUsuario(guille);
		RepoUsuarios.getInstance().addUsuario(alvaro);
		RepoUsuarios.getInstance().addUsuario(martin);
		RepoUsuarios.getInstance().addUsuario(julio);
		RepoUsuarios.getInstance().addUsuario(jon);
		
		MovieListController mcl = new MovieListController();
		List<RankingActor> ranking = mcl.getRankingFromActorsByMovies(rankingMovies.getId());
		//ranking.forEach(ac -> System.out.println("ID: "+ ac.getMovieActor() + " -- value: "+ ac.getCantRepeticiones()));			
		
		MovieList movielist1 = new MovieList("Lista A", guille.getId());
		movielist1.addPelicula(mc.getPeliculaById((long)120));
		MovieList movielist2 = new MovieList("Lista B", guille.getId());
		movielist2.addPelicula(mc.getPeliculaById((long)122));
		
		
		
		RepoMoviesLists.getInstance().addMovieList(movielist1);
		RepoMoviesLists.getInstance().addMovieList(movielist2);
		RepoMoviesLists.getInstance().addMovieList(new MovieList("Lista C", 1l));
	}
}

