package tacs;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import model.MovieList;

public interface MovieListRepository extends MongoRepository<MovieList, String> {

	public MovieList findById(String id);
	public List<MovieList> findByOwnerId(long ownerId);
	
}
