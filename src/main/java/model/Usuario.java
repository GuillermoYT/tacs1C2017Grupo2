package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import util.PassEncoder;

public class Usuario implements UserDetails {
	
	private Long id;
	private String username;
	private String password; //nota va a ser un salted hash a futuro?
	private Rol rol;
	private List<SummaryActor> actoresFavoritos;
	private Date ultimaSesion;

	//CONSTRUCTOR
	public Usuario(long unId, String unUser, String unaPass) {
		id = unId;
		username = unUser;
		password = PassEncoder.encriptarPassword(unaPass);
		actoresFavoritos = new ArrayList<SummaryActor>();
	}
	
	public Usuario() {
		actoresFavoritos = new ArrayList<SummaryActor>();
	}

	//GETTERS and SETTERS
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = PassEncoder.encriptarPassword(password);
	}
	
	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public List<SummaryActor> getIdsActoresFavoritos() {
		return this.actoresFavoritos;
	}
	
	public void addIdActorFavorito(SummaryActor unId) {
		this.actoresFavoritos.add(unId);
	}
	
	public Date getUltimaSesion() {
		return ultimaSesion;
	}

	public void setUltimaSesion(Date ultimaSesion) {
		this.ultimaSesion = ultimaSesion;
	}
	
	//METODOS
	/**Metodo que elimina un actor favorito de la lista de actores favoritos
	 * 
	 * @param unActor
	 */
	public void removeIdActorFavorito(SummaryActor unActor) {
		List<SummaryActor> aclist = new ArrayList<>();
		aclist.addAll(this.actoresFavoritos);
		aclist.stream().forEach(ac -> {
			if (ac.getId() == unActor.getId())
				actoresFavoritos.remove(ac);						
		});
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> auths = 
				new HashSet<GrantedAuthority>(1);
		if (rol.getName().equals("Administrador")) {
			auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		} else {
			auths.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		return auths;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public boolean sosAdmin() {
		if (rol.getName().equals("Administrador")) {
			return true;
		}
		
		return false;
	}


//	public List<SummaryActor> getActoresFavoritos() {
//		return actoresFavoritos;
//	}
}
