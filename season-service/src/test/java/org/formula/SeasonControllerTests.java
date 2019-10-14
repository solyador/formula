package org.formula;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.formula.season.Season;
import org.formula.season.SeasonRepository;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SeasonControllerTests {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SeasonRepository seasonRepository;

    @Before
    public void setup() {
        Season season = new Season(1L, 1);
        when(seasonRepository.findById(1L)).thenReturn(Optional.of(season));
    }

    @Test
    public void shouldFindSeason() throws Exception {
        this.mockMvc.perform(get("/seasons/1")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(content()
                .json("{ 'id': 1, 'race' : 1 }"));
        verify(seasonRepository, times(1)).findById(1L);

    }

    @Test
    public void shoudlFindAllSeasons() throws Exception {

        List<Season> seasons = Arrays.asList(new Season(1L, 1), new Season(2L, 1));
        when(seasonRepository.findAll()).thenReturn(seasons);

        this.mockMvc.perform(get("/seasons")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].race", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].race", is(1)));
        verify(seasonRepository, times(1)).findAll();

    }

    @Test
    public void shouldReturn404WhenSeasonNotFound() throws Exception {
        this.mockMvc.perform(get("/seasons/5")).andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateSeason() throws Exception {
        Season season = new Season(1L, 1);
        when(this.seasonRepository.save(any(Season.class))).thenReturn(season);
        this.mockMvc.perform(post("/seasons").content(objectMapper.writeValueAsString(season))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.race", is(1)));
    }

    @Test
    public void shouldUpdateSeason() throws Exception {
        Season updateSeason = new Season(1L, 1);
        when(this.seasonRepository.save(any(Season.class))).thenReturn(updateSeason);
        this.mockMvc.perform(put("/seasons/1")
                .content(objectMapper.writeValueAsString(updateSeason))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.race", is(1)));
    }

    @Test
    public void shouldDeleteSeason() throws Exception {
        doNothing().when(this.seasonRepository).deleteById(1L);
        this.mockMvc.perform(delete("/seasons/1")).andExpect(status().isOk());
        verify(this.seasonRepository, times(1)).deleteById(1L);
    }

}