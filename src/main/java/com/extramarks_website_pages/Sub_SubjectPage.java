package com.extramarks_website_pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;





public class Sub_SubjectPage extends BasePage{
	public Sub_SubjectPage(WebDriver dr, ExtentTest t) throws Exception
	{
		super(dr, t);
		PageFactory.initElements(driver, this);
	}
	public List<WebElement> getMainChapter()
	{
		List<WebElement> MainChap = driver.findElements(By.xpath("//*[@id='lptmaincontroller']//div[@class='postlogin-card ng-scope']//div[@ng-click]/a[@href or @ng-click]"));
		return MainChap;
	}
	
	public List<WebElement> getSubChapter()
	{
		List<WebElement> SubChap= driver.findElements(By.xpath("//*[@id='lptmaincontroller']//div[@class='child1 panel-collapse sub-subject collapse ng-scope in']//a[@ng-if]"));
		return SubChap;
	}
	
	public List<WebElement> getPostSubChap()
	{
		List<WebElement> postSub=driver.findElements(By.xpath("//div[contains(@class, 'topic collapse in')]"));
		return postSub;
	}
	@FindBy(xpath = "//*[@id='lptmaincontroller']//a[@ng-click='goBack()']")
	public List<WebElement> BackToChapter;
}
