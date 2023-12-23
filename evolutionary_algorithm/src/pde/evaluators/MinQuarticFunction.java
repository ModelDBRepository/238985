package pde.evaluators;

import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Łukasz Górski <lgorski@mat.umk.pl>
 */
public class MinQuarticFunction implements DifferentialEvaluator {

    private final int maxGens; //max no. of generations
    private final Random random = new Random();
    double rands[] = new double[30];

    public static double calcResidual(double args[]) {
        double sum = 0;
        for (double arg : args) {
            sum += arg * arg;
        }
        return sum;
    }

    public MinQuarticFunction(int maxGenes) {
        this.maxGens = maxGenes;
        for (int i = 0; i < rands.length; i++) {
            rands[i] = random.nextDouble();
        }
    }

    @Override
    public boolean continueOptimisation(int iterNo, double[][] vectors) {
        return maxGens != iterNo;
    }

    @Override
    public int evaluateVectors(double[] first, double[] second) {
        double cost1, cost2;

        cost1 = f(first);
        cost2 = f(second);
        if (Math.abs(cost1 - cost2) < 1e-6) {
            return 0;
        }
        if (cost1 < cost2) {
            return -1;
        }
        return 1;
    }

    @Override
    public double f(double args[]) {
        double sum = 0;
        for (int j = 1; j <= 30; j++) {
            sum += (j * Math.pow(args[j - 1], 4) + rands[j - 1]);
        }
        return sum;
    }
}
