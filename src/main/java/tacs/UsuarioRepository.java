package tacs;

import org.springframework.data.mongodb.repository.MongoRepository;

import model.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {

	public Usuario findByUsername(String username);
	public Usuario findById(String id);
	
}
