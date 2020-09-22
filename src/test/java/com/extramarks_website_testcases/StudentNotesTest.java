package com.extramarks_website_testcases;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
import com.extramarks_website_pages.NotesPage;
import com.extramarks_website_utils.Constants;
import com.extramarks_website_utils.DataUtil;
import com.extramarks_website_utils.ExtentManager;

import com.extramarks_website_utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;



public class StudentNotesTest extends BaseTest {

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
		Object[][] data = DataUtil.getData(xls, "StudentNotesTest");
		return data;
	}

	@Test(dataProvider = "getData", enabled = true, invocationCount = 1, priority = 1,testName="Validating Add Notes",description="Verifying Student is allowed to send notes")
	public void studentAddNotes(Hashtable<String, String> data, Method method) {
		
		try {
			SoftAssert sAssert = new SoftAssert();
			Boolean loginResult = defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"),
					data.get("Board"), data.get("Class"));
			if (loginResult) {
				DashBoardPage dp = new DashBoardPage(driver, test);
				Thread.sleep(3000);
				// --------change Classs -----------------
				String expectedResult = "AddNotes_PASS";
				String actualResult = "";
				LoginPage lp = new LoginPage(driver, test);
				NotesPage np = new NotesPage(driver, test);
				Object resultPage2 = dp.openNotes();
				if (resultPage2 instanceof NotesPage) {
					test.log(LogStatus.INFO, "Notes Page Opened");
					actualResult = "AddNotes_PASS";
					System.out.println("NotesPage opens");
				} else {
					actualResult = "AddNotes_FAIL";
					lp.takeScreenShot();
					test.log(LogStatus.INFO, "Notes Page not open");
				}
				np.AddNotes(data.get("Title"), data.get("Class"), data.get("Subject"), data.get("Chapter"),
						data.get("Description"));
				Thread.sleep(5000);
				if (np.notesList.size() != 0) {
					List<HashMap<String, String>> notes = new ArrayList<HashMap<String, String>>();
					notes = np.viewNotes(data.get("Title"), data.get("Class"), data.get("Subject"), data.get("Chapter"),
							data.get("Description"));
					if (notes.size() != 0) {
						if (notes.get(0).get("Subject").trim().contains(data.get("Subject"))
								&& notes.get(0).get("Title").trim().contains(data.get("Title"))) {
							actualResult = "AddNotes_PASS";
						} else {
							actualResult = "AddNotes_Records_Not_Matched/Displayed_on_Grid_FAIL, Subject :"+notes.get(0).get("Subject").trim()+" and Title :"+notes.get(0).get("Title").trim();
						}
					} else {
						actualResult = "AddNotes_Records_not_Displayed_on_Grid_FAIL";
					}
				} else {
					actualResult = "AddNotes_Records_Not_Display_on_Grid_FAIL";
				}
				assertEquals(actualResult, expectedResult, "Verifying add notes functionality", sAssert);
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

	@Test(dataProvider = "getData", enabled = true, invocationCount = 1, priority = 2,testName="Validating edit notes",description="Verifying user is allowed to update notes")
	public void studentEditNotes(Hashtable<String, String> data, Method method) {
		
		try {
			SoftAssert sAssert = new SoftAssert();
			Boolean loginResult = defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"),
					data.get("Board"), data.get("Class"));
			if (loginResult) {
				DashBoardPage dp = new DashBoardPage(driver, test);
				Thread.sleep(3000);
				String expectedResult = "EditNotes_PASS";
				String actualResult = "";
				LoginPage lp = new LoginPage(driver, test);
				NotesPage np = new NotesPage(driver, test);
				Object resultPage2 = dp.openNotes();
				if (resultPage2 instanceof NotesPage) {
					test.log(LogStatus.INFO, "Notes Page Opened");
					actualResult = "EditNotes_PASS";
					System.out.println("NotesPage opens");
				} else {
					actualResult = "EditNotes_FAIL";
					lp.takeScreenShot();
					test.log(LogStatus.INFO, "Notes Page not open");
				}

				boolean editResult = np.EditNotes(data.get("Title"), data.get("Class"), data.get("Subject"),
						data.get("Chapter"), data.get("Description"), data.get("EditSubject"), data.get("EditChapter"),
						0);
				Thread.sleep(5000);
				if (editResult) {
					if (np.notesList.size() != 0) {
						List<HashMap<String, String>> notes = new ArrayList<HashMap<String, String>>();

						notes = np.viewNotes(data.get("Title"), data.get("Class"), data.get("Subject"),
								data.get("Chapter"), data.get("Description"));
						Thread.sleep(5000);
						if (notes.size() != 0) {
							if (notes.get(0).get("Subject").trim().contains(data.get("EditSubject"))
									&& notes.get(0).get("Title").trim().contains(data.get("Title"))) {
								actualResult = "EditNotes_PASS";
							} else {
								actualResult = "EditNotes_Records_not_Matched/Displayed_on_Grid_FAIL";
							}
						} else {
							actualResult = "EditNotes_Records_not_Displayed_on_Grid_FAIL";
						}
					} else {
						actualResult = "EditNotes_Records_not_Displayed_on_Grid_FAIL";
					}

					assertEquals(actualResult, expectedResult, "Verifying edit notes functionality", sAssert);
				} else {
					System.out.println("Edit Notes Failed");
					test.log(LogStatus.INFO, "Edit Notes Failed");
					Reporter.log("Edit Notes Failed");
					sAssert.fail("Edit Notes Failed");
				}
			}

			else {
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

	@Test(dataProvider = "getData", enabled = true, invocationCount = 1, priority = 3,testName="Validating delete notes",description="Verifying student is allowed to delete notes")
	public void studentDeleteNotes(Hashtable<String, String> data, Method method) {
		
		try {
			SoftAssert sAssert = new SoftAssert();
			Boolean loginResult = defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"),
					data.get("Board"), data.get("Class"));
			if (loginResult) {
				driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				DashBoardPage dp = new DashBoardPage(driver, test);
				Thread.sleep(3000);
				String expectedResult = "DeleteNotes_PASS";
				String actualResult = "";
				LoginPage lp = new LoginPage(driver, test);
				NotesPage np = new NotesPage(driver, test);
				Object resultPage2 = dp.openNotes();
				if (resultPage2 instanceof NotesPage) {
					test.log(LogStatus.INFO, "Notes Page Opened");
					actualResult = "DeleteNotes_PASS";
					System.out.println("NotesPage opens");
				} else {
					actualResult = "DeleteNotes_Fail";
					lp.takeScreenShot();
					test.log(LogStatus.INFO, "Notes Page not open");
				}

				HashMap<String, String> DeleteNotes = np.DeleteNotes(0, data.get("Title"), data.get("Class"),
						data.get("Subject"), data.get("Chapter"), data.get("Description"));

				List<HashMap<String, String>> notes = new ArrayList<HashMap<String, String>>();
				if (DeleteNotes.size() != 0) {
					if (np.notesList.size() != 0) {
						notes = np.viewNotes(data.get("Title"), data.get("Class"), data.get("Subject"),
								data.get("Chapter"), data.get("Description"));
						if (notes.get(0).get("Title").trim().equalsIgnoreCase(DeleteNotes.get(data.get("Title")))
								&& notes.get(0).get("Subject").equalsIgnoreCase(DeleteNotes.get(data.get("Subject")))) {

							actualResult = "DeleteNotes_Records_not_deleted_FAIL";
						} else {
							actualResult = "DeleteNotes_PASS";
						}
					} else {
						actualResult = "DeleteNotes_PASS";
					}
				} else {

					test.log(LogStatus.INFO, "No Notes found to delete");
					System.out.println("No Notes found to delete");
					Reporter.log("No Notes found to delete");
					actualResult = "DeleteNotes_PASS";

				}

				assertEquals(actualResult, expectedResult, "Verifying delete notes functionality", sAssert);
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

	@Test(dataProvider = "getData", enabled = true, invocationCount = 1, priority = 4,testName="Validating Shatre notes",description="Verifying Student is allowed to share notes")
	public void studentShareNotes(Hashtable<String, String> data, Method method) {
		
		try {
			SoftAssert sAssert = new SoftAssert();
			Boolean loginResult = defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"),
					data.get("Board"), data.get("Class"));
			if (loginResult) {
				driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				DashBoardPage dp = new DashBoardPage(driver, test);
				Thread.sleep(3000);
				String expectedResult = "shareNotes_PASS";
				String actualResult = "";
				LoginPage lp = new LoginPage(driver, test);
				NotesPage np = new NotesPage(driver, test);
				Object resultPage2 = dp.openNotes();
				if (resultPage2 instanceof NotesPage) {
					test.log(LogStatus.INFO, "Notes Page Opened");
					actualResult = "shareNotes_PASS";
					System.out.println("NotesPage opens");
				} else {
					actualResult = "shareNotes_FAIL";
					lp.takeScreenShot();
					test.log(LogStatus.INFO, "Notes Page not open");
				}

				boolean shareNotesFlag = np.shareNotes(0, data.get("shareType"));
				if (shareNotesFlag == true) {
					actualResult = "shareNotes_PASS";
				} else {
					actualResult = "shareNotes_FAIL";
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

	@Test(dataProvider = "getData", enabled = false, invocationCount = 1, priority = 5,testName="VerifyShare Notes")
	public void studentVerifysharedNotes(Hashtable<String, String> data, Method method) {
	
		try {
			SoftAssert sAssert = new SoftAssert();
			Boolean loginResult = defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"),
					data.get("Board"), data.get("Class"));
			if (loginResult) {
				driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
				DashBoardPage dp = new DashBoardPage(driver, test);
				Thread.sleep(3000);
				String expectedResult = "studentVerifysharedNotes_PASS";
				String actualResult = "";
				LoginPage lp = new LoginPage(driver, test);
				NotesPage np = new NotesPage(driver, test);
				Object resultPage2 = dp.openNotes();
				if (resultPage2 instanceof NotesPage) {
					test.log(LogStatus.INFO, "Notes Page Opened");
					actualResult = "studentVerifysharedNotes_PASS";
					System.out.println("NotesPage opens");
				} else {
					actualResult = "studentVerifysharedNotes_FAIL";
					lp.takeScreenShot();
					test.log(LogStatus.INFO, "Notes Page not open");
				}

				Thread.sleep(5000);

				List<HashMap<String, String>> notes = new ArrayList<HashMap<String, String>>();

				notes = np.viewNotesFromOtherUser(data.get("Title"), data.get("Class"), data.get("Subject"),
						data.get("Chapter"), data.get("Description"));
				if (notes.size() != 0) {
					if (notes.get(0).get("Subject").trim().contains(data.get("Subject"))
							&& notes.get(0).get("Title").trim().contains(data.get("Title"))) {
						actualResult = "VerifysharedNotes_PASS";
					} else {
						actualResult = "VerifySharedNotes_Records_Not_Matched/Displayed_on_Grid_FAIL";
					}
				} else {
					actualResult = "VerifySharedNotes_Records_not_Displayed_on_Grid_FAIL";
				}

				assertEquals(actualResult, expectedResult, "Verifying shared notes functionality", sAssert);
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

	@Test(dataProvider = "getData", enabled = false, invocationCount = 1, priority = 6,testName="Validating view lession from notes")
	public void studentViewLesson(Hashtable<String, String> data, Method method) {
	
		try {
			SoftAssert sAssert = new SoftAssert();
			Boolean loginResult = defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"),
					data.get("Board"), data.get("Class"));
			if (loginResult) {
				DashBoardPage dp = new DashBoardPage(driver, test);
				Thread.sleep(3000);
				String expectedResult = "studentViewLesson_PASS";
				String actualResult = "";
				LoginPage lp = new LoginPage(driver, test);
				Object resultPage2 = dp.openNotes();
				if (resultPage2 instanceof NotesPage) {
					test.log(LogStatus.INFO, "Notes Page Opened");
					actualResult = "studentViewLesson_PASS";
					System.out.println("NotesPage opens");
				} else {
					actualResult = "studentViewLesson_FAIL";
					lp.takeScreenShot();
					test.log(LogStatus.INFO, "Notes Page not open");
				}

				NotesPage np = (NotesPage) resultPage2;
				HashMap<String, String> notesData = new HashMap<String, String>();

				Object resultPage3 = np.viewLesson(0, data.get("Title"), data.get("Class"), data.get("Subject"),
						data.get("Chapter"), data.get("Description"));

				// HttpClient httpClient=new HttpClient();
				// Verified by HTTP API

				CloseableHttpClient httpClient = HttpClients.createDefault();
				HttpGet httpGet = new HttpGet("http://www.extramarks.com/weblpt/json/bredcrumbs/2269/0/chapter");
				// httpGet.addHeader("User-Agent", USER_AGENT);
				CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
				// System.out.println("GET Response LogStatus:" +
				// httpResponse.getLogStatusLine().getLogStatusCode());
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(httpResponse.getEntity().getContent()));
				String response2 = httpResponse.getEntity().getContent().toString();
				// HttpResponse response = httpClient.execute(httpGet);
				HttpEntity entity = httpResponse.getEntity();
				String responseString = EntityUtils.toString(entity, "UTF-8");
				String bredcrumbs = "";
				try {
					JSONObject jsonObject = new JSONObject(responseString);
					for (String key : jsonObject.keySet()) {
						// System.out.println(key + "=" + jsonObject.get(key)); // to get the value }

						JSONArray jsonArray = jsonObject.optJSONArray("bredcrumbs");
						if (jsonArray != null) {
							String rack_type = "";
							String rack_name = "";
							for (int i = 1; i < jsonArray.length(); i++) {
								// System.out.println(jsonArray.length());
								JSONObject jsonObjects = jsonArray.optJSONObject(i);
								rack_name = rack_name + "," + jsonObjects.optString("rack_name");
								rack_type = rack_type + "," + jsonObjects.optString("rack_type");
								// System.out.println("Rack Name" + rack_name + " Rack Type : " + rack_type);
							}
							bredcrumbs = rack_name;
							System.out.println("Bredcrumbs" + bredcrumbs);
						}
					}
				} catch (JSONException err) {
					System.out.println("error" + err.getMessage());
				}

				httpClient.close();
				if (resultPage2 instanceof HashMap<?, ?>) {
					if (notesData.get("Subject").equalsIgnoreCase("") || notesData.get("Subject") == null) {
						test.log(LogStatus.PASS, "No nots found on grid to view lesson");
					} else {
						assertContains("", bredcrumbs, "Verifying View Lesson from notes functionality", sAssert);
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