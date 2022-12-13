package com.hexa17.pldagile.model;

import java.util.ArrayList;

/**
 * <p>Tournee class.</p>
 *
 * @author Yannick
 * @version $Id: $Id
 */
public class Tournee {

    private ArrayList<Segment> listeSegment;
    private ArrayList<Livraison> listeLivraison;



    /**
     * <p>Constructor for Tournee.</p>
     */
    public Tournee() {
        this.listeSegment = new ArrayList<Segment>();
        this.listeLivraison = new ArrayList<Livraison>();
    }

    /**
     * <p>Constructor for Tournee.</p>
     *
     * @param listeSegment a {@link java.util.ArrayList} object
     * @param listeLivraison a {@link java.util.ArrayList} object
     */
    public Tournee(ArrayList<Segment> listeSegment, ArrayList<Livraison> listeLivraison) {
        this.listeSegment = listeSegment;
        this.listeLivraison = listeLivraison;
    }

    

    /**
     * <p>obtenirListeSegment.</p>
     *
     * @return a {@link java.util.ArrayList} object
     */
    public ArrayList<Segment> obtenirListeSegment() {
        return listeSegment;
    }

    /**
     * <p>modifierListeSegment.</p>
     *
     * @param listeSegment a {@link java.util.ArrayList} object
     */
    public void modifierListeSegment(ArrayList<Segment> listeSegment) {
        this.listeSegment = listeSegment;
    }

    /**
     * <p>ajouterSegment.</p>
     *
     * @param seg a {@link com.hexa17.pldagile.model.Segment} object
     */
    public void ajouterSegment(Segment seg) {
        this.listeSegment.add(seg);
    }

    /**
     * <p>obtenirListeLivraison.</p>
     *
     * @return a {@link java.util.ArrayList} object
     */
    public ArrayList<Livraison> obtenirListeLivraison() {
        return listeLivraison;
    }

    /**
     * <p>modifierListeLivraison.</p>
     *
     * @param listeLivraison a {@link java.util.ArrayList} object
     */
    public void modifierListeLivraison(ArrayList<Livraison> listeLivraison) {
        this.listeLivraison = listeLivraison;
    }

    /**
     * <p>toString.</p>
     *
     * @return a {@link java.lang.String} object
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
