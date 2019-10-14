package org.formula.season;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/seasons")
public class SeasonController {
    private final Logger LOG = LoggerFactory.getLogger(SeasonController.class);
    private final ISeasonService seasonService;

    public SeasonController(SeasonService seasonService) {
        this.seasonService = seasonService;
    }

    @GetMapping
    public ResponseEntity<List<Season>> getAllSeasons() {
        List<Season> seasons = seasonService.findAll();
        if (seasons == null || seasons.isEmpty()) {
            LOG.info("no seasons found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Season>>(seasons, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSeason(@PathVariable Long id) {
        LOG.info("getting season ", id);
        Season season = seasonService.findById(id);
        if (season == null) {
            LOG.info("season with id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(season, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addSeason(@RequestBody Season season) {
       /*
        LOG.info("adding new season ", season);
        if (seasonService.exists(season)) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }*/
        return new ResponseEntity<>(seasonService.save(season), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSeason(@PathVariable Long id, @RequestBody Season newSeason) {
        LOG.info("updating season ", newSeason);
        Season currentSeason = seasonService.findById(id);
        if (currentSeason == null) {
            LOG.info("Season with id {} not found", id);
            return new ResponseEntity<Season>(HttpStatus.NOT_FOUND);
        }
        seasonService.updateSeason(newSeason, id);
        return new ResponseEntity<>(newSeason, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSeason(@PathVariable Long id) {
        LOG.info("deleting season ", id);
        Season season = seasonService.findById(id);
        if (season == null) {
            LOG.info("Season with id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        seasonService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}