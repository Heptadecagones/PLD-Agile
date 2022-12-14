package com.hexa17.pldagile.model;

import java.util.ArrayList;

/**
 * <p>Tournee classe.</p>
 *
 * @author Yannick
 * @version $Id: $Id
 */
public class Tournee {

    //Liste de segment correspondant à tous les segments par lesquels doivent passer le livreur
    //afin de réaliser l'ensemble de ses livraisons
    private ArrayList<Segment> listeSegment;

    //Liste de livraison correspondant à l'ordre dans lequel les livraisons seront réalisées
    private ArrayList<Livraison> listeLivraison;



    /**
     * <p>Constructeur pour Tournee.</p>
     */
    public Tournee() {
        this.listeSegment = new ArrayList<Segment>();
        this.listeLivraison = new ArrayList<Livraison>();
    }

    /**
     * <p>Constructeur pour Tournee.</p>
     *
     * @param listeSegment un {@link java.util.ArrayList} objet
     * @param listeLivraison un {@link java.util.ArrayList} objet
     */
    public Tournee(ArrayList<Segment> listeSegment, ArrayList<Livraison> listeLivraison) {
        this.listeSegment = listeSegment;
        this.listeLivraison = listeLivraison;
    }

    

    /**
     * <p>AccesseurListeSegment.</p>
     *
     * @return un {@link java.util.ArrayList} objet
     */
    public ArrayList<Segment> obtenirListeSegment() {
        return listeSegment;
    }

    /**
     * <p>Modificateur ListeSegment.</p>
     *
     * @param listeSegment un {@link java.util.ArrayList} objet
     */
    public void modifierListeSegment(ArrayList<Segment> listeSegment) {
        this.listeSegment = listeSegment;
    }

    /**
     * <p>Accesseur ListeLivraison.</p>
     *
     * @return un {@link java.util.ArrayList} objet
     */
    public ArrayList<Livraison> obtenirListeLivraison() {
        return listeLivraison;
    }

    /**
     * <p>Modificateur ListeLivraison.</p>
     *
     * @param listeLivraison un {@link java.util.ArrayList} objet
     */
    public void modifierListeLivraison(ArrayList<Livraison> listeLivraison) {
        this.listeLivraison = listeLivraison;
    }

    /**
     * <p>toString.</p>
     *
     * @return un {@link java.lang.String} objet
     */
    public String toString() {
        String description = "Liste des segments :\n";
        for (int i = 0; i < this.listeSegment.size(); i++) {
            description += this.listeSegment.get(i);
            description += "\n";
        }
        return description;
    }
}
