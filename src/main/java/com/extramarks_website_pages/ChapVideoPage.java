package com.extramarks_website_pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import com.relevantcodes.extentreports.ExtentTest;



public class ChapVideoPage extends BasePage 
{
	@FindBy(xpath="//button[text()='NEXT']")
	WebElement NextBtn;
	
	@FindBy(xpath="//button[@class='component_base prev']")
	WebElement PrevBtn;
	
	@FindBy(xpath="//button[@class='component_base std outline']")
	WebElement outline;
	
	@FindAll(@FindBy(xpath="//div[@class='outline']//div[@class='thumb']"))
	List<WebElement> outlineDiv;
	
	
	/*public ChapVideoPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}*/
	public ChapVideoPage(WebDriver dr, ExtentTest t) throws Exception {
		super(dr, t);
		// TODO Auto-generated constructor stub
	}
	
	public WebElement getNextBtn()
	{
		return NextBtn;
	}
	public WebElement getPrevBtn()
	{
		return PrevBtn;
	}
	
	public WebElement getOutline()
	{
		return outline;
	}
	
	public List<WebElement> getoutlineDiv()
	{
		return outlineDiv;
	}

}
