package model.algo.Dijkstra;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Node {

    private String nom;
    private List<Node> cheminPlusCourt;
    private Integer distance;
    private Map<Node, Integer> nodeAdjacentes;

    public Node(String nom) {
        this.nom = nom;
        this.cheminPlusCourt = new LinkedList<>();
        this.distance = Integer.MAX_VALUE;
        this.nodeAdjacentes = new HashMap<>();
    }

    public void ajouterDestination(Node destination, int distance) {
        this.nodeAdjacentes.put(destination, distance);
    }

    public String obtenirNom() {
        return nom;
    }

    public void modifierNom(String nom) {
        this.nom = nom;
    }

    public List<Node> obtenirCheminPlusCourt() {
        return cheminPlusCourt;
    }

    public void modifierCheminPlusCourt(List<Node> cheminPlusCourt) {
        this.cheminPlusCourt = cheminPlusCourt;
    }

    public Integer obtenirDistance() {
        return distance;
    }

    public void modifierDistance(Integer distance) {
        this.distance = distance;
    }

    public Map<Node, Integer> obtenirNodeAdjacentes() {
        return nodeAdjacentes;
    }

    public void modifierNodeAdjacentes(Map<Node, Integer> nodeAdjacentes) {
        this.nodeAdjacentes = nodeAdjacentes;
    }

    
}
