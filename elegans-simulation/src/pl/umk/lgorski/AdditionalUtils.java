/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.umk.lgorski;

/**
 *
 * @author Łukasz Górski <lgorski@mat.umk.pl>
 */
public class AdditionalUtils {

    public static double[] nthPairCombination (int[] elems, long nth, int sizeOfCombination) {
        double[] ret = new double[sizeOfCombination];
        
        for (int j = 0; j < ret.length; j++) {
                ret[ret.length - j - 1] = (nth & (1L << j)) != 0 ? elems[1] : elems[0];
        }
        
        return ret;
    }
    
    public static int[] nthPairCombinationInt (int[] elems, long nth, int sizeOfCombination) {
        int[] ret = new int[sizeOfCombination];
        
        for (int j = 0; j < ret.length; j++) {
                ret[ret.length - j - 1] = (nth & (1L << j)) != 0 ? elems[1] : elems[0];
        }
        
        return ret;
    }
    
    public static double[][] pairCombinations(int[] elems, int size) {

        //oh, well, can't think of better solution on generating all combinations of elems
        //of size suma
        int sz = 1 << size;

        double[][] ret = new double[sz][size];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = nthPairCombination(elems, i, size);
        }
        return ret;
    }

    public static double[][] matrixMultiply(double[][] m1, double[][] m2) {

        int p = m1.length, q = m1[0].length, r = m2[0].length;
        double[][] res = new double[p][r];

        for (int i = 0; i < p; i++) {
            for (int j = 0; j < r; j++) {
                for (int k = 0; k < q; k++) {
                    res[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return res;
    }
    public static double[][] matrixMultiply(double[][] m1, int[][] m2) {

        int p = m1.length, q = m1[0].length, r = m2[0].length;
        double[][] res = new double[p][r];

        for (int i = 0; i < p; i++) {
            for (int j = 0; j < r; j++) {
                for (int k = 0; k < q; k++) {
                    res[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return res;
    }
    public static double[][] matrixMultiply(double[][] m, double a) {
        double[][] mm = new double[m.length][m[0].length];
        for (int i = 0; i < mm.length; i++) {
            for (int j = 0; j < mm[0].length; j++) {
                mm[i][j] = a * m[i][j];
            }
        }
        return mm;
    }
    
    public static double[][] matrixRowByRowMultiply (double[][] m1, int[][] m2) {
        double[][] ret = new double[m1.length][m1[0].length];
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m1.length; j++) {
                ret[i][j] = m1[i][j]*m2[i][j];
            }
        }
        return ret;
    }
}
