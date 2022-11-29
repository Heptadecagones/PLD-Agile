package model.algo.Dijkstra;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import model.Intersection;
import model.Plan;
import model.Segment;

public class Graph {

    private Set<Node> nodes;

    public Graph() {
        this.nodes = new HashSet<>();
    }

    // Constructeur à partir d'un ensemble de nodes
    public Graph(Set<Node> nodes) {
        this.nodes = nodes;
    }

    public Graph(Plan plan) {

        ArrayList<Intersection> listeIntersection = plan.obtenirListeIntersection();
        ArrayList<Segment> listeSegment = plan.obtenirListeSegment();

        this.nodes = new HashSet<>();

        for (Intersection intersection : listeIntersection) {
            Node tempNode = new Node(intersection.obtenirId());
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

    public void ajouterNode(Node nodeA) {
        nodes.add(nodeA);
    }

    public Set<Node> obtenirNodes() {
        return nodes;
    }

    public void modifierNodes(Set<Node> nodes) {
        this.nodes = nodes;
    }
}
