/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pde.parallel.loadbalancing;

import pde.evaluators.DifferentialEvaluator;
import pde.parallel.ParallelBaseSequentialMigration;

/**
 *
 * @author Łukasz Górski <lgorski@mat.umk.pl>
 */
public class NoLoadBalancing extends LoadBalancingPolicy {

    public NoLoadBalancing (ParallelBaseSequentialMigration pde) {

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
