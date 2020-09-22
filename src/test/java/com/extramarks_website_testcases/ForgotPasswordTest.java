package com.extramarks_website_testcases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
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
import com.extramarks_website_pages.ForgotPasswordPage;
import com.extramarks_website_pages.HomePage;
import com.extramarks_website_pages.LaunchPage;
import com.extramarks_website_pages.LoginPage;
import com.extramarks_website_pages.ProfilePage;
import com.extramarks_website_utils.Constants;
import com.extramarks_website_utils.DataUtil;
import com.extramarks_website_utils.ExtentManager;
import com.extramarks_website_utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class ForgotPasswordTest extends BaseTest {
	

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
		Object[][] data = DataUtil.getData(xls, "ForgotPasswordTest");
		return data;
	}

	@Test(dataProvider = "getData", priority = 1,testName="Validating Forgot Password")
	public void forgotPassword(Hashtable<String, String> data,Method method) {
		
		try {
		String expectedResult = "ForgotPassword_PASS";
		String actualResult = "";
		SoftAssert sAssert = new SoftAssert();
		String browser = data.get("Browser");
		openBrowser(browser);
		LaunchPage launch = new LaunchPage(driver, test);
		Object resultGetURL = launch.goToHomePage();		
		ForgotPasswordPage fp = new ForgotPasswordPage(driver, test);
		SQLConnector con = new SQLConnector();
		String queryUsername = "select username from user where username in ('" + data.get("RegisteredMobile/Email")
				+ "')";
		String database_username = con.connectionsetup(queryUsername, "username");
		if (database_username.equalsIgnoreCase(data.get("RegisteredMobile/Email"))) {

			PageFactory.initElements(driver, fp);
			WebDriverWait wt = new WebDriverWait(driver, 20);
			wt.until(ExpectedConditions.elementToBeClickable(fp.SignIn));
		         HomePage hp = (HomePage) resultGetURL;
		         Object clickSignObject=hp.clickSignIn();
			        LoginPage lp = (LoginPage) clickSignObject;
			        WebElement code = driver.findElement(
							By.xpath("//*[@id='go-to-school-initiative']//*[@class='login-box newlogin mt90']//form/div//a"));
					clickElement(code,"code");
					Thread.sleep(1000);
			Object forgotPasswordResult = fp.forgotPassword(data.get("RegisteredMobile/Email"));
			if (forgotPasswordResult instanceof LoginPage) {
				System.out.println("ForgotPassword done successfully");
				test.log(LogStatus.INFO, "ForgotPassword done successfully");

					/*
					 * String query =
					 * "SELECT `sms_text` FROM `t_sms_log` WHERE `user_id` in (select `user_id` from user where username in ('"
					 * + data.get("RegisteredMobile/Email") + "') order by id desc)"; String
					 * database_sms_text = con.connectionsetup(query, "sms_text"); String OTP =
					 * database_sms_text.substring(29, 35); System.out.println("OTP : " + OTP);
					 * test.log(LogLogStatus.INFO, "OTP : " + OTP); Xls_Reader xls = new
					 * Xls_Reader(Constants.XLS_FILE_PATH); DataUtil.setData(xls,
					 * "ForgotPasswordTest", 1, "Password", OTP);
					 * 
					 * Object loginResultPage = lp.doLogin(data.get("RegisteredMobile/Email"), OTP);
					 * if (loginResultPage instanceof DashBoardPage) { actualResult =
					 * "ForgotPassword_PASS"; } else { actualResult =
					 * "ForgotPassword_Unable to Login with created Password_Fail"; }
					 */

			} else {
				System.out.println("ForgotPassword Fail");
				test.log(LogStatus.FAIL, "ForgotPassword Fail");
				actualResult = "ForgotPassword Fail";
				sAssert.fail("ForgotPassword Fail");
				fp.takeScreenShot();
			}

		} else

		{
			System.out.println("Mobile Number is not Exists");
			test.log(LogStatus.FAIL, "Mobile Number is not Exists");
			Thread.sleep(4000);
			if (fp.validationMsg.getText()
					.equalsIgnoreCase("Enter your registered Mobile Number/E-mail ID to reset password:")) {
				actualResult = "ForgotPassword_PASS";
				test.log(LogStatus.FAIL, "ForgotPassword_Username_Validation_on_Forgot_Password_Pass");
				System.out.println("ForgotPassword_Username_Validation_on_Forgot_Password_Pass");
				Reporter.log("ForgotPassword_Username_Validation_on_Forgot_Password_Pass");
			} else {
				actualResult = "ForgotPassword_Username_Validation_on_Forgot_Password_Fail";
				sAssert.fail("ForgotPassword_Username_Validation_on_Forgot_Password_Fail");
				System.out.println("ForgotPassword_Username_Validation_on_Forgot_Password_Fail");
				test.log(LogStatus.FAIL, "ForgotPassword_Username_Validation_on_Forgot_Password_Fail");
				fp.takeScreenShot();
			}
		}
	}catch(Exception e) {
		test.log(LogStatus.FAIL, "Getting Exception :"+e.getMessage());
		
	}}
}
