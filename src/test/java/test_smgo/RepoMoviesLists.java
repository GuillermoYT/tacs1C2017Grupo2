package test_smgo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import model.MovieList;
import tacs.MovieListRepository;

public class RepoMoviesLists implements MovieListRepository {

	private static RepoMoviesLists instance;
	private static List<MovieList> moviesLists = new ArrayList<MovieList>();
	private static Integer counter = 1;
	
	public static RepoMoviesLists getInstance() {
		if (instance == null) {
			instance = new RepoMoviesLists();
		}
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public MovieList insert(MovieList unMovieList) {
		unMovieList.setId(counter.toString());
		moviesLists.add(unMovieList);		
		counter++;
		
		return unMovieList;
	}
	
	@Override
	public  MovieList findById(String idMovieList){
		Optional<MovieList> lista = moviesLists.stream().filter(ml -> ml.getId().equals(idMovieList)).findFirst();
		return lista.isPresent() ? lista.get() : null;
	}
	
	@Override
	public  List<MovieList> findByOwnerId(String ownerId){
		List<MovieList> listas = moviesLists.stream().filter(ml -> ml.getOwnerId().equals(ownerId)).collect(Collectors.toList());;
		return listas;
	}
	
	@Override
	public List<MovieList> findAll() {
		return moviesLists;
	}
	
	@Override
	public <S extends MovieList> S save(S movieList) {
		
		MovieList ml = findById(movieList.getId());
		
		if(ml != null) {
			moviesLists.set(moviesLists.indexOf(ml), movieList);
		}
		
		return movieList;
	}

	
	//para que no rompa la interfaz, no se usan
	@Override
	public List<MovieList> findAll(Sort arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends MovieList> List<S> findAll(Example<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends MovieList> List<S> findAll(Example<S> arg0, Sort arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends MovieList> List<S> insert(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends MovieList> List<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<MovieList> findAll(Pageable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(MovieList arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Iterable<? extends MovieList> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<MovieList> findAll(Iterable<String> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MovieList findOne(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends MovieList> long count(Example<S> arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends MovieList> boolean exists(Example<S> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <S extends MovieList> Page<S> findAll(Example<S> arg0, Pageable arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends MovieList> S findOne(Example<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}	

}
