package com.hexa17.pldagile;

import com.rpieniazek.tabu.Matrix;
import com.rpieniazek.tabu.TabuSearch;

public class App {

    public static void main(String[] args) {

        // int[][] m = new int[][]{{0, 1, 3, 4, 5},
        // {1, 0, 1, 4, 8},
        // {3, 1, 0, 5, 1},
        // {4, 4, 5, 0, 2},
        // {5, 8, 1, 2, 0}};

        int size = 10;
        while (size < 2000) {
            Matrix matrix = new Matrix(size);
            TabuSearch tabuSearch = new TabuSearch(matrix);
            Long start = System.currentTimeMillis();
            tabuSearch.invoke();
            Long stop = System.currentTimeMillis() - start;
            System.out.println(String.format("Size: %d\t Time in ms: %d ", size, stop));
            size += 10;
        }
    }
}
