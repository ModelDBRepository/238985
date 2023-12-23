/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pde.parallel;

import org.pcj.PCJ;

/**
 *
 * @author Łukasz Górski <lgorski@mat.umk.pl>
 */
public class ParallelDelayedNonBlockingMigration extends ParallelBaseSequentialMigration {

    @Override
    public void stepMigrate(int step) {
        if (step % 30 == 0) {
            double[] best = ea.getOptimalVector();
            PCJ.put((PCJ.myId() + 1) % PCJ.threadCount(), "migratedVectors", best, 0);
        }

        double[][] res = PCJ.getLocal("resultVectors");
        if (step > 10 && step % 30 == 10 && Math.random() <= phi) {
            double[] prop = PCJ.getLocal("migratedVectors", 0);
            if (prop != null) {
                int idx = findSwapIndexInArray(res, prop);
                System.arraycopy(prop, 0, res[idx], 0, prop.length);
                PCJ.putLocal("resultVectors", res[idx], idx);
            }
        }

    }
}
