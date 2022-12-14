package com.hexa17.pldagile.model;

import com.hexa17.pldagile.model.algo.Noeud;

//import java.util.ArrayList;

/**
 * <p>Intersection classe.</p>
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
     * @param i un {@link java.lang.String} objet
     * @param longi un double
     * @param lat un double
     */
    public Intersection(String i, double longi, double lat) {
        super(i);
        this.longitude = longi;
        this.latitude = lat;
    }

    /**
     * <p>Accesseur de longitude</p>
     * 
     * @return un double
     */
    public double obtenirLongitude() {
        return longitude;
    }

    /**
     * <p>Accesseur de latitude.</p>
     *
     * @return un double
     */
    public double obtenirLatitude() {
        return latitude;
    }

    /**
     * <p>Modificateur longitude.</p>
     *
     * @param longi un double
     */
    public void modifierLongitude(double longi) {
        this.longitude = longi;
    }

    /**
     * <p>Modificateur latitude.</p>
     *
     * @param lat un double
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
