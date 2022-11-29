package model.algo.Dijkstra;

import java.util.HashSet;
import java.util.Set;

public class Graph {
    
    private Set<Node> nodes;

    public Graph() {
        this.nodes = new HashSet<>();
    }

    // Constructeur à partir d'un ensemble de nodes
    public Graph(Set<Node> nodes) {
        this.nodes = nodes; 
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
