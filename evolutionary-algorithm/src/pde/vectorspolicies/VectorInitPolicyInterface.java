/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pde.vectorspolicies;

/**
 *
 * @author Łukasz Górski <lgorski@mat.umk.pl>
 */
public interface VectorInitPolicyInterface {
    
    void initVector (double[] vector, int i);
    
    void boundVector (double[] vector);
}
