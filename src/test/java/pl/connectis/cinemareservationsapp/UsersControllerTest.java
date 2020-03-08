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
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @CsvFileSource(resources = "/findUserById.csv", delimiter = ';')
    public void findUserById(long id, String expectedJson) throws Exception {
        mockMvc.perform(get("/user/?id={id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(matchAll(status().is2xxSuccessful(), content().json(expectedJson)));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/allUser.csv", delimiter = ';')
    public void findAllUser(String expectedJson) throws Exception {
        mockMvc.perform(get("/user/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(matchAll(status().is2xxSuccessful(), content().json(expectedJson)));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/addNewClient.csv", delimiter = ';')
    public void addNewClient(String expectedJson) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/registration/client").content(expectedJson)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/addListOfNewClient.csv", delimiter = ';')
    public void addListOfNewClient(String expectedJson) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/many").content(expectedJson)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/addNewEmployee.csv", delimiter = ';')
    public void addNewEmployee(String expectedJson) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/registration/employee").content(expectedJson)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/updateUser.csv", delimiter = ';')
    public void updateUser(long id, String expectedJson) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/user/?id={id}", id)
                .content(expectedJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Termos"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("termos@cinema.pl"));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/deleteUserById.csv", delimiter = ';')
    public void deleteUser(long id) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/user/?id={id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
