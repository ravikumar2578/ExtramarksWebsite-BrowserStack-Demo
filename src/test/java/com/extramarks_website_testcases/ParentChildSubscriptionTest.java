package com.extramarks_website_testcases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Hashtable;

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

import com.extramarks_website_pages.ChildSubscriptionPage;
import com.extramarks_website_pages.DashBoardPage;
import com.extramarks_website_utils.Constants;
import com.extramarks_website_utils.DataUtil;
import com.extramarks_website_utils.ExtentManager;
import com.extramarks_website_utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;




public class ParentChildSubscriptionTest extends BaseTest {

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
		Object[][] data = DataUtil.getData(xls, "ParentChildSubscriptionTest");
		return data;
	}

	@Test(dataProvider = "getData",priority=1,testName="Validating child Subscription",description="Verifying child has Subscription")
	public void parentChildSubscription(Hashtable<String, String> data, Method method) {
		
		try {
			SoftAssert sAssert = new SoftAssert();
			Boolean loginResult = defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"),
					data.get("Board"), data.get("Class"));
			if (loginResult) {
				Thread.sleep(5000);
				String expectedResult = "ParentChildSubscriptionTest_PASS";
				String actualResult = "";
				DashBoardPage dp = new DashBoardPage(driver, test);

				Object resultPage = dp.openChildSubs();
				if (resultPage instanceof ChildSubscriptionPage) {
					test.log(LogStatus.INFO, "ChildSubscriptionPage opens");
					actualResult = "ParentChildSubscriptionTest_PASS";
					System.out.println("ChildSubscriptionPage opens");
					ChildSubscriptionPage csp = (ChildSubscriptionPage) resultPage;
					csp.openProgSubs();
				} else {
					actualResult = "ParentChildSubscriptionTest_Child_SubscriptionPage_not_Open_Fail";
					dp.takeScreenShot();
					test.log(LogStatus.INFO, "ChildSubscriptionPage not open");
					System.out.println("ChildSubscriptionPage not opens");
				}
				if (!expectedResult.equals(actualResult)) {
					// take screenshot
					dp.takeScreenShot();
					test.log(LogStatus.FAIL, "Got actual result as " + actualResult);
					Assert.fail("Got actual result as " + actualResult);
				} else {
					test.log(LogStatus.PASS, "ChildSubscription Test passed");
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