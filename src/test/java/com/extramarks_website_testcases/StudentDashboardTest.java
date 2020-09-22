package com.extramarks_website_testcases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

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
import com.extramarks_website_pages.NotesPage;
import com.extramarks_website_pages.SchedulePage;
import com.extramarks_website_pages.StudyPage;
import com.extramarks_website_utils.Constants;
import com.extramarks_website_utils.DataUtil;
import com.extramarks_website_utils.ExtentManager;
import com.extramarks_website_utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;



public class StudentDashboardTest extends BaseTest {

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
		Object[][] data = DataUtil.getData(xls, "StudentDashboardTest");
		return data;
	}

	@Test(dataProvider = "getData", priority = 1, enabled = true, invocationCount = 1,testName="Validating student Dashboard Menu")
	public void studentDashboardMenu(Hashtable<String, String> data,Method method) {
	
		try {
			SoftAssert sAssert = new SoftAssert();
			Boolean loginResult = defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"),
					data.get("Board"), data.get("Class"));
			if (loginResult) {
				
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

	@Test(dataProvider = "getData", invocationCount = 1, enabled = false, priority = 2,testName="ChangeClass")
	public void studentDashboardChangeClass(Hashtable<String, String> data)  {
		try {
			SoftAssert sAssert = new SoftAssert();
			Boolean loginResult = defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"),
					data.get("Board"), data.get("Class"));
			if (loginResult) {
				driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
				DashBoardPage dp = new DashBoardPage(driver, test);
				Thread.sleep(3000);
				// --------change Classs -----------------

				Object resultPage = dp.changeClass(data.get("Board"), data.get("Class"));
				if (resultPage instanceof StudyPage) {
					StudyPage sg = (StudyPage) resultPage;
					System.out.println("Change class Pass");
					test.log(LogStatus.PASS, "Change class Pass");
					sg.takeScreenShot();
					Thread.sleep(3000);
				} else {
					System.out.println("Change class Fail");
					test.log(LogStatus.FAIL, "Change class Fail");
					sAssert.fail("Change class Fail");
					dp.takeScreenShot();
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

	@Test(dataProvider = "getData", invocationCount = 1, enabled = false, priority = 3)
	public void studentDashboardProgressReport(Hashtable<String, String> data,Method method) {
		
		try {
			SoftAssert sAssert = new SoftAssert();
			Boolean loginResult = defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"),
					data.get("Board"), data.get("Class"));
			if (loginResult) {
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
				DashBoardPage dp = new DashBoardPage(driver, test);
				clickElement(dp.Dashboard, 0, "Dashboard");
				// --------Progress Report -----------------
				int progressReportPresent = dp.progressReport.size();
				if (progressReportPresent != 0) {
					dp.pReportSubPoint();
				} else {
					System.out.println("Progress Report not Present");
					test.log(LogStatus.FAIL, "Progress Report not Present");
					dp.takeScreenShot();
				}

				/*
				 * dp.Dashboard.get(0).click();
				 * 
				 * int preparationPresent = dp.preparation.size(); if (preparationPresent != 0)
				 * { dp.preparation(); } else {
				 * System.out.println("Preparation Program not Present");
				 * test.log(LogStatus.FAIL, "Preparation Program not Present");
				 * dp.takeScreenShot(); }
				 */
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

	@Test(dataProvider = "getData", invocationCount = 1, enabled = false, priority = 4)
	public void studentDashboardRecentActivity(Hashtable<String, String> data)  {
		try {
			SoftAssert sAssert = new SoftAssert();
			Boolean loginResult = defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"),
					data.get("Board"), data.get("Class"));
			if (loginResult) {
				driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
				driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
				DashBoardPage dp = new DashBoardPage(driver, test);
				dp.Dashboard.get(0).click();
				driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
				dp.ViewActivities();
				Thread.sleep(4000);
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

	@Test(dataProvider = "getData", invocationCount = 1, enabled = false, priority = 5)
	public void studentDashboardNotes(Hashtable<String, String> data,Method method) {
		
		try {
			SoftAssert sAssert = new SoftAssert();
			Boolean loginResult = defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"),
					data.get("Board"), data.get("Class"));
			if (loginResult) {
				driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
				DashBoardPage dp = new DashBoardPage(driver, test);
				NotesPage np = new NotesPage(driver, test);
				clickElement(np.AddNotes, "Addnotes");

				np.AddNotes(data.get("Title"), data.get("Class"), data.get("Subject"), data.get("Chapter"),
						data.get("Description"));
				Thread.sleep(4000);
				test.log(LogStatus.INFO, "Notes Added");
				System.out.println("Notes Added");
				Reporter.log("Notes Added");
			} else {
				System.out.println("Login Failed");
				test.log(LogStatus.INFO, "Login Failed");
				Reporter.log("Login Failed");
				sAssert.fail("Login Failed");
			}
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Getting Exception :" + e.getMessage());
			
		}
	}

	@Test(dataProvider = "getData", invocationCount = 1, enabled = false, priority = 6)
	public void studentDashboardSchedule(Hashtable<String, String> data,Method method) {
	
		try {
			SoftAssert sAssert = new SoftAssert();
			Boolean loginResult = defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"),
					data.get("Board"), data.get("Class"));
			if (loginResult) {
				driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
				driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
				DashBoardPage dp = new DashBoardPage(driver, test);
				dp.Dashboard.get(0).click();
				driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
				SchedulePage sp = new SchedulePage(driver, test);
				sp.clickAddSchedule(data.get("Title"), data.get("Class"), data.get("Subject"), data.get("Chapter"));
				if (dp.scheduleList.size() != 0) {
					String date = dp.scheduleDate.get(0).getText().trim();
					String ScheduleInfo = dp.scheduleInfo.get(0).getText().trim();
					test.log(LogStatus.INFO,
							"Schedule is available for date : " + date + " and Schedile Deatils are : " + ScheduleInfo);
					System.out.println(
							"Schedule is available for date : " + date + " and Schedile Deatils are : " + ScheduleInfo);
				}
				Thread.sleep(3000);
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