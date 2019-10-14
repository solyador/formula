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
    private Integer driver;
    private Integer season;
    private Integer position;
    private Integer points;

    public Standing(Integer driver, Integer season, Integer position, Integer points) {
        this.driver = driver;
        this.season = season;
        this.position = position;
        this.points = points;
    }
}
