package com.extramarks_website_testcases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

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

import com.extramarks_website_pages.DashBoardPage;
import com.extramarks_website_pages.LoginPage;
import com.extramarks_website_pages.ReportPage;
import com.extramarks_website_utils.Constants;
import com.extramarks_website_utils.DataUtil;
import com.extramarks_website_utils.ExtentManager;
import com.extramarks_website_utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;



public class StudentReportTest extends BaseTest {

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
		Object[][] data = DataUtil.getData(xls, "StudentReportTest");
		return data;
	}

	@Test(dataProvider = "getData", priority = 1, invocationCount = 1,testName="Validating Student Report",description="Verifying Student Report")
	public void Report(Hashtable<String, String> data,Method method) {
		try {
			SoftAssert sAssert = new SoftAssert();
			Boolean loginResult = defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"),
					data.get("Board"), data.get("Class"));
			if (loginResult) {
				driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
				driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
				String expectedResult = "Report_PASS";
				String actualResult = "Report_PASS";
				LoginPage lp = new LoginPage(driver, test);
				DashBoardPage dp = new DashBoardPage(driver, test);
				String className = dp.getClassName();
				ReportPage rp = new ReportPage(driver, test);
				Thread.sleep(3000);
				Object resultPage = dp.openReport();
				if (resultPage instanceof ReportPage) {
					test.log(LogStatus.INFO, "Opening Report Page");
					System.out.println("Opening Report Page");
					Reporter.log("Opening Report Page");
					rp = (ReportPage) resultPage;
					HashMap<String, Double> reportData = rp.viewReport(data.get("Subject"), data.get("Chapter"));
					Double sum = reportData.get("Learn") + reportData.get("Practice") + reportData.get("Test");
					assertEquals(reportData.get("Total"), sum, "Verifying Sum of Learn-Practice-Test to Total",
							sAssert);

					if (rp.chapterView.size() != 0) {
						assertEquals(rp.chapterView.get(0).getText().trim(), data.get("Chapter"),
								"Verifying Chapter Present on Report Page", sAssert);
						int learnPresent = rp.Learn.size();
						String totalServiceTime = "";

						SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
						timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
						int Class = 0;
						if (className == "Nursery" || className == "KG") {
							Class = 0;
						} else {
							Class = DataUtil.romanToDecimal(className);
						}
						if (Class <= 5 && Class >= 0) {
							if (learnPresent != 0) {
								List<HashMap<String, String>> learndata = rp.Learn();
								long Total = 0;
								for (int i = 0; i < learndata.size(); i++) {

									totalServiceTime = learndata.get(i).get("Time");
									Date date = timeFormat.parse(totalServiceTime);
									Total = Total + date.getTime();
								}
								String totalTime = timeFormat.format(new Date(Total));
								String[] Time = totalTime.split(":");
								int timeHrs = Integer.parseInt(Time[0]);
								int timeMin = Integer.parseInt(Time[1]);
								int timeSeconds = Integer.parseInt(Time[2]);
								double totalTimeTakenInMin = timeHrs * 60 + timeMin + (double) timeSeconds / 60;

								double learnScore = ((double) totalTimeTakenInMin / 60) * 50 / 2;
								learnScore = Math.round(learnScore * 10) / 10.00;
								System.out.println(learnScore);
								test.log(LogStatus.INFO, "Learn Score : " + learnScore);
								assertEquals(reportData.get("Learn"), learnScore, "Verifying Learn Score Percentage",
										sAssert);
								lp.takeScreenShot();
							} else {
								test.log(LogStatus.FAIL, "Learn Tab is not Present for --> " + data.get("Subject")
										+ data.get("Chapter"));
								System.out.println("Learn Tab is not Present for --> " + data.get("Subject")
										+ data.get("Chapter"));
								lp.takeScreenShot();
								sAssert.fail("Learn Tab is not Present for --> " + data.get("Subject")
										+ data.get("Chapter"));
								actualResult = "Report_FAIL";
								lp.takeScreenShot();
							}
							int practicePresent = rp.Practice.size();
							if (practicePresent != 0) {
								List<HashMap<String, String>> practicedata = rp.Practice();
								long Total = 0;
								for (int i = 0; i < practicedata.size(); i++) {

									totalServiceTime = practicedata.get(i).get("Time");
									Date date = timeFormat.parse(totalServiceTime);
									Total = Total + date.getTime();
								}
								String totalTime = timeFormat.format(new Date(Total));
								String[] Time = totalTime.split(":");
								int timeHrs = Integer.parseInt(Time[0]);
								int timeMin = Integer.parseInt(Time[1]);
								int timeSeconds = Integer.parseInt(Time[2]);
								double totalTimeTakenInMin = timeHrs * 60 + timeMin + (double) timeSeconds / 60;

								double practiceScore = ((double) totalTimeTakenInMin / 60) * 50 / 2;
								practiceScore = Math.round(practiceScore * 10) / 10.00;
								System.out.println(practiceScore);
								test.log(LogStatus.INFO, "Practice Score : " + practiceScore);
								assertEquals(reportData.get("Practice"), practiceScore,
										"Verifying Practice Score Percentage", sAssert);
								lp.takeScreenShot();
							} else {
								test.log(LogStatus.FAIL, "Practice Tab is not Present for --> " + data.get("Subject")
										+ data.get("Chapter"));
								System.out.println("Practice Tab is not Present for --> " + data.get("Subject")
										+ data.get("Chapter"));
								lp.takeScreenShot();
								sAssert.fail("Practice Tab is not Present for --> " + data.get("Subject")
										+ data.get("Chapter"));
								actualResult = "Report_FAIL";
							}
						} else if (Class <= 12 && Class >= 6) {
							if (learnPresent != 0) {
								List<HashMap<String, String>> learndata = rp.Learn();
								long Total = 0;
								for (int i = 0; i < learndata.size(); i++) {

									totalServiceTime = learndata.get(i).get("Time");
									Date date = timeFormat.parse(totalServiceTime);
									Total = Total + date.getTime();
								}
								String totalTime = timeFormat.format(new Date(Total));
								String[] Time = totalTime.split(":");
								int timeHrs = Integer.parseInt(Time[0]);
								int timeMin = Integer.parseInt(Time[1]);
								int timeSeconds = Integer.parseInt(Time[2]);
								double totalTimeTakenInMin = timeHrs * 60 + timeMin + (double) timeSeconds / 60;

								double learnScore = ((double) totalTimeTakenInMin / 60) * 50 / 4;
								learnScore = Math.round(learnScore * 10) / 10.00;
								System.out.println(learnScore);
								test.log(LogStatus.INFO, "Learn Score : " + learnScore);
								assertEquals(reportData.get("Learn"), learnScore, "Verifying Learn Score Percentage",
										sAssert);
								lp.takeScreenShot();
							} else {
								test.log(LogStatus.FAIL, "Learn Tab is not Present for --> " + data.get("Subject")
										+ data.get("Chapter"));
								System.out.println("Learn Tab is not Present for --> " + data.get("Subject")
										+ data.get("Chapter"));
								lp.takeScreenShot();
								sAssert.fail("Learn Tab is not Present for --> " + data.get("Subject")
										+ data.get("Chapter"));
								actualResult = "Report_FAIL";
								lp.takeScreenShot();
							}
							int practicePresent = rp.Practice.size();
							if (practicePresent != 0) {
								List<HashMap<String, String>> practicedata = rp.Practice();
								long Total = 0;
								for (int i = 0; i < practicedata.size(); i++) {

									totalServiceTime = practicedata.get(i).get("Time");
									Date date = timeFormat.parse(totalServiceTime);
									Total = Total + date.getTime();
								}
								String totalTime = timeFormat.format(new Date(Total));
								String[] Time = totalTime.split(":");
								int timeHrs = Integer.parseInt(Time[0]);
								int timeMin = Integer.parseInt(Time[1]);
								int timeSeconds = Integer.parseInt(Time[2]);
								double totalTimeTakenInMin = timeHrs * 60 + timeMin + (double) timeSeconds / 60;

								double practiceScore = ((double) totalTimeTakenInMin / 60) * 50 / 4;
								practiceScore = Math.round(practiceScore * 10) / 10.00;
								System.out.println(practiceScore);
								test.log(LogStatus.INFO, "Practice Score : " + practiceScore);
								assertEquals(reportData.get("Practice"), practiceScore,
										"Verifying Practice Score Percentage", sAssert);
								lp.takeScreenShot();
							} else {
								test.log(LogStatus.FAIL, "Practice Tab is not Present for --> " + data.get("Subject")
										+ data.get("Chapter"));
								System.out.println("Practice Tab is not Present for --> " + data.get("Subject")
										+ data.get("Chapter"));
								lp.takeScreenShot();
								sAssert.fail("Practice Tab is not Present for --> " + data.get("Subject")
										+ data.get("Chapter"));
								actualResult = "Report_FAIL";
							}
							int testPresent = rp.Test.size();
							if (testPresent != 0) {
								List<HashMap<String, String>> testdata = rp.Test();
								long Total = 0;
								for (int i = 0; i < testdata.size(); i++) {
									totalServiceTime = testdata.get(i).get("Time");
									Date date = timeFormat.parse(totalServiceTime);
									Total = Total + date.getTime();
								}
								String totalTime = timeFormat.format(new Date(Total));
								String[] Time = totalTime.split(":");
								int timeHrs = Integer.parseInt(Time[0]);
								int timeMin = Integer.parseInt(Time[1]);
								int timeSeconds = Integer.parseInt(Time[2]);
								double totalTimeTakenInMin = timeHrs * 60 + timeMin + (double) timeSeconds / 60;

								double testScore = ((double) totalTimeTakenInMin / 60) * 50 / 2;
								testScore = Math.round(testScore * 10) / 10.00;
								System.out.println(testScore);
								test.log(LogStatus.INFO, "Learn Score : " + testScore);
								assertEquals(reportData.get("Test"), testScore, "Verifying Test Score Percentage",
										sAssert);
								lp.takeScreenShot();
							} else {
								test.log(LogStatus.FAIL,
										"Test Tab is not Present for --> " + data.get("Subject") + data.get("Chapter"));
								System.out.println(
										"Test Tab is not Present for --> " + data.get("Subject") + data.get("Chapter"));
								lp.takeScreenShot();
								sAssert.fail(
										"Test Tab is not Present for --> " + data.get("Subject") + data.get("Chapter"));
								actualResult = "Report_FAIL";
							}

						}

					} else {
						test.log(LogStatus.INFO, "Chapter is not Present");
						sAssert.fail("Chapter is not Present");
						actualResult = "Report_FAIL";
					}
					sAssert.assertAll();
					if (actualResult.equalsIgnoreCase(expectedResult)) {
						test.log(LogStatus.PASS, "Report Test PASS");
					} else {
						test.log(LogStatus.FAIL, "Report Test FAIL");
					}

				}
			} else {
				System.out.println("Login Failed");
				test.log(LogStatus.INFO, "Login Failed");
				Reporter.log("Login Failed");
				sAssert.fail("Login Failed");
			}
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Getting Exception :" + e.getMessage());
		
		}
	}
}
