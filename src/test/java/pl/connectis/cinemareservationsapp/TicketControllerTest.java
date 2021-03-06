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
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @CsvFileSource(resources = "/findTicketById.csv", delimiter = ';')
    public void findTicketById(long id, String content) throws Exception {
        mockMvc.perform(get("/ticket/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(matchAll(status().is2xxSuccessful(), content().json(content)));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/addNewTicket.csv", delimiter = ';')
    public void addNewTicket(String content) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/ticket").content(content)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/deleteTicketById.csv", delimiter = ';')
    public void deleteTicketById(long id) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/ticket/?id={id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
