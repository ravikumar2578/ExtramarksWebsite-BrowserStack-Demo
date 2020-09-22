package com.extramarks_website_testcases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.extramarks_website_pages.DashBoardPage;
import com.extramarks_website_pages.Mentor_Assessment;
import com.extramarks_website_utils.Constants;
import com.extramarks_website_utils.DataUtil;
import com.extramarks_website_utils.ExtentManager;
import com.extramarks_website_utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;


public class MentorAssessmentTest extends BaseTest {

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
		Object[][] data = DataUtil.getData(xls, "MentorAssessmentTest");
		return data;
	}

	@Test(dataProvider = "getData",testName="Validating Mentor Assessment",description="Verifying Teacher is allowed to create Assessment")
	public void MentorAssessment(Hashtable<String, String> data,Method method) {
		
		try {
			SoftAssert sAssert = new SoftAssert();
			Boolean loginResult = defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"),
					data.get("Board"), data.get("Class"));
			if (loginResult) {
				driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				DashBoardPage dp = new DashBoardPage(driver, test);
				String expectedResult = "PASS";
				String actualResult = "";
				Thread.sleep(3000);
				// JavascriptExecutor js=(JavascriptExecutor)driver;
				// js.executeScript("arguments[0].click();", dp.LeftMenumentorTab.get(0));
				Thread.sleep(3000);
				clickElement(dp.Assessment, "Assessment");
				Mentor_Assessment ma = new Mentor_Assessment(driver, test);
				Thread.sleep(1000);
				if (dp.AssessmentSwitchClass.size() != 0) {
					if (dp.AssessmentSwitchClass.get(0).isDisplayed()) {
						clickElement(dp.AssessmentSwitchClass, 0, "AssessmentSwitchClass");
						Select boardSel = new Select(dp.AssessmentSwitchBoard.get(0));
						boardSel.selectByVisibleText(data.get("Board"));
						Select classSel = new Select(dp.AssessmentSwitchClass.get(0));
						classSel.selectByVisibleText(data.get("Class"));
						clickElement(dp.AssessmentSwitchBoardContinueBtn, "dp.AssessmentSwitchBoardContinueBtn");
						Thread.sleep(3000);
					}
				}
				test.log(LogStatus.INFO, "Opening Assessment Page");
				System.out.println("Opening Assessment Page");
				ma.DraftAssessment();
				Thread.sleep(3000);
				ma.AssessmentDetails(data.get("Title"), data.get("Class"), data.get("Subject"), data.get("Chapter"),
						data.get("Hours_1"), data.get("Hours_2"), data.get("Minutes_1"), data.get("Minutes_2"),
						data.get("AssessmentInstruction"));
				ma.writeQuestions(data.get("Ques_Area"));
				String students = ma.step3StudentCount.getText();
				if (students.contains("0")) {
					System.out.println("No any Student is Added");
					test.log(LogStatus.INFO, "No any Student is Added");
					actualResult = "PASS";
				} else {
					ma.shareAssessment(data.get("Ques_Area"));
					List<HashMap<String, String>> viewAssessmentList = ma.AssessmentList(data.get("Marks"));
					for (int i = 0; i < viewAssessmentList.size(); i++) {
						if (viewAssessmentList.get(i).get("Subject").equalsIgnoreCase(data.get("Subject"))) {
							if (viewAssessmentList.get(i).get("Chapter").equalsIgnoreCase(data.get("Chapter"))) {
								actualResult = "PASS";
							} else {
								{
									actualResult = "Fail";
								}
							}
						}
					}

				}

				if (!expectedResult.equals(actualResult)) {
					dp.takeScreenShot();
					test.log(LogStatus.FAIL, "AssessmentTest Failed");
					System.out.println("AssessmentTest Failed");
					sAssert.fail("AssessmentTest Failed");
				} else {
					test.log(LogStatus.PASS, "AssessmentTest passed");
					System.out.println("AssessmentTest Pass");
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
}
