package com.hexa17.pldagile.model;

/**
 *
 * @author Yannick
 */

public class Livraison {

    private double heureLivraison;
    private int horaireLivraison;
    private Intersection lieu;
    // inutile pour l'instant
    private Livreur livreur;
    //

    public Livraison() {
    }

    public String toString() {
        String description = "Livreur : "+livreur.obtenirId() + livreur.obtenirNom() + "Plage Horaire : " + this.horaireLivraison + " lieu : " + this.lieu;
        return description;
    }

    public Livraison(int horaire, Intersection l, Livreur liv) {

        this.lieu = l;
        this.livreur = liv;
        this.heureLivraison = 0;
        this.horaireLivraison = horaire;
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

    

    
}
