package model;

import java.util.ArrayList;

public class Livreur {

    private int id;
    private boolean disponbilite;
    private ArrayList<Livraison> livraisons;

    public Livreur(int id) {
        this.id = id;
        this.disponbilite = true;
        this.livraisons = new ArrayList<Livraison>();
    }

    public int obtenirId() {
        return id;
    }

    public void modifierId(int id) {
        this.id = id;
    }

    public boolean estDisponbilite() {
        return disponbilite;
    }

    public void modifierDisponbilite(boolean disponbilite) {
        this.disponbilite = disponbilite;
    }

    public ArrayList<Livraison> obtenirLivraisons() {
        return livraisons;
    }

    public void modifierLivraisons(ArrayList<Livraison> livraisons) {
        this.livraisons = livraisons;
    }

    public String toString() {
        String description = "id: " + this.id + " dispo: " + this.disponbilite + " ";
        for (Livraison l : this.livraisons) {
            description += " livraison: " + l;
        }
        return description;
    }
}
