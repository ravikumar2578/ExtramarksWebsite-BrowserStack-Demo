package com.extramarks_website_testcases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
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

import com.extramarks_website_pages.AssessmentPage;
import com.extramarks_website_pages.DashBoardPage;
import com.extramarks_website_pages.LoginPage;
import com.extramarks_website_utils.Constants;
import com.extramarks_website_utils.DataUtil;
import com.extramarks_website_utils.ExtentManager;
import com.extramarks_website_utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;


public class StudentAssessmentTest extends BaseTest {

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
		Object[][] data = DataUtil.getData(xls, "StudentAssessmentTest");
		return data;
	}

	@Test(dataProvider = "getData", invocationCount = 1,testName="Student Assessment")
	public void studentAssessment(Hashtable<String, String> data,Method method) {
	
		try {
			SoftAssert sAssert = new SoftAssert();
			Boolean loginResult = defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"),
					data.get("Board"), data.get("Class"));
			if (loginResult) {
				driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
				driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
				String expectedResult = "PASS";
				String actualResult = "";
				LoginPage lp = new LoginPage(driver, test);
				DashBoardPage dp = new DashBoardPage(driver, test);
				AssessmentPage ap = new AssessmentPage(driver, test);
				Thread.sleep(3000);
				Object resultPage = dp.openAssessment();
				ap.AttemptAssessment(data.get("Answer"));
				ap.SubmittedForEvaluation();
				ap.Evaluated();
				if (resultPage instanceof AssessmentPage) {
					test.log(LogStatus.INFO, "AssessmentPage validated");
					actualResult = "PASS";
					System.out.println("AssessmentPage opens");
				} else {
					actualResult = "FAIL";
					lp.takeScreenShot();
					test.log(LogStatus.INFO, "AssessmentPage not open");
					System.out.println("AssessmentPage not opens");
				}
				if (!expectedResult.equals(actualResult)) {
					// take screenshot
					lp.takeScreenShot();
					test.log(LogStatus.FAIL, "Got actual result as " + actualResult);
					Assert.fail("Got actual result as " + actualResult);
				}
				test.log(LogStatus.PASS, "Student Assessment test passed");
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
