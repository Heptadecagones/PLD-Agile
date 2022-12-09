package com.hexa17.pldagile.model.algo;

/*import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;*/
import java.util.Map;

/*import ch.qos.logback.core.joran.sanity.Pair;
import model.Segment;*/

/**
 *
 * @author Hugo, Yannick (initial, très fortement modifié depuis)
 */

public class Noeud {

    protected String id;
    private int horaireLivraison;

    /*
     * A chaque Noeud correspond le plus court chemin (ArrayList<Segment>) et
     * le coût pour aller à ce noeud (Double) stockée dans Lien
     */
    protected Map<Noeud, Lien> arborescence;
    

    public Noeud(String id) {
        this.id = id;
        this.horaireLivraison = 99;
        arborescence = null;
    }

    // Constructeur par copie
    public Noeud(Noeud n) {
        this.id = n.obtenirId();
        arborescence = n.obtenirArborescence(); //WARN: Copie peu profonde
    }

    public String obtenirId() {
        return id;
    }

    public int obtenirHoraireLivraison() {
        return this.horaireLivraison;
    }

    public void modifierHoraireLivraison(int horaire) {
        this.horaireLivraison = horaire;
    }

    public Map<Noeud, Lien> obtenirArborescence() {
        return arborescence;
    }

    public void modifierArborescence(Map<Noeud, Lien> arbo) {
        this.arborescence = arbo;
    }

    @Override
    public String toString() {
        String s = "Noeud " + id + ", horaire de livraison : " + this.horaireLivraison; 
        return s;
    }


}
