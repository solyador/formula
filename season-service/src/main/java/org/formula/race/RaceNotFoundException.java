package org.formula.race;

public class RaceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    RaceNotFoundException(Long id) {
        super("Could not find race " + id);
    }
}
