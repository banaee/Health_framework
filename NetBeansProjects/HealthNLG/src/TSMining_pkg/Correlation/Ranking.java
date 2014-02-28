/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TSMining_pkg.Correlation;

/**
 *
 * @author hadi
 */
public class Ranking {

    private int originalID;
    private double value;
    private double rank;

    /**
     * Constructor. <p> Sets the id value and the raw value. Rank value is set
     * using the appropriate modifier method.
     */
    public Ranking(int id, double d) {
        originalID = id;
        value = d;
    }

    /**
     * Accessor for the id value
     *
     * @return data point id
     */
    public int getID() {
        return originalID;
    }

    /**
     * Accessor for the raw value
     *
     * @return raw value
     */
    public double getValue() {
        return value;
    }

    /**
     * Accessor for the rank value
     *
     * @return data point rank
     */
    public double getRank() {
        return rank;
    }

    /**
     * Modifier for the rank value
     */
    public void setRank(double d) {
        rank = d;
    }
}
