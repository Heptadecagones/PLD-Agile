package model;

public class Livreur {
    
    private String id;
    private boolean disponbilite;

    public Livreur(String id) {
        this.id = id;
        this.disponbilite = true;
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

    
}
