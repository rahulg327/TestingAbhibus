package com.abhibus.qa.utilities;

import com.abhibus.qa.config.DriverManager;
import com.relevantcodes.extentreports.ExtentTest;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class Screenshot {
  private WebDriver driver = DriverManager.getBrowser();
  private ExtentTest extentTest;

  /*  public Screenshot(WebDriver driver) {
    super(driver);
  }*/

  private final int FULLPAGE_WIDTH = 600;
  private final int FULLPAGE_HEIGHT = 300;

  public void takeScreenshot() {
    File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    BufferedImage img = null;
    try {
      img = ImageIO.read(scrFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    OutputStream b64 = new Base64OutputStream(os);
    try {
      ImageIO.write(img, "png", b64);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
