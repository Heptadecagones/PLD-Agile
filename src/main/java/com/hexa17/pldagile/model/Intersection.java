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
    //Coordonnée de l'intersection
    private double longitude;
    private double latitude;

    /**
     * Attributs hérités de Noeud :
     * - id : String
     * - arborescence : Map<Noeud, Lien>
     */

     /**
     * <p>Constructeur pour Intersection.</p>
     */
    public Intersection() {
        
    }
    

    /**
     * <p>Constructeur pour Intersection.</p>
     *
     * @param i a {@link java.lang.String} object
     * @param longi a double
     * @param lat a double
     */
    public Intersection(String i, double longi, double lat) {
        super(i);
        this.longitude = longi;
        this.latitude = lat;
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

    /** {@inheritDoc} */
    @Override
    public String toString() {
        String description = "Intersection : " + this.id + " longitude : " + this.longitude + " latitude : "
                + this.latitude;
        return description;
    }
}
