package com.abhibus.testcases.pagefactory;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.By.xpath;
public class AbhibusHomeTest {

  public static WebDriver browser;
  public WebDriverWait wait;

  Logger log = LoggerFactory.getLogger(this.getClass());

  @FindBy(xpath = "//a[text()='Modify Search']")
  public WebElement modifySearch;

  @FindBy(xpath = "//img[@alt='abhibus.com']")
  public WebElement homePage;

  @FindBy(xpath = "//input[@id='source']")
  public WebElement leavingFrom;

  @FindBy(id = "destination")
  public WebElement destination;

  @FindBy(id = "journey_date")
  public WebElement journeyStartDate;

  @FindBy(xpath = "//a[text()='Search']")
  public WebElement btnSearch;

  public String suggestions = "//li[text()='%s']";

  @FindBy(xpath = "//button[text()='Later']")
  WebElement btnLater;

  @FindBy(xpath = "//button[@class='popup-close']")
  WebElement msgPopup;

  /*@FindBy(how = How.XPATH, using = "//button[@class='popup-close']")

    static WebElement msgPopup ;
  */
  public AbhibusHomeTest() {
//    this.browser=driver;
    PageFactory.initElements(browser, this);
  }

  public  void  createDriver() {
    System.setProperty(
        "webdriver.chrome.driver", "src/main/resources/drivers/CHROME/chromedriver.exe");
//    ChromeOptions options = new ChromeOptions();
//    options.addArguments(Arrays.asList("start-maximized"));
//    browser = new ChromeDriver(options);
      browser = new ChromeDriver();
    AbhibusHomeTest abhibusHomeTest = new AbhibusHomeTest();
    browser.manage().window().maximize();
    browser.manage().deleteAllCookies();
//        return browser;
  }

  @BeforeTest
  public void launchAbhibus() throws InterruptedException {
    createDriver();
    //    wait = new WebDriverWait(browser, 20);
    loadAbhibus();
  }

  @AfterTest
  public void tearTest() {
    closeAllBrowsers();
  }

  @Test(priority = 1)
  public void verifyAbhibusTitle() {
    String expectedTitle =
        "Bus Ticket Booking | Book Bus Ticket Online - Save Rs. 500 with Abhibus";
    String actualTitle = browser.getTitle();
    Assert.assertEquals(expectedTitle, actualTitle);
  }

  @Test(priority = 2)
  public void verifyBusTabIsDisplayed() {
    Assert.assertTrue(browser.findElement(By.id("pills-home-tab")).isDisplayed());
  }

  @Test(priority = 3)
  public void validateSearch() {
    enterLeaving("Pune");
    enterDestination("Bangalore");
    selectLeavingDate("4", "4", "2020");
    clickSearchButton();
    WebDriverWait wait = new WebDriverWait(browser, 20);

    Assert.assertTrue(wait.until(ExpectedConditions.visibilityOf(modifySearch)).isDisplayed());
  }

  public String getUrl(String hostName) {
    if (StringUtils.isBlank(hostName))
      throw new IllegalArgumentException("Invalid host name" + hostName);
    return "http://" + hostName + ".com";
  }

  public void closeAllBrowsers() {
    browser.quit();
  }

  public void navigateToHomePage() throws InterruptedException {
    homePage.click();
    closePopMessages();
  }

  public void enterLeaving(String value) {
    leavingFrom.sendKeys(value);
    waitAndClick(String.format(suggestions, value));
  }

  public void enterDestination(String value) {
    destination.sendKeys(value);
    waitAndClick(String.format(suggestions, value));
  }

  public void loadAbhibus() throws InterruptedException {
    browser.get(getUrl("abhibus"));
    browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    closePopMessages();
  }

  public void closePopMessages() throws InterruptedException {
    Thread.sleep(5000);
    //    waitForElementToDisplay(msgPopup);
    if (msgPopup.isDisplayed()) {
      //      waitForElementAndClick(msgPopup);
      msgPopup.click();
    }
    try {
      btnLater.isDisplayed();
      waitForElementAndClick(btnLater);
    } catch (Exception e) {
      log.info("later button is clicked");
    }
  }

  public void waitAndClick(String element) {
    JavascriptExecutor executor = (JavascriptExecutor) browser;
    executor.executeScript(
        "arguments[0].click();",
        wait.until(ExpectedConditions.visibilityOfElementLocated(xpath(element))));
  }

  public void waitForElementAndClick(WebElement element) {
    wait.until(ExpectedConditions.visibilityOf(element)).click();
  }

  public void waitForElementToDisplay(WebElement element) {
    wait.until(ExpectedConditions.visibilityOf(element));
  }

  public void selectLeavingDate(String date, String month, String year) {
    journeyStartDate.click();
    JavascriptExecutor executor = (JavascriptExecutor) browser;
    String selectDateMonthYear =
        "//a[text()='%s']/parent::td[@data-month='%s' and @data-year='%s']";
    WebDriverWait wait = new WebDriverWait(browser, 20);

    WebElement createElement =
        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath(String.format(selectDateMonthYear, date, month, year))));
    executor.executeScript("arguments[0].click();", createElement);
  }

  public void clickSearchButton() {
    btnSearch.click();
  }
}
