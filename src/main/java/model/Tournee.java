package model;

import java.util.ArrayList;

public class Tournee {

    private ArrayList<Segment> listeSegment;

    public Tournee() {
        this.listeSegment = new ArrayList<Segment>();
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
