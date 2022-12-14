package com.hexa17.pldagile.model.algo.tabu;

import java.util.Random;

/**
 * <p>Matrix class.</p>
 *
 * @author omi
 * @version $Id: $Id
 */
public class Matrice {

    private double[][] matrice;

    private int nbAretes;

    /**
     * Constructeur à valeurs aléatoires
     *
     * @param taille, un int
     */
    public Matrice(int taille) {
        nbAretes = taille;
        matrice = new double[taille][taille];
        generateMatrix(taille);
    }

    /**
     * Constructeur par copie
     *
     * @param matrix un tableau 2D de {@link double} 
     */
    public Matrice(double[][] matrix) {
        nbAretes = matrix.length;
        this.matrice = matrix;
    }

    /**
     * <p>Getter pour le champ <code>nbAretes</code>.</p>
     *
     * @return un int
     */
    public int obtenirNbAretes() {
        return nbAretes;
    }

    /**
     * <p>Getter pour le poids d'un déplacement.</p>
     *
     * @param from a int
     * @param to a int
     * @return a double
     */
    public double getWeight(int from, int to) {
        return matrice[from][to];
    }

    /**
     * <p>Getter pour le champ <code>matrice</code>.</p>
     *
     * @return une liste de {@link double} 
     */
    public double[][] getMatrice() {
        return matrice;
    }

    /**
     * Calcule la distance totale d'une solution
     *
     * @param solution an array of {@link int} objects
     * @return la distance entière
     */
    public int calculerDistance(int solution[]) {
        int cout = 0;
        for (int i = 0; i < solution.length - 1; i++) {
            cout += matrice[solution[i]][solution[i + 1]];
        }
        return cout;
    }

    /**
     * Génère une matrice aléatoire
     *
     * @param taille, un int
     */
    private void generateMatrix(int taille) {
        Random alea = new Random();
        for (int ligne = 0; ligne < taille; ligne++) {
            for (int col = 0; col < taille; col++) {
                if (ligne != col) {
                    int valeur = alea.nextInt(100) + 1;
                    matrice[ligne][col] = valeur;
                    matrice[col][ligne] = valeur;
                }
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        String s = "";

        for (int i = 0; i < nbAretes; i++) {
            for (int j = 0; j < nbAretes; j++) {
                s += matrice[i][j] + " ";
            }
            s += '\n';
        }
        return s;
    }
}
