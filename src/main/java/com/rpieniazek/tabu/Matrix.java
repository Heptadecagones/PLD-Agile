package com.rpieniazek.tabu;

import java.util.Random;

public class Matrix {

    private int[][] matrix;

    private int edgeCount;

    /** Constructeur à valeurs aléatoires
     * @param taille
     */
    public Matrix(int size) {
        edgeCount = size;
        matrix = new int[size][size];
        generateMatrix(size);
    }

    /** Constructeur par copie
     * @param matrice
     */
    public Matrix(int[][] matrix) {
        edgeCount = matrix.length;
        this.matrix = matrix;
    }

    public int getEdgeCount() {
        return edgeCount;
    }

    public int getWeight(int from, int to) {
        return matrix[from][to];
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public int getSize() {
        return edgeCount;
    }

    /** Calcule la distance totale d'une solution
     * @param solution
     * @return la distance entière
     */
    public int calculateDistance(int solution[]) {
        int cost = 0;
        for (int i = 0; i < solution.length - 1; i++) {
            cost += matrix[solution[i]][solution[i + 1]];
        }
        return cost;
    }

    /** Génère une matrice aléatoire
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

}
