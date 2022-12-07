package model;

import model.algo.Dijkstra.Graphe;

import java.util.ArrayList;

public class Plan extends Graphe implements Cloneable {
    private Intersection entrepot;

    private static ArrayList<Intersection> listeIntersection;
    private static ArrayList<Segment> listeSegment;

    public Plan(ArrayList<Intersection> listeIntersection, ArrayList<Segment> listeSegment, Intersection entrepot) {
        super(listeIntersection, listeSegment); // C'est super
        this.listeIntersection = listeIntersection;
        this.listeSegment = listeSegment;
        this.entrepot = entrepot;
    }

    @Override
    public Plan clone() {
        Plan clonedPlan = null;
        try {
            clonedPlan = (Plan) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return clonedPlan;
    }

    // chargement d'un plan( avec segments,intersections,entrepot) à partir d'un
    // fichier XML

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
