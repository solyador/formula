package org.formula;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Arrays;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.formula.pilot.Pilot;
import org.formula.pilot.PilotRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PilotControllerTests {

        private static final ObjectMapper objectMapper = new ObjectMapper();
        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private PilotRepository pilotRepository;

        @Before
        public void setup() {
                Pilot pilot = new Pilot(1L, "Louis", "Hamilton");
                when(pilotRepository.findById(1L)).thenReturn(Optional.of(pilot));
        }

        @Test
        public void shouldFindPilot() throws Exception {
                this.mockMvc.perform(get("/pilots/1")).andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(content()
                                                .json("{ 'id': 1, 'firstName' : 'Louis', 'lastName' : 'Hamilton' }"));
                verify(pilotRepository, times(1)).findById(1L);

        }

        @Test
        public void shoudlFindAllPilots() throws Exception {

                List<Pilot> pilots = Arrays.asList(new Pilot(1L, "Louis", "Hamilton"),
                                new Pilot(2L, "Sebastien", "Vettel"));
                when(pilotRepository.findAll()).thenReturn(pilots);

                this.mockMvc.perform(get("/pilots")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                                .andExpect(jsonPath("$[0].id", is(1)))
                                .andExpect(jsonPath("$[0].firstName", is("Louis")))
                                .andExpect(jsonPath("$[0].lastName", is("Hamilton")))
                                .andExpect(jsonPath("$[1].id", is(2)))
                                .andExpect(jsonPath("$[1].firstName", is("Sebastien")))
                                .andExpect(jsonPath("$[1].lastName", is("Vettel")));

                verify(pilotRepository, times(1)).findAll();

        }

        @Test
        public void shouldReturn404WhenPilotNotFound() throws Exception {
                this.mockMvc.perform(get("/pilots/5")).andExpect(status().isNotFound());
        }

        @Test
        public void shouldCreatePilot() throws JsonProcessingException, Exception {
                Pilot pilot = new Pilot(1L, "Pierre", "Gasly");
                when(this.pilotRepository.save(any(Pilot.class))).thenReturn(pilot);
                this.mockMvc.perform(post("/pilots").content(objectMapper.writeValueAsString(pilot))
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id", is(1)))
                                .andExpect(jsonPath("$.firstName", is("Pierre")))
                                .andExpect(jsonPath("$.lastName", is("Gasly")))
                                ;
        }

        @Test
        public void shouldUpdatePilot() throws Exception {
                Pilot updatePilot = new Pilot(1L, "Louis", "Hamilton");
                when(this.pilotRepository.save(any(Pilot.class))).thenReturn(updatePilot);
                this.mockMvc.perform(put("/pilots/1")
                                .content(objectMapper.writeValueAsString(updatePilot))
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                                .andExpect(status().isOk())
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(jsonPath("$.id", is(1)))
                                .andExpect(jsonPath("$.firstName", is("Louis")))
                                .andExpect(jsonPath("$.lastName", is("Hamilton")));
        }

        @Test
        public void shouldDeletePilot() throws Exception {
                doNothing().when(this.pilotRepository).deleteById(1L);
                this.mockMvc.perform(delete("/pilots/1")).andExpect(status().isOk());
                verify(this.pilotRepository, times(1)).deleteById(1L);
        }
}
