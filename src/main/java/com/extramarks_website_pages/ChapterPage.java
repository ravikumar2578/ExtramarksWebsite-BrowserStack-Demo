package com.extramarks_website_pages;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import com.extramarks_website_utils.Constants;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
public class ChapterPage extends BasePage {

	public ChapterPage(WebDriver dr, ExtentTest t) throws Exception {
		super(dr, t);
		PageFactory.initElements(driver, this);
		defaultRun();
	}

	@FindBy(xpath = "//div[@id='pl-table-cont13']//div[@class='media-body media-middle text-left']")
	public List<WebElement> TestLevel;

	@FindBy(xpath = "//*[@class='btn practise-btn orange ng-scope']")
	public WebElement ChooseTest;

	@FindBy(xpath = "//div[@class='modal fade ng-scope in']//*[@class='close']")
	public WebElement ClosePopup;

	@FindBy(xpath = "//div[@class='row']//li[@ng-repeat='ques in testData.data']")
	public List<WebElement> TotalQues;

	@FindBy(xpath = "//a[contains(text(),'Finish')]")
	public WebElement FinishBtn;

	@FindBy(xpath = "//a[@class='btn jawab-btn']")
	public WebElement StartTest;

	@FindBy(xpath = "//ul[@class='table-list ng-scope']//a[@id='serviceType.serviceId']")
	List<WebElement> TableContent;

	@FindBy(xpath = "//div[@class='postlogin-card ng-scope']//*[@id='service-tab-Learn']//*[contains(@class,'lesson')]")
	public List<WebElement> Lesson;

	@FindBy(xpath = "//ul[@class='table-list ng-scope']//a[@ng-click]")
	List<WebElement> Table;

	@FindBy(xpath = "//*[@id='lptmaincontroller']//a[@ng-click='goBack()']")
	public List<WebElement> BackToChapter;

	LoginPage lp = new LoginPage(driver, test);

	public List<WebElement> getTableContent() {
		return TableContent;
	}

	// MCQ Report
	@FindBy(xpath = "//*[@class='postlogin-card']//*[@class='col-sm-12']//*[contains(@class,'highcharts-point')]")
	public List<WebElement> qWAQuestions;

	@FindBy(xpath = "//*[@class='postlogin-card']//*[@class='col-sm-12']//*[contains(@class,'highcharts-label highcharts-tooltip')]")
	public List<WebElement> qWAQuestionsToolTip;

	@FindBy(xpath = "//*[@class='postlogin-card']//*[@class='col-sm-12']//*[contains(@class,'highcharts-label highcharts-tooltip')]//*[@data-z-index]//*[@style][1]")
	public WebElement qWAQuestionsToolTipColor;

//.......................................................LEARN...............................................................
	public List<WebElement> getLearnTB() throws Exception {
		ChapterPage chPg = new ChapterPage(driver, test);
		List<WebElement> LearnTb = driver.findElements(By.xpath("//a[@id='learn-panel']"));
		return LearnTb;
	}

	public void clickLearn() throws Exception {
		ChapterPage chPg = new ChapterPage(driver, test);
		List<WebElement> LearnTb = driver.findElements(By.xpath("//a[@id='learn-panel']"));
		LearnTb.get(0).click();
	}

	public List<WebElement> getConceptLearning() throws Exception {
		ChapterPage chPg = new ChapterPage(driver, test);
		List<WebElement> ConceptLearning = driver
				.findElements(By.xpath("//div[@class='mb10  ng-scope lpt-service-thumb']"));
		return ConceptLearning;
	}

	public void clickConceptLearning() throws Exception {
		ChapterPage chPg = new ChapterPage(driver, test);
		List<WebElement> ConceptLearning = driver
				.findElements(By.xpath("//div[@class='mb10  ng-scope lpt-service-thumb']"));
		ConceptLearning.get(0).click();
	}

	public void ConceptLearning() throws Exception {
		ChapterPage chPg = new ChapterPage(driver, test);
		WebDriverWait wt = new WebDriverWait(driver, 30);
		int framePresent1 = driver.findElements(By.xpath("//iframe[@id='fulscr']")).size();
		if (framePresent1 == 0) {
			Thread.sleep(3000);
			driver.navigate().back();
		} else {
			driver.switchTo().frame("fulscr");
			wt.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='component_base std outline']")));

			driver.findElement(By.xpath("//button[@class='component_base std outline']")).click();

			List<WebElement> outlineDiv = driver.findElements(By.xpath("//div[@class='outline']//div[@class='thumb']"));
			int outSize = outlineDiv.size();
			WebElement NextBtn = driver.findElement(By.xpath("//button[@class='component_base next']"));
			// WebElement PrevBtn=
			// driver.findElement(By.xpath("//button[@class='component_base prev']"));
			System.out.println("Total slides  = " + outSize);

			for (int k = 0; k <= outSize - 2; k++) {
				wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='component_base next']")));

				NextBtn.click();
				int j = k + 1;
				test.log(LogStatus.INFO, "Open" + j + " Video");

				Thread.sleep(1000);
				lp.takeScreenShot();
			}

			/*
			 * for (int l = 0; l <= outSize - 2; l++) { Thread.sleep(1000); PrevBtn.click();
			 * int j= l+1; test.log(LogStatus.INFO, "Open"+j+" Video"); Thread.sleep(1000);
			 * lp.takeScreenShot(); }
			 */
			if (BackToChapter.size() != 0) {
				JavascriptExecutor jslpt = (JavascriptExecutor) driver;
				jslpt.executeScript("arguments[0].click();", BackToChapter.get(0));
			} else {
				driver.navigate().back();
			}
		}
	}

	public List<WebElement> getDetailedLearning() throws Exception {
		ChapterPage chPg = new ChapterPage(driver, test);
		List<WebElement> DetailedLearning = driver.findElements(By.xpath(
				"//*[@id='lptmaincontroller']//*[@id='modal-displaydata-Learn']//div[contains(@ng-if,'Detailed Learning')]//div[@ng-repeat]"));
		return DetailedLearning;
	}

	public boolean clickQuickLearning() throws Exception {
		
		try {
			List<WebElement> QuickLearning = driver.findElements(By.xpath(
					"//*[@id='modal-displaydata-Learn']//div[contains(@ng-if,'Quick Learning')]//div[@ng-repeat]"));
			clickElement(By.xpath(
					"//*[@id='modal-displaydata-Learn']//div[contains(@ng-if,'Quick Learning')]//div[@ng-repeat]"), 0,
					"QuickLearning");
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public List<WebElement> getQuickLearning() {

		List<WebElement> DetailedLearning = driver.findElements(By
				.xpath("//*[@id='modal-displaydata-Learn']//div[contains(@ng-if,'Quick Learning')]//div[@ng-repeat]"));
		return DetailedLearning;
	}

	public boolean QuickLearning() throws Exception {
		ChapterPage chPg = new ChapterPage(driver, test);
		boolean quickTest = false;
		try {
			((JavascriptExecutor) driver).executeScript("scroll(0,-500)");
			Thread.sleep(5000);
			test.log(LogStatus.INFO, "Opening Quick Learning");
			driver.switchTo().frame("fulscr");
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='mindmapTree']"))));
			if (driver.findElement(By.xpath("//*[@id='mindmapTree']")).isDisplayed()) {
				test.log(LogStatus.INFO, "Quick Learning Page Present");
				lp.takeScreenShot();
				quickTest = true;
			} else {
				test.log(LogStatus.INFO, "Quick Learning Page not Present");
				lp.takeScreenShot();

			}
			driver.switchTo().defaultContent();
			if (BackToChapter.size() != 0) {
				JavascriptExecutor jslpt = (JavascriptExecutor) driver;
				jslpt.executeScript("arguments[0].click();", BackToChapter.get(0));
			} else {
				driver.navigate().back();
			}
		} catch (Exception e) {
			driver.switchTo().defaultContent();
			if (BackToChapter.size() != 0) {
				JavascriptExecutor jslpt = (JavascriptExecutor) driver;
				jslpt.executeScript("arguments[0].click();", BackToChapter.get(0));
			} else {
				driver.navigate().back();
			}

		}
		return quickTest;
	}

	public boolean clickDetailedLearning() {

		try {
			List<WebElement> DetailedLearning = driver.findElements(By.xpath(
					"//*[@id='modal-displaydata-Learn']//div[contains(@ng-if,'Detailed Learning')]//div[@ng-repeat]"));
			clickElement(DetailedLearning, 0, "DetailedLearning");
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean DetailedLearning() throws Exception {
		ChapterPage chPg = new ChapterPage(driver, test);
		boolean detailLearningResult = false;
		WebDriverWait wt = new WebDriverWait(driver, 60);
		int framePresent1 = driver.findElements(By.xpath("//iframe[@id='fulscr']")).size();
		if (framePresent1 == 0) {
			Thread.sleep(1000);
			if (BackToChapter.size() != 0) {
				JavascriptExecutor jslpt = (JavascriptExecutor) driver;
				jslpt.executeScript("arguments[0].click();", BackToChapter.get(0));
			} else {
				driver.navigate().back();
			}
			Thread.sleep(5000);
		} else {
			boolean result = fluentWaitIsDisplay(driver.findElements(By.xpath("//iframe[@id='fulscr']")), 0, 60,
					"Detail Learning Screen");
			if (result) {

				try {
					driver.switchTo().frame((driver.findElements(By.xpath("//iframe")).get(0)));
					int framePresent2 = driver.findElements(By.xpath("//iframe")).size();
					if (framePresent2 != 0) {
						driver.switchTo().frame((driver.findElements(By.xpath("//iframe")).get(0)));
					}
					List<WebElement> videos = driver.findElements(By.xpath("//video[@src]"));
					if (videos.size() != 0) {
						System.out.println("Detail Learning, Video is Present, Opening Video");
						test.log(LogStatus.INFO, "Detail Learning, Video is Present, Opening Video");
						Thread.sleep(1000);
						lp.takeScreenShot();
						String src = driver.findElement(By.xpath("//video[@src]")).getAttribute("src");
						if (src.contains("http")) {

						} else {
							File f = new File(src);

							// Get the absolute path of file f
							String absoluteSrc = f.getAbsolutePath();
							src = absoluteSrc;
						}
						URI uri = new URI(src);
						URL url = uri.toURL();
						HttpURLConnection connection = (HttpURLConnection) url.openConnection();
						String response = "";
						try {
							connection.connect();
							response = connection.getResponseMessage();

							connection.disconnect();
						} catch (Exception exp) {
						}
						if (response != null || response != "") {
							test.log(LogStatus.INFO, "Video is not blank and response is " + response);
							System.out.println("Video is not blank and response is " + response);
							Thread.sleep(1000);
							lp.takeScreenShot();
						}

					} else {

						((JavascriptExecutor) driver).executeScript("scroll(0,400)");
						Thread.sleep(1000);
						wt.until(ExpectedConditions
								.elementToBeClickable(By.xpath("//button[@class='component_base std outline']")));

						driver.findElement(By.xpath("//button[@class='component_base std outline']")).click();

						List<WebElement> outlineDiv = driver
								.findElements(By.xpath("//div[@class='outline']//div[@class='thumb']"));
						int outSize = outlineDiv.size();
						Thread.sleep(1000);
						WebElement NextBtn = driver.findElement(By.xpath("//button[@class='component_base next']"));
						// WebElement PrevBtn=
						// driver.findElement(By.xpath("//button[@class='component_base prev']"));
						System.out.println("Total slides  = " + outSize);

						for (int k = 0; k <= outSize - 2; k++) {
							clickElement(NextBtn, "NextBtn");
							int j = k + 1;
							test.log(LogStatus.INFO, "Open" + j + " Video");

							Thread.sleep(1000);
							lp.takeContentScreenshot(driver.findElement(By.xpath("//*[@id='content']")));
						}
					}

					if (BackToChapter.size() != 0) {
						JavascriptExecutor jslpt = (JavascriptExecutor) driver;
						jslpt.executeScript("arguments[0].click();", BackToChapter.get(0));
					} else {
						driver.navigate().back();
					}
					detailLearningResult = true;
				} catch (Exception e) {
					if (BackToChapter.size() != 0) {
						JavascriptExecutor jslpt = (JavascriptExecutor) driver;
						jslpt.executeScript("arguments[0].click();", BackToChapter.get(0));
					} else {
						driver.navigate().back();
					}

				}
			} else {

			}
		}
		return detailLearningResult;

	}

	public boolean clickMiscellaneous() {
	
		try {
			((JavascriptExecutor) driver).executeScript("scroll(0,800)");
			List<WebElement> Miscellaneous = driver.findElements(By.xpath(
					"//*[@id='modal-displaydata-Learn']//div[contains(@ng-if,'Miscellaneous')]//div[@ng-repeat]"));
			boolean clickMisc = clickElement(Miscellaneous, 0, "Miscellaneous");
			Thread.sleep(6000);
			if (clickMisc) {
				lp.takeScreenShot();
				if (BackToChapter.size() != 0) {
					JavascriptExecutor jslpt = (JavascriptExecutor) driver;
					jslpt.executeScript("arguments[0].click();", BackToChapter.get(0));
				} else {
					driver.navigate().back();
				}
			} else {
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public List<WebElement> getMiscellaneous() {

		List<WebElement> Miscellaneous = driver.findElements(
				By.xpath("//*[@id='modal-displaydata-Learn']//div[contains(@ng-if,'Miscellaneous')]//div[@ng-repeat]"));
		return Miscellaneous;
	}

	public boolean Miscellaneous() throws Exception {

		try {
			WebDriverWait wt = new WebDriverWait(driver, 30);
			((JavascriptExecutor) driver).executeScript("scroll(0,600)");
			int miscSize = getMiscellaneous().size();

			test.log(LogStatus.INFO, "List of Animation contains " + miscSize + " Animations");
			System.out.println("List of Animation contains " + miscSize + " Animations");
			lp.takeScreenShot();
			if (miscSize != 0) {
				for (int j = 0; j < miscSize; j++) {
					int misNum = j + 1;
					test.log(LogStatus.INFO, "Animation  " + misNum + " = " + getMiscellaneous().get(j).getText());
					System.out.println("Animation  " + misNum + " = " + getMiscellaneous().get(j).getText());
				}
			}
			return true;
		} catch (Exception e) {
			test.log(LogStatus.FAIL,
					"Exception on Miscellaneous Page" + e.getMessage() + " Location is " + driver.getCurrentUrl());
			lp.takeScreenShot();
			return false;
		}

	}

	public List<WebElement> getGuideLines() {
	
		List<WebElement> GuideLines = driver.findElements(By.xpath("//p[contains(text(),'Guidelines')]"));
		return GuideLines;
	}

	public void Guidelines() throws InterruptedException {
	
		WebDriverWait wt = new WebDriverWait(driver, 20);
		getGuideLines().get(0).click();
		driver.switchTo().frame("fulscr");
		wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='component_base std outline']")));

		driver.findElement(By.xpath("//button[@class='component_base std outline']")).click();

		List<WebElement> outlineDiv = driver.findElements(By.xpath("//div[@class='outline']//div[@class='thumb']"));
		int outSize = outlineDiv.size();
		WebElement NextBtn = driver.findElement(By.xpath("//button[@class='component_base next']"));
		WebElement PrevBtn = driver.findElement(By.xpath("//button[@class='component_base prev']"));
		System.out.println("Total slides  = " + outSize);

		for (int k = 0; k <= outSize - 2; k++) {
			wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='component_base next']")));

			NextBtn.click();
			int j = k + 1;
			test.log(LogStatus.INFO, "Open" + j + " Video");

			Thread.sleep(1000);
			lp.takeScreenShot();
		}

		/*
		 * for (int l = 0; l <= outSize - 2; l++) { Thread.sleep(1000); PrevBtn.click();
		 * int j=l+1; test.log(LogStatus.INFO, "Open"+j+" Video");
		 * 
		 * Thread.sleep(1000); lp.takeScreenShot(); }
		 */
		if (BackToChapter.size() != 0) {
			BackToChapter.get(0).click();
		} else {
			driver.navigate().back();
		}
	}

//.................................................................PRACTISE...........................................................

	public List<WebElement> getPracticeTb() {

		List<WebElement> PracticeTb = driver.findElements(By.xpath("//a[@id='practise-panel']"));

		return PracticeTb;
	}

	public void clickPractise() {
	
		List<WebElement> PracticeTb = driver.findElements(By.xpath("//a[@id='practise-panel']"));
		PracticeTb.get(0).click();

	}

	public List<WebElement> getMotionGallery() throws InterruptedException {

		List<WebElement> MotionGallery = driver.findElements(By.xpath("//p[contains(text(),'Motion Gallery')]"));
		return MotionGallery;
	}

	public boolean MotionGallery() throws Exception {
		
		WebDriverWait wt = new WebDriverWait(driver, 40);
		boolean clickMotionGallery = clickElement(By.xpath("//p[contains(text(),'Motion Gallery')]"), 0, "getMotionGallery");

		if (clickMotionGallery) {
			try {
				int tableSize = Table().size();
				System.out.println("No. of content = " + tableSize);
				for (int z = 0; z < tableSize; z++) {
					wt.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//p[contains(text(),'Motion Gallery')]")));
					clickElement(getMotionGallery(), 0, "getMotionGallery");

					wt.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
							By.xpath("//ul[@class='table-list ng-scope']//a[@ng-click]")));
					Table().get(z).click();
					System.out.println("S.No.= " + z);
					List<WebElement> frame = driver.findElements(By.xpath("//iframe[@id='fulscr']"));
					int framePresent = frame.size();

					if (framePresent == 0) {
						Thread.sleep(3000);
						test.log(LogStatus.INFO, "Video is playing");

						lp.takeScreenShot();
						driver.navigate().back();
					}
				}

			} catch (Exception e) {
				test.log(LogStatus.FAIL,
						"Error on MotionGallery " + e.getMessage() + " Location is " + driver.getCurrentUrl());
				lp.takeScreenShot();
				driver.navigate().back();
			}

			return true;
		} else {
			test.log(LogStatus.FAIL, "Unable to open Motion Gallery");
			System.out.println("Unable to open Motion Gallery");
			return false;
		}

	}

	public List<WebElement> Table() {
	
		List<WebElement> Table = driver.findElements(By.xpath("//ul[@class='table-list ng-scope']//a[@ng-click]"));
		return Table;
	}

	public List<WebElement> LessonTable() {
	
		List<WebElement> LessonTable = driver.findElements(By.xpath("//ul[@class='table-list']//a[@ng-click]"));
		return LessonTable;
	}

	public void getTable() {

		Table().size();
	}

	public List<WebElement> getCaseStudy() throws InterruptedException {
		
		List<WebElement> CaseStudy = driver.findElements(By.xpath("//p[contains(text(),'Case Study')]"));
		return CaseStudy;
	}

	public void CaseStudy() throws Exception {
		ChapterPage chPg = new ChapterPage(driver, test);
		try {
			getCaseStudy().get(0).click();
		} catch (Exception e) {
			test.log(LogStatus.FAIL,
					"Error on click on CaseStudy " + e.getMessage() + " Location is " + driver.getCurrentUrl());
			lp.takeScreenShot();

		}
		try {
			WebDriverWait wt = new WebDriverWait(driver, 30);
			test.log(LogStatus.INFO, "Open Case Study");
			lp.takeScreenShot();

			int framePresent1 = driver.findElements(By.xpath("//iframe[@id='fulscr']")).size();
			if (framePresent1 == 0) {
				Thread.sleep(5000);
				lp.takeScreenShot();
				driver.navigate().back();
			} else {
				driver.switchTo().frame("fulscr");
				wt.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//button[@class='component_base std outline']")));

				driver.findElement(By.xpath("//button[@class='component_base std outline']")).click();

				List<WebElement> outlineDiv = driver
						.findElements(By.xpath("//div[@class='outline']//div[@class='thumb']"));
				int outSize = outlineDiv.size();
				WebElement NextBtn = driver.findElement(By.xpath("//button[@class='component_base next']"));
				WebElement PrevBtn = driver.findElement(By.xpath("//button[@class='component_base prev']"));
				System.out.println("Total slides  = " + outSize);

				for (int k = 0; k <= outSize - 2; k++) {
					wt.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//button[@class='component_base next']")));

					NextBtn.click();
					int j = k + 1;
					test.log(LogStatus.INFO, "Open" + j + " Video");

					Thread.sleep(1000);
					lp.takeScreenShot();
				}

				/*
				 * for (int l = 0; l <= outSize - 2; l++) { Thread.sleep(1000); PrevBtn.click();
				 * int j=l+1; test.log(LogStatus.INFO, "Open"+j+" Video");
				 * 
				 * Thread.sleep(1000); lp.takeScreenShot(); }
				 */
				driver.navigate().back();
			}
		} catch (Exception e) {

			test.log(LogStatus.FAIL, "Error on CaseStudy" + e.getMessage() + " Location is " + driver.getCurrentUrl());
			lp.takeScreenShot();
			driver.navigate().back();
		}

	}

	public List<WebElement> getQA() {

		List<WebElement> QA = driver.findElements(By.xpath(
				"//div[@class='postlogin-card ng-scope']//*[@id='service-tab-Practice']//*[@class='qa']/parent::a"));
		return QA;
	}

	public List<WebElement> multiChoiceQA() {
	
		List<WebElement> selectAns = driver.findElements(By.xpath("//div[@class='row ng-scope']//form//div[4]"));
		return selectAns;
	}

	public List<WebElement> typedQA() {

		List<WebElement> TypedAns = driver.findElements(By.xpath("//textarea[@class='form-control qa-textarea']"));
		return TypedAns;
	}

	public boolean QA() throws Exception {
		ChapterPage chPg = new ChapterPage(driver, test);
		try {
			List<WebElement> AnswerTab1 = driver
					.findElements(By.xpath("//div[@class='postlogin-card ng-scope']//*[@class='col-sm-3']//a"));// 69

			int ansSize = AnswerTab1.size();
			test.log(LogStatus.INFO, "Total Questions in QA= " + ansSize);
			System.out.println("Total answers : " + ansSize);
			Thread.sleep(2000);
			for (int i = 0; i < ansSize; i++) {
				List<WebElement> AnswerTab = driver
						.findElements(By.xpath("//div[@class='postlogin-card ng-scope']//*[@class='col-sm-3']//a"));
				Actions ac = new Actions(driver);
				ac.moveToElement(AnswerTab.get(i)).build().perform();
				clickElement(AnswerTab, i, "AnswerTab :" + i);
				Thread.sleep(1000);
				lp.takeScreenShot();
				Thread.sleep(1000);
				if (multiChoiceQA().get(i).isDisplayed()) {
					clickElement(multiChoiceQA(), i, "Selecting Answer : " + i);
					test.log(LogStatus.INFO, "Answering Questions number( Multiple Choice ) in QA= " + i);
					System.out.println("Answering Questions number( Multiple Choice ) in QA= " + i);
					Thread.sleep(3000);
					WebElement closePopup = driver
							.findElement(By.xpath("//div[@class='modal fade ng-scope in']//*[@class='close']"));
					clickElement(closePopup, "closePopup");
					Thread.sleep(3000);

				} else if (typedQA().get(i).isDisplayed()) {
					typedQA().get(i).sendKeys("Test");
					Thread.sleep(1000);
				}
				Thread.sleep(1000);
				lp.takeScreenShot();
			}
			driver.navigate().back();
			return true;
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Error on QA " + e.getMessage() + " Location is " + driver.getCurrentUrl());
			lp.takeScreenShot();
			driver.navigate().back();
			return false;
		}
	}

	public List<WebElement> getTopicWiseQA() {
	
		List<WebElement> TopicWiseQA = driver.findElements(By.xpath("//p[contains(text(),'Topicwise QA')]"));
		return TopicWiseQA;
	}

	public void TopicwiseQA() throws Exception {
		ChapterPage chPg = new ChapterPage(driver, test);
		try {
			getTopicWiseQA().get(0).click();
		} catch (Exception e) {
			test.log(LogStatus.FAIL,
					"Error on click on TopicwiseQA " + e.getMessage() + " Location is " + driver.getCurrentUrl());
			lp.takeScreenShot();

		}
		try {

			test.log(LogStatus.INFO, "Opening Topic Wise QA");
			List<WebElement> Answers = driver.findElements(By.xpath("//a[contains(text(),'Answer')]"));
			int AnsCount = Answers.size();
			test.log(LogStatus.INFO, "Total questions in TopicWiseQA = " + AnsCount);
			System.out.println("Number of Questions = " + AnsCount);
			driver.navigate().back();
		} catch (Exception e) {
			test.log(LogStatus.FAIL,
					"Error on TopicwiseQA  " + e.getMessage() + " Location is " + driver.getCurrentUrl());
			lp.takeScreenShot();
			driver.navigate().back();

		}
	}

	public List<WebElement> getAssignment() {
		
		List<WebElement> Assignment = driver.findElements(By.xpath("//p[contains(text(),'Assignment')]"));
		return Assignment;
	}

	public List<WebElement> getConceptCraft() {
		
		List<WebElement> ConceptCraft = driver.findElements(By.xpath("//p[contains(text(),'Concept Craft')]"));
		return ConceptCraft;
	}

	public void ConceptCraft() throws Exception {
		ChapterPage chPg = new ChapterPage(driver, test);
		try {
			getConceptCraft().get(0).click();

		} catch (Exception e) {
			test.log(LogStatus.FAIL,
					"Error on click on ConceptCraft " + e.getMessage() + " Location is " + driver.getCurrentUrl());
			lp.takeScreenShot();

		}
		try {
			int TableSize = Table().size();
			System.out.println("Table of Content = " + TableSize);
			if (TableSize == 0) {
				Thread.sleep(2000);
				System.out.println("Opening Concept Craft");
				test.log(LogStatus.INFO, "Opening Concept Craft");
				lp.takeScreenShot();
				driver.navigate().back();
			} else {
				for (int a1 = 0; a1 < TableSize; a1++) {
					getPracticeTb().get(0).click();
					getConceptCraft().get(0).click();
					Thread.sleep(2000);
					Table().get(a1).click();
					System.out.println("Opening" + " " + a1 + " Concept Craft");
					test.log(LogStatus.INFO, "Opening" + " " + a1 + " Concept Craft");

					Thread.sleep(2000);
					lp.takeScreenShot();
					driver.navigate().back();
				}
			}

		} catch (Exception e) {
			test.log(LogStatus.FAIL,
					"Error on ConceptCraft " + e.getMessage() + " Location is " + driver.getCurrentUrl());
			lp.takeScreenShot();
			driver.navigate().back();

		}
	}

	/*
	 * public List<WebElement> getProjects() { List<WebElement> Projects =
	 * driver.findElements(By.xpath("//p[contains(text(),'Projects')]")); return
	 * Projects; } public void Projects() { getProjects().get(0).click(); }
	 */

	public void Assignment() throws Exception {
		ChapterPage chPg = new ChapterPage(driver, test);
		try {
			getAssignment().get(0).click();
		} catch (Exception e) {
			test.log(LogStatus.FAIL,
					"Error on click on Assignment " + e.getMessage() + " Location is " + driver.getCurrentUrl());
			lp.takeScreenShot();

		}
		try {
			int Tablesize = Table().size();
			System.out.println("Table of Content = " + Tablesize);
			if (Tablesize == 0) {
				Thread.sleep(2000);
				List<WebElement> ViewAns = driver.findElements(By.xpath("//*[contains(text(),'View Answer')]"));
				int Questions = ViewAns.size();
				test.log(LogStatus.INFO, "No. of questions : " + Questions);
				lp.takeScreenShot();

				System.out.println("Number of questions: " + Questions);
				Thread.sleep(2000);
				WebElement EndTest = driver.findElement(By.id("endTest"));
				EndTest.click();
				driver.switchTo().alert().accept();
				test.log(LogStatus.INFO, "End Test");
				lp.takeScreenShot();
				driver.navigate().back();
			}

			else {
				for (int a = 0; a < Tablesize; a++) {
					getPracticeTb().get(0).click();
					getAssignment().get(0).click();
					test.log(LogStatus.INFO, "Open Assignment");
					lp.takeScreenShot();

					Thread.sleep(1000);
					Table().get(a).click();
					test.log(LogStatus.INFO, "Opening" + " " + a + " Assignment");
					Thread.sleep(1000);
					List<WebElement> ViewAns = driver.findElements(By.xpath("//*[contains(text(),'View Answer')]"));
					int Questions = ViewAns.size();
					test.log(LogStatus.INFO, "No. of questions : " + Questions);
					lp.takeScreenShot();

					System.out.println("Number of questions: " + Questions);
					Thread.sleep(1000);
					WebElement EndTest = driver.findElement(By.id("endTest"));
					EndTest.click();
					driver.switchTo().alert().accept();
					test.log(LogStatus.INFO, "End Test");
					lp.takeScreenShot();

					driver.navigate().back();
				}
			}
		} catch (Exception e) {
			test.log(LogStatus.FAIL,
					"Error on Assignment " + e.getMessage() + " Location is " + driver.getCurrentUrl());
			lp.takeScreenShot();
			driver.navigate().back();

		}
	}

	public List<WebElement> getNCERTSol() {

		List<WebElement> NCERTSolution = driver.findElements(By.xpath(
				"//div[@class='postlogin-card ng-scope']//*[@id='service-tab-Practice']//*[@class='ncert-solutions']/parent::a"));
		return NCERTSolution;
	}

	public boolean clickNCERTSolution() throws Exception {
		ChapterPage chPg = new ChapterPage(driver, test);
		try {
			clickElement(getNCERTSol(), 0, "NCERT Link");
			Thread.sleep(1000);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean NCERTSolution() throws Exception {
		ChapterPage chPg = new ChapterPage(driver, test);
		boolean ncertresultpage = fluentWaitIsDisplay(driver.findElement(By.xpath("//iframe[@id='fulscr']")), 30,
				"NCERT Page");
		if (ncertresultpage) {
			test.log(LogStatus.INFO, "NCERT Solutions Page Opened");
			lp.takeScreenShot();
			Thread.sleep(3000);
			driver.navigate().back();
			return true;
		} else {
			return false;
		}
	}

	public List<WebElement> getVBQ() {
		
		List<WebElement> VBQ = driver.findElements(By.xpath("//p[contains(text(),'VBQ')]"));
		return VBQ;
	}

	public void VBQ() throws InterruptedException {
	
		getVBQ().get(0).click();
		List<WebElement> AnsTab = driver
				.findElements(By.xpath("//div[@class='postlogin-card ng-scope']//a[contains(text(),'Answer')]"));
		int size = AnsTab.size();
		System.out.println("Total answers : " + size);
		Thread.sleep(1000);

		List<WebElement> SelAns = driver.findElements(By.xpath("//form[@class='form col-sm-9 ng-pristine ng-valid']"));
		List<WebElement> TypeAns = driver.findElements(By.xpath("//textarea[@class='form-control qa-textarea']"));
		int TestArea = TypeAns.size();
		System.out.println(TestArea);
		int ASize = SelAns.size();
		System.out.println(ASize);

		for (int p = 0; p < TestArea; p++) {
			int count = p + ASize;
			AnsTab.get(count).click();
			TypeAns.get(p).sendKeys("Done");
			// System.out.println(p + " done");
			Thread.sleep(1000);
		}
		driver.navigate().back();
	}

	public List<WebElement> getHOTS() {

		List<WebElement> HOTS = driver.findElements(By.xpath(
				"//div[@class='postlogin-card ng-scope']//*[@id='service-tab-Practice']//*[@class='hots']/parent::a"));
		return HOTS;
	}

	public boolean HOTS() throws Exception {
		ChapterPage chPg = new ChapterPage(driver, test);
		boolean clickHOTS = clickElement(getHOTS(), 0, "HOTS");
		if (clickHOTS) {
			try {
				WebDriverWait wt = new WebDriverWait(driver, 120);
				driver.switchTo().frame("fulscr");
				WebElement outline = driver.findElement(By.xpath("//button[@class='component_base std outline']"));
				outline.click();

				List<WebElement> outlineDiv = driver
						.findElements(By.xpath("//div[@class='outline']//div[@class='thumb']"));
				int outSize = outlineDiv.size();
				WebElement NextBtn = driver.findElement(By.xpath("//button[@class='component_base next']"));
				// WebElement PrevBtn=
				// driver.findElement(By.xpath("//button[@class='component_base prev']"));
				System.out.println("Total slides  = " + outSize);
				Thread.sleep(5000);
				for (int k = 0; k <= outSize - 2; k++) {

					wt.until(ExpectedConditions.elementToBeClickable(By
							.xpath("//div[@class='component_container next']//button[@class='component_base next']")));
					NextBtn.click();
					int j = k + 1;
					test.log(LogStatus.INFO, "Open" + j + " Video");

					Thread.sleep(1000);
					lp.takeScreenShot();
				}
				driver.navigate().back();
				return true;
			} catch (Exception e) {
				driver.navigate().back();
				return false;
			}

		} else {
			return false;
		}
	}
	// .............................................TEST
	// SERVICES......................................................................................

	public List<WebElement> getTestTb() {
	
		List<WebElement> TestTb = driver.findElements(By.xpath("//a[@id='test-panel']"));

		return TestTb;
	}

	public List<WebElement> getMCQ() {
	
		List<WebElement> MCQ = driver.findElements(By.xpath(
				"//div[@class='postlogin-card ng-scope']//*[@id='service-tab-Test']//*[contains(@class,'mcq')]"));
		return MCQ;
	}

	public boolean openMCQTest() throws Exception {
		ChapterPage chPg = new ChapterPage(driver, test);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			clickElement(getMCQ(), 0, "click on MCQ Test");
			// js.executeScript("arguments[0].click();", getMCQ().get(0));
			Reporter.log("MCQ Test is Opened ");
			test.log(LogStatus.INFO, "MCQ Test is Opened");
			return true;
		} catch (Exception e) {
			test.log(LogStatus.FAIL,
					"Error on click on  MCQ Test " + e.getMessage() + " Location is " + driver.getCurrentUrl());
			lp.takeScreenShot();
			return false;
		}
	}

	public boolean MCQ() throws Exception {
		ChapterPage chPg = new ChapterPage(driver, test);
		boolean mcq = false;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			((JavascriptExecutor) driver).executeScript("scroll(0,400)");

			List<WebElement> TestLevels = driver.findElements(
					By.xpath("//div[@id='pl-table-cont13']//div[@class='media-body media-middle text-left']"));
			boolean takeATest = fluentWaitIsDisplay(TestLevels, 0, 60, "TestLevels");
			if (takeATest) {
				int size = TestLevels.size();
				test.log(LogStatus.INFO, "Number of Levels in  MCQ =" + size);
				Reporter.log("Number of Levels in  MCQ =" + size);
				System.out.println("Number of Levels in MCQ= " + size);
				Thread.sleep(3000);
				WebElement ChoseTest = driver.findElement(
						By.xpath("//*[@class='view-notes']//*[@class='media subject-list']//a[@ng-click]"));
				// js.executeScript("arguments[0].click();", ChoseTest);
				clickElement(ChoseTest, "click on take a Test");
				WebElement PopMenu = driver.findElement(By.xpath("//*[@ng-click='openMcqTest(mcqServiceId)']"));
				clickElement(PopMenu, "PopMenu");
				Thread.sleep(3000);
				int Questions = driver.findElements(By.xpath("//ul[@class='testq-counter ng-scope']//li[@ng-repeat]"))
						.size();
				System.out.println("Number of Questions in MCQ :" + Questions);
				long start = System.currentTimeMillis();
				// some time passes
				String testDuration = "";
				long elapsedTime = 0;
				for (int s = 0; s < Questions; s++) {

					if (s < 4) {
						Thread.sleep(2000);
						driver.findElement(By.xpath(" //div[@class='col-sm-12 chapter-list-name']//form//div[1]"))
								.click();
					} else if (s >= 4) {
						Thread.sleep(2000);
						driver.findElement(By.xpath(" //div[@class='col-sm-12 chapter-list-name']//form//div[2]"))
								.click();
					}
					if (s == Questions - 1) {

						testDuration = driver.findElement(By.xpath("//*[@class='testq-heading-right']//div/span[2]"))
								.getText().trim();
						Thread.sleep(3000);
						long end = System.currentTimeMillis();
						elapsedTime = (end - start) / 60000;
					}

					clickElement(driver.findElements(
							By.xpath("//*[@class='postlogin-card']//*[@class='row']//ul/li[@class]/a")), 1, "Next");
				}
				System.out.println("Time taken for a Test from System : " + elapsedTime);
				test.log(LogStatus.INFO, "Time taken for a Test from System : " + elapsedTime);
				Thread.sleep(1000);
				String[] duration = testDuration.split(" ");
				Thread.sleep(3000);
				String remainingTime = driver
						.findElement(By.xpath("(//*[@class='pl-report-table']//*[@ng-if='remainingTimeFlag==1'])[4]"))
						.getText().trim();
				String[] rTime = remainingTime.split(" ");
				WebElement submitTest = driver.findElement(By.xpath(
						"//div[@id='pauseAdaptiveModal']//*[@class='pl-serv-challenge-btn']//*[ @class='btn btn-banner blue text-white']"));
				js.executeScript("arguments[0].click();", submitTest);
				Thread.sleep(3000);
				System.out.println("Time taken for test : " + duration[3]);
				System.out.println("Remaining Time for test : " + rTime[3]);
				test.log(LogStatus.INFO, "Time taken for test : " + duration[3]);
				test.log(LogStatus.INFO, "Remaining Time for test : " + rTime[3]);
				SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
				timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
				String[] Time = duration[3].split(":");
				System.out.println(Time[0]);
				int timeMin = Integer.parseInt(Time[0]);
				int timeSeconds = Integer.parseInt(Time[1]);
				int totalTimeTakenInSeconds = (timeMin * 60) + timeSeconds;
				double totalTimeTakenInMin = (double) timeMin + (double) timeSeconds / 60;
				totalTimeTakenInMin = Math.round(totalTimeTakenInMin * 10) / 10.0;
				// System.out.println(totalTimeTakenInMin);
				Date date1 = timeFormat.parse(duration[3]);
				Date date2 = timeFormat.parse(rTime[3]);
				long sum = date1.getTime() + date2.getTime();
				String date3 = timeFormat.format(new Date(sum));
				if (date3.equalsIgnoreCase("20:00")) {
					System.out.println("Sum Of Time Taken and Time Remaining is equal to Total Time");
					test.log(LogStatus.PASS, "Sum Of Time Taken and Time Remaining is equal to Total Time");
					Reporter.log("Sum Of Time Taken and Time Remaining is equal to Total Time");
					mcq = true;
				} else {
					System.out.println("Sum Of Time Taken and Time Remaining is not  equal to Total Time");
					test.log(LogStatus.FAIL, "Sum Of Time Taken and Time Remaining is equal to Total Time");
					Reporter.log("Sum Of Time Taken and Time Remaining is equal to Total Time");
				}
				Thread.sleep(4000);
				String correctAnswer = driver
						.findElements(By.xpath("(//*[@class='postlogin-card']//*[@class='col-sm-6'])[2]/div")).get(0)
						.getText();
				String wrongAnswer = driver
						.findElements(By.xpath("(//*[@class='postlogin-card']//*[@class='col-sm-6'])[2]/div")).get(1)
						.getText();
				String skipAnswer = driver
						.findElements(By.xpath("(//*[@class='postlogin-card']//*[@class='col-sm-6'])[2]/div")).get(2)
						.getText();
				String[] correctAns2 = correctAnswer.split("/");
				String[] wrongAns2 = wrongAnswer.split("/");
				String[] skipAns2 = skipAnswer.split("/");
				int correctAns = Integer.parseInt(correctAns2[0].trim());
				System.out.println("Correct : " + correctAns);
				int wrongAns = Integer.parseInt(wrongAns2[0].trim());
				System.out.println("Wrong : " + wrongAns);
				int skipAns = Integer.parseInt(skipAns2[0].trim());
				System.out.println("Skip : " + skipAns);
				test.log(LogStatus.INFO, "Correct : " + correctAns);
				test.log(LogStatus.INFO, "Wrong : " + wrongAns);
				test.log(LogStatus.INFO, "Skip : " + skipAns);
				int getActualScore = getActualScore();
				int expectedScore = (correctAns * 10);
				if (getActualScore == expectedScore) {
					System.out.println("Actual Score is Correct");
					test.log(LogStatus.PASS, "Actual Score is Correct");
					Reporter.log("Actual Score is Correct");
					mcq = true;
				} else {
					System.out.println("Actual Score is incorrect");
					test.log(LogStatus.FAIL, "Actual Score is incorrect");
					Reporter.log("Actual Score is incorrect");
				}

				HashMap<String, Integer> answerKeyData = answerKeys();
				if (answerKeyData.get("CorrectQues") == correctAns && answerKeyData.get("WrongQues") == wrongAns
						&& answerKeyData.get("SkipQues") == skipAns) {
					System.out.println("Number of Questions on answerKey is Correct");
					test.log(LogStatus.PASS, "Number of Questions on answerKey is Correct");
					Reporter.log("Number of Questions on answerKey is Correct");
					mcq = true;
				} else {

					System.out.println("Number of Questions on answerKey is incorrect");
					test.log(LogStatus.FAIL, "Number of Questions on answerKey is incorrect");
					Reporter.log("Number of Questions on answerKey is incorrect");
				}
				HashMap<String, Double> evaluationData = evaluation();
				if (evaluationData.get("TimeTaken") == totalTimeTakenInMin) {
					System.out.println("Evaluation - Time Taken is correct, Actual Time : "
							+ evaluationData.get("TimeTaken") + " Expected Time :" + totalTimeTakenInMin);
					test.log(LogStatus.PASS, "Evaluation - Time Taken is correct, Actual Time : "
							+ evaluationData.get("TimeTaken") + " Expected Time :" + totalTimeTakenInMin);
					Reporter.log("Evaluation - Time Taken is correct, Actual Time : " + evaluationData.get("TimeTaken")
							+ " Expected Time :" + totalTimeTakenInMin);
					mcq = true;
				} else {
					System.out.println("Evaluation - Time Taken is incorrect, Actual Time : "
							+ evaluationData.get("TimeTaken") + " Expected Time :" + totalTimeTakenInMin);
					test.log(LogStatus.FAIL, "Evaluation - Time Taken is incorrect, Actual Time : "
							+ evaluationData.get("TimeTaken") + " Expected Time :" + totalTimeTakenInMin);
					Reporter.log("Evaluation - Time Taken is incorrect, Actual Time : "
							+ evaluationData.get("TimeTaken") + " Expected Time :" + totalTimeTakenInMin);
				}

				if (evaluationData.get("Speed") == (double) totalTimeTakenInSeconds / 10) {

					System.out.println("Evaluation - Speed is correct, Actual Speed: " + evaluationData.get("Speed")
							+ " Expected Speed :" + (double) totalTimeTakenInSeconds / 10);
					test.log(LogStatus.PASS,
							"Evaluation - Speed is correct, Actual Speed: " + evaluationData.get("Speed")
									+ " Expected Speed :" + (double) totalTimeTakenInSeconds / 10);
					Reporter.log("Evaluation - Speed is correct, Actual Speed: " + evaluationData.get("Speed")
							+ " Expected Speed :" + (double) totalTimeTakenInSeconds / 10);
					mcq = true;
				} else {
					System.out.println("Evaluation - Speed is incorrect, Actual Speed: " + evaluationData.get("Speed")
							+ " Expected Speed :" + (double) totalTimeTakenInSeconds / 10);
					test.log(LogStatus.FAIL,
							"Evaluation - Speed is incorrect, Actual Speed: " + evaluationData.get("Speed")
									+ " Expected Speed :" + (double) totalTimeTakenInSeconds / 10);
					Reporter.log("Evaluation - Speed is incorrect, Actual Speed: " + evaluationData.get("Speed")
							+ " Expected Speed :" + (double) totalTimeTakenInSeconds / 10);
				}
				List<HashMap<String, String>> questionWiseAnalysis = questionWiseAnalysis();
				int qWACorrectQues = 0, qWAWrongQues = 0, qWASkipQues = 0, TimeTaken = 0;
				for (int question = 0; question < questionWiseAnalysis.size(); question++) {
					if (questionWiseAnalysis.get(question).get("Color").trim().equalsIgnoreCase("Green")) {
						qWACorrectQues = qWACorrectQues + 1;
					}
					if (questionWiseAnalysis.get(question).get("Color").trim().equalsIgnoreCase("Red")) {
						qWAWrongQues = qWAWrongQues + 1;
					}
					if (questionWiseAnalysis.get(question).get("Color").trim().equalsIgnoreCase("Grey")) {
						qWASkipQues = qWASkipQues + 1;
					}
					int time = Integer.parseInt(questionWiseAnalysis.get(question).get("Time").trim());
					TimeTaken = TimeTaken + time;
				}
				if (qWACorrectQues == correctAns && qWAWrongQues == wrongAns && qWASkipQues == skipAns) {
					System.out
							.println("QuestionWiseAnalysis - Correct Answer/Wrong Answer/Skip Answer Count is correct");
					test.log(LogStatus.PASS,
							"QuestionWiseAnalysis - Correct Answer/Wrong Answer/Skip Answer Count is correct");
					Reporter.log("QuestionWiseAnalysis - Correct Answer/Wrong Answer/Skip Answer Count is correct");
					mcq = true;
				} else {
					System.out.println(
							"QuestionWiseAnalysis - Correct Answer/Wrong Answer/Skip Answer Count is incorrect");
					test.log(LogStatus.FAIL,
							"QuestionWiseAnalysis - Correct Answer/Wrong Answer/Skip Answer Count is incorrect");
					Reporter.log("QuestionWiseAnalysis - Correct Answer/Wrong Answer/Skip Answer Count is incorrect");
				}
				if (TimeTaken == totalTimeTakenInSeconds) {
					System.out.println(
							"QuestionWiseAnalysis - sum of time taken for Questions is equal to Total Time taken");
					test.log(LogStatus.PASS,
							"QuestionWiseAnalysis - sum of time taken for Questions is equal to Total Time taken");
					Reporter.log("QuestionWiseAnalysis - sum of time taken for Questions is equal to Total Time taken");
					mcq = true;
				} else {
					System.out.println(
							"QuestionWiseAnalysis - sum of time taken for Questions is not equal to Total Time taken");
					test.log(LogStatus.FAIL,
							"QuestionWiseAnalysis - sum of time taken for Questions is not equal to Total Time taken");
					Reporter.log(
							"QuestionWiseAnalysis - sum of time taken for Questions is not equal to Total Time taken");
				}
				lp.takeFullScreenshot();

				Thread.sleep(3000);
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				clickElement(By.xpath("//*[@class='text-center mb20']//a[contains(@href,'chapter')]"),
						"click on take another test");
				return true;
			} else {
				test.log(LogStatus.FAIL, "Not able to Take A Test button" + " Location is " + driver.getCurrentUrl());
				System.out.println("Not able to Take A Test button" + " Location is " + driver.getCurrentUrl());
				return false;
			}
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Error on MCQ Page " + e.getMessage() + " Location is " + driver.getCurrentUrl());
			lp.takeFullScreenshot();
			Thread.sleep(3000);
			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			if (driver.findElements(By.xpath("//*[@class='text-center mb20']//a[contains(@href,'chapter')]"))
					.size() != 0) {
				clickElement(By.xpath("//*[@class='text-center mb20']//a[contains(@href,'chapter')]"),
						"click on take another test");
			} else {
				driver.navigate().back();
			}

			return false;
		}

	}

	public int getActualScore() throws InterruptedException {
		
		int actualScore = 0;
		try {
			WebDriverWait wt = new WebDriverWait(driver, 30);
			wt.until(ExpectedConditions.visibilityOf(
					driver.findElement(By.xpath("//*[@class='col-sm-6']//*[@class='highcharts-title']"))));
			String getActualScore = driver.findElement(By.xpath("//*[@class='col-sm-6']//*[@class='highcharts-title']"))
					.getText().trim();
			getActualScore = getActualScore.replace("%", "");
			actualScore = Integer.parseInt(getActualScore);
			System.out.println("actualScore : " + actualScore);
			test.log(LogStatus.INFO, "actualScore : " + actualScore);
			Reporter.log("actualScore : " + actualScore);
		} catch (Exception e) {
			System.out.println("Error on Getting Actual Score" + e.getMessage());
			test.log(LogStatus.INFO, "Error on Getting Actual Score" + e.getMessage());
			Reporter.log("actualScore : " + actualScore);

		}

		Thread.sleep(3000);
		return actualScore;
	}

	public HashMap<String, Double> evaluation() throws InterruptedException {
	
		HashMap<String, Double> evaluationData = new HashMap<String, Double>();
		try {
			WebDriverWait wt = new WebDriverWait(driver, 30);
			wt.until(ExpectedConditions
					.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class='col-sm-6 pdl0']//p[@class]")));
			String timeTakens = driver
					.findElements(By.xpath("//*[@class='postlogin-card']//*[@class='col-sm-6 pdl0']//p[2][@class]"))
					.get(0).getText().trim();
			String ansSpeed = driver
					.findElements(By.xpath("//*[@class='postlogin-card']//*[@class='col-sm-6 pdl0']//p[2][@class]"))
					.get(1).getText().trim();
			String[] timeTaken = timeTakens.split(" ");
			String[] ansSpeed2 = ansSpeed.split(" ");
			evaluationData.put("TimeTaken", Double.parseDouble(timeTaken[0]));
			evaluationData.put("Speed", Double.parseDouble(ansSpeed2[0]));
			System.out.println(
					"TimeTaken : " + Float.parseFloat(timeTaken[0]) + " Speed : " + Float.parseFloat(ansSpeed2[0]));
			test.log(LogStatus.INFO,
					"TimeTaken : " + Float.parseFloat(timeTaken[0]) + " Speed : " + Float.parseFloat(ansSpeed2[0]));
			Reporter.log(
					"TimeTaken : " + Float.parseFloat(timeTaken[0]) + " Speed : " + Float.parseFloat(ansSpeed2[0]));
		} catch (Exception e) {
			System.out.println("Error on Getting Actual Time Taken/Speed" + e.getMessage());
			test.log(LogStatus.INFO, "Error on Getting Actual Time Taken/Speed" + e.getMessage());
		}

		Thread.sleep(3000);
		return evaluationData;
	}

	public HashMap<String, Integer> answerKeys() throws InterruptedException {
	
		HashMap<String, Integer> answerKeyData = new HashMap<String, Integer>();
		try {
			Thread.sleep(4000);
			WebDriverWait wt = new WebDriverWait(driver, 30);
			wt.until(ExpectedConditions.visibilityOf(
					driver.findElement(By.xpath("//*[@class='postlogin-card']//*[@class='col-sm-6']//a"))));

			WebElement answerKeys = driver
					.findElement(By.xpath("//*[@class='postlogin-card']//*[@class='col-sm-6']//a"));
			answerKeys.click();
			Thread.sleep(3000);
			int correctQues = driver
					.findElements(By.xpath("//*[@class='postlogin-card']//*[@id='rightQ']/div/table/tbody")).size();
			int wrongQues = driver
					.findElements(By.xpath("//*[@class='postlogin-card']//*[@id='wrongQ']/div/table/tbody")).size();
			int skipQues = driver
					.findElements(By.xpath("//*[@class='postlogin-card']//*[@id='skippedQ']/div/table/tbody")).size();
			System.out.println("Correct Question : " + correctQues + "Wrong Question : " + correctQues
					+ " and Skip Question : " + skipQues);
			test.log(LogStatus.INFO, "Correct Question : " + correctQues + "Wrong Question : " + correctQues
					+ " and Skip Question : " + skipQues);
			Reporter.log("Correct Question : " + correctQues + "Wrong Question : " + correctQues
					+ " and Skip Question : " + skipQues);
			answerKeyData.put("CorrectQues", correctQues);
			answerKeyData.put("WrongQues", wrongQues);
			answerKeyData.put("SkipQues", skipQues);
		} catch (Exception e) {
			System.out.println("Error on Answerkeys" + e.getMessage());
			test.log(LogStatus.INFO, "Error on Answerkeys" + e.getMessage());
		}
		lp.takeScreenShot();
		driver.navigate().back();
		Thread.sleep(3000);
		return answerKeyData;

	}

	public List<HashMap<String, String>> questionWiseAnalysis() throws InterruptedException {

		List<HashMap<String, String>> questionWiseAnalysisList = new ArrayList<HashMap<String, String>>();

		try {

			WebDriverWait wt = new WebDriverWait(driver, 30);
			wt.until(ExpectedConditions.visibilityOfAllElements(qWAQuestions));
			Actions ac = new Actions(driver);
			System.out.println("Total Questions :" + qWAQuestions.size());
			if (qWAQuestions.size() != 0) {
				((JavascriptExecutor) driver).executeScript("scroll(0,1000)");
				for (int i = 0; i < qWAQuestions.size(); i++) {
					HashMap<String, String> questionWiseAnalysisData = new HashMap<String, String>();
					int k = i + 1;
					ac.clickAndHold(qWAQuestions.get(i)).build().perform();
					String timeToolTip = qWAQuestionsToolTip.get(0).getText();
					String[] tooltip = timeToolTip.split(" ");
					String color2 = qWAQuestionsToolTipColor.getAttribute("style");
					String[] colorCode = color2.split(":");

					String color = "";
					if (colorCode[1].trim().equalsIgnoreCase("rgb(52, 203, 98);")) {
						color = "Green";

					}
					if (colorCode[1].trim().equalsIgnoreCase("rgb(217, 68, 68);")) {
						color = "Red";

					}
					if (colorCode[1].trim().equalsIgnoreCase("rgb(128, 128, 128);")) {
						color = "Grey";

					}
					questionWiseAnalysisData.put("Color", color);
					questionWiseAnalysisData.put("Time", tooltip[3].replace("s", "").trim());

					System.out.println("Question : " + k + " Color is : " + color + " and Time "
							+ tooltip[3].replace("s", "").trim());
					test.log(LogStatus.INFO, "Question : " + k + " Color is : " + color + " and Time "
							+ tooltip[3].replace("s", "").trim());
					Reporter.log("Question : " + k + " Color is : " + color + " and Time "
							+ tooltip[3].replace("s", "").trim());
					questionWiseAnalysisList.add(questionWiseAnalysisData);
				}
			}
		} catch (Exception e) {
			System.out.println("Error on Getting on Question Wise AnalysisData" + e.getMessage());
			test.log(LogStatus.INFO, "Error on Getting on Question Wise AnalysisData" + e.getMessage());
		}

		Thread.sleep(3000);
		return questionWiseAnalysisList;
	}

	public List<WebElement> getAdaptiveTest() {

		List<WebElement> AdaptiveTest = driver.findElements(By.xpath(
				"//div[@class='postlogin-card ng-scope']//*[@id='service-tab-Test']//*[contains(@class,'adaptive-test')]"));
		return AdaptiveTest;
	}

	public boolean AdaptiveTest() throws Exception {
		ChapterPage chPg = new ChapterPage(driver, test);
		boolean adaptiveResult = false;
		try {
			WebDriverWait wt = new WebDriverWait(driver, 60);
			WebElement StartTest = driver.findElement(By.xpath("//a[@ng-click='openAdaptiveTest(adaptiveServiceId)']"));
			Thread.sleep(5000);
			wt.until(ExpectedConditions.elementToBeClickable(StartTest));
			StartTest.click();
			List<WebElement> Questions = driver.findElements(
					By.xpath("//div[@class='row']//ul[@class='testq-counter adaptive-test-counter']//li//span"));
			int size = Questions.size();
			test.log(LogStatus.INFO, "Number of questions in Adaptive Test: " + size);
			System.out.println("Number of questions in Adaptive Test: " + size);
			((JavascriptExecutor) driver).executeScript("scroll(0,400)");
			Thread.sleep(4000);
			String testDuration = "";
			for (int b = 0; b < size; b++) {
				Thread.sleep(4000);
				if (b < 4) {
					driver.findElement(By.xpath("//div[@class='col-sm-12 chapter-list-name']//form//div[3]")).click();
				} else if (b >= 4) {
					driver.findElement(By.xpath(" //div[@class='col-sm-12 chapter-list-name']//form//div[2]")).click();
				}
				Thread.sleep(2000);
				if (b == size - 1) {
					testDuration = driver.findElement(By.xpath("//*[@class='testq-heading-right']//div/span[2]"))
							.getText().trim();
				}

				clickElement(driver.findElements(By.xpath("//*[@class='postlogin-card']//*[@class='row']//ul/li[@class]/a")),
						0, "Next Button");

			}

			Thread.sleep(1000);

			String[] duration = testDuration.split(" ");
			Thread.sleep(3000);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			Thread.sleep(3000);
			String remainingTime = driver
					.findElement(By.xpath("(//*[@class='pl-report-table']//*[@ng-if='remainingTimeFlag==1'])[4]"))
					.getText().trim();
			String[] rTime = remainingTime.split(" ");
			List<WebElement> submitTests = driver.findElements(By.xpath(
					"//*[@id='pauseAdaptiveModal']//*[@class='row']//div[@class='pl-serv-challenge-btn']//input[@class='btn btn-banner blue text-white']"));
			boolean clicksubmitTest = false;
			for (WebElement submitTest : submitTests) {
				if (submitTest.isDisplayed()) {
					clicksubmitTest = clickElement(submitTest, "submitTest");
					break;
				}
			}
			if (clicksubmitTest) {
				Thread.sleep(3000);
				System.out.println("Time taken for test : " + duration[3]);
				System.out.println("Remaining Time for test : " + rTime[3]);
				test.log(LogStatus.INFO, "Time taken for test : " + duration[3]);
				test.log(LogStatus.INFO, "Remaining Time for test : " + rTime[3]);
				SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
				timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
				String[] Time = duration[3].split(":");
				int timeMin = Integer.parseInt(Time[0]);
				int timeSeconds = Integer.parseInt(Time[1]);
				int totalTimeTakenInSeconds = (timeMin * 60) + timeSeconds;
				double totalTimeTakenInMin = (double) timeMin + (double) timeSeconds / 60;
				totalTimeTakenInMin = Math.round(totalTimeTakenInMin * 10) / 10.0;
				// System.out.println(totalTimeTakenInMin);
				Date date1 = timeFormat.parse(duration[3]);
				Date date2 = timeFormat.parse(rTime[3]);
				long sum = date1.getTime() + date2.getTime();
				String date3 = timeFormat.format(new Date(sum));
				if (date3.equalsIgnoreCase("20:00")) {
					System.out.println("Sum Of Time Taken and Time Remaining is equal to Total Time");
					test.log(LogStatus.PASS, "Sum Of Time Taken and Time Remaining is equal to Total Time");
					Reporter.log("Sum Of Time Taken and Time Remaining is equal to Total Time");
					adaptiveResult = true;
				} else {
					System.out.println("Sum Of Time Taken and Time Remaining is not  equal to Total Time");
					test.log(LogStatus.FAIL, "Sum Of Time Taken and Time Remaining is equal to Total Time");
					Reporter.log("Sum Of Time Taken and Time Remaining is equal to Total Time");
				}
				Thread.sleep(4000);
				List<WebElement> AttemptQues = driver
						.findElements(By.xpath("(//*[@class='postlogin-card']//*[@class='col-sm-6'])[2]/div"));
				String correctAnswer = AttemptQues.get(0).getText();
				String wrongAnswer = AttemptQues.get(1).getText();
				String skipAnswer = AttemptQues.get(2).getText();
				String[] correctAns2 = correctAnswer.split("/");
				String[] wrongAns2 = wrongAnswer.split("/");
				String[] skipAns2 = skipAnswer.split("/");
				int correctAns = Integer.parseInt(correctAns2[0].trim());
				System.out.println("Correct : " + correctAns);
				int wrongAns = Integer.parseInt(wrongAns2[0].trim());
				System.out.println("Wrong : " + wrongAns);
				int skipAns = Integer.parseInt(skipAns2[0].trim());
				System.out.println("Skip : " + skipAns);
				test.log(LogStatus.INFO, "Correct : " + correctAns);
				test.log(LogStatus.INFO, "Wrong : " + wrongAns);
				test.log(LogStatus.INFO, "Skip : " + skipAns);
				int getActualScore = getActualScore();
				int expectedScore = (correctAns * 10);
				if (getActualScore == expectedScore) {
					System.out.println("Actual Score is Correct");
					test.log(LogStatus.PASS, "Actual Score is Correct");
					Reporter.log("Actual Score is Correct");
					adaptiveResult = true;
				} else {
					System.out.println("Actual Score is incorrect");
					test.log(LogStatus.FAIL, "Actual Score is incorrect");

				}

				HashMap<String, Integer> answerKeyData = answerKeys();
				if (answerKeyData.get("CorrectQues") == correctAns && answerKeyData.get("WrongQues") == wrongAns
						&& answerKeyData.get("SkipQues") == skipAns) {
					System.out.println("Number of Questions on answerKey is Correct");
					test.log(LogStatus.PASS, "Number of Questions on answerKey is Correct");
					Reporter.log("Number of Questions on answerKey is Correct");
					adaptiveResult = true;
				} else {

					System.out.println("Number of Questions on answerKey is incorrect");
					test.log(LogStatus.FAIL, "Number of Questions on answerKey is incorrect");

				}
				HashMap<String, Double> evaluationData = evaluation();
				if (evaluationData.get("TimeTaken") == totalTimeTakenInMin) {
					System.out.println("Evaluation - Time Taken is correct, Actual Time : "
							+ evaluationData.get("TimeTaken") + " Expected Time :" + totalTimeTakenInMin);
					test.log(LogStatus.PASS, "Evaluation - Time Taken is correct, Actual Time : "
							+ evaluationData.get("TimeTaken") + " Expected Time :" + totalTimeTakenInMin);
					Reporter.log("Evaluation - Time Taken is correct, Actual Time : " + evaluationData.get("TimeTaken")
							+ " Expected Time :" + totalTimeTakenInMin);
					adaptiveResult = true;
				} else {
					System.out.println("Evaluation - Time Taken is incorrect, Actual Time : "
							+ evaluationData.get("TimeTaken") + " Expected Time :" + totalTimeTakenInMin);
					test.log(LogStatus.FAIL, "Evaluation - Time Taken is incorrect, Actual Time : "
							+ evaluationData.get("TimeTaken") + " Expected Time :" + totalTimeTakenInMin);

				}

				if (evaluationData.get("Speed") == (double) totalTimeTakenInSeconds / 10) {

					System.out.println("Evaluation - Speed is correct, Actual Speed: " + evaluationData.get("Speed")
							+ " Expected Speed :" + (double) totalTimeTakenInSeconds / 10);
					test.log(LogStatus.PASS,
							"Evaluation - Speed is correct, Actual Speed: " + evaluationData.get("Speed")
									+ " Expected Speed :" + (double) totalTimeTakenInSeconds / 10);
					Reporter.log("Evaluation - Speed is correct, Actual Speed: " + evaluationData.get("Speed")
							+ " Expected Speed :" + (double) totalTimeTakenInSeconds / 10);
					adaptiveResult = true;
				} else {
					System.out.println("Evaluation - Speed is incorrect, Actual Speed: " + evaluationData.get("Speed")
							+ " Expected Speed :" + (double) totalTimeTakenInSeconds / 10);
					test.log(LogStatus.FAIL,
							"Evaluation - Speed is incorrect, Actual Speed: " + evaluationData.get("Speed")
									+ " Expected Speed :" + (double) totalTimeTakenInSeconds / 10);
				}
				List<HashMap<String, String>> questionWiseAnalysis = questionWiseAnalysis();
				int qWACorrectQues = 0, qWAWrongQues = 0, qWASkipQues = 0, TimeTaken = 0;
				for (int question = 0; question < questionWiseAnalysis.size(); question++) {
					if (questionWiseAnalysis.get(question).get("Color").trim().equalsIgnoreCase("Green")) {
						qWACorrectQues = qWACorrectQues + 1;
					}
					if (questionWiseAnalysis.get(question).get("Color").trim().equalsIgnoreCase("Red")) {
						qWAWrongQues = qWAWrongQues + 1;
					}
					if (questionWiseAnalysis.get(question).get("Color").trim().equalsIgnoreCase("Grey")) {
						qWASkipQues = qWASkipQues + 1;
					}
					int time = Integer.parseInt(questionWiseAnalysis.get(question).get("Time").trim());
					TimeTaken = TimeTaken + time;
				}
				if (qWACorrectQues == correctAns && qWAWrongQues == wrongAns && qWASkipQues == skipAns) {
					System.out
							.println("QuestionWiseAnalysis - Correct Answer/Wrong Answer/Skip Answer Count is correct");
					test.log(LogStatus.PASS,
							"QuestionWiseAnalysis - Correct Answer/Wrong Answer/Skip Answer Count is correct");
					Reporter.log("QuestionWiseAnalysis - Correct Answer/Wrong Answer/Skip Answer Count is correct");
					adaptiveResult = true;
				} else {
					System.out.println(
							"QuestionWiseAnalysis - Correct Answer/Wrong Answer/Skip Answer Count is incorrect");
					test.log(LogStatus.FAIL,
							"QuestionWiseAnalysis - Correct Answer/Wrong Answer/Skip Answer Count is incorrect");
				}
				if (TimeTaken == totalTimeTakenInSeconds) {
					System.out.println(
							"QuestionWiseAnalysis - sum of time taken for Questions is equal to Total Time taken");
					test.log(LogStatus.PASS,
							"QuestionWiseAnalysis - sum of time taken for Questions is equal to Total Time taken");
					Reporter.log("QuestionWiseAnalysis - sum of time taken for Questions is equal to Total Time taken");
					adaptiveResult = true;
				} else {
					System.out.println(
							"QuestionWiseAnalysis - sum of time taken for Questions is not equal to Total Time taken");
					test.log(LogStatus.FAIL,
							"QuestionWiseAnalysis - sum of time taken for Questions is not equal to Total Time taken");
				}
				lp.takeFullScreenshot();
				Thread.sleep(3000);
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				clickElement(By.xpath("//*[@class='text-center mb20']//a[contains(@href,'chapter')]"),
						"click on take another test");
				Thread.sleep(5000);
				adaptiveResult = true;
			}
		} catch (Exception e) {
			test.log(LogStatus.FAIL,
					"Error on Adaptive Page " + e.getMessage() + " Location is " + driver.getCurrentUrl());
			lp.takeFullScreenshot();
			Thread.sleep(3000);
			if (driver.findElements(By.xpath("//*[@class='text-center mb20']//a[contains(@href,'chapter')]"))
					.size() != 0) {
				JavascriptExecutor js = ((JavascriptExecutor) driver);
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				clickElement(By.xpath("//*[@class='text-center mb20']//a[contains(@href,'chapter')]"),
						"click on take another test");
			} else {
				driver.navigate().back();
			}

			return false;
		}
		return adaptiveResult;
	}

	public List<WebElement> getPeriodicTest() {
	
		List<WebElement> PeriodicTest = driver.findElements(By.xpath("//i[@class='periodic-test']"));
		return PeriodicTest;
	}

	public boolean PeriodicTest() throws Exception {
		ChapterPage chPg = new ChapterPage(driver, test);
		boolean periodicTest = false;
		boolean clickPeriodic = clickElement(getPeriodicTest(), 0, "getPeriodicTest");
		if (clickPeriodic) {
			periodicTest = true;
		}
		try {
			List<WebElement> Table = driver
					.findElements(By.xpath("//ul[@class='table-list ng-scope']//a[@id='serviceType.serviceId']"));
			int TotalService = Table.size();
			System.out.println("total number of service under Periodic Test ---" + TotalService);
			/*
			 * for(int q=0; q<TotalService; q++) {
			 */

			/*
			 * getTestTb().get(0).click(); getPeriodicTest().get(0).click();
			 */ int q = 0;
			Thread.sleep(5000);
			if (TotalService > 0) {
				Table.get(q).click();
				List<WebElement> Ques = driver.findElements(By.xpath("//*[contains(text(),'View Answer')]"));
				int QuesCount = Ques.size();
				System.out.println("no of questions = " + QuesCount);
			}
			List<WebElement> BackBtn = driver.findElements(By.xpath("//a[contains(@ng-click,'Back')]"));
			if (BackBtn.size() != 0) {
				BackBtn.get(0).click();
			} else {
				driver.navigate().back();
			}
			periodicTest = true;
		} catch (Exception e) {
			periodicTest = false;
			test.log(LogStatus.FAIL,
					"Error on PeriodicTest " + e.getMessage() + " Location is " + driver.getCurrentUrl());
			lp.takeScreenShot();
			driver.navigate().back();

		}
		return periodicTest;
	}

	public List<WebElement> getUniformTest() {

		List<WebElement> UniformTest = driver.findElements(By.xpath("//i[@class='uniform-sa']/parent::a"));
		return UniformTest;
	}

	public List<WebElement> unformTestTable() {
		List<WebElement> unformTestTable = driver.findElements(By.xpath("//a[@id='serviceType.serviceId']"));
		return unformTestTable;
	}

	public boolean UniformTest() throws Exception {

		boolean unformTest = false;
		clickElement(getUniformTest(), 0, "ClickUniformTest()");

		try {

			int TotalChapter = unformTestTable().size();
			System.out.println("Total Number of Service under Uniform SA Test ---" + TotalChapter);
			for (int ut = 0; ut < TotalChapter; ut++) {
				boolean clickResult = clickElement(unformTestTable(), ut, "unformTestTable()");
				if (clickResult) {
					List<WebElement> Question = driver.findElements(By.xpath("//*[contains(text(),'View Answer')]"));
					int Questioncount = Question.size();
					System.out.println("no of questions = " + Questioncount);
				}
				WebElement BackBtn = driver.findElement(By.xpath("//a[@ng-click='goBackToLPTDataboard();']"));
				BackBtn.click();
			}
			unformTest = true;
		} catch (Exception e) {
			unformTest = false;
			test.log(LogStatus.FAIL,
					"Error on UniformTest " + e.getMessage() + " Location is " + driver.getCurrentUrl());
			lp.takeScreenShot();
			driver.navigate().back();
		}
		return unformTest;
	}

	public List<WebElement> getSolvedPapers() {
		
		List<WebElement> SolvedPapers = driver.findElements(By.xpath("//i[@class='solved-board-papers']"));
		return SolvedPapers;
	}

	public boolean SolvedPapers() throws Exception {
		ChapterPage chPg = new ChapterPage(driver, test);
		boolean solvedPaper = false;
		clickElement(getSolvedPapers(), 0, "SolvedPapers");

		try {
			List<WebElement> Services = driver.findElements(By.xpath("//ul[@class='table-list ng-scope']/li"));
			int TotalService = Services.size();
			System.out.println("Total Number of Solved Paper= " + TotalService);
			Thread.sleep(2000);
			Services.get(0).click();
			List<WebElement> ViewAns = driver.findElements(By.xpath("//*[contains(text(),'View Answer')]"));
			int Questions = ViewAns.size();
			System.out.println("No of Questions = " + Questions);
			driver.findElement(By.xpath("//a[@id='endTest']")).click();
			driver.switchTo().alert().accept();

			Thread.sleep(3000);
			driver.navigate().back();
			solvedPaper = true;
		} catch (Exception e) {
			solvedPaper = false;
			test.log(LogStatus.FAIL,
					"Error on SolvedPapers" + e.getMessage() + " Location is " + driver.getCurrentUrl());
			System.out.println("Error on SolvedPapers" + e.getMessage() + " Location is " + driver.getCurrentUrl());
			lp.takeScreenShot();
			driver.navigate().back();
		}
		return solvedPaper;
	}

	public List<WebElement> getModelPaper() {
	
		List<WebElement> ModelPapers = driver.findElements(By.xpath("//p[contains(text(), 'Model Paper')]"));
		return ModelPapers;
	}

	public void ModelPapers() throws Exception {
		ChapterPage chPg = new ChapterPage(driver, test);
		try {
			getModelPaper().get(0).click();
		} catch (Exception e) {
			test.log(LogStatus.FAIL,
					"Error on click on ModelPapers" + e.getMessage() + " Location is " + driver.getCurrentUrl());
			lp.takeScreenShot();
		}
		try {
			int table = Table().size();
			System.out.println("Table of Content =" + table);

			/*
			 * for(int a1=0; a1<table; a1++) {
			 */ getPracticeTb().get(0).click();
			getModelPaper().get(0).click();
			Thread.sleep(5000);
			Table().get(0).click();

			List<WebElement> ViewAns = driver.findElements(By.xpath("//*[contains(text(),'View Answer')]"));
			int Questions = ViewAns.size();
			System.out.println("No. of Questiond =" + Questions);
			Thread.sleep(5000);
			lp.takeScreenShot();
			driver.navigate().back();
		} catch (Exception e) {
			test.log(LogStatus.FAIL,
					"Error on ModelPapers" + e.getMessage() + " Location is " + driver.getCurrentUrl());
			lp.takeScreenShot();
			driver.navigate().back();

		}
	}

	public void getLesson() {
	
		Lesson.get(0).click();

	}

	public boolean Lesson() throws InterruptedException {
	
		try {
			boolean clickResult = clickElement(this.Lesson, 0, "Lesson");
			if (clickResult) {
				Thread.sleep(3000);
				WebDriverWait wt = new WebDriverWait(driver, 80);
				int framePresent = driver.findElements(By.xpath("//iframe[@id='fulscr']")).size();
				int table = LessonTable().size();
				int table_2 = Table.size();
				System.out.println("Table 1 present = " + table);
				System.out.println("Table 2 present = " + table_2);
				if (table == 0 && framePresent == 0 && table_2 == 0 && framePresent == 0) {
					Thread.sleep(2000);
					lp.takeScreenShot();
					// driver.navigate().back();
				} else if (table == 0 || table_2 == 0) {
					if (framePresent != 0) {
						System.out.println("No table");
						driver.switchTo().frame("fulscr");
						wt.until(ExpectedConditions
								.elementToBeClickable(By.xpath("//span[contains(text(),'OUTLINE')]")));
						JavascriptExecutor js = ((JavascriptExecutor) driver);

						driver.findElement(By.xpath("//span[contains(text(),'OUTLINE')]")).click();
						int outSize = driver.findElements(By.xpath("//div[@class='outline']//div[@class='thumb']"))
								.size();
						int slides = outSize - 2;
						System.out.println("Total slides  = " + outSize);
						// WebElement Next =
						// driver.findElement(By.xpath("//button[@class='component_base next']"));
						// WebElement PrevBtn=
						// driver.findElement(By.xpath("//button[@class='component_base prev']"));

						for (int k = 0; k <= slides; k++) {
							wt.until(ExpectedConditions.elementToBeClickable(By.xpath(
									"//div[@class='component_container next']//button[@class='component_base next']")));
							WebElement Next = driver.findElement(By.xpath(
									"//div[@class='component_container next']//button[@class='component_base next']"));
							Next.click();
							int j = k + 1;
							test.log(LogStatus.INFO, j + " slide opens");
							lp.takeScreenShot();
						}
						driver.navigate().back();
					} else if (table != 0 || table_2 != 0) {
						// wt.until(
						// ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='table-list']//a[@ng-click]")));
						System.out.println("Table of content : " + table_2 + " or " + table);
						for (int m = 0; m < table || m < table_2; ++m) {
							// Lesson.get(0).click();
							// LessonTable().get(m).click();
							if (table != 0) {
								LessonTable().get(m).click();
							} else {
								Table.get(m).click();
							}
							driver.switchTo().frame("fulscr");
							// WebElement PrevBtn=
							// driver.findElement(By.xpath("//button[@class='component_base prev']"));
							List<WebElement> ResumeBtn = driver
									.findElements(By.xpath("//button[contains(text(),'YES')]"));
							int ResumePresent = ResumeBtn.size();
							if (ResumePresent != 0) {
								ResumeBtn.get(0).click();
							}
							Actions ac = new Actions(driver);
							WebElement Next = driver.findElement(By.xpath("//button[@class='component_base next']"));
							ac.moveToElement(Next).build().perform();
							wt.until(ExpectedConditions
									.elementToBeClickable(By.xpath("//span[contains(text(),'OUTLINE')]")));
							driver.findElement(By.xpath("//span[contains(text(),'OUTLINE')]")).click();
							List<WebElement> outlineDiv = driver
									.findElements(By.xpath("//div[@class='outline']//div[@class='thumb']"));
							int outSize = outlineDiv.size();
							test.log(LogStatus.INFO, "Total slides = " + outSize);
							System.out.println("Total slides  = " + outSize);
							for (int k = 0; k <= outSize - 2; k++) {
								Next.click();
								int j = k + 1;
								test.log(LogStatus.INFO, j + " slide Open");
								lp.takeScreenShot();
							}
							driver.navigate().back();
						}
					}
				}
				return true;
			} else {
				
				return false;
			}

		} catch (Exception e) {
			driver.navigate().back();
			return false;
		}
	}

	public List<WebElement> getAnimation() {

		List<WebElement> Animation = driver.findElements(By.xpath(
				"//div[@class='postlogin-card ng-scope']//*[@id='service-tab-Learn']//*[contains(@class,'animation')]"));
		return Animation;
	}

	public boolean Animation() {

		try {
			boolean clickResult = clickElement(getAnimation(), 0, "Animation");

			if (clickResult) {
				test.log(LogStatus.INFO, "Opens Animation");
				WebDriverWait wt = new WebDriverWait(driver, 20);
				wt.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//div[@id='animationtab']//li[@ng-repeat]"))));
				int listAnim = driver.findElements(By.xpath("//div[@id='animationtab']//li[@ng-repeat]")).size();
				test.log(LogStatus.INFO, "List of Animation contains " + listAnim + " Animations");
				lp.takeScreenShot();
				System.out.println("List of Animation contains " + listAnim + " Animations");
				wt.until(ExpectedConditions.elementToBeClickable(By.xpath(
						"//*[@class='postlogin-card ng-scope']//*[@class='nav nav-tabs customTab']//*[@ng-class='{active: 1 == selected}']")));
				driver.findElement(By.xpath("//*[@class='postlogin-card ng-scope']//*[@class='nav nav-tabs customTab']//*[@ng-class='{active: 1 == selected}']")).click();

				int otherAnim = driver.findElements(By.xpath("//div[@id='otheranimationtab']//li[@ng-repeat]")).size();
				test.log(LogStatus.INFO, "Other Animation tab contains " + otherAnim + " Animations");
				lp.takeScreenShot();
				System.out.println("Other Animation tab contains " + otherAnim + " Animations");
				driver.navigate().back();
				return true;
			} else {

				return false;
			}
		} catch (Exception e) {
			driver.navigate().back();
			return false;
		}

	}

	public List<WebElement> getQuickStudy() {
	
		List<WebElement> QuickStudy = driver.findElements(By.xpath("//p[contains(text(),'Quick Study')]"));
		return QuickStudy;
	}

	public void QuickStudy() throws InterruptedException {
		getQuickStudy().get(0).click();
		Thread.sleep(1000);
		test.log(LogStatus.INFO, "Opening Quick Study");
		lp.takeScreenShot();
		Thread.sleep(3000);
		driver.navigate().back();
	}

	public List<WebElement> getConceptLearningAnimation() {
	
		List<WebElement> CLAnimation = driver.findElements(By.xpath("//h4[contains(text(), 'Concept Learning')]"));
		return CLAnimation;
	}

	public List<WebElement> chapterConceptLearningAnimation() {

		List<WebElement> animChap = driver.findElements(By.xpath("//*[contains(@class,'lpt-new-concept-vfx')]"));
		return animChap;
	}

	public List<WebElement> animationConceptLearning() {

		List<WebElement> listAnim = driver.findElements(By.xpath("//*[@id='demo']//div[@ng-click]"));
		return listAnim;
	}

	public boolean conceptLearningAnimation() throws Exception {
		ChapterPage chPg = new ChapterPage(driver, test);
		try {
			test.log(LogStatus.INFO, "Animation");
			int animChapSize = chapterConceptLearningAnimation().size();
			test.log(LogStatus.INFO, "List of Animation  chapter  " + animChapSize);
			System.out.println("List of Animation  chapter  " + animChapSize);
			if (animChapSize != 0) {
				for (int i = 0; i < animChapSize && chapterConceptLearningAnimation().get(i).isDisplayed(); i++) {
					int aniChap = i + 1;
					test.log(LogStatus.INFO, "Animation Chapter = " + aniChap + "   "
							+ chapterConceptLearningAnimation().get(i).getText());
					System.out.println("Animation Chapter = " + aniChap + "   "
							+ chapterConceptLearningAnimation().get(i).getText());
					JavascriptExecutor js = (JavascriptExecutor) driver;
					// js.executeScript("arguments[0].click();", animChap.get(i));
					Thread.sleep(3000);
					clickElement(chapterConceptLearningAnimation(), i, " animChap");
					Thread.sleep(6000);
					int animSize = animationConceptLearning().size();
					test.log(LogStatus.INFO, "This chapter contains " + animSize + " Animations");
					System.out.println("This chapter contains " + animSize + " Animations");
					if (animSize != 0) {
						((JavascriptExecutor) driver).executeScript("scroll(0,400)");
						for (int j = 0; j < animSize; j++) {
							int aniNum = j + 1;
							Thread.sleep(4000);
							try {
								test.log(LogStatus.INFO,
										"Animation  " + aniNum + " = " + animationConceptLearning().get(j).getText());
								System.out.println(
										"Animation  " + aniNum + " = " + animationConceptLearning().get(j).getText());
								Thread.sleep(3000);
							} catch (StaleElementReferenceException e) {
								System.out.println("Getting exception, Animation not found" + e.getMessage());
							}
							lp.takeScreenShot();

						}
					}
					((JavascriptExecutor) driver).executeScript("scroll(0,-400)");
					Thread.sleep(4000);
					if (i > 5) {
						if (driver.findElements(By.xpath("//button[@class='owl-next']")).size() != 0) {
							js.executeScript("arguments[0].click();",
									driver.findElements(By.xpath("//button[@class='owl-next']")).get(0));
							Thread.sleep(3000);
						}
					}
				}
			} else {

				List<WebElement> listAnim = driver
						.findElements(By.xpath("//*[contains(@ng-if,'Concept Learning')]//div[@ng-repeat]"));
				int animSize = listAnim.size();
				test.log(LogStatus.INFO, "List of Animation contains " + animSize + " Animations");
				lp.takeScreenShot();
				System.out.println("List of Animation contains " + animSize + " Animations");
				if (animSize != 0) {
					for (int j = 0; j < animSize && listAnim.get(j).isDisplayed(); j++) {
						int aniNum = j + 1;
						test.log(LogStatus.INFO, "Animation  " + aniNum + " = " + driver
								.findElements(By.xpath("//*[contains(@ng-if,'Concept Learning')]//div[@ng-repeat]"))
								.get(j).getText());
						System.out.println("Animation  " + aniNum + " = " + driver
								.findElements(By.xpath("//*[contains(@ng-if,'Concept Learning')]//div[@ng-repeat]"))
								.get(j).getText());
						// ((JavascriptExecutor) driver).executeScript("scroll(0,400)");
						lp.takeScreenShot();
						if (j > 5) {
							if (driver.findElements(By.xpath("//button[@class='owl-next']")).size() != 0) {
								JavascriptExecutor js = (JavascriptExecutor) driver;
								js.executeScript("arguments[0].click();",
										driver.findElements(By.xpath("//button[@class='owl-next']")).get(0));
								Thread.sleep(3000);
							}
						}
					}
				}

			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// Add Schedule

	@FindBy(xpath = "//a[@data-target='#create-a-schedule']")
	public List<WebElement> addSchedule;

	@FindBy(xpath = "//*[@id='title']")
	public List<WebElement> addTitle;

	@FindBy(xpath = "//*[@id='create-a-schedule']//button[@ng-click='addSchedule()']")
	public List<WebElement> save;

	@FindBy(xpath = "//input[@id='schedule_date6']")
	WebElement StartDateTime;

	@FindBy(xpath = "//input[@id='schedule_date7']")
	WebElement EndDate;

	@FindBy(xpath = "(//div[@class='datetimepicker-days'])[3]//table[@class]//td[@class='day active']")
	List<WebElement> Day_1;

	@FindBy(xpath = "(//div[@class='datetimepicker-hours'])[3]//table[@class]//*[@class='hour']")
	List<WebElement> Hours_1;

	@FindBy(xpath = "(//div[@class='datetimepicker-minutes'])[3]//table[@class=' table-condensed']//span[@class='minute']")
	List<WebElement> Minutes_1;

	@FindBy(xpath = "(//div[@class='datetimepicker-days'])[4]//table[@class]//td[@class='day' or @class='day new']")
	List<WebElement> Day_2;

	@FindBy(xpath = "(//div[@class='datetimepicker-hours'])[4]//table[@class]//*[@class='hour']")
	List<WebElement> Hours_2;

	@FindBy(xpath = "(//div[@class='datetimepicker-minutes'])[4]//table[@class=' table-condensed']//span[@class='minute']")
	List<WebElement> Minutes_2;

	public void addSchedule(String Title) throws InterruptedException {
	
		/*
		 * WebDriverWait wait = new WebDriverWait(driver, 20); Thread.sleep(2000);
		 * click(driver, addSchedule, 0, "addSchedule"); Thread.sleep(2000);
		 * test.log(LogStatus.INFO, "Schedule Page"); lp.takeScreenShot();
		 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("title")));
		 * addTitle.get(0).sendKeys(Title); Thread.sleep(3000); StartDateTime.click();
		 * Thread.sleep(1000); Day_1.get(0).click(); Hours_1.get(0).click();
		 * Minutes_1.get(0).click();
		 * 
		 * EndDate.click(); Thread.sleep(1000); Day_2.get(0).click();
		 * Thread.sleep(1000); Hours_2.get(1).click(); Minutes_2.get(1).click();
		 * save.get(0).click(); Thread.sleep(2000); test.log(LogStatus.INFO,
		 * "Schedule Added"); lp.takeScreenShot();
		 */
	}

	// Add Notes

	@FindBy(xpath = "//a[@data-target='#make-a-note']")
	public WebElement AddNotes;

	@FindBy(id = "notesTitle")
	public WebElement AddTitle;

	@FindBy(id = "notesDesc")
	WebElement Description;

	@FindBy(xpath = "//*[@data-provides='fileinput']//input[@id=\"image_file_id\"]")
	WebElement Attachment;

	@FindBy(xpath = "//button[@ng-click='addNotes();']")
	WebElement Submit;

	@FindBy(xpath = "//*[@id='msgDiv_note']")
	public WebElement addSuccess;

	@FindBy(xpath = "//*[@id='make-a-note']//button[@class=\"close\"]")
	public WebElement popupClose;

	public String addNotes(String Title, String description) throws IOException, InterruptedException {
		return "";
		/*
		 * click(driver, AddNotes, "AddNotes"); Thread.sleep(1000);
		 * test.log(LogStatus.INFO, "Adding Notes"); AddTitle.sendKeys(Title);
		 * Description.sendKeys(description); Thread.sleep(1000); String imagePath =
		 * Constants.Image_PATH + "test.png"; File file = new File(imagePath);
		 * Attachment.sendKeys(file.getAbsolutePath()); Thread.sleep(5000);
		 * lp.takeScreenShot(); Submit.click(); WebDriverWait wt = new
		 * WebDriverWait(driver, 30);
		 * wt.until(ExpectedConditions.textToBePresentInElement(addSuccess,
		 * "Added Successfully")); String successMsg = addSuccess.getText().trim();
		 * test.log(LogStatus.INFO, "Notes Added"); Thread.sleep(1000);
		 * popupClose.click(); return successMsg;
		 */
	}
}
