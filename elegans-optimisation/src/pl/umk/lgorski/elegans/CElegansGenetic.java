package pl.umk.lgorski.elegans;

import pl.umk.lgorski.elegans.evaluators.EvaluatorFactory;
import pde.parallel.EvolutionaryStorage;
import java.io.IOException;
import org.pcj.PCJ;
import pde.DifferentialEvolution;
import pde.EvolutionaryAlgorithm;
import pde.dumper.EmptyDumper;
import pde.dumper.VectorDumper;
import pde.evaluators.DifferentialEvaluator;
import pde.parallel.ParallelBaseSequentialMigration;
import pde.parallel.ParallelDelayedNonBlockingMigration;
import pde.parallel.loadbalancing.NoLoadBalancing;
import pde.parallel.loadbalancing.LoadBalancingPolicy;
import pde.vectorspolicies.VectorInitPolicyInterface;
import pl.umk.lgorski.Utils;
import pl.umk.lgorski.Utils.Distance;
import pl.umk.lgorski.elegans.evaluators.ElegansEvaluatorBase;
import pl.umk.lgorski.elegans.evaluators.ElegansEvaluatorLimitByIterations;
import pl.umk.lgorski.elegans.vectors.ElegansRandomVectorPolicy;
import pl.umk.lgorski.elegans.vectors.ElegansVectorPolicyFactory;
import pl.umk.lgorski.elegans.vectors.VectorDumperFactory;
import pl.umk.lgorski.utils.Configuration;

class ElegansSolver extends ParallelBaseSequentialMigration {

    @Override
    public void showResult() {
        if (PCJ.myId() == 0) {
            double[] theBest = PCJ.getLocal("theBest");
            PCJ.log(String.format("distance = %f qs = %f qe = %f ashCoeff = %f fASH = %f input = %f eta = %d k = %d l = %d",
                    theBest[0],
                    theBest[1], theBest[2], theBest[3], theBest[4], theBest[5],
                    Math.round(theBest[6]), Math.round(theBest[7]),
                    Math.round(theBest[8])));

        }
    }

    private Configuration readPropertyFile() throws IOException {
        Configuration props = Configuration.create("config.properties");
        return props;
    }

    int logVectorsValue = 0;

    @Override
    public void performInit() {
        try {
            Configuration props = readPropertyFile();

            int iterations = props.readPropertyInteger("ga.I");
            int M = props.readPropertyInteger("ga.M");
            int N = props.readPropertyInteger("ga.N");
            double C = props.readPropertyDouble("ga.C");
            double F = props.readPropertyDouble("ga.F");
            double phi = props.readPropertyDouble("ga.phi");

            logVectorsValue = props.readProperty("ga.logVectorsValue", logVectorsValue);

            String loadBalancingPolicyName = props.readProperty("ga.loadBalancingPolicy", NoLoadBalancing.class.getSimpleName());
            LoadBalancingPolicy loadBalancing = LoadBalancingFactory.getLoadBalancingPolicy(loadBalancingPolicyName, this, props);

            String evaluatorName = props.readProperty("ga.elegans.evaluator", ElegansEvaluatorLimitByIterations.class.getSimpleName());

            String distanceFunctionName = props.readProperty("ga.elegans.distance", "EuclideanDistance");
            Distance distance = DistanceFunctionFactory.getDistanceFunction(distanceFunctionName);
            ElegansEvaluatorBase elegansEvaluator = EvaluatorFactory.getElegansEvaluator(evaluatorName, props);
            elegansEvaluator.getElegans().setDistance(distance);
            
            loadBalancing.setDifferentialEvaluator(elegansEvaluator);
            DifferentialEvaluator evaluator = loadBalancing;

            String vectorPolicyName = props.readProperty("ga.elegans.vectors", ElegansRandomVectorPolicy.class.getSimpleName());
            VectorInitPolicyInterface vectors = ElegansVectorPolicyFactory.getElegansVectorPolicy(vectorPolicyName, props, elegansEvaluator.getElegans().getVarL());

            String vectorDumperName = props.readProperty("ga.dumper", EmptyDumper.class.getSimpleName());
            VectorDumper dumper = VectorDumperFactory.getVectorDumper(vectorDumperName, PCJ.myId(), props);

            this.setPhi(phi);
            EvolutionaryAlgorithm de = new DifferentialEvolution(M, N, C, F, vectors, evaluator);
            de.setDumper(dumper);
            this.configureWithEvolutionaryAlgorithm(de);

        } catch (IOException ex) {
            PCJ.log("Critical configuration file IO error");
        }

    }

    @Override
    public void stepMigrate(int step) {
        super.stepMigrate(step);

        if (logVectorsValue > 0 && step % logVectorsValue == 0) {
            PCJ.log("distance = " + this.getEa().getOptimalVector()[0] + " step = " + step);
        }
    }
}

public class CElegansGenetic {

    public static void main(String[] args) {
        String filename = "nodes.txt";
        if (args.length > 0) {
            filename = args[0];
        }
        PCJ.start(ElegansSolver.class, EvolutionaryStorage.class, filename);
    }

}
