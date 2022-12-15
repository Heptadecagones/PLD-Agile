package com.hexa17.pldagile.model;

import com.hexa17.pldagile.model.algo.Graphe;
import com.hexa17.pldagile.model.algo.Noeud;

import java.util.ArrayList;
import java.util.Map;

/**
 * <p>Plan classe.</p>
 *
 * @author omi
 * @version $Id: $Id
 */
public class Plan extends Graphe {
    private Intersection entrepot;

    private ArrayList<Intersection> listeIntersection;
    private ArrayList<Segment> listeSegment;

    /**
     * <p>Constructeur pour Plan.</p>
     *
     * @param listeIntersection un {@link java.util.ArrayList} objet
     * @param listeSegment un {@link java.util.ArrayList} objet
     * @param entrepot un {@link com.hexa17.pldagile.model.Intersection} objet
     * @param liensEntreNoeuds un {@link java.util.Map} objet
     */
    public Plan(ArrayList<Intersection> listeIntersection, ArrayList<Segment> listeSegment, Intersection entrepot, Map<Noeud, ArrayList<Segment>> liensEntreNoeuds) {
        super(liensEntreNoeuds);
        this.listeIntersection = listeIntersection;
        this.listeSegment = listeSegment;
        this.entrepot = entrepot;
    }

    /**
     * <p>Accesseur Entrepot.</p>
     *
     * @return un {@link com.hexa17.pldagile.model.Intersection} objet
     */
    public Intersection obtenirEntrepot() {
        return entrepot;
    }

    /**
     * <p>Accesseur ListeIntersection.</p>
     *
     * @return un {@link java.util.ArrayList} objet
     */
    public ArrayList<Intersection> obtenirListeIntersection() {
        return listeIntersection;
    }

    /**
     * <p>Accesseur ListeSegment.</p>
     *
     * @return un {@link java.util.ArrayList} objet
     */
    public ArrayList<Segment> obtenirListeSegment() {
        return listeSegment;
    }
}
