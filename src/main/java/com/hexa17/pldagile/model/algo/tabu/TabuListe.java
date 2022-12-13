package com.hexa17.pldagile.model.algo.tabu;

/**
 * <p>Classe TabuListe.</p>
 *
 * @author omi
 * @version $Id: $Id
 */
public class TabuListe {

    int[][] tabuListe;

    /**
     * <p>Constructeur de TabuListe.</p>
     *
     * @param nbVilles, un int
     */
    public TabuListe(int nbVilles) {
        tabuListe = new int[nbVilles][nbVilles]; // city 0 is not used here, but left for simplicity
    }

    /**
     * <p>Interdit une permutation.</p>
     *
     * @param ville1, un int
     * @param ville2, un int
     */
    public void tabuDeplacer(int ville1, int ville2) {
        tabuListe[ville1][ville2] += 5;
        tabuListe[ville2][ville1] += 5;

    }

    /**
     * <p>Décrémente la liste tabou.</p>
     */
    public void decrementerTabu() {
        for (int i = 0; i < tabuListe.length; i++) {
            for (int j = 0; j < tabuListe.length; j++) {
                tabuListe[i][j] -= tabuListe[i][j] <= 0 ? 0 : 1;
            }
        }
    }

}
