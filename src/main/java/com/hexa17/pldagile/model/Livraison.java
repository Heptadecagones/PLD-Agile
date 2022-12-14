package com.hexa17.pldagile.model;

/**
 * <p>Livraison classe.</p>
 *
 * @author Yannick
 * @version $Id: $Id
 */
public class Livraison {

    //Heure de livraison
    private double heureLivraison;

    //Horaire de livraison
    private int horaireLivraison;

    //Intersection correspondant au lieu de la livraison
    private Intersection lieu;

    //Vrai si l'heure de livraison est dans la plage horaire de livraison
    private boolean valide;

    //Livreur qui doit faire cette livraison 
    private Livreur livreur;

    /**
     * <p>Constructeur pour Livraison.</p>
     */
    public Livraison() {

    }

    /**
     * <p>Constructeur pour Livraison.</p>
     *
     * @param horaire un int
     * @param l un {@link com.hexa17.pldagile.model.Intersection} objet
     * @param liv un {@link com.hexa17.pldagile.model.Livreur} objet
     */
    public Livraison(int horaire, Intersection l, Livreur liv) {

        this.lieu = l;
        this.livreur = liv;
        this.heureLivraison = 0;
        this.horaireLivraison = horaire;
        this.valide = true;
    }

    /**
     * <p>Constructeur pour Livraison.</p>
     *
     * @param heure un double
     * @param horaire un int
     * @param l un {@link com.hexa17.pldagile.model.Intersection} objet
     * @param liv un {@link com.hexa17.pldagile.model.Livreur} objet
     */
    public Livraison(double heure,int horaire, Intersection l, Livreur liv) {

        this.lieu = l;
        this.livreur = liv;
        this.heureLivraison = 0;
        this.horaireLivraison = horaire;
        this.heureLivraison=heure;
        this.valide = (heureLivraison < horaireLivraison+1);
    }


    /**
     * <p>toString.</p>
     *
     * @return un {@link java.lang.String} objet
     */
    public String toString() {
        final long HH = (long)(this.heureLivraison);
        final long MM = (long)((this.heureLivraison - HH) * 60);
        String description="";
        if(!valide){
            description+="INVALIDE ";
        }

        description+= "Livreur : " + livreur.obtenirId()+ " " + livreur.obtenirNom() + " Heure d'arrivée: "
                + HH + "h" + MM + " lieu : " + this.lieu;
        return description;
    }

    /**
     * <p>Accesseur lieu.</p>
     *
     * @return un {@link com.hexa17.pldagile.model.Intersection} objet
     */
    public Intersection obtenirLieu() {
        return lieu;
    }

    /**
     * <p>Accesseur livreur.</p>
     *
     * @returnun{@link com.hexa17.pldagile.model.Livreur} objet
     */
    public Livreur obtenirLivreur() {
        return livreur;
    }

    /**
     * <p>Modificateur lieu.</p>
     *
     * @param lieu un {@link com.hexa17.pldagile.model.Intersection} objet
     */
    public void modifierLieu(Intersection lieu) {
        this.lieu = lieu;
    }

    /**
     * <p>modificateru livreur.</p>
     *
     * @param livreur un {@link com.hexa17.pldagile.model.Livreur} objet
     */
    public void modifierLivreur(Livreur livreur) {
        this.livreur = livreur;
    }

    /**
     * <p>Accesseur heure de livraison.</p>
     *
     * @return un double
     */
    public double obtenirHeureLivraison() {
        return heureLivraison;
    }

    /**
     * <p>Modificateur heure de livraison.</p>
     *
     * @param heureLivraison un double
     */
    public void modifierHeureLivraison(double heureLivraison) {
        this.valide = (heureLivraison < horaireLivraison+1);
        this.heureLivraison = heureLivraison;
    }

    /**
     * <p>Accesseur horaire de livraison.</p>
     *
     * @return un int
     */
    public int obtenirHoraireLivraison() {
        return horaireLivraison;
    }

    /**
     * <p>Modificateur horaire de livraison..</p>
     *
     * @param horaireLivraison un int
     */
    public void modifierHoraireLivraison(int horaireLivraison) {
        this.horaireLivraison = horaireLivraison;
    }

    /**
     * <p>Accesseur validité.</p>
     *
     * @returnunboolean
     */
    public boolean estValide() {
        return valide;
    }

    /**
     * <p>Modificateur validité.</p>
     *
     * @param valideunboolean
     */
    public void modifierValide(boolean valide) {
        this.valide = valide;
    }

}
