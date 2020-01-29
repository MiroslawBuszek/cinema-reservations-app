package pl.connectis.cinemareservationsapp;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class CinemaReservationsAppApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @CsvFileSource(resources = "/clientData.csv", delimiter = ';')
    void findAllClientIsOK(String expectedJson) throws Exception {
        mockMvc.perform(get("/client/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(matchAll(
                        status().is2xxSuccessful(),
                        content().json(expectedJson)
                ));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/employeeData.csv", delimiter = ';')
    void findAllEmployeeIsOK(String expectedJson) throws Exception {
        mockMvc.perform(get("/employee/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(matchAll(
                        status().is2xxSuccessful(),
                        content().json(expectedJson)
                ));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/movieData.csv", delimiter = ';')
    void findAllMovieIsOK(String expectedJson) throws Exception {
        mockMvc.perform(get("/movie/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(matchAll(
                        status().is2xxSuccessful(),
                        content().json(expectedJson)
                ));
    }
}