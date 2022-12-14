package com.hexa17.pldagile.model.algo;

import java.util.Map;

/**
 * <p>Noeud class.</p>
 *
 * @author Hugo, Yannick (initial, très fortement modifié depuis)
 * @version $Id: $Id
 */
public class Noeud {

    protected String id;

    /*
     * un chaque Noeud correspond le plus court chemin (ArrayList<Segment>) et
     * le coût pour aller à ce noeud (Double) stockée dans Lien
     */
    protected Map<Noeud, Lien> arborescence;

    /**
     * <p>Constructeur pour Noeud.</p>
     */
    public Noeud() {

    }

    /**
     * <p>Constructeur pour Noeud.</p>
     *
     * @param id un {@link java.lang.String} objet
     */
    public Noeud(String id) {
        this.id = id;
        arborescence = null;
    }

    // Constructeur par copie
    /**
     * <p>Constructeur pour Noeud.</p>
     *
     * @param n un {@link com.hexa17.pldagile.model.algo.Noeud} objet
     */
    public Noeud(Noeud n) {
        this.id = n.obtenirId();
        arborescence = n.obtenirArborescence(); 
    }

    /**
     * <p>Accesseur Id.</p>
     *
     * @return un {@link java.lang.String} objet
     */
    public String obtenirId() {
        return id;
    }

    /**
     * <p>Accesseur Arborescence.</p>
     *
     * @return un {@link java.util.Map} objet
     */
    public Map<Noeud, Lien> obtenirArborescence() {
        return arborescence;
    }

    /**
     * <p>Modificateur Arborescence.</p>
     *
     * @param arbo un {@link java.util.Map} objet
     */
    public void modifierArborescence(Map<Noeud, Lien> arbo) {
        this.arborescence = arbo;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        String s = "Noeud " + id; 
        return s;
    }
}