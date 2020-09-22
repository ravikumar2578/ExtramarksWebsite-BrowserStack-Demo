package com.extramarks_website_testcases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
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
import com.extramarks_website_pages.ProfilePage;
import com.extramarks_website_pages.SubscriptionPage;
import com.extramarks_website_utils.Constants;
import com.extramarks_website_utils.DataUtil;
import com.extramarks_website_utils.ExtentManager;

import com.extramarks_website_utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class SettingTest extends BaseTest {

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
		Object[][] data = DataUtil.getData(xls, "SettingTest");
		return data;
	}

	@DataProvider
	public Object[][] getChangePasswordData() {
		Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);
		Object[][] data = DataUtil.getData(xls, "ChangePasswordTest");
		return data;
	}

	@Test(dataProvider = "getData", priority = 1, testName = "Validating edit user's profile", description = "Verifying that User is allowed to update profile")
	public void editProfile(Hashtable<String, String> data) {
		try {
			SoftAssert sAssert = new SoftAssert();
			Object loginResult = defaultLogin(data);

			DashBoardPage dp = new DashBoardPage(driver, test);
			Thread.sleep(3000);
			Object resultPage = dp.openProfile();
			if (resultPage instanceof ProfilePage) {
				test.log(LogStatus.INFO, "Opening Profile");
				System.out.println("Opening Profile");

				ProfilePage pg = (ProfilePage) resultPage;
				pg.takeScreenShot();
				Object resultPage2 = pg.editProfile();
				if (resultPage2 instanceof ProfilePage) {
					test.log(LogStatus.PASS, "Edit Profile Pass");
					System.out.println("Edit Profile Pass");
					pg.takeScreenShot();
					pg.takeFullScreenshot();
				} else {
					test.log(LogStatus.FAIL, "Edit Profile Fail");
					System.out.println("Edit Profile Fail");
					sAssert.fail("Edit Profile Fail");
					dp.takeScreenShot();
					dp.takeFullScreenshot();
				}
			} else {
				test.log(LogStatus.FAIL, "Profile Page not Opened");
				System.out.println("Profile Page not Opened");
				dp.takeScreenShot();
			}

			sAssert.assertAll();
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Getting Exception :" + e.getMessage());

		}
	}

	@Test(dataProvider = "getChangePasswordData", priority = 0, testName = "Validating Change Password", description = "Verifying Password is change successfully")
	public void changePassword(Hashtable<String, String> data, Method method) {
		try {
			Object loginResult = defaultLogin(data);
			SoftAssert sAssert = new SoftAssert();
			Thread.sleep(2000);
			if (loginResult instanceof DashBoardPage) {
				DashBoardPage dp = (DashBoardPage) loginResult;
				Object resultPage = dp.openProfile();
				if (resultPage instanceof ProfilePage) {
					test.log(LogStatus.INFO, "Opening Profile");
					System.out.println("Opening Profile");
					dp.takeScreenShot();
					ProfilePage pg = (ProfilePage) resultPage;
					pg.changePassword(data);
					WebDriverWait wt = new WebDriverWait(driver, 30);
					wt.until(ExpectedConditions.visibilityOf(pg.savePasswordSuccess));
					if (pg.savePasswordSuccess.getText().trim().equalsIgnoreCase("Password changed successfully")) {
						test.log(LogStatus.PASS, "ChangePassword Pass");
						System.out.println("ChangePassword Pass");
						pg.takeScreenShot();
						Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);
						String CurrentPassword = data.get("Password");
						Thread.sleep(100);
						DataUtil.setData(xls, "SettingTest", 1, "Password", data.get("ConfirmPassword"));
						Thread.sleep(100);
						DataUtil.setData(xls, "SettingTest", 1, "CurrentPassword", data.get("ConfirmPassword"));
						Thread.sleep(100);
						DataUtil.setData(xls, "SettingTest", 1, "NewPassword", CurrentPassword);
						Thread.sleep(100);
						DataUtil.setData(xls, "SettingTest", 1, "ConfirmPassword", CurrentPassword);
					} else {
						test.log(LogStatus.FAIL, "ChangePassword Fail");
						System.out.println("ChangePassword Fail");
						sAssert.fail("ChangePassword Fail");
						dp.takeScreenShot();
					}

				} else {
					test.log(LogStatus.INFO, "Profile Page not Opened");
					System.out.println("Profile Page not Opened");
					dp.takeScreenShot();
				}
			}
			sAssert.assertAll();
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Getting Exception :" + e.getMessage());

		}
	}

	@Test(dataProvider = "getData", priority = 2, testName = "Vaildating Redeem Voucher", description = "Verifying user is allowed to reddem voucher")
	public void redeemVoucher(Hashtable<String, String> data, Method method) {
		try {
			Object loginResult = defaultLogin(data);
			SoftAssert sAssert = new SoftAssert();
			LoginPage lp = new LoginPage(driver, test);
			DashBoardPage dp = new DashBoardPage(driver, test);
			dp.redeemVoucher(data);
			Thread.sleep(3000);

			try {

				Alert alert = driver.switchTo().alert();
				if (alert.getText().equalsIgnoreCase("The activation code or coupon code is invalid!")) {
					test.log(LogStatus.PASS, "Invalid Voucher entered");
					System.out.println("Invalid Voucher entered");
					alert.accept();
					dp.redeemVoucherClose.click();
					clickElement(dp.redeemVoucherClose, "redeemVoucherClose");

				} else {
					test.log(LogStatus.FAIL, "Message not Rec for invalid Voucher entered");
					System.out.println("Message not Rec for invalid Voucher entered");
				}
				dp.takeScreenShot();
			} catch (Exception e) {
				test.log(LogStatus.FAIL, "Getting Error on Voucher Page " + e.getMessage());
				System.out.println("Getting Error on Voucher Page " + e.getMessage());
				dp.takeScreenShot();
			}

		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Getting Exception :" + e.getMessage());

		}
	}

	@Test(dataProvider = "getData", priority = 3, testName = "Validating User's Subscription", description = "Verifying user is allowed to subscribe package")
	public void settingSubscription(Hashtable<String, String> data) {
		try {
			SoftAssert sAssert = new SoftAssert();
			Object loginResult = defaultLogin(data);
			LoginPage lp = new LoginPage(driver, test);
			DashBoardPage dp = new DashBoardPage(driver, test);

			Object resultPage = dp.openSubscription();
			if (resultPage instanceof SubscriptionPage) {
				test.log(LogStatus.INFO, "Opening Subscription Page");
				System.out.println("Opening Subscription Page");
				SubscriptionPage sg = (SubscriptionPage) resultPage;
				String packgeName = sg.packageName.getText().trim();
				String packgeValidity = sg.packageValidity.getText().trim();
				test.log(LogStatus.PASS,
						"Subscription Package Name : " + packgeName + " and Validity is : " + packgeValidity);
				System.out
						.println("Subscription Package Name : " + packgeName + " and Validity is : " + packgeValidity);
				sg.takeScreenShot();
				driver.navigate().back();
				Thread.sleep(3000);
			} else {
				test.log(LogStatus.FAIL, "Subscription Page not opened");
				System.out.println("Subscription Page not opened");
				sAssert.fail("Subscription Page not opened");
				dp.takeScreenShot();
			}

			sAssert.assertAll();
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Getting Exception :" + e.getMessage());

		}
	}
}
