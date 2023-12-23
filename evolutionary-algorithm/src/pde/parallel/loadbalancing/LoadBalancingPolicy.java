/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pde.parallel.loadbalancing;

import pde.evaluators.DifferentialEvaluator;

/**
 *
 * @author Łukasz Górski <lgorski@mat.umk.pl>
 */
public abstract class LoadBalancingPolicy implements DifferentialEvaluator {

        protected DifferentialEvaluator evaluator;

    public void setDifferentialEvaluator(DifferentialEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    public int dataSizeBalance(int size) {
        return size;
    }
}
