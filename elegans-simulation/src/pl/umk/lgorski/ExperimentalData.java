/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.umk.lgorski;

/**
 *
 * @author Łukasz
 */
public class ExperimentalData {

    public static final int[][] wypalenie
            = {{1, 1, 1, 1, 1, 1, 1}, {0, 1, 1, 1, 1, 1, 1}, {1, 0, 1, 1, 1, 1, 1},
            {1, 1, 0, 1, 1, 1, 1}, {1, 1, 1, 0, 1, 1, 1}, {1, 1, 1, 1, 1, 0, 1},
            {1, 1, 1, 1, 1, 1, 0}, {0, 0, 1, 1, 1, 1, 1}, {0, 1, 0, 1, 1, 1, 1},
            {0, 0, 0, 1, 1, 1, 1}, {1, 0, 0, 1, 1, 1, 1}, {1, 0, 1, 1, 1, 1, 0},
            {1, 1, 0, 1, 1, 1, 0}, {1, 0, 0, 1, 1, 1, 0}, {1, 1, 0, 0, 1, 1, 0},
            {1, 1, 0, 1, 1, 0, 0}, {1, 0, 0, 1, 0, 1, 0}, {1, 1, 1, 1, 1, 0, 0}};

    public static final double[] expTfTbunit = {0.755556, 0.926923, 0.572581, 0.513636, 0.576087, 0.551095,
        0.828283, 0.692029, 0.501222, 0.590551, 0.54902, 0.859244, 0.433333,
        0.664286, 0.585903, 0.612613, 0.606061, 0.617564};

    public static final int[][] abl;
    
    static {
        abl = new int[18][9];
        for (int k = 0; k < 18; k++) {
            System.arraycopy(wypalenie[k], 0, abl[k], 0, 6);
            abl[k][6] = abl[k][7] = 1;
            abl[k][8] = wypalenie[k][6];
        }

    }
}
