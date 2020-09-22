package com.extramarks_website_testcases;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.extramarks_website_pages.BasePage;
import com.extramarks_website_pages.DashBoardPage;
import com.extramarks_website_pages.LaunchPage;
import com.extramarks_website_pages.LiveClassPage;
import com.extramarks_website_pages.SchoolAtHomePage;
import com.extramarks_website_pages.ViewHomeworkPage;
import com.extramarks_website_pages.ViewLiveClassPage;
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
import junit.framework.Assert;

@Listeners(AllureListener.class)
public class GoToSchoolTest extends BaseTest {

	@BeforeMethod(alwaysRun = true)
	public void init(Method method) throws Exception {
		System.out.println(this.getClass().getSimpleName());
		test = rep.startTest(
				method.getAnnotation(Test.class).testName() + " --> " + method.getAnnotation(Test.class).description());
		childTest.appendChild(test);
		if (driver == null) {
			driver = openBrowser("Chrome");
			LaunchPage launch = new LaunchPage(driver, test);
			Object resultGetURL = launch.goToHomePage();
		}
		if (!BasePage.isLoggedIn) {
			if (this.getClass().getSimpleName().contains("Parent")) {
				defaultLogin(Constants.PARENT_USERNAME, Constants.PASSWORD);
			}
			if (this.getClass().getSimpleName().contains("Teacher")) {
				defaultLogin(Constants.TEACHER_USERNAME, Constants.PASSWORD);
			} else {
				defaultLogin(Constants.USERNAME, Constants.PASSWORD);
			}
		}

	}

	@AfterMethod(alwaysRun = true)
	public void writeResults() throws Exception {
		rep.endTest(test);
		rep.flush();

	}

	@AfterTest(alwaysRun = true)
	public void quit() {

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

	public static class LiveClass extends GoToSchoolTest {
		public static class CreateSessionByTeacher extends LiveClass {
			@Severity(SeverityLevel.BLOCKER)
			@Test(dataProvider = "getTeacherData", invocationCount = 1, priority = 100, testName = "VerifyNavigationToLiveClass", enabled = true, description = "verify user is Navigated to Live Class Page")
			public void verifyNavigationToCreateSessionPage(Hashtable<String, String> data) throws Exception {
				if (!DataUtil.isTestRunnable(xls, "TeacherLoginTest") || data.get("Runmode").equals("N")) {
					test.log(LogStatus.SKIP, "Skipping the test");
					Reporter.log("Skipping the test");
					throw new SkipException("skipping the test");
				}
				Thread.sleep(3000);
				DashBoardPage dashboardPage = new DashBoardPage(driver, test);
				boolean clickResult = dashboardPage.clickGoToLink();

				if (clickResult) {
					LiveClassPage liveClassObject = new LiveClassPage(driver, test);
					Object createSessionPageObject = liveClassObject.goToCreateSessionPage(data);
					Assert.assertTrue(createSessionPageObject instanceof LiveClassPage);
				}

			}

			@Severity(SeverityLevel.BLOCKER)
			@Test(dataProvider = "getTeacherData", invocationCount = 1, priority = 101, testName = "Verify Session Type", enabled = true, description = "Verifying user is able to select Session type")
			public void verifySessionType(Hashtable<String, String> data) throws Exception {
				Thread.sleep(3000);
				LiveClassPage liveClassObject = new LiveClassPage(driver, test);
				Object result = liveClassObject.selectSessionType("Live Lecture");
				Assert.assertTrue((Boolean) result);

			}

			@Severity(SeverityLevel.BLOCKER)
			@Test(dataProvider = "getTeacherData", invocationCount = 1, priority = 102, testName = "Verify Board", enabled = true, description = "Verifying user is able to select Board")
			public void verifyBoard(Hashtable<String, String> data) throws Exception {
				Thread.sleep(3000);
				LiveClassPage liveClassObject = new LiveClassPage(driver, test);
				Object resutlt = liveClassObject.selectBoard(data.get("board"), data.get("school_name"));
				Assert.assertTrue((Boolean) resutlt);

			}

			@Severity(SeverityLevel.BLOCKER)
			@Test(dataProvider = "getTeacherData", invocationCount = 1, priority = 103, testName = "Verify Class", enabled = true, description = "Verifying user is able to select class")
			public void verifyClass(Hashtable<String, String> data) throws Exception {
				Thread.sleep(3000);
				LiveClassPage liveClassObject = new LiveClassPage(driver, test);
				Object resutlt = liveClassObject.selectClass(data.get("class"));
				Assert.assertTrue((Boolean) resutlt);
			}

			@Severity(SeverityLevel.BLOCKER)
			@Test(dataProvider = "getTeacherData", invocationCount = 1, priority = 104, testName = "Verify Subject", enabled = true, description = "Verifying user is able to select Subject")
			public void verifySubject(Hashtable<String, String> data) throws Exception {
				Thread.sleep(3000);
				LiveClassPage liveClassObject = new LiveClassPage(driver, test);
				Object resutlt = liveClassObject.selectSubject(data.get("subject_name"));
				Assert.assertTrue((Boolean) resutlt);
			}

			@Severity(SeverityLevel.BLOCKER)
			@Test(dataProvider = "getTeacherData", invocationCount = 1, priority = 105, testName = "Verify Section", enabled = true, description = "Verifying user is able to select Section")
			public void verifySection(Hashtable<String, String> data) throws Exception {
				Thread.sleep(3000);
				LiveClassPage liveClassObject = new LiveClassPage(driver, test);
				Object resutlt = liveClassObject.selectSection(data.get("section"));
				Assert.assertTrue((Boolean) resutlt);
			}

			@Severity(SeverityLevel.BLOCKER)
			@Test(invocationCount = 1, priority = 106, testName = "Verify Date", enabled = true, description = "Verifying user is able to select date")
			public void verifyDate() throws Exception {
				Thread.sleep(3000);
				LiveClassPage liveClassObject = new LiveClassPage(driver, test);
				Object resutlt = liveClassObject.selectDatePicker();
				Assert.assertTrue((Boolean) resutlt);
			}

			@Severity(SeverityLevel.NORMAL)
			@Test(invocationCount = 1, priority = 107, testName = "Verify Duration", enabled = true, description = "Verifying user is able to enter duration")
			public void verifyDuration() throws Exception {
				Thread.sleep(3000);
				LiveClassPage liveClassObject = new LiveClassPage(driver, test);
				Object resutlt = liveClassObject.enterDuration("30");
				Assert.assertTrue((Boolean) resutlt);
			}

			@Severity(SeverityLevel.NORMAL)
			@Test(dataProvider = "getTeacherData", invocationCount = 1, priority = 108, testName = "VerifyLectureTitle", enabled = true, description = "Verifying user is able to enter Lecture Title")
			public void verifyLectureTitle(Hashtable<String, String> data) throws Exception {
				Thread.sleep(3000);
				LiveClassPage liveClassObject = new LiveClassPage(driver, test);
				Object result = liveClassObject.enterLectureTitle(data);
				Assert.assertTrue((Boolean) result);
			}

			@Severity(SeverityLevel.NORMAL)
			@Test(dataProvider = "getTeacherData", invocationCount = 1, priority = 109, testName = "Verify Submit Button", enabled = true, description = "Verifying user is able to submit details")
			public void verifySubmit(Hashtable<String, String> data) throws Exception {
				Thread.sleep(3000);
				LiveClassPage liveClassObject = new LiveClassPage(driver, test);
				Object result = liveClassObject.clickSubmit(data);
				Assert.assertTrue(result instanceof ViewLiveClassPage);
			}
		}

		public static class ViewSessionByStudent extends LiveClass {
			@Severity(SeverityLevel.BLOCKER)
			@Test(dataProvider = "getStudentData", priority = 200, invocationCount = 1, testName = "Verify GoToSchoolLinkClickable", enabled = true, description = "Verifying Student is able to navigaate on GoToSchool Link")
			public void verifyGoToSchoolLinkClickable(Hashtable<String, String> data) throws Exception {
				if (!DataUtil.isTestRunnable(xls, "StudentLoginTest") || data.get("Runmode").equals("N")) {
					test.log(LogStatus.SKIP, "Skipping the test");
					Reporter.log("Skipping the test");
					throw new SkipException("skipping the test");
				}
				Thread.sleep(3000);
				LiveClassPage liveClassPageObject = new LiveClassPage(driver, test);
				Object resultViewSession = liveClassPageObject.clickStudentGoToSchool();
				Thread.sleep(2000);
				Assert.assertTrue((Boolean) resultViewSession);
			}

			@Severity(SeverityLevel.BLOCKER)
			@Test(dataProvider = "getStudentData", priority = 201, invocationCount = 1, testName = "VerifyViewSessionByStudent", enabled = true, description = "Verifying Student is able to view Live Class/Session")
			public void verifyViewSessionByStudent(Hashtable<String, String> data) throws Exception {

				LiveClassPage liveClassPageObject = new LiveClassPage(driver, test);
				Object resultViewSession = liveClassPageObject.getUpcommingClassDetail();
				liveClassPageObject.takeScreenShot();
				Thread.sleep(2000);
				if (resultViewSession instanceof String) {
					String classDetail = (String) resultViewSession;
					for (int i = 0; i < LiveClassPage.expectedSessionData.size(); i++) {
						Thread.sleep(2000);
						if (data.get("school_name")
								.equalsIgnoreCase(LiveClassPage.expectedSessionData.get(i).get("school_name"))) {
							if (data.get("board")
									.equalsIgnoreCase(LiveClassPage.expectedSessionData.get(i).get("board"))) {
								if (data.get("class")
										.equalsIgnoreCase(LiveClassPage.expectedSessionData.get(i).get("class"))) {
									if (data.get("section").equalsIgnoreCase(
											LiveClassPage.expectedSessionData.get(i).get("section"))) {
										Date date = new Date();
										// Pattern
										SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
										liveClassPageObject.takeScreenShot();
										Assert.assertTrue(classDetail.contains(sdf.format(date)));
										Assert.assertTrue(classDetail
												.contains(LiveClassPage.expectedSessionData.get(i).get("title")));
										liveClassPageObject.takeScreenShot();
									}
								}
							}
						}
					}
				}

			}

			@Severity(SeverityLevel.BLOCKER)
			@Test(dataProvider = "getStudentData", priority = 202, invocationCount = 1, testName = "joinLiveClassByStudent", enabled = true, description = "Verifying Student is able to join Classes")
			public void joinLiveClassByStudent(Hashtable<String, String> data) throws Exception {
				LiveClassPage liveClassPageObject = new LiveClassPage(driver, test);
				Object resultViewSession = liveClassPageObject.joinLiveClassByStudent(data);
				Thread.sleep(2000);
				Assert.assertTrue((Boolean) resultViewSession);

			}
		}
	}

	public static class HomeWork extends GoToSchoolTest {
		public static class AssignHomeWorkByTeacher extends HomeWork {
			@Severity(SeverityLevel.BLOCKER)
			@Test(dataProvider = "getTeacherData", invocationCount = 1, priority = 300, testName = "Verify GoToSchoolLinkClickable", enabled = true)
			public void verifyGoToSchoolLinkClickable(Hashtable<String, String> data) throws Exception {
				if (!DataUtil.isTestRunnable(xls, "TeacherLoginTest") || data.get("Runmode").equals("N")) {
					test.log(LogStatus.SKIP, "Skipping the test");
					Reporter.log("Skipping the test");
					throw new SkipException("skipping the test");
				}
				Thread.sleep(3000);
				DashBoardPage dashboardPage = new DashBoardPage(driver, test);
				dashboardPage.GoToSchoolLink.get(0).click();
			}

			@Severity(SeverityLevel.BLOCKER)
			@Test(dataProvider = "getTeacherData", invocationCount = 1, priority = 301, testName = "VerifyAssignHomework", enabled = true)
			public void verifyAssignHomework(Hashtable<String, String> data) throws Exception {
				SchoolAtHomePage schoolAtHomePage = new SchoolAtHomePage(driver, test);
				Worksheet_HomeworkPage worksheet_homeworkPage = new Worksheet_HomeworkPage(driver, test);
				worksheet_homeworkPage.takeScreenShot();
				Object result = worksheet_homeworkPage.assignHomework(data);
				Assert.assertTrue(result instanceof ViewHomeworkPage);
				worksheet_homeworkPage.takeScreenShot();

			}
		}

		public static class VerifyHomeWorkByStudent extends HomeWork {
			@Severity(SeverityLevel.BLOCKER)
			@Test(dataProvider = "getStudentData", invocationCount = 1, priority = 400, testName = "verifyGoToSchoolLinkClickable", enabled = true)
			public void verifyGoToSchoolLinkClickable(Hashtable<String, String> data, Method method) throws Exception {

				Thread.sleep(2000);
				if (!DataUtil.isTestRunnable(xls, "StudentLoginTest") || data.get("Runmode").equals("N")) {
					test.log(LogStatus.SKIP, "Skipping the test");
					Reporter.log("Skipping the test");
					throw new SkipException("skipping the test");
				}
				Thread.sleep(3000);
				DashBoardPage dashboardPage = new DashBoardPage(driver, test);
				dashboardPage.GoToSchoolStudentLink.click();
			}

			@Severity(SeverityLevel.BLOCKER)
			@Test(dataProvider = "getStudentData", invocationCount = 1, priority = 401, testName = "Verify Homework", enabled = true)
			public void verifyHomework(Hashtable<String, String> data, Method method) throws Exception {

				Worksheet_HomeworkPage worksheet_homeworkPage = new Worksheet_HomeworkPage(driver, test);
				Object resultSchoolAtHome = worksheet_homeworkPage.verifyHomework(data);
			}

		}
	}

	public static class Worksheet extends GoToSchoolTest {
		public static class AssignWorksheetByTeacher extends HomeWork {
			@Severity(SeverityLevel.BLOCKER)
			@Test(dataProvider = "getTeacherData", invocationCount = 1, priority = 500, testName = "verifyGoToSchoolClickable", enabled = true)
			public void verifyGoToSchoolClickable(Hashtable<String, String> data) throws Exception {
				if (!DataUtil.isTestRunnable(xls, "TeacherLoginTest") || data.get("Runmode").equals("N")) {
					test.log(LogStatus.SKIP, "Skipping the test");
					Reporter.log("Skipping the test");
					throw new SkipException("skipping the test");
				}
				Thread.sleep(3000);

				DashBoardPage dashboardPage = new DashBoardPage(driver, test);
				clickElement(dashboardPage.GoToSchoolLink, 0, "GoToSchoolLink");
				dashboardPage.takeScreenShot();

			}

			@Severity(SeverityLevel.BLOCKER)
			@Test(dataProvider = "getTeacherData", invocationCount = 1, priority = 501, testName = "VerifyAssignWorksheet", enabled = true)
			public void verifyAssignWorksheet(Hashtable<String, String> data) throws Exception {
				Worksheet_HomeworkPage worksheet_homeworkPage = new Worksheet_HomeworkPage(driver, test);
				Object result = worksheet_homeworkPage.assignWorksheet(data);
				Assert.assertTrue(result instanceof DashBoardPage);
			}
		}

		public static class VerifyWorksheetByStudent extends HomeWork {
			@Severity(SeverityLevel.BLOCKER)
			@Test(dataProvider = "getStudentData", invocationCount = 1, priority = 600, testName = "verifyGoToSchoolLinkClickable", enabled = true)
			public void verifyGoToSchoolLinkClickable(Hashtable<String, String> data, Method method) throws Exception {
				Thread.sleep(2000);
				if (!DataUtil.isTestRunnable(xls, "StudentLoginTest") || data.get("Runmode").equals("N")) {
					test.log(LogStatus.SKIP, "Skipping the test");
					Reporter.log("Skipping the test");
					throw new SkipException("skipping the test");
				}
				Thread.sleep(3000);

				DashBoardPage dashboardPage = new DashBoardPage(driver, test);
				dashboardPage.GoToSchoolStudentLink.click();

			}

			@Severity(SeverityLevel.BLOCKER)
			@Test(dataProvider = "getStudentData", invocationCount = 1, priority = 601, testName = "Verify Worksheet", enabled = true)
			public void verifyWorksheet(Hashtable<String, String> data, Method method) throws Exception {

				Worksheet_HomeworkPage worksheet_homeworkPage = new Worksheet_HomeworkPage(driver, test);
				Object result = worksheet_homeworkPage.verifyWorksheet(data);
				Assert.assertTrue(result instanceof ViewWorksheetPage);

			}
		}
	}

	public static class WeeklyTest extends GoToSchoolTest {
		public static class AssignWeeklyTestByTeacher extends WeeklyTest {
			@Severity(SeverityLevel.BLOCKER)
			@Test(dataProvider = "getTeacherData", invocationCount = 1, priority = 700, testName = "Verify GoToSchoolLinkClickable", enabled = true)
			public void verifyGoToSchoolLinkClickable(Hashtable<String, String> data) throws Exception {
				if (!DataUtil.isTestRunnable(xls, "TeacherLoginTest") || data.get("Runmode").equals("N")) {
					test.log(LogStatus.SKIP, "Skipping the test");
					Reporter.log("Skipping the test");
					throw new SkipException("skipping the test");
				}
				Object resultLogin = defaultLogin(data);
				Thread.sleep(3000);
				if (resultLogin instanceof DashBoardPage) {
					DashBoardPage dashboardPage = (DashBoardPage) resultLogin;
					dashboardPage.GoToSchoolLink.get(0).click();
				}
			}

			@Severity(SeverityLevel.BLOCKER)
			@Test(dataProvider = "getTeacherData", invocationCount = 1, priority = 701, testName = "VerifyAssignWeeklyTest", enabled = false)
			public void verifyAssignWeeklyTest(Hashtable<String, String> data, Method method) throws Exception {
				SchoolAtHomePage schoolAtHomePage = new SchoolAtHomePage(driver, test);
				WeeklyTestPage weeklyTestPage = new WeeklyTestPage(driver, test);
				Object result = weeklyTestPage.weeklyTest(data);
				Assert.assertTrue(result instanceof DashBoardPage);
			}

		}

		public static class VerifyWeeklyTestByStudent extends WeeklyTest {
			@Severity(SeverityLevel.BLOCKER)
			@Test(dataProvider = "getStudentData", invocationCount = 1, priority = 800, testName = "verifyGoToSchoolLinkClickable", enabled = true)
			public void verifyGoToSchoolLinkClickable(Hashtable<String, String> data, Method method) throws Exception {

				Thread.sleep(2000);
				if (!DataUtil.isTestRunnable(xls, "StudentLoginTest") || data.get("Runmode").equals("N")) {
					test.log(LogStatus.SKIP, "Skipping the test");
					Reporter.log("Skipping the test");
					throw new SkipException("skipping the test");
				}
				Thread.sleep(3000);

				DashBoardPage dashboardPage = new DashBoardPage(driver, test);
				dashboardPage.GoToSchoolStudentLink.click();

			}

			@Severity(SeverityLevel.BLOCKER)
			@Test(dataProvider = "getStudentData", invocationCount = 1, priority = 801, testName = "VerifyWeeklyTest", enabled = true)
			public void verifyWeeklyTest(Hashtable<String, String> data, Method method) throws Exception {
				WeeklyTestPage weeklyTestPage = new WeeklyTestPage(driver, test);
				weeklyTestPage.takeScreenShot();
				Object result = weeklyTestPage.verifyWeeklyTest(data);
				Assert.assertTrue(result instanceof DashBoardPage);

			}

		}
	}
}