package com.extramarks_website_testcases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.extramarks_website_pages.DashBoardPage;
import com.extramarks_website_pages.HomePage;
import com.extramarks_website_pages.LaunchPage;
import com.extramarks_website_pages.LoginPage;
import com.extramarks_website_utils.Constants;
import com.extramarks_website_utils.DataUtil;
import com.extramarks_website_utils.ExtentManager;
import com.extramarks_website_utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

//@Listeners(AllureListener.class)	
public class ParentLoginTest extends BaseTest {
	DashBoardPage dashboardPage;

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
	public Object[][] getParentData() {
		Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);
		Object[][] data = DataUtil.getData(xls, "ParentLoginTest");
		return data;
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(dataProvider = "getParentData", testName = "Validating Home Page Title", description = "Verifying Home Page Title on Home Page ", priority = 1)
	public void verifyHomePageTitle(Hashtable<String, String> data) {
		try {
			SoftAssert sAssert = new SoftAssert();
			if (!DataUtil.isTestRunnable(xls, "ParentLoginTest") || data.get("Runmode").equals("N")) {
				test.log(LogStatus.SKIP, "Skipping the test");
				Reporter.log("Skipping the test");
				throw new SkipException("skipping the test");
			}
			driver = openBrowser("Chrome");
			LaunchPage launch = new LaunchPage(driver, test);
			Object resultGetURL = launch.goToHomePage();
			if (resultGetURL instanceof HomePage) {
				test.log(LogStatus.INFO, "Website Home Page");
				Reporter.log("Website Home Page");
				HomePage homePage = (HomePage) resultGetURL;
				Thread.sleep(3000);
				String pageTitle = driver.getTitle();
				Thread.sleep(2000);
				assertEquals(pageTitle, data.get("HomePageTitle"), "Verifying HomePageTitle ", sAssert);

			}
		} catch (Exception e) {

			System.out.println("Got error while validating page title");
			test.log(LogStatus.FAIL, "Got error while validating page title");

		}
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(dataProvider = "getParentData", testName = "Validating Login Page", description = "Verifying Login Page is displayed after click on Sign in button", priority = 2)
	public void verifyLoginPage(Hashtable<String, String> data) {
		try {
			LoginPage loginPage = null;
			SoftAssert sAssert = new SoftAssert();
			HomePage homePage = new HomePage(driver, test);
			Object clickSignObject = homePage.clickSignIn();
			if (clickSignObject instanceof LoginPage) {
				loginPage = (LoginPage) clickSignObject;
				test.log(LogStatus.INFO, "Login Page");
				Reporter.log("Login Page");
				loginPage.takeScreenShot();
				WebElement code = driver.findElement(By
						.xpath("//*[@id='go-to-school-initiative']//*[@class='login-box newlogin mt90']//form/div//a"));
				clickElement(code, "code");
				Thread.sleep(1000);
			} else {
				test.log(LogStatus.INFO, "Login Page is nor displayed");
				Reporter.log("Login Page is not displayed");

			}
			WebDriverWait wait = new WebDriverWait(driver, 30);
			sAssert.assertNotNull(wait.until(ExpectedConditions.visibilityOf(loginPage.usernameField)));
			sAssert.assertAll();
		} catch (Exception e) {
			test.log(LogStatus.INFO, "Error on Login Page");
			System.out.println("Error on Login Page");
		}

	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(dataProvider = "getParentData", testName = "Validating Parent Login", description = "Verifying Parent user is successful Login in to application", priority = 3)
	public void verifyParentLogin(Hashtable<String, String> data) {

		try {
			LoginPage loginPage = new LoginPage(driver, test);
			SoftAssert sAssert = new SoftAssert();
			String actualResult = "";
			String expectedResult = "Login_Pass";
			Object loginResultPage = null;
			if (data.get("email") == null || data.get("email") == "") {
				if (data.get("mobile") == null || data.get("mobile") == "") {
					test.log(LogStatus.FAIL, "Data is blank !");
					// sAssert.fail("Data is blank !");
				} else {
					if (data.get("mobile").length() != 10) {
						test.log(LogStatus.FAIL, "Mobile Number is Invalid !");
						// sAssert.fail("Mobile Number is Invalid !");
					} else {

						Thread.sleep(1000);
						loginResultPage = loginPage.doLogin(data.get("mobile"), data.get("Password"));
					}
				}

			} else {
				if (DataUtil.isValidEmail(data.get("email"))) {
					Thread.sleep(1000);
					loginResultPage = loginPage.doLogin(data.get("email"), data.get("Password"));
				} else {
					test.log(LogStatus.FAIL, "Email is Invalid !");
					// sAssert.fail("Email is Invalid !");
				}
			}
			SQLConnector con = new SQLConnector();
			String database_userType = con.connectionsetup(
					"SELECT user_type_id FROM `user` WHERE `username`=" + data.get("Username"), "user_type_id");
			String database_userPassword = con.connectionsetup(
					"SELECT password FROM `user` WHERE `username`=" + data.get("Username"), "password");
			Thread.sleep(2000);
			if (loginResultPage instanceof DashBoardPage) {
				if (String.valueOf(Math.round(Double.parseDouble(data.get("Password")))).equalsIgnoreCase("123456")) {
					// if
					// (database_userPassword.equalsIgnoreCase("e10adc3949ba59abbe56e057f20f883e"))
					// {
					actualResult = "Login_Pass";
					if (database_userType.equalsIgnoreCase("1")) {
						System.out.println("Student is Logged in Successfully");
						test.log(LogStatus.INFO, "Student is Logged in Successfully");
					} else if (database_userType.equalsIgnoreCase("2")) {
						System.out.println("Parent is Logged in Successfuuly");
						test.log(LogStatus.INFO, "Parent is Logged in Successfully");
					} else {
						System.out.println("Teacher is Logged in Successfully");
						test.log(LogStatus.INFO, "Teacher is Logged in Successfully");
					}

					// } else {
					// actualResult = "Login_fail_User_Not_Exists";
					// }
				}
				if (String.valueOf(Math.round(Double.parseDouble(data.get("Password")))).equalsIgnoreCase("12345678")) {
					// if
					// (database_userPassword.equalsIgnoreCase("e10adc3949ba59abbe56e057f20f883e"))
					// {
					actualResult = "Login_Pass";
					// } else {
					actualResult = "Login_fail_User_Not_Exists";
					// }

				}
			} else {
				actualResult = "Login_fail_Dashboard_Page_is_not_displayed";
			}
			assertEquals(actualResult, expectedResult, "Verifying Parent Login Test Functionality", sAssert);
			sAssert.assertAll();
		} catch (Exception e) {
			System.out.println("Error on Login");
			test.log(LogStatus.INFO, "Error on Login");
		}
	}

	@Test(dataProvider = "getParentData", testName = "Validating Dashboard Page Title", description = "Verifying Dashboard Page title after successful Login", priority = 4)
	public void verifyDashboardPageTitle(Hashtable<String, String> data) {
		try {
			SoftAssert sAssert = new SoftAssert();
			test.log(LogStatus.INFO, "Website Dashboard Page");
			Reporter.log("Website Dashboard Page");
			Thread.sleep(3000);
			String pageTitle = driver.getTitle();
			Thread.sleep(2000);
			assertEquals(pageTitle, data.get("DashboardPageTitle"), "Verifying DashboardPageTitle ", sAssert);

		} catch (Exception e) {

			System.out.println("Got error while validating page title");
			test.log(LogStatus.FAIL, "Got error while validating page title");

		}
	}
}