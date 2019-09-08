package org.formula.pilot;

public class PilotNotFoundException extends RuntimeException {
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    PilotNotFoundException(Long id) {
        super("Could not find pilot " + id);
    }
}
