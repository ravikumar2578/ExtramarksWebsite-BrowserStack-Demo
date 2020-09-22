package com.extramarks_website_pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;


public class Parent_DashBoard extends BasePage 
{
	@FindBy(partialLinkText="Dashboard")
	WebElement Dashboard;
	//@FindBy(xpath="//span[@class='input-group-addon']")
	public Parent_DashBoard(WebDriver driver, ExtentTest test) throws Exception
	{
		super(driver, test);
		PageFactory.initElements(driver, this);
	}
	
	
}
