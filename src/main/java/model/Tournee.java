package model;

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

    public Tournee(ArrayList<Segment> liste) {
        this.listeSegment = liste;
    }

    public ArrayList<Segment> obtenirListeSegment() {
        return listeSegment;
    }

    public void modifierListeSegment(ArrayList<Segment> listeSegment) {
        this.listeSegment = listeSegment;
    }
    public ArrayList<Livraison> obtenirListeLivraison() {
        return listeLivraison;
    }

    public void modifierListeLivraison(ArrayList<Livraison> listeLivraison) {
        this.listeLivraison = listeLivraison;
    }

    public void ajouterSegment(Segment seg) {
        this.listeSegment.add(seg);
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
