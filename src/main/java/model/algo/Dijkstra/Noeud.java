package model.algo.Dijkstra;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Yannick
 */

public class Noeud {

    private String nom;
    private List<Noeud> cheminPlusCourt;
    private double distance;
    private Map<Noeud, Double> nodeAdjacentes;
    private int horaireLivraison;

    public int obtenirHoraireLivraison() {
        return horaireLivraison;
    }

    public void modifierHoraireLivraison(int horaireLivraison) {
        this.horaireLivraison = horaireLivraison;
    }

    public Noeud(String nom) {
        this.nom = nom;
        this.cheminPlusCourt = new LinkedList<>();
        this.distance = Integer.MAX_VALUE;
        this.nodeAdjacentes = new HashMap<>();
        this.horaireLivraison = 0;
    }

    // Constructeur par copie
    public Noeud(Noeud n) {
        this.nom = n.obtenirNom();
        this.cheminPlusCourt = n.obtenirCheminPlusCourt();
        this.distance = n.obtenirDistance();
        this.nodeAdjacentes = n.obtenirNoeudsAdjacents();
    }

    public void ajouterDestination(Noeud destination, double distance) {
        this.nodeAdjacentes.put(destination, distance);
    }

    public String obtenirNom() {
        return nom;
    }

    public List<Noeud> obtenirCheminPlusCourt() {
        return cheminPlusCourt;
    }

    public void modifierCheminPlusCourt(List<Noeud> cheminPlusCourt) {
        this.cheminPlusCourt = cheminPlusCourt;
    }

    public double obtenirDistance() {
        return distance;
    }

    public void modifierDistance(double distance) {
        this.distance = distance;
    }

    public Map<Noeud, Double> obtenirNoeudsAdjacents() {
        return nodeAdjacentes;
    }

    public void modifierNoeudAdjacentes(Map<Noeud, Double> nodeAdjacentes) {
        this.nodeAdjacentes = nodeAdjacentes;
    }

    @Override
    public String toString() {
        String s = "Noeud " + nom + ": " + horaireLivraison; 
        return s;
    }
}
