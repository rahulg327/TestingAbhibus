package com.abhibus.qa.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

  private static final Logger log = LoggerFactory.getLogger(PropertiesUtil.class);

  public static String getEnvironmentPropertyValue(String propertyName) {
    String propertyValue =
        getValueFromPropertiesFile("properties/" + "environment.properties", propertyName);
    if (propertyValue == null) {
      log.error("property not define");
    }
    return propertyValue;
  }

  public static String getValueFromPropertiesFile(String propFileName, String propKeyName) {
    Properties generateProp = new Properties();
    InputStream filePath = PropertiesUtil.class.getClassLoader().getResourceAsStream(propFileName);
    try {
      generateProp.load(filePath);
    } catch (IOException e) {
      log.error("error occured");
    }
    return generateProp.getProperty(propKeyName);
  }
}
