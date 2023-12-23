/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.umk.lgorski.elegans.evaluators;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import pde.evaluators.DifferentialEvaluator;
import pl.umk.lgorski.utils.Configuration;

/**
 *
 * @author Łukasz
 */
public class TestEvaluators {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    Configuration props;
    
    @Before
    public void setupConfig () throws FileNotFoundException, IOException {
        File config = folder.newFile("config.properties");
        try (PrintWriter writer = new PrintWriter(config)) {
            writer.println("ga.I=42");
            writer.println("ga.elegans.evaluator.precision=0.1");
        }
        props = Configuration.create(config.getPath());
    }
    @Test
    public void testIterations() {
        DifferentialEvaluator eval = new ElegansEvaluatorLimitByIterations(props);

        boolean ret = eval.continueOptimisation(7, null);
        Assert.assertEquals(true, ret);

        ret = eval.continueOptimisation(43, null);
        Assert.assertEquals(false, ret);
    }

    @Test
    public void testLimit() {

        DifferentialEvaluator eval = new ElegansEvaluatorLimitByPrecision(props);

        double[][] start = new double[][]{{1, 2, 3}, {2, 2, 2}, {0.5, 1, 1}, {3, 3, 3}};
        double[][] set1 = new double[][]{{0.39, 1, 1}, {2, 3, 3}, {11, 1, 1}, {2, 2, 2}};
        double[][] set2 = new double[][]{{11, 11, 11}, {1, 2, 3}, {1, 1, 1}, {0.3, 1, 1}};

        boolean res = eval.continueOptimisation(1, start);
        Assert.assertEquals(true, res);
        res = eval.continueOptimisation(2, set1);
        Assert.assertEquals(true, res);
        res = eval.continueOptimisation(3, set2);
        Assert.assertEquals(false, res);
    }
}
