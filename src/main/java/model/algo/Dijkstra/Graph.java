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

public class Graph {

    private Set<Node> nodes;

    public Graph() {
        this.nodes = new LinkedHashSet<>();
    }

    // Constructeur à partir d'un ensemble de nodes
    public Graph(Set<Node> nodes) {
        this.nodes = nodes;
    }

    // Constructeur à partir d'un plan
    public Graph(Plan plan) {

        ArrayList<Intersection> listeIntersection = plan.obtenirListeIntersection();
        ArrayList<Segment> listeSegment = plan.obtenirListeSegment();

        this.nodes = new LinkedHashSet<>();

        for (Intersection intersection : listeIntersection) {
            Node tempNode = new Node(intersection.obtenirId(), 4);
            nodes.add(tempNode);
        }

        Node origine = null;
        Node destination = null;
        int c = 0;

        for (Segment segment : listeSegment) {
            c = 0;
            for (Node node : nodes) {
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
    public void ajouterNode(Node nodeA) {
        nodes.add(nodeA);
    }

    /**
     * @return
     */
    public Set<Node> obtenirNodes() {
        return nodes;
    }

    /**
     * @param nodes
     */
    public void modifierNodes(Set<Node> nodes) {
        this.nodes = nodes;
    }
}
