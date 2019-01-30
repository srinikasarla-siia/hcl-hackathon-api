package com.hcl.hackathon.fullstack.acceptance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.hackathon.fullstack.FullStackApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {FullStackApplication.class})
public class RoomsControllerAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void findAvailableRooms_returnsAvailableRoomsForRequestedData() throws Exception {

        mockMvc.perform(post("/api/rooms/available")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"startDateTime\": \"2018-11-05T11:30\",\n" +
                        "  \"endDateTime\": \"2018-11-05T12:30\",\n" +
                        "  \"minimumCapacity\": 4,\n" +
                        "  \"amenities\": [\n" +
                        "    \"AppleTV\",\n" +
                        "    \"Phone\"\n" +
                        "  ]\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                        "    {\n" +
                        "        \"roomId\": 1,\n" +
                        "        \"name\": \"Death Star\",\n" +
                        "        \"capacity\": 4,\n" +
                        "        \"buildingName\": \"Sweet Candy Building\",\n" +
                        "        \"floor\": \"Second\",\n" +
                        "        \"city\": \"Irving\",\n" +
                        "        \"amenities\": [\n" +
                        "            \"Apple TV\",\n" +
                        "            \"Phone\",\n" +
                        "            \"Television\"\n" +
                        "        ]\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"roomId\": 3,\n" +
                        "        \"name\": \"Joe Pool Lake\",\n" +
                        "        \"capacity\": 8,\n" +
                        "        \"buildingName\": \"Cypress\",\n" +
                        "        \"floor\": \"Third\",\n" +
                        "        \"city\": \"Dallas\",\n" +
                        "        \"amenities\": [\n" +
                        "            \"Zoom room\",\n" +
                        "            \"Apple TV\",\n" +
                        "            \"Phone\"\n" +
                        "        ]\n" +
                        "    }\n" +
                        "]"));
    }

    @Test
    public void reserveARoom_reservesARoom() throws Exception {
        String request = "{\n" +
                "  \"roomId\": 3,\n" +
                "  \"startDateTime\":\"2018-11-06T12:30\",\n" +
                "  \"endDateTime\":\"2018-11-06T13:30\"\n" +
                "}";
        mockMvc.perform(post("/api/rooms/reserve")
                .content(request)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1));
    }
}
