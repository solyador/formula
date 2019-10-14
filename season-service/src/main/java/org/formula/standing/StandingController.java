package org.formula.standing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/standings")
public class StandingController {
    private final Logger LOG = LoggerFactory.getLogger(StandingController.class);
    private final IStandingService standingService;

    public StandingController(StandingService standingService) {
        this.standingService = standingService;
    }

    @GetMapping
    public ResponseEntity<List<Standing>> getAllStandings() {
        List<Standing> standings = standingService.findAll();
        if (standings == null || standings.isEmpty()) {
            LOG.info("no standings found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Standing>>(standings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStanding(@PathVariable Long id) {
        LOG.info("getting standing ", id);
        Standing standing = standingService.findById(id);
        if (standing == null) {
            LOG.info("standing with id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(standing, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addStanding(@RequestBody Standing standing) {
       /*
        LOG.info("adding new standing ", standing);
        if (standingService.exists(standing)) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }*/
        return new ResponseEntity<>(standingService.save(standing), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStanding(@PathVariable Long id, @RequestBody Standing newStanding) {
        LOG.info("updating standing ", newStanding);
        Standing currentStanding = standingService.findById(id);
        if (currentStanding == null) {
            LOG.info("Standing with id {} not found", id);
            return new ResponseEntity<Standing>(HttpStatus.NOT_FOUND);
        }
        standingService.updateStanding(newStanding, id);
        return new ResponseEntity<>(newStanding, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStanding(@PathVariable Long id) {
        LOG.info("deleting standing ", id);
        Standing standing = standingService.findById(id);
        if (standing == null) {
            LOG.info("Standing with id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        standingService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}