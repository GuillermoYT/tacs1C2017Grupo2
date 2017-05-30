package tacs;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import model.Usuario;
import repos.RepoUsuarios;

@Service
public class UserServiceImpl implements UserDetailsService {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Usuario usuario = RepoUsuarios.getInstance().buscarUsuario(userName);
		if(usuario == null)
			throw new UsernameNotFoundException(userName);
		 Date time = new Date();
		 usuario.setUltimaSesion(time);
		return usuario;
	}

}
