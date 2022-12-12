package com.hexa17.pldagile.model;

import com.hexa17.pldagile.model.algo.Graphe;
import com.hexa17.pldagile.model.algo.Noeud;

import java.util.ArrayList;
import java.util.Map;

//TODO Singleton Design pattern
public class Plan extends Graphe {
    private Intersection entrepot;

    private ArrayList<Intersection> listeIntersection;
    private ArrayList<Segment> listeSegment;

    public Plan(ArrayList<Intersection> listeIntersection, ArrayList<Segment> listeSegment, Intersection entrepot, Map<Noeud, ArrayList<Segment>> liensEntreNoeuds) {
        super(liensEntreNoeuds);
        this.listeIntersection = listeIntersection;
        this.listeSegment = listeSegment;
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
