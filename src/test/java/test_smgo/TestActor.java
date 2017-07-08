package test_smgo;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import model.Actor;
import tacs.ActorController;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestActor extends TestActores{

	private ActorController actorController = new ActorController();
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private RepoUsuarios repoU = RepoUsuarios.getInstance();
	
	@Before
	public void setUp() {
		ReflectionTestUtils.setField(actorController, "userRepo", repoU);
	}
	

    //Como usuario quiero poder ver el detalle de un actor
   @Test
   public void testGetDetalleActor() throws Exception {
	   Actor actor = actorController.getActorById(10);
	   assertEquals(10, actor.getId());
	   assertEquals("http://image.tmdb.org/t/p/w300//13YNM8lBKnK26MYd2Lp3OpU6JdI.jpg", actor.getImagePath());
	   assertEquals("Robert \"Bob\" Peterson (born January 1961) is an American animator, screenwriter, director and voice actor, who has worked for Pixar since 1994. His first job was working as a layout artist and animator on Toy Story. He was nominated for an Oscar for his screenplay for Finding Nemo. Petersonis a co-director and the writer of Up, which gained him his second Oscar nomination. He also performed the voices of Roz in Monsters, Inc., Mr. Ray in Finding Nemo, Dug the dog and Alpha the dog in Up and in Dug's Special Mission, and the Janitor in Toy Story 3.\n\nPeterson  was born in Wooster, Ohio, his family moved to Dover, Ohio, where he graduated from Dover High School. He received his undergraduate degree from Ohio Northern University, and a Master's degree in mechanical engineering from Purdue University in 1986. While attending Purdue, he wrote and illustrated the comic strip Loco Motives for the Purdue Exponent. Prior to coming to Pixar, Peterson worked at Wavefront Technologies and Rezn8 Productions.\n\nIn 2008,  Peterson played the part of Terry Cane, a puppeteer in Dan Scanlon's first feature film Tracy. He also played additional voices in Tokyo Mater in 2008, and the voice of Mr. Ray for the Finding Nemo Submarine Voyage ride at Disneyland Park in 2007. His most  recent job at Pixar was voicing the Janitor at Sunnyside Daycare Center in Pixar's 11th film, Toy Story 3, which was released on June 18, 2010.\n\nDescription above from the Wikipedia article Bob  Peterson (animator), licensed under CC-BY-SA, full list of contributors on Wikipedia.", actor.getBiography());
	   assertEquals(11, actor.getListaPeliculas().size());
   }
	
	
//	@Test
//	public void testCantidadActores() throws Exception{
//		mockMvc.perform(MockMvcRequestBuilders.get("/actores")).andExpect(status().isOk())
//		.andExpect(content().contentType("application/json;charset=UTF-8"))
//		.andExpect(jsonPath("$", hasSize(3)));
//	}
//
//	@Test
//	public void testListarActores() throws Exception{		
//		mockMvc.perform(MockMvcRequestBuilders.get("/actores")).andExpect(status().isOk())
//				.andExpect(content().contentType("application/json;charset=UTF-8"))
//				.andExpect(jsonPath("$", hasSize(3)))
//                .andExpect(jsonPath("$[0].id", is(0)))
//				.andExpect(jsonPath("$[0].nombre").value("Tom Cruise"))
//				.andExpect(jsonPath("$[0].edad").value(50))
//				.andExpect(jsonPath("$[0].lugarNac").value("New York, USA"))
//				.andExpect(jsonPath("$[2].id", is(2)))
//				.andExpect(jsonPath("$[2].nombre").value("Ricardo Darin"))
//				.andExpect(jsonPath("$[2].edad").value(53))
//				.andExpect(jsonPath("$[2].lugarNac").value("Buenos Aires, ARG"));
//	}
//
//	@Test
//	public void testActoresPorId() throws Exception{		
//		mockMvc.perform(MockMvcRequestBuilders.get("/actores/0")).andExpect(status().isOk()).andDo(print())
//				.andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andExpect(jsonPath("$.id", is(0)))
//				.andExpect(jsonPath("$.nombre").value("Tom Cruise"))
//				.andExpect(jsonPath("$.edad").value(50))
//				.andExpect(jsonPath("$.lugarNac").value("New York, USA")).andDo(print());
//				
//	}
//	
//	@Test
//	public void testCrearActor() throws Exception{
////		mockMvc.perform(MockMvcRequestBuilders.post("/actores"))
//	}
	
}
