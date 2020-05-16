package com.abhibus.testcases.abhibusTest;

import com.abhibus.qa.pages.AbhibusHomePage;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class AbhibusHomeTest extends AbhibusHomePage {

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
    selectLeavingDate("20", "5", "2020");
    clickSearchButton();
    WebDriverWait wait = new WebDriverWait(browser, 20);

    Assert.assertTrue(wait.until(ExpectedConditions.visibilityOf(modifySearch)).isDisplayed());
  }
}
