package model;

import java.util.ArrayList;

public class Livreur {
    
    private String id;
    private boolean disponbilite;
    private ArrayList<Livraison> livraisons;

    public Livreur(String id) {
        this.id = id;
        this.disponbilite = true;
        this.livraisons = new ArrayList<Livraison>();
    }

    public String obtenirId() {
        return id;
    }

    public void modifierId(String id) {
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

    
}
