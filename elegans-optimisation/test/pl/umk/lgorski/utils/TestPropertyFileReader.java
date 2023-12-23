/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.umk.lgorski.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 *
 * @author Łukasz
 */
public class TestPropertyFileReader {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    
    @Test
    public void testOpeningFile () throws IOException {
        File config = folder.newFile("config.properties");
        try (PrintWriter writer = new PrintWriter(config)) {
            writer.println("double1.test=17");
            writer.println("double2:8");
        }
        Configuration props = Configuration.create(config.getPath());
        
        double res = props.readProperty("double1.test", 8);
        Assert.assertEquals(17, res, 0.1);
        res = props.readProperty("double2", 10);
        Assert.assertEquals(8, res, 0.1);
        res = props.readProperty("double1.test2", 42);
        Assert.assertEquals(42, res, 0.1);
    }
}
