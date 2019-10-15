package org.formula;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.formula.season.Season;
import org.formula.season.SeasonRepository;
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

import static java.lang.Boolean.FALSE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;




@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SeasonControllerTests {

    private Season firstSeason;
    private Season secondSeason;
    private Season thirdSeason;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SeasonRepository seasonRepository;

    @Before
    public void setup() {
        seasonRepository.deleteAllInBatch();
        firstSeason = seasonRepository.save(new Season(2));
        secondSeason = seasonRepository.save(new Season(10));
        thirdSeason = seasonRepository.save(new Season(20));
    }
    @Test
    public void should_return_a_season() throws Exception {
        this.mockMvc.perform(get("/seasons/" + firstSeason.getId())).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.race", is(2)));

    }

    @Test
    public void should_return_all_seasons() throws Exception {
        this.mockMvc.perform(get("/seasons"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(firstSeason.getId().intValue())))
                .andExpect(jsonPath("$[0].race", is(2)))
                .andExpect(jsonPath("$[1].id", is(secondSeason.getId().intValue())))
                .andExpect(jsonPath("$[1].race", is(10)))
                .andExpect(jsonPath("$[2].id", is(thirdSeason.getId().intValue())))
                .andExpect(jsonPath("$[2].race", is(20)));
    }

    @Ignore
    @Test
    public void should_return_404_when_season_not_found() throws Exception {
        this.mockMvc.perform(get("/seasons/5")).andExpect(status().isNotFound());
    }

    @Test
    public void should_create_season() throws Exception {
        Season season = new Season(16);
        this.mockMvc.perform(post("/seasons").content(objectMapper.writeValueAsString(season))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.race", is(16)));
    }

    @Test
    public void should_update_season() throws Exception {
        firstSeason.setRace(290);
        this.mockMvc.perform(put("/seasons/" + firstSeason.getId())
                .content(objectMapper.writeValueAsString(firstSeason))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(firstSeason.getId().intValue())))
                .andExpect(jsonPath("$.race", is(290)));
    }

    @Test
    public void should_delete_season() throws Exception {
        this.mockMvc.perform(delete("/seasons/" + firstSeason.getId())).andExpect(status().isOk());
        assertThat(seasonRepository.findById(firstSeason.getId()).isPresent()).isEqualTo(FALSE);
    }
}