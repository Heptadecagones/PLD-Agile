package model;

public class Livreur {
    
    private int id;
    private boolean disponbilite;

    public Livreur(int id) {
        this.id = id;
        this.disponbilite = true;
    }

    public String toString() {
        return "Livreur [id=" + id + ", disponbilite=" + disponbilite + "]";
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

    
}
