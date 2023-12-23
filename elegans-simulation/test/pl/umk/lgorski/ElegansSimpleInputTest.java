/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.umk.lgorski;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Łukasz
 */
public class ElegansSimpleInputTest {

    public ElegansSimpleInputTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of run method, of class C_Elegans_Java.
     */
    @Test
    public void testRun() {
        C_Elegans_Java instance = new C_Elegans_Java(1, Utils.TYPICAL_THETA, Utils.TYPICAL_GAMMA),
                instance2 = new C_Elegans_Java(1.5, Utils.TYPICAL_THETA, Utils.TYPICAL_GAMMA);

        double result = instance.run(0.05, 0.03, 0.3, -0.5, 3, 96, 1);

        assertEquals(0.3735, result, 0.0001);

//0 > 0.372599 qs = 0.481311 qe = 0.009894 ashCoeff = 1.160047 tmpTheta = -7 tmpGamma = 1.000000 eta = 4 k = 20 l = 217312
        //double result2 = instance2.run(0.1, 0.005, 0.5, -40, 0.9, 3, 52, 42270);

        //assertEquals(0.8990379960514404, result2, 0.0001);

    }

}
