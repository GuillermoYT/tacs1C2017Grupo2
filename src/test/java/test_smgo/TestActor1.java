package test_smgo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import apiResult.MovieCastResult;
import model.Actor;
import tacs.ActorController;
import tacs.Application;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    //Como usuario quiero poder buscar actor por nombre
    @Test
    public void testBuscarActores() throws Exception {
    	this.mockMvc.perform(get("/actores")
    			.param("query", "Tom Hanks")
    			.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].nombre").value("Tom Hanks"))
                .andExpect(jsonPath("$.[0].imagePath").value("http://image.tmdb.org/t/p/w300//a14CNByTYALAPSGlwlmfHILpEIW.jpg"))
                .andDo(print());
    }

    //Como usuario quiero poder ver el detalle de un actor
    @Test
    public void testGetDetalleActor() throws Exception {
    	this.mockMvc.perform(get("/actores/{actor}",10).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
            	.andExpect(jsonPath("$.id").exists())
            	.andExpect(jsonPath("$.id").value(10))
            	.andExpect(jsonPath("$.imagePath").value("http://image.tmdb.org/t/p/w300//13YNM8lBKnK26MYd2Lp3OpU6JdI.jpg"))
            	.andExpect(jsonPath("$.biography").value("Robert \"Bob\" Peterson (born January 1961) is an American animator, screenwriter, director and voice actor, who has worked for Pixar since 1994. His first job was working as a layout artist and animator on Toy Story. He was nominated for an Oscar for his screenplay for Finding Nemo. Petersonis a co-director and the writer of Up, which gained him his second Oscar nomination. He also performed the voices of Roz in Monsters, Inc., Mr. Ray in Finding Nemo, Dug the dog and Alpha the dog in Up and in Dug's Special Mission, and the Janitor in Toy Story 3.\n\nPeterson  was born in Wooster, Ohio, his family moved to Dover, Ohio, where he graduated from Dover High School. He received his undergraduate degree from Ohio Northern University, and a Master's degree in mechanical engineering from Purdue University in 1986. While attending Purdue, he wrote and illustrated the comic strip Loco Motives for the Purdue Exponent. Prior to coming to Pixar, Peterson worked at Wavefront Technologies and Rezn8 Productions.\n\nIn 2008,  Peterson played the part of Terry Cane, a puppeteer in Dan Scanlon's first feature film Tracy. He also played additional voices in Tokyo Mater in 2008, and the voice of Mr. Ray for the Finding Nemo Submarine Voyage ride at Disneyland Park in 2007. His most  recent job at Pixar was voicing the Janitor at Sunnyside Daycare Center in Pixar's 11th film, Toy Story 3, which was released on June 18, 2010.\n\nDescription above from the Wikipedia article Bob  Peterson (animator), licensed under CC-BY-SA, full list of contributors on Wikipedia."))
            	.andExpect(jsonPath("$.listaPeliculas",hasSize(10)))
            	.andDo(print());
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
