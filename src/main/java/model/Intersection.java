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

    public Intersection() {
    }

    public Intersection(String i, double longi, double lat) {
        this.id = i;
        this.longitude = longi;
        this.latitude = lat;
        this.listeSegmentOrigine = new ArrayList<Segment>();
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

    public ArrayList<Segment> obtenirListeSegmentOrigine() {
        return listeSegmentOrigine;
    }

    public String toString() {
        String description = "Id : " + this.id + " longitude : " + this.longitude + " latitude : " + this.latitude;
        return description;
    }
}
