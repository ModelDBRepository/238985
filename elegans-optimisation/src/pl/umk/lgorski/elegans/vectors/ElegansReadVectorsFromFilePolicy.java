/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.umk.lgorski.elegans.vectors;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.pcj.PCJ;
import pl.umk.lgorski.utils.Configuration;

/**
 *
 * @author Łukasz
 */
public class ElegansReadVectorsFromFilePolicy extends ElegansRandomVectorPolicy {

    String path;

    public ElegansReadVectorsFromFilePolicy(Configuration props) throws IOException, ClassNotFoundException {
        super(props);
        path = props.readProperty("ga.dumper.path", DumpVectorsToFile.dumpingPath);
        readCandidateVectors();
        Arrays.sort(candidateVectors, (a, b) -> Double.compare(a[0], b[0]));
    }

    double[][] candidateVectors;

    protected void readCandidateVectors() throws IOException, ClassNotFoundException {
        String fileName = path + File.separator + PCJ.myId();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            candidateVectors = (double[][]) ois.readObject();
        }

    }

    @Override
    public void initVector(double[] vector, int i) {
        if (i < candidateVectors.length) {
            System.arraycopy(candidateVectors[i], 0, vector, 0, candidateVectors[i].length);
        } else {
            super.initVector(vector, i);
        }
    }

}
