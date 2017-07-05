package test_smgo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tacs.MovieListRepository;
import tacs.UsuarioRepository;

@Configuration
public class TestConfiguration {
	
	@Bean
	public UsuarioRepository usuarioRepository() {
		return new RepoUsuarios();
	}
	
	@Bean
	public MovieListRepository movieListRepository() {
		return new RepoMoviesLists();
	}
	
}
