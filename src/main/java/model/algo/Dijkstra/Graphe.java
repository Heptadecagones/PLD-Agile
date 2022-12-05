package model.algo.Dijkstra;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import model.Intersection;
import model.Plan;
import model.Segment;

/**
 *
 * @author Yannick
 */

public class Graphe {

    private Set<Noeud> noeuds;

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

    public Graphe(Plan plan) {

        ArrayList<Intersection> listeIntersection = plan.obtenirListeIntersection();
        ArrayList<Segment> listeSegment = plan.obtenirListeSegment();

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
                if (node.obtenirNom().equals(segment.obtenirOrigine().obtenirId())) {
                    origine = node;
                    c++;
                }

                if (node.obtenirNom().equals(segment.obtenirDestination().obtenirId())) {
                    destination = node;
                    c++;
                }

                if (c > 1)
                    break;
            }

            if (origine != null && destination != null)
                origine.ajouterDestination(destination, segment.obtenirLongueur());
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
}
