/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pde.parallel.loadbalancing;

import org.pcj.PCJ;
import pde.evaluators.DifferentialEvaluator;

/**
 *
 * @author Łukasz
 */
public class ProportionalWork extends LoadBalancingPolicy {

    private final double compressionSize;
    public ProportionalWork() {
        Linpack linpack = new Linpack();
        double result = linpack.run_benchmark();
        result = linpack.run_benchmark(new Linpack.PCJPrinter());

        PCJ.putLocal("loadBalancingTimingResult", result);
        PCJ.barrier();
        double max = result;

        for (int i = 0; i < PCJ.threadCount(); i++) {
            double other = PCJ.get(i, "loadBalancingTimingResult");
            max = Math.max(max, other);
        }

        compressionSize = result/max;
        
        PCJ.log("compressionSize = " + compressionSize);
        
    }


    @Override
    public int dataSizeBalance(int size) {
        int res = Math.max((int) Math.ceil(size * compressionSize), 4);
        PCJ.log("M reduced from " + size + " to " + res);
        return res;
    }

    @Override
    public boolean continueOptimisation(int iterNo, double[][] vectors) {
        return evaluator.continueOptimisation(iterNo, vectors);
    }

    @Override
    public double f(double[] args) {
        return evaluator.f(args);
    }

}
