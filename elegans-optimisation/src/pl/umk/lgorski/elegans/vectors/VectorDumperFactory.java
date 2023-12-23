/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.umk.lgorski.elegans.vectors;

import pde.dumper.EmptyDumper;
import pde.dumper.VectorDumper;
import pl.umk.lgorski.utils.Configuration;

/**
 *
 * @author Łukasz
 */
public class VectorDumperFactory {
    public static VectorDumper getVectorDumper (String name, int threadNo, Configuration props) {
        
        name = name.trim();
        
        switch (name) {
            case "EmptyDumper":
                return new EmptyDumper();
            case "DumpVectorsToFile":
                return new DumpVectorsToFile(threadNo, props);
            case "DumpVectorsToFileTextual":
                return new DumpVectorsToFileTextual (threadNo, props);
            default:
                throw new RuntimeException("Unknown dumper.");
        }
    }
}
