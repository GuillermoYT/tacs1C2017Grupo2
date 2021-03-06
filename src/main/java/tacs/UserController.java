package tacs;

import java.util.ArrayList;
import java.util.List;

import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import creacionales.UsuarioBuilder;
import hierarchyOfExceptions.UserNotFoundException;
import util.BooleanObj;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/usuarios")
public class UserController extends AbstractController{

	@Autowired
	private FullDataUsuario userfull;

	@Autowired
	private ActorController controladorActores = new ActorController();
	
	@Autowired
	private UsuarioRepository repo;
	
	@RequestMapping(value="/crearDb")
	public String crearUsuarioDb() {
		Usuario guille = new UsuarioBuilder("Guille").pass("1234").rol(new Rol("Administrador")).build();
		Usuario alvaro = new UsuarioBuilder("Alvaro").pass("1234").rol(new Rol("Administrador")).build();
		Usuario martin = new UsuarioBuilder("martin").pass("1234").rol(new Rol("Administrador")).build();
		Usuario julio = new UsuarioBuilder("Julio").pass("1234").rol(new Rol("Administrador")).build();
		Usuario jon = new UsuarioBuilder("Jon").pass("1234").rol(new Rol("Administrador")).build();
		
//		guille.setId(0);
//		alvaro.setId(1);
//		martin.setId(2);
//		julio.setId(3);
//		jon.setId(4);
		
		repo.save(martin);
		repo.save(guille);
		repo.save(alvaro);
		repo.save(julio);
		repo.save(jon);
		
		return "{\"result\":\"Ok\"}";
	}
	
	// Lista de Usuarios
	@RequestMapping(method = RequestMethod.GET)
	public List<Usuario> getUsuarios() {
		logger.info("getUsuarios()");
		return repo.findAll();
	}	
	
	// Mostrar Detalle de un usuario
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public FullDataUsuario getUsuarioById(@PathVariable("id") String id) throws UserNotFoundException {
		logger.info("getUsuarioById()");

		if(userfull!=null){
			if(userfull.getId().equals(id)){
				return userfull;
			}
		}
		userfull.reload(repo.findById(id));
		return  userfull;

	}
	
	// Crear nuevo usuario
	@RequestMapping(method = RequestMethod.POST)
	public Response addUser(@RequestBody Usuario user) {
		logger.info("addUser()");
		if(getUsuarios().stream().filter(u -> user.getUsername().equalsIgnoreCase(u.getUsername())).count()==0){
			user.setRol(new Rol("Usuario"));
			repo.insert(user);
			return new Response(201, "El usuario " + user.getUsername() + " ha sido creado");
		}else{
			return new Response(202, "El nombre de usuario ya existe");
		}
		
	}

	// es mi actor favorito
	@RequestMapping(value = "/{usuario}/actorFavorito/{idActor}", method = RequestMethod.GET)
	public BooleanObj esActorFavorito(@PathVariable("usuario") String id, @PathVariable("idActor") long idActor) throws UserNotFoundException {
		logger.info("esActorFacvorito()");
		Usuario user;
		//user = RepoUsuarios.getInstance().getUserById(id);
		user = repo.findById(id);
		BooleanObj bool = new BooleanObj(user.getIdsActoresFavoritos().stream().anyMatch(actor -> actor.getId() == (int)idActor ));
		return bool;
	}		
	
	// Lista de actores favoritos
	@RequestMapping(value = "/{usuario}/actoresFavoritos", method = RequestMethod.GET)
	public List<SummaryActor> getActoresFavoritos(@PathVariable("usuario") String id) {
		logger.info("getActoresFacvoritos()");
		List<SummaryActor> actoresFavoritos = new ArrayList<SummaryActor>();
		Usuario user;
		try {
			//user = RepoUsuarios.getInstance().getUserById(id);
			user = repo.findById(id);
		} catch(Exception e) {
			logger.error("Usuario inexistente");
			return null;
		}
		return user.getIdsActoresFavoritos();
	}		
	
	// Marcar como favorito a un actor
	@RequestMapping(value = "/{usuario}/favorito/{actor}", method = RequestMethod.PUT)
	public Response addActorFavorito(@PathVariable("usuario") String usuario, @PathVariable("actor") long actor) {
		logger.info("addActorFavorito()"+ String.valueOf(actor));
		
		System.out.println("addActorFavorito()"+ String.valueOf(actor));
		try {
			//RepoUsuarios.getInstance().getUserById(usuario).addIdActorFavorito(controladorActores.getSumActorById(actor));
			Usuario user = repo.findById(usuario);
			user.addIdActorFavorito(controladorActores.getSumActorById(actor));
			repo.save(user);
		} catch(Exception e) {
			logger.error(e.getMessage());
			return new Response(404, "Usuario inexistente");
		}
		
		return new Response(200, "Accion realizada correctamente");
	}
	
	// Desmarcar como favorito a un actor
	@RequestMapping(value = "/{usuario}/favorito/{actor}", method = RequestMethod.DELETE)
	public Response removeActorFavorito(@PathVariable("usuario") String usuario, @PathVariable("actor") long actor) {
		logger.info("removeActorFavorito()");
		try {
//			RepoUsuarios.getInstance().getUserById(usuario).removeIdActorFavorito(controladorActores.getSumActorById(actor));
			Usuario user = repo.findById(usuario);
			user.removeIdActorFavorito(controladorActores.getSumActorById(actor));
			repo.save(user);
		} catch(Exception e) {
			logger.error(e.getMessage());
			return new Response(404, "Usuario inexistente");
		}
		
		return new Response(200, "Accion realizada correctamente");
	}
}
