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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureMockMvc(addFilters = false)
public class RoomControllerTest {

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
    @CsvFileSource(resources = "/room/getRoom.csv", delimiter = ';')
    public void getRoom(String response) throws Exception {
        mockMvc.perform(get("/room")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(response))
                .andDo(print());
    }

    @Order(2)
    @ParameterizedTest
    @CsvFileSource(resources = "/room/getRoomByCapacity.csv", delimiter = ';')
    public void getRoomByCapacity(int capacity, String response) throws Exception {
        mockMvc.perform(get("/room?capacity={capacity}", capacity)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(response))
                .andDo(print());
    }

    @Order(3)
    @ParameterizedTest
    @CsvFileSource(resources = "/room/addRoom.csv", delimiter = ';')
    public void addRoom(String request, String response) throws Exception {
        mockMvc.perform(post("/room")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(response))
                .andDo(print());
    }

    @Order(4)
    @ParameterizedTest
    @CsvFileSource(resources = "/room/addRoomWrongCapacity.csv", delimiter = ';')
    public void addRoomWrongCapacity(String request) throws Exception {
        mockMvc.perform(post("/room")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Order(5)
    @ParameterizedTest
    @CsvFileSource(resources = "/room/addRoomInappropriateLayoutFormat.csv", delimiter = ';')
    public void addRoomInappropriateLayoutFormat(String request) throws Exception {
        mockMvc.perform(post("/room")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Order(6)
    @ParameterizedTest
    @CsvFileSource(resources = "/room/updateRoom.csv", delimiter = ';')
    public void updateRoom(String request, String response) throws Exception {
        mockMvc.perform(put("/room")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(response))
                .andDo(print());
    }

    @Order(7)
    @ParameterizedTest
    @CsvFileSource(resources = "/room/deleteRoom.csv", delimiter = ';')
    public void deleteRoom(long id) throws Exception {
        mockMvc.perform(delete("/room?id={id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Order(8)
    @ParameterizedTest
    @CsvFileSource(resources = "/room/deleteRoomDoesntExists.csv", delimiter = ';')
    public void deleteRoomDoesntExists(long id) throws Exception {
        mockMvc.perform(delete("/room?id={id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Order(9)
    @ParameterizedTest
    @CsvFileSource(resources = "/room/updateRoomDoesntExists.csv", delimiter = ';')
    public void updateRoomDoesntExists(String request) throws Exception {
        mockMvc.perform(put("/room")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

}
