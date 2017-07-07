package tacs;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import apiResult.MovieResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import model.MovieList;
import model.Pelicula;
import model.Ranking;
import model.RankingActor;
import model.Response;
import util.LongsWrapper;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/movielists")
public class MovieListController extends AbstractController{
	
	@Autowired
	private MovieListRepository repo;

	// Crear lista
	@RequestMapping(method=RequestMethod.POST)
	public Response  createMovielist(@RequestBody String nombre, @RequestParam("user") String ownerId){
		logger.info("createMovieList()");
		
		try {
			//RepoMoviesLists.getInstance().addMovieList(aMovieList);
			//Si ya esta creada, no hago nada 
			if(repo.findByOwnerId(ownerId).stream().filter(ml -> ml.getNombre()==nombre).count()==0){
				MovieList aMovieList = new MovieList(nombre, ownerId);
				repo.insert(aMovieList);
			}
		}catch (Exception e){
			logger.error("Error al crear lista: " + e.getMessage() + "\nCausado por: " + e.getCause());
			e.printStackTrace();
		}
		return new Response(200, "Lista creada correctamente");
	}

	// Agregar pelicula a la lista
	@RequestMapping(value="/{movielistId}/{movieId}", method=RequestMethod.PUT)
	public Response addMovieToList(@PathVariable("movielistId") String movielistId, @PathVariable Long movieId){ //@RequestBody Pelicula peli){
		try {
			MovieResult pelicula = api.getForObject(BASE_URL + "/movie/" + movieId.toString() + "?" + API_KEY, MovieResult.class);
//			MovieList movieList = RepoMoviesLists.getInstance().getMovieList(movielistId);
			MovieList movieList = repo.findById(movielistId);
			movieList.addPelicula(pelicula.toMovie());
			repo.save(movieList);
			logger.info("addMovieList()");
		}catch (Exception e){
			logger.error("Usuario inexistente");
			e.printStackTrace();
		}
		return new Response(200, "Pelicula agregada correctamente");
	}
	
	// Obtener lista por id
	@RequestMapping(value="/{movielist}", method=RequestMethod.GET)
	public MovieList getMovieList(@PathVariable("movielist") String movielist){
		logger.info("getMoviesForMoviesListId()");
		
		//return RepoMoviesLists.getInstance().getMovieList(movielist);
		return repo.findById(movielist);
	}

	// Obtener listas de un usuario
	@RequestMapping(value="/search",method=RequestMethod.GET)
	public List<MovieList> getMovieListsByUser(@RequestParam("ownerId") String ownerId){
		logger.info("getMovieListsByUser()");
		
		//return RepoMoviesLists.getInstance().getMovieListByUser(ownerId);
		return repo.findByOwnerId(ownerId);
	}
	
//	// Obtener todas las listas
	@RequestMapping(method=RequestMethod.GET)
	public List<MovieList> getList(){
		logger.info("getAllMoviesLists()");
//		return RepoMoviesLists.getInstance().getAllMovieLists();
		return repo.findAll();
	}
		
	// Eliminar varias peliculas de la lista
	@RequestMapping(value="/{movieListId}", method=RequestMethod.DELETE)
	public Response deleteMovieFromUserListById(@PathVariable("movieListId") String movieListId, @RequestBody LongsWrapper idMovies) {
		logger.info("deleteMoviesFromUserListbyId()");
		
//		idMovies.getIds().stream().forEach(mId ->
//		RepoMoviesLists.getInstance().getMovieList(movieListId).getListaPeliculas().removeIf(mv -> mv.getId()==mId));
		MovieList ml = repo.findById(movieListId);
		idMovies.getIds().stream().forEach(mId -> ml.getListaPeliculas().removeIf(mv -> mv.getId()==mId));
		
		repo.save(ml); //actualizo en db
	
		return new Response(200, "Pelicula eliminada de la lista");
	}
	
	// Comparar dos listas de peliculas
	@RequestMapping(value="/compare", method=RequestMethod.GET)
	public List<Pelicula> getMovielistComparison(@RequestParam("list1") String list1, @RequestParam("list2") String list2) {
		logger.info("getMovielistComparison()" + "Listas: " +list1 + " - "+ list2);
		
		//return RepoMoviesLists.getInstance().getMovieList(list1).interseccion(RepoMoviesLists.getInstance().getMovieList(list2));
		return repo.findById(list1).interseccion(repo.findById(list2));
	}
	
	// Ranking de actores que se repiten en las peliculas de una lista
	@RequestMapping(value="/actoresRepetidos/{movieListId}", method=RequestMethod.GET)
	public List<RankingActor> getRankingFromActorsByMovies(@PathVariable("movieListId") String movieListId) {
		logger.info("getRankingFromMovie()");

//		List<Pelicula> ml = RepoMoviesLists.getInstance().getMovieList(movieListId).getListaPeliculas();
		List<Pelicula> ml = repo.findById(movieListId).getListaPeliculas();
		Ranking rk = new Ranking();
		ml.forEach(p-> {
			rk.processRankingFromActorByMovieList(Connection.getActorsMovie(Long.toString(p.getId())));
		} );
		
		List<RankingActor> rankingActores = new ArrayList<>();
		
		rk.getRankingFromActorsByMovies().forEach((k,v)-> {
			rankingActores.add(new RankingActor(v, v.getCount()));
		});

		return rankingActores.stream().sorted((u1,u2)-> {
            return Integer.valueOf(u2.getCantRepeticiones()).compareTo(Integer.valueOf(u1.getCantRepeticiones()));})
            .collect(Collectors.toList());
	}
}
