/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.umk.lgorski;

import java.util.stream.DoubleStream;
import static pl.umk.lgorski.PhysiologicalConstants.*;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;

/**
 *
 * @author Łukasz Górski <lgorski@mat.umk.pl>
 */
public class Equation implements FirstOrderDifferentialEquations {

    private double input;

    public double getInput() {
        return input;
    }

    public void setInput(double input) {
        this.input = input;
    }
    private double[] x;
    private int[] a;
    private double[][] nonZeroSW;
    private double[][] nonZeroGJ;
    private double[] gamma;
    private double[] theta;
    private int[][] epsilon;
    private double ashT;
    private double fASH;

    public double getfASH() {
        return fASH;
    }

    public void setfASH(double fASH) {
        this.fASH = fASH;
    }
    

    Equation() {
        
    }

    public double[] getX() {
        return x;
    }

    public void setX(double[] x) {
        this.x = x;
    }

    public int[] getA() {
        return a;
    }

    public void setA(int[] a) {
        this.a = a;
    }

    public double[][] getNonZeroSW() {
        return nonZeroSW;
    }

    public void setNonZeroSW(double[][] nonZeroSW) {
        this.nonZeroSW = nonZeroSW;
    }

    public double[][] getNonZeroGJ() {
        return nonZeroGJ;
    }

    public void setNonZeroGJ(double[][] nonZeroGJ) {
        this.nonZeroGJ = nonZeroGJ;
    }

    public double[] getGamma() {
        return gamma;
    }

    public void setGamma(double[] gamma) {
        this.gamma = gamma;
    }

    public double[] getTheta() {
        return theta;
    }

    public void setTheta(double[] theta) {
        this.theta = theta;
    }

    public int[][] getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(int[][] epsilon) {
        this.epsilon = epsilon;
    }

    public double getAshT() {
        return ashT;
    }

    public void setAshT(double ashT) {
        this.ashT = ashT;
    }

    public Equation(double[][] nonZeroSW, double[][] nonZeroGJ, int[] a, int[][] epsilon, double[] x, double input, double[] gamma, double[] theta, double ashT) {
        this.nonZeroSW = nonZeroSW;
        this.nonZeroGJ = nonZeroGJ;
        this.x = x;
        this.input = input;
        this.a = a;
        this.gamma = gamma;
        this.theta = theta;
        this.epsilon = epsilon;
        this.ashT = ashT;
    }

    double m(double fnI) {
        return 1.0 / (1 + Math.exp(-(fnI + 20) / 9));
    }

    public double funH(double x, double gamma, double theta) {
        return 1.0 / (1 + Math.exp(-gamma * (x - theta)));
    }

    @Override
    public int getDimension() {
        return 14;
    }

    private double fn(double[] y, int i) {
        if (i == 1) {
            return ashT;
        } else {
            return y[i - 2];
        }
    }

    private double ca(double[] y, int i) {
        if (i == 9) {
            return y[13];
        } else {
            return y[i - 2 + 8];
        }
    }

    @Override
    public void computeDerivatives(double t, double[] y, double[] ydot) throws MaxCountExceededException, DimensionMismatchException {
        /* from Mathematica code we have the following mapping:
         y = { ava, avb, avd, ave, dva, eb, ef, pvc, cava, cavb, cavd, cave, cdva, cpvc }
         ash = const
        
  
         */
        int[] indexes = new int[]{2, 3, 4, 5, 6, 9};

        //-------------------eqnsVolIntN ------------------------------------
        for (int i : indexes) {
            double sum1 = 0, sum2 = 0;

            for (int j = 1; j <= 9; j++) {
                if (j == i) {
                    continue;
                }

                sum1 += a[j - 1] * nonZeroSW[i - 1][j - 1] * funH(fn(y, j), gamma[j - 1], theta[j - 1])
                        * (fn(y, i) - (1 - epsilon[i - 1][j - 1]) * getvCl());
                sum2 += a[i - 1] * a[j - 1] * nonZeroGJ[i - 1][j - 1] * (fn (y, i) - fn(y, j));
            }
            ydot[i - 2]
                    = (1 / getcC()) * (-getgL() * (fn (y, i) - getVl())
                    - getgCa() * Math.pow(m(fn (y, i)), 2) * (fn (y, i) - getvCa())
                    - getgKCa() * ca (y, i) / (getkD() + ca (y, i)) * (fn (y, i) - getvK())
                    - sum1 - sum2 + (input*x[i - 1]) * (1 + a[0] * fASH * funH (fn(y, 1), gamma[1 - 1], theta[1 - 1]) ));
        }

        //-------------------------- eqnsVolMotN -----------------------------
        for (int i = 7; i <= 8; i++) {
            double sum1 = 0, sum2 = 0;

            for (int j = 1; j <= 9; j++) {
                if (j == i) {
                    continue;
                }

                sum1 += a[j - 1] * nonZeroSW[i - 1][j - 1] * funH(fn (y, j), gamma[j - 1], theta[j - 1])
                        * (fn (y, i) - (1 - epsilon[i - 1][j - 1]) * getvCl());
                sum2 += a[i - 1] * a[j - 1] * nonZeroGJ[i - 1][j - 1] * (fn (y, i) - fn (y, j));
            }
            ydot[i - 2] = (1 / getcC()) * (-getgL() * (fn (y, i) - getVl())
                    - sum1 - sum2);
        }
        //--------------------- eqnsCon -------------------------------------
        for (int i: indexes) {
            int idx = (i == 9) ? 7 : i;
            ydot[idx - 2 + 8] = -ca (y, i) / getTauCa() - 2 * getgCa() * Math.pow(m(fn (y, i)), 2)
                    / (getD() * getFar()) * (fn (y, i) - getvCa());

        }
    }

    public double initialValueForT() {
        return 0.0;
    }

    public double[] initialConditions() {
        return DoubleStream.iterate(2, x -> x).limit(getDimension()).toArray();
    }

}
