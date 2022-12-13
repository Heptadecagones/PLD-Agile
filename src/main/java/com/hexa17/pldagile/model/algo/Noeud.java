package com.hexa17.pldagile.model.algo;

/*import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;*/
import java.util.Map;

/*import ch.qos.logback.core.joran.sanity.Pair;
import model.Segment;*/

/**
 * <p>Noeud class.</p>
 *
 * @author Hugo, Yannick (initial, très fortement modifié depuis)
 * @version $Id: $Id
 */
public class Noeud {

    protected String id;

    /*
     * A chaque Noeud correspond le plus court chemin (ArrayList<Segment>) et
     * le coût pour aller à ce noeud (Double) stockée dans Lien
     */
    protected Map<Noeud, Lien> arborescence;

    /**
     * <p>Constructor for Noeud.</p>
     */
    public Noeud() {

    }

    /**
     * <p>Constructor for Noeud.</p>
     *
     * @param id a {@link java.lang.String} object
     */
    public Noeud(String id) {
        this.id = id;
        arborescence = null;
    }

    // Constructeur par copie
    /**
     * <p>Constructor for Noeud.</p>
     *
     * @param n a {@link com.hexa17.pldagile.model.algo.Noeud} object
     */
    public Noeud(Noeud n) {
        this.id = n.obtenirId();
        arborescence = n.obtenirArborescence(); 
    }

    /**
     * <p>obtenirId.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String obtenirId() {
        return id;
    }

    /**
     * <p>obtenirArborescence.</p>
     *
     * @return a {@link java.util.Map} object
     */
    public Map<Noeud, Lien> obtenirArborescence() {
        return arborescence;
    }

    /**
     * <p>modifierArborescence.</p>
     *
     * @param arbo a {@link java.util.Map} object
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
