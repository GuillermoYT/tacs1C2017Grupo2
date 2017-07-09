package test_smgo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import creacionales.UsuarioBuilder;
import model.Rol;
import model.Usuario;
import tacs.JwtAuthenticationRequest;
import tacs.JwtTokenUtil;
import tacs.LoginController;
import tacs.SecurityConfiguration;
import tacs.UserController;
import tacs.UserServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestLogin {
	
	private UserController userController = new UserController();
	private UserServiceImpl userServiceImpl = new UserServiceImpl();
	private LoginController loginController = new LoginController();

	private AuthenticationManager authenticationManager = new AuthenticationManager() {
		
		@Override
		public Authentication authenticate(Authentication authentication) throws AuthenticationException {
			// TODO Auto-generated method stub
			return null;
		}
	};

    private JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private RepoUsuarios repoU = RepoUsuarios.getInstance();
	
	@Before
	public void setUp() {
		ReflectionTestUtils.setField(userServiceImpl, "userRepo", repoU);
		ReflectionTestUtils.setField(userController, "repo", repoU);
		ReflectionTestUtils.setField(loginController, "authenticationManager", authenticationManager);
		ReflectionTestUtils.setField(loginController, "userDetailsService", userServiceImpl);
		ReflectionTestUtils.setField(loginController, "jwtTokenUtil", jwtTokenUtil);
	}
		
	@Test
	public void test() throws JsonProcessingException, AuthenticationException {
		Usuario pepe = new UsuarioBuilder("Nelson").pass("1234").rol(new Rol("Usuario")).build();
		//Crear Usuario Pepe
		assertEquals(201, userController.addUser(pepe).getStatus());

		JwtAuthenticationRequest authenticationRequest = new JwtAuthenticationRequest();
		authenticationRequest.setUsername("Nelson");
		authenticationRequest.setPassword("1234");
//		assertEquals(11, loginController.createAuthenticationToken(authenticationRequest));
		
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(loginController.createAuthenticationToken(authenticationRequest));
		System.out.println(json);
	}

}
