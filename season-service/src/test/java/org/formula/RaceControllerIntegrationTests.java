package org.formula;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.formula.race.Race;
import org.formula.race.RaceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RaceControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RaceRepository raceRepository;

    @Test
    public void should_create_race() throws Exception {
        Race race = new Race(15, 20, 100, 1, 1);
        mockMvc.perform(post("/races/")
                .content(objectMapper.writeValueAsString(race))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        assertThat(raceRepository.findAllBySeasonAndWeek(100, 1).size()).isEqualTo(1);
        Race searchedRace = raceRepository.findAllBySeasonAndWeek(100, 1).get(0);
        assertThat(searchedRace.getDriver()).isEqualTo(15);
        assertThat(searchedRace.getPosition()).isEqualTo(20);
        assertThat(searchedRace.getSeason()).isEqualTo(100);
        assertThat(searchedRace.getWeek()).isEqualTo(1);
        assertThat(searchedRace.getScore()).isEqualTo(1);
    }

}
