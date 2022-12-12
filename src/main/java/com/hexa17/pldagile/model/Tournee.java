package com.hexa17.pldagile.model;

import java.util.ArrayList;

/**
 *
 * @author Yannick
 */

public class Tournee {

    private ArrayList<Segment> listeSegment;
    private ArrayList<Livraison> listeLivraison;

    public Tournee() {
        this.listeSegment = new ArrayList<Segment>();
        this.listeLivraison = new ArrayList<Livraison>();
    }

    public Tournee(ArrayList<Segment> listeSegment, ArrayList<Livraison> listeLivraison) {
        this.listeSegment = listeSegment;
        this.listeLivraison = listeLivraison;
    }

    public ArrayList<Segment> obtenirListeSegment() {
        return listeSegment;
    }

    public void modifierListeSegment(ArrayList<Segment> listeSegment) {
        this.listeSegment = listeSegment;
    }

    public void ajouterSegment(Segment seg) {
        this.listeSegment.add(seg);
    }

    public ArrayList<Livraison> obtenirListeLivraison() {
        return listeLivraison;
    }

    public void modifierListeLivraison(ArrayList<Livraison> listeLivraison) {
        this.listeLivraison = listeLivraison;
    }

    public String toString() {
        String description = "Liste des segments :\n";
        for (int i = 0; i < this.listeSegment.size(); i++) {
            description += this.listeSegment.get(i);
            description += "\n";
        }
        return description;
    }
}
