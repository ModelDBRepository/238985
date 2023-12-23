/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pde.vectorspolicies;

import java.util.Random;

/**
 *
 * @author Łukasz Górski <lgorski@mat.umk.pl>
 */
public class RandomVectorInitPolicy implements VectorInitPolicyInterface {
    protected Random random;
    double[] min = null, max = null;
    public RandomVectorInitPolicy(long seed, int size) {
        random = new Random (seed);
        min = new double[size + 1];
        max = new double[size + 1];
    }
    
    public void setMinMax (double min, double max) {
        for (int i = 1; i < this.max.length; i++) {
            this.max[i] = max;
            this.min[i] = min;
        }
    }
    
    public void setMin (double... mins) {
        for (int i = 1; i < mins.length; i++) {
            min[i] = mins[i];
        }
    }
    
    public void setMax (double... maxs) {
        for (int i = 1; i < maxs.length; i++) {
            max[i] = maxs[i];
        }
    }
    
    
    @Override
    public void initVector(double[] vector, int iz) {
             for (int j = 1; j < vector.length; j++) {
                double _min = min[j];
                double _max = max[j];
                vector[j] = _min + (_max - _min) * random.nextDouble();
            }
    }

    @Override
    public void boundVector(double[] vector) {
        for (int i = 1; i < vector.length; i++) {
            vector[i] = Math.max(vector[i], min[i]);
            vector[i] = Math.min (vector[i], max[i]);
        }
    }

    
}
