package pl.connectis.cinemareservationsapp;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @ParameterizedTest
    @CsvFileSource(resources = "/allMovie.csv", delimiter = ';')
    public void findAllMovie(String expectedJson) throws Exception {
        mockMvc.perform(get("/movie/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(matchAll(status().is2xxSuccessful(), content().json(expectedJson)));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/findMovieById.csv", delimiter = ';')
    public void findMovieById(long id, String expectedJson) throws Exception {
        mockMvc.perform(get("/movie/?id={id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(matchAll(status().is2xxSuccessful(), content().json(expectedJson)));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/addNewMovie.csv", delimiter = ';')
    public void addNewMovie(String content) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/movie").content(content)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/addListOfNewMovie.csv", delimiter = ';')
    public void addListOfNewMovie(String expectedJson) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/movie/many").content(expectedJson)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/updateMovie.csv", delimiter = ';')
    public void updateMovie(long id, String expectedJson) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/movie/?id={id}", id)
                .content(expectedJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("3 idiots"));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/deleteMovieById.csv", delimiter = ';')
    public void deleteMovie(long id) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/movie/?id={id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
