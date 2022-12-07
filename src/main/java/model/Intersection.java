package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ch.qos.logback.core.joran.sanity.Pair;

/**
 *
 * @author Yannick
 */

public class Intersection {

    private String id;
    private double longitude;
    private double latitude;
    private ArrayList<Segment> listeSegmentOrigine;
    private Integer horaireLivraison;

    private Map<Intersection, Pair<ArrayList<Segment>, Double>> arborescence;

    public Intersection() {
    }

    public Intersection(String i, double longi, double lat) {
        this.id = i;
        this.longitude = longi;
        this.latitude = lat;
        this.listeSegmentOrigine = new ArrayList<Segment>();
        this.horaireLivraison = null;
        this.arborescence = new HashMap<>();
    }
    
    //TODO check return type
    //TODO interface
    /*public void () {

    }*/

    public double obtenirLongitude() {
        return longitude;
    }

    public double obtenirLatitude() {
        return latitude;
    }

    public String obtenirId() {
        return id;
    }

    public void modifierId(String i) {
        this.id = i;
    }

    public void modifierLongitude(double longi) {
        this.longitude = longi;
    }

    public void modifierLatitude(double lat) {
        this.latitude = lat;
    }

    public void ajouterSegment(Segment segment) {
        this.listeSegmentOrigine.add(segment);
    }

    public Map<Intersection, Pair<ArrayList<Segment>, Double>> obtenirArborescence() {
        return arborescence;
    }


    public String toString() {
        String description = "Id : " + this.id + " longitude : " + this.longitude + " latitude : " + this.latitude;
        return description;
    }

    /**
     * Méthode pour ajouter une tranche horaire passée en paramètres.
     * La tranche horaire est acceptée si elle appartient à [8;11] et refusée
     * sinon.
     * 
     * @param trancheHoraire
     * @return true si la trancheHoraire est acceptée, false si elle est refusée
     */
    public boolean modifierTrancheHoraire(Integer trancheHoraire) {
        if (trancheHoraire < 8 || trancheHoraire > 11) {
            return false;
        } else {
            horaireLivraison = trancheHoraire;
            return true;
        }
    }

    /**
     * 
     */
    public void calculerArborescenceChemins(ArrayList<Intersection> intersections) {

    }
}
