package org.formula;

import org.formula.race.Race;
import org.formula.season.Season;
import org.formula.standing.Standing;

import java.util.Arrays;
import java.util.List;

class RaceControllerTestHelper {

          static List<Standing> getStandings() {
             return Arrays.asList(
                     new Standing(1L, 1,1,1,0),
                     new Standing(2L, 2, 1, 2, 0),
                     new Standing(3L, 3, 1, 3, 0));
         }

         static Season getCurrentSeason() {
             return new Season(1L, 2);
         }

         static List<Race> getRace() {
             return Arrays.asList(new Race(1L, 1, 0, 1, 2, 0),
                                  new Race(1L, 2, 0, 1, 2, 0),
                                  new Race(1L, 3, 0, 1, 2, 0));
         }


}
