package tacs;

import org.junit.Before;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class LoginControllerTest {

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(new LoginController()).build();
    }
    @Test
    public void createAuthenticationToken() throws Exception {
        //String LOGIN_REQUEST = "{\"username\" : \"Jon\",\"password\" : \"1234\"}";
        this.mockMvc.perform(formLogin("/auth").user("Guille").password("1234"))
//          .andExpect(status().isOk())
             .andDo(print());

    }
    @Test
    public void createAuthenticationToken2() throws Exception {

        this.mockMvc.perform(post("/auth").param("username","Jon").param("password","1234"))

//                .andExpect(status().isOk())
                .andDo(print());


    }

    @Test
    public void refreshAndGetAuthenticationToken() throws Exception {
    }

}