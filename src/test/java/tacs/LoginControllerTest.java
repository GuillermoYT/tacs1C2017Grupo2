package tacs;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class LoginControllerTest {

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(new LoginController()).build();
    }
    @Test
    public void createAuthenticationToken() throws Exception {

        MvcResult result = this.mockMvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"Guille\",\"password\":\"1234\"}"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    public void refreshAndGetAuthenticationToken() throws Exception {
    }

}