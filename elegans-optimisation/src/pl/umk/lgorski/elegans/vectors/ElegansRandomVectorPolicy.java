/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.umk.lgorski.elegans.vectors;

import java.util.Random;
import org.pcj.PCJ;
import pde.vectorspolicies.VectorInitPolicyInterface;
import pl.umk.lgorski.utils.Configuration;

/**
 *
 * @author Łukasz
 */
public class ElegansRandomVectorPolicy implements VectorInitPolicyInterface {

    public static double QS_MIN = 0, QS_MAX = 0.5,
            QE_MIN = 0, QE_MAX = 0.5,
            ASH_COEFF_MIN = 0, ASH_COEFF_MAX = 2,
            FASH_MIN = -1, FASH_MAX = 1,
            INPUT_MIN = 0, INPUT_MAX = 5;

    public static int ETA_MIN = 1, ETA_MAX = 10,
            K_MIN = 1, K_MAX = 128;

    public static long L_MIN = 1, L_MAX = 8796093022208L;

    Random random = new Random();

    public ElegansRandomVectorPolicy(Configuration props) {
        
        QS_MIN = props.readProperty("elegans.qs.min", QS_MIN);
        QS_MAX = props.readProperty("elegans.qs.max", QS_MAX);
        
        QE_MIN = props.readProperty("elegans.qe.min", QE_MIN);
        QE_MAX = props.readProperty("elegans.qe.max", QE_MAX);
        
        ASH_COEFF_MIN = props.readProperty("elegans.ashCoeff.min", ASH_COEFF_MIN);
        ASH_COEFF_MAX = props.readProperty("elegans.ashCoeff.max", ASH_COEFF_MAX);
        
        FASH_MIN = props.readProperty("elegans.fash.min", FASH_MIN);
        FASH_MAX = props.readProperty("elegans.fash.max", FASH_MAX);
        
        ETA_MIN = props.readProperty("elegans.eta.min", ETA_MIN);
        ETA_MAX = props.readProperty("elegans.eta.max", ETA_MAX);
        
        K_MIN = props.readProperty("elegans.k.min", K_MIN);
        K_MAX = props.readProperty("elegans.k.max", K_MAX);
        
        INPUT_MIN = props.readProperty("elegans.input.min", INPUT_MIN);
        INPUT_MAX = props.readProperty("elegans.input.max", INPUT_MAX);
        
        L_MIN = props.readProperty("elegans.l.min", L_MIN);
        L_MAX = props.readProperty("elegans.l.max", L_MAX);
    }
    private double randDouble(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    private long randLong(long min, long max) {
        return min + ((long) (random.nextDouble() * (max + 1 - min)));
    }

    @Override
    public void initVector(double[] vector, int i) {
        vector[1] = randDouble(QS_MIN, QS_MAX);
        vector[2] = randDouble(QE_MIN, QE_MAX);
        vector[3] = randDouble(ASH_COEFF_MIN, ASH_COEFF_MAX);
        vector[4] = randDouble(FASH_MIN, FASH_MAX);
        vector[5] = randDouble (INPUT_MIN, INPUT_MAX);
        vector[6] = randLong(ETA_MIN, ETA_MAX);
        vector[7] = randLong(K_MIN, K_MAX);
        vector[8] = randLong(L_MIN, L_MAX);
    }

    @Override
    public void boundVector(double[] vector) {
        vector[1] = Math.min(vector[1], QS_MAX);
        vector[1] = Math.max(vector[1], QS_MIN);

        vector[2] = Math.min(vector[2], QE_MAX);
        vector[2] = Math.max(vector[2], QE_MIN);

        vector[3] = Math.min(vector[3], ASH_COEFF_MAX);
        vector[3] = Math.max(vector[3], ASH_COEFF_MIN);

        vector[4] = Math.min(vector[4], FASH_MAX);
        vector[4] = Math.max(vector[4], FASH_MIN);

        vector[5] = Math.min(vector[5], INPUT_MAX);
        vector[5] = Math.max(vector[5], INPUT_MIN);
        
        vector[6] = Math.min(Math.round(vector[6]), ETA_MAX);
        vector[6] = Math.max(vector[6], ETA_MIN);

        vector[7] = Math.min(Math.round(vector[7]), K_MAX);
        vector[7] = Math.max(vector[7], K_MIN);

        vector[8] = Math.min(Math.round(vector[8]), L_MAX);
        vector[8] = Math.max(vector[8], L_MIN);
    }

}
