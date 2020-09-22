package com.extramarks_website_pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;





public class SubscriptionPage extends BasePage {

	public SubscriptionPage(WebDriver dr, ExtentTest t) throws Exception {
		super(dr, t);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//*[@class='thumbnail profile-card text-center']")
	public List<WebElement> packageDetail;
	
	@FindBy(xpath = "//*[@class='thumbnail profile-card text-center']//h4")
	public WebElement packageName;
	
	@FindBy(xpath = "//*[@class='thumbnail profile-card text-center']//p[4]")
	public WebElement packageValidity;
}
