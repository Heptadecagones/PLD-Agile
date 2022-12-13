package com.hexa17.pldagile.model.algo.tabu;

import java.util.Random;

/**
 * <p>Matrix class.</p>
 *
 * @author omi
 * @version $Id: $Id
 */
public class Matrix {

    private double[][] matrix;

    private int edgeCount;

    /**
     * Constructeur à valeurs aléatoires
     *
     * @param size a int
     */
    public Matrix(int size) {
        edgeCount = size;
        matrix = new double[size][size];
        generateMatrix(size);
    }

    /**
     * Constructeur par copie
     *
     * @param matrix an array of {@link double} objects
     */
    public Matrix(double[][] matrix) {
        edgeCount = matrix.length;
        this.matrix = matrix;
    }

    /**
     * <p>Getter for the field <code>edgeCount</code>.</p>
     *
     * @return a int
     */
    public int getEdgeCount() {
        return edgeCount;
    }

    /**
     * <p>getWeight.</p>
     *
     * @param from a int
     * @param to a int
     * @return a double
     */
    public double getWeight(int from, int to) {
        return matrix[from][to];
    }

    /**
     * <p>Getter for the field <code>matrix</code>.</p>
     *
     * @return an array of {@link double} objects
     */
    public double[][] getMatrix() {
        return matrix;
    }

    /**
     * <p>getSize.</p>
     *
     * @return a int
     */
    public int getSize() {
        return edgeCount;
    }

    /**
     * Calcule la distance totale d'une solution
     *
     * @param solution an array of {@link int} objects
     * @return la distance entière
     */
    public int calculateDistance(int solution[]) {
        int cost = 0;
        for (int i = 0; i < solution.length - 1; i++) {
            cost += matrix[solution[i]][solution[i + 1]];
        }
        return cost;
    }

    /**
     * Génère une matrice aléatoire
     * 
     * @param taille
     */
    private void generateMatrix(int size) {
        Random random = new Random();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (row != col) {
                    int value = random.nextInt(100) + 1;
                    matrix[row][col] = value;
                    matrix[col][row] = value;
                }
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        String s = "";

        for (int i = 0; i < edgeCount; i++) {
            for (int j = 0; j < edgeCount; j++) {
                s += matrix[i][j] + " ";
            }
            s += '\n';
        }
        return s;
    }
}
