package pde.evaluators;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Łukasz Górski <lgorski@mat.umk.pl>
 */
public class MinRosenbrockSaddleParallelEvaluator implements DifferentialEvaluator{

    private final int maxGens; //max no. of generations
    private final double precision; //target precision

    public double getPrecision() {
        return precision;
    }
    
    public double calcResidual (double x, double y) {
        return (1-x)*(1-x)+(1-y)*(1-y);
    }
    
    public MinRosenbrockSaddleParallelEvaluator (int maxGenes, double precision) {
        this.maxGens = maxGenes;
        this.precision = precision;
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
        if ( Math.abs(cost1 - cost2) < 1e-6)
            return 0;
        if (cost1 < cost2)
            return -1;
        return 1;
    }

    @Override
    public double f(double[] args) {
        double x = args[0], y = args[1];
        return 100*Math.pow((x*x - y), 2)+Math.pow((1 - x), 2);
    }

    

    
}
