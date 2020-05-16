package com.abhibus.qa.config;

import com.abhibus.qa.utilities.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;

import static com.abhibus.qa.utilities.Constants.CHROME_DRIVER;
import static com.abhibus.qa.utilities.Constants.CHROME_DRIVER_PROPERTY_KEY;

public class DriverManager {

  public static WebDriver browser;
  public static WebDriverWait wait;

  public static WebDriver getBrowser() {
     return createDriver();
  }

  public static String getUrl(String hostName) {
    if (StringUtils.isBlank(hostName))
      throw new IllegalArgumentException("Invalid host name" + hostName);
    return "http://" + hostName + ".com";
  }

  public static WebDriver createDriver() {
    File file = new File(PropertiesUtil.getDriverPropertyValue(CHROME_DRIVER));
    System.setProperty(CHROME_DRIVER_PROPERTY_KEY, file.getPath());
    browser = new ChromeDriver();
    browser.manage().window().maximize();
    browser.manage().deleteAllCookies();
    return browser;
  }

  public static void closeAllBrowsers() {
    browser.quit();
  }
}
