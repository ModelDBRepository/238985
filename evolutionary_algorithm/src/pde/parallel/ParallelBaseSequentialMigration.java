/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pde.parallel;

import java.net.InetAddress;
import java.net.UnknownHostException;
import pde.parallel.loadbalancing.NoLoadBalancing;
import pde.parallel.loadbalancing.LoadBalancingPolicy;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import pde.evaluators.DifferentialEvaluator;
import pde.vectorspolicies.VectorInitPolicyInterface;
import org.pcj.PCJ;
import org.pcj.StartPoint;
import pde.EvolutionaryAlgorithm;
import pde.dumper.EmptyDumper;
import pde.dumper.VectorDumper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Łukasz Górski <lgorski@mat.umk.pl>
 */
public class ParallelBaseSequentialMigration implements StartPoint {

    protected EvolutionaryAlgorithm ea;
    

    Random random = new Random();
    double phi;

    public EvolutionaryAlgorithm getEa() {
        return ea;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    @Override
    public void main() throws Throwable {
        try {
            PCJ.log(InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            
        }
        this.performInit();
        long start = System.nanoTime();
        
        ea.initializePopulation();
        this.performParallelDifferentialEvolution(1);
        long middle = System.nanoTime();
        long beforeReduce = System.nanoTime();
        PCJ.log((beforeReduce - start) * 1e-9 + " s before reduction");
        this.reduceTheBestVector();
        
        long stop = System.nanoTime();

        PCJ.log((stop - start) * 1e-9 + " s final time");
        ea.getDumper().finalDataDump(PCJ.getLocal("resultVectors"));
        this.showResult();
    }

    public void performInit() {
        throw new NotImplementedException();
    }

    public void showResult() {
        throw new NotImplementedException();
    }

    public void performParallelDifferentialEvolution(int ss) {
        for (; ea.getEvaluator().continueOptimisation(ss, ea.getVectors()); ss++) {
            ea.optimizeSolutionStep(ss);
            this.stepMigrate(ss);
        }
    }

    private void reduceTheBestVector() throws ClassCastException {
        double[] bestVector = ea.getOptimalVector();
        PCJ.putLocal("theBest", bestVector);

        PCJ.barrier();

        if (PCJ.myId() == 0) {
            for (int id = 1; id < PCJ.threadCount(); id++) {
                double[] other = PCJ.get(id, "theBest");
                if (ea.getEvaluator().evaluateVectors(other, bestVector) < 0) {
                    bestVector = other;
                }
            }
            PCJ.putLocal("theBest", bestVector);
        }
        PCJ.barrier();
    }

    public void configureWithEvolutionaryAlgorithm(EvolutionaryAlgorithm de) {
        this.ea = de;
        
        if (ea.getEvaluator() instanceof LoadBalancingPolicy) {
            de.setM(((LoadBalancingPolicy)ea.getEvaluator()).dataSizeBalance(de.getM()));
        }
        double[][] res = new double[de.getM()][de.getN() + 1];
        PCJ.putLocal("resultVectors", res);
        PCJ.putLocal("migratedVectors", new double[1][]);
        PCJ.monitor("migratedVectors");
        de.setVectors(res);
        PCJ.barrier();
    }

    public void setEa(EvolutionaryAlgorithm de) {
        this.ea = de;
    }

    public double getPhi() {
        return phi;
    }

    public void setPhi(double phi) {
        this.phi = phi;
    }

    public void stepMigrate(int step) {
        double[] best = ea.getOptimalVector();
        double[][] res = PCJ.getLocal("resultVectors");
        PCJ.put((PCJ.myId() + 1) % PCJ.threadCount(), "migratedVectors", best, 0);
        PCJ.waitFor("migratedVectors");

        if (Math.random() <= this.phi) {
            double[] prop = PCJ.getLocal("migratedVectors", 0);

            int swapIdx = findSwapIndexInArray(res, prop);

            System.arraycopy(prop, 0, res[swapIdx], 0, prop.length);
            PCJ.putLocal("resultVectors", res[swapIdx], swapIdx);
        }
    }

    int findSwapIndexInArray(double[][] array, double[] toSwap) {
        List<Integer> ints = IntStream.range(0, array.length).boxed().collect(Collectors.toList());
        Collections.shuffle(ints);

        int swapIdx = 0;
        for (int idx : ints) {
            if (!Arrays.equals(array[idx], toSwap)) {
                swapIdx = idx;
                break;
            }
        }
        return swapIdx;
    }
}
