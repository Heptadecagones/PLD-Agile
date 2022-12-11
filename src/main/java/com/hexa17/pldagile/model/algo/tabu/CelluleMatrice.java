package com.hexa17.pldagile.model.algo.tabu;

public class CelluleMatrice {

    private double poids;
    private int horaire;

    public CelluleMatrice() {

    }

    public CelluleMatrice(double poids, int horaire) {
        this.poids = poids;
        this.horaire = horaire;
    }

    public double obtenirPoids() {
        return poids;
    }

    public void modifierPoids(double poids) {
        this.poids = poids;
    }

    public int obtenirHoraire() {
        return horaire;
    }

    public void modifierHoraire(int horaire) {
        this.horaire = horaire;
    }

}