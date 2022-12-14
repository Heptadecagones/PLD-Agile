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
     * <p>Constructeur pour Graphe.</p>
     */
    public Graphe() {
    }

    /**
     * <p>Constructeur pour Graphe.</p>
     *
     * @param liensEntreNoeuds un {@link java.util.Map} objet
     */
    public Graphe(Map<Noeud, ArrayList<Segment>> liensEntreNoeuds) {
        this.liensEntreNoeuds = liensEntreNoeuds;
    }

    /**
     * <p>Accesseur LiensEntreNoeuds.</p>
     *
     * @return un {@link java.util.Map} objet
     */
    public Map<Noeud, ArrayList<Segment>> obtenirLiensEntreNoeuds() {
        return liensEntreNoeuds;
    }

}
