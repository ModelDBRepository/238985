/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pde.parallel;

import java.util.Arrays;
import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.StrictExpectations;
import mockit.Verifications;
import org.junit.Assert;
import org.junit.Test;
import org.pcj.PCJ;
import pde.EvolutionaryAlgorithm;
import pde.evaluators.DifferentialEvaluator;

/**
 *
 * @author Łukasz
 */
public class TestParallelDifferentialEvolutionBaseSequentialMigration {

    @Mocked
    PCJ pcj = null;

    @Test
    public void testReduceTheBestVector(@Mocked final EvolutionaryAlgorithm de) {

        ParallelBaseSequentialMigration pde = new ParallelBaseSequentialMigration();
        pde.setEa(de);
        pde.setEvaluator(new MockUp<DifferentialEvaluator>() {
            @Mock
            public int evaluateVectors (double[] first, double[] second) {
                return (int) Math.signum(first[0] - second[0]);
            }
        }.getMockInstance());
        new Expectations() {
            {
                double[] arr = new double[]{1.0, 0.0, 0.0};
                de.getOptimalVector();
                result = arr;
                PCJ.putLocal("theBest", arr);
                PCJ.barrier();
                PCJ.myId();
                result = 0;
                PCJ.threadCount();
                result = 4;

                PCJ.get(1, "theBest");
                result = new double[]{0.5, 0.1, 0.1};
                PCJ.get(2, "theBest");
                result = new double[]{0.2, 0.1, 0.1};
                PCJ.get(3, "theBest");
                result = new double[]{0.4, 0.1, 0.1};

            }

        } ;
        
        Deencapsulation.invoke(pde, "reduceTheBestVector");
        
        new Verifications() {
            {
                PCJ.putLocal("theBest", new double[]{0.2, 0.1, 0.1});
            }
        };

    }

    @Test

    public void testFindSwapIndexInArray() {
        double[][] array = new double[][]{{1.0, 2.0, 3.0}, {2.0, 3.0, 5.0}, {1.1, 2.2, 1.1}};
        double[][] array2 = new double[][]{{1.1, 1.1, 1.1}, {1.1, 1.1, 1.1}, {1.1, 1.1, 1.1}};
        double[] toSwap1 = new double[]{2.0, 3.0, 5.0};
        double[] toSwap2 = new double[]{1.0, 2.0, 3.0};
        double[] toSwap3 = new double[]{1.0, 2.0, 3.01};
        double[] toSwap4 = new double[]{1.1, 1.1, 1.1};

        ParallelBaseSequentialMigration pde = new ParallelBaseSequentialMigration();

        assertArrayInequality(pde, array, toSwap1);
        assertArrayInequality(pde, array, toSwap2);
        assertArrayInequality(pde, array, toSwap3);

        int res = invoke(pde, array2, toSwap4);
        Assert.assertEquals(0, res);
    }

    private int invoke(ParallelBaseSequentialMigration pde, double[][] arr, double[] toSwap) {
        return Deencapsulation.invoke(pde, "findSwapIndexInArray", arr, toSwap);
    }

    private void assertArrayInequality(ParallelBaseSequentialMigration pde, double[][] arr, double[] toSwap) {
        int res = invoke(pde, arr, toSwap);
        Assert.assertFalse(Arrays.equals(arr[res], toSwap));

    }

}
