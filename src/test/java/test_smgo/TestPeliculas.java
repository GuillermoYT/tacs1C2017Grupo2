package test_smgo;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tacs.MovieController;
public class TestPeliculas {

	private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(new MovieController()).build();
    }

    @Test
    public void testObtenerDetallePelicula() throws Exception {
    	//id = 263115, pelicula logan
    	String id = "263115";
    	
    	this.mockMvc.perform(get("/peliculas/"+id).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
    	.andExpect(status().isOk())
    	.andExpect(jsonPath("$.id").exists())
    	.andExpect(jsonPath("$.id").value(263115))
    	.andExpect(jsonPath("$.nombre").value("Logan"))
    	.andExpect(jsonPath("$.cast",hasSize(99)))
    	.andExpect(jsonPath("$.cast[0].name").value("Hugh Jackman"))
    	.andExpect(jsonPath("$.cast[1].name").value("Patrick Stewart"))
    	.andExpect(jsonPath("$.imagePath").value("http://image.tmdb.org/t/p/w300//9EXnebqbb7dOhONLPV9Tg2oh2KD.jpg"))
    	.andExpect(jsonPath("$.descripcion").value("In the near future, a weary Logan cares for an ailing Professor X in a hideout on the Mexican border. But Logan's attempts to hide from the world and his legacy are upended when a young mutant arrives, pursued by dark forces."))
    	.andDo(print());
    }

}
