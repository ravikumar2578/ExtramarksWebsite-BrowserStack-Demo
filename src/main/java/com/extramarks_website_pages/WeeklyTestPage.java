package com.extramarks_website_pages;

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

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;





public class WeeklyTestPage extends SchoolAtHomePage {

	public WeeklyTestPage(WebDriver dr, ExtentTest t) throws Exception {
		super(dr, t);
		PageFactory.initElements(driver, this);
		// defaultRun();
	}

	@FindBy(xpath = "//*[@class='row']//*[@class='col-sm-6 col-md-6']//input[@id='max_marks']")
	WebElement MaxMarks;
	@FindBy(xpath = "//*[@class='row']//*[@class='col-sm-6 col-md-6']//input[@id='max_time']")
	WebElement Total_time;
	@FindBy(xpath = "//*[@class='row']//*[@class='col-sm-6 col-md-6']//*[@class='multi-select']//div[@class='btn-group']/button")
	WebElement Language;
	@FindBy(xpath = "//*[@class='row']//*[@class='col-sm-6 col-md-6']//input[@id='paper_name']")
	WebElement QuestionName;
	@FindBy(xpath = "//*[@class='row']//*[@class='col-sm-6 col-md-6']//select[@id='no_of_section']")
	WebElement NumberOfSection;
	@FindBy(xpath = "//*[@class='row']//*[@class='col-sm-12 col-md-12']//*[@id='instruction']")
	WebElement PaperInstruction;
	@FindBy(xpath = "//*[@class='creat-temp']//*[@class='page-title']")
	WebElement QuestionMethod;

	@FindBy(xpath = "//*[@class='creat-temp']//*[@class='col-sm-12 col-md-12 col-padd']/input")
	WebElement Submit;

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

	
	
	public List<WebElement> Category() {
		List<WebElement> cat = driver.findElements(
				By.xpath("//*[@class='row']//*[@class='col-sm-6 col-md-6']//select[@id='paper_type']/option"));
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
		List<WebElement> language = driver.findElements(By.xpath(
				"//*[@class='row']//*[@class='col-sm-6 col-md-6']//*[@class='multi-select']//div[@class='btn-group']/ul/li/a"));
		return language;
	}

	public List<WebElement> NoOfSection() {
		List<WebElement> students = driver.findElements(By.xpath(
				"//*[@class='row']//*[@class='col-sm-6 col-md-6']//select[@id='no_of_section']//option"));
		return students;
	}

	public List<WebElement> Date() {
		List<WebElement> time = driver.findElements(By.xpath(
				"//*[@class='xdsoft_datetimepicker xdsoft_noselect ' and contains(@style,'block')]//table//tr//td"));
		return time;
	}

	public List<WebElement> Time() {
		List<WebElement> time = driver.findElements(By.xpath(
				"//*[@class='xdsoft_datetimepicker xdsoft_noselect ' and contains(@style,'block')]//*[@class='xdsoft_time_box xdsoft_scroller_box']/div/div"));
		return time;
	}

	public Object weeklyTest(Hashtable<String, String> data) throws Exception {
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
			Reporter.log("Selecting Session Live Lecture");
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.visibilityOfAllElements(this.Category()));
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

			Thread.sleep(3000);
			fillData(this.Total_time, "Total Time", "60");
			Thread.sleep(3000);
			fillData(this.MaxMarks, "Maximum Marks", "100");
			Thread.sleep(3000);
			Thread.sleep(3000);
			System.out.println("Number of Language:" + (this.Language().size() - 1));
			for (WebElement language : this.Language()) {
				String name = language.getText();
				if (name.equalsIgnoreCase("English")) {
					System.out.println(name);
					language.click();
					break;
				}
			}
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
			System.out.println("Number of Classes:" + (this.Class().size() - 1));
			for (WebElement classes : this.Class()) {
				String name = classes.getText();
				if (name.equalsIgnoreCase("VI")) {
					System.out.println(name);
					classes.click();
					break;
				}
			}

			Thread.sleep(3000);
			System.out.println("Number of Subjects:" + (this.Subject().size() - 1));
			for (WebElement subject : this.Subject()) {
				String name = subject.getText();
				if (name.equalsIgnoreCase("Science")) {
					System.out.println(name);
					subject.click();
					break;
				}
			}
			Thread.sleep(3000);
			System.out.println("Number of Class Section:" + (this.Section().size() - 1));
			for (WebElement classSection : this.Section()) {
				String name = classSection.getText();
				if (name.equalsIgnoreCase("cbsebatch1")) {
					System.out.println(name);
					classSection.click();
					break;
				}
			}
			Thread.sleep(3000);
			fillData(this.QuestionName, "QuestionPaeper", "Test1");
			Thread.sleep(3000);
			System.out.println("Number of Sction:" + (this.NoOfSection().size() - 1));
			for (WebElement section : this.Section()) {
				String name = section.getText();
				if (name.equalsIgnoreCase("3")) {
					System.out.println(name);
					section.click();
					break;
				}
			}
			Thread.sleep(3000);
			fillData(this.PaperInstruction, "Paper Instruction", "Please read blow section carefully");
			Thread.sleep(2000);
			clickElement(this.Submit, "Submit Button");
		} catch (Exception e) {

		}
		return new DashBoardPage(driver, test);
	}

	
	public Object verifyWeeklyTest(Hashtable<String, String> data) throws Exception {
		try {
			SchoolAtHomePage schoolathomepage = new SchoolAtHomePage(driver, test);
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.visibilityOf(schoolathomepage.StudentWeeklyTest));
			Thread.sleep(3000);
			test.log(LogStatus.INFO, "Verifying WeeklyTest");
			this.takeScreenShot();
			String text = schoolathomepage.StudentWorksheet.getText();
			String regex = "^(?=.*?[1-9])\\d+(\\.\\d+)?$";
			Pattern pattern = Pattern.compile(regex);
			if (pattern.matcher(text).matches()) {
				test.log(LogStatus.INFO, "Weekly Test is not assigned to verify");
				this.takeScreenShot();
				SchoolAtHomePage schoolAtHomePAge = new SchoolAtHomePage(driver, test);
				schoolAtHomePAge.takeScreenShot();
				return schoolAtHomePAge;
			} else {
				clickElement(schoolathomepage.StudentWeeklyTest, "Student worksheetLink");
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
					this.takeScreenShot();
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
					test.log(LogStatus.INFO, "Number of Weekly Test:" + (this.StudentWorksheetList().size()));
					for (WebElement worksheet : this.StudentSubjectList()) {
						String name = worksheet.getText();
						// if (name.equalsIgnoreCase(this.worksheetTitle)) {
						System.out.println("Worksheet is : " + name);
						test.log(LogStatus.INFO, "Worksheet is : " + name);
						this.takeScreenShot();
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
