package pl.connectis.cinemareservationsapp;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Properties;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureMockMvc(addFilters = false)
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public static void setSpringProfile() {
        Properties properties = System.getProperties();
        properties.setProperty("spring.profiles.active", "develop");
    }

    @AfterAll
    public static void resetSpringProfile() {
        System.clearProperty("spring.profiles.active");
    }

    @Order(1)
    @ParameterizedTest
    @CsvFileSource(resources = "/movie/getMovieByExampleAll.csv", delimiter = ';')
    public void getMovieByExampleAll(String response) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/movie")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    @Order(2)
    @ParameterizedTest
    @CsvFileSource(resources = "/movie/getMovieByExampleCategory.csv", delimiter = ';')
    public void getMovieByExampleCategory(String category, String response) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/movie?category={category}", category)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    @Order(3)
    @ParameterizedTest
    @CsvFileSource(resources = "/movie/getMovieByExampleTitle.csv", delimiter = ';')
    public void getMovieByExampleTitle(String title, String response) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/movie?title={title}", title)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    @Order(4)
    @ParameterizedTest
    @CsvFileSource(resources = "/movie/addMovie.csv", delimiter = ';')
    public void addMovie(String request, String response) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/movie")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(response))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Order(5)
    @ParameterizedTest
    @CsvFileSource(resources = "/movie/updateMovie.csv", delimiter = ';')
    public void updateMovie(String request, String response) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/movie")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    @Order(6)
    @ParameterizedTest
    @CsvFileSource(resources = "/movie/deleteMovie.csv", delimiter = ';')
    public void deleteMovie(long id) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/movie?id={id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
