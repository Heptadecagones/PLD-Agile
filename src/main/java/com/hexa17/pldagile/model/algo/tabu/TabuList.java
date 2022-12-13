package com.hexa17.pldagile.model.algo.tabu;

/**
 * <p>TabuList class.</p>
 *
 * @author omi
 * @version $Id: $Id
 */
public class TabuList {

    int[][] tabuList;

    /**
     * <p>Constructor for TabuList.</p>
     *
     * @param numCities a int
     */
    public TabuList(int numCities) {
        tabuList = new int[numCities][numCities]; // city 0 is not used here, but left for simplicity
    }

    /**
     * <p>tabuMove.</p>
     *
     * @param city1 a int
     * @param city2 a int
     */
    public void tabuMove(int city1, int city2) { // tabus the swap operation
        tabuList[city1][city2] += 5;
        tabuList[city2][city1] += 5;

    }

    /**
     * <p>decrementTabu.</p>
     */
    public void decrementTabu() {
        for (int i = 0; i < tabuList.length; i++) {
            for (int j = 0; j < tabuList.length; j++) {
                tabuList[i][j] -= tabuList[i][j] <= 0 ? 0 : 1;
            }
        }
    }

}
