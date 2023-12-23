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
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import org.pcj.PCJ;
import pl.umk.lgorski.utils.Configuration;

/**
 *
 * @author Łukasz
 */
public class ElegansReadVectorsFromTextualFilePolicy extends ElegansReadVectorsFromFilePolicy {

    public ElegansReadVectorsFromTextualFilePolicy(Configuration props) throws IOException, ClassNotFoundException {
        super(props);
    }
    
    protected void readCandidateVectors() throws IOException, ClassNotFoundException {
        String fileName = path + File.separator + PCJ.myId();
        try (Scanner in = new Scanner (new File(fileName)).useLocale(Locale.US)) {
            final int m = in.nextInt(), n = in.nextInt();
            candidateVectors = new double[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    candidateVectors[i][j] = in.nextDouble();
                }
            }
        }

        
    }
}
