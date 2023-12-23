package pde.parallel.loadbalancing;

import org.pcj.PCJ;
import pde.evaluators.DifferentialEvaluator;
import pde.parallel.ParallelBaseSequentialMigration;

/**
 *
 * @author Łukasz
 *
 * All threads stop after the fastest one finishes. The the slowest one
 * broadcasts its population vectors data. And all threads resume calculation
 * starting from that point
 */
public class ContinueFromSlowest extends LoadBalancingPolicy {

    private final ParallelBaseSequentialMigration pde;
    private double[] optimalVector;
    private int slowestStep;
    private LoadBalancingState state = new BeforeLoadBalancing();

    private void setState(LoadBalancingState state) {
        this.state = state;
    }

    public ContinueFromSlowest(ParallelBaseSequentialMigration pde) {
        this.pde = pde;
    }



    @Override
    public boolean continueOptimisation(int iterNo, double[][] vectors) {
        return state.continueOptimisation(iterNo, vectors);
    }

    @Override
    public double f(double[] args) {
        return evaluator.f(args);
    }

    interface LoadBalancingState {

        public boolean continueOptimisation(int iterNo, double[][] vectors);
    }

    private class BeforeLoadBalancing implements LoadBalancingState {


        @Override
        public boolean continueOptimisation(int iterNo, double[][] vectors) {
            PCJ.putLocal("step", iterNo);
            boolean doContinue = evaluator.continueOptimisation(iterNo, vectors);
            boolean stopAll = PCJ.getLocal("stopAll");
            if (stopAll == true || doContinue == false) {
                prepare2ndPhase();
            }
            return true;
        }

        private void prepare2ndPhase() {
            double[] optimal = pde.getEa().getOptimalVector();
            optimalVector = new double[optimal.length];
            System.arraycopy(optimal, 0, optimalVector, 0, optimal.length);
            PCJ.broadcast("stopAll", true);
            PCJ.log("Broadcasted stopAll message");
            getVectorsFromSlowestThread();

            ContinueFromSlowest.this.setState(new AfterLoadBalancing());
        }

        private void getVectorsFromSlowestThread() {
            int slowestIndex = findSlowestThreadIndex();
            PCJ.log("Taking vectors from thread #" + slowestIndex + " cont'd from step " + slowestStep);
            double[][] vectors = PCJ.get(slowestIndex, "resultVectors");
            PCJ.putLocal("resultVectors", vectors);
            pde.getEa().setVectors(vectors);
        }

        private int findSlowestThreadIndex() {
            slowestStep = Integer.MAX_VALUE;
            int slowestIdx = -1;
            for (int idx = 0; idx < PCJ.threadCount(); idx++) {
                int remoteStep = PCJ.get(idx, "step");
                if (remoteStep < slowestStep) {
                    slowestStep = remoteStep;
                    slowestIdx = idx;
                }
            }
            ContinueFromSlowest.this.slowestStep = slowestStep;
            return slowestIdx;
        }

    }

    private class AfterLoadBalancing implements LoadBalancingState {

        public void finishedDifferentialEvolution() {
            double[] newOptimum = pde.getEa().getOptimalVector();
            if (optimalVector[0] < newOptimum[0]) {
                PCJ.log("Replacing vector with ED = " + newOptimum[0] + " with that with ED = " + optimalVector[0]);
                pde.getEa().getVectors()[0] = optimalVector;
            }
            PCJ.log("Finished work");
        }

        @Override
        public boolean continueOptimisation(int iterNo, double[][] vectors) {
            PCJ.putLocal("step", ++slowestStep);
            boolean doContinue = evaluator.continueOptimisation(slowestStep, vectors);
            PCJ.log("Real step = " + slowestStep);

            if (doContinue == false) {
                finishedDifferentialEvolution();
            }
            return doContinue;
        }

    }

}
