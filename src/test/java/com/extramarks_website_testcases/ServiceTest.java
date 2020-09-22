package com.extramarks_website_testcases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.extramarks_website_pages.ChapterPage;
import com.extramarks_website_pages.DashBoardPage;
import com.extramarks_website_utils.Constants;
import com.extramarks_website_utils.DataUtil;
import com.extramarks_website_utils.ExtentManager;
import com.extramarks_website_utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;


public class ServiceTest extends BaseTest {

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
		Object[][] data = DataUtil.getData(xls, "chPgTServicesTest");
		return data;
	}

	@Test(dataProvider = "getData", priority = 1)
	public boolean verifyLearnService(Hashtable<String, String> data, SoftAssert sAssert) throws Exception {
		boolean learnServiceResult = false;
		ChapterPage chPg = new ChapterPage(driver, test);

		try {
			 WebDriverWait wt = new WebDriverWait(driver, 60);
			int LearnPresent = chPg.getLearnTB().size();
			if (LearnPresent != 0) {
				Thread.sleep(3000);
				boolean displayLearnTabResult = fluentWaitIsDisplay(chPg.getLearnTB(), 0, 60,
						"Verify Learn Tab is Display");
				if (displayLearnTabResult) {
					boolean clickElementLeanTabResult = clickElement(chPg.getLearnTB(), 0, "clickElement on LearnTB");
					if (clickElementLeanTabResult) { // Concept Learning
						wt.until(ExpectedConditions.elementToBeClickable(chPg.getLearnTB().get(0)));
						int ConLAnimationPresent = chPg.getConceptLearningAnimation().size();
						if (ConLAnimationPresent != 0) {
							boolean displayResult = fluentWaitIsDisplay(chPg.getConceptLearningAnimation(), 0, 10,
									"getConceptLearningAnimation Screen");
							boolean animationResult = chPg.conceptLearningAnimation();
							if (animationResult) {
								test.log(LogStatus.PASS, "ConceptLearning Page Pass");
								chPg.takeScreenShot();
								Reporter.log("ConceptLearning Page Pass");
								System.out.println("ConceptLearning Page Pass");
								learnServiceResult = true;
							} else {
								test.log(LogStatus.FAIL,
										"ConceptLearning Page Fail, Location is " + driver.getCurrentUrl());
								chPg.takeScreenShot();
								sAssert.fail("ConceptLearning Page Fail, Location is " + driver.getCurrentUrl());
								System.out.println("ConceptLearning Page Fail, Location is " + driver.getCurrentUrl());
								learnServiceResult = false;
							}
							Thread.sleep(3000);
						}
						// Detail Learning
						wt.until(ExpectedConditions.elementToBeClickable(chPg.getLearnTB().get(0)));
						int DetailedLearningPresent = chPg.getDetailedLearning().size();
						test.log(LogStatus.INFO, "No.of DetailedLearningPresent = " + DetailedLearningPresent);
						chPg.takeScreenShot();
						if (DetailedLearningPresent != 0) {
							boolean displayResult2 = fluentWaitIsDisplay(chPg.getDetailedLearning(), 0, 10,
									"DetailedLearning Screen");
							boolean clickElementresult2 = chPg.clickDetailedLearning();
							if (clickElementresult2) {
								fluentWaitIsDisplay(driver.findElements(By.xpath("//iframe[@id='fulscr']")), 0, 10,
										"Detail Learning Screen");
								Thread.sleep(2000);
								boolean detailLeaningResult = chPg.DetailedLearning();
								if (detailLeaningResult) {
									test.log(LogStatus.PASS, "DetailedLearning Test Pass");
									System.out.println("DetailedLearning Test Pass");
									learnServiceResult = true;

								} else {
									test.log(LogStatus.FAIL,
											"DetailedLearning Test Fail, Location is " + driver.getCurrentUrl());
									System.out.println(
											"DetailedLearning Test Fail, Location is " + driver.getCurrentUrl());
									sAssert.fail("DetailedLearning Test Fail, Location is " + driver.getCurrentUrl());
									chPg.takeScreenShot();
									learnServiceResult = false;
									Thread.sleep(3000);
								}
							} else {
								System.out
										.println("Unable to navigate on Detail Leaning page " + driver.getCurrentUrl());
								test.log(LogStatus.FAIL,
										"unable to navigate on Detail Leaning page " + driver.getCurrentUrl());
								sAssert.fail("Unable to navigate on Detail Leaning page " + driver.getCurrentUrl());
								learnServiceResult = false;
							}

						} // Quick Learning
						wt.until(ExpectedConditions.elementToBeClickable(chPg.getLearnTB().get(0)));
						int QuickLearningPresent = chPg.getQuickLearning().size();
						if (QuickLearningPresent != 0) {
							boolean displayResult3 = fluentWaitIsDisplay(chPg.getQuickLearning(), 0, 10,
									"Quick Learning Page");
							boolean clickElementQuicklearn = chPg.clickQuickLearning();
							{
								if (clickElementQuicklearn) {
									boolean displayResult4 = fluentWaitIsDisplay(chPg.AddNotes, 10,
											"Quick Learning Page");
									Thread.sleep(2000);
									if (displayResult4) {
										Thread.sleep(3000);

										try {
											String result = chPg.addNotes(data.get("Title"), data.get("Description"));
											assertContains(chPg.addSuccess.getText().trim(), "Added Successfully",
													"Verifying Add Notes Functionality", sAssert);
											boolean displayResult5 = fluentWaitIsDisplay(chPg.addSchedule, 0, 60,
													"Quick Learning Screen");
											if (displayResult3) {
												chPg.addSchedule(data.get("Title"));
											}
										} catch (Exception e) {
											test.log(LogStatus.FAIL,
													"Getting Error on adding Notes/Schedule " + e.getMessage());
											System.out.println(
													"Getting Error on adding Notes/Schedule " + e.getMessage());
											sAssert.fail("Getting Error on adding Notes/Schedule " + e.getMessage());
											chPg.takeScreenShot();
										}

									}
								}
								boolean qLearning = chPg.QuickLearning();
								if (qLearning) {
									test.log(LogStatus.PASS, "QuickLearning Test Pass ");
									System.out.println("QuickLearning Test Pass ");
									learnServiceResult = true;
								} else {
									test.log(LogStatus.FAIL, "QuickLearning Test Fail ,Location is " + driver.getCurrentUrl());
									System.out.println(
											"QuickLearning Test Fail " + "Location is " + driver.getCurrentUrl());
									sAssert.fail("QuickLearning Test Fail " + "Location is " + driver.getCurrentUrl());
									chPg.takeScreenShot();
									learnServiceResult = false;
								}
								Thread.sleep(3000);
							}
						}

						// Miscellaneous
						wt.until(ExpectedConditions.elementToBeClickable(chPg.getLearnTB().get(0)));
						int MiscellaneousPresent = chPg.getMiscellaneous().size();
						if (MiscellaneousPresent != 0) {
							boolean displayResult6 = fluentWaitIsDisplay(chPg.getMiscellaneous(), 0, 10,
									"getMiscellaneous display");
							Thread.sleep(3000);
							chPg.Miscellaneous();
							Thread.sleep(5000);
							boolean clickElementMiscResult = chPg.Miscellaneous();
							if (clickElementMiscResult) {
								test.log(LogStatus.PASS, "Miscellaneous Test Pass ");
								System.out.println("Miscellaneous Test Pass ");
								learnServiceResult = true;
								Thread.sleep(3000);
							} else {
								test.log(LogStatus.FAIL, "Miscellaneous Test Fail ");
								System.out.println("Miscellaneous Test Fail");
								sAssert.fail("Miscellaneous Test Fail " + " Location is " + driver.getCurrentUrl());
								learnServiceResult = false;
							}
						}

						// ICSE Services UI change

						wt.until(ExpectedConditions.elementToBeClickable(chPg.getLearnTB().get(0)));
						int LessonPresent = chPg.Lesson.size();
						if (LessonPresent != 0) {
							boolean result = chPg.Lesson();
							if (result) {
								test.log(LogStatus.PASS, "Lesson Test Pass ");
								System.out.println("Lesson Test Pass ");
								learnServiceResult = true;
								Thread.sleep(3000);
							} else {
								test.log(LogStatus.FAIL, "Lesson Test Fail ");
								System.out.println("Lesson Test Fail");
								sAssert.fail("Lesson Test Fail " + " Location is " + driver.getCurrentUrl());
								learnServiceResult = false;
							}
						}

						wt.until(ExpectedConditions.elementToBeClickable(chPg.getLearnTB().get(0)));
						int AnimationPresent = chPg.getAnimation().size();
						if (AnimationPresent != 0) {
							wt.until(ExpectedConditions.elementToBeClickable(chPg.getLearnTB().get(0)));
							boolean animationResult = chPg.Animation();
							if (animationResult) {
								test.log(LogStatus.PASS, "Animation Test Pass ");
								System.out.println("Animation Test Pass ");
								learnServiceResult = true;
								Thread.sleep(3000);
							} else {
								test.log(LogStatus.FAIL, "Animation Test Fail ");
								System.out.println("Animation Test Fail ");
								sAssert.fail("Animation Test Fail " + " Location is " + driver.getCurrentUrl());
								learnServiceResult = false;
							}
						}

						wt.until(ExpectedConditions.elementToBeClickable(chPg.getLearnTB().get(0)));
						int QuickStudyPresent = chPg.getQuickStudy().size();
						if (QuickStudyPresent != 0) {
							chPg.QuickStudy();
							learnServiceResult = true;
						}
					}
				}
			}
			return learnServiceResult;
		} catch (Exception e) {
			learnServiceResult = false;
			test.log(LogStatus.FAIL,
					"Getting Exception on Service Page :" + message(e) + " and URL is " + driver.getCurrentUrl());
			sAssert.fail("Getting Exception on Service Page :" + message(e) + " and URL is " + driver.getCurrentUrl());
			System.out.println(
					"Getting Exception on Service Page :" + message(e) + " and URL is " + driver.getCurrentUrl());
			chPg.takeScreenShot();
			return learnServiceResult;
		}
	}

	@Test(dataProvider = "getData", priority = 1)
	public boolean verifyPracticeService(Hashtable<String, String> data, SoftAssert sAssert) throws Exception {
		boolean practiceServiceResult = false;
		try {
			ChapterPage chPg = new ChapterPage(driver, test);
			WebDriverWait wt = new WebDriverWait(driver, 60);
			int PractisePresent = chPg.getPracticeTb().size();
			if (PractisePresent != 0) {
				wt.until(ExpectedConditions.elementToBeClickable(chPg.getPracticeTb().get(0)));
				clickElement(chPg.getPracticeTb(), 0, "Practice Tab");
				int MotionGalleryPresent = chPg.getMotionGallery().size();
				test.log(LogStatus.INFO, "MotionGalleryPresent" + MotionGalleryPresent);
				chPg.takeScreenShot();
				if (MotionGalleryPresent != 0) {
					System.out.println("Motion Gallery");
					chPg.MotionGallery();
				}

				wt.until(ExpectedConditions.elementToBeClickable(chPg.getPracticeTb().get(0)));
				clickElement(chPg.getPracticeTb(), 0, "chPg.getPracticeTb()");
				int NCERTPresent = chPg.getNCERTSol().size();
				test.log(LogStatus.INFO, "NCERT Present " + NCERTPresent);
				chPg.takeScreenShot();
				if (NCERTPresent != 0) {
					boolean clickElementResult5 = chPg.clickNCERTSolution();
					if (clickElementResult5) {
						boolean ncertResult6 = chPg.NCERTSolution();
						if (ncertResult6) {
							test.log(LogStatus.PASS, "NCERT Test is Pass");
							System.out.println("NCERT Test is Pass");
							sAssert.fail("NCERT Test is Pass");
						} else {
							test.log(LogStatus.FAIL, "NCERT is Fail,Location is : " + driver.getCurrentUrl());
							System.out.println("NCERT is Fail,Location is : " + driver.getCurrentUrl());
							sAssert.fail("NCERT is Fail,Location is : " + driver.getCurrentUrl());
						}
						/*
						 * String result = chPg.addNotes(data.get("Title"), data.get("Description"));
						 * assertContains(chPg.addSuccess.getText().trim(), "Added Successfully",
						 * "Verifying Add Notes Functionality", sAssert);
						 * chPg.addSchedule(data.get("Title"));
						 */
					} else {
						test.log(LogStatus.FAIL,
								"Not able to clickElement on NCERTPresent,Location is : " + driver.getCurrentUrl());
						System.out.println(
								"Not able to clickElement on NCERTPresent,Location is : " + driver.getCurrentUrl());
						sAssert.fail(
								"Not able to clickElement on NCERTPresent,Location is : " + driver.getCurrentUrl());
					}
					Thread.sleep(5000);
				}

				wt.until(ExpectedConditions.elementToBeClickable(chPg.getPracticeTb().get(0)));
				clickElement(chPg.getPracticeTb(), 0, "getPracticeTb");
				int QAPresent = chPg.getQA().size();
				test.log(LogStatus.INFO, "QAPresent" + QAPresent);
				chPg.takeScreenShot();
				if (QAPresent != 0) {
					System.out.println("QA");
					boolean clickElementQA = clickElement(chPg.getQA(), 0, "getQA");
					if (clickElementQA) {
						boolean QAResult = chPg.QA();
						if (QAResult) {
							test.log(LogStatus.PASS, "Q&A Test is Pass ");
							System.out.println("Q&A Test is Pass ");
							sAssert.fail("Q&A Test is Pass ");
						} else {
							test.log(LogStatus.FAIL, "Q&A Test is Fail ,Location is : " + driver.getCurrentUrl());
							System.out.println("Q&A Test is Fail ,Location is : " + driver.getCurrentUrl());
							sAssert.fail("Q&A Test is Fail ,Location is : " + driver.getCurrentUrl());
						}
					} else {
						test.log(LogStatus.FAIL,
								"Not able to clickElement on Q&A,Location is : " + driver.getCurrentUrl());
						System.out.println("Not able to clickElement on Q&A,Location is : " + driver.getCurrentUrl());
						sAssert.fail("Not able to clickElement on Q&A,Location is : " + driver.getCurrentUrl());
					}

				}

				wt.until(ExpectedConditions.elementToBeClickable(chPg.getPracticeTb().get(0)));
				clickElement(chPg.getPracticeTb(), 0, "chPg.getPracticeTb()");
				int HOTSPresent = chPg.getNCERTSol().size();
				test.log(LogStatus.INFO, "HOTSPresent" + HOTSPresent);
				chPg.takeScreenShot();
				if (HOTSPresent != 0) {
					boolean hotsResult = chPg.HOTS();
					if (hotsResult) {
						test.log(LogStatus.FAIL, "HOTS Test is Pass");
						System.out.println("HOTS Test is Pass");
						sAssert.fail("HOTS Test is Pass");
					} else {
						test.log(LogStatus.FAIL, "HOTS Test is Fail ,Location is : " + driver.getCurrentUrl());
						System.out.println("HOTS Test is Fail ,Location is : " + driver.getCurrentUrl());
						sAssert.fail("HOTS Test is Fail ,Location is : " + driver.getCurrentUrl());
					}
				}

				wt.until(ExpectedConditions.elementToBeClickable(chPg.getPracticeTb().get(0)));
				clickElement(chPg.getPracticeTb(), 0, "chPg.getPracticeTb()");
				int TopicWiseQAPresent = chPg.getTopicWiseQA().size();
				test.log(LogStatus.INFO, "TopicWiseQAPresent" + TopicWiseQAPresent);
				chPg.takeScreenShot();
				if (TopicWiseQAPresent != 0) {
					System.out.println("TopicWiseQA");
					chPg.TopicwiseQA();
				}

				wt.until(ExpectedConditions.elementToBeClickable(chPg.getPracticeTb().get(0)));
				chPg.getPracticeTb().get(0).click();
				int CaseStudyPresent = chPg.getCaseStudy().size();
				test.log(LogStatus.INFO, "CaseStudyPresent" + CaseStudyPresent);
				chPg.takeScreenShot();
				if (CaseStudyPresent != 0) {
					System.out.println("Case Study");
					chPg.CaseStudy();
				}

				wt.until(ExpectedConditions.elementToBeClickable(chPg.getPracticeTb().get(0)));
				clickElement(chPg.getPracticeTb(), 0, "chPg.getPracticeTb()");
				int AssignmentPresent = chPg.getAssignment().size();
				test.log(LogStatus.INFO, "AssignmentPresent" + AssignmentPresent);
				chPg.takeScreenShot();
				System.out.println(AssignmentPresent);
				if (AssignmentPresent != 0) {
					System.out.println("Assignment is present");
					chPg.Assignment();
				}

				wt.until(ExpectedConditions.elementToBeClickable(chPg.getPracticeTb().get(0)));
				clickElement(chPg.getPracticeTb(), 0, "chPg.getPracticeTb()");
				int ConceptCraftPresent = chPg.getConceptCraft().size();
				test.log(LogStatus.INFO, "ConceptCraftPresent" + ConceptCraftPresent);
				chPg.takeScreenShot();
				if (ConceptCraftPresent != 0) {
					System.out.println("Concept Craft");
					chPg.ConceptCraft();
				}
			}
			return practiceServiceResult;
		} catch (Exception e) {
			practiceServiceResult = false;
			ChapterPage chPg = new ChapterPage(driver, test);
			chPg.takeScreenShot();
			test.log(LogStatus.FAIL,
					"Getting Exception on Service Page :" + message(e) + " and URL is " + driver.getCurrentUrl());
			sAssert
					.fail("Getting Exception on Service Page :" + message(e) + " and URL is " + driver.getCurrentUrl());
			System.out.println(
					"Getting Exception on Service Page :" + message(e) + " and URL is " + driver.getCurrentUrl());
			return practiceServiceResult;
		}
	}

	@Test(dataProvider = "getData", priority = 1)
	public boolean verifyTestService(Hashtable<String, String> data, SoftAssert sAssert) throws Exception {
		boolean testServiceResult = false;
		try {
			// SoftAssert sAssert = new SoftAssert();
			ChapterPage chPg = new ChapterPage(driver, test);
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.elementToBeClickable(chPg.getTestTb().get(0)));
			clickElement(chPg.getTestTb(), 0, "chPg.getTestTb()");
			test.log(LogStatus.INFO, "Open Test");
			chPg.takeScreenShot();
			wt.until(ExpectedConditions.elementToBeClickable(chPg.getTestTb().get(0)));
			clickElement(chPg.getTestTb(), 0, "chPg.getTestTb()");
			int MCQPresent = chPg.getMCQ().size();
			if (MCQPresent != 0) {
				boolean clickElementMCQResult = chPg.openMCQTest();
				if (clickElementMCQResult) {
					/*
					 * try { String result = chPg.addNotes(data.get("Title"),
					 * data.get("Description")); assertContains(chPg.addSuccess.getText().trim(),
					 * "Added Successfully", "Verifying Add Notes Functionality", sAssert);
					 * chPg.addSchedule(data.get("Title")); } catch (Exception e) {
					 * 
					 * }
					 */

					boolean mcqResult = chPg.MCQ();
					if (mcqResult) {
						boolean testTabDisplay = fluentWaitIsDisplay((By.xpath("//a[@id='test-panel']")), 10,
								"Test Tab");
						if (testTabDisplay) {
							test.log(LogStatus.PASS, "MCQ Test is Pass");
							System.out.println("MCQ Test is Pass");
							testServiceResult = true;
						} else {
							test.log(LogStatus.FAIL, "MCQ Test is Fail, Location is " + driver.getCurrentUrl());
							System.out.println("MCQ Test is Fail, Location is " + driver.getCurrentUrl());
							sAssert.fail("MCQ Test is Fail, Location is " + driver.getCurrentUrl());
							testServiceResult = false;
						}
					}
				} else {
					test.log(LogStatus.FAIL, "Not able to clickElement on MCQ");
					System.out.println("Not able to clickElement on MCQ");
					sAssert.fail("Not able to clickElement on MCQ");
					testServiceResult = false;
				}

			}
			wt.until(ExpectedConditions.elementToBeClickable(chPg.getTestTb().get(0)));
			clickElement(chPg.getTestTb(), 0, "chPg.getTestTb()");
			int AdaptiveTestPresent = chPg.getAdaptiveTest().size();
			if (AdaptiveTestPresent != 0) {
				boolean clickElementadaptiveTest = clickElement(chPg.getAdaptiveTest(), 0, "getAdaptiveTest");
				if (clickElementadaptiveTest) {
					boolean adaptiveTestResult = chPg.AdaptiveTest();
					if (adaptiveTestResult) {
						boolean testTabDisplay = fluentWaitIsDisplay((By.xpath("//a[@id='test-panel']")), 10,
								"Test Tab");
						if (testTabDisplay) {
							test.log(LogStatus.PASS, "Adaptive Test Pass");
							System.out.println("Adaptive Test Pass");
							Reporter.log("Adaptive Test Pass");
							testServiceResult = true;
						} else {
							test.log(LogStatus.FAIL, "Adaptive Test Fail");
							System.out.println("Adaptive Test Fail");
							sAssert.fail("Adaptive Test Fail");
							testServiceResult = false;
						}
					}
				} else {
					test.log(LogStatus.FAIL, "Not able to clickElement on Adaptive");
					System.out.println("Not able to clickElement on Adaptive");
					sAssert.fail("Not able to clickElement on Adaptive");
					testServiceResult = false;
				}

			}

			wt.until(ExpectedConditions.elementToBeClickable(chPg.getTestTb().get(0)));
			clickElement(chPg.getTestTb(), 0, "chPg.getTestTb()");
			int PeriodicTestPresent = chPg.getPeriodicTest().size();
			if (PeriodicTestPresent != 0) {
				boolean periodicTest = chPg.PeriodicTest();
				if (periodicTest) {
					testServiceResult = true;
					test.log(LogStatus.PASS, "Periodic Test Pass");
					System.out.println("Periodic Test Pass");
					Reporter.log("Periodic Test Pass");

				} else {
					test.log(LogStatus.FAIL, "Periodic Test Fail");
					System.out.println("Periodic Test Fail");
					sAssert.fail("Periodic Test Fail");
					testServiceResult = false;
				}

			}

			wt.until(ExpectedConditions.elementToBeClickable(chPg.getTestTb().get(0)));
			clickElement(chPg.getTestTb(), 0, "chPg.getTestTb()");
			int UniformTestPresent = chPg.getUniformTest().size();
			if (UniformTestPresent != 0) {
				boolean unformTest = chPg.UniformTest();
				if (unformTest) {
					testServiceResult = true;
					test.log(LogStatus.PASS, "Uniform Test Pass");
					System.out.println("Uniform Test Pass");
					Reporter.log("Uniform Test Pass");
				} else {
					test.log(LogStatus.FAIL, "Uniform Test Fail");
					System.out.println("Uniform Test Fail");
					sAssert.fail("Uniform Test Fail");
					testServiceResult = false;
				}
			}

			wt.until(ExpectedConditions.elementToBeClickable(chPg.getTestTb().get(0)));
			clickElement(chPg.getTestTb(), 0, "chPg.getTestTb()");
			int SolvedPapersPresent = chPg.getSolvedPapers().size();
			if (SolvedPapersPresent != 0) {
				boolean solvedpaper = chPg.SolvedPapers();
				if (solvedpaper) {
					testServiceResult = true;
					test.log(LogStatus.PASS, "SolvedPapers Test Pass");
					System.out.println("SolvedPapers Test Pass");
					Reporter.log("SolvedPapers Test Pass");
				} else {
					testServiceResult = true;
					test.log(LogStatus.FAIL, "SolvedPapers Test Fail");
					System.out.println("SolvedPapers Test Fail");
					Reporter.log("SolvedPapers Test Fail");
				}
				testServiceResult = true;
			}
			return testServiceResult;
		} catch (Exception e) {
			testServiceResult = false;
			ChapterPage chPg = new ChapterPage(driver, test);
			test.log(LogStatus.FAIL,
					"Getting Exception on Service Page  :" + message(e) + " and URL is " + driver.getCurrentUrl());
			sAssert.fail(
					"Getting Exception on Service Page  :" + message(e) + " and URL is " + driver.getCurrentUrl());
			System.out.println(
					"Getting Exception on Service Page  :" + message(e) + " and URL is " + driver.getCurrentUrl());
			chPg.takeScreenShot();
			return testServiceResult;
		}
	}
}
