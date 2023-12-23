/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.umk.lgorski.elegans.evaluators;

import java.util.Arrays;
import org.pcj.PCJ;
import pl.umk.lgorski.utils.Configuration;

/**
 *
 * @author Łukasz
 */
public class ElegansEvaluatorLimitByPrecision extends ElegansEvaluatorBase {

    private double stopThreshold;
    private double previousIterationBest = Double.MAX_VALUE;

    public ElegansEvaluatorLimitByPrecision(Configuration props) {
        super(props);
        stopThreshold = props.readProperty("ga.elegans.evaluator.precision", 0.1);
    }

    @Override
    public boolean continueOptimisation(int iterNo, double[][] vectors) {
        double current = Arrays.stream(vectors).mapToDouble(x -> x[0]).min().getAsDouble();
        if (Math.abs(previousIterationBest - current) < stopThreshold) {
            return false;
        }
        
        previousIterationBest = current;
        return true;
    }

}
