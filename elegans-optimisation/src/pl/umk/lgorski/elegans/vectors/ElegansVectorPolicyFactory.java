/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.umk.lgorski.elegans.vectors;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pde.vectorspolicies.VectorInitPolicyInterface;
import pl.umk.lgorski.utils.Configuration;

/**
 *
 * @author Łukasz
 */
public class ElegansVectorPolicyFactory {

    public static VectorInitPolicyInterface getElegansVectorPolicy(String name, Configuration props, long maxL) {

        name = name.trim();

        props.getProperties().setProperty("elegans.l.min", "" + 1);
        props.getProperties().setProperty("elegans.l.max", "" + maxL);

        switch (name) {
            case "ElegansRandomVectorPolicy":
                return new ElegansRandomVectorPolicy(props);
            case "ElegansReadVectorsFromFilePolicy": {
                try {
                    return new ElegansReadVectorsFromFilePolicy(props);
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
            }
            case "ElegansReadVectorsFromTextualFilePolicy":
                try {
                    return new ElegansReadVectorsFromTextualFilePolicy(props);
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
            default:
                throw new RuntimeException("Unknown vector policy");
        }
    }

}
