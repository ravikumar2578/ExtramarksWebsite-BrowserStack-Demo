package com.extramarks_website_pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;



public class SchoolAtHomePage extends BasePage {

	public SchoolAtHomePage(WebDriver dr, ExtentTest t) throws Exception {
		super(dr, t);
		PageFactory.initElements(driver, this);
		// defaultRun();
	}

	public static List<HashMap<String, String>> expectedSessionData = new ArrayList<HashMap<String, String>>();
	@FindBy(xpath = "//*[@class='postlogin-card']//div[@class='col-sm-4 pdl0 mb20 pdmb0 center-box']//a[contains(text(),'Homework')]")
	WebElement HomeWork;
	@FindBy(xpath = "//*[@id='studentdashboardgrid']//*[@class='col-sm-11']//*[@class='row']//*[@class='col-sm-6']//*[@class='postlogin-card']//div[contains(@*,'homework')]/a")
	WebElement StudentHomeWork;
	
	@FindBy(xpath = "//*[@class='postlogin-card']//div[@class='col-sm-4 pdl0 mb20 pdmb0 center-box']//a[contains(text(),'Worksheet')]")
	WebElement Worksheet;
	
	@FindBy(xpath = "//*[@id='studentdashboardgrid']//*[@class='col-sm-11']//*[@class='row']//*[@class='col-sm-6']//*[@class='postlogin-card']//div[contains(@*,'worksheet')]/a")
	WebElement StudentWorksheet;

	@FindBy(xpath = "//*[@class='postlogin-card']//div[@class='col-sm-4 pdl0 mb20 pdmb0 center-box']//a[contains(text(),'Weekly')]")
	WebElement WeeklyTest;
	
	@FindBy(xpath = "//*[@id='studentdashboardgrid']//*[@class='col-sm-11']//*[@class='row']//*[@class='col-sm-6']//*[@class='postlogin-card']//div[contains(@*,'weeklytest')]/a")
	WebElement StudentWeeklyTest;
	
	@FindBy(xpath = "//*[@id='studentdashboardgrid']//*[@class='col-sm-11']//*[@class='row']//*[@class='col-sm-6']//*[@class='postlogin-card']//a[@*[contains(.,'home-session')]]")
	WebElement StudentLiveSessionTest;
	
	public Object createAssignHomework(Hashtable<String, String> data) throws Exception {
		DashBoardPage dashboardPage = new DashBoardPage(driver, test);
		HashMap<String, String> sessionData = new HashMap<String, String>();
		return sessionData;
	}

}
