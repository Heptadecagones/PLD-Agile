package com.hexa17.pldagile.model.algo;

import java.util.ArrayList;
import java.util.Map;

import com.hexa17.pldagile.model.Segment;

/**
 * <p>Graphe class.</p>
 *
 * @author Yannick
 * @version $Id: $Id
 */
public class Graphe {
    
    /* 
     * Pour un noeud contient l'ensemble des segments qui partent de celui-ci
     */
    protected Map<Noeud, ArrayList<Segment>> liensEntreNoeuds;

    /** {@inheritDoc} */
    @Override
    public String toString() {
        String s = super.toString() + "\n";

        return s;
    }

    /**
     * <p>Constructor for Graphe.</p>
     */
    public Graphe() {
    }

    /**
     * <p>Constructor for Graphe.</p>
     *
     * @param liensEntreNoeuds a {@link java.util.Map} object
     */
    public Graphe(Map<Noeud, ArrayList<Segment>> liensEntreNoeuds) {
        this.liensEntreNoeuds = liensEntreNoeuds;
    }

    /**
     * <p>obtenirLiensEntreNoeuds.</p>
     *
     * @return a {@link java.util.Map} object
     */
    public Map<Noeud, ArrayList<Segment>> obtenirLiensEntreNoeuds() {
        return liensEntreNoeuds;
    }

}
