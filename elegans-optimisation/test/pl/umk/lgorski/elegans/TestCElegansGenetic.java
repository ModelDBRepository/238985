/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.umk.lgorski.elegans;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Optional;
import org.junit.Test;
import pde.parallel.loadbalancing.NoLoadBalancing;
import pde.parallel.loadbalancing.GetSlowestThreadDataAfterFinishing;
import pde.parallel.loadbalancing.ContinueFromSlowest;
import pde.parallel.loadbalancing.LoadBalancingPolicy;

/**
 *
 * @author Łukasz
 */
public class TestCElegansGenetic {

    private void prepareTestFile(String name, String... lines) throws IOException {
        File file = new File(name);
        PrintStream stream = new PrintStream(file);
        try {
            for (String line : lines) {
                stream.println(line);
            }
        } finally {
            stream.close();
        }
    }

    private void TestLoadBalancingPolicyByClass (Class<? extends LoadBalancingPolicy> clazz, Optional<Integer> param) throws IOException {
        String name = clazz.getName();
        String paramString = "";
        if (param.isPresent()) {
            paramString = param.get().toString();
        }
        prepareTestFile("nodes.txt", "localhost", "localhost", "localhost");
        prepareTestFile("params.txt", "5 5 8 0.8 0.9 0.9 " + name + " " + paramString);
        
        CElegansGenetic.main(new String[]{});
    }
    @Test
    public void TestEmptyLoadBalancingPolicy() throws IOException {
        TestLoadBalancingPolicyByClass(NoLoadBalancing.class, Optional.empty());
    }
    
    @Test
    public void TestGetSlowestThreadDataAfterFinishingLoadBalancingPolicy() throws IOException {
        TestLoadBalancingPolicyByClass(GetSlowestThreadDataAfterFinishing.class, Optional.empty());
    }
    
    @Test
    public void TestContinueFromSlowestLoadBalancingPolicy () throws IOException {
        TestLoadBalancingPolicyByClass(ContinueFromSlowest.class, Optional.of(10));
    }
    
    
}
