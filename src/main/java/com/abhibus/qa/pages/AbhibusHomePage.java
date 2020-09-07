package com.abhibus.qa.pages;

import com.abhibus.qa.config.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.DateFormatter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class AbhibusHomePage extends TestBase {

  final Logger log = LoggerFactory.getLogger(this.getClass());

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

  @FindBy(xpath = "//div[@class='dragon']")
  static WebElement abhiChat;

  @FindBy(xpath = "//div[@class='apsrtc-logo']")
  static WebElement apsrtcLogo;

  @FindBy(xpath = "//h1[text()='Abhi BOT - 24x7 Chat Support']")
  static WebElement chatHoverText;

  @FindBy(xpath = "//button[text()='Later']")
  public WebElement btnLater;

  @FindBy(xpath = "//button[@class='popup-close']")
  public WebElement msgPopup;

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
    try {
     if (msgPopup.isDisplayed()) {
      waitForElementAndClick(msgPopup);
    }
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

  public void selectLeavingDate(String date){
    journeyStartDate.sendKeys(date);
  }

  public void selectLeavingDate(String date, String month, String year) {
    journeyStartDate.click();
    JavascriptExecutor executor = (JavascriptExecutor) browser;
    String selectDateMonthYear =
        "//a[text()='%s']/parent::td[@data-month='%s' and @data-year='%s']";
    WebElement createElement =
        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath(String.format(selectDateMonthYear, date, month, year))));
    executor.executeScript("arguments[0].click();", createElement);
  }

  public void clickSearchButton() {
    btnSearch.click();
  }

  public String verifyChatHoverText() throws InterruptedException {
    hoverOnElement(abhiChat);
    return chatHoverText.getText();
  }

  public String getCurrentDate(String pattern) {
    LocalDateTime date = LocalDateTime.now();
    return date.format(DateTimeFormatter.ofPattern(pattern));
  }

  public String getDesiredDate(String pattern, String desiredTime, int days) {
    LocalDateTime date = LocalDateTime.now();
    if (desiredTime.equals("Past")) {
      date = date.minusDays(days);
      return date.format(DateTimeFormatter.ofPattern(pattern));
    } else {
      date = date.plusDays(days);
      return date.format(DateTimeFormatter.ofPattern(pattern));
    }
    }
}
