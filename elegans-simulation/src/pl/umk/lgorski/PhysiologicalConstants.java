/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.umk.lgorski;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Łukasz
 */
public class PhysiologicalConstants {

    private static double cC = PhysiologicalConstants.defaultcC; // (*uF/cm^2 Capacitance of the membrane*)
    private static double gL = PhysiologicalConstants.defaultgL; // (*mS/cm^2 leak conductance*)
    private static double vl = PhysiologicalConstants.defaultvl; //, (*mV leak reversal potential*)
    private static double gCa = PhysiologicalConstants.defaultgCa; //, (*mS/cm^2 Calcium conductance*)
    private static double vCa = PhysiologicalConstants.defaultvCa;// ,(*mV Calcium reversal potential*)
    private static double gKCa = PhysiologicalConstants.defaultgKCa;//, (*mS/cm^2 Potasium-Ca gated conductance*)
    private static double vK = PhysiologicalConstants.defaultvK;//,(*mV Potasium reversal potential*)
    private static double kD = PhysiologicalConstants.defaultkD;//, (*uM Calcium concentration*)
    private static double vCl = PhysiologicalConstants.defaultvCl;//,(*mV Chloride reversal potential*)
    private static double tauCa = PhysiologicalConstants.defaulttauCa;//, (*ms - relaxation time for Ca concentration*)
    private static double far = PhysiologicalConstants.defaultfar;//, (*10 mC/umol = 10 kC/mol Faraday constant*)
    private static double d = PhysiologicalConstants.defaultd;// (*um *) }

    public static final double defaultcC = 1; // (*uF/cm^2 Capacitance of the membrane*)
    public static final double defaultgL = 0.0067; // (*mS/cm^2 leak conductance*)
    public static final double defaultvl = -60; //, (*mV leak reversal potential*)
    public static final double defaultgCa = 0.043; //, (*mS/cm^2 Calcium conductance*)
    public static final double defaultvCa = 120;// ,(*mV Calcium reversal potential*)
    public static final double defaultgKCa = 0.057;
    public static final double defaultvK = -90;//,(*mV Potasium reversal potential*)
    public static final double defaultkD = 30;//, (*uM Calcium concentration*)
    public static final double defaultvCl = -50;//,(*mV Chloride reversal potential*)
    public static final double defaulttauCa = 150;//, (*ms - relaxation time for Ca concentration*)
    public static final double defaultfar = 9.648;//, (*10 mC/umol = 10 kC/mol Faraday constant*)
    public static final double defaultd = 0.5;// (*um *) }

//(*CONNECTIVITY MATRIX*)
//(* Numerical values of synaptic connectivity matrix *)
    public static final double[][] connSW = {{0, 0, 0, 0, 0, 0, 0, 0, 0}, {1.75, 0, 6.75, 15.75, 10.5, 2., 0.25,
        0, 5.}, {2.25, 0.5, 0, 0.25, 0, 0.5, 0, 0, 7.75}, {3., 1., 0.75, 0,
        0.25, 0, 0.25, 0, 3.25}, {0.75, 1., 0.75, 0, 0, 7., 0, 0, 1.25}, {0,
        0, 0, 0, 0, 0, 0, 0.5, 2.}, {0, 41.75, 1.5, 7., 8.25, 1., 0, 0,
        1.}, {0, 2.5, 0.25, 0.25, 0.25, 6.5, 0, 0, 12.}, {0, 7., 0, 0.25,
        0.25, 2., 1.25, 0.25, 0}};

//(* Numerical values of gap junction connectivity matrix *)
    public static final double[][] connGJ = {{0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 25.5, 3.5, 2.5}, {0,
        0, 0, 0, 0, 1., 0.5, 13.75, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0,
        0, 0, 0, 0, 0, 0, 0}, {0, 0, 1., 0, 0, 0, 0, 0.5, 0.5}, {0, 25.5,
        0.5, 0, 0, 0, 0, 0, 0.75}, {0, 3.5, 13.75, 0, 0, 0.5, 0, 0,
        0.75}, {0, 2.5, 0, 0, 0, 0.5, 0.75, 0.75, 0}};

    public static double getcC() {
        return cC;
    }

    public static void setcC(double acC) {
        cC = acC;
    }

    public static double getgL() {
        return gL;
    }

    public static void setgL(double agL) {
        gL = agL;
    }

    public static double getVl() {
        return vl;
    }

    public static void setVl(double aVl) {
        vl = aVl;
    }

    public static double getgCa() {
        return gCa;
    }

    public static void setgCa(double agCa) {
        gCa = agCa;
    }

    public static double getvCa() {
        return vCa;
    }

    public static void setvCa(double avCa) {
        vCa = avCa;
    }

    public static double getgKCa() {
        return gKCa;
    }

    public static void setgKCa(double agKCa) {
        gKCa = agKCa;
    }

    public static double getvK() {
        return vK;
    }

    public static void setvK(double avK) {
        vK = avK;
    }

    public static double getkD() {
        return kD;
    }

    public static void setkD(double akD) {
        kD = akD;
    }

    public static double getvCl() {
        return vCl;
    }

    public static void setvCl(double avCl) {
        vCl = avCl;
    }

    public static double getTauCa() {
        return tauCa;
    }

    public static void setTauCa(double aTauCa) {
        tauCa = aTauCa;
    }

    public static double getFar() {
        return far;
    }

    public static void setFar(double aFar) {
        far = aFar;
    }

    public static double getD() {
        return d;
    }

    public static void setD(double aD) {
        d = aD;
    }

}
