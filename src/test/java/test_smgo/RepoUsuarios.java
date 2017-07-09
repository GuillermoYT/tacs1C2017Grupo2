package test_smgo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hierarchyOfExceptions.UserNotFoundException;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import model.Usuario;
import tacs.UsuarioRepository;;

@Component
public class RepoUsuarios implements UsuarioRepository {

	private static RepoUsuarios instance;
	private static List<Usuario> usuarios = new ArrayList<Usuario>();
	private static Integer counter = 1;
	
	public static RepoUsuarios getInstance() {
		if (instance == null) {
			instance = new RepoUsuarios();
		}
		return instance;
	}
	
	public void addUsuario(Usuario unUsuario) {
		unUsuario.setId(counter.toString());
		usuarios.add(unUsuario);		
		counter++;
	}

	public void cleanRepo(){
		instance = null;
		usuarios = new ArrayList<Usuario>();
		counter = 1;
	}

	public  Usuario buscarUsuario(String nombre, String pass) throws Exception{
		List<Usuario> auxUsers = usuarios.stream().filter(usuario -> usuario.getUsername().toUpperCase().equals(nombre.toUpperCase()) && usuario.getPassword().equals(pass)).collect(Collectors.toList());
		
		if(auxUsers.isEmpty()){
			throw new UserNotFoundException("Usuario Incorrecto");
		}
		
		return auxUsers.get(0);
	}
	
	@Override
	public  Usuario findByUsername(String nombre){
		List<Usuario> auxUsers = usuarios.stream().filter(usuario -> usuario.getUsername().toUpperCase().equals(nombre.toUpperCase())).collect(Collectors.toList());
		
		return auxUsers.get(0);
	}
	
	@Override
	public Usuario findById(String id) {
		Usuario aux = null;
		for(Usuario u : usuarios) {
			if (u.getId().equals(id)) {
				aux = u;
			}
		}
		
		//throw new UserNotFoundException("Usuario invalido.");
		return aux;
	}
	
	@Override
	public List<Usuario> findAll() {
		return usuarios;
	}

	
	//para que no rompa la interfaz, no se usan
	@Override
	public List<Usuario> findAll(Sort arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Usuario> List<S> findAll(Example<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Usuario> List<S> findAll(Example<S> arg0, Sort arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Usuario> S insert(S arg0) {
		this.addUsuario((Usuario)arg0);
		return null;
	}

	@Override
	public <S extends Usuario> List<S> insert(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Usuario> List<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Usuario> findAll(Pageable arg0) {
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
	public void delete(Usuario arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Iterable<? extends Usuario> arg0) {
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
	public Iterable<Usuario> findAll(Iterable<String> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario findOne(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Usuario> S save(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Usuario> long count(Example<S> arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends Usuario> boolean exists(Example<S> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <S extends Usuario> Page<S> findAll(Example<S> arg0, Pageable arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Usuario> S findOne(Example<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}	
	
}
