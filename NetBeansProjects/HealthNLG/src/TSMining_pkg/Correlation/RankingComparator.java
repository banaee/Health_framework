/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TSMining_pkg.Correlation;

import java.util.Comparator;

/**
 *
 * @author hadi
 */
public class RankingComparator implements Comparator<Ranking> {

    @Override
    public int compare(Ranking r1, Ranking r2) {
        if (r2.getValue() > r1.getValue()) {
            return -1;
        } else if (r2.getValue() < r1.getValue()) {
            return 1;
        } else {
            return 0;
        }
    }
}
