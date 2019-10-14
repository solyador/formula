package org.formula;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class StandingControllerTests {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StandingRepository standingRepository;

    @Before
    public void setup() {
        Standing standing = new Standing(1L, 1, 1, 1, 1);
        when(standingRepository.findById(1L)).thenReturn(Optional.of(standing));
    }

    @Test
    public void shouldFindStanding() throws Exception {
        this.mockMvc.perform(get("/standings/1")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(content()
                .json("{ 'id': 1, 'driver' : 1, 'season': 1, 'position' : 1, 'points' : 1 }"));
        verify(standingRepository, times(1)).findById(1L);

    }

    @Test
    public void shoudlFindAllStandings() throws Exception {

        List<Standing> standings = Arrays.asList(new Standing(1L, 1, 1, 1, 1),
                                         new Standing(2L, 2, 2, 2, 2));
        when(standingRepository.findAll()).thenReturn(standings);

        this.mockMvc.perform(get("/standings")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].driver", is(1)))
                .andExpect(jsonPath("$[0].season", is(1)))
                .andExpect(jsonPath("$[0].position", is(1)))
                .andExpect(jsonPath("$[0].points", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].driver", is(2)))
                .andExpect(jsonPath("$[1].season", is(2)))
                .andExpect(jsonPath("$[1].position", is(2)))
                .andExpect(jsonPath("$[1].points", is(2)));
        verify(standingRepository, times(1)).findAll();

    }

    @Ignore
    @Test
    public void shouldReturn404WhenStandingNotFound() throws Exception {
        this.mockMvc.perform(get("/standings/5")).andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateStanding() throws Exception {
        Standing standing = new Standing(1L, 1, 1, 1, 1);
        when(this.standingRepository.save(any(Standing.class))).thenReturn(standing);
        this.mockMvc.perform(post("/standings").content(objectMapper.writeValueAsString(standing))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.driver", is(1)))
                .andExpect(jsonPath("$.season", is(1)))
                .andExpect(jsonPath("$.position", is(1)))
                .andExpect(jsonPath("$.points", is(1)));
    }

    @Test
    public void shouldUpdateStanding() throws Exception {
        Standing updateStanding = new Standing(1L, 1, 1,1, 1);
        when(this.standingRepository.save(any(Standing.class))).thenReturn(updateStanding);
        this.mockMvc.perform(put("/standings/1")
                .content(objectMapper.writeValueAsString(updateStanding))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.driver", is(1)))
                .andExpect(jsonPath("$.season", is(1)))
                .andExpect(jsonPath("$.position", is(1)))
                .andExpect(jsonPath("$.points", is(1)));
    }

    @Test
    public void shouldDeleteStanding() throws Exception {
        doNothing().when(this.standingRepository).deleteById(1L);
        this.mockMvc.perform(delete("/standings/1")).andExpect(status().isOk());
        verify(this.standingRepository, times(1)).deleteById(1L);
    }

}