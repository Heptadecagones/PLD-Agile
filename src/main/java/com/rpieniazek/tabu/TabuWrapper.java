package com.rpieniazek.tabu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import model.algo.Dijkstra.Graphe;
import model.algo.Dijkstra.Noeud;

public class TabuWrapper {

    private final Matrix matrice; // Matrice d'adjacence du graphe
    private Graphe dijkstraResult;

    public TabuWrapper(Graphe graph) {
        this.dijkstraResult = graph;
        this.matrice = grapheEnMatrice();
    }

    private Matrix grapheEnMatrice() {
        ArrayList<Noeud> grapheNoeuds = new ArrayList<Noeud>(this.dijkstraResult.obtenirNoeuds());
        int taille = grapheNoeuds.size();
        double[][] preMatrice = new double[taille][taille];

        // Construire la matrice d'adjacence dont Tabu a besoin
        for (int i = 0; i < taille; i++) {
            Noeud n = grapheNoeuds.get(i);
            for (Node n : n.obtenirNoeudAdjacentes()) {
                
            }
        }
        Matrix m = new Matrix(preMatrice);
        return null;
    }

}
