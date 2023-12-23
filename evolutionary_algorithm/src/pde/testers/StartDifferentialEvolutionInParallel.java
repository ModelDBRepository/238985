/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pde.testers;

import org.pcj.PCJ;
import pde.DifferentialEvolution;
import pde.evaluators.DifferentialEvaluator;
import pde.evaluators.MinRosenbrockSaddle;
import pde.parallel.EvolutionaryStorage;
import pde.parallel.ParallelNonBlockingMigration;
import pde.vectorspolicies.RandomVectorInitPolicy;

class ParallelTest extends ParallelNonBlockingMigration {



    @Override
    public void performInit() {

        DifferentialEvaluator evaluator = new MinRosenbrockSaddle(40_000);
        RandomVectorInitPolicy vectors = new RandomVectorInitPolicy(System.currentTimeMillis(), 2);
        vectors.setMinMax(-20, 20);

        this.configureWithEvolutionaryAlgorithm(new DifferentialEvolution(300, 2, 0.8, 0.9, vectors, evaluator));

    }

    @Override
    public void showResult() {
        System.out.println(ea.getOptimalVector()[1] + " " + ea.getOptimalVector()[2]);
    }

}

public class StartDifferentialEvolutionInParallel {

    public static void main(String[] args) {
        PCJ.start(ParallelTest.class, EvolutionaryStorage.class, "nodes.txt");
    }
}
