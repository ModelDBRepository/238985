/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.umk.lgorski.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;

/**
 *
 * @author Łukasz
 */
public class Configuration {
    
    private Properties properties;

    public Properties getProperties() {
        return properties;
    }
    public Configuration (String filename) throws IOException {
        properties = new Properties();
        try (InputStream is = new FileInputStream(filename)) {
            if (is != null) {
                properties.load(is);
            }
        }
    }
    
    public static Configuration create (String filename) throws IOException {
        return new Configuration(filename);
    }
    
    public double readProperty (String key, double defaultValue) {
        String ret = properties.getProperty(key, ""+defaultValue);
        return Double.parseDouble(ret);
    }
    
    public int readProperty (String key, int defaultValue) {
        String ret = properties.getProperty(key, ""+defaultValue);
        return Integer.parseInt(ret);
    }
    
    public int readPropertyInteger (String key) {
        String ret = properties.getProperty(key).trim();
        return Integer.parseInt(ret);
    }
    
    public double readPropertyDouble (String key) {
        String ret = properties.getProperty(key);
        return Double.parseDouble(ret);
    }
    
    public String readProperty (String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    public String readProperty (String key) {
        return properties.getProperty(key);
    }
    
    public long readPropertyLong (String key) {
        String ret = properties.getProperty(key);
        return Long.parseLong(ret);
    }
    
    public long readProperty (String key, long defaultValue) {
        String ret = properties.getProperty(key, ""+defaultValue);
        return Long.parseLong(ret);
    }
}
