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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static java.lang.Boolean.FALSE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class StandingControllerIntegrationTests {

    private Standing firstStanding;
    private Standing secondStanding;
    private Standing thirdStanding;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    StandingRepository standingRepository;
    
    @Before
    public void setup() {
        standingRepository.deleteAllInBatch();
        firstStanding = standingRepository.save(new Standing(2, 3, 4));
        secondStanding = standingRepository.save(new Standing(2, 1, 61));
        thirdStanding = standingRepository.save(new Standing( 2, 2, 12));
    }
    @Test
    public void should_return_a_standing() throws Exception {
        this.mockMvc.perform(get("/standings/" + firstStanding.getId())).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.season", is(2)))
                .andExpect(jsonPath("$.driver", is(3)))
                .andExpect(jsonPath("$.points", is(4)));

    }

    @Test
    public void should_return_all_standings() throws Exception {
        this.mockMvc.perform(get("/standings"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(firstStanding.getId().intValue())))
                .andExpect(jsonPath("$[0].driver", is(3)))
                .andExpect(jsonPath("$[1].id", is(secondStanding.getId().intValue())))
                .andExpect(jsonPath("$[1].driver", is(1)))
                .andExpect(jsonPath("$[2].id", is(thirdStanding.getId().intValue())))
                .andExpect(jsonPath("$[2].driver", is(2)));
    }

    @Test
    public void should_create_standing() throws Exception {
        Standing standing = new Standing( 3, 4, 129);
        this.mockMvc.perform(post("/standings").content(objectMapper.writeValueAsString(standing))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.points", is(129)));
    }

    @Test
    public void should_update_standing() throws Exception {
        firstStanding.setPoints(290);
        this.mockMvc.perform(put("/standings/" + firstStanding.getId())
                .content(objectMapper.writeValueAsString(firstStanding))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(firstStanding.getId().intValue())))
                .andExpect(jsonPath("$.points", is(290)));
    }

    @Test
    public void should_delete_standing() throws Exception {
        this.mockMvc.perform(delete("/standings/" + firstStanding.getId())).andExpect(status().isOk());
        assertThat(standingRepository.findById(firstStanding.getId()).isPresent()).isEqualTo(FALSE);
    }
}
