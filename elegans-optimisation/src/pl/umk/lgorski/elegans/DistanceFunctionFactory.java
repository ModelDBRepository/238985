/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.umk.lgorski.elegans;

import pl.umk.lgorski.Utils;

/**
 *
 * @author Łukasz
 */
public class DistanceFunctionFactory {

    static Utils.Distance getDistanceFunction(String distanceFunctionName) {
        switch (distanceFunctionName) {
            case "EuclideanDistance":
                return Utils::EuclideanDistance;
            case "MahalanobisDistance":
                return Utils::MahalanobisDistance;
            default:
                throw new RuntimeException("Unknown distance function");
        }
    }
    
}
