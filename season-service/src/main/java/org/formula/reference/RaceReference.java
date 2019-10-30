package org.formula.reference;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RaceReference {

    private @Id @GeneratedValue Long id;
    private String circuit;

    public RaceReference(String circuit) {
        this.circuit = circuit;
    }
}
