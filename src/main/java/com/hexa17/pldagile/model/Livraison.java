package com.hexa17.pldagile.model;

/**
 * <p>Livraison class.</p>
 *
 * @author Yannick
 * @version $Id: $Id
 */
public class Livraison {

    private double heureLivraison;
    private int horaireLivraison;
    private Intersection lieu;
    private boolean valide;
    // inutile pour l'instant
    private Livreur livreur;
    //

    /**
     * <p>Constructor for Livraison.</p>
     */
    public Livraison() {
    }

    /**
     * <p>toString.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String toString() {
        final long HH = (long)(this.heureLivraison);
        final long MM = (long)((this.heureLivraison - HH) * 60);
        String description="";
        if(!valide){
            description+="INVALIDE ";
        }

        description+= "Livreur : " + livreur.obtenirId()+ " " + livreur.obtenirNom() + " Heure d'arrivée: "
                + HH + "h" + MM + " lieu : " + this.lieu;
        return description;
    }

    /**
     * <p>Constructor for Livraison.</p>
     *
     * @param horaire a int
     * @param l a {@link com.hexa17.pldagile.model.Intersection} object
     * @param liv a {@link com.hexa17.pldagile.model.Livreur} object
     */
    public Livraison(int horaire, Intersection l, Livreur liv) {

        this.lieu = l;
        this.livreur = liv;
        this.heureLivraison = 0;
        this.horaireLivraison = horaire;
        this.valide = true;
    }
    /**
     * <p>Constructor for Livraison.</p>
     *
     * @param heure a double
     * @param horaire a int
     * @param l a {@link com.hexa17.pldagile.model.Intersection} object
     * @param liv a {@link com.hexa17.pldagile.model.Livreur} object
     */
    public Livraison(double heure,int horaire, Intersection l, Livreur liv) {

        this.lieu = l;
        this.livreur = liv;
        this.heureLivraison = 0;
        this.horaireLivraison = horaire;
        this.heureLivraison=heure;
        this.valide = (heureLivraison < horaireLivraison+1);
    }

    /**
     * <p>obtenirLieu.</p>
     *
     * @return a {@link com.hexa17.pldagile.model.Intersection} object
     */
    public Intersection obtenirLieu() {
        return lieu;
    }

    /**
     * <p>obtenirLivreur.</p>
     *
     * @return a {@link com.hexa17.pldagile.model.Livreur} object
     */
    public Livreur obtenirLivreur() {
        return livreur;
    }

    /**
     * <p>modifierLieu.</p>
     *
     * @param lieu a {@link com.hexa17.pldagile.model.Intersection} object
     */
    public void modifierLieu(Intersection lieu) {
        this.lieu = lieu;
    }

    /**
     * <p>modifierLivreur.</p>
     *
     * @param livreur a {@link com.hexa17.pldagile.model.Livreur} object
     */
    public void modifierLivreur(Livreur livreur) {
        this.livreur = livreur;
    }

    /**
     * <p>obtenirHeureLivraison.</p>
     *
     * @return a double
     */
    public double obtenirHeureLivraison() {
        return heureLivraison;
    }

    /**
     * <p>modifierHeureLivraison.</p>
     *
     * @param heureLivraison a double
     */
    public void modifierHeureLivraison(double heureLivraison) {
        this.valide = (heureLivraison < horaireLivraison+1);
        this.heureLivraison = heureLivraison;
    }

    /**
     * <p>obtenirHoraireLivraison.</p>
     *
     * @return a int
     */
    public int obtenirHoraireLivraison() {
        return horaireLivraison;
    }

    /**
     * <p>modifierHoraireLivraison.</p>
     *
     * @param horaireLivraison a int
     */
    public void modifierHoraireLivraison(int horaireLivraison) {
        this.horaireLivraison = horaireLivraison;
    }

    /**
     * <p>estValide.</p>
     *
     * @return a boolean
     */
    public boolean estValide() {
        return valide;
    }

    /**
     * <p>modifierValide.</p>
     *
     * @param valide a boolean
     */
    public void modifierValide(boolean valide) {
        this.valide = valide;
    }

}
