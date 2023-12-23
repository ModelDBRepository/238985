package pde;

import java.util.Random;
import pde.dumper.EmptyDumper;
import pde.dumper.VectorDumper;
import pde.evaluators.DifferentialEvaluator;
import pde.vectorspolicies.VectorInitPolicyInterface;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Łukasz Górski <lgorski@mat.umk.pl>
 */
public class DifferentialEvolution extends EvolutionaryAlgorithm {

    Random random = new Random();

    
    public DifferentialEvolution(int M, int N, double C, double F, VectorInitPolicyInterface vectorInit, DifferentialEvaluator evaluator) {
        super(M, N, C, F, vectorInit, evaluator);
    }
    
    @Override
    public void optimizeSolutionStep(int step) {
        for (int i = 0; i < M; i++) {
            double[] trialVector = new double[N + 1];
            double[] currentVector = vectors[i];
            final int ii = i;
            int[] rs = random.ints(0, vectors.length).filter(j -> j != ii).distinct().limit(3).toArray();

            //create a trial vector
            for (int j = 1; j < N + 1; j++) {
                trialVector[j] = vectors[rs[0]][j] + F * (vectors[rs[1]][j] - vectors[rs[2]][j]);
            }

            //crossover
            int l = random.nextInt(N + 1) + 1;
            for (int m = 1; m < N + 1; m++) {
                if (random.nextDouble() >= C && m != l) {
                    trialVector[m] = currentVector[m];
                }
            }
            vectorInit.boundVector(trialVector);
            trialVector[0] = evaluator.f(trialVector);
            
            if (evaluator.evaluateVectors(trialVector, currentVector) < 0) {
                vectors[i] = trialVector;
            }

        }
        
        dumper.dumpVectors(vectors, step);
    }
    

}
