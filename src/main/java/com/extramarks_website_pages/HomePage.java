package com.extramarks_website_pages;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;

public class HomePage extends BasePage {

	public HomePage(WebDriver dr, ExtentTest t) throws Exception {
		super(dr, t);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "")
	WebElement SignIn;

	@FindBy(xpath = "//*[@id='navbar-main']//*[@class='container']//*[@class='collapse navbar-collapse']//ul[@class='nav navbar-nav navbar-right nav2 mt5']/li")
	public List<WebElement> NavigationBarRightLink;

	public List<WebElement> NavigationBar() {
		List<WebElement> NavigationBarRightLink = driver.findElements(By.xpath(
				"//*[@id='navbar-main']//*[@class='container']//*[@class='collapse navbar-collapse']//ul[@class='nav navbar-nav navbar-right nav2 mt5']/li"));
		// int totalNavigationLink = NavigationBarRightLink.size();
		return NavigationBarRightLink;

	}

	public List<WebElement> K12BoardList() {
		List<WebElement> K12BoardList = driver.findElements(By.xpath(
				"//*[@class='nav navbar-nav navbar-right nav2 mt5']//ul[@class='dropdown-menu school-board-class']//*[@class='board_listing col-xs-4 col-sm-4']/ul/li"));
		// int totalNavigationLink = NavigationBarRightLink.size();
		return K12BoardList;
	}
	public List<WebElement> K12ClassList() {
		List<WebElement> K12ClassList = driver.findElements(By.xpath(
				"//*[@class='nav navbar-nav navbar-right nav2 mt5']//ul[@class='dropdown-menu school-board-class']//*[@class='col-xs-8 col-sm-8 study-class-listing']/div/ul[@class='class-list']/li"));
		// int totalNavigationLink = NavigationBarRightLink.size();
		return K12ClassList;
	}
	public List<WebElement> TestPrepBoardList() {
		List<WebElement> TestPrepBoardList = driver.findElements(By.xpath(
				"//*[@class='nav navbar-nav navbar-right nav2 mt5']//ul[@class='dropdown-menu test-prep']//ul[@class='board-list ']/li"));
		// int totalNavigationLink = NavigationBarRightLink.size();
		return TestPrepBoardList;
	}
	public List<WebElement> TestPrepClassList() {
		List<WebElement> TestPrepClassList = driver.findElements(By.xpath(
				"//*[@class='nav navbar-nav navbar-right nav2 mt5']//ul[@class='dropdown-menu test-prep']//*[@class='col-xs-7 col-sm-7 padd0 test-prep-subitem']/div/ul[contains(@class,'class-list-tp') and not(contains(@class,'hidden'))]/li"));
		// int totalNavigationLink = NavigationBarRightLink.size();
		return TestPrepClassList;
	}
	public Object clickNavigationBarRightLink(String linkName) throws InterruptedException {
		Thread.sleep(3000);
		WebDriverWait wt = new WebDriverWait(driver, 60);
		wt.until(ExpectedConditions.visibilityOfAllElements(NavigationBar()));
		for (WebElement link : NavigationBar()) {
			String name = link.getText().trim();
			if (name.equalsIgnoreCase(linkName)) {
				link.click();
				break;
			}
		}

		return "Pass";

	}
	@FindBy(xpath = "//section//img")
	public
	List<WebElement> ImageTag;
	
	public Object verifyImage(String src) throws IOException	{
		
		URL url=new URL(src);
		HttpURLConnection httpCon=(HttpURLConnection)url.openConnection();
		httpCon.setRequestMethod("HEAD");
		httpCon.connect();
		Integer code=httpCon.getResponseCode();
		return code;
		
	}
	


	public Object clickSignIn() throws InterruptedException {
		try {
			LoginPage lp = new LoginPage(driver, test);
			WebElement signin = driver.findElement(By.xpath("//*[@class='signin']"));
			clickElement(signin, "signin");
			Thread.sleep(1000);
			PageFactory.initElements(driver, lp);
			return lp;
		} catch (Exception e) {
			return this;

		}
	}

}
