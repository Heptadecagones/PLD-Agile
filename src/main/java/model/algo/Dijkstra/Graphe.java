package model.algo.Dijkstra;

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
    /* Contient l'ensemble des noeuds adjacents (destination) d'un noeud 
     * spécifique (origine) et leur lien (seg)
    */
    private Map<Noeud, Map<Noeud, Segment>> liensEntreNoeuds;

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

        for (Intersection intersection : listeIntersection) {
            Noeud tempNoeud = new Noeud(intersection.obtenirId());
            noeuds.add(tempNoeud);
        }

        Noeud origine = null;
        Noeud destination = null;
        int c = 0;

        for (Segment segment : listeSegment) {
            c = 0;
            for (Noeud node : noeuds) {
                if (node.obtenirId().equals(segment.obtenirOrigine().obtenirId())) {
                    origine = node;
                    c++;
                }

                if (node.obtenirId().equals(segment.obtenirDestination().obtenirId())) {
                    destination = node;
                    c++;
                }

                if (c > 1)
                    break;
            }

            if (origine != null && destination != null)
                origine.ajouterDestination(destination, segment.obtenirLongueur());
        }


        // Creation de liensEntreNoeuds
        liensEntreNoeuds = new HashMap<Noeud, Map<Noeud, Segment>>();
        Noeud tempOrigine;
        Noeud tempDestination;
        for(Segment seg : listeSegment) {
            
            tempOrigine = seg.obtenirOrigine();
            tempDestination = seg.obtenirDestination();

            if(!liensEntreNoeuds.containsKey(tempOrigine)) {
                liensEntreNoeuds.put(tempOrigine, new HashMap<Noeud, Segment>());
            }
            liensEntreNoeuds.get(tempOrigine).put(tempDestination, seg);
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

    public Map<Noeud, Map<Noeud, Segment>> obtenirLiensEntreNoeuds() {
        return liensEntreNoeuds;
    }

}
