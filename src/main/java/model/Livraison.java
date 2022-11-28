package model;

public class Livraison {
    
    private int plageHoraire;
    private Intersection lieu;
    private int livreur;

    public Livraison()
    {
    }
    public String toString()
    {
        String description = "Plage Horaire : " + this.plageHoraire + " lieu : " + "[a faire]" + " livreur : " + this.livreur;
        return description;
    }
    public Livraison(int horaire, Intersection l, int liv)
    {
        this.plageHoraire = horaire;
        this.lieu = l;
        this.livreur = liv;
    }

    public int obtenirPlageHoraire() {
        return plageHoraire;
    }

    public Intersection obtenirLieu() {
        return lieu;
    }

    public int obtenirLivreur() {
        return livreur;
    }

    public void modifierPlageHoraire(int plageHoraire) {
        this.plageHoraire = plageHoraire;
    }

    public void modifierLieu(Intersection lieu) {
        this.lieu = lieu;
    }

    public void modifierLivreur(int livreur) {
        this.livreur = livreur;
    }
}
