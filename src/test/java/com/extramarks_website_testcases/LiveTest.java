package com.extramarks_website_testcases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.extramarks_website_pages.ChapterPage;
import com.extramarks_website_pages.ClassPage;
import com.extramarks_website_pages.DashBoardPage;
import com.extramarks_website_pages.LaunchPage;
import com.extramarks_website_pages.LoginPage;
import com.extramarks_website_pages.SubjectPage;
import com.extramarks_website_utils.Constants;
import com.extramarks_website_utils.DataUtil;
import com.extramarks_website_utils.ExtentManager;

import com.extramarks_website_utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;



public class LiveTest extends BaseTest {

	@BeforeClass(alwaysRun = true)
	public void initReport(ITestContext tests) throws IOException {
		rep = ExtentManager.getInstance();
		parentTest = rep.startTest(this.getClass().getSimpleName());

		parentTest.log(LogStatus.INFO, "Total Number of TestCases : " + tests.getAllTestMethods().length);
		parentTest.assignCategory("Functional Testing");

	}

	@BeforeMethod(alwaysRun = true)
	public void init(Method method) throws IOException {
		test = rep.startTest(
				method.getAnnotation(Test.class).testName() + " --> " + method.getAnnotation(Test.class).description());
		parentTest.appendChild(test);

		// test = childTest.createNode(method.getName());

	}

	@AfterMethod(alwaysRun = true)
	public void writeResults() throws Exception {
		rep.endTest(parentTest);
		rep.endTest(test);
		rep.flush();

	}

	@AfterClass(alwaysRun = true)
	public void logOut() throws Exception {

		DashBoardPage dp = new DashBoardPage(driver, test);
		dp.logout();
		Thread.sleep(1000);
		if (driver != null) {
			driver.close();
			driver = null;
		}
	}

	@AfterTest(alwaysRun = true)
	public void quit() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}

	}


	@DataProvider
	public Object[][] getData() {
		Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);
		Object[][] data = DataUtil.getData(xls, "LPTServicesTest");
		return data;
	}

	@Test(dataProvider = "getData", priority = 1)
	public void doLogin(Hashtable<String, String> data) throws Exception {
		/*
		 * String expectedResult="Login_PASS"; String actualResult="";
		 */ String browser = data.get("Browser");
		test.log(LogStatus.INFO, "Login test started");
		if (!DataUtil.isTestRunnable(xls, "LPTServicesTest") || data.get("Runmode").equals("N")) {

			test.log(LogStatus.SKIP, "Skipping the test");

			throw new SkipException("skipping the test");
		}

		openBrowser(browser);
		test.log(LogStatus.INFO, "Browser Opened");
		LaunchPage launch = new LaunchPage(driver, test);
		Object resultGetURL = launch.goToHomePage();
		if (resultGetURL instanceof LoginPage) {
			LoginPage lp=(LoginPage)resultGetURL;
			String title = driver.getTitle();
			System.out.println(title);
			test.log(LogStatus.INFO, "Extramarks Website Home Page");
			lp.takeScreenShot();

			WebElement signin = driver.findElement(By.xpath("//*[@class='signin']"));
			signin.click();
			test.log(LogStatus.INFO, "Trying to Login");
			Object resultPage = lp.doLogin(data.get("Username"), data.get("Password"));
			Thread.sleep(5000);

			/*
			 * if(resultPage instanceof LoginPage){ test.log(LogStatus.INFO,
			 * "Not able to Login"); actualResult="Login_FAIL";
			 * System.out.println("Not logged in"); } else if(resultPage instanceof
			 * DashBoardPage){ test.log(LogStatus.INFO, "Able to Login");
			 * actualResult="Login_PASS"; System.out.println("Logged in"); }
			 * 
			 * if(!expectedResult.equals(actualResult)){ //take screenshot
			 * lp.takeScreenShot(); test.log(LogStatus.FAIL,
			 * "Got actual result as "+actualResult);
			 * Assert.fail("Got actual result as "+actualResult); }
			 */
			test.log(LogStatus.PASS, "Login Test Passed");
		} else {

		}
	}

//.....................................................PRACTISE SERVISE.............................................................................

	@Test(priority = 3)
	public void openPractise() throws Exception {
		LoginPage lp = new LoginPage(driver, test);
		SubjectPage sp = new SubjectPage(driver, test);
		ChapterPage chPg = new ChapterPage(driver, test);
		ClassPage cp = new ClassPage(driver, test);
		// WebDriverWait wt= new WebDriverWait(driver, 30);

		/*
		 * WebDriverWait wt = new WebDriverWait(driver, 20);
		 * wt.until(ExpectedConditions.visibilityOfAllElements(cp.SubjectLinks));
		 */
		DashBoardPage dp = new DashBoardPage(driver, test);

		dp.openstudytab();
		test.log(LogStatus.INFO, "Open Study Tab");
		Thread.sleep(2000);
		lp.takeScreenShot();
		Thread.sleep(5000);

		int l1 = cp.getTotalSub();
		for (int i = 4; i < l1; i++) {
			System.out.println("Subjects:" + cp.getSubjectLinks().get(i).getText());
			cp.getSubjectLinks().get(i).click();
			test.log(LogStatus.INFO, "Open Chapters");

			Thread.sleep(2000);
			lp.takeScreenShot();

			Thread.sleep(3000);
			int elink = sp.getMainChapter().size();
			test.log(LogStatus.INFO, "Number of Chapters :" + elink);
			lp.takeScreenShot();
			System.out.println("No. of chapters in this subject = " + elink);
			Thread.sleep(1000);

			for (int j = 0; j < elink; j++)// 1st-elink= 10
			{
				Thread.sleep(5000);
				test.log(LogStatus.INFO, "Chapter: " + j + "= " + sp.getMainChapter().get(j).getText());
				System.out.println(sp.getMainChapter().get(j).getText());
				sp.getMainChapter().get(j).click();

				Thread.sleep(2000);
				lp.takeScreenShot();

				int sizeChap = sp.getSubChapter().size();

				System.out.println("No.Sub Chapters = " + sizeChap);
				Thread.sleep(2000);

				if (sizeChap != 0)
				// ..............................................
				{
					for (int su = 0; su < sizeChap; ++su) {

						test.log(LogStatus.INFO, "No. of Sub Chapter : " + sizeChap);

						System.out.println("Chapter : " + su + "=" + sp.getSubChapter().get(su).getText());
						Thread.sleep(1000);
						sp.getSubChapter().get(su).click();
						test.log(LogStatus.INFO, "Open SubChapter");

						Thread.sleep(2000);
						lp.takeScreenShot();

						// WebDriverWait wt= new WebDriverWait(driver, 30);
						int postSubChap = sp.getPostSubChap().size();
						test.log(LogStatus.INFO, "No. of Sub-Sub Chapter : " + postSubChap);
						System.out.println("No. of Sub-sub Chapters = " + postSubChap);
						if (postSubChap != 0) {
							for (int ps = 0; ps < postSubChap; ps++) {

								System.out.println("Subchapter : " + sp.getPostSubChap().get(ps).getText());
								Thread.sleep(1000);
								sp.getPostSubChap().get(ps).click();
								test.log(LogStatus.INFO, "Open sub chapters");

								Thread.sleep(2000);
								lp.takeScreenShot();

								int PractisePresent = chPg.getPracticeTb().size();
								if (PractisePresent != 0) {
									WebDriverWait wt = new WebDriverWait(driver, 30);
									wt.until(ExpectedConditions
											.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
									chPg.getPracticeTb().get(0).click();
									test.log(LogStatus.INFO, "Open Practise");
									lp.takeScreenShot();

									wt.until(ExpectedConditions
											.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
									chPg.getPracticeTb().get(0).click();
									int MotionGalleryPresent = chPg.getMotionGallery().size();
									if (MotionGalleryPresent != 0) {
										chPg.MotionGallery();
									}

									wt.until(ExpectedConditions
											.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
									chPg.getPracticeTb().get(0).click();
									int CaseStudyPresent = chPg.getCaseStudy().size();

									if (CaseStudyPresent != 0) {
										chPg.CaseStudy();
									}

									wt.until(ExpectedConditions
											.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
									chPg.getPracticeTb().get(0).click();
									int QAPresent = chPg.getQA().size();
									if (QAPresent != 0) {
										chPg.getQA();
									}

									wt.until(ExpectedConditions
											.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
									chPg.getPracticeTb().get(0).click();
									int NCERTPresent = chPg.getNCERTSol().size();
									if (NCERTPresent != 0) {
										chPg.getNCERTSol();
									}

									wt.until(ExpectedConditions
											.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
									chPg.getPracticeTb().get(0).click();
									int HOTSPresent = chPg.getNCERTSol().size();
									if (HOTSPresent != 0) {
										chPg.getHOTS();
									}

									wt.until(ExpectedConditions
											.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
									chPg.getPracticeTb().get(0).click();
									int TopicWiseQAPresent = chPg.getTopicWiseQA().size();
									if (TopicWiseQAPresent != 0) {
										chPg.getTopicWiseQA();
									}

									wt.until(ExpectedConditions
											.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
									chPg.getPracticeTb().get(0).click();
									int AssignmentPresent = chPg.getAssignment().size();
									System.out.println(AssignmentPresent);
									if (AssignmentPresent != 0) {
										System.out.println("Assignment is present");
										chPg.Assignment();
									}

									wt.until(ExpectedConditions
											.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
									chPg.getPracticeTb().get(0).click();
									int ConceptCraftPresent = chPg.getConceptCraft().size();
									if (ConceptCraftPresent != 0) {
										chPg.ConceptCraft();
									}

								}

								// ..............................
								driver.navigate().back();
								Thread.sleep(3000);
								sp.getMainChapter().get(j).click();

								Thread.sleep(3000);
								sp.getSubChapter().get(su).click();
							}
							sp.getSubChapter().get(su).click();
						} else {

							// .........................................
							int PractisePresent = chPg.getPracticeTb().size();
							if (PractisePresent != 0) {
								WebDriverWait wt = new WebDriverWait(driver, 30);
								wt.until(
										ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
								chPg.getPracticeTb().get(0).click();
								int MotionGalleryPresent = chPg.getMotionGallery().size();
								if (MotionGalleryPresent != 0) {
									System.out.println("Motion Gallery");
									chPg.MotionGallery();
								}

								wt.until(
										ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
								chPg.getPracticeTb().get(0).click();
								int NCERTPresent = chPg.getNCERTSol().size();
								if (NCERTPresent != 0) {
									chPg.getNCERTSol();
								}

								wt.until(
										ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
								chPg.getPracticeTb().get(0).click();
								int QAPresent = chPg.getQA().size();
								if (QAPresent != 0) {
									System.out.println("QA");
									chPg.QA();
								}

								wt.until(
										ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
								chPg.getPracticeTb().get(0).click();
								int HOTSPresent = chPg.getNCERTSol().size();
								if (HOTSPresent != 0) {
									chPg.getHOTS();
								}

								wt.until(
										ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
								chPg.getPracticeTb().get(0).click();
								int TopicWiseQAPresent = chPg.getTopicWiseQA().size();
								if (TopicWiseQAPresent != 0) {
									System.out.println("TopicWiseQA");
									chPg.TopicwiseQA();
								}

								wt.until(
										ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
								chPg.getPracticeTb().get(0).click();
								int CaseStudyPresent = chPg.getCaseStudy().size();
								if (CaseStudyPresent != 0) {
									System.out.println("Case Study");
									chPg.CaseStudy();
								}

								wt.until(
										ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
								chPg.getPracticeTb().get(0).click();
								int AssignmentPresent = chPg.getAssignment().size();
								System.out.println(AssignmentPresent);
								if (AssignmentPresent != 0) {
									System.out.println("Assignment is present");
									chPg.Assignment();
								}

								wt.until(
										ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
								chPg.getPracticeTb().get(0).click();
								int ConceptCraftPresent = chPg.getConceptCraft().size();
								if (ConceptCraftPresent != 0) {
									System.out.println("Concept Craft");
									chPg.ConceptCraft();
								}

							}
							// ..............................
							driver.navigate().back();
							Thread.sleep(3000);
							sp.getMainChapter().get(j).click();
						}
					}
					sp.getMainChapter().get(j).click();
				}
				// .................................................ELSE...............................................................................//
				else {
					int PractisePresent = chPg.getPracticeTb().size();
					if (PractisePresent != 0) {
						WebDriverWait wt = new WebDriverWait(driver, 30);
						wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
						chPg.getPracticeTb().get(0).click();
						test.log(LogStatus.INFO, "Open Practise");
						lp.takeScreenShot();

						wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
						chPg.getPracticeTb().get(0).click();
						int MotionGalleryPresent = chPg.getMotionGallery().size();
						if (MotionGalleryPresent != 0) {
							chPg.MotionGallery();
						}

						wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
						chPg.getPracticeTb().get(0).click();
						int NCERTPresent = chPg.getNCERTSol().size();
						if (NCERTPresent != 0) {
							chPg.getNCERTSol();
						}

						wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
						chPg.getPracticeTb().get(0).click();
						int CaseStudyPresent = chPg.getCaseStudy().size();

						if (CaseStudyPresent != 0) {
							chPg.CaseStudy();
						}

						wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
						chPg.getPracticeTb().get(0).click();
						int HOTSPresent = chPg.getNCERTSol().size();
						if (HOTSPresent != 0) {
							chPg.getHOTS();
						}
						wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
						chPg.getPracticeTb().get(0).click();
						int TopicWiseQAPresent = chPg.getTopicWiseQA().size();
						if (TopicWiseQAPresent != 0) {
							System.out.println("TopicWiseQA");
							chPg.TopicwiseQA();
						}

						wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
						chPg.getPracticeTb().get(0).click();
						int QAPresent = chPg.getQA().size();
						if (QAPresent != 0) {
							System.out.println("QA");
							chPg.QA();
						}

						wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
						chPg.getPracticeTb().get(0).click();
						int AssignmentPresent = chPg.getAssignment().size();
						if (AssignmentPresent != 0) {
							chPg.getAssignment().get(0).click();
							chPg.Assignment();
						}

						wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
						chPg.getPracticeTb().get(0).click();
						int ConceptCraftPresent = chPg.getConceptCraft().size();
						if (ConceptCraftPresent != 0) {
							chPg.ConceptCraft();
						}

						driver.navigate().back();
					}

					// ............................................................

				}
			}
			driver.navigate().back();
		}

	}

//.......................................................LEARN SERVICE..............................................................................

	@Test(priority = 2)
	public void Learn() throws Exception {
		LoginPage lp = new LoginPage(driver, test);
		SubjectPage sp = new SubjectPage(driver, test);
		ChapterPage chPg = new ChapterPage(driver, test);
		ClassPage cp = new ClassPage(driver, test);

		DashBoardPage dp = new DashBoardPage(driver, test);
		dp.openstudytab();

		Thread.sleep(5000);

		int l1 = cp.getTotalSub();
		for (int i = 0; i < l1; i++) {
			System.out.println("Subjects:" + cp.getSubjectLinks().get(i).getText());
			cp.getSubjectLinks().get(i).click();
			Thread.sleep(2000);
			lp.takeScreenShot();

			Thread.sleep(3000);
			int elink = sp.getMainChapter().size();
			System.out.println("No. of chapters in this subject = " + elink);
			Thread.sleep(1000);

			for (int j = 0; j < elink; j++) {
				Thread.sleep(5000);
				System.out.println("Main Chapter : " + sp.getMainChapter().get(j).getText());
				sp.getMainChapter().get(j).click();
				Thread.sleep(2000);
				lp.takeScreenShot();

				int sizeChap = sp.getSubChapter().size();

				System.out.println("No.Sub Chapters = " + sizeChap);

				if (sizeChap != 0)
				// ..............................................
				{
					for (int su = 0; su < sizeChap; ++su) {
						System.out.println("Chapter : " + sp.getSubChapter().get(su).getText());
						test.log(LogStatus.INFO, "Chapter : " + sp.getSubChapter().get(su).getText());
						Thread.sleep(1000);
						sp.getSubChapter().get(su).click();

						Thread.sleep(2000);
						lp.takeScreenShot();

						int postSubChap = sp.getPostSubChap().size();
						test.log(LogStatus.INFO, "No. of Sub-Sub Chapter : " + postSubChap);
						System.out.println("No. of Sub-sub Chapters = " + postSubChap);

						if (postSubChap != 0) {
							for (int ps = 0; ps < postSubChap; ps++) {
								System.out.println("Subchapter : " + sp.getPostSubChap().get(ps).getText());
								Thread.sleep(1000);
								sp.getPostSubChap().get(ps).click();
								test.log(LogStatus.INFO, "Open sub chapters");

								Thread.sleep(2000);
								lp.takeScreenShot();

								int LearnPresent = chPg.getLearnTB().size();
								if (LearnPresent != 0) {
									WebDriverWait wt = new WebDriverWait(driver, 30);
									wt.until(ExpectedConditions.visibilityOfAllElements(chPg.getLearnTB()));
									// chPg.getLearnTB().get(0).click();
									test.log(LogStatus.INFO, "Open Learn");
									lp.takeScreenShot();

									wt.until(ExpectedConditions.visibilityOfAllElements(chPg.getLearnTB()));
									int LessonPresent = chPg.Lesson.size();
									if (LessonPresent != 0) {
										chPg.Lesson();
									}

									wt.until(ExpectedConditions.visibilityOfAllElements(chPg.getLearnTB()));
									int AnimationPresent = chPg.getAnimation().size();
									if (AnimationPresent != 0) {
										chPg.getAnimation();
									}

									wt.until(ExpectedConditions.visibilityOfAllElements(chPg.getLearnTB()));
									int QuickStudyPresent = chPg.getQuickStudy().size();
									if (QuickStudyPresent != 0) {
										chPg.QuickStudy();
									}
								}
								driver.navigate().back();
								Thread.sleep(3000);
								sp.getMainChapter().get(j).click();

								Thread.sleep(3000);
								sp.getSubChapter().get(su).click();
							}
							sp.getSubChapter().get(su).click();
						} else {
							int LearnPresent = chPg.getLearnTB().size();
							System.out.println(LearnPresent);
							if (LearnPresent != 0) {
								WebDriverWait wt = new WebDriverWait(driver, 30);
								// wt.until(ExpectedConditions.visibilityOfAllElements(chPg.getLearnTB()));
								// chPg.getLearnTB().get(0).click();
								// test.log(LogStatus.INFO, "Open Learn");
								chPg.Lesson();
								lp.takeScreenShot();

								// wt.until(ExpectedConditions.visibilityOfAllElements(chPg.getLearnTB()));
								/*
								 * int LessonPresent=chPg.getLesson().size(); System.out.println(LessonPresent);
								 * if(LessonPresent!=0) { chPg.Lesson(); }
								 */

								wt.until(ExpectedConditions.visibilityOfAllElements(chPg.getLearnTB()));
								int AnimationPresent = chPg.getAnimation().size();
								if (AnimationPresent != 0) {
									chPg.getAnimation();
								}

								wt.until(ExpectedConditions.visibilityOfAllElements(chPg.getLearnTB()));
								int QuickStudyPresent = chPg.getQuickStudy().size();
								if (QuickStudyPresent != 0) {
									chPg.QuickStudy();
								}

							}
							driver.navigate().back();
							Thread.sleep(3000);
							sp.getMainChapter().get(j).click();
						}
					}
					sp.getMainChapter().get(j).click();
				}
//...............................................................................................................................................

				else {
					int LearnPresent = chPg.getLearnTB().size();
					System.out.println("Learn Present = " + LearnPresent);
					if (LearnPresent != 0) {
						// chPg.getLearnTB().get(0).click();
						// test.log(LogStatus.INFO, "Open Learn");
						lp.takeScreenShot();

						WebDriverWait wt = new WebDriverWait(driver, 50);
						/*
						 * wt.until(ExpectedConditions.visibilityOfAllElements(chPg.getLearnTB())); int
						 * LessonPresent=chPg.getLesson().size(); if(LessonPresent!=0) { chPg.Lesson();
						 * }
						 */
						wt.until(ExpectedConditions.visibilityOfAllElements(chPg.getLearnTB()));
						int AnimationPresent = chPg.getAnimation().size();
						if (AnimationPresent != 0) {
							chPg.Animation();
						}

						wt.until(ExpectedConditions.visibilityOfAllElements(chPg.getLearnTB()));
						int QuickStudyPresent = chPg.getQuickStudy().size();
						if (QuickStudyPresent != 0) {
							chPg.QuickStudy();
						}

						driver.navigate().back();
					}
				}
			}
			driver.navigate().back();
		}
	}
//.....................................................TEST SERVISE..............................................................................//	

	@Test(priority = 4)
	public void Test() throws Exception {
		LoginPage lp = new LoginPage(driver, test);
		SubjectPage sp = new SubjectPage(driver, test);
		ChapterPage chPg = new ChapterPage(driver, test);
		ClassPage cp = new ClassPage(driver, test);

		DashBoardPage dp = new DashBoardPage(driver, test);
		WebDriverWait wt = new WebDriverWait(driver, 30);

		dp.openstudytab();

		Thread.sleep(5000);

		int l1 = cp.getTotalSub();
		for (int i = 0; i < l1; i++) {
			System.out.println("Subjects:" + cp.getSubjectLinks().get(i).getText());
			cp.getSubjectLinks().get(i).click();
			Thread.sleep(2000);
			lp.takeScreenShot();

			Thread.sleep(3000);
			int elink = sp.getMainChapter().size();
			System.out.println("No. of chapters in this subject = " + elink);
			Thread.sleep(1000);

			for (int j = 0; j < elink; j++) {
				Thread.sleep(5000);
				System.out.println(sp.getMainChapter().get(j).getText());
				sp.getMainChapter().get(j).click();
				Thread.sleep(2000);
				lp.takeScreenShot();

				int sizeChap = sp.getSubChapter().size();

				System.out.println("No.Sub Chapters = " + sizeChap);

				if (sizeChap != 0)
				// ..............................................
				{
					for (int su = 0; su < sizeChap; ++su) {
						System.out.println("Chapter : " + sp.getSubChapter().get(su).getText());
						test.log(LogStatus.INFO, "Chapter : " + sp.getSubChapter().get(su).getText());
						Thread.sleep(1000);
						sp.getSubChapter().get(su).click();

						Thread.sleep(2000);
						lp.takeScreenShot();

						int postSubChap = sp.getPostSubChap().size();
						test.log(LogStatus.INFO, "No. of Sub-Sub Chapter : " + postSubChap);
						System.out.println("No. of Sub-sub Chapters = " + postSubChap);

						if (postSubChap != 0) {
							for (int ps = 0; ps < postSubChap; ps++) {
								System.out.println("Subchapter : " + sp.getPostSubChap().get(ps).getText());
								Thread.sleep(1000);
								sp.getPostSubChap().get(ps).click();
								test.log(LogStatus.INFO, "Open sub chapters");

								Thread.sleep(2000);
								lp.takeScreenShot();

								int TestPresent = chPg.getTestTb().size();
								if (TestPresent != 0) {
									wt.until(
											ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='test-panel']")));
									chPg.getTestTb().get(0).click();
									test.log(LogStatus.INFO, "Open Test");
									lp.takeScreenShot();

									wt.until(
											ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='test-panel']")));
									chPg.getTestTb().get(0).click();
									int MCQPresent = chPg.getMCQ().size();
									if (MCQPresent != 0) {
										chPg.MCQ();
									}

									wt.until(
											ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='test-panel']")));
									chPg.getTestTb().get(0).click();
									int AdaptiveTestPresent = chPg.getAdaptiveTest().size();
									if (AdaptiveTestPresent != 0) {
										chPg.AdaptiveTest();
									}

									wt.until(
											ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='test-panel']")));
									chPg.getTestTb().get(0).click();
									int PeriodicTestPresent = chPg.getPeriodicTest().size();
									if (PeriodicTestPresent != 0) {
										chPg.PeriodicTest();
									}

									wt.until(
											ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='test-panel']")));
									chPg.getTestTb().get(0).click();
									int UniformTestPresent = chPg.getUniformTest().size();
									if (UniformTestPresent != 0) {
										chPg.UniformTest();
									}

									wt.until(
											ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='test-panel']")));
									chPg.getTestTb().get(0).click();
									int SolvedPapersPresent = chPg.getSolvedPapers().size();
									if (SolvedPapersPresent != 0) {
										chPg.SolvedPapers();
									}

								}
								driver.navigate().back();
								Thread.sleep(3000);
								sp.getMainChapter().get(j).click();

								Thread.sleep(3000);
								sp.getSubChapter().get(su).click();
							}
							sp.getSubChapter().get(su).click();
						}

						else {
							int TestPresent = chPg.getTestTb().size();
							if (TestPresent != 0) {
								wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='test-panel']")));
								chPg.getTestTb().get(0).click();
								test.log(LogStatus.INFO, "Open Test");
								lp.takeScreenShot();

								wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='test-panel']")));
								chPg.getTestTb().get(0).click();
								int MCQPresent = chPg.getMCQ().size();
								if (MCQPresent != 0) {
									chPg.MCQ();
								}

								wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='test-panel']")));
								chPg.getTestTb().get(0).click();
								int AdaptiveTestPresent = chPg.getAdaptiveTest().size();
								if (AdaptiveTestPresent != 0) {
									chPg.AdaptiveTest();
								}

								wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='test-panel']")));
								chPg.getTestTb().get(0).click();
								int PeriodicTestPresent = chPg.getPeriodicTest().size();
								if (PeriodicTestPresent != 0) {
									chPg.PeriodicTest();
								}

								wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='test-panel']")));
								chPg.getTestTb().get(0).click();
								int UniformTestPresent = chPg.getUniformTest().size();
								if (UniformTestPresent != 0) {
									chPg.UniformTest();
								}

								wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='test-panel']")));
								chPg.getTestTb().get(0).click();
								int SolvedPapersPresent = chPg.getSolvedPapers().size();
								if (SolvedPapersPresent != 0) {
									chPg.SolvedPapers();
								}

							}
							driver.navigate().back();
							Thread.sleep(3000);
							sp.getMainChapter().get(j).click();
						}
					}
					sp.getMainChapter().get(j).click();
				} else {
					int TestPresent = chPg.getTestTb().size();
					if (TestPresent != 0) {
						wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='test-panel']")));
						chPg.getTestTb().get(0).click();
						test.log(LogStatus.INFO, "Open Test");
						lp.takeScreenShot();

						wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='test-panel']")));
						chPg.getTestTb().get(0).click();
						int MCQPresent = chPg.getMCQ().size();
						if (MCQPresent != 0) {
							chPg.MCQ();
						}

						wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='test-panel']")));
						chPg.getTestTb().get(0).click();
						int AdaptiveTestPresent = chPg.getAdaptiveTest().size();
						if (AdaptiveTestPresent != 0) {
							chPg.AdaptiveTest();
						}

						wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='test-panel']")));
						chPg.getTestTb().get(0).click();
						int PeriodicTestPresent = chPg.getPeriodicTest().size();
						if (PeriodicTestPresent != 0) {
							chPg.PeriodicTest();
						}

						wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='test-panel']")));
						chPg.getTestTb().get(0).click();
						int UniformTestPresent = chPg.getUniformTest().size();
						if (UniformTestPresent != 0) {
							chPg.UniformTest();
						}

						wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='test-panel']")));
						chPg.getTestTb().get(0).click();
						int SolvedPapersPresent = chPg.getSolvedPapers().size();
						if (SolvedPapersPresent != 0) {
							chPg.SolvedPapers();
						}
					}
				}
			}
			driver.navigate().back();
		}
		test.log(LogStatus.PASS, "Test Passed");
	}
}