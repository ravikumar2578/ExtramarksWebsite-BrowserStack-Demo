package com.extramarks_website_testcases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Hashtable;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.extramarks_website_pages.ChapterPage;
import com.extramarks_website_pages.ClassPage;
import com.extramarks_website_pages.DashBoardPage;
import com.extramarks_website_pages.Sub_SubjectPage;
import com.extramarks_website_pages.SubjectPage;
import com.extramarks_website_pages.TermSubjectPage;
import com.extramarks_website_utils.Constants;
import com.extramarks_website_utils.DataUtil;
import com.extramarks_website_utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;



public class TestTest extends BaseTest {

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

	@Test(dataProvider = "getData", priority = 1, enabled = true)
	public void verifyTest(Hashtable<String, String> data) throws Exception {
		SoftAssert sAssert = new SoftAssert();
		ServiceTest service = new ServiceTest();
		String expectedResult = "Test_PASS";
		String actualResult = "Test_PASS";
		Boolean loginResult = defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"),
				data.get("Board"), data.get("Class"));
		DashBoardPage dp = new DashBoardPage(driver, test);
		Thread.sleep(5000);
		dp.openstudytab();
		Thread.sleep(5000);
		ClassPage cp = new ClassPage(driver, test);
		int subsize = cp.getTotalSub();
//Subject 

		for (int subj = 0; subj < subsize; subj++) {
			int subNo = subj + 1;
			WebDriverWait wt = new WebDriverWait(driver, 30);
			String subject = "";
			try {
				wt.until(ExpectedConditions.visibilityOfAllElements(cp.getSubjectLinks()));
				String subjects = cp.getSubjectLinks().get(subj).getText();
				String[] subjects2 = subjects.split("\\n");
				subject = subjects2[0].trim();
			} catch (Exception e) {
				System.out.println("Subject not found " + subject);
				test.log(LogStatus.INFO, "Subjects " + subNo + " :" + subject);
				dp.openstudytab();
				Thread.sleep(5000);
				continue;
			}

			System.out.println("Subjects " + subNo + " :" + subject);
			test.log(LogStatus.INFO, "Subjects " + subNo + " :" + subject);

			boolean clickElementSubResult = clickElement(cp.getSubjectLinks(), subj, "SubjectLinks");
			if (clickElementSubResult) {
				SubjectPage sp = new SubjectPage(driver, test);
				Thread.sleep(2000);
				cp.takeScreenShot();
				Thread.sleep(3000);
// Term 				
				int termSize = sp.getTotalTerm();
				if (termSize != 0) {
					for (int term = 0; term < termSize; term++) {
						String termSubject = "";
						try {
							wt.until(ExpectedConditions.visibilityOfAllElements(sp.getTotalTermLinks()));
							String termSubjects = sp.getTotalTermLinks().get(term).getText();
							String[] termSubject2 = termSubjects.split("\\n");
							termSubject = termSubject2[0].trim();
							System.out.println("Term Subject :" + termSubject);
							test.log(LogStatus.INFO, "Term Subject :" + termSubject);
						} catch (Exception e) {
							System.out.println("Term Subject not found " + subject);
							test.log(LogStatus.INFO, "Term Subject not found " + subject);
							dp.openstudytab();
							Thread.sleep(5000);
							clickElement(cp.getSubjectLinks(), subj, "SubjectLinks");
							Thread.sleep(5000);
							continue;
						}
						boolean clickTermResult = clickElement(sp.getTotalTermLinks(), term, "cp.getSubSubjectLinks");
						if (clickTermResult) {
//Sub Subject
							TermSubjectPage tp = new TermSubjectPage(driver, test);
							int subSubjsize = tp.getTotalSubSubj();
							if (subSubjsize != 0) {
								String subSubject = "";
								for (int subSubj = 0; subSubj < subSubjsize; subSubj++) {
									try {
										wt.until(ExpectedConditions.visibilityOfAllElements(tp.getSubSubjectLinks()));
										String subSubjects = tp.getSubSubjectLinks().get(subSubj).getText();
										String[] subSubject2 = subSubjects.split("\\n");
										subSubject = subSubject2[0].trim();
										System.out.println("Sub Subjects:" + subSubject);
										test.log(LogStatus.INFO, "Sub Subjects:" + subSubject);
									} catch (Exception e) {
										System.out.println("Sub Subject not found " + subject);
										test.log(LogStatus.INFO, "Sub Subject not found " + subject);
										dp.openstudytab();
										Thread.sleep(5000);
										clickElement(cp.getSubjectLinks(), subj, "SubjectLinks");
										Thread.sleep(5000);
										clickElement(sp.getTotalTermLinks(), term, "TermSubjectLinks");
										Thread.sleep(5000);
										continue;
									}
									boolean clickElementSubSubjectResult = clickElement(tp.getSubSubjectLinks(),
											subSubj, "cp.getSubSubjectLinks");
									if (clickElementSubSubjectResult) {
										Sub_SubjectPage ssp = new Sub_SubjectPage(driver, test);
										Thread.sleep(2000);
										tp.takeScreenShot();
// Chapter
										int chapSize = ssp.getMainChapter().size();
										System.out.println("No. of chapters in this subject = " + chapSize);
										test.log(LogStatus.INFO, "No. of chapters in this subject = " + chapSize);
										Thread.sleep(1000);
										fluentWaitIsDisplay(ssp.getMainChapter(), 0, 30, "sp.getMainChapter");
										if (chapSize != 0) {
											for (int ch = 0; ch < chapSize; ch++) {

												int chapNo = ch + 1;
												wt.until(ExpectedConditions
														.visibilityOfAllElements(ssp.getMainChapter()));
												Thread.sleep(5000);
												String chapter = ssp.getMainChapter().get(ch).getText();
												System.out.println("Main Chapter :  " + chapNo + "  " + chapter);
												test.log(LogStatus.INFO, "Main Chapter :  " + chapNo + "  " + chapter);
												boolean clickElementchapterResult = clickElement(ssp.getMainChapter(),
														ch, "chapter link");
												if (clickElementchapterResult) {
													ChapterPage chPg = new ChapterPage(driver, test);
													chPg.takeScreenShot();
													Thread.sleep(5000);
// Sub Chapter
													int subChapSize = sp.getSubChapter().size();
													System.out.println("No.Sub Chapters = " + subChapSize);
													test.log(LogStatus.INFO, "No.Sub Chapters = " + subChapSize);

													if (subChapSize != 0)
													// ..............................................
													{
														for (int su = 0; su < subChapSize; ++su) {
															System.out.println("Chapter : "
																	+ sp.getSubChapter().get(su).getText());
															test.log(LogStatus.INFO, "Chapter : "
																	+ sp.getSubChapter().get(su).getText());
															Thread.sleep(1000);
															clickElement(sp.getSubChapter(), su, "sp.getSubChapter()");
															Thread.sleep(2000);
															chPg.takeScreenShot();
// Post Sub Chapter
															int postSubChap = sp.getPostSubChap().size();
															test.log(LogStatus.INFO,
																	"No. of Sub-Sub Chapter : " + postSubChap);
															System.out.println(
																	"No. of Sub-sub Chapters = " + postSubChap);
															if (postSubChap != 0) {
																for (int ps = 0; ps < postSubChap; ps++) {
																	System.out.println("Subchapter : "
																			+ sp.getPostSubChap().get(ps).getText());
																	Thread.sleep(1000);
																	clickElement(sp.getPostSubChap(), ps,
																			"sp.getPostSubChap()");
																	test.log(LogStatus.INFO, "Open sub chapters");
																	Thread.sleep(2000);
																	chPg.takeScreenShot();
// Service Tab Section on post chapter
																	try {
																		boolean testServiceResult = service
																				.verifyTestService(data, sAssert);
																		if (testServiceResult) {
																			test.log(LogStatus.PASS,
																					this.getClass().getSimpleName()
																							+ " Service Pass for  "
																							+ subject + ">>"
																							+ subSubject + ">>"
																							+ chapter);
																			System.out.println(
																					this.getClass().getSimpleName()
																							+ " Service Pass for  "
																							+ subject + ">>"
																							+ subSubject + ">>"
																							+ chapter);
																		} else {
																			test.log(LogStatus.FAIL,
																					this.getClass().getSimpleName()
																							+ " Service Fail for  "
																							+ subject + ">>"
																							+ subSubject + ">>"
																							+ chapter);
																			System.out.println(
																					this.getClass().getSimpleName()
																							+ " Service Fail for  "
																							+ subject + ">>"
																							+ subSubject + ">>"
																							+ chapter);
																			sAssert.fail(this.getClass().getSimpleName()
																					+ " Service Fail for  " + subject
																					+ ">>" + subSubject + ">>"
																					+ chapter);
																			actualResult = this.getClass()
																					.getSimpleName() + "Service_Fail";
																		}
																	} catch (Exception e) {
																		test.log(LogStatus.INFO,
																				this.getClass().getSimpleName()
																						+ " Fail for  " + subject + ">>"
																						+ subSubject + ">>" + chapter);
																		System.out
																				.println(this.getClass().getSimpleName()
																						+ " Fail for  " + subject + ">>"
																						+ subSubject + ">>" + chapter);
																		sAssert.fail(this.getClass().getSimpleName()
																				+ " Fail for  " + subject + ">>"
																				+ subSubject + ">>" + chapter);
																		chPg.takeScreenShot();
																	}
																	if (chPg.BackToChapter.size() != 0) {
																		JavascriptExecutor jschPgt = (JavascriptExecutor) driver;
																		jschPgt.executeScript("arguments[0].click();",
																				chPg.BackToChapter.get(0));
																		boolean termSubjectDisplay = fluentWaitIsDisplay(
																				sp.getTotalTermLinks(), term, 30,
																				"TermSubj");
																		if (termSubjectDisplay) {
																			int termSubjsize2 = sp.getTotalTerm();
																			if (termSubjsize2 != 0) {
																				wt.until(ExpectedConditions
																						.elementToBeClickable(
																								sp.getTotalTermLinks()
																										.get(term)));
																				boolean clickTermSubjectResult2 = clickElement(
																						sp.getTotalTermLinks(), term,
																						"TermSubjectLinks");
																				if (clickTermSubjectResult2) {
																					boolean clickSubSubjectResult3 = clickElement(
																							sp.getSubSubjectLinks(),
																							subSubj, "SubSubjectLinks");
																					if (clickSubSubjectResult3) {
																						System.out.println(
																								"User navigated on Chapter page from Test/Practice/Test Tab "
																										+ subject + ">>"
																										+ subSubject
																										+ ">>"
																										+ termSubject);

																						test.log(LogStatus.INFO,
																								"User navigated on Chapter page from Test/Practice/Test Tab "
																										+ subject + ">>"
																										+ subSubject
																										+ ">>"
																										+ termSubject);
																						chPg.takeScreenShot();
																					} else {
																						System.out.println(
																								"User not able to navigated on chapter from Sub Subjects "
																										+ subject + ">>"
																										+ termSubject
																										+ ">>"
																										+ subSubject);

																						test.log(LogStatus.INFO,
																								"User not able to navigated on chapter from Sub Subjects "
																										+ subject + ">>"
																										+ termSubject
																										+ ">>"
																										+ subSubject);
																						chPg.takeScreenShot();
																					}
																				}
																			}
																		} else {
																			System.out.println(
																					"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test/Practice/Test Tab  "
																							+ subject + ">>"
																							+ termSubject + ">>"
																							+ subSubject);
																			test.log(LogStatus.INFO,
																					"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test/Practice/Test Tab  "
																							+ subject + ">>"
																							+ termSubject + ">>"
																							+ subSubject);
																			sAssert.fail(
																					"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test/Practice/Test Tab  "
																							+ subject + ">>"
																							+ termSubject + ">>"
																							+ subSubject);
																			chPg.takeScreenShot();
																			dp.openstudytab();
																			Thread.sleep(5000);
																			clickElement(cp.getSubjectLinks(), subj,
																					"SubjectLinks");
																			Thread.sleep(5000);
																			clickElement(sp.getTotalTermLinks(), subj,
																					"TotalTermLinks");
																			Thread.sleep(5000);
																			clickElement(sp.getSubSubjectLinks(),
																					subSubj, "SubSubjectLinks");
																			Thread.sleep(5000);
																		}
																	} else {
																		driver.navigate().back();
																	}
																	Thread.sleep(5000);
																	clickElement(sp.getMainChapter(), ch,
																			"sp.getMainChapter()");
																	Thread.sleep(5000);
																	clickElement(sp.getSubChapter(), su,
																			"sp.getSubChapter()");
																	Thread.sleep(5000);
																}

															} else {
// Service Tab Section on sub chapter												
																try {
																	boolean testServiceResult = service
																			.verifyTestService(data, sAssert);
																	if (testServiceResult) {
																		test.log(LogStatus.PASS,
																				this.getClass().getSimpleName()
																						+ " Service Pass for  "
																						+ subject + ">>" + subSubject
																						+ ">>" + termSubject + ">>"
																						+ chapter);
																		System.out
																				.println(this.getClass().getSimpleName()
																						+ " Service Pass for  "
																						+ subject + ">>" + subSubject
																						+ ">>" + termSubject + ">>"
																						+ chapter);
																	} else {
																		test.log(LogStatus.FAIL,
																				this.getClass().getSimpleName()
																						+ " Service Fail for  "
																						+ subject + ">>" + subSubject
																						+ ">>" + termSubject + ">>"
																						+ chapter);
																		System.out
																				.println(this.getClass().getSimpleName()
																						+ " Service Fail for  "
																						+ subject + ">>" + subSubject
																						+ ">>" + termSubject + ">>"
																						+ chapter);
																		sAssert.fail(this.getClass().getSimpleName()
																				+ " Service Fail for  " + subject + ">>"
																				+ subSubject + ">>" + termSubject + ">>"
																				+ chapter);
																		actualResult = this.getClass().getSimpleName()
																				+ "Service_Fail";
																	}
																} catch (Exception e) {
																	test.log(LogStatus.INFO,
																			this.getClass().getSimpleName()
																					+ " Fail for  " + subject + ">>"
																					+ subSubject + ">>" + termSubject
																					+ ">>" + chapter);
																	System.out.println(this.getClass().getSimpleName()
																			+ " Fail for  " + subject + ">>"
																			+ subSubject + ">>" + termSubject + ">>"
																			+ chapter);
																	sAssert.fail(this.getClass().getSimpleName()
																			+ " Fail for  " + subject + ">>"
																			+ subSubject + ">>" + termSubject + ">>"
																			+ chapter);
																	chPg.takeScreenShot();
																}
																if (chPg.BackToChapter.size() != 0) {
																	JavascriptExecutor jschPgt = (JavascriptExecutor) driver;
																	jschPgt.executeScript("arguments[0].click();",
																			chPg.BackToChapter.get(0));
																	boolean termSubjectDisplay = fluentWaitIsDisplay(
																			sp.getTotalTermLinks(), term, 30,
																			"TermSubj");
																	if (termSubjectDisplay) {
																		int termSubjsize2 = sp.getTotalTerm();
																		if (termSubjsize2 != 0) {
																			wt.until(ExpectedConditions
																					.elementToBeClickable(
																							sp.getTotalTermLinks()
																									.get(term)));
																			boolean clickTermSubjectResult2 = clickElement(
																					sp.getTotalTermLinks(), term,
																					"TermSubjectLinks");
																			if (clickTermSubjectResult2) {
																				boolean clickSubSubjectResult3 = clickElement(
																						sp.getSubSubjectLinks(),
																						subSubj, "SubSubjectLinks");
																				if (clickSubSubjectResult3) {
																					System.out.println(
																							"User navigated on Chapter page from Test/Practice/Test Tab "
																									+ subject + ">>"
																									+ subSubject + ">>"
																									+ termSubject);

																					test.log(LogStatus.INFO,
																							"User navigated on Chapter page from Test/Practice/Test Tab "
																									+ subject + ">>"
																									+ subSubject + ">>"
																									+ termSubject);
																					chPg.takeScreenShot();
																				} else {
																					System.out.println(
																							"User not able to navigated on chapter from Sub Subjects "
																									+ subject + ">>"
																									+ termSubject + ">>"
																									+ subSubject);

																					test.log(LogStatus.INFO,
																							"User not able to navigated on chapter from Sub Subjects "
																									+ subject + ">>"
																									+ termSubject + ">>"
																									+ subSubject);
																					chPg.takeScreenShot();
																				}
																			}
																		}
																	} else {
																		System.out.println(
																				"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test/Practice/Test Tab  "
																						+ subject + ">>" + termSubject
																						+ ">>" + subSubject);
																		test.log(LogStatus.INFO,
																				"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test/Practice/Test Tab  "
																						+ subject + ">>" + termSubject
																						+ ">>" + subSubject);
																		sAssert.fail(
																				"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test/Practice/Test Tab  "
																						+ subject + ">>" + termSubject
																						+ ">>" + subSubject);
																		chPg.takeScreenShot();
																		Thread.sleep(5000);
																		clickElement(cp.getSubjectLinks(), subj,
																				"SubjectLinks");
																		Thread.sleep(5000);
																		clickElement(sp.getTotalTermLinks(), subj,
																				"TotalTermLinks");
																		Thread.sleep(5000);
																		clickElement(sp.getSubSubjectLinks(), subSubj,
																				"SubSubjectLinks");
																		Thread.sleep(5000);
																	}
																} else {
																	driver.navigate().back();
																}

																Thread.sleep(5000);
																clickElement(sp.getMainChapter(), ch,
																		"sp.getMainChapter()");
																Thread.sleep(5000);
															}

														}

													}
// Sub Chap Else Start ...............................................................................................................................................

													else {
// Service Tab Section on chapter												
														try {
															boolean testServiceResult = service.verifyTestService(data,
																	sAssert);
															if (testServiceResult) {
																test.log(LogStatus.PASS,
																		this.getClass().getSimpleName()
																				+ " Service Pass for  " + subject + ">>"
																				+ subSubject + ">>" + termSubject + ">>"
																				+ chapter);
																System.out.println(this.getClass().getSimpleName()
																		+ " Service Pass for  " + subject + ">>"
																		+ subSubject + ">>" + termSubject + ">>"
																		+ chapter);
															} else {
																test.log(LogStatus.FAIL,
																		this.getClass().getSimpleName()
																				+ " Service Fail for  " + subject + ">>"
																				+ subSubject + ">>" + termSubject + ">>"
																				+ chapter);
																System.out.println(this.getClass().getSimpleName()
																		+ " Service Fail for  " + subject + ">>"
																		+ subSubject + ">>" + termSubject + ">>"
																		+ chapter);
																sAssert.fail(this.getClass().getSimpleName()
																		+ " Service Fail for  " + subject + ">>"
																		+ subSubject + ">>" + chapter);
																actualResult = this.getClass().getSimpleName()
																		+ "Service_Fail";
															}
														} catch (Exception e) {
															test.log(LogStatus.INFO,
																	this.getClass().getSimpleName() + " Fail for  "
																			+ subject + ">>" + subSubject + ">>"
																			+ termSubject + ">>" + chapter);
															System.out.println(this.getClass().getSimpleName()
																	+ " Fail for  " + subject + ">>" + subSubject + ">>"
																	+ termSubject + ">>" + chapter);
															sAssert.fail(this.getClass().getSimpleName() + " Fail for  "
																	+ subject + ">>" + subSubject + ">>" + termSubject
																	+ ">>" + chapter);
															chPg.takeScreenShot();
														}
														if (chPg.BackToChapter.size() != 0) {
															JavascriptExecutor jschPgt = (JavascriptExecutor) driver;
															jschPgt.executeScript("arguments[0].click();",
																	chPg.BackToChapter.get(0));
															boolean termSubjectDisplay = fluentWaitIsDisplay(
																	sp.getTotalTermLinks(), term, 30, "TermSubj");
															if (termSubjectDisplay) {
																int termSubjsize2 = sp.getTotalTerm();
																if (termSubjsize2 != 0) {
																	wt.until(ExpectedConditions.elementToBeClickable(
																			sp.getTotalTermLinks().get(term)));
																	boolean clickTermSubjectResult2 = clickElement(
																			sp.getTotalTermLinks(), term,
																			"TermSubjectLinks");
																	if (clickTermSubjectResult2) {
																		boolean clickSubSubjectResult3 = clickElement(
																				sp.getSubSubjectLinks(), subSubj,
																				"SubSubjectLinks");
																		if (clickSubSubjectResult3) {
																			System.out.println(
																					"User navigated on Chapter page from Test/Practice/Test Tab "
																							+ subject + ">>"
																							+ subSubject + ">>"
																							+ termSubject);

																			test.log(LogStatus.INFO,
																					"User navigated on Chapter page from Test/Practice/Test Tab "
																							+ subject + ">>"
																							+ subSubject + ">>"
																							+ termSubject);
																			chPg.takeScreenShot();
																		} else {
																			System.out.println(
																					"User not able to navigated on chapter from Sub Subjects "
																							+ subject + ">>"
																							+ termSubject + ">>"
																							+ subSubject);

																			test.log(LogStatus.INFO,
																					"User not able to navigated on chapter from Sub Subjects "
																							+ subject + ">>"
																							+ termSubject + ">>"
																							+ subSubject);
																			chPg.takeScreenShot();
																		}
																	}
																}
															} else {
																System.out.println(
																		"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test/Practice/Test Tab  "
																				+ subject + ">>" + termSubject + ">>"
																				+ subSubject);
																test.log(LogStatus.INFO,
																		"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test/Practice/Test Tab  "
																				+ subject + ">>" + termSubject + ">>"
																				+ subSubject);
																sAssert.fail(
																		"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test/Practice/Test Tab  "
																				+ subject + ">>" + termSubject + ">>"
																				+ subSubject);
																chPg.takeScreenShot();
																dp.openstudytab();
																Thread.sleep(5000);
																clickElement(cp.getSubjectLinks(), subj,
																		"SubjectLinks");
																Thread.sleep(5000);
																clickElement(sp.getTotalTermLinks(), subj,
																		"TotalTermLinks");
																Thread.sleep(5000);
																clickElement(sp.getSubSubjectLinks(), subSubj,
																		"SubSubjectLinks");
																Thread.sleep(5000);
															}
														} else {
															driver.navigate().back();
														}
													}
												} else {
													actualResult = "Not_able_to_clickElement_on_chapter_Fail " + subject + ">>"
															+ subSubject + ">>" + chapter;
													System.out.println("Not_able_to_clickElement_on_chapter, Location is --> "
															+ subject + ">>" + subSubject + ">>" + termSubject + ">>"
															+ chapter);
													test.log(LogStatus.FAIL,
															"Not_able_to_clickElement_on_chapter, Location is --> " + subject
																	+ ">>" + subSubject + ">>" + termSubject + ">>"
																	+ chapter);
													sAssert.fail("Not_able_to_clickElement_on_chapter, Location is --> "
															+ subject + ">>" + subSubject + ">>" + termSubject + ">>"
															+ chapter);
													Thread.sleep(5000);
													dp.openstudytab();
													Thread.sleep(5000);
													clickElement(cp.getSubjectLinks(), subj, "SubjectLinks");
													Thread.sleep(5000);
													clickElement(sp.getTotalTermLinks(), term,"Term Subject link");
													Thread.sleep(5000);
													clickElement(tp.getSubSubjectLinks(), subSubj,"Sub Subject link");
													Thread.sleep(5000);
													clickElement(ssp.getMainChapter(), ch,"Chapter link");
													Thread.sleep(5000);
													continue;
												}
											}

										} else {
											actualResult = "Chapter_page_not_display_Fail";
											System.out.println("Chapter page is not Displayed, Location is --> "
													+ subject + " -- >" + termSubject + " -- >" + subSubject);
											test.log(LogStatus.FAIL, "Chapter page is not Displayed, Location is --> "
													+ subject + " -- >" + termSubject + " -- >" + subSubject);
											sAssert.fail("Chapter page is not Displayed, Location is --> " + subject
													+ " -- >" + termSubject + " -- >" + subSubject);
											sp.takeScreenShot();

										}
										if (ssp.BackToChapter.size() != 0) {
											JavascriptExecutor jschPgt = (JavascriptExecutor) driver;
											jschPgt.executeScript("arguments[0].click();", ssp.BackToChapter.get(0));
										} else {
											driver.navigate().back();
										}
										Thread.sleep(5000);

// if clickElement on Sub subject Fail								
									} else {
										actualResult = "Not_able_to_click_on_Sub_subject_Fail " + subject + ">>"
												+ termSubject + ">>" + subSubject;
										System.out.println("Not_able_to_click_on_Sub_subject, Location is --> "
												+ subject + ">>" + termSubject + ">>" + subSubject);
										test.log(LogStatus.FAIL, "Not_able_to_click_on_Sub_subject, Location is --> "
												+ subject + ">>" + termSubject + ">>" + subSubject);
										sAssert.fail("Not_able_to_click_on_Sub_subject, Location is --> " + subject
												+ ">>" + termSubject + ">>" + subSubject);
										Thread.sleep(5000);
									}
								}
//Sub Subject end
// Chapter start in case Sub subject not present
							} else {

								int chapSize = sp.getMainChapter().size();
								System.out.println("No. of chapters in this subject = " + chapSize);
								test.log(LogStatus.INFO, "No. of chapters in this subject = " + chapSize);
								Thread.sleep(1000);
								if (chapSize != 0) {
									for (int ch = 0; ch < chapSize; ch++) {
										ChapterPage chPg = new ChapterPage(driver, test);
										int chapNo = ch + 1;
										wt.until(ExpectedConditions.visibilityOfAllElements(sp.getMainChapter()));
										Thread.sleep(5000);
										String chapter = sp.getMainChapter().get(ch).getText();
										System.out.println("Main Chapter :  " + chapNo + "  " + chapter);
										test.log(LogStatus.INFO, "Main Chapter :  " + chapNo + "  " + chapter);
										// clickElementing on chapter
										boolean clickElementchapterResult = clickElement(sp.getMainChapter(), ch,
												"chapter link");
										if (clickElementchapterResult) {
											chPg.takeScreenShot();
											Thread.sleep(5000);
											// sub Chapter
											int subChapSize = sp.getSubChapter().size();
											System.out.println("No.Sub Chapters = " + subChapSize);
											test.log(LogStatus.INFO, "No.Sub Chapters = " + subChapSize);
											if (subChapSize != 0)
											// ..............................................
											{
												for (int su = 0; su < subChapSize; ++su) {
													System.out.println(
															"Chapter : " + sp.getSubChapter().get(su).getText());
													test.log(LogStatus.INFO,
															"Chapter : " + sp.getSubChapter().get(su).getText());
													Thread.sleep(1000);
													clickElement(sp.getSubChapter(), su, "sp.getSubChapter()");
													Thread.sleep(5000);
													chPg.takeScreenShot();
													// Post sub Chapter
													int postSubChap = sp.getPostSubChap().size();
													test.log(LogStatus.INFO, "No. of Sub-Sub Chapter : " + postSubChap);
													System.out.println("No. of Sub-sub Chapters = " + postSubChap);
													if (postSubChap != 0) {
														for (int ps = 0; ps < postSubChap; ps++) {
															System.out.println("Subchapter : "
																	+ sp.getPostSubChap().get(ps).getText());
															Thread.sleep(1000);
															clickElement(sp.getPostSubChap(), ps,
																	"sp.getPostSubChap()");
															test.log(LogStatus.INFO, "Open sub chapters");

															Thread.sleep(5000);
															chPg.takeScreenShot();
															// Service Tab Section on post sub chapter
															try {
																boolean testServiceResult = service
																		.verifyTestService(data, sAssert);
																if (testServiceResult) {
																	test.log(LogStatus.PASS,
																			this.getClass().getSimpleName()
																					+ " Service Pass for  " + subject
																					+ ">>" + chapter);
																	System.out.println(this.getClass().getSimpleName()
																			+ " Service Pass for  " + subject + ">>"
																			+ chapter);
																} else {
																	test.log(LogStatus.FAIL,
																			this.getClass().getSimpleName()
																					+ " Fail for  " + subject + ">>"
																					+ chapter);
																	System.out.println(this.getClass().getSimpleName()
																			+ " Fail for  " + subject + ">>" + chapter);
																	sAssert.fail(this.getClass().getSimpleName()
																			+ " Fail for  " + subject + ">>" + chapter);
																	actualResult = this.getClass().getSimpleName()
																			+ "Service_Fail";
																}
															} catch (Exception e) {
																test.log(LogStatus.INFO, this.getClass().getSimpleName()
																		+ " Fail for  " + subject + ">>" + chapter);
																System.out.println(this.getClass().getSimpleName()
																		+ " Fail for  " + subject + ">>" + chapter);
																sAssert.fail(this.getClass().getSimpleName()
																		+ " Fail for  " + subject + ">>" + chapter);
																chPg.takeScreenShot();
															}
															if (chPg.BackToChapter.size() != 0) {
																JavascriptExecutor jschPgt = (JavascriptExecutor) driver;
																jschPgt.executeScript("arguments[0].click();",
																		chPg.BackToChapter.get(0));
																boolean subjectDisplay = fluentWaitIsDisplay(
																		cp.getSubjectLinks(), subj, 30,
																		"getTotalSubSubj");
																if (subjectDisplay) {
																	int subjsize2 = cp.getTotalSub();
																	if (subjsize2 != 0) {
																		wt.until(ExpectedConditions
																				.elementToBeClickable(cp
																						.getSubjectLinks().get(subj)));
																		boolean clickSubjectResult2 = clickElement(
																				cp.getSubjectLinks(), subj,
																				"cp.getSubSubjectLinks");
																		if (clickSubjectResult2) {
																			System.out.println(
																					"User not able to navigated on chapter from Sub Subjects "
																							+ subject);

																			test.log(LogStatus.INFO,
																					"User not able to navigated on chapter from Sub Subjects "
																							+ subject);
																			chPg.takeScreenShot();
																		} else {
																			System.out.println(
																					"User not able to navigated on chapter from Sub Subjects "
																							+ subject);

																			test.log(LogStatus.INFO,
																					"User not able to navigated on chapter from Sub Subjects "
																							+ subject);
																			chPg.takeScreenShot();
																		}
																	}
																} else {
																	System.out.println(
																			"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test/Practice/Test Tab  "
																					+ subject);
																	test.log(LogStatus.INFO,
																			"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test/Practice/Test Tab  "
																					+ subject);
																	sAssert.fail(
																			"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test/Practice/Test Tab  "
																					+ subject);
																	chPg.takeScreenShot();
																	dp.openstudytab();
																	Thread.sleep(5000);
																	clickElement(cp.getSubjectLinks(), subj,
																			"SubjectLinks");
																	Thread.sleep(5000);
																}
															} else {
																driver.navigate().back();
															}
															Thread.sleep(5000);
															clickElement(sp.getMainChapter(), ch,
																	"sp.getMainChapter()");

															Thread.sleep(5000);
															clickElement(sp.getSubChapter(), su, "sp.getSubChapter()");
															Thread.sleep(5000);
														}

													} else {
														// Service Tab Section on sub chapter
														try {
															boolean testServiceResult = service.verifyTestService(data,
																	sAssert);
															if (testServiceResult) {
																test.log(LogStatus.PASS,
																		this.getClass().getSimpleName()
																				+ " Service Pass for  " + subject + ">>"
																				+ chapter);
																System.out.println(this.getClass().getSimpleName()
																		+ " Service Pass for  " + subject + ">>"
																		+ chapter);
															} else {
																test.log(LogStatus.FAIL, this.getClass().getSimpleName()
																		+ " Fail for  " + subject + ">>" + chapter);
																System.out.println(this.getClass().getSimpleName()
																		+ " Fail for  " + subject + ">>" + chapter);
																sAssert.fail(this.getClass().getSimpleName()
																		+ " Fail for  " + subject + ">>" + chapter);
																actualResult = this.getClass().getSimpleName()
																		+ "Service_Fail";
															}
														} catch (Exception e) {
															test.log(LogStatus.INFO, this.getClass().getSimpleName()
																	+ " Fail for  " + subject + ">>" + chapter);
															System.out.println(this.getClass().getSimpleName()
																	+ " Fail for  " + subject + ">>" + chapter);
															sAssert.fail(this.getClass().getSimpleName() + " Fail for  "
																	+ subject + ">>" + chapter);
															chPg.takeScreenShot();
														}
														if (chPg.BackToChapter.size() != 0) {
															JavascriptExecutor jschPgt = (JavascriptExecutor) driver;
															jschPgt.executeScript("arguments[0].click();",
																	chPg.BackToChapter.get(0));
															boolean subjectDisplay = fluentWaitIsDisplay(
																	cp.getSubjectLinks(), subj, 30, "getTotalSubSubj");
															if (subjectDisplay) {
																int subSubjsize2 = cp.getTotalSub();
																if (subSubjsize2 != 0) {
																	wt.until(ExpectedConditions.elementToBeClickable(
																			cp.getSubjectLinks().get(subj)));
																	boolean clickSubjectResult2 = clickElement(
																			cp.getSubjectLinks(), subj,
																			"cp.getSubSubjectLinks");
																	if (clickSubjectResult2) {
																		System.out.println(
																				"User not able to navigated on chapter from Sub Subjects "
																						+ subject);

																		test.log(LogStatus.INFO,
																				"User not able to navigated on chapter from Sub Subjects "
																						+ subject);
																		chPg.takeScreenShot();
																	} else {
																		System.out.println(
																				"User not able to navigated on chapter from Sub Subjects "
																						+ subject);

																		test.log(LogStatus.INFO,
																				"User not able to navigated on chapter from Sub Subjects "
																						+ subject);
																		chPg.takeScreenShot();
																	}
																}
															} else {
																System.out.println(
																		"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test/Practice/Test Tab  "
																				+ subject);
																test.log(LogStatus.INFO,
																		"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test/Practice/Test Tab  "
																				+ subject);
																sAssert.fail(
																		"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test/Practice/Test Tab  "
																				+ subject);
																chPg.takeScreenShot();
																dp.openstudytab();
																Thread.sleep(5000);
																clickElement(cp.getSubjectLinks(), subj,
																		"SubjectLinks");
																Thread.sleep(5000);
															}
														} else {
															driver.navigate().back();
														}
														Thread.sleep(5000);
														clickElement(sp.getMainChapter(), ch, "sp.getMainChapter()");
													}

												}

											}
											// ........Sub Chapter End

											else {
												// Service Tab Section on chapter
												try {
													boolean testServiceResult = service.verifyTestService(data,
															sAssert);
													if (testServiceResult) {
														test.log(LogStatus.PASS, this.getClass().getSimpleName()
																+ " Service Pass for  " + subject + ">>" + chapter);
														System.out.println(this.getClass().getSimpleName()
																+ " Service Pass for  " + subject + ">>" + chapter);
													} else {
														test.log(LogStatus.FAIL, this.getClass().getSimpleName()
																+ " Fail for  " + subject + ">>" + chapter);
														System.out.println(this.getClass().getSimpleName()
																+ " Fail for  " + subject + ">>" + chapter);
														sAssert.fail(this.getClass().getSimpleName() + " Fail for  "
																+ subject + ">>" + chapter);
														actualResult = this.getClass().getSimpleName() + "Service_Fail";
													}
												} catch (Exception e) {
													test.log(LogStatus.INFO, this.getClass().getSimpleName()
															+ " Fail for  " + subject + ">>" + chapter);
													System.out.println(this.getClass().getSimpleName() + " Fail for  "
															+ subject + ">>" + chapter);
													sAssert.fail(this.getClass().getSimpleName() + " Fail for  "
															+ subject + ">>" + chapter);
													chPg.takeScreenShot();
												}
												if (chPg.BackToChapter.size() != 0) {
													JavascriptExecutor jschPgt = (JavascriptExecutor) driver;
													jschPgt.executeScript("arguments[0].click();",
															chPg.BackToChapter.get(0));
													boolean subjectDisplay = fluentWaitIsDisplay(cp.getSubjectLinks(),
															subj, 30, "TotalSubj");
													if (subjectDisplay) {
														int subjsize2 = cp.getTotalSub();
														if (subjsize2 != 0) {
															wt.until(ExpectedConditions.elementToBeClickable(
																	cp.getSubjectLinks().get(subj)));
															boolean clickSubjectResult2 = clickElement(
																	cp.getSubjectLinks(), subj, "SubjectLinks");
															if (clickSubjectResult2) {
																System.out.println(
																		"User not able to navigated on chapter from Subjects "
																				+ subject);

																test.log(LogStatus.INFO,
																		"User not able to navigated on chapter from Subjects "
																				+ subject);
																chPg.takeScreenShot();
															} else {
																System.out.println(
																		"User not able to navigated on chapter from Subjects "
																				+ subject);

																test.log(LogStatus.INFO,
																		"User not able to navigated on chapter from Subjects "
																				+ subject);
																chPg.takeScreenShot();
															}
														}
													} else {
														System.out.println(
																"Back Button is not working or timeLimit Exceeds, User not able to navigated on Subjects from Test/Practice/Test Tab  "
																		+ subject);
														test.log(LogStatus.INFO,
																"Back Button is not working or timeLimit Exceeds, User not able to navigated on Subjects from Test/Practice/Test Tab  "
																		+ subject);
														sAssert.fail(
																"Back Button is not working or timeLimit Exceeds, User not able to navigated on Subjects from Test/Practice/Test Tab  "
																		+ subject);
														chPg.takeScreenShot();
														dp.openstudytab();
														Thread.sleep(5000);
														clickElement(cp.getSubjectLinks(), subj, "SubjectLinks");
														Thread.sleep(5000);
														clickElement(sp.getTotalTermLinks(), term,"Term Subject link");
														Thread.sleep(5000);
													}
												} else {
													driver.navigate().back();
												}
											}
											// if clickElement on chapter Fail
										} else {

											actualResult = "Not_able_to_clickElement_on_chapter_Fail " + subject + ">>"
													+ chapter;
											System.out.println("Not_able_to_clickElement_on_Chapter, Location is --> "
													+ subject + ">>" + chapter);
											test.log(LogStatus.FAIL,
													"Not_able_to_clickElement_on_Chapter, Location is --> " + subject
															+ ">>" + chapter);
											sAssert.fail("Not_able_to_clickElement_on_Chapter, Location is --> "
													+ subject + ">>" + chapter);
											Thread.sleep(5000);
											dp.openstudytab();
											Thread.sleep(5000);
											clickElement(cp.getSubjectLinks(), subj, "SubjectLinks");
											Thread.sleep(5000);
											clickElement(sp.getTotalTermLinks(), term,"Term Subject link");
											Thread.sleep(5000);
										
											clickElement(tp.getMainChapter(), ch,"Chapter link");
											Thread.sleep(5000);
											continue;
											
										}

									}

								} else {
									actualResult = "Chapter page is not Displayed_FAIL";
									System.out.println("Chapter page is not Displayed, Location is --> " + subject);
									test.log(LogStatus.FAIL,
											"Chapter page is not Displayed, Location is --> " + subject);
									sAssert.fail("Chapter page is not Displayed, Location is --> " + subject);
									Thread.sleep(5000);

								}

							}

						} else {
							actualResult = "Term page is not Displayed_FAIL";
							System.out.println(
									"Term page is not Displayed_FAIL, Location is --> " + subject + ">>" + termSubject);
							test.log(LogStatus.FAIL,
									"Term page is not Displayed_FAIL, Location is --> " + subject + ">>" + termSubject);
							sAssert.fail(
									"Term page is not Displayed_FAIL, Location is --> " + subject + ">>" + termSubject);
							Thread.sleep(5000);
						}

						if (sp.BackToChapter.size() != 0) {
							JavascriptExecutor jschPgt = (JavascriptExecutor) driver;
							jschPgt.executeScript("arguments[0].click();", sp.BackToChapter.get(0));
						} else {
							driver.navigate().back();
						}
						Thread.sleep(5000);
					}
// end of Term Subject		

				} else {
// Sub Subject in case Term Subject is not Present

					int subSubjsize = sp.getTotalSubSubj();
					if (subSubjsize != 0) {
						String subSubject = "";
						for (int subSubj = 0; subSubj < subSubjsize; subSubj++) {
							try {
								wt.until(ExpectedConditions.visibilityOfAllElements(sp.getSubSubjectLinks()));
								String subSubjects = sp.getSubSubjectLinks().get(subSubj).getText();
								String[] subSubject2 = subSubjects.split("\\n");
								subSubject = subSubject2[0].trim();
								System.out.println("Sub Subjects:" + subSubject);
								test.log(LogStatus.INFO, "Sub Subjects:" + subSubject);
							} catch (Exception e) {
								System.out.println("Sub Subject not found " + subject);
								test.log(LogStatus.INFO, "Sub Subject not found " + subject);
								dp.openstudytab();
								Thread.sleep(5000);
								clickElement(cp.getSubjectLinks(), subj, "SubjectLinks");
								Thread.sleep(5000);
								continue;
							}
							boolean clickElementSubSubjectResult = clickElement(sp.getSubSubjectLinks(), subSubj,
									"SubSubjectLinks");
							if (clickElementSubSubjectResult) {
								Sub_SubjectPage ssp = new Sub_SubjectPage(driver, test);
								Thread.sleep(2000);
								sp.takeScreenShot();
//Chapter
								int chapSize = ssp.getMainChapter().size();
								System.out.println("No. of chapters in this subject = " + chapSize);
								test.log(LogStatus.INFO, "No. of chapters in this subject = " + chapSize);
								Thread.sleep(1000);
								fluentWaitIsDisplay(ssp.getMainChapter(), 0, 30, "sp.getMainChapter");
								if (chapSize != 0) {
									for (int ch = 0; ch < chapSize; ch++) {
										ChapterPage chPg = new ChapterPage(driver, test);
										int chapNo = ch + 1;
										wt.until(ExpectedConditions.visibilityOfAllElements(ssp.getMainChapter()));
										Thread.sleep(5000);
										String chapter = ssp.getMainChapter().get(ch).getText();
										System.out.println("Main Chapter :  " + chapNo + "  " + chapter);
										test.log(LogStatus.INFO, "Main Chapter :  " + chapNo + "  " + chapter);
										boolean clickElementchapterResult = clickElement(ssp.getMainChapter(), ch,
												"chapter link");
										if (clickElementchapterResult) {
											chPg.takeScreenShot();
											Thread.sleep(5000);
//Sub Chapter
											int subChapSize = sp.getSubChapter().size();
											System.out.println("No.Sub Chapters = " + subChapSize);
											test.log(LogStatus.INFO, "No.Sub Chapters = " + subChapSize);

											if (subChapSize != 0)
											// ..............................................
											{
												for (int su = 0; su < subChapSize; ++su) {
													System.out.println(
															"Chapter : " + sp.getSubChapter().get(su).getText());
													test.log(LogStatus.INFO,
															"Chapter : " + sp.getSubChapter().get(su).getText());
													Thread.sleep(1000);
													clickElement(sp.getSubChapter(), su, "sp.getSubChapter()");
													Thread.sleep(2000);
													chPg.takeScreenShot();
//Post Sub Chapter
													int postSubChap = sp.getPostSubChap().size();
													test.log(LogStatus.INFO, "No. of Sub-Sub Chapter : " + postSubChap);
													System.out.println("No. of Sub-sub Chapters = " + postSubChap);
													if (postSubChap != 0) {
														for (int ps = 0; ps < postSubChap; ps++) {
															System.out.println("Subchapter : "
																	+ sp.getPostSubChap().get(ps).getText());
															Thread.sleep(1000);
															clickElement(sp.getPostSubChap(), ps,
																	"sp.getPostSubChap()");
															test.log(LogStatus.INFO, "Open sub chapters");
															Thread.sleep(2000);
															chPg.takeScreenShot();
//Service Tab Section on post chapter
															try {
																boolean testServiceResult = service
																		.verifyTestService(data, sAssert);
																if (testServiceResult) {
																	test.log(LogStatus.PASS,
																			this.getClass().getSimpleName()
																					+ " Service Pass for  " + subject
																					+ ">>" + subSubject + ">>"
																					+ chapter);
																	System.out.println(this.getClass().getSimpleName()
																			+ " Service Pass for  " + subject + ">>"
																			+ subSubject + ">>" + chapter);
																} else {
																	test.log(LogStatus.FAIL,
																			this.getClass().getSimpleName()
																					+ " Service Fail for  " + subject
																					+ ">>" + subSubject + ">>"
																					+ chapter);
																	System.out.println(this.getClass().getSimpleName()
																			+ " Service Fail for  " + subject + ">>"
																			+ subSubject + ">>" + chapter);
																	sAssert.fail(this.getClass().getSimpleName()
																			+ " Service Fail for  " + subject + ">>"
																			+ subSubject + ">>" + chapter);
																	actualResult = this.getClass().getSimpleName()
																			+ "Service_Fail";
																}
															} catch (Exception e) {
																test.log(LogStatus.INFO,
																		this.getClass().getSimpleName() + " Fail for  "
																				+ subject + ">>" + subSubject + ">>"
																				+ chapter);
																System.out.println(this.getClass().getSimpleName()
																		+ " Fail for  " + subject + ">>" + subSubject
																		+ ">>" + chapter);
																sAssert.fail(this.getClass().getSimpleName()
																		+ " Fail for  " + subject + ">>" + subSubject
																		+ ">>" + chapter);
																chPg.takeScreenShot();
															}
															if (chPg.BackToChapter.size() != 0) {
																JavascriptExecutor jschPgt = (JavascriptExecutor) driver;
																jschPgt.executeScript("arguments[0].click();",
																		chPg.BackToChapter.get(0));
																boolean subSubjectDisplay = fluentWaitIsDisplay(
																		sp.getSubSubjectLinks(), subSubj, 30,
																		"getTotalSubSubj");
																if (subSubjectDisplay) {
																	int subSubjsize2 = sp.getTotalSubSubj();
																	if (subSubjsize2 != 0) {
																		wt.until(
																				ExpectedConditions.elementToBeClickable(
																						sp.getSubSubjectLinks()
																								.get(subSubj)));
																		boolean clickElementSubSubjectResult2 = clickElement(
																				sp.getSubSubjectLinks(), subSubj,
																				"cp.getSubSubjectLinks");
																		if (clickElementSubSubjectResult2) {
																			System.out.println(
																					"User navigated on Sub Subjects from Test/Practice/Test Tab "
																							+ subject + ">>"
																							+ subSubject);

																			test.log(LogStatus.INFO,
																					"User navigated on Sub Subjects from Test/Practice/Test Tab "
																							+ subject + ">>"
																							+ subSubject);
																			chPg.takeScreenShot();
																		} else {
																			System.out.println(
																					"User not able to navigated on chapter from Sub Subjects "
																							+ subject + ">>"
																							+ subSubject);

																			test.log(LogStatus.INFO,
																					"User not able to navigated on chapter from Sub Subjects "
																							+ subject + ">>"
																							+ subSubject);
																			chPg.takeScreenShot();
																		}
																	}
																} else {
																	System.out.println(
																			"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test/Practice/Test Tab  "
																					+ subject + ">>" + subSubject);
																	test.log(LogStatus.INFO,
																			"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test/Practice/Test Tab  "
																					+ subject + ">>" + subSubject);
																	sAssert.fail(
																			"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test/Practice/Test Tab  "
																					+ subject + ">>" + subSubject);
																	chPg.takeScreenShot();
																	dp.openstudytab();
																	Thread.sleep(5000);
																	clickElement(cp.getSubjectLinks(), subj,
																			"SubjectLinks");
																	Thread.sleep(5000);
																	clickElement(sp.getSubSubjectLinks(), subSubj,
																			"SubSubjectLinks");
																	Thread.sleep(5000);
																}
															} else {
																driver.navigate().back();
															}
															Thread.sleep(5000);
															clickElement(sp.getMainChapter(), ch,
																	"sp.getMainChapter()");
															Thread.sleep(5000);
															clickElement(sp.getSubChapter(), su, "sp.getSubChapter()");
															Thread.sleep(5000);
														}

													} else {
//Service Tab Section on sub chapter												
														try {
															boolean testServiceResult = service.verifyTestService(data,
																	sAssert);
															if (testServiceResult) {
																test.log(LogStatus.PASS,
																		this.getClass().getSimpleName()
																				+ " Service Pass for  " + subject + ">>"
																				+ subSubject + ">>" + chapter);
																System.out.println(this.getClass().getSimpleName()
																		+ " Service Pass for  " + subject + ">>"
																		+ subSubject + ">>" + chapter);
															} else {
																test.log(LogStatus.FAIL,
																		this.getClass().getSimpleName()
																				+ " Service Fail for  " + subject + ">>"
																				+ subSubject + ">>" + chapter);
																System.out.println(this.getClass().getSimpleName()
																		+ " Service Fail for  " + subject + ">>"
																		+ subSubject + ">>" + chapter);
																sAssert.fail(this.getClass().getSimpleName()
																		+ " Service Fail for  " + subject + ">>"
																		+ subSubject + ">>" + chapter);
																actualResult = this.getClass().getSimpleName()
																		+ "Service_Fail";
															}
														} catch (Exception e) {
															test.log(LogStatus.INFO,
																	this.getClass().getSimpleName() + " Fail for  "
																			+ subject + ">>" + subSubject + ">>"
																			+ chapter);
															System.out.println(this.getClass().getSimpleName()
																	+ " Fail for  " + subject + ">>" + subSubject + ">>"
																	+ chapter);
															sAssert.fail(this.getClass().getSimpleName() + " Fail for  "
																	+ subject + ">>" + subSubject + ">>" + chapter);
															chPg.takeScreenShot();
														}
														if (chPg.BackToChapter.size() != 0) {
															JavascriptExecutor jschPgt = (JavascriptExecutor) driver;
															jschPgt.executeScript("arguments[0].click();",
																	chPg.BackToChapter.get(0));
															boolean subSubjectDisplay = fluentWaitIsDisplay(
																	sp.getSubSubjectLinks(), subSubj, 30,
																	"getTotalSubSubj");
															if (subSubjectDisplay) {
																int subSubjsize2 = sp.getTotalSubSubj();
																if (subSubjsize2 != 0) {
																	wt.until(ExpectedConditions.elementToBeClickable(
																			sp.getSubSubjectLinks().get(subSubj)));
																	boolean clickElementSubSubjectResult2 = clickElement(
																			sp.getSubSubjectLinks(), subSubj,
																			"cp.getSubSubjectLinks");
																	if (clickElementSubSubjectResult2) {
																		System.out.println(
																				"User navigated on Sub Subjects from Test/Practice/Test Tab "
																						+ subject + ">>" + subSubject);

																		test.log(LogStatus.INFO,
																				"User navigated on Sub Subjects from Test/Practice/Test Tab "
																						+ subject + ">>" + subSubject);
																		chPg.takeScreenShot();
																	} else {
																		System.out.println(
																				"User not able to navigated on chapter from Sub Subjects "
																						+ subject + ">>" + subSubject);

																		test.log(LogStatus.INFO,
																				"User not able to navigated on chapter from Sub Subjects "
																						+ subject + ">>" + subSubject);
																		chPg.takeScreenShot();
																	}
																}
															} else {
																System.out.println(
																		"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test/Practice/Test Tab  "
																				+ subject + ">>" + subSubject);
																test.log(LogStatus.INFO,
																		"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test/Practice/Test Tab  "
																				+ subject + ">>" + subSubject);
																sAssert.fail(
																		"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test/Practice/Test Tab  "
																				+ subject + ">>" + subSubject);
																chPg.takeScreenShot();
																dp.openstudytab();
																Thread.sleep(5000);
																clickElement(cp.getSubjectLinks(), subj,
																		"SubjectLinks");
																Thread.sleep(5000);
																clickElement(sp.getSubSubjectLinks(), subSubj,
																		"SubSubjectLinks");
																Thread.sleep(5000);
															}
														} else {
															driver.navigate().back();
														}

														Thread.sleep(5000);
														clickElement(sp.getMainChapter(), ch, "sp.getMainChapter()");
														Thread.sleep(5000);
													}

												}

											}
//Sub Chap Else Start ...............................................................................................................................................

											else {
//Service Tab Section on chapter												
												try {
													boolean testServiceResult = service.verifyTestService(data,
															sAssert);
													if (testServiceResult) {
														test.log(LogStatus.PASS,
																this.getClass().getSimpleName() + " Service Pass for  "
																		+ subject + ">>" + subSubject + ">>" + chapter);
														System.out.println(
																this.getClass().getSimpleName() + " Service Pass for  "
																		+ subject + ">>" + subSubject + ">>" + chapter);
													} else {
														test.log(LogStatus.FAIL,
																this.getClass().getSimpleName() + " Service Fail for  "
																		+ subject + ">>" + subSubject + ">>" + chapter);
														System.out.println(
																this.getClass().getSimpleName() + " Service Fail for  "
																		+ subject + ">>" + subSubject + ">>" + chapter);
														sAssert.fail(
																this.getClass().getSimpleName() + " Service Fail for  "
																		+ subject + ">>" + subSubject + ">>" + chapter);
														actualResult = this.getClass().getSimpleName() + "Service_Fail";
													}
												} catch (Exception e) {
													test.log(LogStatus.INFO,
															this.getClass().getSimpleName() + " Fail for  " + subject
																	+ ">>" + subSubject + ">>" + chapter);
													System.out.println(this.getClass().getSimpleName() + " Fail for  "
															+ subject + ">>" + subSubject + ">>" + chapter);
													sAssert.fail(this.getClass().getSimpleName() + " Fail for  "
															+ subject + ">>" + subSubject + ">>" + chapter);
													chPg.takeScreenShot();
												}
												if (chPg.BackToChapter.size() != 0) {
													JavascriptExecutor jschPgt = (JavascriptExecutor) driver;
													jschPgt.executeScript("arguments[0].click();",
															chPg.BackToChapter.get(0));
													boolean subSubjectDisplay = fluentWaitIsDisplay(
															sp.getSubSubjectLinks(), subSubj, 30, "getTotalSubSubj");
													if (subSubjectDisplay) {
														int subSubjsize2 = sp.getTotalSubSubj();
														if (subSubjsize2 != 0) {
															wt.until(ExpectedConditions.elementToBeClickable(
																	sp.getSubSubjectLinks().get(subSubj)));
															boolean clickElementSubSubjectResult2 = clickElement(
																	sp.getSubSubjectLinks(), subSubj,
																	"cp.getSubSubjectLinks");
															if (clickElementSubSubjectResult2) {
																System.out.println(
																		"User navigated on Sub Subjects from Test/Practice/Test Tab "
																				+ subject + ">>" + subSubject);

																test.log(LogStatus.INFO,
																		"User navigated on Sub Subjects from Test/Practice/Test Tab "
																				+ subject + ">>" + subSubject);
																chPg.takeScreenShot();
															} else {
																System.out.println(
																		"User not able to navigated on chapter from Sub Subjects "
																				+ subject + ">>" + subSubject);

																test.log(LogStatus.INFO,
																		"User not able to navigated on chapter from Sub Subjects "
																				+ subject + ">>" + subSubject);
																chPg.takeScreenShot();
															}
														}
													} else {
														System.out.println(
																"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test/Practice/Test Tab  "
																		+ subject + ">>" + subSubject);
														test.log(LogStatus.INFO,
																"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test/Practice/Test Tab  "
																		+ subject + ">>" + subSubject);
														sAssert.fail(
																"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test/Practice/Test Tab  "
																		+ subject + ">>" + subSubject);
														chPg.takeScreenShot();
														dp.openstudytab();
														Thread.sleep(5000);
														clickElement(cp.getSubjectLinks(), subj, "SubjectLinks");
														Thread.sleep(5000);
														clickElement(sp.getSubSubjectLinks(), subSubj,
																"SubSubjectLinks");
														Thread.sleep(5000);
													}
												} else {
													driver.navigate().back();
												}
											}
										} else {
											actualResult = "Test_Not_able_to_clickElement_on_chapter_Fail " + subject
													+ ">>" + subSubject + ">>" + chapter;
											System.out.println(
													"Test_Not_able_to_clickElement_on_Chapter, Location is --> "
															+ subject + ">>" + subSubject + ">>" + chapter);
											test.log(LogStatus.FAIL,
													"Test_Not_able_to_clickElement_on_Chapter, Location is --> "
															+ subject + ">>" + subSubject + ">>" + chapter);
											sAssert.fail("Test_Not_able_to_clickElement_on_Chapter, Location is --> "
													+ subject + ">>" + subSubject + ">>" + chapter);
											Thread.sleep(5000);
											dp.openstudytab();
											Thread.sleep(5000);
											clickElement(cp.getSubjectLinks(), subj, "SubjectLinks");
											Thread.sleep(5000);
										
											clickElement(sp.getSubSubjectLinks(), subSubj,"Sub Subject link");
											Thread.sleep(5000);
											clickElement(ssp.getMainChapter(), ch,"Chapter link");
											Thread.sleep(5000);
											continue;
											
										}
									}

								} else {
									actualResult = "Test_chapter_page_not_display_FAIL";
									System.out.println("Chapter page is not Displayed, Location is --> " + subject
											+ " -- >" + subSubject);
									test.log(LogStatus.FAIL, "Chapter page is not Displayed, Location is --> " + subject
											+ " -- >" + subSubject);
									sAssert.fail("Chapter page is not Displayed, Location is --> " + subject + " -- >"
											+ subSubject);
									sp.takeScreenShot();

								}
								if (sp.BackToChapter.size() != 0) {
									JavascriptExecutor jschPgt = (JavascriptExecutor) driver;
									jschPgt.executeScript("arguments[0].click();", sp.BackToChapter.get(0));
								} else {
									driver.navigate().back();
								}
								Thread.sleep(5000);

//if click on Sub subject Fail								
							} else {
								actualResult = "Test_Not_able_to_clickElement_on_Sub_subject_Fail " + subject + ">>"
										+ subSubject;
								System.out.println("Test_Not_able_to_clickElement_on_subject, Location is --> "
										+ subject + ">>" + subSubject);
								test.log(LogStatus.FAIL, "Test_Not_able_to_clickElement_on_subject, Location is --> "
										+ subject + ">>" + subSubject);
								sAssert.fail("Test_Not_able_to_clickElement_on_subject, Location is --> " + subject
										+ ">>" + subSubject);
								Thread.sleep(5000);
							}
						}

//Sub Subject end
//chapter start --in case sub subject not present on page
					} else {
						int chapSize = sp.getMainChapter().size();
						System.out.println("No. of chapters in this subject = " + chapSize);
						test.log(LogStatus.INFO, "No. of chapters in this subject = " + chapSize);
						Thread.sleep(1000);
						if (chapSize != 0) {
							for (int ch = 0; ch < chapSize; ch++) {
								ChapterPage chPg = new ChapterPage(driver, test);
								int chapNo = ch + 1;
								wt.until(ExpectedConditions.visibilityOfAllElements(sp.getMainChapter()));
								Thread.sleep(5000);
								String chapter = sp.getMainChapter().get(ch).getText();
								System.out.println("Main Chapter :  " + chapNo + "  " + chapter);
								test.log(LogStatus.INFO, "Main Chapter :  " + chapNo + "  " + chapter);
//clickElementing on chapter
								boolean clickElementchapterResult = clickElement(sp.getMainChapter(), ch,
										"chapter link");
								if (clickElementchapterResult) {
									chPg.takeScreenShot();
									Thread.sleep(5000);
//sub Chapter
									int subChapSize = sp.getSubChapter().size();
									System.out.println("No.Sub Chapters = " + subChapSize);
									test.log(LogStatus.INFO, "No.Sub Chapters = " + subChapSize);
									if (subChapSize != 0)
									// ..............................................
									{
										for (int su = 0; su < subChapSize; ++su) {
											System.out.println("Chapter : " + sp.getSubChapter().get(su).getText());
											test.log(LogStatus.INFO,
													"Chapter : " + sp.getSubChapter().get(su).getText());
											Thread.sleep(1000);
											clickElement(sp.getSubChapter(), su, "sp.getSubChapter()");
											Thread.sleep(5000);
											chPg.takeScreenShot();
//Post sub Chapter
											int postSubChap = sp.getPostSubChap().size();
											test.log(LogStatus.INFO, "No. of Sub-Sub Chapter : " + postSubChap);
											System.out.println("No. of Sub-sub Chapters = " + postSubChap);
											if (postSubChap != 0) {
												for (int ps = 0; ps < postSubChap; ps++) {
													System.out.println(
															"Subchapter : " + sp.getPostSubChap().get(ps).getText());
													Thread.sleep(1000);
													clickElement(sp.getPostSubChap(), ps, "sp.getPostSubChap()");
													test.log(LogStatus.INFO, "Open sub chapters");

													Thread.sleep(5000);
													chPg.takeScreenShot();
//Service Tab Section on post sub chapter											
													try {
														boolean testServiceResult = service.verifyTestService(data,
																sAssert);
														if (testServiceResult) {
															test.log(LogStatus.PASS, this.getClass().getSimpleName()
																	+ " Service Pass for  " + subject + ">>" + chapter);
															System.out.println(this.getClass().getSimpleName()
																	+ " Service Pass for  " + subject + ">>" + chapter);
														} else {
															test.log(LogStatus.FAIL, this.getClass().getSimpleName()
																	+ " Fail for  " + subject + ">>" + chapter);
															System.out.println(this.getClass().getSimpleName()
																	+ " Fail for  " + subject + ">>" + chapter);
															sAssert.fail(this.getClass().getSimpleName() + " Fail for  "
																	+ subject + ">>" + chapter);
															actualResult = this.getClass().getSimpleName()
																	+ "Service_Fail";
														}
													} catch (Exception e) {
														test.log(LogStatus.INFO, this.getClass().getSimpleName()
																+ " Fail for  " + subject + ">>" + chapter);
														System.out.println(this.getClass().getSimpleName()
																+ " Fail for  " + subject + ">>" + chapter);
														sAssert.fail(this.getClass().getSimpleName() + " Fail for  "
																+ subject + ">>" + chapter);
														chPg.takeScreenShot();
													}
													if (chPg.BackToChapter.size() != 0) {
														JavascriptExecutor jschPgt = (JavascriptExecutor) driver;
														jschPgt.executeScript("arguments[0].click();",
																chPg.BackToChapter.get(0));
														boolean subjectDisplay = fluentWaitIsDisplay(
																cp.getSubjectLinks(), subj, 30, "getTotalSubSubj");
														if (subjectDisplay) {
															int subjsize2 = cp.getTotalSub();
															if (subjsize2 != 0) {
																wt.until(ExpectedConditions.elementToBeClickable(
																		cp.getSubjectLinks().get(subj)));
																boolean clickSubjectResult2 = clickElement(
																		cp.getSubjectLinks(), subj,
																		"cp.getSubSubjectLinks");
																if (clickSubjectResult2) {
																	System.out.println(
																			"User not able to navigated on chapter from Sub Subjects "
																					+ subject);

																	test.log(LogStatus.INFO,
																			"User not able to navigated on chapter from Sub Subjects "
																					+ subject);
																	chPg.takeScreenShot();
																} else {
																	System.out.println(
																			"User not able to navigated on chapter from Sub Subjects "
																					+ subject);

																	test.log(LogStatus.INFO,
																			"User not able to navigated on chapter from Sub Subjects "
																					+ subject);
																	chPg.takeScreenShot();
																}
															}
														} else {
															System.out.println(
																	"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test/Practice/Test Tab  "
																			+ subject);
															test.log(LogStatus.INFO,
																	"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test/Practice/Test Tab  "
																			+ subject);
															sAssert.fail(
																	"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test/Practice/Test Tab  "
																			+ subject);
															chPg.takeScreenShot();
															dp.openstudytab();
															Thread.sleep(5000);
															clickElement(cp.getSubjectLinks(), subj, "SubjectLinks");
															Thread.sleep(5000);
														}
													} else {
														driver.navigate().back();
													}
													Thread.sleep(5000);
													clickElement(sp.getMainChapter(), ch, "sp.getMainChapter()");

													Thread.sleep(5000);
													clickElement(sp.getSubChapter(), su, "sp.getSubChapter()");
													Thread.sleep(5000);
												}

											} else {
//Service Tab Section on sub chapter												
												try {
													boolean testServiceResult = service.verifyTestService(data,
															sAssert);
													if (testServiceResult) {
														test.log(LogStatus.PASS, this.getClass().getSimpleName()
																+ " Service Pass for  " + subject + ">>" + chapter);
														System.out.println(this.getClass().getSimpleName()
																+ " Service Pass for  " + subject + ">>" + chapter);
													} else {
														test.log(LogStatus.FAIL, this.getClass().getSimpleName()
																+ " Fail for  " + subject + ">>" + chapter);
														System.out.println(this.getClass().getSimpleName()
																+ " Fail for  " + subject + ">>" + chapter);
														sAssert.fail(this.getClass().getSimpleName() + " Fail for  "
																+ subject + ">>" + chapter);
														actualResult = this.getClass().getSimpleName() + "Service_Fail";
													}
												} catch (Exception e) {
													test.log(LogStatus.INFO, this.getClass().getSimpleName()
															+ " Fail for  " + subject + ">>" + chapter);
													System.out.println(this.getClass().getSimpleName() + " Fail for  "
															+ subject + ">>" + chapter);
													sAssert.fail(this.getClass().getSimpleName() + " Fail for  "
															+ subject + ">>" + chapter);
													chPg.takeScreenShot();
												}
												if (chPg.BackToChapter.size() != 0) {
													JavascriptExecutor jschPgt = (JavascriptExecutor) driver;
													jschPgt.executeScript("arguments[0].click();",
															chPg.BackToChapter.get(0));
													boolean subjectDisplay = fluentWaitIsDisplay(cp.getSubjectLinks(),
															subj, 30, "getTotalSubSubj");
													if (subjectDisplay) {
														int subSubjsize2 = cp.getTotalSub();
														if (subSubjsize2 != 0) {
															wt.until(ExpectedConditions.elementToBeClickable(
																	cp.getSubjectLinks().get(subj)));
															boolean clickSubjectResult2 = clickElement(
																	cp.getSubjectLinks(), subj,
																	"cp.getSubSubjectLinks");
															if (clickSubjectResult2) {
																System.out.println(
																		"User not able to navigated on chapter from Sub Subjects "
																				+ subject);

																test.log(LogStatus.INFO,
																		"User not able to navigated on chapter from Sub Subjects "
																				+ subject);
																chPg.takeScreenShot();
															} else {
																System.out.println(
																		"User not able to navigated on chapter from Sub Subjects "
																				+ subject);

																test.log(LogStatus.INFO,
																		"User not able to navigated on chapter from Sub Subjects "
																				+ subject);
																chPg.takeScreenShot();
															}
														}
													} else {
														System.out.println(
																"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test/Practice/Test Tab  "
																		+ subject);
														test.log(LogStatus.INFO,
																"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test/Practice/Test Tab  "
																		+ subject);
														sAssert.fail(
																"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test/Practice/Test Tab  "
																		+ subject);
														chPg.takeScreenShot();
														dp.openstudytab();
														Thread.sleep(5000);
														clickElement(cp.getSubjectLinks(), subj, "SubjectLinks");
														Thread.sleep(5000);
													}
												} else {
													driver.navigate().back();
												}
												Thread.sleep(5000);
												clickElement(sp.getMainChapter(), ch, "sp.getMainChapter()");
											}

										}

									}
//........Sub Chapter End

									else {
//Service Tab Section on chapter										
										try {
											boolean testServiceResult = service.verifyTestService(data, sAssert);
											if (testServiceResult) {
												test.log(LogStatus.PASS, this.getClass().getSimpleName()
														+ " Service Pass for  " + subject + ">>" + chapter);
												System.out.println(this.getClass().getSimpleName()
														+ " Service Pass for  " + subject + ">>" + chapter);
											} else {
												test.log(LogStatus.FAIL, this.getClass().getSimpleName() + " Fail for  "
														+ subject + ">>" + chapter);
												System.out.println(this.getClass().getSimpleName() + " Fail for  "
														+ subject + ">>" + chapter);
												sAssert.fail(this.getClass().getSimpleName() + " Fail for  " + subject
														+ ">>" + chapter);
												actualResult = this.getClass().getSimpleName() + "Service_Fail";
											}
										} catch (Exception e) {
											test.log(LogStatus.INFO, this.getClass().getSimpleName() + " Fail for  "
													+ subject + ">>" + chapter);
											System.out.println(this.getClass().getSimpleName() + " Fail for  " + subject
													+ ">>" + chapter);
											sAssert.fail(this.getClass().getSimpleName() + " Fail for  " + subject
													+ ">>" + chapter);
											chPg.takeScreenShot();
										}
										if (chPg.BackToChapter.size() != 0) {
											JavascriptExecutor jschPgt = (JavascriptExecutor) driver;
											jschPgt.executeScript("arguments[0].click();", chPg.BackToChapter.get(0));
											boolean subjectDisplay = fluentWaitIsDisplay(cp.getSubjectLinks(), subj, 30,
													"TotalSubj");
											if (subjectDisplay) {
												int subjsize2 = cp.getTotalSub();
												if (subjsize2 != 0) {
													wt.until(ExpectedConditions
															.elementToBeClickable(cp.getSubjectLinks().get(subj)));
													boolean clickSubjectResult2 = clickElement(cp.getSubjectLinks(),
															subj, "SubjectLinks");
													if (clickSubjectResult2) {
														System.out.println(
																"User not able to navigated on chapter from Subjects "
																		+ subject);

														test.log(LogStatus.INFO,
																"User not able to navigated on chapter from Subjects "
																		+ subject);
														chPg.takeScreenShot();
													} else {
														System.out.println(
																"User not able to navigated on chapter from Subjects "
																		+ subject);

														test.log(LogStatus.INFO,
																"User not able to navigated on chapter from Subjects "
																		+ subject);
														chPg.takeScreenShot();
													}
												}
											} else {
												System.out.println(
														"Back Button is not working or timeLimit Exceeds, User not able to navigated on Subjects from Test/Practice/Test Tab  "
																+ subject);
												test.log(LogStatus.INFO,
														"Back Button is not working or timeLimit Exceeds, User not able to navigated on Subjects from Test/Practice/Test Tab  "
																+ subject);
												sAssert.fail(
														"Back Button is not working or timeLimit Exceeds, User not able to navigated on Subjects from Test/Practice/Test Tab  "
																+ subject);
												chPg.takeScreenShot();
												dp.openstudytab();
												Thread.sleep(5000);
												clickElement(cp.getSubjectLinks(), subj, "SubjectLinks");
												Thread.sleep(5000);
											}
										} else {
											driver.navigate().back();
										}
									}
//if clickElement on chapter Fail	
								} else {

									actualResult = "Not_able_to_clickElement_on_chapter_Fail " + subject + ">>"
											+ chapter;
									System.out.println("Not_able_to_clickElement_on_Chapter, Location is --> " + subject
											+ ">>" + chapter);
									test.log(LogStatus.FAIL, "Not_able_to_clickElement_on_Chapter, Location is --> "
											+ subject + ">>" + chapter);
									sAssert.fail("Not_able_to_clickElement_on_Chapter, Location is --> " + subject
											+ ">>" + chapter);
									Thread.sleep(5000);
									dp.openstudytab();
									Thread.sleep(5000);
									clickElement(cp.getSubjectLinks(), subj, "SubjectLinks");
									clickElement(sp.getMainChapter(), ch,"Chapter link");
									Thread.sleep(5000);
									continue;
									
									
								}

							}

						} else {
							actualResult = "Chapter page is not Displayed_FAIL";
							System.out.println("Chapter page is not Displayed, Location is --> " + subject);
							test.log(LogStatus.FAIL, "Chapter page is not Displayed, Location is --> " + subject);
							sAssert.fail("Chapter page is not Displayed, Location is --> " + subject);
							Thread.sleep(5000);

						}
					}
				}

				if (sp.BackToChapter.size() != 0) {
					JavascriptExecutor jschPgt = (JavascriptExecutor) driver;
					jschPgt.executeScript("arguments[0].click();", sp.BackToChapter.get(0));
				} else {
					driver.navigate().back();
				}
				Thread.sleep(5000);
			}

// if clickElement on Subject Fail	
			else {
				actualResult = "Not_able_to_clickElement_on_subject_Fail " + subject;
				System.out.println("Not_able_to_clickElement_on_subject, Location is --> " + subject);
				test.log(LogStatus.FAIL, "Not_able_to_clickElement_on_subject, Location is --> " + subject);
				sAssert.fail("Not_able_to_clickElement_on_subject --> " + subject);
				Thread.sleep(5000);
			}

		}

		cp.takeScreenShot();
		driver.navigate().back();
		cp.takeScreenShot();
		if (actualResult.equalsIgnoreCase(expectedResult)) {
			test.log(LogStatus.PASS, "Test Pass");
		} else {
			test.log(LogStatus.FAIL, "Test Fail");
		}
		sAssert.assertAll();

	}
}
