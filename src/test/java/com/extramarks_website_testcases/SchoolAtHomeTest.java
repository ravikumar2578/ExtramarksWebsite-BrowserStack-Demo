package com.extramarks_website_testcases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Hashtable;

import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.extramarks_website_pages.DashBoardPage;
import com.extramarks_website_pages.LiveHomeSession;
import com.extramarks_website_pages.SchoolAtHomePage;
import com.extramarks_website_pages.ViewHomeworkPage;
import com.extramarks_website_pages.ViewWeeklyTestPage;
import com.extramarks_website_pages.ViewWorksheetPage;
import com.extramarks_website_pages.WeeklyTestPage;
import com.extramarks_website_pages.Worksheet_HomeworkPage;
import com.extramarks_website_utils.Constants;
import com.extramarks_website_utils.DataUtil;
import com.extramarks_website_utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class SchoolAtHomeTest extends BaseTest {

	@BeforeMethod(alwaysRun = true)
	public void init(Method method) throws IOException {
		test = rep.startTest(
				method.getAnnotation(Test.class).testName() + " --> " + method.getAnnotation(Test.class).description());
		parentTest.appendChild(test);

		// test = childTest.createNode(method.getName());

	}

	@AfterMethod(alwaysRun = true)
	public void writeResults() throws Exception {
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
	public Object[][] getTeacherData() {
		Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);
		Object[][] data = DataUtil.getData(xls, "TeacherLoginTest");
		return data;
	}

	@DataProvider
	public Object[][] getStudentData() {
		Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);
		Object[][] data = DataUtil.getData(xls, "StudentLoginTest");
		return data;
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(dataProvider = "getTeacherData", invocationCount = 1, priority = 11, testName = "assignHomework", enabled = true)
	public void assignHomework(Hashtable<String, String> data) throws Exception {
		SoftAssert sAssert = new SoftAssert();
		String expectedResult = "Homework is Assigned";
		String actualResult = "";
		String browser = data.get("Browser");
		test.log(LogStatus.INFO, "Login test started");
		Reporter.log("Login test started");
		if (!DataUtil.isTestRunnable(xls, "SchoolAtHomeTest") || data.get("Runmode").equals("N")) {
			test.log(LogStatus.SKIP, "Skipping the test");
			Reporter.log("Skipping the test");
			throw new SkipException("skipping the test");
		}
		Object resultLogin = defaultLogin(data);
		Thread.sleep(3000);
		if (resultLogin instanceof DashBoardPage) {
			DashBoardPage dashboardPage = (DashBoardPage) resultLogin;
			boolean clickResult = dashboardPage.clickSchoolAtHomeLink();
			SchoolAtHomePage schoolAtHomePage = new SchoolAtHomePage(driver, test);
			Worksheet_HomeworkPage worksheet_homeworkPage = new Worksheet_HomeworkPage(driver, test);
			Object resultSchoolAtHome = worksheet_homeworkPage.assignHomework(data);
			if (resultSchoolAtHome instanceof DashBoardPage) {
				actualResult = "Homework is Assigned";
			} else {
				actualResult = "Homework is not Assigned";
			}
			assertEquals(actualResult, expectedResult, "Verifying assign Homework Test Functionality", sAssert);
			sAssert.assertAll();
		} else {
			test.log(LogStatus.FAIL, "Not able to enter URL in address bar Browser");
			sAssert.fail("Not able to enter URL in address bar Browser");
		}

		sAssert.assertAll();

	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(dataProvider = "getStudentData", invocationCount = 1, priority = 12, testName = "Verify Homework", enabled = true)
	public void verifyHomework(Hashtable<String, String> data, Method method) throws Exception {

		Thread.sleep(2000);
		SoftAssert sAssert = new SoftAssert();
		String expectedResult = "Homework is Verified";
		String actualResult = "";
		String browser = data.get("Browser");
		// .log(LogStatus.INFO, "Login test started");
		Reporter.log("Login test started");
		if (!DataUtil.isTestRunnable(xls, "StudentLoginTest") || data.get("Runmode").equals("N")) {
			test.log(LogStatus.SKIP, "Skipping the test");
			Reporter.log("Skipping the test");
			throw new SkipException("skipping the test");
		}
		Object resultLogin = defaultLogin(data);
		Thread.sleep(3000);
		if (resultLogin instanceof DashBoardPage) {
			DashBoardPage dashboardPage = (DashBoardPage) resultLogin;
			boolean clickResult = dashboardPage.clickSchoolAtHomeLink();
			SchoolAtHomePage schoolAtHomePage = new SchoolAtHomePage(driver, test);
			Worksheet_HomeworkPage worksheet_homeworkPage = new Worksheet_HomeworkPage(driver, test);
			Object resultSchoolAtHome = worksheet_homeworkPage.verifyHomework(data);
			if (resultSchoolAtHome instanceof ViewHomeworkPage) {
				actualResult = "Homework is Verified";
			} else if (resultSchoolAtHome instanceof SchoolAtHomePage) {
				actualResult = "Homework is Verified";
			} else {

				actualResult = "Homework is not Verified";
			}
			assertEquals(actualResult, expectedResult, "Verifying assigned Homework Test Functionality", sAssert);
			worksheet_homeworkPage.takeScreenShot();
			sAssert.assertAll();
		} else {
			test.log(LogStatus.FAIL, "Not able to enter URL in address bar Browser");
			sAssert.fail("Not able to enter URL in address bar Browser");
		}

		sAssert.assertAll();

	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(dataProvider = "getTeacherData", invocationCount = 1, priority = 20, testName = "assignWorksheet", enabled = true)
	public void assignWorksheet(Hashtable<String, String> data) throws Exception {
		SoftAssert sAssert = new SoftAssert();
		String expectedResult = "Worksheet is Assigned";
		String actualResult = "";
		String browser = data.get("Browser");
		test.log(LogStatus.INFO, "Login test started");
		Reporter.log("Login test started");
		if (!DataUtil.isTestRunnable(xls, "SchollAtHomeTest") || data.get("Runmode").equals("N")) {
			test.log(LogStatus.SKIP, "Skipping the test");
			Reporter.log("Skipping the test");
			throw new SkipException("skipping the test");
		}
		Object resultLogin = defaultLogin(data);
		Thread.sleep(3000);
		if (resultLogin instanceof DashBoardPage) {
			DashBoardPage dashboardPage = (DashBoardPage) resultLogin;
			boolean clickResult = dashboardPage.clickSchoolAtHomeLink();
			SchoolAtHomePage schoolAtHomePage = new SchoolAtHomePage(driver, test);
			Worksheet_HomeworkPage worksheet_homeworkPage = new Worksheet_HomeworkPage(driver, test);
			Object resultSchoolAtHome = worksheet_homeworkPage.assignWorksheet(data);
			if (resultSchoolAtHome instanceof DashBoardPage) {
				actualResult = "Worksheet is Assigned";
			} else {
				actualResult = "Worksheet is not Assigned";
			}
			assertEquals(actualResult, expectedResult, "Verifying assign Worksheet Test Functionality", sAssert);
			sAssert.assertAll();
		} else {
			test.log(LogStatus.FAIL, "Not able to enter URL in address bar Browser");
			sAssert.fail("Not able to enter URL in address bar Browser");
		}
		sAssert.assertAll();

	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(dataProvider = "getStudentData", invocationCount = 1, priority = 21, testName = "Verify Worksheet", enabled = true)
	public void verifyWorksheet(Hashtable<String, String> data, Method method) throws Exception {

		SoftAssert sAssert = new SoftAssert();
		String expectedResult = "Worksheet is Verified";
		String actualResult = "";
		String browser = data.get("Browser");
		test.log(LogStatus.INFO, "Login test started");
		Reporter.log("Login test started");
		if (!DataUtil.isTestRunnable(xls, "StudentLoginTest") || data.get("Runmode").equals("N")) {
			test.log(LogStatus.SKIP, "Skipping the test");
			Reporter.log("Skipping the test");
			throw new SkipException("skipping the test");
		}
		Object resultLogin = defaultLogin(data);
		Thread.sleep(3000);
		if (resultLogin instanceof DashBoardPage) {
			DashBoardPage dashboardPage = (DashBoardPage) resultLogin;
			boolean clickResult = dashboardPage.clickSchoolAtHomeLink();
			SchoolAtHomePage schoolAtHomePage = new SchoolAtHomePage(driver, test);
			Worksheet_HomeworkPage worksheet_homeworkPage = new Worksheet_HomeworkPage(driver, test);
			Object resultSchoolAtHome = worksheet_homeworkPage.verifyWorksheet(data);
			if (resultSchoolAtHome instanceof ViewWorksheetPage) {
				actualResult = "Worksheet is Verified";
			} else if (resultSchoolAtHome instanceof SchoolAtHomePage) {
				actualResult = "Worksheet is Verified";
			} else {
				actualResult = "Worksheet is not Verified";
			}
			assertEquals(actualResult, expectedResult, "Verifying assigned Worksheet Test Functionality", sAssert);
			sAssert.assertAll();
		} else {
			test.log(LogStatus.FAIL, "Not able to enter URL in address bar Browser");
			sAssert.fail("Not able to enter URL in address bar Browser");
		}

		sAssert.assertAll();

	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(dataProvider = "getTeacherData", invocationCount = 1, priority = 6, testName = "Weekly Test", enabled = false)
	public void weeklyTest(Hashtable<String, String> data, Method method) throws Exception {

		SoftAssert sAssert = new SoftAssert();
		String expectedResult = "Weekly Test is Assigned";
		String actualResult = "";
		String browser = data.get("Browser");
		test.log(LogStatus.INFO, "Login test started");
		Reporter.log("Login test started");
		if (!DataUtil.isTestRunnable(xls, "LoginTest") || data.get("Runmode").equals("N")) {
			test.log(LogStatus.SKIP, "Skipping the test");
			Reporter.log("Skipping the test");
			throw new SkipException("skipping the test");
		}
		Object resultLogin = defaultLogin(data);
		Thread.sleep(3000);
		if (resultLogin instanceof DashBoardPage) {
			DashBoardPage dashboardPage = (DashBoardPage) resultLogin;
			boolean clickResult = dashboardPage.clickSchoolAtHomeLink();
			SchoolAtHomePage schoolAtHomePage = new SchoolAtHomePage(driver, test);
			WeeklyTestPage weeklyTestPage = new WeeklyTestPage(driver, test);
			Object resultSchoolAtHome = weeklyTestPage.weeklyTest(data);
			if (resultSchoolAtHome instanceof DashBoardPage) {
				actualResult = "Weekly Test is Assigned";
			} else {
				actualResult = "Weekly Test is not Assigned";
			}
			assertEquals(actualResult, expectedResult, "Verifying assign Weekly Test Functionality", sAssert);
			sAssert.assertAll();
		} else {
			test.log(LogStatus.FAIL, "Not able to enter URL in address bar Browser");
			sAssert.fail("Not able to enter URL in address bar Browser");
		}
		sAssert.assertAll();

	}

	@Severity(SeverityLevel.BLOCKER)
	public void verifyWeeklyTest(Hashtable<String, String> data, Method method) throws Exception {

		SoftAssert sAssert = new SoftAssert();
		String expectedResult = "Homework is Verified";
		String actualResult = "";
		String browser = data.get("Browser");
		test.log(LogStatus.INFO, "Login test started");
		Reporter.log("Login test started");
		if (!DataUtil.isTestRunnable(xls, "StudentLoginTest") || data.get("Runmode").equals("N")) {
			test.log(LogStatus.SKIP, "Skipping the test");
			Reporter.log("Skipping the test");
			throw new SkipException("skipping the test");
		}
		Object resultLogin = defaultLogin(data);
		Thread.sleep(3000);
		if (resultLogin instanceof DashBoardPage) {
			DashBoardPage dashboardPage = (DashBoardPage) resultLogin;
			boolean clickResult = dashboardPage.clickSchoolAtHomeLink();
			SchoolAtHomePage schoolAtHomePage = new SchoolAtHomePage(driver, test);
			WeeklyTestPage weeklyTestPage = new WeeklyTestPage(driver, test);

			Object resultSchoolAtHome = weeklyTestPage.verifyWeeklyTest(data);
			if (resultSchoolAtHome instanceof ViewWeeklyTestPage) {
				actualResult = "WeeklyTest is Verified";
			} else if (resultSchoolAtHome instanceof SchoolAtHomePage) {
				actualResult = "WeeklyTest is Verified";
			} else {

				actualResult = "WeeklyTest is not Verified";
			}
			assertEquals(actualResult, expectedResult, "Verifying assigned Weekly Test Functionality", sAssert);
			sAssert.assertAll();
		} else {
			test.log(LogStatus.FAIL, "Not able to enter URL in address bar Browser");
			sAssert.fail("Not able to enter URL in address bar Browser");
		}

		sAssert.assertAll();

	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(dataProvider = "getStudentData", invocationCount = 1, priority = 31, testName = "LiveClass", enabled = true)
	public void verifyLiveTest(Hashtable<String, String> data, Method method) throws Exception {

		SoftAssert sAssert = new SoftAssert();
		String expectedResult = "LiveClass is Verified";
		String actualResult = "";
		String browser = data.get("Browser");
		test.log(LogStatus.INFO, "Login test started");
		Reporter.log("Login test started");
		if (!DataUtil.isTestRunnable(xls, "StudentLoginTest") || data.get("Runmode").equals("N")) {
			test.log(LogStatus.SKIP, "Skipping the test");
			Reporter.log("Skipping the test");
			throw new SkipException("skipping the test");
		}
		Object resultLogin = defaultLogin(data);
		Thread.sleep(3000);
		if (resultLogin instanceof DashBoardPage) {
			DashBoardPage dashboardPage = (DashBoardPage) resultLogin;
			boolean clickResult = dashboardPage.clickSchoolAtHomeLink();
			Thread.sleep(3000);
			SchoolAtHomePage schoolAtHomePage = new SchoolAtHomePage(driver, test);
			com.extramarks_website_pages.LiveHomeSession LiveTestPage = new com.extramarks_website_pages.LiveHomeSession(
					driver, test);
			Object resultSchoolAtHome = LiveTestPage.verifyLiveHomeSession(data);
			if (resultSchoolAtHome instanceof LiveHomeSession) {
				actualResult = "LiveClass is Verified";
			} else if (resultSchoolAtHome instanceof SchoolAtHomePage) {
				actualResult = "LiveClass is Verified";

			} else {
				actualResult = "LiveClass is not Verified";
			}
			assertEquals(actualResult, expectedResult, "Verifying assigned LiveClass Test Functionality", sAssert);
			sAssert.assertAll();
		} else {
			test.log(LogStatus.FAIL, "Not able to enter URL in address bar Browser");
			sAssert.fail("Not able to enter URL in address bar Browser");
		}

		sAssert.assertAll();

	}

}
