package org.formula.season;

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
public class Season {
    private @Id @GeneratedValue Long id;
    private Integer race;

    public Season(Integer race) {
        this.race = race;
    }
}
