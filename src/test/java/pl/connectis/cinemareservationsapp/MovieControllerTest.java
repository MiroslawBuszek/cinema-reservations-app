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

import java.util.Properties;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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
        mockMvc.perform(get("/movie")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(response))
                .andDo(print());
    }

    @Order(2)
    @ParameterizedTest
    @CsvFileSource(resources = "/movie/getMovieByExampleCategory.csv", delimiter = ';')
    public void getMovieByExampleCategory(String category, String response) throws Exception {
        mockMvc.perform(get("/movie?category={category}", category)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(response))
                .andDo(print());
    }

    @Order(3)
    @ParameterizedTest
    @CsvFileSource(resources = "/movie/getMovieByExampleTitle.csv", delimiter = ';')
    public void getMovieByExampleTitle(String title, String response) throws Exception {
        mockMvc.perform(get("/movie?title={title}", title)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(response))
                .andDo(print());
    }

    @Order(4)
    @ParameterizedTest
    @CsvFileSource(resources = "/movie/addMovie.csv", delimiter = ';')
    public void addMovie(String request, String response) throws Exception {
        mockMvc.perform(post("/movie")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(response))
                .andExpect(jsonPath("$.id").exists())
                .andDo(print());
    }

    @Order(5)
    @ParameterizedTest
    @CsvFileSource(resources = "/movie/updateMovie.csv", delimiter = ';')
    public void updateMovie(String request, String response) throws Exception {
        mockMvc.perform(put("/movie")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(response))
                .andDo(print());
    }


    @Order(6)
    @ParameterizedTest
    @CsvFileSource(resources = "/movie/deleteMovie.csv", delimiter = ';')
    public void deleteMovie(long id) throws Exception {
        mockMvc.perform(delete("/movie?id={id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Order(7)
    @ParameterizedTest
    @CsvFileSource(resources = "/movie/deleteMovieDoesntExist.csv", delimiter = ';')
    public void deleteMovieDoesntExist(long id) throws Exception {
        mockMvc.perform(delete("/movie?id={id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Order(8)
    @ParameterizedTest
    @CsvFileSource(resources = "/movie/updateMovieDoesntExist.csv", delimiter = ';')
    public void updateMovieDoesntExist(String request) throws Exception {
        mockMvc.perform(put("/movie")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

}
