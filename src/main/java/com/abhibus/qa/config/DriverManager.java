package com.abhibus.qa.config;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;

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
//    File file = new File(PropertiesUtil.getDriverPropertyValue(CHROME_DRIVER));
//    System.setProperty(CHROME_DRIVER_PROPERTY_KEY, file.getPath());
    System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/CHROME/chromedriver.exe");
    ChromeOptions options = new ChromeOptions();
    options.addArguments(Arrays.asList("start-maximized"));
    browser = new ChromeDriver(options);
    browser.manage().window().maximize();
    browser.manage().deleteAllCookies();
    return browser;
  }

  public static void closeAllBrowsers() {
    browser.quit();
  }
}
