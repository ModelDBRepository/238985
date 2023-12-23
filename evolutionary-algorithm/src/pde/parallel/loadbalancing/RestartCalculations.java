package pde.parallel.loadbalancing;

import java.util.Arrays;
import java.util.stream.Stream;
import javafx.util.converter.BooleanStringConverter;
import org.pcj.PCJ;
import pde.evaluators.DifferentialEvaluator;


/* Restarts calculations for faster-running threads */
public class RestartCalculations extends LoadBalancingPolicy {

    public RestartCalculations() {
        Boolean[] arr = new Boolean[PCJ.threadCount()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = false;
        }
        PCJ.putLocal("finished", arr);
        PCJ.barrier();
    }


    int totalIter = 0;

    @Override
    public boolean continueOptimisation(int iterNo, double[][] vectors) {
        totalIter++;
        boolean doContinue = evaluator.continueOptimisation(iterNo, vectors);
        
        if (doContinue == false) {
            for (int nodeId = 0; nodeId < PCJ.threadCount(); nodeId++) {
                PCJ.put(nodeId, "finished", true, PCJ.myId());
            }
        }
        
        Boolean[] finished = PCJ.getLocal("finished");
        boolean allFinished = Arrays.stream(finished).allMatch(b -> b == true);
        

        if (allFinished) {
            PCJ.log("Total iter = " + totalIter);
            return false;
        }
        return true;
    }

    @Override
    public double f(double[] args) {
        return evaluator.f(args);
    }

}
