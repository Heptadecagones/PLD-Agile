package model;

import model.algo.Dijkstra.Noeud;

//import java.util.ArrayList;

/**
 *
 * @author Yannick
 */

public class Intersection extends Noeud {

    // Ce sont l'ensemble des informations qui ne seront pas traités par Noeud
    private double longitude;
    private double latitude;
    //private ArrayList<Segment> listeSegmentOrigine;
    

    /**
     * Attributs hérités de Noeud :
     * - id : String
     * - cheminPlusCourtDepuisEntrepot : List<Noeud>
     * - poids : double
     * - noeudsAdjacents : Map<Noeud, Double>
     * - horaireLivrasion : int
     * le poids correspond à la distance
     */

    /*public Intersection() {
    }*/

    public Intersection(String i, double longi, double lat) {
        super(i);
        this.longitude = longi;
        this.latitude = lat;
        //this.listeSegmentOrigine = new ArrayList<Segment>();
    }

    public double obtenirLongitude() {
        return longitude;
    }

    public double obtenirLatitude() {
        return latitude;
    }


    public void modifierLongitude(double longi) {
        this.longitude = longi;
    }

    public void modifierLatitude(double lat) {
        this.latitude = lat;
    }

    /*public void ajouterSegment(Segment segment) {
        this.listeSegmentOrigine.add(segment);
    }*/

    @Override
    public String toString() {
        String description = "Intersection : " + this.id + " longitude : " + this.longitude + " latitude : " + this.latitude;
        return description;
    }
}
