/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.umk.lgorski.elegans.evaluators;

import pl.umk.lgorski.elegans.evaluators.ElegansEvaluatorBase;
import pl.umk.lgorski.elegans.evaluators.ElegansEvaluatorLimitByIterations;
import pl.umk.lgorski.elegans.evaluators.ElegansEvaluatorLimitByPrecision;
import pl.umk.lgorski.utils.Configuration;

/**
 *
 * @author Łukasz
 */
public class EvaluatorFactory {
    public static ElegansEvaluatorBase getElegansEvaluator (String name, Configuration props) {
        name=name.trim();
        switch (name) {
            case "ElegansEvaluatorLimitByIterations":
                return new ElegansEvaluatorLimitByIterations(props);
            case "ElegansEvaluatorLimitByPrecision":
                return new ElegansEvaluatorLimitByPrecision(props);
            default:
                throw new RuntimeException("Unknown evaluator");
        }
    }
    
}
