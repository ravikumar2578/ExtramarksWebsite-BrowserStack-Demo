package com.extramarks_website_testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class BrowserStack extends BrowserStackTestNGTest {
	/*public static final String AUTOMATE_USERNAME = "vikrantsingh9";
	public static final String AUTOMATE_ACCESS_KEY = "D1phjhtszpFxzMPCDMmC";
	public static final String URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY*/
			//+ "@hub-cloud.browserstack.com/wd/hub";
   @Test
	public void OpenBrowser() throws MalformedURLException {
		/*
		 * DesiredCapabilities caps = new DesiredCapabilities();
		 * caps.setCapability("os", "Windows"); //caps.setCapability("os_version",
		 * "10"); caps.setCapability("browser", "Chrome");
		 * caps.setCapability("browser_version", "85"); caps.setCapability("project",
		 * "Login"); caps.setCapability("build", "5.0"); caps.setCapability("name",
		 * "Login Test"); caps.setCapability("browserstack.local", "false");
		 * caps.setCapability("browserstack.networkLogs", "true");
		 * caps.setCapability("browserstack.debug", "true");
		 * caps.setCapability("browserstack.selenium_version", "2.53.0"); WebDriver
		 * driver = new RemoteWebDriver(new URL(URL), caps);
		 */
		driver.get("https://www.extramarks.com/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		String actualURL = driver.getCurrentUrl();
		System.out.println(actualURL);
		/* String expectedURL = "https://www.extramarks.com/";
		 Assert.assertEquals(actualURL, expectedURL,*/
	  //  "Actual and expected are not same");
		
	}

	

}
