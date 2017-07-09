package test_smgo;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;


import creacionales.UsuarioBuilder;
import model.Rol;
import model.Usuario;
import tacs.JwtAuthenticationRequest;
import tacs.LoginController;
import tacs.UserController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestLogin {
	private LoginController loginController = new LoginController();
	private UserController userController = new UserController();
	private RepoUsuarios repoU = RepoUsuarios.getInstance();

	@Before
	public void setUp() {
		ReflectionTestUtils.setField(userController, "repo", repoU);	
		Usuario pepe = new UsuarioBuilder("pepe").pass("1234").rol(new Rol("Usuario")).build();
		repoU.addUsuario(pepe);

	}
	
	@Test
	public void testLogueo() {
		System.out.println("----------------------------------------------");
		System.out.println(repoU.findAll().size());
		System.out.println(loginController.createAuthenticationToken(new JwtAuthenticationRequest("pepe", "1234")));
	}
}