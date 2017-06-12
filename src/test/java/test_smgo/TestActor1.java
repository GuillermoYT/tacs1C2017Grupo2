package test_smgo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.awt.print.Printable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import tacs.ActorController;
import tacs.Application;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration

public class TestActor1 {

	private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(new ActorController()).build();
    }

    @Test
    public void testGetActores() throws Exception {
    	MvcResult result = this.mockMvc.perform(get("/actores").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andReturn();
    	String content = result.getResponse().getContentAsString();
    	System.out.println(content);
    }

    //Test wihtout mock
    @Test
    public void getNombre() throws Exception {
        String nameExpectedValue="EsteEsElNombre";

        Actor actor=new Actor();
        actor.setNombre(nameExpectedValue);
        String nameActualValue=actor.getNombre();

        assertEquals(nameExpectedValue, nameActualValue);
    }

    @Test
    public void getListaPeliculas() throws Exception {
        MovieCastResult movie1 = new MovieCastResult();
        MovieCastResult movie2 = new MovieCastResult();
        MovieCastResult movie3 = new MovieCastResult();
        List<MovieCastResult> listExpectedValue = new ArrayList<MovieCastResult>();
        listExpectedValue.add(movie1);
        listExpectedValue.add(movie2);
        listExpectedValue.add(movie3);

        Actor actor=new Actor();
        actor.setListaPeliculas(listExpectedValue);
        listExpectedValue.add(new MovieCastResult());
        assertEquals(actor.getListaPeliculas(), listExpectedValue);
    }

}
