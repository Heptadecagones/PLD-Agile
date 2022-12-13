package com.hexa17.pldagile.model;

/**
 * <p>Segment class.</p>
 *
 * @author Yannick
 * @version $Id: $Id
 */
public class Segment {

    private String nom; //Nom de rue
    private Intersection origine; //Intersection d'origine
    private Intersection destination; //Intersection de destination
    private double longueur; //Longueur du segment

    /**
     * <p>Constructor for Segment.</p>
     */
    public Segment() {
    }

    /**
     * <p>Constructor for Segment.</p>
     *
     * @param n a {@link java.lang.String} object
     * @param ori a {@link com.hexa17.pldagile.model.Intersection} object
     * @param dest a {@link com.hexa17.pldagile.model.Intersection} object
     * @param l a double
     */
    public Segment(String n, Intersection ori, Intersection dest, double l) {
        this.nom = n;
        this.origine = ori;
        this.destination = dest;
        this.longueur = l;
    }

    /**
     * <p>obtenirNom.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String obtenirNom() {
        return nom;
    }

    /**
     * <p>obtenirOrigine.</p>
     *
     * @return a {@link com.hexa17.pldagile.model.Intersection} object
     */
    public Intersection obtenirOrigine() {
        return origine;
    }

    /**
     * <p>obtenirDestination.</p>
     *
     * @return a {@link com.hexa17.pldagile.model.Intersection} object
     */
    public Intersection obtenirDestination() {
        return destination;
    }

    /**
     * <p>obtenirLongueur.</p>
     *
     * @return a double
     */
    public double obtenirLongueur() {
        return longueur;
    }

    /**
     * <p>modifierLongueur.</p>
     *
     * @param l a double
     */
    public void modifierLongueur(double l) {
        this.longueur = l;
    }

    /**
     * <p>modifierOrigine.</p>
     *
     * @param ori a {@link com.hexa17.pldagile.model.Intersection} object
     */
    public void modifierOrigine(Intersection ori) {
        this.origine = ori;
    }

    /**
     * <p>modifierDestination.</p>
     *
     * @param dest a {@link com.hexa17.pldagile.model.Intersection} object
     */
    public void modifierDestination(Intersection dest) {
        this.destination = dest;
    }

    /**
     * <p>modifierNom.</p>
     *
     * @param n a {@link java.lang.String} object
     */
    public void modifierNom(String n) {
        this.nom = n;
    }

    /**
     * <p>toString.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String toString() {
        String description = "Nom : " + this.nom + " longueur : " + this.longueur + " origine : "
                + this.origine.obtenirId() + " destination : " + this.destination.obtenirId();
        return description;
    }
}
