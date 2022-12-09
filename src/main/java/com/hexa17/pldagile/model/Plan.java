package com.hexa17.pldagile.model;

import com.hexa17.pldagile.model.algo.Graphe;

import java.util.ArrayList;

//TODO Singleton Design pattern
public class Plan extends Graphe {
    private Intersection entrepot;

    private static ArrayList<Intersection> listeIntersection;
    private static ArrayList<Segment> listeSegment;

    public Plan(ArrayList<Intersection> listeIntersection, ArrayList<Segment> listeSegment, Intersection entrepot) {
        super(listeIntersection, listeSegment); // C'est super
        Plan.listeIntersection = listeIntersection;
        Plan.listeSegment = listeSegment;
        this.entrepot = entrepot;
    }

    public Intersection obtenirEntrepot() {
        return entrepot;
    }

    public long obtenirNombreIntersection() {
        return listeIntersection.size();
    }

    public long obtenirNombreSegment() {
        return listeSegment.size();
    }

    public ArrayList<Intersection> obtenirListeIntersection() {
        return listeIntersection;
    }

    public ArrayList<Segment> obtenirListeSegment() {
        return listeSegment;
    }
}
