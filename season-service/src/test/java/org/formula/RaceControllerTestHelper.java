package org.formula;

import org.formula.season.Season;
import org.formula.standing.Standing;

import java.util.Arrays;
import java.util.List;

class RaceControllerTestHelper {

          static List<Standing> getStandings(Integer currentSeason) {
             return Arrays.asList(
                     new Standing(currentSeason,1,0),
                     new Standing(currentSeason, 2, 0),
                     new Standing(currentSeason, 3, 0),
                     new Standing(currentSeason, 4, 0));
         }

         static Season getCurrentSeason() {
             return new Season(4);
         }
}
