package org.formula.race;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/races")
public class RaceController {
    private final Logger LOG = LoggerFactory.getLogger(RaceController.class);
    private final IRaceService raceService;

    public RaceController(RaceService raceService) {
        this.raceService = raceService;
    }

    @GetMapping
    public ResponseEntity<List<Race>> getAllRaces() {
        List<Race> races = raceService.findAll();
        if (races == null || races.isEmpty()) {
            LOG.info("no races found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Race>>(races, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRace(@PathVariable Long id) {
        LOG.info("getting race ", id);
        Race race = raceService.findById(id);
        if (race == null) {
            LOG.info("race with id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(race, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addRace(@RequestBody Race race) {
       /*
        LOG.info("adding new race ", race);
        if (raceService.exists(race)) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }*/
        return new ResponseEntity<>(raceService.save(race), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRace(@PathVariable Long id, @RequestBody Race newRace) {
        LOG.info("updating race ", newRace);
        Race currentRace = raceService.findById(id);
        if (currentRace == null) {
            LOG.info("Race with id {} not found", id);
            return new ResponseEntity<Race>(HttpStatus.NOT_FOUND);
        }
        raceService.updateRace(newRace, id);
        return new ResponseEntity<>(newRace, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRace(@PathVariable Long id) {
        LOG.info("deleting race ", id);
        Race race = raceService.findById(id);
        if (race == null) {
            LOG.info("Race with id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        raceService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}