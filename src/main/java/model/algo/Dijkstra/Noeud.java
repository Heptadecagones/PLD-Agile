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

    protected String id;
    private List<Noeud> cheminPlusCourt;  // Mais c'est quoi au juste ?
    protected double poids;
    private Map<Noeud, Double> noeudsAdjacents;

    

    public Noeud(String id) {
        this.id = id;
        this.cheminPlusCourt = new LinkedList<>();
        this.poids = Integer.MAX_VALUE;
        this.noeudsAdjacents = new HashMap<>();
    }

    // Constructeur par copie
    public Noeud(Noeud n) {
        this.id = n.obtenirId();
        this.cheminPlusCourt = n.obtenirCheminPlusCourt();
        this.poids = n.obtenirPoids();
        this.noeudsAdjacents = n.obtenirNoeudsAdjacents();
    }

    public void ajouterDestination(Noeud destination, double poids) {
        this.noeudsAdjacents.put(destination, poids);
    }

    public String obtenirId() {
        return id;
    }

    public List<Noeud> obtenirCheminPlusCourt() {
        return cheminPlusCourt;
    }

    public void modifierCheminPlusCourt(List<Noeud> cheminPlusCourt) {
        this.cheminPlusCourt = cheminPlusCourt;
    }

    public double obtenirPoids() {
        return poids;
    }

    public void modifierPoids(double poids) {
        this.poids = poids;
    }

    public Map<Noeud, Double> obtenirNoeudsAdjacents() {
        return noeudsAdjacents;
    }

    public void modifierNoeudAdjacentes(Map<Noeud, Double> noeudsAdjacents) {
        this.noeudsAdjacents = noeudsAdjacents;
    }

    @Override
    public String toString() {
        String s = "Noeud " + id; 
        return s;
    }
}
