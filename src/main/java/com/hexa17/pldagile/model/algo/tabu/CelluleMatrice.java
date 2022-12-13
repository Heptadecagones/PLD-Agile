package com.hexa17.pldagile.model.algo.tabu;

/**
 * <p>
 * Classe CelluleMatrice.
 * </p>
 *
 * @author Yannick
 * @version 1.0
 */
public class CelluleMatrice {

    private double poids;
    private int horaire;

    /**
     * <p>
     * Constructeur vide de CelluleMatrice.
     * </p>
     */
    public CelluleMatrice() {
    }

    /**
     * <p>
     * Constructeur complet de CelluleMatrice.
     * </p>
     *
     * @param poids   un double
     * @param horaire un int
     */
    public CelluleMatrice(double poids, int horaire) {
        this.poids = poids;
        this.horaire = horaire;
    }

    /**
     * <p>
     * Getter pour le champ <code>poids</code>.
     * </p>
     *
     * @return a double
     */
    public double obtenirPoids() {
        return poids;
    }

    /**
     * <p>
     * Setter pour le champ <code>poids</code>.
     * </p>
     *
     * @param poids un double
     */
    public void modifierPoids(double poids) {
        this.poids = poids;
    }

    /**
     * <p>
     * Getter pour le champ <code>obtenirHoraire</code>.
     * </p>
     *
     * @return un int
     */
    public int obtenirHoraire() {
        return horaire;
    }

    /**
     * <p>
     * Setter pour le champ <code>obtenirHoraire</code>.
     * </p>
     *
     * @param horaire un int
     */
    public void modifierHoraire(int horaire) {
        this.horaire = horaire;
    }

}
