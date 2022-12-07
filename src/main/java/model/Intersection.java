package model;

import java.util.ArrayList;

/**
 *
 * @author Yannick
 */

public class Intersection {

    private String id;
    private double longitude;
    private double latitude;
    private ArrayList<Segment> listeSegmentOrigine;

    private Integer trancheHoraireLivraison;

    public Intersection() {
    }

    public Intersection(String i, double longi, double lat) {
        this.id = i;
        this.longitude = longi;
        this.latitude = lat;
        this.listeSegmentOrigine = new ArrayList<Segment>();
        trancheHoraireLivraison = null;
    }

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

    public String toString() {
        String description = "Id : " + this.id + " longitude : " + this.longitude + " latitude : " + this.latitude;
        return description;
    }

    /**
     * Méthode pour ajouter une tranche horaire passée en paramètres.
     * La tranche horaire est acceptée si elle appartient à [8;11] et refusée
     * sinon.
     * @param trancheHoraire
     * @return true si la trancheHoraire est acceptée, false si elle est refusée
     */
    public boolean modifierTrancheHoraire(Integer trancheHoraire) {
        if(trancheHoraire < 8 || trancheHoraire > 11) {
            trancheHoraireLivraison = trancheHoraire;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 
     */
    public void calculerPlusCourtChemin() {

    }
}
