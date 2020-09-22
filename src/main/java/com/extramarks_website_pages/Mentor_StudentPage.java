package com.extramarks_website_pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.extramarks_website_utils.Constants;
import com.extramarks_website_utils.DataUtil;
import com.extramarks_website_utils.Xls_Reader;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Mentor_StudentPage extends BasePage {

	@FindBy(xpath = "//img[@alt='add-child']")
	WebElement AddChild;

	@FindBy(xpath = "//input[@id='student_email']")
	WebElement EmailChild;

	@FindBy(xpath = "//select[@id='student_board']")
	WebElement StudentBoard;

	@FindBy(xpath = "//select[@id='student_class']")
	WebElement StudentClass;

	@FindBy(xpath = "//select[@id='student_subject']")
	WebElement StudentSubject;

	@FindBy(xpath = "//button[@id='popup_addmurid']")
	WebElement AddStudentButton;

	@FindBy(xpath = "//div[@id='add-murid']//button[@class='close']")
	WebElement CloseButton;

	@FindBy(xpath = "//img[@alt='user-guide']")
	public List<WebElement> StudentList;

	@FindBy(xpath = "//div[@class='modal-dialog']//*[@class='modal-body']/span[@id='succussesMessageload']")
	public List<WebElement> InvalidEmailMsg;

	public Mentor_StudentPage(WebDriver driver, ExtentTest test) throws Exception {
		super(driver, test);
		PageFactory.initElements(driver, this);
	}

	LoginPage lp = new LoginPage(driver, test);

	public boolean AddStudent(String email, String Board, String Class, String Subject) throws InterruptedException {
			try {
		    AddChild.click();
			Thread.sleep(3000);
			test.log(LogStatus.INFO, "Adding new Student");
			Thread.sleep(3000);

			EmailChild.sendKeys(email);
			Thread.sleep(2000);
			clickElement(StudentBoard, "StudentBoard");
			Select sel = new Select(StudentBoard);

			sel.selectByVisibleText(Board);
			Thread.sleep(2000);
			clickElement(StudentClass, "StudentClass");
			Select sel1 = new Select(StudentClass);
			sel1.selectByVisibleText(Class);
			Thread.sleep(2000);
			clickElement(StudentSubject, "StudentSubject");
			String selectedSubject=selectData(StudentSubject,"StudentSubject",1);
			Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);
			Thread.sleep(100);
			DataUtil.setData(xls, "StudentNotesTest", 1, "Subject", selectedSubject);
			Thread.sleep(100);
			Thread.sleep(3000);
			clickElement(AddStudentButton, "AddStudentButton");
		
			/*
			 * Add Student service is not working in test-automation.www.extramarks.com
			 * 
			 */
			// close button will not be clicked once Select subject start working.
			// CloseButton.click();
          return true;
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Getting error on Add Student" + e.getMessage().substring(0, 200));
			return false;
		}
	}

}
