package org.formula.pilot;

import java.util.List;

public interface IPilotService {

    public List<Pilot> findAll();
    public Pilot save(Pilot pilot);
    public Pilot findById(Long id);
    public void deleteById(Long id);
    public void updatePilot(Pilot newPilot, Long id);
	public boolean exists(Pilot pilot);
}
