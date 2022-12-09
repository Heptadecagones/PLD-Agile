package com.hexa17.pldagile.model;

/**
 *
 * @author Yannick
 */

public class Livraison {

    private int plageHoraire;
    private double heureLivraison;
    private Intersection lieu;
    // inutile pour l'instant
    private Livreur livreur;
    //

    public Livraison() {
    }

    public String toString() {
        String description = "Plage Horaire : " + this.plageHoraire + " lieu : " + this.lieu;
        return description;
    }

    public Livraison(int horaire, Intersection l, Livreur liv) {

        this.plageHoraire = horaire;
        this.lieu = l;
        this.livreur = liv;
        this.heureLivraison = 0;
    }

    public int obtenirPlageHoraire() {
        return plageHoraire;
    }

    public Intersection obtenirLieu() {
        return lieu;
    }

    public Livreur obtenirLivreur() {
        return livreur;
    }

    public void modifierPlageHoraire(int plageHoraire) {
        this.plageHoraire = plageHoraire;
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

    
}
