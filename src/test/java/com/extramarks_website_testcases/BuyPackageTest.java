package com.extramarks_website_testcases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
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

import com.extramarks_website_pages.BuyPackagePage;
import com.extramarks_website_pages.DashBoardPage;
import com.extramarks_website_pages.ProfilePage;
import com.extramarks_website_utils.Constants;
import com.extramarks_website_utils.DataUtil;
import com.extramarks_website_utils.ExtentManager;

import com.extramarks_website_utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class BuyPackageTest extends BaseTest {

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
		Object[][] data = DataUtil.getData(xls, "BuyPackageTest");
		return data;
	}

	@Test(dataProvider = "getData", priority = 1,testName="Validating Buy Package",description ="Verifying Open Package Page, Buy Package ")
	public void openBuyPackage(Hashtable<String, String> data, Method method) {
		String testMethodName = method.getName();
		
		try {
			SoftAssert sAssert = new SoftAssert();
			Boolean loginResult = defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"),
					data.get("Board"), data.get("Class"));
			BuyPackagePage bp = new BuyPackagePage(driver, test);
			bp.BuyPackage(data.get("Coupon"), data.get("PostalCode"), data.get("Address"));
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Getting Exception :" + e.getMessage());

		}
	}

}
