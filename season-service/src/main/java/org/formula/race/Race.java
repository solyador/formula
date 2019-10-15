package org.formula.race;

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
public class Race {
    private @Id @GeneratedValue Long id;
    private Integer driver;
    private Integer season;
    private Integer week;
    private Integer score;

    public Race(Integer season, Integer week, Integer driver, Integer score) {
        this.season = season;
        this.week = week;
        this.driver = driver;
        this.score = score;
    }
}
