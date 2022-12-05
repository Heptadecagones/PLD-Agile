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

    private Set<Noeud> nodes;

    public Graphe() {
        this.nodes = new LinkedHashSet<>();
    }

    // Constructeur à partir d'un ensemble de nodes
    public Graphe(Set<Noeud> nodes) {
        this.nodes = nodes;
    }

    public Graphe(Plan plan) {

        ArrayList<Intersection> listeIntersection = plan.obtenirListeIntersection();
        ArrayList<Segment> listeSegment = plan.obtenirListeSegment();

        this.nodes = new LinkedHashSet<>();

        for (Intersection intersection : listeIntersection) {
            Noeud tempNode = new Noeud(intersection.obtenirId());
            nodes.add(tempNode);
        }

        Noeud origine = null;
        Noeud destination = null;
        int c = 0;

        for (Segment segment : listeSegment) {
            c = 0;
            for (Noeud node : nodes) {
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
    public void ajouterNode(Noeud nodeA) {
        nodes.add(nodeA);
    }

    /**
     * @return
     */
    public Set<Noeud> obtenirNodes() {
        return nodes;
    }

    /**
     * @param nodes
     */
    public void modifierNodes(Set<Noeud> nodes) {
        this.nodes = nodes;
    }
}
