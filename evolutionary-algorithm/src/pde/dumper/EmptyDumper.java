/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pde.dumper;

/**
 *
 * @author Łukasz
 */
public class EmptyDumper implements VectorDumper {

    @Override
    public void dumpVectors(double[][] vectors, int step) {
    }
    
    @Override
    public void finalDataDump (double[][] vectors) {
        
    }
}
