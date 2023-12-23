/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.umk.lgorski;

import java.util.Arrays;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.DormandPrince54Integrator;
import org.apache.commons.math3.ode.nonstiff.DormandPrince853Integrator;

/**
 *
 * @author Łukasz Górski <lgorski@mat.umk.pl>
 */
public class Solver {

    private int[][] pozEpsilon;
    private double[][] dox;
    private int suma = 0;

    public Solver(int[][] pozEpsilon, int suma, double[][] dox, double[] gamma, double[] theta) {
        this.pozEpsilon = pozEpsilon;
        this.dox = dox;
        this.suma = suma;
        equation.setGamma(gamma);
        equation.setTheta(theta);
    }

    final Equation equation = new Equation();
    final FirstOrderIntegrator integrator = new DormandPrince54Integrator(1.0e-6, 100000.0, 1.0e-6, 1.0e-6);
    final double[] fin = new double[14];

    public double wijRozn(double[][] nonZeroSW, double[][] nonZeroGJ, int[] a, int[][] epsilon, double[] x, double input, double ashT, double fASH) {
        equation.setNonZeroSW(nonZeroSW);
        equation.setNonZeroGJ(nonZeroGJ);
        equation.setA(a);
        equation.setEpsilon(epsilon);
        equation.setX(x);
        equation.setAshT(ashT);
        equation.setfASH(fASH);
        equation.setInput(input);
        
        integrator.integrate(equation, equation.initialValueForT(),
                equation.initialConditions(), 3000, fin);
        double feb = fin[5];
        double fef = fin[6];
        return fef - feb;

    }

    public double wijGA(int[][] mask, double qs, double qe, double ashCoeff, double fASH, double input, int eta, int k, long l) {
        double[][] CSW = AdditionalUtils.matrixRowByRowMultiply(AdditionalUtils.matrixMultiply(PhysiologicalConstants.connSW, qs), mask);
        double[][] CGJ = AdditionalUtils.matrixMultiply(PhysiologicalConstants.connGJ, qe);

        double[] kk = new double[ExperimentalData.abl.length];
        for (int i = 0; i < kk.length; i++) {
            kk[i] = wijRozn(CSW, CGJ, ExperimentalData.abl[i], Utils.podmiana(l, suma, pozEpsilon),
                    dox[k - 1], input, ashCoeff * equation.getTheta()[0], fASH);
        }
        return Utils.compTfTb(kk, eta);
    }
}
