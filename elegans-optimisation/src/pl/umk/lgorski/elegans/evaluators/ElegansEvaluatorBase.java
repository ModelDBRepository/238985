/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.umk.lgorski.elegans.evaluators;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import pde.evaluators.DifferentialEvaluator;
import pl.umk.lgorski.C_Elegans_Java;
import static pl.umk.lgorski.PhysiologicalConstants.defaultcC;
import static pl.umk.lgorski.PhysiologicalConstants.defaultd;
import static pl.umk.lgorski.PhysiologicalConstants.defaultfar;
import static pl.umk.lgorski.PhysiologicalConstants.defaultgCa;
import static pl.umk.lgorski.PhysiologicalConstants.defaultgKCa;
import static pl.umk.lgorski.PhysiologicalConstants.defaultgL;
import static pl.umk.lgorski.PhysiologicalConstants.defaultkD;
import static pl.umk.lgorski.PhysiologicalConstants.defaulttauCa;
import static pl.umk.lgorski.PhysiologicalConstants.defaultvCa;
import static pl.umk.lgorski.PhysiologicalConstants.defaultvCl;
import static pl.umk.lgorski.PhysiologicalConstants.defaultvK;
import static pl.umk.lgorski.PhysiologicalConstants.defaultvl;
import static pl.umk.lgorski.PhysiologicalConstants.setD;
import static pl.umk.lgorski.PhysiologicalConstants.setFar;
import static pl.umk.lgorski.PhysiologicalConstants.setTauCa;
import static pl.umk.lgorski.PhysiologicalConstants.setVl;
import static pl.umk.lgorski.PhysiologicalConstants.setcC;
import static pl.umk.lgorski.PhysiologicalConstants.setgCa;
import static pl.umk.lgorski.PhysiologicalConstants.setgKCa;
import static pl.umk.lgorski.PhysiologicalConstants.setgL;
import static pl.umk.lgorski.PhysiologicalConstants.setkD;
import static pl.umk.lgorski.PhysiologicalConstants.setvCa;
import static pl.umk.lgorski.PhysiologicalConstants.setvCl;
import static pl.umk.lgorski.PhysiologicalConstants.setvK;
import pl.umk.lgorski.utils.Configuration;

/**
 *
 * @author Łukasz
 */
public abstract class ElegansEvaluatorBase implements DifferentialEvaluator {

    private final C_Elegans_Java elegans;

    public C_Elegans_Java getElegans() {
        return elegans;
    }

    private void readPhysiologicalProperties(Configuration props) {

        setcC(props.readProperty("elegans.cC", defaultcC));
        setgL(props.readProperty("elegans.gL", defaultgL));
        setVl(props.readProperty("elegans.vl", defaultvl));
        setgCa(props.readProperty("elegans.gCa", defaultgCa));
        setvCa(props.readProperty("elegans.vCa", defaultvCa));
        setgKCa(props.readProperty("elegans.gKCa", defaultgKCa));
        setvK(props.readProperty("elegans.vK", defaultvK));
        setkD(props.readProperty("elegans.kD", defaultkD));
        setvCl(props.readProperty("elegans.vCl", defaultvCl));
        setTauCa(props.readProperty("elegans.tauCa", defaulttauCa));
        setFar(props.readProperty("elegans.far", defaultfar));
        setD(props.readProperty("elegans.d", defaultd));

    }

    public ElegansEvaluatorBase(Configuration props) {
        double progPolaczen = 1.5, progWariacji = 1.5;

        readPhysiologicalProperties(props);
        double[] gamma = readGammaOrTheta(props, "gamma");
        double[] theta = readGammaOrTheta(props, "theta");
        progPolaczen = props.readProperty("elegans.connThreshold", progPolaczen);
        progWariacji = props.readProperty("elegans.varThreshold", progWariacji);

        elegans = new C_Elegans_Java(progPolaczen, progWariacji, theta, gamma);
    }

    @Override
    public double f(double[] args) {
        double res;
        long start = System.nanoTime();
        res = elegans.run(args[1], args[2], args[3], args[4], args[5], (int) args[6], (int) args[7], (long) args[8]);
        long stop = System.nanoTime();
        return res;

    }

    private double[] readGammaOrTheta(Configuration props, String name) {
        double[] ret = new double[9];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = props.readProperty("elegans." + name + "." + (i + 1), -90.0);
        }
        return ret;
    }

}
