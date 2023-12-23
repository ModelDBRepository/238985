/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.umk.lgorski.elegans;

import pde.parallel.ParallelBaseSequentialMigration;
import pde.parallel.loadbalancing.NoLoadBalancing;
import pde.parallel.loadbalancing.GetSlowestThreadDataAfterFinishing;
import pde.parallel.loadbalancing.ContinueFromSlowest;
import pde.parallel.loadbalancing.LoadBalancingPolicy;
import pde.parallel.loadbalancing.ProportionalWork;
import pde.parallel.loadbalancing.RestartCalculations;
import pl.umk.lgorski.utils.Configuration;

/**
 *
 * @author Łukasz
 */
public class LoadBalancingFactory {

    public static LoadBalancingPolicy getLoadBalancingPolicy(String name, ParallelBaseSequentialMigration pde, Configuration props) {
        name = name.trim();
        switch (name) {
            case "LoadBalancingContinueFromSlowest":
                return new ContinueFromSlowest(pde);
            case "GetSlowestThreadDataAfterFinishing":
                int threshold = props.readProperty("ga.loadBalancingPolicy.threshold", 10);
                return new GetSlowestThreadDataAfterFinishing(pde, threshold);
            case "ProportionalWork":
                return new ProportionalWork();
            case "RestartCalculations":
                return new RestartCalculations();
            default:
                return new NoLoadBalancing(pde);
        }
    }
}
