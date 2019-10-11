package org.formula.pilot;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pilots")
public class PilotController {

    private final Logger LOG = LoggerFactory.getLogger(PilotController.class);
    private final IPilotService pilotService;

    public PilotController(PilotService pilotService) {
        this.pilotService = pilotService;
    }

    @GetMapping
    public ResponseEntity<List<Pilot>> getAllPilots() {
        List<Pilot> pilots = pilotService.findAll();
        if (pilots == null || pilots.isEmpty()) {
            LOG.info("no pilots found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Pilot>>(pilots, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPilot(@PathVariable Long id) {
        LOG.info("getting pilot ", id);
        Pilot pilot = pilotService.findById(id);
        if (pilot == null) {
            LOG.info("pilot with id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pilot, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addPilot(@RequestBody Pilot pilot, UriComponentsBuilder ucBuilder) {
        LOG.info("adding new pilot ", pilot);
        if (pilotService.exists(pilot)) {
            LOG.info("a pilot with name " + pilot.getFirstName() + " " + pilot.getLastName() + " already exists");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(pilotService.save(pilot), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePilot(@PathVariable Long id, @RequestBody Pilot newPilot) {
        LOG.info("updating pilot ", newPilot);
        Pilot currentPilot = pilotService.findById(id);
        if (currentPilot == null) {
            LOG.info("Pilot with id {} not found", id);
            return new ResponseEntity<Pilot>(HttpStatus.NOT_FOUND);
        }
        pilotService.updatePilot(newPilot, id);
        return new ResponseEntity<>(newPilot, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePilot(@PathVariable Long id) {
        LOG.info("deleting pilot ", id);
        Pilot pilot = pilotService.findById(id);
        if (pilot == null) {
            LOG.info("Pilot with id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        pilotService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
