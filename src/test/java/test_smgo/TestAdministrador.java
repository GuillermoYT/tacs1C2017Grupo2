package test_smgo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import creacionales.UsuarioBuilder;
import model.Rol;
import model.Usuario;
import repos.RepoUsuarios;
import tacs.Application;
import tacs.UserController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class TestAdministrador {

	private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(new UserController()).build();
		Rol usr = new Rol("Usuario");
	
		Usuario guille = new UsuarioBuilder("Guille").pass("1234").rol(usr).build();
		RepoUsuarios.getInstance().addUsuario(guille);
    }

    //Como administrador quiero poder ver los siguientes datos de un usuario
    @Test
    public void testGetDetalleUsuario() throws Exception{
    	
    	MvcResult result = this.mockMvc.perform(get("/usuarios/{id}",1).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andReturn();
    	String content = result.getResponse().getContentAsString();
    	System.out.println(content);
    	
    }
}
