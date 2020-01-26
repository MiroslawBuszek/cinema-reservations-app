package pl.connectis.cinemareservationsapp;

import org.junit.jupiter.api.Test;
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

    @Test
    void ifGetClientRequestIsCorrectShouldReturnCode200() throws Exception {
        mockMvc.perform(get("/client/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(matchAll(
                        status().is2xxSuccessful(),
                        content().json("[\n" +
                                "    {\n" +
                                "        \"id\": 1,\n" +
                                "        \"firstName\": \"Adrian\",\n" +
                                "        \"lastName\": \"Budny\",\n" +
                                "        \"age\": 39,\n" +
                                "        \"email\": \"adrian.budny@gmail.com\"\n" +
                                "    }\n" +
                                "]")
                ));
    }
}