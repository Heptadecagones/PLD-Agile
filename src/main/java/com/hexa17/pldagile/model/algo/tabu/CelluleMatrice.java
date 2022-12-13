package com.hexa17.pldagile.model.algo.tabu;

/**
 * <p>CelluleMatrice class.</p>
 *
 * @author omi
 * @version $Id: $Id
 */
public class CelluleMatrice {

    private double poids;
    private int horaire;

    /**
     * <p>Constructor for CelluleMatrice.</p>
     */
    public CelluleMatrice() {

    }

    /**
     * <p>Constructor for CelluleMatrice.</p>
     *
     * @param poids a double
     * @param horaire a int
     */
    public CelluleMatrice(double poids, int horaire) {
        this.poids = poids;
        this.horaire = horaire;
    }

    /**
     * <p>obtenirPoids.</p>
     *
     * @return a double
     */
    public double obtenirPoids() {
        return poids;
    }

    /**
     * <p>modifierPoids.</p>
     *
     * @param poids a double
     */
    public void modifierPoids(double poids) {
        this.poids = poids;
    }

    /**
     * <p>obtenirHoraire.</p>
     *
     * @return a int
     */
    public int obtenirHoraire() {
        return horaire;
    }

    /**
     * <p>modifierHoraire.</p>
     *
     * @param horaire a int
     */
    public void modifierHoraire(int horaire) {
        this.horaire = horaire;
    }

}
