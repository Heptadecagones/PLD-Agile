package com.rpieniazek.tabu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import model.algo.Dijkstra.Graph;
import model.algo.Dijkstra.Node;

public class TabuWrapper {

    private final Matrix matrice; // Matrice d'adjacence du graphe
    private Graph dijkstraResult;

    public TabuWrapper(Graph graph) {
        this.dijkstraResult = graph;
        this.matrice = grapheEnMatrice();
    }

    private Matrix grapheEnMatrice() {
        ArrayList<Node> graphNodes = new ArrayList<Node>(this.dijkstraResult.obtenirNodes());
        int taille = graphNodes.size();
        double[][] preMatrice = new double[taille][taille];
        // Trier les livraisons
        Collections.sort(graphNodes, new Comparator<Node>() {
            public int compare(Node n1, Node n2) {
                return n1.obtenirHoraireLivraison() - n2.obtenirHoraireLivraison();
            }
        });

        for (Node n : graphNodes) {
            System.out.println("Node " + n.obtenirNom() + ": " + n.obtenirHoraireLivraison());
        }

        Matrix m = new Matrix(preMatrice);
        return null;
    }

}
