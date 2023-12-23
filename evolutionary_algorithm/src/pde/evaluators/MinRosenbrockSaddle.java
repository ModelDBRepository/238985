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
public class MinRosenbrockSaddle implements DifferentialEvaluator {

    private final int maxGens; //max no. of generations
    
    public MinRosenbrockSaddle (int maxGenes) {
        this.maxGens = maxGenes;
    }
    
    @Override
    public boolean continueOptimisation(int iterNo, double[][] vectors) {
        return maxGens != iterNo;
    }


    @Override
    public double f(double[] args) {
        double x = args[1], y = args[2];
        return 100*Math.pow((y - x*x), 2)+Math.pow((1 - x), 2);
    }

    

    
}
