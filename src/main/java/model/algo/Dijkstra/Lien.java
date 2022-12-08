package model.algo.Dijkstra;

import java.util.ArrayList;

import model.Segment;

/**
 * @author Hugo
 * Classe pour obtenir l'équivalent d'une paire
 */
public class Lien {
    private ArrayList<Segment> listeSegment;
    private double poids;

    // On a un passage de référence pour listeSegment, pas de copie
    public Lien(ArrayList<Segment> listeSegment, double poids) {
        this.listeSegment = listeSegment;
        this.poids = poids;
    }

    // Equivalent de getFirst()
    public ArrayList<Segment> obtenirChemin() {
        return listeSegment;
    }

    // Equivalent de getSecond()
    public double obtenirPoids() {
        return poids;
    }
}
