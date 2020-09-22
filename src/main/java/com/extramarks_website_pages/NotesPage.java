package com.extramarks_website_pages;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.extramarks_website_utils.Constants;
import com.extramarks_website_utils.DataUtil;
import com.extramarks_website_utils.Xls_Reader;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class NotesPage extends BasePage {
	@FindBy(partialLinkText = "Add Notes")
	public WebElement AddNotes;
	@FindBy(id = "title")

	public WebElement AddTitle;
	@FindBy(id = "classDropDown")

	public WebElement ClassDropDwn;
	@FindBy(xpath = "//select[@id='subjectDropDown']")

	WebElement SubjectDropDown;
	@FindBy(id = "editSubjectDropDown")
	WebElement EditSubject;

	@FindBy(id = "editChapterDropDown")
	WebElement EditChapter;

	@FindBy(id = "subSubjectDiv")
	WebElement SubSubjectDropDwn;

	@FindBy(id = "subSubjectDiv")
	List<WebElement> SubSubject;

	@FindBy(id = "chapterDropDown")
	WebElement ChapterDropDown;

	@FindBy(id = "notesDescription")
	WebElement Description;

	@FindBy(id = "addlessonSubmit")
	WebElement Submit;

	@FindBy(xpath = "//*[@id='add-note']//*[@style='padding: 50px;']//*[@class='row']//*[@id='auploadfiles']/..")
	WebElement Attachment;

	@FindBy(xpath = "//*[@id='allnoteDiv']//div[@class='subject-list mb20']")
	public List<WebElement> notesList;

	@FindBy(xpath = "//*[@class='list-inline']//a[contains(@href,'chapter')]")
	public List<WebElement> viewLesson;

	@FindBy(xpath = "//*[@id='add-note']//button[@class='close']")
	public List<WebElement> addNotesClosePopup;

	@FindBy(xpath = "//*[@id='editnoteModalBox']//button[@class='close']")
	public List<WebElement> editNotesClosePopup;

	@FindBy(xpath = "//*[@id='delete']//button[@class='close']")
	public List<WebElement> deleteNotesClosePopup;

	@FindBy(xpath = "//*[@id='share-note']//button[@class='close']")
	public List<WebElement> shareNotesClosePopup;

	public List<WebElement> NotesCol(int row, int col) {

		List<WebElement> element = driver.findElements(
				By.xpath("((//*[@id='allnoteDiv']//div[@class='subject-list mb20'])[" + row + "]//span)[" + col + "]"));
		return element;

	}

	@FindBy(xpath = "//a[@title='Edit Notes']")
	List<WebElement> EditNotes;

	@FindBy(id = "saveedit")
	WebElement SaveEdit;

	@FindBy(xpath = "//a[@title='Delete Notes']")
	List<WebElement> DeleteNote;

	@FindBy(xpath = "//button[@id='deleteButton']")
	WebElement DeleteButton;

	@FindBy(xpath = "//a[@title='Share Notes']")
	List<WebElement> shareNotes;

	@FindBy(xpath = "//*[@id='sharetype']")
	WebElement selectContact;

	@FindBy(xpath = "//*[@class='checkbox']/input")
	WebElement checkContact;

	@FindBy(xpath = "//*[@id='bagikan']")
	List<WebElement> shareNotesBtn;

	@FindBy(xpath = "//*[@id='share-success']")
	public WebElement shareNotesSuccess;

	public NotesPage(WebDriver driver, ExtentTest test) throws Exception {
		super(driver, test);
		PageFactory.initElements(driver, this);
	}

	LoginPage lp = new LoginPage(driver, test);

	public boolean AddNotes(String Title, String Class, String Subject, String Chapter, String description)
			throws Exception {
		try {
			clickElement(AddNotes, "AddNotes");
			Thread.sleep(1000);
			fillData(AddTitle, "Title", Title);
			Select sel = new Select(ClassDropDwn);
			Thread.sleep(1000);
			sel.selectByVisibleText(Class);
			Thread.sleep(3000);
			SubjectDropDown.click();
			String selectedSubject = selectData(this.SubjectDropDown, "SubjectDropDown", 1);
			Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);
			Thread.sleep(100);
			DataUtil.setData(xls, "StudentNotesTest", 1, "Subject", selectedSubject);
			System.out.println(Subject);
			Thread.sleep(100);
			/*
			 * int SubSubjectPresent = SubSubject.size();
			 * System.out.println(SubSubjectPresent);
			 * 
			 * if (SubSubjectPresent != 0) { SubSubjectDropDwn.click(); Select sel2 = new
			 * Select(SubSubjectDropDwn); sel2.selectByIndex(3); }
			 */

			Thread.sleep(3000);
			ChapterDropDown.click();
			String chapter = selectData(this.ChapterDropDown, "ChapterDropDown", 1);
			fillData(this.Description, "Description", description);
			Thread.sleep(100);
			DataUtil.setData(xls, "StudentNotesTest", 1, "Chapter", chapter);
			Thread.sleep(100);
			String imagePath = Constants.Image_PATH + "test.png";
			File file = new File(imagePath);

			fillData(this.Attachment, "Attachment", file.getAbsolutePath());
			Thread.sleep(5000);
			lp.takeScreenShot();
			clickElement(this.Submit, "Submit");
			Thread.sleep(5000);
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.invisibilityOfElementLocated(By.id("addlessonSubmit")));
			return true;
		} catch (Exception e) {
			if (addNotesClosePopup.size() != 0) {
				if (this.addNotesClosePopup.get(0).isDisplayed()) {
					clickElement(addNotesClosePopup, 0, "closePopup");
				}
			}
			return false;
		}

	}

	public List<HashMap<String, String>> viewNotes(String Title, String Class, String Subject, String Chapter,
			String description) throws IOException, InterruptedException {
		List<HashMap<String, String>> notes = new ArrayList<HashMap<String, String>>();
		try {
			Thread.sleep(3000);

			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.visibilityOfAllElements(this.notesList));
			for (int i = 1; i <= this.notesList.size(); i++) {
				((JavascriptExecutor) driver).executeScript("scroll(0,200)");
				Thread.sleep(1000);
				HashMap<String, String> notesData = new HashMap<String, String>();
				notesData.put("Subject", this.NotesCol(i, 1).get(0).getText().trim());
				notesData.put("Title", this.NotesCol(i, 2).get(0).getText().trim());
				notesData.put("CreatedDate", this.NotesCol(i, 3).get(0).getText().trim());
				notes.add(notesData);
				System.out.println("Subject" + " : " + this.NotesCol(i, 1).get(0).getText().trim());
				test.log(LogStatus.INFO, "Subject" + " : " + this.NotesCol(i, 1).get(0).getText().trim());
			}
		} catch (Exception e) {
			test.log(LogStatus.INFO, "Getting error on View notes" + e.getMessage());
			System.out.println("Getting error on View notes" + e.getMessage());
			notes = null;
		}
		return notes;
	}

	public boolean EditNotes(String Title, String Class, String Subject, String Chapter, String description,
			String editSubject, String editChapter, int i) throws Exception {
		try {
			int TotalNotes = EditNotes.size();
			if (TotalNotes != 0) {
				// for (int i = 0; i < TotalNotes; i++) {
				Thread.sleep(4000);
				clickElement(EditNotes, i, "EditNotes");
				Thread.sleep(2000);
				test.log(LogStatus.INFO, "Edit notes clicked");
				lp.takeScreenShot();
				EditSubject.click();
				String selectededitSubject = selectData(this.EditSubject, "EditSubject", 2);
				Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);
				Thread.sleep(100);
				DataUtil.setData(xls, "StudentNotesTest", 1, "EditSubject", selectededitSubject);
				Thread.sleep(100);
				EditChapter.click();
				String selectedEditCahpter = selectData(this.EditChapter, "EditChapter", 2);
				Thread.sleep(100);
				DataUtil.setData(xls, "StudentNotesTest", 1, "EditChapter", selectedEditCahpter);
				Thread.sleep(100);

				lp.takeScreenShot();
				test.log(LogStatus.INFO, "Editing Notes");
				clickElement(this.SaveEdit, "SaveEdit");
				WebDriverWait wt = new WebDriverWait(driver, 60);
				wt.until(ExpectedConditions.invisibilityOfElementLocated(By.id("saveedit")));
				Thread.sleep(5000);
			} else {
				test.log(LogStatus.INFO, "Notes Record not found on grid");
				System.out.println("Notes Record not found on grid");
			}
			return true;
		} catch (Exception e) {
			if (editNotesClosePopup.size() != 0) {
				if (this.editNotesClosePopup.get(0).isDisplayed()) {
					clickElement(editNotesClosePopup, 0, "closePopup");
				}
			}
			return false;
		}
	}

	int deleteIteration = 1;

	public HashMap<String, String> DeleteNotes(int i, String Title, String Class, String Subject, String Chapter,
			String description) throws IOException, InterruptedException {
		List<HashMap<String, String>> notes = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> notesData = new HashMap<String, String>();
		try {
			int deleteNotes = DeleteNote.size();
			if (deleteNotes != 0) {
				System.out.println(deleteNotes);
				WebDriverWait wt = new WebDriverWait(driver, 20);
				wt.until(ExpectedConditions.visibilityOfAllElements(DeleteNote));
				notes = viewNotes(Title, Class, Subject, Chapter, description);
				if (notes.size() != 0) {
					notesData = notes.get(i);
				} else {

				}

				Thread.sleep(3000);
				clickElement(DeleteNote, i, "DeleteNote");
				Thread.sleep(300);
				clickElement(DeleteButton, "DeleteButton");
				Thread.sleep(3000);
			} else {
				test.log(LogStatus.INFO, "No Records found for delete Notes");
				Reporter.log("No Records found for delete Notes");
				System.out.println("No Records found for delete Notes");

			}

		} catch (Exception e) {

		}
		return notesData;
	}

	public boolean shareNotes(int i, String shareType) throws Exception {
		boolean shareNotesFlag = false;
		try {
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.visibilityOfAllElements(shareNotes));
			if (shareNotes.size() != 0) {
				shareNotes.get(i).click();
				Thread.sleep(3000);
				selectContact.click();
				// Select mentor = new Select(selectContact);
				Thread.sleep(1000);
				// mentor.selectByVisibleText(shareType);
				selectData(selectContact, "selectContact", 1);
				Thread.sleep(1000);

				if (this.shareNotesBtn.size() != 0) {
					if (this.shareNotesBtn.get(0).getText().trim().equalsIgnoreCase("Share (0)")) {
						this.checkContact.click();
					} else {

					}
					this.shareNotesBtn.get(0).click();
					Thread.sleep(1000);
					wt.until(ExpectedConditions.textToBePresentInElement(shareNotesSuccess, "Successfully share !"));
				} else {
					test.log(LogStatus.INFO, "No contact found to share");
					Reporter.log("No contact found to share");
					System.out.println("No contact found to share");
				}
				shareNotesFlag = true;
			}
		} catch (Exception e) {
			if (shareNotesClosePopup.size() != 0) {
				if (this.shareNotesClosePopup.get(0).isDisplayed()) {
					clickElement(shareNotesClosePopup, 0, "shareNotesClosePopup");
				}

			}
		}
		return shareNotesFlag;
	}

	public List<HashMap<String, String>> viewNotesFromOtherUser(String Title, String Class, String Subject,
			String Chapter, String description) throws IOException, InterruptedException {
		Thread.sleep(3000);
		List<HashMap<String, String>> notes = new ArrayList<HashMap<String, String>>();
		WebDriverWait wt = new WebDriverWait(driver, 30);
		wt.until(ExpectedConditions.visibilityOfAllElements(notesList));
		for (int i = 1; i < notesList.size(); i++) {
			((JavascriptExecutor) driver).executeScript("scroll(0,200)");
			Thread.sleep(1000);
			HashMap<String, String> notesData = new HashMap<String, String>();
			notesData.put("Subject", NotesCol(i, 1).get(0).getText().trim());
			notesData.put("Title", NotesCol(i, 2).get(0).getText().trim());
			notesData.put("CreatedDate", NotesCol(i, 3).get(0).getText().trim());
			notes.add(notesData);
			System.out.println("Subject" + " : " + NotesCol(i, 1).get(0).getText().trim());
			test.log(LogStatus.INFO, "Subject" + " : " + NotesCol(i, 1).get(0).getText().trim());
		}
		return notes;
	}

	public Object viewLesson(int i, String Title, String Class, String Subject, String Chapter, String description)
			throws IOException, InterruptedException {
		HashMap<String, String> notesData = new HashMap<String, String>();
		try {
			Thread.sleep(3000);
			List<HashMap<String, String>> notes = new ArrayList<HashMap<String, String>>();
			WebDriverWait wt = new WebDriverWait(driver, 30);
			wt.until(ExpectedConditions.visibilityOfAllElements(viewLesson));
			notes = viewNotes(Title, Class, Subject, Chapter, description);
			notesData = notes.get(i);
			viewLesson.get(i).click();
			System.out.println(notesData);
		} catch (Exception e) {

		}

		if (notesData.get(i) == null) {
			return "";
		} else {
			return notesData;
		}
	}
}
