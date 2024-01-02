package com.parexel.automation.commonUtils;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigReader {
    Logger logger = LoggerHelper.getLogger(ConfigReader.class);
    static String  path="Configuration.properties";

    /*****************************************************************************************************
     *
     * @param 			:property  specific key of the file
     * @return  		:String  value of the specific key property
     * @description 	:This method will set path to global path variable in Helper class
     *
     ****************************************************************************************************/
    public static String loadProperties(String property) {
        Properties prop = new Properties();
        InputStream input;
        try {
            input = new FileInputStream(path);

            // loads a properties file

            prop.load(input);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return prop.getProperty(property);
    }

    public static Map<String, String> readPropertiesFile() {
        Properties properties = new Properties();
        Map<String, String> propertyMap = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(path)) {
            properties.load(fis);

            // Convert the Properties object to a Map
            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key);
                propertyMap.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return propertyMap;
    }
}
