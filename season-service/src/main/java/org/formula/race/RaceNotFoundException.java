package org.formula.race;

class RaceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    RaceNotFoundException(Long id) {
        super("Could not find race " + id);
    }
}
