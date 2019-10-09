package org.formula.pilot;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Pilot {
    private @Id @GeneratedValue Long id;
    private String firstName;
    private String lastName;

    public Pilot(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
