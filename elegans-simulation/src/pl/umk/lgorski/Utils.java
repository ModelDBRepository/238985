/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.umk.lgorski;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.DoubleStream;

/**
 *
 * @author Łukasz
 */
public class Utils {

    @FunctionalInterface
    public interface Distance {

        public double distance(double[] vec1, double[] vec2);
    }

    private static Distance distanceFunction = Utils::EuclideanDistance;

    static void setDistanceFunction(Distance distanceFunction) {
        Utils.distanceFunction = distanceFunction;
    }
    

    public static double eFun(double pW, double theta) {
        double pwt = pW / theta;
        return Math.exp(pwt) / (1 + Math.exp(pwt));
    }

    public static double EuclideanDistance(double[] vec1, double[] vec2) {
        double sum = 0;
        for (int i = 0; i < vec1.length; i++) {
            sum += (vec1[i] - vec2[i]) * (vec1[i] - vec2[i]);
        }
        return Math.sqrt(sum);
    }

    public static double MahalanobisDistance(double[] vec1, double[] vec2) {
        final double[] errors = {0.0331723, 0.0215089, 0.0494927, 0.071064, 0.131853, 0.0455805,
            0.0640241, 0.0970122, 0.110012, 0.0521812, 0.103968, 0.0367469,
            0.169268, 0.0941945, 0.128526, 0.136071, 0.0901046, 0.0583286};
        double sum = 0;
        for (int i = 0; i < vec1.length; i++) {
            sum += (vec1[i] - vec2[i]) * (vec1[i] - vec2[i]) / errors[i];
        }
        return Math.sqrt(sum);
    }

    public static double compTfTb(double[] pW, double eta) {
        double[] temp = new double[pW.length];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = eFun(pW[i], eta);
        }
        return distanceFunction.distance(temp, ExperimentalData.expTfTbunit);
    }

    static class IntRefHolder {

        public int value;
    }

    public static int[][] prog(double zd, double zg, double[][] a, Optional<IntRefHolder> refN) {
        int[][] w1 = new int[a.length][a[0].length];
        refN.ifPresent(val -> val.value = 0);
        for (int i = 0; i < w1.length; i++) {
            for (int j = 0; j < w1[0].length; j++) {
                if (a[i][j] > zd && a[i][j] <= zg) {
                    w1[i][j] = 1;
                    refN.ifPresent(val -> val.value++);
                } else {
                    w1[i][j] = 0;
                }
            }
        }
        return w1;
    }

    public static int[][] podmiana(long k, int suma, int[][] pozEpsilon) {

        k = k - 1;
        int[][] zerosE = new int[9][9];

        int[] combinations = AdditionalUtils.nthPairCombinationInt(new int[]{0, 1}, k, suma);

        for (int i = 0; i < pozEpsilon.length; i++) {
            int i1 = pozEpsilon[i][0];
            int i2 = pozEpsilon[i][1];

            zerosE[i1][i2] = combinations[i];
        }

        for (int i = 0; i < zerosE.length; i++) {
            zerosE[i][6] = zerosE[i][7] = 1;
        }

        return zerosE;
    }

    public static int[][] position(int[][] maska, int p) {
        ArrayList<int[]> arr = new ArrayList<>();
        for (int i = 0; i < maska.length; i++) {
            for (int j = 0; j < maska[i].length; j++) {
                if (maska[i][j] == p) {
                    arr.add(new int[]{i, j});
                }
            }
        }
        return arr.toArray(new int[][]{});
    }

    public static final double[] TYPICAL_GAMMA = prepareGammaOrTheta(0.03, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01);
    public static final double[] TYPICAL_THETA = prepareGammaOrTheta(-90, 80, 80, 80, 80, 80, 80, 80, 80);

    public static double[] prepareGammaOrTheta(double ash, double ava, double avb, double avd, double ave, double dva, double pvc, double eb, double ef) {
        return DoubleStream.of(ash, ava, avb, avd, ave, dva, pvc, eb, ef).toArray();
    }
}
