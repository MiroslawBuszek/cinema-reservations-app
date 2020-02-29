package pl.connectis.cinemareservationsapp;

import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class RoomControllerTest {

	@Autowired private MockMvc mockMvc;
	
	
	@ParameterizedTest
	@CsvFileSource(resources = "/allRoom.csv", delimiter = ';')
	public void findAllRoom(String expectedJson) throws Exception {
		mockMvc.perform(get("/room/all").contentType(MediaType.APPLICATION_JSON))
				.andExpect(matchAll(status().is2xxSuccessful(), content().json(expectedJson)));
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/findRoomById.csv", delimiter = ';')
	public void findRoomById(long id, String expectedJson) throws Exception {
		mockMvc.perform(get("/room/?id={id}", new Object[] { id }).contentType(MediaType.APPLICATION_JSON))
				.andExpect(matchAll(status().is2xxSuccessful(), content().json(expectedJson)));
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/addNewRoom.csv", delimiter = ';')
	public void addNewRoom(String expectedJson) throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/room").content(expectedJson)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/addListOfNewRoom.csv", delimiter = ';')
	public void addListOfNewRoom(String expectedJson) throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/room/many").content(expectedJson)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/updateRoom.csv", delimiter = ';')
	public void updateRoom(long id, String expectedJson) throws Exception {
		mockMvc.perform( MockMvcRequestBuilders
	      .put("/room/?id={id}", new Object[] { id })
	      .content(expectedJson)
	      .contentType(MediaType.APPLICATION_JSON)
	      .accept(MediaType.APPLICATION_JSON))
	      .andExpect(status().isCreated());
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/deleteRoomById.csv", delimiter = ';')
	public void deleteRoom(long id) throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
	            .delete("/room/?id={id}",  new Object[] { id })
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON))
	            .andExpect(status().isNoContent());
	}
	
}
