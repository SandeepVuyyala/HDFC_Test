package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ObjectReader {

    private Properties properties;

    public ObjectReader() {
        properties = new Properties();
        FileInputStream fis = null;
        try {
            // Path matches the structure shown in your project tree
            fis = new FileInputStream("ObjectRepository/Object.properties");
            properties.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public String getObjectValue(String key) {
        return properties.getProperty(key);
    }

    public String geturl() {
        return properties.getProperty("BaseUrl");
    }
}