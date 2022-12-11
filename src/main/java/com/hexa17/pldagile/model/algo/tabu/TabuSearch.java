package com.hexa17.pldagile.model.algo.tabu;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.hexa17.pldagile.model.algo.Graphe;
import com.hexa17.pldagile.model.algo.Lien;
import com.hexa17.pldagile.model.algo.Noeud;

/**
 * Created by Rafal on 2015-12-02.
 */
public class TabuSearch {

    private TabuList tabuList; // Liste tabou
    private Matrix matrix; // Matrice d'adjacence

    int[] currSolution; // Solution actuelle
    int numberOfIterations; // Nombre maximal d'itérations
    int problemSize; // Taille du problème
    double horaireMinimale;

    private Map<Noeud, Integer> places; // TODO Meilleur nom

    private int[] bestSolution;
    private int bestCost;

    public TabuSearch(Graphe graphe, int minLiv) {
        this.places = new HashMap<>();
        this.horaireMinimale = minLiv;
        this.matrix = GrapheVersMatrice(graphe);
        this.problemSize = matrix.getEdgeCount();
        this.numberOfIterations = problemSize * 10; // ?
        this.tabuList = new TabuList(problemSize);

        setupCurrentSolution();
        setupBestSolution();
    }

    // @author Thibaut
    private Matrix GrapheVersMatrice(Graphe graphe) {
        Set<Noeud> noeuds = graphe.obtenirNoeuds();
        int taille = noeuds.size();
        double[][] preMatrice = new double[taille][taille];

        int i = 0;

        // Pour chaque noeud dans le graphe
        for (Noeud orig : noeuds) {

            // On regarde ses voisins et on ajoute la paire orig -> dest à la matrice
            for (Entry<Noeud, Lien> dest : orig.obtenirArborescence().entrySet()) {
                int origID = -1, destID = -1;

                // Si on est déjà passés par cette node, on connaît son index
                if (places.containsKey(orig)) {
                    origID = places.get(orig);
                } else { // Sinon on l'ajoute à la liste connue
                    places.put(orig, i);
                    origID = i++;
                }

                // De même pour la destination
                if (places.containsKey(dest.getKey())) {
                    destID = places.get(dest.getKey());
                } else {
                    places.put(dest.getKey(), i);
                    destID = i++;
                }

                // Test de santé mentale
                assert (origID != -1 && destID != -1);

                // WARN: Edge case
                int origTime = orig.obtenirHoraireLivraison();
                int destTime = dest.getKey().obtenirHoraireLivraison();

                //TODO bouger le code qui supprime des liens dans grapheTSP
                if (origTime == 99 && destTime > horaireMinimale || origTime >= destTime) {
                    preMatrice[origID][destID] = Integer.MAX_VALUE;
                } else {
                    preMatrice[origID][destID] = dest.getValue().obtenirPoids();
                }
            }
        }

        Matrix m = new Matrix(preMatrice);
        return m;
    }

    private void setupBestSolution() {
        bestSolution = new int[problemSize + 1];
        System.arraycopy(currSolution, 0, bestSolution, 0, bestSolution.length);
        bestCost = matrix.calculateDistance(bestSolution);
    }

    private void setupCurrentSolution() {
        currSolution = new int[problemSize + 1];
        for (int i = 0; i < problemSize; i++)
            currSolution[i] = i;
        currSolution[problemSize] = 0;
    }

    private void printSolution(int[] solution) {
        for (int i = 0; i < solution.length; i++) {
            System.out.print(solution[i] + " ");
        }
        System.out.println();
    }

    public int[] invoke() {

        for (int i = 0; i < numberOfIterations; i++) {
            int city1 = 0;
            int city2 = 0;

            for (int j = 1; j < currSolution.length - 1; j++) {
                for (int k = 2; k < currSolution.length - 1; k++) {
                    if (j != k) {
                        swap(j, k);
                        int currCost = matrix.calculateDistance(currSolution);
                        if ((currCost < bestCost) && tabuList.tabuList[j][k] == 0) {
                            city1 = j;
                            city2 = k;
                            System.arraycopy(currSolution, 0, bestSolution, 0, bestSolution.length);
                            bestCost = currCost;
                        }
                    }
                }
            }

            if (city1 != 0) {
                tabuList.decrementTabu();
                tabuList.tabuMove(city1, city2);
            }
        }

        System.out.println("Search done! \nBest Solution cost found = " + bestCost +
                "\nBest Solution :");
        printSolution(bestSolution);

        return bestSolution;
    }

    private void swap(int i, int k) {
        int temp = currSolution[i];
        currSolution[i] = currSolution[k];
        currSolution[k] = temp;
    }

    public static <V, K> Map<V, K> inverser(Map<K, V> map) {
        return map.entrySet()
                .stream()
                .collect(Collectors.toMap(Entry::getValue, Entry::getKey));
    }

    public Noeud[] soluceEnNoeuds() {
        int[] soluceEnInt = invoke();

        // On inverse la Map pour retrouver les noeuds en fonction des entiers
        Map<Integer, Noeud> invPlaces = inverser(places);
        Noeud[] soluce = new Noeud[soluceEnInt.length];
        double heure = horaireMinimale;

        Noeud noeud = invPlaces.get(soluceEnInt[0]);
        noeud.modifierHeureLivraison(heure);
        soluce[0] = noeud;

        for (int i = 1; i < soluceEnInt.length; i++) {
            noeud = invPlaces.get(soluceEnInt[i]);

            heure += noeud.obtenirArborescence().get(soluce[i-1]).obtenirPoids()/15000;
            noeud.modifierHeureLivraison(heure);

            soluce[i] = noeud;
        }

        return soluce;
    }
}
