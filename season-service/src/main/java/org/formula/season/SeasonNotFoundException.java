package org.formula.season;

class SeasonNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    SeasonNotFoundException(Long id) {
        super("Could not find season " + id);
    }
}
