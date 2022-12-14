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

    // On un un passage de référence pour listeSegment, pas de copie
    /**
     * <p>Constructeur pour Lien.</p>
     *
     * @param listeSegment un {@link java.util.ArrayList} objet
     * @param poids un double
     */
    public Lien(ArrayList<Segment> listeSegment, double poids) {
        this.listeSegment = listeSegment;
        this.poids = poids;
    }

    // Equivalent de getFirst()
    /**
     * <p>Accesseur Chemin.</p>
     *
     * @return un {@link java.util.ArrayList} objet
     */
    public ArrayList<Segment> obtenirChemin() {
        return listeSegment;
    }

    // Equivalent de getSecond()
    /**
     * <p>Accesseur Poids.</p>
     *
     * @return un double
     */
    public double obtenirPoids() {
        return poids;
    }

    /**
     * <p>Modificateur Chemin.</p>
     *
     * @param listeSeg un {@link java.util.ArrayList} objet
     */
    public void modifierChemin(ArrayList<Segment> listeSeg) {
        this.listeSegment = listeSeg;
    }

    /**
     * <p>Modificateur Poids.</p>
     *
     * @param poids un double
     */
    public void modifierPoids(double poids) {
        this.poids = poids;
    }
}
