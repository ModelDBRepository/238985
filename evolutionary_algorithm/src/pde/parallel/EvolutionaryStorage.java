/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pde.parallel;

import org.pcj.Shared;
import org.pcj.Storage;

/**
 *
 * @author Łukasz
 */
public class EvolutionaryStorage extends Storage {

    @Shared
    double[] theBest;

    @Shared
    double[][] migratedVectors;

    @Shared
    double[][] resultVectors;

    @Shared
    int step;
    
    @Shared
    boolean stopAll;
    
    @Shared
    double loadBalancingTimingResult;
    
    //for RestartCalculations
    @Shared
    Boolean[] finished;
}
