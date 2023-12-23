/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.umk.lgorski;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 * @author Ĺ�ukasz GĂłrski <lgorski@mat.umk.pl>
 */
public class C_Elegans_Java {

    private int[][] maskaPolaczen = null;
    private int[][] pozEpsilon = null;
    private double[][] dox = null;
    private Solver solver = null;
    private long varL = 0;

    public long getVarL() {
        return varL;
    }

    
    
    public C_Elegans_Java (double prog, double[] theta, double[] gamma) {
        this(prog, prog, theta, gamma);
    }
    
    public C_Elegans_Java(double progPolaczen, double progWariacji, double[] theta, double[] gamma) {

        maskaPolaczen = Utils.prog(progPolaczen, Double.MAX_VALUE, PhysiologicalConstants.connSW, Optional.empty());
        
        Utils.IntRefHolder intRef = new Utils.IntRefHolder();
        int[][] maskaWariacjiE = Utils.prog (progWariacji, Double.MAX_VALUE, PhysiologicalConstants.connSW, Optional.of(intRef));
        varL = 1L << intRef.value;
        
        pozEpsilon = Utils.position(maskaWariacjiE, 1);

        int suma = Stream.of(maskaWariacjiE).flatMapToInt(IntStream::of).sum();
        dox = AdditionalUtils.pairCombinations(new int[]{-1, 1}, 7);
        solver = new Solver(pozEpsilon, suma, dox, gamma, theta);
        
        for (int i = 0; i < dox.length; i++) {
            double[] arr = new double[dox[i].length + 2];

            System.arraycopy(dox[i], 0, arr, 0, 6);
            arr[6] = arr[7] = 0;
            arr[8] = dox[i][6];
            dox[i] = arr;
        }
        
      
    }

    public double run(double qs, double qe, double ashCoeff, double fASH, double input, int eta, int k, long l) {
        return solver.wijGA(maskaPolaczen, qs, qe, ashCoeff, fASH, input, eta, k, l);
    }

    public static void main(String[] args) {
        C_Elegans_Java elegans = new C_Elegans_Java(1, Utils.TYPICAL_THETA, Utils.TYPICAL_GAMMA);
        double ret = 0;
         
        ret =  elegans.run(0.109766, 0.026847, 1.129987,-0.360135,1,2,128,1);


        System.out.println(ret);
    }
    
    public void setDistance (Utils.Distance distance) {
        Utils.setDistanceFunction(distance);
    }

}
