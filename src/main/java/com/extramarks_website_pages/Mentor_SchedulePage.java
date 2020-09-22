package com.extramarks_website_pages;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.extramarks_website_utils.Constants;
import com.extramarks_website_utils.DataUtil;
import com.extramarks_website_utils.Xls_Reader;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class Mentor_SchedulePage extends BasePage {
	@FindBy(xpath = "//a[@id='lampiran']")
	WebElement UploadBtn;
	@FindBy(xpath = "//select[@id='uploadsubject']")
	WebElement UploadSubject;
	@FindBy(xpath = "//select[@id='uploadchapters']")
	WebElement UploadChapter;
	@FindBy(id = "fileNameUpload")
	WebElement FileUpload;

	@FindBy(xpath = "//input[@value='Upload File']")
	WebElement UploadFileButton;

	@FindBy(xpath = "(//*[@class='postlogin-card']//*[@class='recent-view'])[1]")
	public
	List<WebElement> studentExist;
	
	@FindBy(xpath = "//img[@alt='user-guide']")
	List<WebElement> AddStudent;
	
	@FindBy(xpath = "//a[@id='jadwal']")
	WebElement Schedule;
	@FindBy(xpath = "//*[@class='postlogin-card my-profile-sec ng-scope']//a[contains(text(),'Add Schedule')]")
	WebElement AddScheduleBtn;

	@FindBy(xpath = "//input[@id='title']")
	WebElement Title;
	@FindBy(xpath = "//input[@id='schedule_date6']")
	WebElement StartDate;

	@FindBy(xpath = "//div[@class='datetimepicker datetimepicker-dropdown-top-left dropdown-menu']//div[@class='datetimepicker-days']//table[@class=' table-condensed']//tr//td[@class='day']")
	List<WebElement> Date;

	@FindBy(xpath = "//div[@class='datetimepicker datetimepicker-dropdown-top-left dropdown-menu']//div[@class='datetimepicker-hours']//table[@class=' table-condensed']//td//span")
	List<WebElement> Hours;
	@FindBy(xpath = "//div[@class='datetimepicker datetimepicker-dropdown-top-left dropdown-menu']//div[@class='datetimepicker-minutes']//table[@class=' table-condensed']//td//span")
	List<WebElement> Minutes;
	@FindBy(xpath = "//div[@class='datetimepicker datetimepicker-dropdown-bottom-right dropdown-menu']//div[@class='datetimepicker-hours']//table[@class=' table-condensed']//td//span")
	List<WebElement> Hours_End;
	@FindBy(xpath = "//div[@class='datetimepicker datetimepicker-dropdown-bottom-right dropdown-menu']//div[@class='datetimepicker-minutes']//table[@class=' table-condensed']//td//span")
	List<WebElement> Minutes_End;

	@FindBy(xpath = "//input[@id='schedule_date7']")
	WebElement EndDt;
	@FindBy(xpath = "//div[@class='datetimepicker datetimepicker-dropdown-bottom-right dropdown-menu']//table[@class=' table-condensed']//tr//td[@class='day']")
	List<WebElement> EndDate;
	@FindBy(xpath = "//select[@id='classDropDown']")
	WebElement ClassDropDwn;
	@FindBy(xpath = "//select[@id='subjectDropDown']")
	WebElement SubjectDropDwn;
	@FindBy(xpath = "//select[@id='chapterDropDown']")
	WebElement ChapterDropDwn;

	@FindBy(xpath = "//select[@id='service']")
	WebElement Service;

	@FindBy(xpath = "//button[@id='addlessonSubmit']")
	WebElement CreateBtn;

	@FindBy(xpath = "//*[@class='postlogin-card']//a[@title]")
	public List<WebElement> studentList;

	public Mentor_SchedulePage(WebDriver driver, ExtentTest test) throws Exception {
		super(driver, test);
		PageFactory.initElements(driver, this);
	}

	LoginPage lp = new LoginPage(driver, test);

	public void AddSchedule(String title, String Class, String Subject, String Chapter) throws Exception {
		Thread.sleep(3000);
		clickElement(Schedule, "Schedule");
		Thread.sleep(3000);
		Thread.sleep(3000);
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		clickElement(AddScheduleBtn, "AddScheduleBtn");
		WebDriverWait wt = new WebDriverWait(driver, 20);
		wt.until(ExpectedConditions.visibilityOf(Title));
		Title.sendKeys(title);
		clickElement(StartDate, "StartDate");
		clickElement(Date, 0, "StartDate");
		clickElement(Hours, 2, "Hours");
		clickElement( Minutes, 2, "Minutes");

		clickElement(EndDt, "EndDt");
		clickElement(EndDate, 1, "EndDate");
		clickElement(Hours_End, 5, "EndDate");
		clickElement(Minutes_End, 2, "EndDate");
		Select classdrp = new Select(ClassDropDwn);
		classdrp.selectByVisibleText(Class);
		Thread.sleep(3000);
		clickElement(SubjectDropDwn, "SubjectDropDwn");
		String selectedSubject=selectData(SubjectDropDwn,"SubjectDropDwn",1);
		Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);
		Thread.sleep(100);
		DataUtil.setData(xls, "StudentNotesTest", 1, "Subject", selectedSubject);
		Thread.sleep(100);
		Thread.sleep(3000);
		clickElement(ChapterDropDwn, "ChapterDropDwn");
		String selectedChapter=selectData(ChapterDropDwn,"ChapterDropDwn",1);
		Thread.sleep(100);
		DataUtil.setData(xls, "StudentNotesTest", 1, "Chapter", selectedChapter);
		Thread.sleep(100);
		Thread.sleep(3000);
		clickElement(Service, "Service");
		selectData(Service,"Service",1);
		clickElement(CreateBtn, "CreateBtn");
		driver.navigate().back();
		driver.navigate().back();

	}

	public void UploadFiles() throws Exception {
		WebDriverWait wt = new WebDriverWait(driver, 15);
		wt.until(ExpectedConditions.elementToBeClickable(UploadBtn));
		clickElement(UploadBtn,"UploadBtn");
		Thread.sleep(3000);
		Select subject = new Select(UploadSubject);
		subject.selectByIndex(1);
		Thread.sleep(3000);
		Select chap = new Select(UploadChapter);
		chap.selectByIndex(3);
		FileUpload.click();
		new ProcessBuilder("D:\\AutoIT\\FileUpload1.exe").start();
		UploadFileButton.click();
		lp.takeFullScreenshot();
		test.log(LogStatus.INFO, "Uploading File");

		Thread.sleep(5000);
	}

}
