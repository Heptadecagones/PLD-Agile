package model.algo.Dijkstra;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Yannick
 */

public class Node {

    private String nom;
    private List<Node> cheminPlusCourt;
    private double distance;
    private Map<Node, Double> nodeAdjacentes;

    public Node(String nom) {
        this.nom = nom;
        this.cheminPlusCourt = new LinkedList<>();
        this.distance = Integer.MAX_VALUE;
        this.nodeAdjacentes = new HashMap<>();
    }

    // Constructeur par copie
    public Node(Node n) {
        this.nom = n.obtenirNom();
        this.cheminPlusCourt = n.obtenirCheminPlusCourt();
        this.distance = n.obtenirDistance();
        this.nodeAdjacentes = n.obtenirNodeAdjacentes();
    }

    public void ajouterDestination(Node destination, double distance) {
        this.nodeAdjacentes.put(destination, distance);
    }

    public String obtenirNom() {
        return nom;
    }

    public List<Node> obtenirCheminPlusCourt() {
        return cheminPlusCourt;
    }

    public void modifierCheminPlusCourt(List<Node> cheminPlusCourt) {
        this.cheminPlusCourt = cheminPlusCourt;
    }

    public double obtenirDistance() {
        return distance;
    }

    public void modifierDistance(double distance) {
        this.distance = distance;
    }

    public Map<Node, Double> obtenirNodeAdjacentes() {
        return nodeAdjacentes;
    }

    public void modifierNodeAdjacentes(Map<Node, Double> nodeAdjacentes) {
        this.nodeAdjacentes = nodeAdjacentes;
    }

}
