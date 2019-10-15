package org.formula.standing;

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
public class Standing {
    private @Id @GeneratedValue Long id;
    private Integer season;
    private Integer driver;
    private Integer points;

    public Standing(Integer season, Integer driver, Integer points) {
        this.season = season;
        this.driver = driver;
        this.points = points;
    }
}
