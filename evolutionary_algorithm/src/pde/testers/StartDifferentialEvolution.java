/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pde.testers;

import pde.DifferentialEvolution;
import pde.EvolutionaryAlgorithm;
import pde.evaluators.DifferentialEvaluator;
import pde.evaluators.MinRosenbrockSaddle;
import pde.vectorspolicies.RandomVectorInitPolicy;

/**
 *
 * @author Łukasz Górski <lgorski@mat.umk.pl>
 */
public class StartDifferentialEvolution {

    public static void main(String[] args) throws InterruptedException {

        DifferentialEvaluator evaluator = new MinRosenbrockSaddle(200);
        RandomVectorInitPolicy vectors = new RandomVectorInitPolicy(System.currentTimeMillis(), 2);
        vectors.setMinMax(-2.048, 2.048);
        EvolutionaryAlgorithm de = new DifferentialEvolution(800, 2, 0.8, 0.9, vectors, evaluator);


        de.initializePopulation();

        long t = -System.nanoTime();
        for (int step = 1; evaluator.continueOptimisation(step, de.getVectors()); step++) {
            de.optimizeSolutionStep(step);
            if ((step - 1) % 10 == 0) {
        //        System.out.println(evaluator.f(de.getOptimalVector()));
            }
        }
        t += System.nanoTime();
      //  System.out.println("Time: " + (t * 1e-9) + " s");
        double[] res = de.getOptimalVector();
        //SSystem.out.println(res[0] + " " + res[1]);
    }
}
