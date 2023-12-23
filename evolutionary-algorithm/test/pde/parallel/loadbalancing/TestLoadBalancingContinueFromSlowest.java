/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pde.parallel.loadbalancing;

import junit.framework.Assert;
import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mock;
import mockit.Mocked;
import mockit.StrictExpectations;
import mockit.Tested;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pcj.PCJ;
import pde.parallel.ParallelBaseSequentialMigration;

/**
 *
 * @author Łukasz
 */
@RunWith(JMockit.class)
public class TestLoadBalancingContinueFromSlowest {

    @Mocked
    PCJ pcj = null;

    @Mocked
    ParallelBaseSequentialMigration pde = new ParallelBaseSequentialMigration();

    @Tested
    ContinueFromSlowest lbcfs = new ContinueFromSlowest(pde);

    @Test
    public void testFinishedDifferentialEvolution() {
        new Expectations(ContinueFromSlowest.class) {
            {
                PCJ.getLocal("stopAll");
                result = true;
                pde.getEa().getOptimalVector();
                returns(new double[]{3.0, 0, 0}, new double[] {2.0, 1, 1});
                Deencapsulation.invoke(lbcfs, "getVectorsFromSlowestThread");

                PCJ.broadcast("stopAll", true);
                pde.performParallelDifferentialEvolution(anyInt);
                pde.getEa().getVectors(); result = new double[][] {{0,0,0}};
            }
        };
        lbcfs.loadBalanceStep(1);
        lbcfs.finishedDifferentialEvolution();

        new Verifications() {
            {
                pde.getEa().getVectors(); times = 1;
            }
        };
    }

    @Test
    public void testLoadBalanceStepIfStops() {

        new Expectations(ContinueFromSlowest.class) {
            {
                PCJ.getLocal("stopAll");
                returns(false, true);
            }
        };
        boolean actual = lbcfs.loadBalanceStep(10);
        boolean actual2 = lbcfs.loadBalanceStep(15);

        Assert.assertEquals(true, actual);
        Assert.assertEquals(false, actual2);

    }

    @Test
    public void testGetVectorsFromSlowestThread() {

        new Expectations(ContinueFromSlowest.class) {
            {
                final double[][] doubles = new double[][]{{1, 2, 3}, {4, 5, 6}};
                Deencapsulation.invoke(lbcfs, "findSlowestThreadIndex");
                result = 2;
                PCJ.get(2, "resultVectors");
                result = doubles;
                PCJ.putLocal("resultVectors", doubles);
            }
        };

        Deencapsulation.invoke(lbcfs, "getVectorsFromSlowestThread");
    }

    @Test
    public void testFindSlowestThreadIndex() {
        new Expectations() {
            {
                PCJ.threadCount();
                result = 5;

                PCJ.get(0, "step");
                result = 2;
                PCJ.get(1, "step");
                result = 6;
                PCJ.get(2, "step");
                result = 2;
                PCJ.get(3, "step");
                result = 1;
                PCJ.get(4, "step");
                result = 6;
            }
        };
        int actual = Deencapsulation.invoke(lbcfs, "findSlowestThreadIndex");
        Assert.assertEquals(3, actual);
    }
}
