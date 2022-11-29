package model.algo.Dijkstra;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Node implements Cloneable{

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

    public void ajouterDestination(Node destination, double distance) {
        this.nodeAdjacentes.put(destination, distance);
    }

    @Override
    public Node clone() throws CloneNotSupportedException {   
	    return (Node)super.clone();
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
