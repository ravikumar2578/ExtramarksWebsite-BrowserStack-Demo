package com.extramarks_website_testcases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

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
import com.extramarks_website_pages.Parent_MyChildPage;
import com.extramarks_website_utils.Constants;
import com.extramarks_website_utils.DataUtil;
import com.extramarks_website_utils.ExtentManager;
import com.extramarks_website_utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;



public class ParentDashboardTest extends BaseTest {


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
		Object[][] data = DataUtil.getData(xls, "ParentDashboardTest");
		return data;
	}

	@Test(dataProvider = "getData", priority = 1, enabled = true,testName="Validating Parent dashboard menu")
	public void parentDashboardMenu(Hashtable<String, String> data,Method method) {
	
		try {
			SoftAssert sAssert = new SoftAssert();
			Boolean loginResult = defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"),
					data.get("Board"), data.get("Class"));
			if (loginResult) {
				driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
				driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
				// --------Menu Icon -----------------
				DashBoardPage dp = new DashBoardPage(driver, test);
				int menuCount = dp.menuTab.size();
				test.log(LogStatus.INFO, "Total Menu : " + menuCount);
				System.out.println("Total Menu : " + menuCount);
				if (menuCount != 0) {
					int k = 0;
					for (int i = 0; i < menuCount; i++) {
						// System.out.println(dp.menuTab.get(i).getAttribute("class"));
						if (getAttribute(driver, dp.menuTab, i, "class", "Menu Tab").trim()
								.equalsIgnoreCase("active")) {
							test.log(LogStatus.INFO, "Current/default Menu : " + dp.menuTab.get(i).getText());
							System.out.println("Current/default Menu : " + dp.menuTab.get(i).getText());
							k = i;
						}
					}
					for (int i = 0; i < 1; i++) {
						// click(dp.menuTab,i,"Menu Tab");
						Thread.sleep(5000);
						String menu = dp.menuTab();
						String[] menuData = menu.split(">>");
						if (dp.menuTab.get(k).getText().trim().equalsIgnoreCase("Student")) {
							try {
								if (dp.menuLinks.size() != 0) {
									for (String response : menuData[0].split(",")) {
										assertEquals(response, "OK",
												"Verifying Menu Icon for Link : " + data.get("Student"), sAssert);
									}

									assertEquals(menuData[1], data.get("Student"), " Verifying Menu Link Name ",
											sAssert);
								}

							} catch (Exception e) {

							}
						}
						if (dp.menuTab.get(k).getText().trim().equalsIgnoreCase("Parent")) {
							try {
								if (dp.menuLinks.size() != 0) {
									for (String response : menuData[0].split(",")) {
										assertEquals(response, "OK",
												"Verifying Menu Icon for Link : " + data.get("Parent"), sAssert);
									}

									assertEquals(menuData[1], data.get("Parent"), " Verifying Menu Link Name ",
											sAssert);
								}

							} catch (Exception e) {

							}
						}
						if (dp.menuTab.get(k).getText().trim().equalsIgnoreCase("Mentor")) {
							try {
								if (dp.menuLinks.size() != 0) {
									for (String response : menuData[0].split(",")) {
										assertEquals(response, "OK",
												"Verifying Menu Icon for Link : " + data.get("Mentor"), sAssert);
									}

									assertEquals(menuData[1], data.get("Mentor"), " Verifying Menu Link Name ",
											sAssert);
								}

							} catch (Exception e) {

							}
						}

					}
					// dp.menuTab.get(k).click();
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

	@Test(dataProvider = "getData", priority = 2,testName="Parent dashboard add child")
	public void parentDashboardAddChild(Hashtable<String, String> data,Method method) {
	
		try {
			String expectedResult = "ParentDashboardAddChildTest_PASS";
			String actualResult = "";
			SoftAssert sAssert = new SoftAssert();
			Boolean loginResult = defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"),
					data.get("Board"), data.get("Class"));
			Thread.sleep(5000);
			DashBoardPage dp = new DashBoardPage(driver, test);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.elementToBeClickable(dp.AddChild));
			clickElement(dp.AddChild, "AddChild");
			Parent_MyChildPage pmc = new Parent_MyChildPage(driver, test);
			pmc.AddChild(data.get("ChildEmail"));
			pmc.AddChildNew(data.get("ChildName"), data.get("ChildUserId"), data.get("Password"),
					data.get("SchoolName"));
			try {
				driver.switchTo().alert().accept();
				test.log(LogStatus.INFO, "New Child Added");
				actualResult = "ParentDashboardAddChildTest_PASS";
			} catch (Exception e2) {
				if (pmc.errorAddChild.size() != 0) {
					assertEquals(pmc.errorAddChild.get(0).getText().trim(), "Child username already exist",
							"Verifying Child Already Exsist", sAssert);
					actualResult="ParentDashboardAddChildTest_PASS";
				} else {
					test.log(LogStatus.INFO, "No Message Alert Found for adding child");
					System.out.println("No Message Alert Found for adding child");
					actualResult = "Add_Child_Fail";
				}
			}

			Thread.sleep(5000);
			assertEquals(actualResult, expectedResult, "Verifying Dashboard Page Objects >> Add Child ", sAssert);
			sAssert.assertAll();
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Getting Exception :" + e.getMessage());

		}
	}

	@Test(dataProvider = "getData", priority = 3, enabled = true,testName="View Activity")
	public void parentDashboardViewActivity(Hashtable<String, String> data,Method method) {
		
		try {
			String expectedResult = "DashboardTest_ViewActivity_PASS";
			String actualResult = "";
			SoftAssert sAssert = new SoftAssert();
			Boolean loginResult = defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"),
					data.get("Board"), data.get("Class"));
			Thread.sleep(5000);
			DashBoardPage dp = new DashBoardPage(driver, test);
			// View All my Activity
			Thread.sleep(3000);
			dp.ViewActivities();
			actualResult = "DashboardTest_ViewActivity_PASS";
			Thread.sleep(3000);
			assertEquals(actualResult, expectedResult, "Verifying Dashboard View Activity", sAssert);
			sAssert.assertAll();
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Getting Exception  while Verifying Dashboard View Activity:" + e.getMessage());
		}
	}
}
