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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @CsvFileSource(resources = "/findSessionById.csv", delimiter = ';')
    public void findSessionById(long id) throws Exception {
        mockMvc.perform(get("/session/?id={id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(matchAll(status().is2xxSuccessful()));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/addNewSession.csv", delimiter = ';')
    public void addNewSession(String expectedJson) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/session").content(expectedJson)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/updateSession.csv", delimiter = ';')
    public void updateSession(String expectedJson) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/session")
                .content(expectedJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/deleteSessionById.csv", delimiter = ';')
    public void deleteUser(long id) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/session/?id={id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
