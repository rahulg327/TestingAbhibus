package com.abhibus.testcases.abhibusTest;

import com.abhibus.qa.config.DriverManager;
import com.abhibus.qa.pages.AbhibusHomePage;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class TestRunner extends DriverManager {

  AbhibusHomePage abhibusHomePage;

  @BeforeTest
  public void launchAbhibus() {
    getBrowser();
    abhibusHomePage = new AbhibusHomePage();
    wait = new WebDriverWait(browser, 20);
    abhibusHomePage.loadAbhibus();
  }

  @AfterTest
  public void tearTest() {
    closeAllBrowsers();
  }
}
