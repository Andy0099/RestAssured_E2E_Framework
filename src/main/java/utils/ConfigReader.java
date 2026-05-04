package utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {
	public static String getProperty(String key) {
        Properties prop = new Properties();
        try {
            // This points to the file we created in Step 1
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            prop.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prop.getProperty(key);
	}
}
