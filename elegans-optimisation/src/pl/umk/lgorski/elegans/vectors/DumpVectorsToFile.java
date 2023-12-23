/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.umk.lgorski.elegans.vectors;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import pde.dumper.VectorDumper;
import pl.umk.lgorski.utils.Configuration;

/**
 *
 * @author Łukasz
 */
public class DumpVectorsToFile implements VectorDumper {

    public static final String dumpingPath = "vectors";
    protected int stepLimit=10;
    protected int threadNo;
    protected String path;
    
    public DumpVectorsToFile (int threadNo, Configuration props) {
        this.threadNo = threadNo;
        path = props.readProperty("ga.dumper.path", dumpingPath);
        stepLimit = props.readProperty("ga.dumper.step", stepLimit);
    }
    
    @Override
    public void dumpVectors(double[][] vectors, int step) {
        
        if (step % stepLimit == 0) {
            finalDataDump(vectors);
        }
        
    }

    @Override
    public void finalDataDump(double[][] vectors) {
        File file = new File (path + File.separator + threadNo);
        file.getParentFile().mkdirs();
        
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(vectors);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } 
    }
    
}
