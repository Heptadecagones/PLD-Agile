package model;

public class Segment {

    private String nom;
    private Intersection origine;
    private Intersection destination;
    private double longueur;

    public Segment() {
    }

    public Segment(String n, Intersection ori, Intersection dest, double l) {
        this.nom = n;
        this.origine = ori;
        this.destination = dest;
        this.longueur = l;
    }

    public String obtenirNom() {
        return nom;
    }

    public Intersection obtenirOrigine() {
        return origine;
    }

    public Intersection obtenirDestination() {
        return destination;
    }

    public double obtenirLongueur() {
        return longueur;
    }

    public void modifierLongueur(double l) {
        this.longueur = l;
    }

    public void modifierOrigine(Intersection ori) {
        this.origine = ori;
    }

    public void modifierDestination(Intersection dest) {
        this.destination = dest;
    }

    public void modifierNom(String n) {
        this.nom = n;
    }

    public String toString() {
        String description = "Nom : " + this.nom + " longueur : " + this.longueur + " origine : "
                + this.origine.obtenirId() + " destination : " + this.destination.obtenirId();
        return description;
    }
}
