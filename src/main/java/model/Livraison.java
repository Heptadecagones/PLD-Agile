package model;

public class Livraison {
    
    private int plageHoraire;
    private Intersection lieu;
    private Livreur livreur;

    public Livraison()
    {
    }
    public String toString()
    {
        String description = "Plage Horaire : " + this.plageHoraire + " lieu : " + "[a faire]" + " livreur : " + this.livreur;
        return description;
    }
    public Livraison(int horaire, Intersection l, Livreur liv)
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
}
