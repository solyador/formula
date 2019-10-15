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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RaceControllerTests {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RaceRepository raceRepository;

    @MockBean
    private StandingRepository standingRepository;

    @MockBean
    private SeasonRepository seasonRepository;

    @Before
    public void setup() {
        Race race = new Race(1L, 1, 1,1, 1, 1);
        when(raceRepository.findById(1L)).thenReturn(Optional.of(race));
    }

    @Test
    public void shouldFindRace() throws Exception {
        this.mockMvc.perform(get("/races/1")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(content()
                .json("{ 'id': 1, 'driver' : 1, 'position': 1, 'season' : 1, 'week' : 1, 'score' : 1    }"));
        verify(raceRepository, times(1)).findById(1L);

    }

    @Test
    public void shoudlFindAllRaces() throws Exception {

        List<Race> races = Arrays.asList(new Race(1L, 1, 1, 1, 1, 1),
                                         new Race(2L, 2, 2, 2, 2, 2));
        when(raceRepository.findAll()).thenReturn(races);

        this.mockMvc.perform(get("/races"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].driver", is(1)))
                .andExpect(jsonPath("$[0].position", is(1)))
                .andExpect(jsonPath("$[0].season", is(1)))
                .andExpect(jsonPath("$[0].week", is(1)))
                .andExpect(jsonPath("$[0].score", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].driver", is(2)))
                .andExpect(jsonPath("$[1].position", is(2)))
                .andExpect(jsonPath("$[1].season", is(2)))
                .andExpect(jsonPath("$[1].week", is(2)))
                .andExpect(jsonPath("$[1].score", is(2)));
        verify(raceRepository, times(1)).findAll();

    }

    @Ignore
    @Test
    public void shouldReturn404WhenRaceNotFound() throws Exception {
        this.mockMvc.perform(get("/races/5")).andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateRace() throws Exception {
        Race race = new Race(1L, 1, 1, 1, 1, 1);
        when(this.raceRepository.save(any(Race.class))).thenReturn(race);
        this.mockMvc.perform(post("/races").content(objectMapper.writeValueAsString(race))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.driver", is(1)))
                .andExpect(jsonPath("$.position", is(1)))
                .andExpect(jsonPath("$.season", is(1)))
                .andExpect(jsonPath("$.week", is(1)))
                .andExpect(jsonPath("$.score", is(1)));
    }

    @Test
    public void shouldUpdateRace() throws Exception {
        Race updateRace = new Race(1L, 1, 1,1, 1, 1);
        when(this.raceRepository.save(any(Race.class))).thenReturn(updateRace);
        this.mockMvc.perform(put("/races/1")
                .content(objectMapper.writeValueAsString(updateRace))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.driver", is(1)))
                .andExpect(jsonPath("$.position", is(1)))
                .andExpect(jsonPath("$.season", is(1)))
                .andExpect(jsonPath("$.week", is(1)))
                .andExpect(jsonPath("$.score", is(1)));
    }

    @Test
    public void shouldDeleteRace() throws Exception {
        doNothing().when(this.raceRepository).deleteById(1L);
        this.mockMvc.perform(delete("/races/1")).andExpect(status().isOk());
        verify(this.raceRepository, times(1)).deleteById(1L);
    }

    @Test
    public void shouldReturnResult() throws Exception {
        Season currentSeason = RaceControllerTestHelper.getCurrentSeason();
        List<Standing> standings = RaceControllerTestHelper.getStandings();
        List<Race> race = RaceControllerTestHelper.getRace();

        when(this.standingRepository.findAllBySeason(currentSeason.getId().intValue())).thenReturn(standings);
        when(this.raceRepository.findAllBySeasonAndWeek(currentSeason.getId().intValue(), currentSeason.getRace())).thenReturn(race);
        when(this.standingRepository.findBySeasonAndDriver(1, 1)).thenReturn(standings.get(0));
        when(this.standingRepository.findBySeasonAndDriver(1, 2)).thenReturn(standings.get(1));
        when(this.standingRepository.findBySeasonAndDriver(1, 3)).thenReturn(standings.get(2));
        this.mockMvc.perform(get("/races/results/season/1/week/2"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
               /* .andExpect(jsonPath("$[0].score", is(1)))
                .andExpect(jsonPath("$[1].score", is(2)))
                .andExpect(jsonPath("$[2].score", is(1)));*/
    }

}