/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pde;

import pde.dumper.EmptyDumper;
import pde.dumper.VectorDumper;
import pde.evaluators.DifferentialEvaluator;
import pde.vectorspolicies.VectorInitPolicyInterface;

/**
 *
 * @author Łukasz Górski <lgorski@mat.umk.pl>
 */
public abstract class EvolutionaryAlgorithm {

    protected VectorDumper dumper = new EmptyDumper();

    protected double[][] vectors;

    protected int M; //number of vectors
    protected int N; //size of single vector
    protected double F; //scaling factor
    protected double C; //crossover probability

    protected final DifferentialEvaluator evaluator;
    protected VectorInitPolicyInterface vectorInit;

    public VectorDumper getDumper() {
        return dumper;
    }

    public void setDumper(VectorDumper dumper) {
        this.dumper = dumper;
    }

    public int getM() {
        return M;
    }

    public void setM(int M) {
        this.M = M;

        updateVectorsWithNewMN();
    }

    public int getN() {
        return N;
    }

    public void setN(int N) {
        this.N = N;

        updateVectorsWithNewMN();
    }

    public double getF() {
        return F;
    }

    public void setF(double F) {
        this.F = F;
    }

    public double getC() {
        return C;
    }

    public void setC(double C) {
        this.C = C;
    }

    private void updateVectorsWithNewMN() {
        vectors = new double[M][N + 1];
    }

    public void initializePopulation() {
        for (int i = 0; i < vectors.length; i++) {
            vectorInit.initVector(vectors[i], i);
            vectors[i][0] = evaluator.f(vectors[i]);
        }
    }

    public void optimizeSolution() {
        for (int step = 1; evaluator.continueOptimisation(step, vectors); step++) {
            optimizeSolutionStep(step);
        }
        dumper.finalDataDump(vectors);
    }
    public double[] getOptimalVector() {
        int bestIdx = 0;

        for (int i = 1; i < vectors.length; i++) {
            if (evaluator.evaluateVectors(vectors[i], vectors[bestIdx]) < 0) {
                bestIdx = i;
            }
        }

        return vectors[bestIdx];
    }

    public abstract void optimizeSolutionStep(int step);

    public VectorInitPolicyInterface getVectorInit() {
        return vectorInit;
    }

    public void setVectorInit(VectorInitPolicyInterface vectorInit) {
        this.vectorInit = vectorInit;
    }

    public DifferentialEvaluator getEvaluator() {
        return evaluator;
    }

    public double[][] getVectors() {
        return vectors;
    }

    public void setVectors(double[][] vectors) {
        this.vectors = vectors;
    }

    public EvolutionaryAlgorithm(int M, int N, double C, double F, VectorInitPolicyInterface vectorInit, DifferentialEvaluator evaluator) {
        this.evaluator = evaluator;
        this.vectorInit = vectorInit;
        this.C = C;
        this.F = F;
        this.M = M;
        this.N = N;

        updateVectorsWithNewMN();
    }
}
