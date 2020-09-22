package com.extramarks_website_pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class LiveHomeSession extends SchoolAtHomePage {

	public LiveHomeSession(WebDriver dr, ExtentTest t) throws Exception {
		super(dr, t);
		PageFactory.initElements(driver, this);
	}

	public static List<HashMap<String, String>> expectedSessionData = new ArrayList<HashMap<String, String>>();

	@FindBy(xpath = "//*[@id='liveSessionApp']//*[@class='col-sm-11']//*[@class='postlogin-card ng-scope']//ul/li/a")
	List<WebElement> Subject;

	// Student Page

	@FindBy(xpath = "//*[@id='liveSessionApp']//*[@class='col-sm-11']//*[@class='postlogin-card ng-scope']//div[@ng-click]")
	List<WebElement> LiveVideos;

	@FindBy(xpath = "//*[@class='postlogin-card']//*[@class='media em-session-live-card']//a")
	WebElement ViewMore;

	@FindBy(xpath = "//*[@id='liveSessionLogStatusModalMsg']")
	WebElement LiveClassNotStartedMsg;

	@FindBy(xpath = "//*[@id='liveSessionLogStatusModalOK']")
	WebElement LiveClassNotStartedOKbtn;

	public Object verifyLiveHomeSession(Hashtable<String, String> data) throws Exception {
		DashBoardPage dashboardPage = new DashBoardPage(driver, test);
		dashboardPage.clickStudentSchoolAtHomeLink();
		test.log(LogStatus.INFO, "School At Home Page displayed");
		try {
			test.log(LogStatus.INFO, " Verifying Live Session");
			Thread.sleep(1000);
			SchoolAtHomePage schoolathomepage = new SchoolAtHomePage(driver, test);
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.visibilityOf(schoolathomepage.StudentLiveSessionTest));
			Thread.sleep(3000);
			String text = schoolathomepage.StudentLiveSessionTest.getText();
			if (text.contains("View More")) {
				Thread.sleep(3000);

				clickElement(schoolathomepage.StudentLiveSessionTest, "StudentLiveSessionTest");
				Thread.sleep(3000);
				test.log(LogStatus.INFO, "Viewing upcomming Session");
				Reporter.log("Viewing upcomming Session");
				wt.until(ExpectedConditions.visibilityOfAllElements(this.Subject));
				Thread.sleep(3000);
				System.out.println("Number of Subjects:" + this.Subject.size());
				for (WebElement sub : this.Subject) {
					String name = sub.getText();
					test.log(LogStatus.INFO, "Subject is : " + name);
					System.out.println(name);
					sub.click();
					break;

				}

				Thread.sleep(3000);
				System.out.println("Number of Live Videos:" + this.LiveVideos.size());
				for (WebElement videos : this.LiveVideos) {
					String name = videos.getText();
					test.log(LogStatus.INFO, "Videos is : " + name);
					System.out.println(name);
					videos.click();
					break;
				}

				Thread.sleep(3000);

				String upCommingSession = this.LiveClassNotStartedMsg.getText();
				if (upCommingSession.equalsIgnoreCase("Session is not live. Please wait.")) {
					test.log(LogStatus.INFO, "Live Session not Live");
					clickElement(this.LiveClassNotStartedOKbtn, "LiveClassNotStartedOKbtn");

					return new SchoolAtHomePage(driver, test);

				
				
				}else {
				return this;
				
				}
			} else {
				test.log(LogStatus.INFO, "Live Session not found");
				return new SchoolAtHomePage(driver, test);
			}

		} catch (Exception e) {
			return null;
		}
	}

}
