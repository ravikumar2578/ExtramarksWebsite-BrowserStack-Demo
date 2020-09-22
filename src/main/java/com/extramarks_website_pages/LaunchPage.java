package com.extramarks_website_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import com.extramarks_website_utils.Constants;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class LaunchPage {
	static WebDriver driver;
	static ExtentTest test;
	
	public LaunchPage(WebDriver dr, ExtentTest t) throws Exception {
		
	//	super(dr, t);
		
		driver = dr;
		test =t;
	}
	

	public void openpage() throws InterruptedException {
		driver.get(Constants.URL);
		PageFactory.initElements(driver, this);
	}

	/*
	 * public class LaunchPage extends BasePage {
	 * 
	 * public LaunchPage(WebDriver dr,ExtentTest t){ super(dr,t); }
	 */

	public synchronized Object  goToHomePage() throws Exception {
	HomePage hp = new HomePage(driver, test);
		// driver.manage().deleteAllCookies();
		try {
			driver.get(Constants.URL);
			System.out.println(Constants.URL);
			Thread.sleep(2000);
			return hp;
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Unable to enter URL" + e.getMessage());
			System.out.println("Unable to enter URL" + e.getMessage());
			Reporter.log("Unable to enter URL" + e.getMessage());
			//this.takeScreenShot();
			throw new Exception(e.getMessage());
		}

	}

	public SignupPage goToDashboardPage() throws Exception {
		driver.get(Constants.URL);
		SignupPage sp = new SignupPage(driver, test);
		PageFactory.initElements(driver, sp);
		// test.log(LogStatus.INFO, "Reached Emscc_Admin homepage");
		return sp;

	}

}
