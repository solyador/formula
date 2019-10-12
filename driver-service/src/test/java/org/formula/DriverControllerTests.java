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

import org.formula.driver.Driver;
import org.formula.driver.DriverRepository;
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
public class DriverControllerTests {

        private static final ObjectMapper objectMapper = new ObjectMapper();
        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private DriverRepository driverRepository;

        @Before
        public void setup() {
                Driver driver = new Driver(1L, "Louis", "Hamilton", "Mercedes");
                when(driverRepository.findById(1L)).thenReturn(Optional.of(driver));
        }

        @Test
        public void shouldFindDriver() throws Exception {
                this.mockMvc.perform(get("/drivers/1")).andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(content()
                                                .json("{ 'id': 1, 'firstName' : 'Louis', 'lastName' : 'Hamilton' }"));
                verify(driverRepository, times(1)).findById(1L);

        }

        @Test
        public void shoudlFindAllDrivers() throws Exception {

                List<Driver> drivers = Arrays.asList(new Driver(1L, "Louis", "Hamilton", "Mercedes"),
                                new Driver(2L, "Sebastien", "Vettel", "Ferrari"));
                when(driverRepository.findAll()).thenReturn(drivers);

                this.mockMvc.perform(get("/drivers")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                                .andExpect(jsonPath("$[0].id", is(1)))
                                .andExpect(jsonPath("$[0].firstName", is("Louis")))
                                .andExpect(jsonPath("$[0].lastName", is("Hamilton")))
                                .andExpect(jsonPath("$[0].team", is("Mercedes")))
                                .andExpect(jsonPath("$[1].id", is(2)))
                                .andExpect(jsonPath("$[1].firstName", is("Sebastien")))
                                .andExpect(jsonPath("$[1].lastName", is("Vettel")))
                                .andExpect(jsonPath("$[1].team", is("Ferrari")));

                verify(driverRepository, times(1)).findAll();

        }

        @Test
        public void shouldReturn404WhenDriverNotFound() throws Exception {
                this.mockMvc.perform(get("/drivers/5")).andExpect(status().isNotFound());
        }

        @Test
        public void shouldCreateDriver() throws JsonProcessingException, Exception {
                Driver driver = new Driver(1L, "Pierre", "Gasly", "Torro Rosso");
                when(this.driverRepository.save(any(Driver.class))).thenReturn(driver);
                this.mockMvc.perform(post("/drivers").content(objectMapper.writeValueAsString(driver))
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id", is(1)))
                                .andExpect(jsonPath("$.firstName", is("Pierre")))
                                .andExpect(jsonPath("$.lastName", is("Gasly")))
                                .andExpect(jsonPath("$.team", is("Torro Rosso")));
        }

        @Test
        public void shouldUpdateDriver() throws Exception {
                Driver updateDriver = new Driver(1L, "Louis", "Hamilton", "Mercedes");
                when(this.driverRepository.save(any(Driver.class))).thenReturn(updateDriver);
                this.mockMvc.perform(put("/drivers/1")
                                .content(objectMapper.writeValueAsString(updateDriver))
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                                .andExpect(status().isOk())
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(jsonPath("$.id", is(1)))
                                .andExpect(jsonPath("$.firstName", is("Louis")))
                                .andExpect(jsonPath("$.lastName", is("Hamilton")))
                                .andExpect(jsonPath("$.team", is("Mercedes")));
        }

        @Test
        public void shouldDeleteDriver() throws Exception {
                doNothing().when(this.driverRepository).deleteById(1L);
                this.mockMvc.perform(delete("/drivers/1")).andExpect(status().isOk());
                verify(this.driverRepository, times(1)).deleteById(1L);
        }
}
