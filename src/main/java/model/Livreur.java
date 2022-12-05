package model;

import java.util.ArrayList;

/**
 *
 * @author Yannick
 */

public class Livreur {

    private int id;
    private boolean disponibilite;
    private ArrayList<Livraison> livraisons;
    private Tournee tournee;

    public Livreur(int id) {
        this.id = id;
        this.disponibilite = true;
        this.livraisons = new ArrayList<Livraison>();
        this.tournee = new Tournee();
    }

    public int obtenirId() {
        return id;
    }

    public void modifierId(int id) {
        this.id = id;
    }

    public boolean estDisponible() {
        return disponibilite;
    }

    public void modifierDisponibilite(boolean disponbilite) {
        this.disponibilite = disponbilite;
    }

    public ArrayList<Livraison> obtenirLivraisons() {
        return livraisons;
    }

    public void modifierLivraisons(ArrayList<Livraison> livraisons) {
        this.livraisons = livraisons;
    }

    public String toString() {
        String description = "id: " + this.id + " dispo: " + this.disponibilite + " ";
        for (Livraison l : this.livraisons) {
            description += " livraison: " + l;
        }
        return description;
    }

    public Tournee obtenirTournee() {
        return tournee;
    }
}
