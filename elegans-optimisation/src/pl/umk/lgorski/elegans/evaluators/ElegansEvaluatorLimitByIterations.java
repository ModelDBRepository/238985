/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.umk.lgorski.elegans.evaluators;

import java.util.Arrays;
import pl.umk.lgorski.utils.Configuration;

/**
 *
 * @author Łukasz
 */
public class ElegansEvaluatorLimitByIterations extends ElegansEvaluatorBase {

    private int maxIter;

    public ElegansEvaluatorLimitByIterations(Configuration props) {
        super(props);
        this.maxIter = props.readPropertyInteger("ga.I");
    }

    @Override
    public boolean continueOptimisation(int iterNo, double[][] vectors) {
        return iterNo <= maxIter;
    }
}

