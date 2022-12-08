package model.algo.Dijkstra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ch.qos.logback.core.joran.sanity.Pair;
import model.Segment;

/**
 *
 * @author Yannick
 */

public class Noeud {

    protected String id;
    private List<Noeud> cheminPlusCourtDepuisEntrepot;
    protected double poids;
    private Map<Noeud, Double> noeudsAdjacents;
    private int horaireLivraison;
    /*
     * A chaque Noeud correspond le plus court chemin (ArrayList<Segment>) et
     * le coût pour aller à ce noeud (Double) 
     */
    private Map<Noeud, Lien> arborescence;
    

    public Noeud(String id) {
        this.id = id;
        this.cheminPlusCourtDepuisEntrepot = new LinkedList<>();
        this.poids = Double.MAX_VALUE;
        this.noeudsAdjacents = new HashMap<>();
        this.horaireLivraison = 99;
        arborescence = null;
    }

    // Constructeur par copie
    public Noeud(Noeud n) {
        this.id = n.obtenirId();
        this.cheminPlusCourtDepuisEntrepot = n.obtenirCheminPlusCourtDepuisEntrepot();
        this.poids = n.obtenirPoids();
        this.noeudsAdjacents = n.obtenirNoeudsAdjacents();
        this.horaireLivraison = n.obtenirHoraireLivraison();
        arborescence = n.obtenirArborescence();
    }

    public void ajouterDestination(Noeud destination, double poids) {
        this.noeudsAdjacents.put(destination, poids);
    }

    public String obtenirId() {
        return id;
    }

    public List<Noeud> obtenirCheminPlusCourtDepuisEntrepot() {
        return cheminPlusCourtDepuisEntrepot;
    }

    public void modifierCheminPlusCourtDepuisEntrepot(List<Noeud> cheminPlusCourtDepuisEntrepot) {
        this.cheminPlusCourtDepuisEntrepot = cheminPlusCourtDepuisEntrepot;
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

    public int obtenirHoraireLivraison() {
        return this.horaireLivraison;
    }

    public void modifierHoraireLivraison(int horaire) {
        this.horaireLivraison = horaire;
    }

    public boolean arborescenceNonNulle() {
        boolean res = true;
        if(this.arborescence == null) res = false;
        return res;
    }

    public Map<Noeud, Lien> obtenirArborescence() {
        return arborescence;
    }

    public void modifierArborescence(Map<Noeud, Lien> arbo) {
        this.arborescence = arbo;
    }

    @Override
    public String toString() {
        String s = "Noeud " + id + ", horaire de livraison : " + this.horaireLivraison; 
        return s;
    }


}
