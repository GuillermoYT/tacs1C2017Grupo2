package tacs;

import test_smgo.RepoMoviesLists;
import test_smgo.RepoUsuarios;

public class ServiceLocator {

	private static ServiceLocator instance;
	public UsuarioRepository repoUsuarios;
	public MovieListRepository repoML;
	
	public static ServiceLocator getInstance() {
		if (instance == null) {
			instance = new ServiceLocator();
		}
		
		return instance;
	}
	
	public ServiceLocator() {
		
	}
	
	public void setProduccion() {
		
	}
	
	public void setTest() {
		repoUsuarios = new RepoUsuarios();
		repoML = new RepoMoviesLists();
	}
	
}
