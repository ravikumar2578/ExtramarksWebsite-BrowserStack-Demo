package com.extramarks_website_testcases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
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
import com.extramarks_website_pages.Parent_MyChildPage;
import com.extramarks_website_pages.SchedulePage;
import com.extramarks_website_utils.Constants;
import com.extramarks_website_utils.DataUtil;
import com.extramarks_website_utils.ExtentManager;
import com.extramarks_website_utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class ParentMyChildTest extends BaseTest {

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
		Object[][] data = DataUtil.getData(xls, "ParentMyChildTest");
		return data;
	}

	@Test(dataProvider = "getData", priority = 1, enabled = true, testName = "Validating Add Child", description = "Verifying Parent user is allowed to add Child")
	public void parentAddChild(Hashtable<String, String> data, Method method) {

		try {
			SoftAssert sAssert = new SoftAssert();
			Boolean loginResult = defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"),
					data.get("Board"), data.get("Class"));
			if (loginResult) {
				Mentor_StudentPage msp = new Mentor_StudentPage(driver, test);
				Thread.sleep(5000);
				LoginPage lp = new LoginPage(driver, test);

				String expectedResult = "PASS";
				String actualResult = "";

				DashBoardPage dp = new DashBoardPage(driver, test);

				int TotalChild = msp.StudentList.size();
				System.out.println("Total Child =" + TotalChild);

				WebDriverWait wait = new WebDriverWait(driver, 20);
				wait.until(ExpectedConditions.elementToBeClickable(dp.MyChild));
				Object resultPage = dp.openMyChild();

				if (resultPage instanceof Parent_MyChildPage) {
					test.log(LogStatus.INFO, "MyChildPage opens");
					actualResult = "PASS";
					System.out.println("MyChildPage opens");
				} else {
					actualResult = "MyChildPage_not_Opens";
					lp.takeScreenShot();
					test.log(LogStatus.INFO, "MyChildPage not open");
					System.out.println("MyChildPage not opens");
				}
				Parent_MyChildPage pmc = new Parent_MyChildPage(driver, test);
				pmc.AddChild(data.get("ChildEmail"));
				pmc.AddChildNew(data.get("ChildName"), data.get("ChildUserId"), data.get("Password"),
						data.get("SchoolName"));

				if (pmc.errorAddChild.size() != 0) {
					assertEquals(pmc.errorAddChild.get(0).getText().trim(), "Child username already exist",
							"Verifying Child Already Exsist", sAssert);
				} else {
					try {
						driver.switchTo().alert().accept();
						test.log(LogStatus.INFO, "New Child Added");
						actualResult = "DashboardTest_PASS";
					} catch (Exception e2) {
						test.log(LogStatus.INFO, "No Message Alert Found for adding child");
						System.out.println("No Message Alert Found for adding child");
						actualResult = "Add_Child_Fail";
					}
				}
			} else {
				System.out.println("Login Failed");
				test.log(LogStatus.INFO, "Login Failed");
				Reporter.log("Login Failed");
				sAssert.fail("Login Failed");
			}
			Thread.sleep(5000);
			sAssert.assertAll();
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Getting Exception :" + e.getMessage());

		}
	}

	@Test(dataProvider = "getData", priority = 2, enabled = true, testName = "Validating My Child", description = "Verifying Parent user is allowed to add schedule,View Activity,Apply filter on My child page")
	public void parentMyChild(Hashtable<String, String> data, Method method) {

		try {
			SoftAssert sAssert = new SoftAssert();
			Boolean loginResult = defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"),
					data.get("Board"), data.get("Class"));
			if (loginResult) {
				Mentor_StudentPage msp = new Mentor_StudentPage(driver, test);
				Thread.sleep(5000);
				LoginPage lp = new LoginPage(driver, test);
				String expectedResult = "Mychild_PASS";
				String actualResult = "";
				Thread.sleep(3000);
				DashBoardPage dp = new DashBoardPage(driver, test);
				// dp.MyChild.click();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].click();", dp.MyChild);
				dp.takeScreenShot();
				Thread.sleep(5000);
				Parent_MyChildPage pmcp = new Parent_MyChildPage(driver, test);
				int TotalChild = pmcp.childList.size();
				System.out.println("Total Child =" + TotalChild);
				test.log(LogStatus.INFO, "Total Child =" + TotalChild);
				if (TotalChild != 0) {
					for (int i = 0; i < 1; i++) {
						pmcp.childViewProfileBtn.get(i).click();
						HashMap<String, String> profileData = new HashMap<String, String>();
						profileData = pmcp.myChildProfile();
						pmcp.myChildSchedule();
						SchedulePage sp = new SchedulePage(driver, test);
						System.out.println(data.get("Title") + data.get("Class") + data.get("Subject"));
						sp.clickAddSchedule(data.get("Title"), data.get("Class"), data.get("Subject"),
								data.get("Chapter"));
						js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
						Thread.sleep(5000);
						dp.ViewActivities();
						dp.takeScreenShot();
						Thread.sleep(3000);
						js.executeScript("window.scrollTo(0, -document.body.scrollHeight)");
						pmcp.myChildScheduleFilter("Jul 2020", "Test");
						dp.takeScreenShot();
						Thread.sleep(5000);
						driver.navigate().back();
					}
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
