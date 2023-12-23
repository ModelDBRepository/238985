/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pde.vectorspolicies;

import org.junit.Assert;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Łukasz
 */
public class TestRandomVectorPolicy {

    @Test
    public void testInitVector () {
        RandomVectorInitPolicy rvp = new RandomVectorInitPolicy(0, 10_000);
        double[] vec = new double [10_000 + 1];
        
        rvp.setMinMax (-100, -30);
        
        rvp.initVector(vec, 0);
        
        for (int i = 1; i < vec.length; i++) {
            if (vec[i]  < -100 || vec[i] > -30) {
                fail("vec[i] is " + vec[i]);
            }
        }
    }
    @Test
    public void testBoundVector () {
        RandomVectorInitPolicy rvp = new RandomVectorInitPolicy(0, 4);
        double[] maxs = new double[] {0,  10, -4, 10, 10};
        double[] mins = new double[] {0, 1, -10, -3, 2};
        double[] vec = new double[] {0,  11, -3, -4, 4};
        rvp.setMax(maxs);
        rvp.setMin(mins);
        rvp.boundVector(vec);
        Assert.assertArrayEquals(new double[] {0, 10, -4, -3, 4}, vec, 0);
    }
}
