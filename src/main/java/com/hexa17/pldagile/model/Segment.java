package com.hexa17.pldagile.model;

/**
 * <p>Segment classe.</p>
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
     * <p>Constructeur pour Segment.</p>
     */
    public Segment() {
    }

    /**
     * <p>Constructeur pour Segment.</p>
     *
     * @param n un {@link java.lang.String} objet
     * @param ori un {@link com.hexa17.pldagile.model.Intersection} objet
     * @param dest un {@link com.hexa17.pldagile.model.Intersection} objet
     * @param l un double
     */
    public Segment(String n, Intersection ori, Intersection dest, double l) {
        this.nom = n;
        this.origine = ori;
        this.destination = dest;
        this.longueur = l;
    }

    /**
     * <p>Accesseur Nom.</p>
     *
     * @return un {@link java.lang.String} objet
     */
    public String obtenirNom() {
        return nom;
    }

    /**
     * <p>Accesseur Origine.</p>
     *
     * @return un {@link com.hexa17.pldagile.model.Intersection} objet
     */
    public Intersection obtenirOrigine() {
        return origine;
    }

    /**
     * <p>Accesseur Destination.</p>
     *
     * @return un {@link com.hexa17.pldagile.model.Intersection} objet
     */
    public Intersection obtenirDestination() {
        return destination;
    }

    /**
     * <p>Accesseur Longueur.</p>
     *
     * @return un double
     */
    public double obtenirLongueur() {
        return longueur;
    }

    /**
     * <p>Modificateur Longueur.</p>
     *
     * @param l un double
     */
    public void modifierLongueur(double l) {
        this.longueur = l;
    }

    /**
     * <p>Modificateur Origine.</p>
     *
     * @param ori un {@link com.hexa17.pldagile.model.Intersection} objet
     */
    public void modifierOrigine(Intersection ori) {
        this.origine = ori;
    }

    /**
     * <p>Modificateur Destination.</p>
     *
     * @param dest un {@link com.hexa17.pldagile.model.Intersection} objet
     */
    public void modifierDestination(Intersection dest) {
        this.destination = dest;
    }

    /**
     * <p>Modificateur Nom.</p>
     *
     * @param n un {@link java.lang.String} objet
     */
    public void modifierNom(String n) {
        this.nom = n;
    }

    /**
     * <p>toString.</p>
     *
     * @return un {@link java.lang.String} objet
     */
    public String toString() {
        String description = "Nom : " + this.nom + " longueur : " + this.longueur + " origine : "
                + this.origine.obtenirId() + " destination : " + this.destination.obtenirId();
        return description;
    }
}
