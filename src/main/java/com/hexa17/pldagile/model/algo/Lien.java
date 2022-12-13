package com.hexa17.pldagile.model.algo;

import java.util.ArrayList;

import com.hexa17.pldagile.model.Segment;

/**
 * <p>Lien class.</p>
 *
 * @author Hugo
 * Classe pour obtenir l'équivalent d'une paire
 * @version $Id: $Id
 */
public class Lien {
    private ArrayList<Segment> listeSegment;
    private double poids;

    // On a un passage de référence pour listeSegment, pas de copie
    /**
     * <p>Constructor for Lien.</p>
     *
     * @param listeSegment a {@link java.util.ArrayList} object
     * @param poids a double
     */
    public Lien(ArrayList<Segment> listeSegment, double poids) {
        this.listeSegment = listeSegment;
        this.poids = poids;
    }

    // Equivalent de getFirst()
    /**
     * <p>obtenirChemin.</p>
     *
     * @return a {@link java.util.ArrayList} object
     */
    public ArrayList<Segment> obtenirChemin() {
        return listeSegment;
    }

    // Equivalent de getSecond()
    /**
     * <p>obtenirPoids.</p>
     *
     * @return a double
     */
    public double obtenirPoids() {
        return poids;
    }

    /**
     * <p>modifierChemin.</p>
     *
     * @param listeSeg a {@link java.util.ArrayList} object
     */
    public void modifierChemin(ArrayList<Segment> listeSeg) {
        this.listeSegment = listeSeg;
    }

    /**
     * <p>modifierPoids.</p>
     *
     * @param poids a double
     */
    public void modifierPoids(double poids) {
        this.poids = poids;
    }
}
