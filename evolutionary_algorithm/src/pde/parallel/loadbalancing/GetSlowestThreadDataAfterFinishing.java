/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pde.parallel.loadbalancing;

import org.pcj.PCJ;
import pde.evaluators.DifferentialEvaluator;
import pde.parallel.ParallelBaseSequentialMigration;

/**
 *
 * @author Łukasz Górski <lgorski@mat.umk.pl>
 */
public class GetSlowestThreadDataAfterFinishing extends LoadBalancingPolicy {

    private final ParallelBaseSequentialMigration pde;
    private final int exchangeThreshold;

    public GetSlowestThreadDataAfterFinishing(ParallelBaseSequentialMigration pde, int exchangeThreshold) {
        this.pde = pde;
        this.exchangeThreshold = exchangeThreshold;
    }


    private int slowestStep = Integer.MAX_VALUE, slowestIdx = Integer.MAX_VALUE;

    private void copySlowestThreadsData() throws ClassCastException {
        //PCJ.log("Copying vector from thread #" + slowestIdx + " its step = " + slowestStep);
        PCJ.putLocal("step", slowestStep);
        double[][] resultVectors = PCJ.get(slowestIdx, "resultVectors");
        PCJ.putLocal("resultVectors", resultVectors);
        pde.getEa().setVectors(resultVectors);
    }

    private void findSlowestThread() {
        slowestIdx = slowestStep = Integer.MAX_VALUE;
        for (int i = 0; i < PCJ.threadCount(); i++) {
            int testedStep = PCJ.get(i, "step");
            if (testedStep < slowestStep) {
                slowestStep = testedStep;
                slowestIdx = i;
            }
        }
    }

    private boolean loadBalanced = false;
    double[] oldOpt = null;

    @Override
    public boolean continueOptimisation(int iterNo, double[][] vectors) {
        PCJ.putLocal("step", iterNo);
        boolean doContinue = evaluator.continueOptimisation(iterNo, vectors);

        if (doContinue == false && loadBalanced == false) {
            oldOpt = pde.getEa().getOptimalVector();
            boolean taken = takeVectorsFromSlowest();
            loadBalanced = true;
            if (taken == true) {
                return true;
            }
        } else if (doContinue == false && loadBalanced == true) {
            double[] newOpt = pde.getEa().getOptimalVector();
            if (oldOpt[0] < newOpt[0]) {
                pde.getEa().getVectors()[0] = oldOpt;
            }
        }
        
        return doContinue;

    }

    @Override
    public double f(double[] args) {
        return evaluator.f(args);
    }

    private boolean takeVectorsFromSlowest() {
        boolean taken = false;
        double[] oldOpt = pde.getEa().getOptimalVector();
        findSlowestThread();
        if (Math.abs(slowestStep - (int) PCJ.getLocal("step")) >= exchangeThreshold) {
            copySlowestThreadsData();
            taken = true;
        }
        return taken;
    }

}
