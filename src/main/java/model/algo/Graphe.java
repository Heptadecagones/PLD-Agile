package model.algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import model.Intersection;
import model.Segment;

/**
 *
 * @author Yannick
 */

public class Graphe {

    private Set<Noeud> noeuds;

    // Map<origine, Map<destination, seg>>
    /*
     * Contient l'ensemble des noeuds adjacents (destination) d'un noeud
     * spécifique (origine) et leur lien (seg)
     */
    private Map<Noeud, ArrayList<Segment>> liensEntreNoeuds;

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

    public Graphe(ArrayList<Intersection> listeIntersection, ArrayList<Segment> listeSegment) {

        this.noeuds = new LinkedHashSet<>();

        // Creation de liensEntreNoeuds
        liensEntreNoeuds = new HashMap<Noeud, ArrayList<Segment>>();

        Noeud tempOrigine;

        for (Segment seg : listeSegment) {

            tempOrigine = seg.obtenirOrigine();

            if (!liensEntreNoeuds.containsKey(tempOrigine)) {
                liensEntreNoeuds.put(tempOrigine, new ArrayList<Segment>());
            }
            liensEntreNoeuds.get(tempOrigine).add(seg);
        }
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
