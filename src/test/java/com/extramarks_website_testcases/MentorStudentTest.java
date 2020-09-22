package com.extramarks_website_testcases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Hashtable;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
import com.extramarks_website_pages.LoginPage;
import com.extramarks_website_pages.Mentor_StudentPage;
import com.extramarks_website_utils.Constants;
import com.extramarks_website_utils.DataUtil;
import com.extramarks_website_utils.ExtentManager;
import com.extramarks_website_utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class MentorStudentTest extends BaseTest {

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
		Object[][] data = DataUtil.getData(xls, "MentorStudentTest");
		return data;
	}

	@Test(dataProvider = "getData",testName="Mentor Student")
	public void MentorsStudent(Hashtable<String, String> data, Method method) {
	
		try {
			SoftAssert sAssert = new SoftAssert();
			Boolean loginResult = defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"),
					data.get("Board"), data.get("Class"));
			if (loginResult) {
				String expectedResult = "MentorStudentTest_PASS";
				String actualResult = "";
				LoginPage lp = new LoginPage(driver, test);
				DashBoardPage dp = new DashBoardPage(driver, test);
				Mentor_StudentPage msp = new Mentor_StudentPage(driver, test);
				Object resultPage = dp.openStudent();
				if (resultPage instanceof Mentor_StudentPage) {
					test.log(LogStatus.INFO, "StudentPage opens");
					expectedResult = "MentorStudentTest_PASS";
					System.out.println("StudentPage opens");
				}

				else {
					actualResult = "StudentPage not opens";
					lp.takeScreenShot();
					test.log(LogStatus.INFO, "StudentPage not open");
					System.out.println("StudentPage not opens");
				}
				JavascriptExecutor js = ((JavascriptExecutor) driver);
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				SQLConnector con = new SQLConnector();
				String database_userType = con.connectionsetup(
						"SELECT user_type_id FROM `user` WHERE `username`=" + data.get("Mobile"), "user_type_id");
				String database_email = con
						.connectionsetup("SELECT email FROM `user` WHERE `username`=" + data.get("Mobile"), "email");
				Thread.sleep(2000);
				if (database_email.equalsIgnoreCase(data.get("Email")) && database_userType.equalsIgnoreCase("1")) {
					boolean result = msp.AddStudent(data.get("Email"), data.get("Board"), data.get("Class"),
							data.get("Subject"));

					Thread.sleep(3000);
					lp.takeScreenShot();
					if (result) {
						actualResult = "MentorStudentTest_PASS";
					} else {
						actualResult = "MentorStudentTest_FAIL";
					}

				} else {
					test.log(LogStatus.INFO, "Email id is incorrect");
					System.out.println("Email id is incorrect");
					WebDriverWait wt = new WebDriverWait(driver, 30);
					boolean result = msp.AddStudent(data.get("Email"), data.get("Board"), data.get("Class"),
							data.get("Subject"));
					wt.until(ExpectedConditions.visibilityOf(msp.InvalidEmailMsg.get(0)));
					if (msp.InvalidEmailMsg.get(0).getText().trim().equalsIgnoreCase("Please enter Student Email")) {
						test.log(LogStatus.PASS, "Add Student - Email Vaildation Test Pass");
						System.out.println("Add Student - Email Vaildation Test Pass");
						expectedResult = "MentorStudentTest_PASS";
					} else {
						test.log(LogStatus.FAIL, "Add Student - Email Vaildation Test Fail");
						System.out.println("Add Student - Email Vaildation Test Fail");
						sAssert.fail("Add Student - Email Vaildation Test Fail");
						lp.takeScreenShot();
						actualResult = "Add Student - Email Vaildation Test Fail";
					}
				}
				if (!expectedResult.equals(actualResult)) {
					// take screenshot
					lp.takeScreenShot();
					test.log(LogStatus.FAIL, "Got actual result as " + actualResult);
					sAssert.fail("Got actual result as " + actualResult);
				} else {
					test.log(LogStatus.PASS, "Mentors Student Test passed");
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
