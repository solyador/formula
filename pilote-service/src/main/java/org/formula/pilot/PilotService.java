package org.formula.pilot;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PilotService implements IPilotService {

    private final PilotRepository pilotRepository;

    public PilotService(PilotRepository pilotRepository) {
        this.pilotRepository = pilotRepository;
    }
    
    @Override
    public List<Pilot> findAll() {
		return pilotRepository.findAll();
	}

    @Override
	public Pilot save(Pilot pilot) {
		return pilotRepository.save(pilot);
	}

    @Override
	public Pilot findById(Long id) {
        return pilotRepository.findById(id).orElseThrow(() -> new PilotNotFoundException(id));
	}

    @Override
	public void deleteById(Long id) {
        pilotRepository.deleteById(id);
    }
    
    @Override
	public void updatePilot(Pilot newPilot, Long id) {
        pilotRepository.findById(id).map(item -> {
                                   item.setFirstName(newPilot.getFirstName());
                                   item.setLastName(newPilot.getLastName());
                                   pilotRepository.save(item);
            return null;
        });
	}

    @Override
    public boolean exists(Pilot pilot) {
        Pilot searchedPilot = pilotRepository.findByFirsNameAndLasstName(pilot.getFirstName(), pilot.getLastName());
        return searchedPilot != null;
    }
}
