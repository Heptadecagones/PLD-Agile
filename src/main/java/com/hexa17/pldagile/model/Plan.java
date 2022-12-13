package com.hexa17.pldagile.model;

import com.hexa17.pldagile.model.algo.Graphe;
import com.hexa17.pldagile.model.algo.Noeud;

import java.util.ArrayList;
import java.util.Map;

/**
 * <p>Plan class.</p>
 *
 * @author omi
 * @version $Id: $Id
 */
public class Plan extends Graphe {
    private Intersection entrepot;

    private ArrayList<Intersection> listeIntersection;
    private ArrayList<Segment> listeSegment;

    /**
     * <p>Constructor for Plan.</p>
     *
     * @param listeIntersection a {@link java.util.ArrayList} object
     * @param listeSegment a {@link java.util.ArrayList} object
     * @param entrepot a {@link com.hexa17.pldagile.model.Intersection} object
     * @param liensEntreNoeuds a {@link java.util.Map} object
     */
    public Plan(ArrayList<Intersection> listeIntersection, ArrayList<Segment> listeSegment, Intersection entrepot, Map<Noeud, ArrayList<Segment>> liensEntreNoeuds) {
        super(liensEntreNoeuds);
        this.listeIntersection = listeIntersection;
        this.listeSegment = listeSegment;
        this.entrepot = entrepot;
    }

    /**
     * <p>obtenirEntrepot.</p>
     *
     * @return a {@link com.hexa17.pldagile.model.Intersection} object
     */
    public Intersection obtenirEntrepot() {
        return entrepot;
    }

    /**
     * <p>obtenirNombreIntersection.</p>
     *
     * @return a long
     */
    public long obtenirNombreIntersection() {
        return listeIntersection.size();
    }

    /**
     * <p>obtenirNombreSegment.</p>
     *
     * @return a long
     */
    public long obtenirNombreSegment() {
        return listeSegment.size();
    }

    /**
     * <p>obtenirListeIntersection.</p>
     *
     * @return a {@link java.util.ArrayList} object
     */
    public ArrayList<Intersection> obtenirListeIntersection() {
        return listeIntersection;
    }

    /**
     * <p>obtenirListeSegment.</p>
     *
     * @return a {@link java.util.ArrayList} object
     */
    public ArrayList<Segment> obtenirListeSegment() {
        return listeSegment;
    }
}
