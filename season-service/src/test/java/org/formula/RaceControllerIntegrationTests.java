package org.formula;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.formula.race.Race;
import org.formula.race.RaceRepository;
import org.formula.season.Season;

import org.formula.season.SeasonRepository;
import org.formula.standing.Standing;
import org.formula.standing.StandingRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static java.lang.Boolean.FALSE;
import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RaceControllerIntegrationTests {

    private Race firstRace;
    private Race secondRace;
    private Race thirdRace;
    private Season currentSeason;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    SeasonRepository seasonRepository;

    @Autowired
    StandingRepository standingRepository;

    @Before
    public void setup() {
        raceRepository.deleteAllInBatch();
        seasonRepository.deleteAllInBatch();
        standingRepository.deleteAllInBatch();

        currentSeason = seasonRepository.save(RaceControllerTestHelper.getCurrentSeason());
        standingRepository.saveAll(RaceControllerTestHelper.getStandings(currentSeason.getId().intValue()));
        firstRace = raceRepository.save(new Race(currentSeason.getId().intValue(),4, 2, 0));
        secondRace = raceRepository.save(new Race(currentSeason.getId().intValue(), 4, 1, 0));
        thirdRace = raceRepository.save(new Race(currentSeason.getId().intValue(), 4, 3, 0));
    }
    @Test
    public void should_return_a_race() throws Exception {
        this.mockMvc.perform(get("/races/" + firstRace.getId())).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.season", is(currentSeason.getId().intValue())))
                .andExpect(jsonPath("$.week", is(4)))
                .andExpect(jsonPath("$.driver", is(2)))
                .andExpect(jsonPath("$.score", is(0)));

    }

    @Test
    public void should_return_all_races() throws Exception {
        this.mockMvc.perform(get("/races"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(firstRace.getId().intValue())))
                .andExpect(jsonPath("$[1].id", is(secondRace.getId().intValue())))
                .andExpect(jsonPath("$[2].id", is(thirdRace.getId().intValue())));
    }

    @Test
    public void should_create_race() throws Exception {
        Race race = new Race( 3, 4, 3,129);
        this.mockMvc.perform(post("/races").content(objectMapper.writeValueAsString(race))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.score", is(129)));
    }

    @Test
    public void should_update_race() throws Exception {
        firstRace.setScore(290);
        this.mockMvc.perform(put("/races/" + firstRace.getId())
                .content(objectMapper.writeValueAsString(firstRace))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(firstRace.getId().intValue())))
                .andExpect(jsonPath("$.score", is(290)));
    }

    @Test
    public void should_delete_race() throws Exception {
        this.mockMvc.perform(delete("/races/" + firstRace.getId())).andExpect(status().isOk());
        assertThat(raceRepository.findById(firstRace.getId()).isPresent()).isEqualTo(FALSE);
    }
    @Test
    public void shouldReturnResult() throws Exception {
        this.mockMvc.perform(get("/races/results/season/"+ currentSeason.getId() + "/week/4"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)))
                .andExpect(jsonPath("$[0].score", is(both(greaterThan(4000)).and(lessThan(7258)))))
                .andExpect(jsonPath("$[1].score", is(both(greaterThan(4000)).and(lessThan(7258)))))
                .andExpect(jsonPath("$[2].score", is(both(greaterThan(4000)).and(lessThan(7258)))));
    }
}
