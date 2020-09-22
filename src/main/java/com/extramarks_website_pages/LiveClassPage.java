package com.extramarks_website_pages;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.extramarks_website_utils.DataUtil;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class LiveClassPage extends GoToSchoolPage {

	public LiveClassPage(WebDriver dr, ExtentTest t) throws Exception {
		super(dr, t);
		PageFactory.initElements(driver, this);
	}

	public static List<HashMap<String, String>> expectedSessionData = new ArrayList<HashMap<String, String>>();
	int row = 0;
	@FindBy(xpath = "//*[@class='postlogin-card']//div[@class='col-sm-4 pdl0 mb20 pdmb0 center-box']//a[contains(text(),'Schedule Live Class')]")
	WebElement ScheduleLiveClass;
	@FindBy(xpath = "//*[@class='container videouploader']//*[@class='row']//select[@id='wiziqsessionType']")
	List<WebElement> SessionSelect;
	@FindBy(xpath = "//*[@class='middleSection']//*[@class='row']//*[@class='col-xs-12 col-sm-3 col-md-3 mb20']//select[@class='form-control selectBoard']")
	WebElement BoardSelect;

	@FindBy(xpath = "//*[@class='middleSection']//*[@class='row']//*[@class='dropdownselect classes']//select[@class='form-control syllabus classDropDown']")
	List<WebElement> ClassSelect;

	@FindBy(xpath = "//*[@class='middleSection']//*[@class='row']//*[@class='col-xs-12 col-sm-3 col-md-3 syllabus_container mb20']//select[@class='form-control syllabus ']")
	List<WebElement> SubjectSelect;
	@FindBy(xpath = "//*[@class='container videouploader']//*[@class='row']//*[@class='col-xs-12 col-sm-3 col-md-3 mb20']//button[@class='multiselect dropdown-toggle btnmultiple btn-default']")
	WebElement SectionSelect;

	@FindBy(id = "date_timepicker")
	WebElement Date;

	@FindBy(xpath = "//*[@class='xdsoft_datetimepicker xdsoft_noselect ' and contains(@style,'block')]//*[@class='xdsoft_time_box xdsoft_scroller_box']/div/div")
	List<WebElement> TimeSelectByOption;
	@FindBy(xpath = "//*[@class='xdsoft_datetimepicker xdsoft_noselect ']//div[@class='xdsoft_calendar']//table//td[contains(@class,'today')]/div")
	WebElement DateSelect;

	@FindBy(xpath = "//*[@class='xdsoft_time_box xdsoft_scroller_box']//*[@class='xdsoft_time xdsoft_current' or @class='xdsoft_time xdsoft_current xdsoft_today']")
	WebElement TimeSelect;

	@FindBy(id = "lecture_title")
	WebElement LectureTitle;

	@FindBy(id = "duration")
	WebElement Duration;

	@FindBy(xpath = "//*[@class='row']//input[@type='submit']")
	WebElement Submit;

	@FindBy(xpath = "//*[@class='col-xs-12 col-sm-12 col-md-12 mt20']")
	WebElement ViewRecords;

	// Student Page

	@FindBy(xpath = "//*[@class='postlogin-card']//div[@class='media em-session-live-card']")
	WebElement UpcommingClass;

	@FindBy(xpath = "//*[@class='postlogin-card']//*[@class='media em-session-live-card']//a")
	WebElement ViewMore;

	@FindBy(xpath = "//*[@id='liveSessionApp']//*[@class='col-sm-11']//*[@class='postlogin-card ng-scope']//*[@class='row clearfix ng-scope']//*[@class='col-sm-6 mb20 ng-scope']//div[@ng-click]")
	List<WebElement> LiveClasses;

	@FindBy(xpath = "//*[@class='col-sm-11 col-sm-offset-1']//*[@class='row']//*[@class='postlogin-card']//*[@class='media em-session-live-card']//img[@id='livesessionredirect']")
	WebElement LiveClass;

	@FindBy(xpath = "//*[@class='i-vc-joinpage-wrapper']//*[@class='i-vc-joinpage-ctrls']//button[@class='md-button md-primary md-raised md-button md-ink-ripple']")
	WebElement JoinClass;

	public Object goToCreateSessionPage(Hashtable<String, String> data) throws Exception {
		DashBoardPage dashboardPage = new DashBoardPage(driver, test);
		try {
			Thread.sleep(3000);
			String parentWindow = driver.getWindowHandle();
			clickElement(this.ScheduleLiveClass, "ScheduleLiveClass");
			Thread.sleep(3000);
			Set<String> windows = driver.getWindowHandles();
			for (String win : windows) {
				if (win != parentWindow) {
					driver.switchTo().window(win);
				}
			}
			return new LiveClassPage(driver, test);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	public static HashMap<String, String> sessionData = new HashMap<String, String>();

	public Object selectSessionType(String sessionType) throws Exception {
		DashBoardPage dashboardPage = new DashBoardPage(driver, test);
		try {
			Thread.sleep(3000);
			test.log(LogStatus.INFO, "Selecting Session Live Lecture");
			Reporter.log("Selecting Session Live Lecture");
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.visibilityOfAllElements(this.SessionSelect));
			Thread.sleep(3000);
			List<WebElement> SessionElements = driver.findElements(By.xpath(
					"//*[@class='container videouploader']//*[@class='row']//select[@id='wiziqsessionType']/option"));
			System.out.println("Number of Session:" + (SessionElements.size() - 1));
			int counter = 0;
			for (WebElement Session : SessionElements) {
				String name = Session.getText();

				if (name.equalsIgnoreCase(sessionType)) {
					System.out.println(name);
					Session.click();
					break;
				} else {
					if (counter == SessionElements.size()) {
						test.log(LogStatus.INFO,
								"Session Type: " + sessionType + " not found in dropdownlist >>selecting random board");
						Reporter.log("Board : " + sessionType + " not found in dropdownlist >>selecting random board");
						System.out.println(
								"Board : " + sessionType + " not found in dropdownlist >>selecting random board");
						Session.click();
					}
				}
			}
			// Select selectSession = new Select(this.SessionSelect.get(0));
			// selectSession.selectByVisibleText("Live Lecture");
			return true;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public Object selectBoard(String board, String schoolName) throws Exception {
		DashBoardPage dashboardPage = new DashBoardPage(driver, test);
		Thread.sleep(3000);
		sessionData.put("school_name", schoolName);
		test.log(LogStatus.INFO, "Selecting Board" + board);
		Reporter.log("Selecting Board");
		List<WebElement> BoardElements = driver.findElements(By.xpath(
				"//*[@class='container videouploader']//*[@class='row']//*[@class='col-xs-12 col-sm-3 col-md-3 mb20']//select[@class='form-control selectBoard']/option"));
		System.out.println("Number of Board:" + (BoardElements.size() - 1));
		int counter = 0;
		for (WebElement Board : BoardElements) {
			counter++;
			String name = Board.getText();
			if (name.equalsIgnoreCase(board)) {
				System.out.println(name);
				Board.click();
				break;
			} else {
				if (counter == BoardElements.size()) {
					test.log(LogStatus.INFO,
							"Board : " + board + " not found in dropdownlist >>selecting random board");
					Reporter.log("Board : " + board + " not found in dropdownlist >>selecting random board");
					System.out.println("Board : " + board + " not found in dropdownlist >>selecting random board");
					Board.click();
				}
			}

			// Select selectBoard = new Select(this.BoardSelect);
			// selectBoard.selectByVisibleText(data.get("board"));
			Thread.sleep(2000);
			sessionData.put("Board", board);
		}
		return true;

	}

	public Object selectClass(String Class) throws Exception {
		DashBoardPage dashboardPage = new DashBoardPage(driver, test);
		Thread.sleep(3000);
		test.log(LogStatus.INFO, "Selecting Class" + Class);
		Reporter.log("Selecting Class");
		String Classes = "";
		if (DataUtil.isDigit(Class) || DataUtil.isFloat(Class)) {
			Classes = DataUtil.convertIntegerToRoman(Integer.parseInt(Class));
		} else {
			Classes = Class;
		}
		List<WebElement> ClassElements = driver.findElements(By.xpath(
				"//*[@class='container videouploader']//*[@class='row']//*[@class='col-xs-12 col-sm-3 col-md-3 syllabus_container mb20']//select[@class='form-control syllabus classDropDown']/option"));
		System.out.println("Number of Class:" + (ClassElements.size() - 1));
		Thread.sleep(1000);
		int counter = 0;
		for (WebElement ClassElement : ClassElements) {
			counter++;
			String name = ClassElement.getText();
			if (name.trim().equalsIgnoreCase(Classes)) {
				System.out.println(name);
				ClassElement.click();
				break;
			} else {
				if (counter == ClassElements.size()) {
					test.log(LogStatus.INFO,
							"Class : " + Class + " not found in dropdownlist >>selecting random class");
					Reporter.log("Class : " + Class + " not found in dropdownlist >>selecting random class");
					System.out.println("Class : " + Class + " not found in dropdownlist >>selecting random class");
					ClassElement.click();

				}
			}
		}
		// Select selectCass = new Select(this.ClassSelect.get(0));
		// selectCass.selectByVisibleText(Class);
		sessionData.put("class", Classes);
		Thread.sleep(1000);
		return true;
	}

	public Object selectSubject(String subject) throws Exception {
		DashBoardPage dashboardPage = new DashBoardPage(driver, test);
		test.log(LogStatus.INFO, "Selecting Subject" + subject);
		Reporter.log("Selecting Subject" + subject);
		List<WebElement> SubjectElements = driver.findElements(By.xpath(
				"//*[@class='container videouploader']//*[@class='row']//*[@class='col-xs-12 col-sm-3 col-md-3 syllabus_container mb20']//select[@class='form-control syllabus ']/option"));
		System.out.println("Number of Subject:" + (SubjectElements.size() - 1));
		int counter = 0;

		for (WebElement sub : SubjectElements) {
			String name = sub.getText().trim();
			if (name.equalsIgnoreCase(subject)) {
				System.out.println(name);
				sub.click();
				break;

			} else {
				if (counter == SubjectElements.size()) {
					test.log(LogStatus.INFO,
							"Subject : " + subject + " not found in dropdownlist >>selecting random subject");
					Reporter.log("Subject : " + subject + " not found in dropdownlist >>selecting random subject");
					System.out
							.println("Subject : " + subject + " not found in dropdownlist >>selecting random subject");
					sub.click();

				}
			}
		}

		// Select selectSubject = new Select(this.SubjectSelect.get(0));
		// selectSubject.selectByVisibleText(data.get("subject_name"));

		sessionData.put("subject_name", subject);
		return true;
	}

	public Object selectSection(String section) throws Exception {
		Thread.sleep(3000);
		Thread.sleep(1000);
		test.log(LogStatus.INFO, "Selecting Section" + section);
		Reporter.log("Selecting Section" + section);
		clickElement(this.SectionSelect, "Section");
		Thread.sleep(1000);
		List<WebElement> SectionElements = driver.findElements(By.xpath(
				"//*[@class='container videouploader']//*[@class='row']//*[@class='col-xs-12 col-sm-3 col-md-3 mb20']//ul/li"));
		System.out.println("Number of Section:" + (SectionElements.size() - 1));
		int counter = 0;
		for (WebElement Section : SectionElements) {
			String name = Section.getText();

			if (name.equalsIgnoreCase(section)) {
				System.out.println(name);
				Section.click();
				break;
			} else {
				if (counter == SectionElements.size()) {
					test.log(LogStatus.INFO,
							"Section : " + section + " not found in dropdownlist >>selecting random section");
					Reporter.log("Section : " + section + " not found in dropdownlist >>selecting random section");
					System.out
							.println("Section : " + section + " not found in dropdownlist >>selecting random section");
					Section.click();

				}
			}
		}

		// Select selectSection = new Select(this.SectionSelect.get(0));
		// selectSection.selectByVisibleText(data.get("section"));
		sessionData.put("section", section);
		Thread.sleep(1000);
		return true;
	}

	public Object selectDatePicker() throws Exception {
		try {
			Thread.sleep(3000);
			clickElement(this.Date, "Date");
			Thread.sleep(2000);
			int size = this.TimeSelectByOption.size();
			for (int k = 0; k < size; k++) {

				Actions ac = new Actions(driver);

				if (driver.findElements(By.xpath(
						"//*[@class='xdsoft_datetimepicker xdsoft_noselect ' and contains(@style,'block')]//*[@class='xdsoft_time_box xdsoft_scroller_box']/div/div"))
						.get(k).getAttribute("class").contains("current")) {
					ac.moveToElement(driver.findElements(By.xpath(
							"//*[@class='xdsoft_datetimepicker xdsoft_noselect ' and contains(@style,'block')]//*[@class='xdsoft_time_box xdsoft_scroller_box']/div/div"))
							.get(k)).build().perform();
					driver.findElements(By.xpath(
							"//*[@class='xdsoft_datetimepicker xdsoft_noselect ' and contains(@style,'block')]//*[@class='xdsoft_time_box xdsoft_scroller_box']/div/div"))
							.get(k).click();
					break;
				}
			}
			// clickElement(this.DateSelect, "Select Date");
			// Thread.sleep(2000);
			// clickElement(this.TimeSelect, "Time");
			Thread.sleep(2000);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public Object enterLectureTitle(Hashtable<String, String> data) throws Exception {
		Thread.sleep(3000);
		try {
			WebDriverWait wt = new WebDriverWait(driver, 30);
			sessionData.put("time", this.Date.getAttribute("value"));
			// System.out.println(this.Date.getAttribute("value"));
			fillData(this.LectureTitle, "Lecture_title", data.get("class") + "_" + data.get("subject_name"));
			sessionData.put("title", data.get("class") + "_" + data.get("subject_name"));
			return true;
		} catch (Exception e) {
			this.takeScreenShot();
			row++;
			return null;
		}
	}

	public Object enterDuration(String daration) throws Exception {
		Thread.sleep(3000);
		try {
			WebDriverWait wt = new WebDriverWait(driver, 30);
			fillData(this.Duration, "Dauration", "20");
			Thread.sleep(1000);
			return true;
		} catch (Exception e) {
			this.takeScreenShot();
			return null;
		}
	}

	public Object clickSubmit(Hashtable<String, String> data) throws Exception {
		ViewLiveClassPage viewLiveClassObject = new ViewLiveClassPage(driver, test);
		Thread.sleep(3000);
		try {
			WebDriverWait wt = new WebDriverWait(driver, 30);
			clickElement(this.Submit, "Save");
			Thread.sleep(1000);
			expectedSessionData.add(sessionData);
			wt.until(ExpectedConditions.visibilityOf(this.ViewRecords));
			this.takeScreenShot();
			row++;
			return viewLiveClassObject;
		} catch (Exception e) {
			this.takeScreenShot();
			row++;
			return null;
		}
	}

	public Object clickStudentGoToSchool() throws InterruptedException {
		try {
			Thread.sleep(3000);
			DashBoardPage dashboardPage = new DashBoardPage(driver, test);
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.visibilityOf(dashboardPage.GoToSchoolStudentLink));
			clickElement(dashboardPage.GoToSchoolStudentLink, "GoToSchoolLink");
			Thread.sleep(3000);
			wt.until(ExpectedConditions.visibilityOf(this.UpcommingClass));
			Thread.sleep(3000);
			dashboardPage.takeScreenShot();
			return true;

		} catch (Exception e) {
			return null;
		}

	}

	public Object getUpcommingClassDetail() throws Exception {
		try {
			Thread.sleep(1000);
			test.log(LogStatus.INFO, "Viewing upcomming Session");
			Reporter.log("Viewing upcomming Session");
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.visibilityOf(this.UpcommingClass));
			Thread.sleep(3000);
			String upCommingSession = this.UpcommingClass.getText();
			this.takeScreenShot();
			return upCommingSession;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	public Object joinLiveClassByStudent(Hashtable<String, String> data) throws Exception {
		try {
			Thread.sleep(3000);
			DashBoardPage dashboardPage = new DashBoardPage(driver, test);
			WebDriverWait wt = new WebDriverWait(driver, 60);
			Thread.sleep(3000);
			test.log(LogStatus.INFO, "Click on view more classes");
			Reporter.log("Click on view more classes");
			wt.until(ExpectedConditions.visibilityOf(this.ViewMore));
			Thread.sleep(3000);
			dashboardPage.takeScreenShot();
			String parentWindow = driver.getWindowHandle();
			clickElement(this.ViewMore, "Live Class");
			Thread.sleep(3000);
			System.out.println("Number of Live Classes:" + this.LiveClasses.size());
			test.log(LogStatus.INFO, "Number of Live Classes:" + this.LiveClasses.size());
			for (int i = this.LiveClasses.size() - 1; i < this.LiveClasses.size(); i++) {
				clickElement(this.LiveClasses, i, "Live classes");
				dashboardPage.takeScreenShot();
			}

			/*
			 * Set<String> windows = driver.getWindowHandles(); for (String win : windows) {
			 * if (win != parentWindow) { driver.switchTo().window(win); } }
			 * 
			 * Thread.sleep(3000); clickElement(this.JoinClass,"Join Live class" );
			 */
			Thread.sleep(3000);
			dashboardPage.takeScreenShot();
			return true;

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}
}
