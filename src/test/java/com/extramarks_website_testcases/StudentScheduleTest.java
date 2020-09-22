package com.extramarks_website_testcases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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

import com.extramarks_website_pages.DashBoardPage;
import com.extramarks_website_pages.LoginPage;
import com.extramarks_website_pages.SchedulePage;
import com.extramarks_website_utils.Constants;
import com.extramarks_website_utils.DataUtil;
import com.extramarks_website_utils.ExtentManager;

import com.extramarks_website_utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;



public class StudentScheduleTest extends BaseTest {

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
		Object[][] data = DataUtil.getData(xls, "StudentScheduleTest");
		return data;
	}

	@Test(dataProvider = "getData", priority = 2, enabled = true, testName="Validating Add Schedule Functionality",description="Verifying Schedule is added Successfully")
	public void verifyAddSchedule(Hashtable<String, String> data,Method method) {
		
		try {
			SoftAssert sAssert = new SoftAssert();
			Boolean loginResult = defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"),
					data.get("Board"), data.get("Class"));
			if (loginResult) {
				LoginPage lp = new LoginPage(driver, test);
				String expectedResult = "AddSchedule_Pass";
				String actualResult = "";
				DashBoardPage dp = new DashBoardPage(driver, test);
				SchedulePage sp = new SchedulePage(driver, test);
				Object resultPage = dp.openSchedule();
				if (resultPage instanceof SchedulePage) {
					test.log(LogStatus.PASS, "SchedulePage opens");
					actualResult = "AddSchedule_Pass";
					System.out.println("SchedulePage opens");
					Reporter.log("SchedulePage opens");
				} else {
					actualResult = "FAIL";
					lp.takeScreenShot();
					test.log(LogStatus.FAIL, "Schedule Page not open");
				}
				sp.clickAddSchedule(data.get("Title"), data.get("Class"), data.get("Subject"), data.get("Chapter"));
				Thread.sleep(5000);
				actualResult = "";
				WebDriverWait wt = new WebDriverWait(driver, 60);
				wt.until(ExpectedConditions.invisibilityOfAllElements(sp.AddLesson));
				lp.takeScreenShot();

				int allSceduleSize = sp.allscheduleList.size();
				System.out.println(allSceduleSize);
				if (allSceduleSize != 0) {
					for (int j = 0; j < 1; j++) {
						if (sp.allscheduleChapter.size() != 0) {
							Actions ac = new Actions(driver);
							ac.moveToElement(sp.allscheduleChapter.get(j)).release().build().perform();
							String chap = sp.allscheduleChapter.get(j).getText().trim();
							String title = sp.allscheduleTitle.get(j).getText().trim();
							String date = sp.allscheduleDate.get(j).getText().trim();
							System.out.println(chap + title + date);
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss");
							Calendar cal = Calendar.getInstance();
							Date d = simpleDateFormat.parse(simpleDateFormat.format(cal.getTime()));
							if ( title.equalsIgnoreCase(data.get("Title"))) {
								actualResult = "AddSchedule_Pass";
							} else {
								actualResult = "AddSchedule_Fail";
							}
						}

					}
				}
				if (!expectedResult.equals(actualResult)) { // take screenshot
					lp.takeScreenShot();
					test.log(LogStatus.FAIL, "Add Schedule Fail " + actualResult);
					sAssert.fail("Got actual result as " + actualResult);
				} else {
					test.log(LogStatus.PASS, "Add Schedule passed");
					Reporter.log("Add Schedule passed");
					System.out.println("Add Schedule passed");
				}
			} else {
				System.out.println("Login Failed");
				test.log(LogStatus.INFO, "Login Failed");
				Reporter.log("Login Failed");
				sAssert.fail("Login Failed");
			}
			sAssert.assertAll();
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Getting Exception :" + e.getMessage());
			
		}
	}

	@Test(dataProvider = "getData", priority = 3, enabled = true,testName="Validating My Schedule ",description="Verifying edit schedule , My Schedule, Schedule filter")
	public void studentMySchedule(Hashtable<String, String> data,Method method) {
		
		try {
			SoftAssert sAssert = new SoftAssert();
			Boolean loginResult = defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"),
					data.get("Board"), data.get("Class"));
			if (loginResult) {
				WebDriverWait wt = new WebDriverWait(driver, 60);
				LoginPage lp = new LoginPage(driver, test);
				String expectedResult = "PASS";
				String actualResult = "";
				DashBoardPage dp = new DashBoardPage(driver, test);
				SchedulePage sp = new SchedulePage(driver, test);

				Object resultPage = dp.openSchedule();
				if (resultPage instanceof SchedulePage) {
					test.log(LogStatus.PASS, "SchedulePage opens");
					actualResult = "PASS";
					System.out.println("SchedulePage opens");
				} else {
					actualResult = "SchedulePage_not_Open";
					lp.takeScreenShot();
					test.log(LogStatus.FAIL, "Schedule Page not open");
				}
			boolean resultPage2 = sp.openMySchedule();
				actualResult = "";
				if (resultPage2) {
					actualResult = "PASS";
				} else {
					actualResult = "FAIL";
				}

				if (!expectedResult.equals(actualResult)) {
					// take screenshot
					lp.takeScreenShot();
					test.log(LogStatus.FAIL, "My Schedule Page Opens " + actualResult);
					sAssert.fail("Got actual result as " + actualResult);
				} else {
					test.log(LogStatus.PASS, "My Schedule Page Opens " + actualResult);
				}

				actualResult = "";
//Edit Schedule
				Thread.sleep(5000);
				boolean editSchedule = sp.editSchedule(data.get("EditSubject"), data.get("EditChapter"));
				if (editSchedule == true) {
					actualResult = "PASS";
				} else {
					if (sp.EditIcon.size() == 0) {
						System.out.println("Not found any Schedule for edit");
						test.log(LogStatus.INFO, "Not found any Schedule for edit");
						actualResult = "PASS";
					}
				}
				if (!expectedResult.equals(actualResult)) {
					// take screenshot
					lp.takeScreenShot();
					test.log(LogStatus.FAIL, "Edit Schedule Fail " + actualResult);
					sAssert.fail("Edit Schedule Fail " + actualResult);
				} else {
					test.log(LogStatus.PASS, "Edit Schedule PASS");
				}

//All Filter
				actualResult = "";
				wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='all']/a")));
				// driver.findElement(By.xpath("//*[@id='all']/a")).click();
				Thread.sleep(5000);
				System.out.println("Applying All Filter");
				test.log(LogStatus.INFO, "Applying All Filter");
				List<HashMap<String, String>> scheduleListAll = new ArrayList<HashMap<String, String>>();
				int ScheduleSize = sp.scheduleList.size();
				try {
					scheduleListAll = sp.myScheduleFilter();
					for (int i = 0; i < ScheduleSize; i++) {
						test.log(LogStatus.INFO, "Scedule Title : " + scheduleListAll.get(i).get("ScheduleHeader"));
						test.log(LogStatus.INFO, "Scedule Subject : " + scheduleListAll.get(i).get("ScheduleSubjects"));
						test.log(LogStatus.INFO, "Scedule Date : " + scheduleListAll.get(i).get("ScheduleEndDate"));
						actualResult = "PASS";
					}
				} catch (Exception e) {
					actualResult = "FAIL";
				}

				if (!expectedResult.equals(actualResult)) {
					// take screenshot
					lp.takeScreenShot();
					test.log(LogStatus.FAIL, "All Schedule Filter Fail " + actualResult);
					sAssert.fail("All Schedule Filter Fail " + actualResult);
				} else {
					test.log(LogStatus.PASS, "All Schedule Filter PASS");
				}

				// Overdue Filter

				actualResult = "";
				wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='overdue']/a")));
				System.out.println("Applying Overdue Filter");
				test.log(LogStatus.INFO, "Applying Overdue Filter");
				sp.Overdue.click();
				Thread.sleep(5000);
				ScheduleSize = sp.scheduleList.size();
				List<HashMap<String, String>> scheduleListOverdue = new ArrayList<HashMap<String, String>>();
				try {
					scheduleListOverdue = sp.myScheduleFilter();
					for (int i = 0; i < ScheduleSize; i++) {
						test.log(LogStatus.INFO, "Scedule Title : " + scheduleListOverdue.get(i).get("ScheduleHeader"));
						test.log(LogStatus.INFO,
								"Scedule Subject : " + scheduleListOverdue.get(i).get("ScheduleSubjects"));
						test.log(LogStatus.INFO, "Scedule Date : " + scheduleListOverdue.get(i).get("ScheduleEndDate"));
						System.out.println("Scedule Title : " + scheduleListOverdue.get(i).get("ScheduleHeader"));
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yy hh:mm:ss");
						Calendar cal = Calendar.getInstance();
						Date currentDate = simpleDateFormat.parse(simpleDateFormat.format(cal.getTime()));
						Date scheduleEndDate = simpleDateFormat
								.parse(scheduleListOverdue.get(i).get("ScheduleEndDate"));
						if (scheduleEndDate.compareTo(currentDate) < 0) {
							actualResult = "PASS";
							System.out.println("End Date is ocuures before Todays Date");
							test.log(LogStatus.INFO, "End Date is ocuures before Todays Date");

						} else {
							System.out.println("End Date is ocuures after Todays Date");
							test.log(LogStatus.INFO, "End Date is ocuures after Todays Date");
							actualResult = "FAIL";
						}

					}
				} catch (Exception e) {
					actualResult = "FAIL";
				}

				if (!expectedResult.equals(actualResult)) {
					// take screenshot
					lp.takeScreenShot();
					test.log(LogStatus.FAIL, "Overdue Schedule Filter Fail " + actualResult);
					sAssert.fail("Overdue Schedule Filter Fail " + actualResult);
				} else {
					test.log(LogStatus.PASS, "Overdue Schedule Filter PASS");
				}
				List<WebElement> Completed = driver.findElements(By.xpath("//span[text()='completed']"));
				int CompletedSchedule = Completed.size();
				System.out.println("Total number of completed tests = " + CompletedSchedule);

				// Suggested Filter
				actualResult = "";
				wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='suggested']/a")));
				sp.Suggested.click();
				Thread.sleep(5000);
				System.out.println("Applying Suggested Filter");
				test.log(LogStatus.INFO, "Applying Suggested Filter");
				ScheduleSize = sp.scheduleList.size();
				List<HashMap<String, String>> scheduleListSuggested = new ArrayList<HashMap<String, String>>();
				try {
					scheduleListSuggested = sp.myScheduleFilter();
					for (int i = 0; i < ScheduleSize; i++) {
						test.log(LogStatus.INFO,
								"Scedule Title : " + scheduleListSuggested.get(i).get("ScheduleHeader"));
						test.log(LogStatus.INFO,
								"Scedule Subject : " + scheduleListSuggested.get(i).get("ScheduleSubjects"));
						test.log(LogStatus.INFO,
								"Scedule Date : " + scheduleListSuggested.get(i).get("ScheduleEndDate"));
						actualResult = "PASS";
					}
				} catch (Exception e) {
					actualResult = "FAIL";
				}

				if (!expectedResult.equals(actualResult)) {
					// take screenshot
					lp.takeScreenShot();
					test.log(LogStatus.FAIL, "Suggested Schedule Filter Fail " + actualResult);
					sAssert.fail("Suggested Schedule Filter Fail " + actualResult);
				} else {
					test.log(LogStatus.PASS, "Suggested Schedule Filter PASS");
				}

				// Recently Added Filter
				actualResult = "";
				wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='recently']/a")));
				sp.RecentlyAdded.click();
				Thread.sleep(5000);
				System.out.println("Applying Recently Added Filter");
				test.log(LogStatus.INFO, "Applying Recently Added Filter");
				ScheduleSize = sp.scheduleList.size();
				List<HashMap<String, String>> scheduleListRecentlyAdded = new ArrayList<HashMap<String, String>>();
				try {
					scheduleListRecentlyAdded = sp.myScheduleFilter();
					for (int i = 0; i < ScheduleSize; i++) {
						test.log(LogStatus.INFO,
								"Scedule Title : " + scheduleListRecentlyAdded.get(i).get("ScheduleHeader"));
						test.log(LogStatus.INFO,
								"Scedule Subject : " + scheduleListRecentlyAdded.get(i).get("ScheduleSubjects"));
						test.log(LogStatus.INFO,
								"Scedule Date : " + scheduleListRecentlyAdded.get(i).get("ScheduleEndDate"));
						actualResult = "PASS";
					}
				} catch (Exception e) {
					actualResult = "FAIL";
				}

				if (!expectedResult.equals(actualResult)) {
					// take screenshot
					lp.takeScreenShot();
					test.log(LogStatus.FAIL, "Recently Added Schedule Filter Fail " + actualResult);
					sAssert.fail("Recently Added Schedule Filter Fail " + actualResult);
				} else {
					test.log(LogStatus.PASS, "Recently Added Schedule Filter PASS");
				}
				sp.ExamSchedule();
				dp.Dashboard.get(0).click();
			} else {
				System.out.println("Login Failed");
				test.log(LogStatus.INFO, "Login Failed");
				Reporter.log("Login Failed");
				sAssert.fail("Login Failed");
			}
			sAssert.assertAll();
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Getting Exception :" + e.getMessage());
			
		}
	}

	@Test(dataProvider = "getData", priority = 4, enabled = true, invocationCount = 1,testName="Validating delete Schedule",description="Verifying user is allowed to delete schedule")
	public void studentDeleteSchedule(Hashtable<String, String> data,Method method) {
		
		try {
			SoftAssert sAssert = new SoftAssert();
			Boolean loginResult = defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"),
					data.get("Board"), data.get("Class"));
			if (loginResult) {
				LoginPage lp = new LoginPage(driver, test);
				String expectedResult = "PASS";
				String actualResult = "";
				DashBoardPage dp = new DashBoardPage(driver, test);
				SchedulePage sp = new SchedulePage(driver, test);

				Object resultPage = dp.openSchedule();
				if (resultPage instanceof SchedulePage) {
					test.log(LogStatus.PASS, "Schedule Page Validated");
					actualResult = "PASS";
					System.out.println("SchedulePage opens");
				} else {
					actualResult = "FAIL";
					lp.takeScreenShot();
					test.log(LogStatus.FAIL, "Schedule Page not open");
				}
				int deleteSize = sp.deleteScheduleBtn.size();
				if (deleteSize != 0) {
					sp.deleteSchedule();
					Thread.sleep(5000);
				} else {

				}
				int deleteSize2 = sp.deleteScheduleBtn.size() + 1;

				if (deleteSize == deleteSize2) {
					actualResult = "PASS";
				} else {
					actualResult = "FAIL";
				}
				if (!expectedResult.equals(actualResult)) {
					// take screenshot
					lp.takeScreenShot();
					test.log(LogStatus.FAIL, "Schedule Delete Fail " + actualResult);
					sAssert.fail("Schedule Delete Fail " + actualResult);
				} else {
					test.log(LogStatus.PASS, "Schedule Delete PASS ");
					lp.takeScreenShot();
				}

				dp.Dashboard.get(0).click();
			} else {
				System.out.println("Login Failed");
				test.log(LogStatus.INFO, "Login Failed");
				Reporter.log("Login Failed");
				sAssert.fail("Login Failed");
			}
			sAssert.assertAll();
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Getting Exception :" + e.getMessage());
			
		}
	}
}