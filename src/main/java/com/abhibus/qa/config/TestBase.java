package com.abhibus.qa.config;

import com.abhibus.qa.pages.AbhibusHomePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static org.openqa.selenium.By.xpath;

public class TestBase extends DriverManager {

  AbhibusHomePage abhibusHomePage;
  Actions builder;

  @BeforeMethod
  public void launchAbhibus() {
    getBrowser();
    setup();
    abhibusHomePage.loadAbhibus();
  }

  public void setup() {
    abhibusHomePage = new AbhibusHomePage();
    builder = new Actions(browser);
    wait = new WebDriverWait(browser, 20);
  }

  @AfterMethod
  public void tearTest() {
    closeAllBrowsers();
  }

  /**
   * explicit wait
   *
   * @parameter Wait for String element and click
   */
  public void waitAndClick(String element) {
    wait.until(ExpectedConditions.visibilityOfElementLocated(xpath(element))).click();
  }

  /**
   * explicit wait
   *
   * @parameter Wait for WebElement and click
   */
  public void waitForElementAndClick(WebElement element) {
    wait.until(ExpectedConditions.visibilityOf(element)).click();
  }

  /**
   * explicit wait
   *
   * @parameter Wait for WebElement to display
   */
  public void waitForElementToDisplay(WebElement element) {
    wait.until(ExpectedConditions.visibilityOf(element));
  }

  public void hoverOnElement(WebElement element) throws InterruptedException {
    builder.moveToElement(element).build().perform();
    Thread.sleep(10000);
  }

  public void moveElementToLeft(WebElement element) throws InterruptedException {
    builder.moveToElement(element).build().perform();
    builder.moveByOffset(0, 100).build().perform();
  Thread.sleep(10000);
  }

}
