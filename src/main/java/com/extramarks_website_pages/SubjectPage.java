package com.extramarks_website_pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;





public class SubjectPage extends BasePage
{
	public SubjectPage(WebDriver dr, ExtentTest t) throws Exception
	{
		super(dr, t);
		PageFactory.initElements(driver, this);
	}
	
	
	public int getTotalTerm() {
		List<WebElement> TermLinks = driver
				.findElements(By.xpath("//*[@class='postlogin-card ng-scope']//a[contains(@href,'term')]"));
		int links = TermLinks.size();
		// System.out.println("Total Subjects: "+links);
		return links;

	}
	public List<WebElement> getTotalTermLinks(){
		List<WebElement> SubSubjectsLinks = driver
				.findElements(By.xpath("//*[@class='postlogin-card ng-scope']//a[contains(@href,'term')]"));
		return SubSubjectsLinks;
		
	}
	public int getTotalSubSubj() {
		List<WebElement> SubSubjectLinks = driver
				.findElements(By.xpath("//*[@class='postlogin-card ng-scope']//a[contains(@href,'sub_subject')]"));
		int links = SubSubjectLinks.size();
		// System.out.println("Total Subjects: "+links);
		return links;

	}
	
	public List<WebElement> getSubSubjectLinks() {
		List<WebElement> SubSubjectsLinks = driver
				.findElements(By.xpath("//*[@class='postlogin-card ng-scope']//a[contains(@href,'sub_subject')]"));
		return SubSubjectsLinks;

	}
	@FindBy(xpath="//a[contains(@href,'/chapter') and not(contains(@href,'lpt'))]")
	public	List<WebElement> MainChap;
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
