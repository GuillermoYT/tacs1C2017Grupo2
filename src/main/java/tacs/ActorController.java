package tacs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import apiResult.ActorCastResult;
import apiResult.ActorListResult;
import apiResult.ActorResult;
import apiResult.SummaryActorResult;
import model.Actor;
import model.FavoritoActor;
import model.SummaryActor;
import model.Usuario;
import util.FavComparator;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/actores")
public class ActorController extends AbstractController {
	
	@Autowired
	private UsuarioRepository userRepo;

	// Lista de actores
	//@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public List<SummaryActor> getActores(@RequestParam("query") Optional<String> queryString) {
		logger.info("getActores()");
		if (queryString.isPresent()) {
			logger.info("Request url: " + BASE_URL + "search/person?" + API_KEY + "&query=" + queryString.get());
			ActorListResult resultadoRequest = api.getForObject(BASE_URL + "search/person?" + API_KEY + "&query=" + queryString.get(), ActorListResult.class);
			return resultadoRequest.toActorList();
		}
		
		ActorListResult resultadoRequest = api.getForObject(BASE_URL + "/person/popular?" + API_KEY, ActorListResult.class);
		return resultadoRequest.toActorList();
	}

	//Detall de un actor
	@RequestMapping(value="/{actor}", method = RequestMethod.GET)
	public Actor getActorById(@PathVariable("actor") long idactor){
		logger.info("getActor()");
		ActorResult actorR = api.getForObject(BASE_URL + "person/"+ idactor + "?" + API_KEY, ActorResult.class);
		ActorCastResult actorCastR = api.getForObject(BASE_URL+ "person/"+ idactor +"/movie_credits?"+ API_KEY, ActorCastResult.class);
		Actor actor = actorR.toActor();
		actor.listMovie(actorCastR);
		return actor;
	}
	
	public SummaryActor getSumActorById(long idActor) {
		return api.getForObject(BASE_URL + "person/"+ idActor + "?" + API_KEY, SummaryActorResult.class).toSumActor();
	}
	
	// Ranking de actores favoriteados
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/rankingFavoritos", method = RequestMethod.GET)
	public List<FavoritoActor> rankingActores(){
		logger.info("rankingActores()");
		List<FavoritoActor> rankingActores = new ArrayList<FavoritoActor>();
		List<Usuario> usuarios = userRepo.findAll();
		Set<SummaryActor> actoresYaPresentes = new HashSet<SummaryActor>();
		for(Usuario u : usuarios) {
			for(SummaryActor a : u.getIdsActoresFavoritos()) {
				// si ya estaba le sumo uno, sino lo agrego a la lista
				if (actoresYaPresentes.contains(a)) {
					for(FavoritoActor fa : rankingActores) {
						if (fa.getActor().equals(a)) {
							fa.masUnFav();
						}
					}
				} else {
					rankingActores.add(new FavoritoActor(a, 1));
					actoresYaPresentes.add(a);
				}
			}
		}
		rankingActores.sort(new FavComparator());
		return rankingActores;
	}
	
}
