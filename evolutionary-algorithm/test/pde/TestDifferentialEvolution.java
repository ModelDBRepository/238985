package pde;

import java.util.Arrays;
import org.junit.Assert;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import pde.evaluators.MinRosenbrockSaddle;
import pde.vectorspolicies.RandomVectorInitPolicy;

public class TestDifferentialEvolution {

    DifferentialEvolution de = null;

    @Before
    public void prepareDE() {
        RandomVectorInitPolicy vip = new RandomVectorInitPolicy(0, 2);
        vip.setMinMax(-2.048, 2.048);
        de = new DifferentialEvolution(10, 2, 0.9, 0.9, vip, new MinRosenbrockSaddle(10));
    }

    @Test
    public void testGetOptimalVector() {
        double[] opt = de.getOptimalVector();
        double[] newOpt = Arrays.copyOf(opt, opt.length);
        newOpt[0]--;
        de.getVectors()[0] = newOpt;
        double[] res = de.getOptimalVector();
        Assert.assertArrayEquals(newOpt, res, 0.0);
    }

    @Test
    public void testResultImproves() {
        double[] res = null;

        de.initializePopulation();

        final double[] starting = Arrays.copyOf(de.getOptimalVector(), 3);
        for (int i = 0; i < 10_000; i++) {
            de.optimizeSolutionStep(i);
            res = de.getOptimalVector();
            if (res[0] > starting[0]) {
                fail("Solution not optimized");
            }
        }
        if (starting[0] <= res[0]) {
            fail("Solution not improved");
        }
    }
}
