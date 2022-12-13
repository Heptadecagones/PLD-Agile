package com.hexa17.pldagile.model;

/**
 *
 * @author Yannick
 */

public class Livraison {

    private double heureLivraison;
    private int horaireLivraison;
    private Intersection lieu;
    private boolean valide;
    // inutile pour l'instant
    private Livreur livreur;
    //

    public Livraison() {
    }

    public String toString() {
        final long HH = (long)(this.heureLivraison);
        final long MM = (long)((this.heureLivraison - HH) * 60);
        String description = "Livreur : " + livreur.obtenirId() + livreur.obtenirNom() + "Heure d'arrivée: "
                + HH + "h" + MM + " lieu : " + this.lieu;
        return description;
    }

    public Livraison(int horaire, Intersection l, Livreur liv) {

        this.lieu = l;
        this.livreur = liv;
        this.heureLivraison = 0;
        this.horaireLivraison = horaire;
        this.valide = true;
    }
    public Livraison(double heure,int horaire, Intersection l, Livreur liv) {

        this.lieu = l;
        this.livreur = liv;
        this.heureLivraison = 0;
        this.horaireLivraison = horaire;
        this.valide = true;
        this.heureLivraison=heure;
    }

    public Intersection obtenirLieu() {
        return lieu;
    }

    public Livreur obtenirLivreur() {
        return livreur;
    }

    public void modifierLieu(Intersection lieu) {
        this.lieu = lieu;
    }

    public void modifierLivreur(Livreur livreur) {
        this.livreur = livreur;
    }

    public double obtenirHeureLivraison() {
        return heureLivraison;
    }

    public void modifierHeureLivraison(double heureLivraison) {
        this.heureLivraison = heureLivraison;
    }

    public int obtenirHoraireLivraison() {
        return horaireLivraison;
    }

    public void modifierHoraireLivraison(int horaireLivraison) {
        this.horaireLivraison = horaireLivraison;
    }

    public boolean estValide() {
        return valide;
    }

    public void modifierValide(boolean valide) {
        this.valide = valide;
    }

    

}
