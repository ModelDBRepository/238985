/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.umk.lgorski.elegans.vectors;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import static pl.umk.lgorski.elegans.vectors.DumpVectorsToFile.dumpingPath;
import pl.umk.lgorski.utils.Configuration;

/**
 *
 * @author Łukasz
 */
public class DumpVectorsToFileTextual extends DumpVectorsToFile {
   public DumpVectorsToFileTextual (int threadNo, Configuration props) {
        super(threadNo, props);
    }
   
    @Override
    public void finalDataDump(double[][] vectors) {
        File file = new File (path + File.separator + threadNo);
        file.getParentFile().mkdirs();
        
        final int m = vectors.length;
        final int n = vectors[0].length;
        
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println(m + " " + n);
            
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    writer.print(vectors[i][j] + " ");
                }
                writer.println();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } 
    }    
}
