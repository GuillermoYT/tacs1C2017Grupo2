package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import tacs.MovieListRepository;

/**
 * Created by jon on 16/04/17.
 */
public class FullDataUsuario {

    private List<MovieList> listaMovieList;
    private String id;
    private String username;
    private String password;
    private Rol rol;
    private List<SummaryActor> actoresFavoritos;
    private Date ultimaSesion;
    
    @Autowired
    private MovieListRepository repoML;

    public FullDataUsuario(Usuario user) {
    	try {
    		listaMovieList = repoML.findAll().stream().filter(movieList -> movieList.getOwnerId()==user.getId()).collect(Collectors.toList());
    	} catch(Exception e) {
    		
    	}
        id = user.getId();
        username = user.getUsername();
        password = user.getPassword();
        rol = user.getRol();
        actoresFavoritos = user.getIdsActoresFavoritos();
        ultimaSesion = user.getUltimaSesion();
    }


    public List<MovieList> getListaMovieList() {
        return this.listaMovieList;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Rol getRol() {
        return rol;
    }

    public List<SummaryActor> getActoresFavoritos() {
        return actoresFavoritos;
    }


	public Date getUltimaSesion() {
		return ultimaSesion;
	}
   
    
}
