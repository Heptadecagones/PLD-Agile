package model;

import java.util.ArrayList;

public class Intersection {
    
    private String id;
    private double longitude;
    private double latitude;
    private ArrayList<Segment> listeCheminOrigine;

    Intersection()
    {
    }

    Intersection(String i, double longi, double lat)
    {
        this.id = i;
        this.longitude = longi;
        this.latitude = lat;
        this.listeCheminOrigine = new ArrayList<>();
    }

    public double obtenirLongitude()
    {
        return longitude;
    }

    public double obtenirLatitude()
    {
        return latitude;
    }

    public String obtenirId()
    {
        return id;
    }

    public void modifierId(String i)
    {
        this.id = i;
    }

    public void modifierLongitude(double longi)
    {
        this.longitude = longi;
    }

    public void modifierLatitude(double lat)
    {
        this.latitude = lat;
    }

    public void ajouterRoute(Segment chemin)
    {
        this.listeCheminOrigine.add(chemin);
    }
}
