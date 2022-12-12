package com.hexa17.pldagile.model.algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.hexa17.pldagile.model.Intersection;
import com.hexa17.pldagile.model.Segment;

/**
 *
 * @author Yannick
 */

public class Graphe {

    private Set<Noeud> noeuds;
    
    /* 
     * Pour un noeud contient l'ensemble des segments qui partent de celui-ci
     */
    protected Map<Noeud, ArrayList<Segment>> liensEntreNoeuds;

    @Override
    public String toString() {
        String s = super.toString() + "\n";

        for (Noeud n : noeuds) {
            s += "|| " + n.toString() + '\n';
        }
        s += "\\/";

        return s;
    }

    public Graphe() {
        this.noeuds = new LinkedHashSet<>();
    }

    // Constructeur à partir d'un ensemble de nodes
    public Graphe(Set<Noeud> nodes) {
        this.noeuds = nodes;
    }

    

    public Graphe(Map<Noeud, ArrayList<Segment>> liensEntreNoeuds) {
        this.liensEntreNoeuds = liensEntreNoeuds;
    }

    /**
     * @param nodeA
     */
    public void ajouterNoeud(Noeud nodeA) {
        noeuds.add(nodeA);
    }

    /**
     * @return
     */
    public Set<Noeud> obtenirNoeuds() {
        return noeuds;
    }

    /**
     * @param nodes
     */
    public void modifierNoeuds(Set<Noeud> nodes) {
        this.noeuds = nodes;
    }

    public Map<Noeud, ArrayList<Segment>> obtenirLiensEntreNoeuds() {
        return liensEntreNoeuds;
    }

}
