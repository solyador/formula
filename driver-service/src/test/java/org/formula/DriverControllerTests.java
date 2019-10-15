package org.formula;

import static java.lang.Boolean.FALSE;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.formula.driver.Driver;
import org.formula.driver.DriverNotFoundException;
import org.formula.driver.DriverRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DriverControllerTests {

        private Driver firstDriver;
        private Driver secondDriver;
        private Driver thirdDriver;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        DriverRepository driverRepository;

        @Rule
        public ExpectedException thrown = ExpectedException.none();

        @Before
        public void setup() {
                driverRepository.deleteAll();
                firstDriver = driverRepository.save(new Driver("Louis", "Hamilton", "Mercedes"));
                secondDriver = driverRepository.save(new Driver("Sebastien", "Vettel", "Ferrari"));
                thirdDriver = driverRepository.save(new Driver("Charles", "Leclerc", "Ferrari"));
        }
        @Test
        public void should_return_a_driver() throws Exception {
                this.mockMvc.perform(get("/drivers/" + firstDriver.getId())).andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                        .andExpect(jsonPath("$.firstName", is("Louis")))
                        .andExpect(jsonPath("$.lastName", is("Hamilton")))
                        .andExpect(jsonPath("$.team", is("Mercedes")));

        }

        @Test
        public void should_return_all_drivers() throws Exception {
                this.mockMvc.perform(get("/drivers"))
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                        .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)))
                        .andExpect(jsonPath("$[0].id", is(firstDriver.getId().intValue())))
                        .andExpect(jsonPath("$[0].firstName", is("Louis")))
                        .andExpect(jsonPath("$[1].id", is(secondDriver.getId().intValue())))
                        .andExpect(jsonPath("$[1].firstName", is("Sebastien")))
                        .andExpect(jsonPath("$[2].id", is(thirdDriver.getId().intValue())))
                        .andExpect(jsonPath("$[2].firstName", is("Charles")));
        }

        @Test
        public void should_create_driver() throws Exception {
                Driver driver = new Driver("Valteri", "Bottas", "Mercedes");
                this.mockMvc.perform(post("/drivers").content(objectMapper.writeValueAsString(driver))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.firstName", is("Valteri")));
        }

        @Test
        public void should_update_driver() throws Exception {
                firstDriver.setFirstName("Michel");
                this.mockMvc.perform(put("/drivers/" + firstDriver.getId())
                        .content(objectMapper.writeValueAsString(firstDriver))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id", is(firstDriver.getId().intValue())))
                        .andExpect(jsonPath("$.firstName", is("Michel")));
        }

        @Test
        public void should_delete_driver() throws Exception {
                this.mockMvc.perform(delete("/drivers/" + firstDriver.getId())).andExpect(status().isOk());
                assertThat(driverRepository.findById(firstDriver.getId()).isPresent()).isEqualTo(FALSE);
        }
}
