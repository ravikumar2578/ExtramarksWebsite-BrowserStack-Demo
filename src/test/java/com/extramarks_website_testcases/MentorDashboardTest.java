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
import com.extramarks_website_pages.LoginPage;
import com.extramarks_website_pages.Mentor_SchedulePage;
import com.extramarks_website_pages.Mentor_StudentPage;
import com.extramarks_website_pages.ReportPage;
import com.extramarks_website_pages.StudyPage;
import com.extramarks_website_utils.Constants;
import com.extramarks_website_utils.DataUtil;
import com.extramarks_website_utils.ExtentManager;
import com.extramarks_website_utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;



public class MentorDashboardTest extends BaseTest {

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
		Object[][] data = DataUtil.getData(xls, "MentorDashboardTest");
		return data;
	}

	@Test(dataProvider = "getData", priority = 1, enabled = true,testName="MentorDashboardMenu")
	public void mentorDashboardMenu(Hashtable<String, String> data,Method method) {

		try {
			SoftAssert sAssert = new SoftAssert();
			Boolean loginResult = defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"),
					data.get("Board"), data.get("Class"));
			if (loginResult) {
				driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

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

	@Test(dataProvider = "getData", priority = 2,testName="View Activity")
	public void mentorDashboardViewActivity(Hashtable<String, String> data,Method method) {
	
		try {
			SoftAssert sAssert = new SoftAssert();
			Boolean loginResult = defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"),
					data.get("Board"), data.get("Class"));
			if (loginResult) {
				driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
				driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
				LoginPage lp = new LoginPage(driver, test);
				DashBoardPage dp = new DashBoardPage(driver, test);
				Mentor_StudentPage msp = new Mentor_StudentPage(driver, test);
				Mentor_SchedulePage msd = new Mentor_SchedulePage(driver, test);
				ReportPage rp = new ReportPage(driver, test);

				try {
					dp.ViewActivities();
					Thread.sleep(3000);
				} catch (Exception e) {
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

	@Test(dataProvider = "getData", priority = 3,testName="MentorDashboard")
	public void mentorDashboardaddSchedule(Hashtable<String, String> data,Method method) {

		try {
			SoftAssert sAssert = new SoftAssert();
			Boolean loginResult = defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"),
					data.get("Board"), data.get("Class"));
			if (loginResult) {
				LoginPage lp = new LoginPage(driver, test);
				DashBoardPage dp = new DashBoardPage(driver, test);
				Object changeClassresultPage = null;
				try {
					Object resultPage = dp.changeClass(data.get("Board"), data.get("Class"));
				} catch (Exception e) {

				}
				if (changeClassresultPage instanceof StudyPage) {
					StudyPage sg = (StudyPage) changeClassresultPage;
					System.out.println("Board/Class is selected successfully");
					test.log(LogStatus.INFO, "Board/Class is selected successfully");
				} else {
					System.out.println("Board/Class is not selected");
					test.log(LogStatus.INFO, "Board/Class not is selected");
				}
				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
				driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
				Mentor_StudentPage msp = new Mentor_StudentPage(driver, test);
				Mentor_SchedulePage msd = new Mentor_SchedulePage(driver, test);
				clickElement(dp.Mentor_AddSchedule, "Mentor_AddSchedule");
				Thread.sleep(3000);
				if (msd.studentExist.size() != 0) {
					if (msd.studentExist.get(0).getText().trim().equalsIgnoreCase("No student found!")) {
						System.out.println("No Student Found");
						test.log(LogStatus.INFO, "No Student Found");
						Reporter.log("No Student Found");
					}
				} else {
					clickElement(msd.studentList, 0, "studentList");
					msd.AddSchedule(data.get("Title"), data.get("Class"), data.get("Subject"), data.get("Chapter"));
					int TotalStudents = msp.StudentList.size();
					test.log(LogStatus.INFO, "Total Students= " + TotalStudents);
					System.out.println("Total Students= " + TotalStudents);
					int i = 0;
					msp.StudentList.get(i).click();
					Thread.sleep(2000);
					// SHEDULE NOT WORKING IN TEST-AUTOMATION//
					/*
					 * WebDriverWait wt= new WebDriverWait(driver, 20);
					 * wt.until(ExpectedConditions.elementToBeClickable(rp.Report));
					 * 
					 * rp.viewReport();
					 */

					// driver.navigate().back();
					dp.openStudent();
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
