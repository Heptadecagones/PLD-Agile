package com.hexa17.pldagile.model;

import com.hexa17.pldagile.model.algo.Noeud;

//import java.util.ArrayList;

/**
 * <p>Intersection class.</p>
 *
 * @author Yannick, Hugo, Thibaut
 * @version $Id: $Id
 */
public class Intersection extends Noeud {

    // Ce sont l'ensemble des informations qui ne seront pas traités par Noeud
    private double longitude;
    private double latitude;
    // private ArrayList<Segment> listeSegmentOrigine;

    /**
     * Attributs hérités de Noeud :
     * - id : String
     * - cheminPlusCourtDepuisEntrepot : List<Noeud>
     * - poids : double
     * - noeudsAdjacents : Map<Noeud, Double>
     * - horaireLivrasion : int
     * le poids correspond à la distance
     */
    public Intersection() {
        
    }
    

    /**
     * <p>Constructor for Intersection.</p>
     *
     * @param i a {@link java.lang.String} object
     * @param longi a double
     * @param lat a double
     */
    public Intersection(String i, double longi, double lat) {
        super(i);
        this.longitude = longi;
        this.latitude = lat;
        // this.listeSegmentOrigine = new ArrayList<Segment>();
    }

    /**
     * <p>obtenirLongitude.</p>
     *
     * @return a double
     */
    public double obtenirLongitude() {
        return longitude;
    }

    /**
     * <p>obtenirLatitude.</p>
     *
     * @return a double
     */
    public double obtenirLatitude() {
        return latitude;
    }

    /**
     * <p>modifierLongitude.</p>
     *
     * @param longi a double
     */
    public void modifierLongitude(double longi) {
        this.longitude = longi;
    }

    /**
     * <p>modifierLatitude.</p>
     *
     * @param lat a double
     */
    public void modifierLatitude(double lat) {
        this.latitude = lat;
    }

    /*
     * public void ajouterSegment(Segment segment) {
     * this.listeSegmentOrigine.add(segment);
     * }
     */

    /** {@inheritDoc} */
    @Override
    public String toString() {
        String description = "Intersection : " + this.id + " longitude : " + this.longitude + " latitude : "
                + this.latitude;
        return description;
    }
}
