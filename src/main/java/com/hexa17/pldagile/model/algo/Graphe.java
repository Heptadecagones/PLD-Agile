package com.hexa17.pldagile.model.algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.hexa17.pldagile.model.Intersection;
import com.hexa17.pldagile.model.Segment;

/**
 * <p>Graphe class.</p>
 *
 * @author Yannick
 * @version $Id: $Id
 */
public class Graphe {

    private Set<Noeud> noeuds;
    
    /* 
     * Pour un noeud contient l'ensemble des segments qui partent de celui-ci
     */
    protected Map<Noeud, ArrayList<Segment>> liensEntreNoeuds;

    /** {@inheritDoc} */
    @Override
    public String toString() {
        String s = super.toString() + "\n";

        for (Noeud n : noeuds) {
            s += "|| " + n.toString() + '\n';
        }
        s += "\\/";

        return s;
    }

    /**
     * <p>Constructor for Graphe.</p>
     */
    public Graphe() {
        this.noeuds = new LinkedHashSet<>();
    }

    // Constructeur à partir d'un ensemble de nodes
    /**
     * <p>Constructor for Graphe.</p>
     *
     * @param nodes a {@link java.util.Set} object
     */
    public Graphe(Set<Noeud> nodes) {
        this.noeuds = nodes;
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
     * <p>ajouterNoeud.</p>
     *
     * @param nodeA a {@link com.hexa17.pldagile.model.algo.Noeud} object
     */
    public void ajouterNoeud(Noeud nodeA) {
        noeuds.add(nodeA);
    }

    /**
     * <p>obtenirNoeuds.</p>
     *
     * @return a {@link java.util.Set} object
     */
    public Set<Noeud> obtenirNoeuds() {
        return noeuds;
    }

    /**
     * <p>modifierNoeuds.</p>
     *
     * @param nodes a {@link java.util.Set} object
     */
    public void modifierNoeuds(Set<Noeud> nodes) {
        this.noeuds = nodes;
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
