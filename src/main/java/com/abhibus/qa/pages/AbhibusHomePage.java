package com.abhibus.qa.pages;

import com.abhibus.qa.config.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class AbhibusHomePage extends TestBase {

  final Logger log = LoggerFactory.getLogger(this.getClass());

  @FindBy(xpath = "//a[text()='Modify Search']")
  public WebElement modifySearch;

  @FindBy(xpath = "//img[@alt='abhibus.com']")
  static WebElement homePage;

  @FindBy(xpath = "//input[@id='source']")
  static WebElement leavingFrom;

  @FindBy(id = "destination")
  static WebElement destination;

  @FindBy(xpath = "//input[@name='journey_date']")
  static WebElement journeyStartDate;

  @FindBy(xpath = "//a[text()='Search']")
  static WebElement btnSearch;

  @FindBy(xpath = "//button[text()='Later']")
  private WebElement btnLater;

  @FindBy(xpath = "//button[@class='popup-close']")
  private WebElement msgPopup;

  private String suggestions = "//li[text()='%s']";

  public AbhibusHomePage() {
    PageFactory.initElements(browser, this);
  }

  public void loadAbhibus() {
    browser.get(getUrl("abhibus"));
    browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    closePopMessages();
  }

  public void closePopMessages() {
    waitForElementToDisplay(msgPopup);
    if (msgPopup.isDisplayed()) {
      waitForElementAndClick(msgPopup);
    }
    try {
      btnLater.isDisplayed();
      waitForElementAndClick(btnLater);
    } catch (Exception e) {
      log.info("later button is clicked");
    }
  }

  public void navigateToHomePage() {
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

  public void selectLeavingDate(String date, String month, String year) {
    journeyStartDate.click();
    JavascriptExecutor executor = (JavascriptExecutor) browser;
    String selectDateMonthYear =
        "//a[text()='%s']/parent::td[@data-month='%s' and @data-year='%s']";
//    wait = new WebDriverWait(browser, 20);

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
