
package pde.evaluator;

import java.util.Arrays;
import mockit.Mocked;
import org.junit.Assert;
import org.junit.Test;
import pde.evaluators.DifferentialEvaluator;

/**
 *
 * @author Łukasz
 */
public class TestDifferentialEvaluator {
    

    @Test
    public void testEvaluateVectors () {
        DifferentialEvaluator de = new DifferentialEvaluator() {

            @Override
            public boolean continueOptimisation(int iterNo, double[][] vectors) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public double f(double[] args) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        
        double[] smaller = new double[] { -1, 0, 1, 3 };
        double[] bigger = new double[] {2, 3, 3, 2};
        
        int res = de.evaluateVectors(smaller, bigger);
        Assert.assertEquals(-1, res);
        
        res = de.evaluateVectors(smaller, Arrays.copyOf(smaller, smaller.length));
        Assert.assertEquals(0, res);
        
        res = de.evaluateVectors(bigger, smaller);
        Assert.assertEquals(1, res);
    }
}
