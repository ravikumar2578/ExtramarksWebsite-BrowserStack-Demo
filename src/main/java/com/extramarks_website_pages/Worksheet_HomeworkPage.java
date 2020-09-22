package com.extramarks_website_pages;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.extramarks_website_utils.Constants;
import com.extramarks_website_utils.DataUtil;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Worksheet_HomeworkPage extends SchoolAtHomePage {

	public Worksheet_HomeworkPage(WebDriver dr, ExtentTest t) throws Exception {
		super(dr, t);

		PageFactory.initElements(driver, this);
		// defaultRun();
	}

	public static List<HashMap<String, String>> expectedSessionData = new ArrayList<HashMap<String, String>>();
	@FindBy(xpath = "//*[@class='row']//*[@class='col-sm-6 col-md-6']//select[@id='paper_type']")
	WebElement Category;
	@FindBy(xpath = "//*[@class='row']//*[@class='col-sm-6 col-md-6']//select[@id='boardIDTemp']")
	WebElement Board;
	@FindBy(xpath = "//*[@class='row']//*[@class='col-sm-6 col-md-6 firstLayer']//select[@id='classID']")
	WebElement Class;
	@FindBy(xpath = "//*[@class='row']//*[@class='col-sm-6 col-md-6']//select[@id='subjectID']")
	WebElement Subject;
	@FindBy(xpath = "//*[@class='row']//*[@class='col-sm-6 col-md-6']//select[@id='class_section_id']")
	WebElement Section;
	@FindBy(xpath = "//*[@class='row']//*[@class='col-sm-6 col-md-6']//select[@id='language_id']")
	WebElement Language;
	@FindBy(xpath = "//*[@class='row']//*[@class='col-sm-6 col-md-6']//*[@class='multi-select']/div/div")
	WebElement Student;
	@FindBy(xpath = "//*[@class='row']//*[@class='col-sm-6 col-md-6']//input[@id='paper_name']")
	WebElement QuestionName;
	@FindBy(xpath = "//*[@class='row']//*[@class='col-sm-6 col-md-6']//input[@id='date_timepicker']")
	WebElement StartDate;
	@FindBy(xpath = "//*[@class='row']//*[@class='col-sm-6 col-md-6']//input[@id='date_timepicker']")
	WebElement StartDate_Day;
	@FindBy(xpath = "//*[@class='row']//*[@class='col-sm-6 col-md-6']//input[@id='date_timepicker_end']")
	WebElement EndDate;
	@FindBy(xpath = "//*[@class='row']//*[@class='col-sm-6 col-md-6']//input[@id='uploadfile']")
	WebElement UploadFile;
	@FindBy(xpath = "//*[@id='addworksheet']//*[@style='margin-top: 20px;']//input[@type='submit']")
	WebElement Submit;

	public List<WebElement> Category() {
		List<WebElement> cat = driver.findElements(
				By.xpath("//*[@class='row']//*[@class='col-sm-6 col-md-6']//select[@id='paper_type']//option"));
		return cat;
	}

	public List<WebElement> Board() {
		List<WebElement> board = driver.findElements(
				By.xpath("//*[@class='row']//*[@class='col-sm-6 col-md-6']//select[@id='boardIDTemp']//option"));
		return board;
	}

	public List<WebElement> Class() {
		List<WebElement> classes = driver.findElements(
				By.xpath("//*[@class='row']//*[@class='col-sm-6 col-md-6 firstLayer']//select[@id='classID']//option"));
		return classes;
	}

	public List<WebElement> Subject() {
		List<WebElement> subjects = driver.findElements(
				By.xpath("//*[@class='row']//*[@class='col-sm-6 col-md-6']//select[@id='subjectID']//option"));
		return subjects;
	}

	public List<WebElement> Section() {
		List<WebElement> section = driver.findElements(
				By.xpath("//*[@class='row']//*[@class='col-sm-6 col-md-6']//select[@id='class_section_id']//option"));
		return section;
	}

	public List<WebElement> Language() {
		List<WebElement> language = driver.findElements(
				By.xpath("//*[@class='row']//*[@class='col-sm-6 col-md-6']//select[@id='language_id']//option"));
		return language;
	}

	public List<WebElement> Students() {
		List<WebElement> students = driver.findElements(By.xpath(
				"//*[@class='row']//*[@class='col-sm-6 col-md-6']//*[@class='multi-select']//*[@class='btn-group open']//ul/li"));
		return students;
	}

	public List<WebElement> Date() {
		List<WebElement> time = driver.findElements(By.xpath(
				"//*[@class='xdsoft_datetimepicker xdsoft_noselect ' and contains(@style,'block')]//table//tr//td"));
		return time;
	}

	public List<WebElement> Time() {
		List<WebElement> time = driver.findElements(By.xpath(
				"//*[@class='xdsoft_datetimepicker xdsoft_noselect ' and contains(@style,'block')]//*[@class='xdsoft_time_box xdsoft_scroller_box']/div/div[not(contains(@class,'disabled'))]"));
		return time;
	}

	public List<WebElement> StudentSubjectList() {
		List<WebElement> subList = driver.findElements(By.xpath(
				"//*[@class='col-sm-11']//*[@class='postlogin-card my-profile-sec mb30']//*[@id='nlwtsubjectlist']//a"));
		return subList;
	}

	public List<WebElement> StudentHomeworkList() {
		List<WebElement> subList = driver.findElements(By.xpath(
				"//*[@class='col-sm-11']//*[@class='mt30 tab-content']//*[@id='all']//*[@class='media postlogin-card radius4 mediastyle']"));
		return subList;
	}

	public List<WebElement> StudentWorksheetList() {
		List<WebElement> worksheetList = driver.findElements(By.xpath(
				"//*[@class='col-sm-11']//*[@class='mt30 tab-content']//*[@id='assigned']//*[@class='media postlogin-card radius4 mediastyle']"));
		return worksheetList;
	}

	public static String assignedHomeworkSubject = "";
	public static String assignedWorksheetSubject = "";
	public static String homeworkTitle = "";
	public static String worksheetTitle = "";

	public Object assignHomework(Hashtable<String, String> data) throws Exception {
		DashBoardPage dashboardPage = new DashBoardPage(driver, test);
		try {
			Thread.sleep(3000);
			String parentWindow = driver.getWindowHandle();
			SchoolAtHomePage schoolAthomeObject = new SchoolAtHomePage(driver, test);

			clickElement(schoolAthomeObject.HomeWork, "Homework");

			Thread.sleep(3000);
			Set<String> windows = driver.getWindowHandles();
			for (String win : windows) {
				if (win != parentWindow) {
					driver.switchTo().window(win);
				}
			}
			test.log(LogStatus.INFO, "Selecting Category");
			Reporter.log("Selecting Category");
			this.takeScreenShot();
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.visibilityOf(this.Category));
			Thread.sleep(3000);
			System.out.println("Number of Category:" + (this.Category().size() - 1));
			for (WebElement cat : this.Category()) {
				String name = cat.getText();
				if (name.equalsIgnoreCase("Homework")) {
					System.out.println(name);
					cat.click();
					break;
				}
			}
			test.log(LogStatus.INFO, "Selecting Board");
			Reporter.log("Selecting Board");
			this.takeScreenShot();
			Thread.sleep(3000);
			clickElement(this.Board,"Board");
			Thread.sleep(1000);
			System.out.println("Number of Board:" + (this.Board().size() - 1));
			for (WebElement board : this.Board()) {
				String name = board.getText();
				if (name.equalsIgnoreCase("CBSE")) {
					System.out.println(name);
					board.click();
					break;
				}
			}
			Thread.sleep(3000);
			this.takeScreenShot();
			test.log(LogStatus.INFO, "Selecting Class");
			Reporter.log("Selecting Class");
			clickElement(this.Class,"Class");
			Thread.sleep(1000);
			System.out.println("Number of Classes:" + (this.Class().size() - 1));
			for (WebElement classes : this.Class()) {
				String name = classes.getText();
				if (name.equalsIgnoreCase("VIII")) {
					System.out.println(name);
					classes.click();
					break;
				}
			}

			Thread.sleep(3000);
			this.takeScreenShot();
			test.log(LogStatus.INFO, "Selecting Subject");
			Reporter.log("Selecting Subject");
			clickElement(this.Subject,"Subject");
			Thread.sleep(1000);
			System.out.println("Number of Subjects:" + (this.Subject().size() - 1));
			for (WebElement subject : this.Subject()) {
				String name = subject.getText();
				if (name.equalsIgnoreCase("Science")) {
					assignedHomeworkSubject = name;
					System.out.println(name);
					subject.click();
					break;
				}
			}
			Thread.sleep(3000);
			test.log(LogStatus.INFO, "Selecting Section");
			Reporter.log("Selecting Section");
			this.takeScreenShot();
			clickElement(this.Section,"Section");
			Thread.sleep(1000);
			System.out.println("Number of Sction:" + (this.Section().size() - 1));
			for (WebElement section : this.Section()) {
				String name = section.getText();
				if (name.equalsIgnoreCase("cbsebatch5")) {
					System.out.println(name);
					section.click();
					break;
				}
			}
			Thread.sleep(3000);
			this.takeScreenShot();
			homeworkTitle = "TestPaper"+DataUtil.generateRandom();
			fillData(this.QuestionName, "QuestionPaeper", homeworkTitle);
			Thread.sleep(3000);
			test.log(LogStatus.INFO, "Selecting Language");
			Reporter.log("Selecting Language");
			this.takeScreenShot();
		
			System.out.println("Number of Language:" + (this.Language().size() - 1));
			for (WebElement language : this.Language()) {
				String name = language.getText();
				if (name.equalsIgnoreCase("English")) {
					System.out.println(name);
					language.click();
					break;
				}
			}
			Thread.sleep(3000);
			clickElement(this.Student, "Student List");
			test.log(LogStatus.INFO, "Selecting Student");
			Reporter.log("Selecting Student");
			this.takeScreenShot();
			System.out.println("Number of Students:" + (this.Students().size() - 1));
			for (WebElement Student : this.Students()) {
				String name = Student.getText();
				if (name.equalsIgnoreCase("website")) {
					System.out.println(name);
					Student.click();
					break;
				}
			}
			Thread.sleep(3000);

			fileUpload(this.UploadFile, Constants.Image_PATH);
			Thread.sleep(3000);
			this.takeScreenShot();
			clickElement(this.StartDate, "StartDate");
			Thread.sleep(2000);
			this.takeScreenShot();
			int size = this.Time().size();
			for (WebElement time : Time()) {

				if (time.getAttribute("class").contains("current")) {
					time.click();
					break;

				}
			}
			Thread.sleep(2000);
			this.takeScreenShot();
			clickElement(this.EndDate, "EndDate");
			int dateSize = this.Date().size();

			int timeSize2 = this.Time().size();
			for (WebElement time : Time()) {
				if (time.getAttribute("class").contains("current")) {
					time.click();
					break;
				}

			}
			Thread.sleep(3000);
			this.takeScreenShot();
			clickElement(this.Submit, "Submit Button");
			this.takeScreenShot();
			return new ViewHomeworkPage(driver, test);
		} catch (Exception e) {
			return null;

		}

	}

	public Object clickSchoolAtHomeworkLink(Hashtable<String, String> data) throws Exception {
		DashBoardPage dashboardPage = new DashBoardPage(driver, test);

		try {

			dashboardPage.clickStudentSchoolAtHomeLink();

			return true;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public Object verifyHomework(Hashtable<String, String> data) throws Exception {

		try {
			test.log(LogStatus.INFO, " Verifying Homework");
			this.takeScreenShot();
			Thread.sleep(1000);
			SchoolAtHomePage schoolathomepage = new SchoolAtHomePage(driver, test);
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.visibilityOf(schoolathomepage.StudentHomeWork));
			Thread.sleep(3000);
			String text = schoolathomepage.StudentHomeWork.getText();
			String regex = "^(?=.*?[1-9])\\d+(\\.\\d+)?$";
			Pattern pattern = Pattern.compile(regex);

			if (pattern.matcher(text).matches()) {
				test.log(LogStatus.INFO, "Homework is not assigned to verify");
				return new SchoolAtHomePage(driver, test);
			} else {
				clickElement(schoolathomepage.StudentHomeWork, "Student HomeworkLink");
				Thread.sleep(3000);
				if (driver
						.findElement(
								By.xpath("//*[@id='exampleModa2']//a[@href='/school-at-home#school-at-home-package']"))
						.isDisplayed()) {
					test.log(LogStatus.INFO, "Package is not subscribed by user ! ");
					System.out.println("Package is not subscribed by user ! ");
					this.takeScreenShot();
				} else {
					test.log(LogStatus.INFO, "Homework Page displayed");
					this.takeScreenShot();
					wt.until(ExpectedConditions.visibilityOfAllElements(this.StudentSubjectList()));
					test.log(LogStatus.INFO, "Number of Subjects: + (this.StudentSubjectList().size())");
					System.out.println("Number of Subjects:" + (this.StudentSubjectList().size()));

					for (WebElement sub : this.StudentSubjectList()) {
						String name = sub.getText();
						// if (name.equalsIgnoreCase(this.assignedHomeworkSubject)) {

						sub.click();

						// }
						test.log(LogStatus.INFO, "Subject is : " + name);
						System.out.println("Subject is : " + name);
						break;
					}
					Thread.sleep(3000);
					wt.until(ExpectedConditions.visibilityOfAllElements(this.StudentHomeworkList()));
					test.log(LogStatus.INFO, "Number of Homework: " + (this.StudentHomeworkList().size()));
					System.out.println("Number of Homework:" + (this.StudentHomeworkList().size()));
					this.takeScreenShot();
					for (WebElement homework : this.StudentSubjectList()) {
						String name = homework.getText();
						// if (name.equalsIgnoreCase(this.homeworkTitle)) {
						test.log(LogStatus.INFO, "Homework is : " + name);
						System.out.println("Homework is : " + name);
						break;
						// }
					}
					Thread.sleep(3000);
				}
				ViewHomeworkPage viewHomeworkPage = new ViewHomeworkPage(driver, test);
				viewHomeworkPage.takeScreenShot();
				return viewHomeworkPage;
			}
		} catch (Exception e) {
			return null;
		}

	}

	public Object assignWorksheet(Hashtable<String, String> data) throws Exception {
		DashBoardPage dashboardPage = new DashBoardPage(driver, test);
		try {
			Thread.sleep(3000);
			String parentWindow = driver.getWindowHandle();
			SchoolAtHomePage schoolAthomeObject = new SchoolAtHomePage(driver, test);
			clickElement(schoolAthomeObject.Worksheet, "Worksheet");
			Thread.sleep(3000);
			test.log(LogStatus.INFO, "Worksheet page is displayed : ");
			System.out.println("Worksheet page is displayed");
			this.takeScreenShot();
			Set<String> windows = driver.getWindowHandles();
			for (String win : windows) {
				if (win != parentWindow) {
					driver.switchTo().window(win);
				}
			}
			test.log(LogStatus.INFO, "Selecting Category");
			Reporter.log("Selecting Category");
			this.takeScreenShot();
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.visibilityOf(this.Category));
			Thread.sleep(3000);
			System.out.println("Number of Category:" + (this.Category().size() - 1));
			for (WebElement cat : this.Category()) {
				String name = cat.getText();
				if (name.equalsIgnoreCase("Worksheet")) {

					test.log(LogStatus.INFO, "Category  is : " + name);
					System.out.println("Category is : " + name);
					cat.click();
					break;
				}
			}

			test.log(LogStatus.INFO, "Selecting Board");
			Reporter.log("Selecting Board");
			this.takeScreenShot();
			Thread.sleep(3000);
			clickElement(this.Board,"Board");
			Thread.sleep(1000);
			System.out.println("Number of Board:" + (this.Board().size() - 1));
			for (WebElement board : this.Board()) {
				String name = board.getText();
				if (name.equalsIgnoreCase("CBSE")) {
					System.out.println(name);
					board.click();
					break;
				}
			}
			Thread.sleep(3000);
			this.takeScreenShot();
			test.log(LogStatus.INFO, "Selecting Class");
			Reporter.log("Selecting Class");
			clickElement(this.Class,"Class");
			Thread.sleep(1000);
			System.out.println("Number of Classes:" + (this.Class().size() - 1));
			for (WebElement classes : this.Class()) {
				String name = classes.getText();
				if (name.equalsIgnoreCase("VIII")) {
					System.out.println(name);
					classes.click();
					break;
				}
			}

			Thread.sleep(3000);
			this.takeScreenShot();
			test.log(LogStatus.INFO, "Selecting Subject");
			Reporter.log("Selecting Subject");
			clickElement(this.Subject,"Subject");
			Thread.sleep(1000);
			System.out.println("Number of Subjects:" + (this.Subject().size() - 1));
			for (WebElement subject : this.Subject()) {
				String name = subject.getText();
				if (name.equalsIgnoreCase("Science")) {
					assignedHomeworkSubject = name;
					System.out.println(name);
					subject.click();
					break;
				}
			}
			Thread.sleep(3000);
			test.log(LogStatus.INFO, "Selecting Section");
			Reporter.log("Selecting Section");
			this.takeScreenShot();
			clickElement(this.Section,"Section");
			Thread.sleep(1000);
			System.out.println("Number of Sction:" + (this.Section().size() - 1));
			for (WebElement section : this.Section()) {
				String name = section.getText();
				if (name.equalsIgnoreCase("cbsebatch5")) {
					System.out.println(name);
					section.click();
					break;
				}
			}
			Thread.sleep(3000);
			this.takeScreenShot();
			homeworkTitle = "TestPaper"+DataUtil.generateRandom();
			fillData(this.QuestionName, "QuestionPaeper", homeworkTitle);
			Thread.sleep(3000);
			test.log(LogStatus.INFO, "Selecting Language");
			Reporter.log("Selecting Language");
			this.takeScreenShot();
		
			System.out.println("Number of Language:" + (this.Language().size() - 1));
			for (WebElement language : this.Language()) {
				String name = language.getText();
				if (name.equalsIgnoreCase("English")) {
					System.out.println(name);
					language.click();
					break;
				}
			}
			Thread.sleep(3000);
			clickElement(this.Student, "Student List");
			test.log(LogStatus.INFO, "Selecting Student");
			Reporter.log("Selecting Student");
			this.takeScreenShot();
			System.out.println("Number of Students:" + (this.Students().size() - 1));
			for (WebElement Student : this.Students()) {
				String name = Student.getText();
				if (name.equalsIgnoreCase("website")) {
					System.out.println(name);
					Student.click();
					break;
				}
			}
			Thread.sleep(3000);

			fileUpload(this.UploadFile, Constants.Image_PATH);
			Thread.sleep(3000);
			this.takeScreenShot();
			clickElement(this.StartDate, "StartDate");
			Thread.sleep(2000);
			this.takeScreenShot();
			int size = this.Time().size();
			for (WebElement time : Time()) {

				if (time.getAttribute("class").contains("current")) {
					time.click();
					break;

				}
			}
			Thread.sleep(2000);
			this.takeScreenShot();
			clickElement(this.EndDate, "EndDate");
			int dateSize = this.Date().size();

			int timeSize2 = this.Time().size();
			for (WebElement time : Time()) {
				if (time.getAttribute("class").contains("current")) {
					time.click();
					break;
				}

			}
			Thread.sleep(3000);
			this.takeScreenShot();
			clickElement(this.Submit, "Submit Button");
			this.takeScreenShot();
			return new ViewWorksheetPage(driver, test);
		} catch (Exception e) {
			return null;

		}
	}

	public Object verifyWorksheet(Hashtable<String, String> data) throws Exception {
		try {
			SchoolAtHomePage schoolathomepage = new SchoolAtHomePage(driver, test);
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.visibilityOf(schoolathomepage.StudentWorksheet));
			Thread.sleep(3000);
			test.log(LogStatus.INFO, "Verifying Worksheet");
			this.takeScreenShot();
			String text = schoolathomepage.StudentWorksheet.getText();
			String regex = "^(?=.*?[1-9])\\d+(\\.\\d+)?$";
			Pattern pattern = Pattern.compile(regex);
			if (pattern.matcher(text).matches()) {
				test.log(LogStatus.INFO, "Worksheet is not assigned to verify");
				SchoolAtHomePage schoolAtHomePAge = new SchoolAtHomePage(driver, test);
				schoolAtHomePAge.takeScreenShot();
				return schoolAtHomePAge;
			} else {
				clickElement(schoolathomepage.StudentWorksheet, "Student worksheetLink");
				Thread.sleep(3000);
				if (driver
						.findElement(
								By.xpath("//*[@id='exampleModa2']//a[@href='/school-at-home#school-at-home-package']"))
						.isDisplayed()) {
					test.log(LogStatus.INFO, "Package is not subscribed by user ! ");
					System.out.println("Package is not subscribed by user ! ");
					this.takeScreenShot();
				} else {

					wt.until(ExpectedConditions.visibilityOfAllElements(this.StudentSubjectList()));

					System.out.println("Number of Subjects:" + (this.StudentSubjectList().size()));
					test.log(LogStatus.INFO, "Number of Subjects: " + (this.StudentSubjectList().size()));
					for (WebElement sub : this.StudentSubjectList()) {
						String name = sub.getText();
						// if (name.equalsIgnoreCase(this.assignedWorksheetSubject)) {
						System.out.println("Subject is : " + name);
						test.log(LogStatus.INFO, "Subject is : " + name);
						this.takeScreenShot();
						sub.click();
						break;
						// }
					}
					Thread.sleep(3000);
					wt.until(ExpectedConditions.visibilityOfAllElements(this.StudentWorksheetList()));
					System.out.println("Number of Worksheet:" + (this.StudentWorksheetList().size()));
					test.log(LogStatus.INFO, "Number of Worksheet:" + (this.StudentWorksheetList().size()));
					this.takeScreenShot();
					for (WebElement worksheet : this.StudentSubjectList()) {
						String name = worksheet.getText();
						// if (name.equalsIgnoreCase(this.worksheetTitle)) {
						System.out.println("Worksheet is : " + name);
						test.log(LogStatus.INFO, "Worksheet is : " + name);
						break;
						// }
					}
					Thread.sleep(3000);
				}
				ViewWorksheetPage viewWorksheetPage = new ViewWorksheetPage(driver, test);
				viewWorksheetPage.takeScreenShot();
				return viewWorksheetPage;
			}
		} catch (Exception e) {
			this.takeScreenShot();
			return null;
		}
	}
}
