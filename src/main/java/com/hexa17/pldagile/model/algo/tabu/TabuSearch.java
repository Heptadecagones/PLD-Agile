package com.hexa17.pldagile.model.algo.tabu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.hexa17.pldagile.model.Livraison;

/**
 * Créé par Rafal le 2015-12-02.
 *
 * @see <a href=https://github.com/w4-pwr/TSP-Tabu-search>Source originale</a>
 * @author omi
 * @version $Id: $Id
 */
public class TabuSearch {

    private TabuListe listeTabu; // Liste tabou
    private Matrice matrix; // Matrice d'adjacence

    int[] soluceActuelle; // Solution actuelle
    int numberOfIterations; // Nombre maximal d'itérations
    int problemSize; // Taille du problème
    int horaireMinimale;

    private Map<Livraison, Integer> places;

    private int[] meilleureSoluce;
    private int meilleurCout;

    /**
     * <p>
     * Constructeur de TabuSearch.
     * </p>
     *
     * @param livraisons a {@link java.util.ArrayList} object
     */
    public TabuSearch(ArrayList<Livraison> livraisons) {

        int minLiv = 24;
        for (Livraison liv : livraisons) {
            if (liv.obtenirHoraireLivraison() < minLiv)
                minLiv = liv.obtenirHoraireLivraison();
        }

        this.horaireMinimale = minLiv;
        this.places = new HashMap<>();
        this.matrix = livraisonsVersMatrice(livraisons);
        this.problemSize = matrix.getNbAretes();
        this.numberOfIterations = problemSize * 10; // ?
        this.listeTabu = new TabuListe(problemSize);

        initSolutionActuelle();
        initMeilleureSolution();
    }

    /**
     * <p>
     * Transforme une liste de livraisons en matrice d'adjacence.
     * </p>
     *
     * @param livraisons a {@link java.util.ArrayList} object
     * @return a {@link com.hexa17.pldagile.model.algo.tabu.Matrice} object
     */
    private Matrice livraisonsVersMatrice(ArrayList<Livraison> livraisons) {
        int taille = livraisons.size();
        double[][] preMatrice = new double[taille][taille];

        int i = 0;

        // Pour chaque noeud dans le graphe
        for (Livraison oriLiv : livraisons) {

            // On regarde ses voisins et on ajoute la paire orig -> dest à la matrice
            for (Livraison destLiv : livraisons) {

                if (destLiv == oriLiv)
                    continue;

                int origID = -1, destID = -1;

                // Si on est déjà passés par cette node, on connaît son index
                if (places.containsKey(oriLiv)) {
                    origID = places.get(oriLiv);
                } else { // Sinon on l'ajoute à la liste connue
                    places.put(oriLiv, i);
                    origID = i++;
                }

                // De même pour la destination
                if (places.containsKey(destLiv)) {
                    destID = places.get(destLiv);
                } else {
                    places.put(destLiv, i);
                    destID = i++;
                }

                // Test de santé mentale
                assert (origID != -1 && destID != -1);

                // WARN: Edge case
                int origTime = oriLiv.obtenirHoraireLivraison();
                int destTime = destLiv.obtenirHoraireLivraison();

                // TODO bouger le code qui supprime des liens dans grapheTSP
                if ((origTime == 99 && destTime > horaireMinimale) || (origTime != 99 && origTime > destTime)) {
                    preMatrice[origID][destID] = Integer.MAX_VALUE;
                } else {
                    preMatrice[origID][destID] = oriLiv.obtenirLieu().obtenirArborescence().get(destLiv.obtenirLieu())
                            .obtenirPoids();
                }
            }
        }
        Matrice m = new Matrice(preMatrice);
        return m;
    }

    /**
     * <p>
     * Initialise la meilleure solution.
     * </p>
     */
    private void initMeilleureSolution() {
        meilleureSoluce = new int[problemSize + 1];
        System.arraycopy(soluceActuelle, 0, meilleureSoluce, 0, meilleureSoluce.length);
        meilleurCout = matrix.calculerDistance(meilleureSoluce);
    }

    /**
     * <p>
     * Initialise la solution actuelle.
     * </p>
     */
    private void initSolutionActuelle() {
        soluceActuelle = new int[problemSize + 1];
        for (int i = 0; i < problemSize; i++)
            soluceActuelle[i] = i;
        soluceActuelle[problemSize] = 0;
    }

    /**
     * <p>
     * Coeur de l'algorithme Tabu, qui calcule un ordre de passage optimisé.
     * </p>
     *
     * @return an array of {@link int} objects
     */
    public int[] invoquer() {

        for (int i = 0; i < numberOfIterations; i++) {
            int ville1 = 0;
            int ville2 = 0;

            for (int j = 1; j < soluceActuelle.length - 1; j++) {
                for (int k = 2; k < soluceActuelle.length - 1; k++) {
                    if (j != k) {
                        echanger(j, k);
                        int coutActuel = matrix.calculerDistance(soluceActuelle);
                        if ((coutActuel < meilleurCout) && listeTabu.tabuListe[j][k] == 0) {
                            ville1 = j;
                            ville2 = k;
                            System.arraycopy(soluceActuelle, 0, meilleureSoluce, 0, meilleureSoluce.length);
                            meilleurCout = coutActuel;
                        }
                    }
                }
            }

            if (ville1 != 0) {
                listeTabu.decrementerTabu();
                listeTabu.tabuDeplacer(ville1, ville2);
            }
        }

        System.out.println("[TABU] Meilleure solution: " + meilleurCout);

        return meilleureSoluce;
    }

    /**
     * <p>
     * Echange deux ints.
     * </p>
     *
     * @param i a int
     * @param k a int
     */
    private void echanger(int i, int k) {
        int temp = soluceActuelle[i];
        soluceActuelle[i] = soluceActuelle[k];
        soluceActuelle[k] = temp;
    }

    /**
     * <p>
     * Inverse la clé et la valeur d'un objet Map.
     * </p>
     *
     * @param map a {@link java.util.Map} object
     * @param <V> a V class
     * @param <K> a K class
     * @return a {@link java.util.Map} object
     */
    public static <V, K> Map<V, K> inverser(Map<K, V> map) {
        return map.entrySet()
                .stream()
                .collect(Collectors.toMap(Entry::getValue, Entry::getKey));
    }

    /**
     * <p>
     * Reconstruit des livraisons à partir de l'ordre de passage calculé par
     * Tabu.
     * </p>
     *
     * @return an array of {@link com.hexa17.pldagile.model.Livraison} objects
     */
    public Livraison[] soluceEnLivraisons() {
        int[] soluceEnInt = invoquer();

        // On inverse la Map pour retrouver les noeuds en fonction des entiers
        Map<Integer, Livraison> invPlaces = inverser(places);
        Livraison[] soluce = new Livraison[soluceEnInt.length];
        double heure = horaireMinimale;

        Livraison livraison = invPlaces.get(soluceEnInt[0]);
        livraison.modifierHeureLivraison(heure);
        soluce[0] = livraison;

        for (int i = 1; i < soluceEnInt.length; i++) {
            livraison = invPlaces.get(soluceEnInt[i]);
            if (i != 1 && livraison.obtenirLieu() != soluce[i - 1].obtenirLieu())
                heure += 5.0 / 60.0;

            heure += livraison.obtenirLieu().obtenirArborescence().get(soluce[i - 1].obtenirLieu()).obtenirPoids()
                    / 15000;
            if (heure < livraison.obtenirHoraireLivraison())
                heure = livraison.obtenirHoraireLivraison();
            livraison.modifierHeureLivraison(heure);

            soluce[i] = livraison;
        }

        return soluce;
    }
}
