package com.extramarks_website_testcases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Hashtable;

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
import com.extramarks_website_pages.LaunchPage;
import com.extramarks_website_pages.SignupPage;
import com.extramarks_website_utils.Constants;
import com.extramarks_website_utils.DataUtil;
import com.extramarks_website_utils.ExtentManager;
import com.extramarks_website_utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;



public class SignUpTest extends BaseTest {

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
		Object[][] data = DataUtil.getData(xls, "SignUpTest");
		return data;
	}

	@Test(dataProvider = "getData", priority = 1, invocationCount = 1,testName="Validating Sign Up",description="Verifying Sign up functionality")
	public void signUp(Hashtable<String, String> data,Method method) {
		
		try {
			SoftAssert sAssert = new SoftAssert();
			String expectedResult = "Signup_Pass";
			String actualResult = "";
			String browser = data.get("Browser");
			test.log(LogStatus.INFO, "SignUp test started");
			Reporter.log("SignUp test started");

			if (!DataUtil.isTestRunnable(xls, "SignUpTest") || data.get("Runmode").equals("N")) {

				test.log(LogStatus.SKIP, "Skipping the test");

				throw new SkipException("Skipping the test");
			}

			openBrowser(browser);
	
			test.log(LogStatus.INFO, "Browser Opened");
			Reporter.log("Browser Opened");
			LaunchPage launch = new LaunchPage(driver, test);
			// HomePage hp = lp.goToHomePage();
			SignupPage sp = launch.goToDashboardPage();
			test.log(LogStatus.INFO, "Extramarks Website Home Page");
			sp.takeScreenShot();

			test.log(LogStatus.INFO, "Trying to Signup");
			boolean result = sp.signup(data.get("Name"), data.get("Mobile"), data.get("City"), data.get("UserType"),
					data.get("CountryCode"), data.get("Locality"));
			String regUsernameQuery = "SELECT * FROM user where username=" + data.get("Mobile") + "";
			if (result == true) {
				SQLConnector con = new SQLConnector();
				String regUsername = con.connectionsetup(regUsernameQuery, "username");
				System.out.println("Signup - Waiting for OTP Verification");
				test.log(LogStatus.PASS, "Signup - Waiting for OTP Verification");
				sp.takeScreenShot();

				String smsTextquery = "SELECT * FROM t_sms_log where mobile_number='+91-" + data.get("Mobile") + "'";

				String OTPMessage = con.connectionsetup(smsTextquery, "sms_text");
				System.out.println("SMS Text >" + OTPMessage);
				String OTP = OTPMessage.substring(17, 23);
				System.out.println("Required OTP for Registration > " + OTP);
				if (sp.otpInput.size() != 0) {
					for (int i = 0; i < sp.otpInput.size(); i++) {
						char otpchar = OTP.charAt(i);
						String otp = Character.toString(otpchar);
						sp.otpInput.get(i).sendKeys(otp);
						Thread.sleep(2000);
					}
					sp.otpVerify.get(0).click();
					Thread.sleep(2000);
				}

				String title = driver.getTitle();
				System.out.println(title);
				boolean signup = false;
				if (title.equals("Extramarks India")) {
					actualResult = "Signup_Pass";
					int codes = Integer.parseInt(data.get("CountryCode"));
					switch (codes) {
					case 263:
						if (data.get("Mobile").length() > 6 || data.get("Mobile").length() < 9) {
							actualResult = "Signup_fail";
							System.out.println("Incorrect Mobile Number, but User Signed UP");
							test.log(LogStatus.PASS, "Incorrect Mobile Number, but User Signed UP");
							sAssert.fail("Incorrect Mobile Number, but User Signed UP");

						}

						break;
					case 27:
						if (data.get("Mobile").length() > 9 || data.get("Mobile").length() < 9) {
							actualResult = "Signup_fail";
							System.out.println("Incorrect Mobile Number, but User Signed UP");
							test.log(LogStatus.PASS, "Incorrect Mobile Number, but User Signed UP");
							sAssert.fail("Incorrect Mobile Number, but User Signed UP");
						}
						break;

					case 63:
						if (data.get("Mobile").length() > 10 || data.get("Mobile").length() < 14) {
							if (data.get("Mobile").length() > 9 || data.get("Mobile").length() < 9) {
								actualResult = "Signup_fail";
								System.out.println("Incorrect Mobile Number, but User Signed UP");
								test.log(LogStatus.PASS, "Incorrect Mobile Number, but User Signed UP");
								sAssert.fail("Incorrect Mobile Number, but User Signed UP");
							}
						}
						break;
					case 91:
						if (data.get("Mobile").length() > 10 || data.get("Mobile").length() < 10) {
							if (data.get("Mobile").length() > 9 || data.get("Mobile").length() < 9) {
								actualResult = "Signup_fail";
								System.out.println("Incorrect Mobile Number, but User Signed UP");
								test.log(LogStatus.PASS, "Incorrect Mobile Number, but User Signed UP");
								sAssert.fail("Incorrect Mobile Number, but User Signed UP");
							}
						}
					default:
						if (data.get("Mobile").length() > 9 || data.get("Mobile").length() < 9) {
							actualResult = "Signup_fail";
							System.out.println("Incorrect Mobile Number, but User Signed UP");
							test.log(LogStatus.PASS, "Incorrect Mobile Number, but User Signed UP");
							sAssert.fail("Incorrect Mobile Number, but User Signed UP");
						}
					}
				} else {
					actualResult = "Signup_fail_for_Correct Mobile Number";
					if (regUsername.equalsIgnoreCase(data.get("Mobile"))) {
						if (sp.dupUserError.getText().equalsIgnoreCase("Mobile number already registered")) {
							System.out.println("Mobile Number alrady registered");
							test.log(LogStatus.PASS, "Mobile Number alrady registered");
							Reporter.log("Mobile Number alrady registered");
							actualResult = "Signup_Pass";

						} else {
							System.out.println("Mobile Number alrady registered, not getting error message on UI");
							test.log(LogStatus.FAIL,
									"Mobile Number alrady registered, not getting error message on UI");
							sAssert.fail("Mobile Number alrady registered, not getting error message on UI");
							actualResult = "Signup_Fail";
						}
					} else {
						int codes = Integer.parseInt(data.get("CountryCode"));
						switch (codes) {
						case 263:
							if (data.get("Mobile").length() > 6 || data.get("Mobile").length() < 9) {
								if (sp.dupUserError.getText().equalsIgnoreCase("Mobile number entered is incorrect")) {
									System.out.println("Incorrect Mobile Number, On UI- Getting Error Message");
									test.log(LogStatus.PASS, "Incorrect Mobile Number, On UI- Getting Error Message");
									actualResult = "Signup_Pass";
								} else {
									System.out.println("Incorrect Mobile Number, On UI- not Getting Error Mesage ");
									test.log(LogStatus.FAIL,
											"Incorrect Mobile Number, On UI- not Getting Error Mesage ");
									sAssert.fail("Incorrect Mobile Numer, On UI- not Getting Error Mesage ");

								}
							} else {
								sAssert.fail("Unable to register correct Mobile Number, getting error on UI");
								actualResult = "Signup_fail";
							}

							break;
						case 27:
							if (data.get("Mobile").length() > 9 || data.get("Mobile").length() < 9) {
								if (sp.dupUserError.getText().equalsIgnoreCase("Mobile number entered is incorrect")) {
									System.out.println("Incorrect Mobile Number, On UI- Getting Error Message");
									test.log(LogStatus.PASS, "Incorrect Mobile Numer, On UI- Getting Error Message");
									actualResult = "Signup_Pass";
								} else {
									System.out.println("Incorrect Mobile Number, On UI- not Getting Error Mesage ");
									test.log(LogStatus.FAIL,
											"Incorrect Mobile Numer, On UI- not Getting Error Mesage ");
									sAssert.fail("Incorrect Mobile Number, On UI- not Getting Error Mesage ");

								}
							} else {
								sAssert.fail("Unable to register correct Mobile Number, getting error on UI");
								actualResult = "Signup_fail";
							}
							break;

						case 63:
							if (data.get("Mobile").length() > 10 || data.get("Mobile").length() < 14) {
								if (data.get("Mobile").length() > 9 || data.get("Mobile").length() < 9) {
									if (sp.dupUserError.getText()
											.equalsIgnoreCase("Mobile number entered is incorrect")) {
										System.out.println("Incorrect Mobile Number, On UI- Getting Error Message");
										test.log(LogStatus.PASS,
												"Incorrect Mobile Number, On UI- Getting Error Message");
										actualResult = "Signup_Pass";
									} else {
										System.out.println("Incorrect Mobile Number, On UI- not Getting Error Mesage ");
										test.log(LogStatus.FAIL,
												"Incorrect Mobile Numer, On UI- not Getting Error Mesage ");
										sAssert.fail("Incorrect Mobile Number, On UI- not Getting Error Mesage ");

									}
								}
							} else {
								sAssert.fail("Unable to register correct Mobile Number, getting error on UI");
								actualResult = "Signup_fail";
							}
							break;
						case 91:
							if (data.get("Mobile").length() > 10 || data.get("Mobile").length() < 10) {
								if (sp.dupUserError.getText().equalsIgnoreCase("Mobile number entered is incorrect")) {
									System.out.println("Incorrect Mobile Number, On UI- Getting Error Message");
									test.log(LogStatus.PASS, "Incorrect Mobile Number, On UI- Getting Error Message");
									actualResult = "Signup_Pass";
								} else {
									System.out.println("Incorrect Mobile Number, On UI- not Getting Error Mesage ");
									test.log(LogStatus.FAIL,
											"Incorrect Mobile Numer, On UI- not Getting Error Mesage ");
									sAssert.fail("Incorrect Mobile Numer, On UI- not Getting Error Mesage ");
								}
							} else {
								sAssert.fail("Unable to register correct Mobile Number, getting error on UI");
								actualResult = "Signup_fail";
							}
						default:
							if (data.get("Mobile").length() > 9 || data.get("Mobile").length() < 9) {
								if (sp.dupUserError.getText().equalsIgnoreCase("Mobile number entered is incorrect")) {
									System.out.println("Incorrect Mobile Number, On UI- Getting Error Message");
									test.log(LogStatus.PASS, "Incorrect Mobile Number, On UI- Getting Error Message");
									actualResult = "Signup_Pass";
								} else {
									System.out.println("Incorrect Mobile Numer, On UI- not Getting Error Mesage ");
									test.log(LogStatus.FAIL,
											"Incorrect Mobile Number, On UI- not Getting Error Mesage ");
									sAssert.fail("Incorrect Mobile Number, On UI- not Getting Error Mesage ");
									actualResult = "Signup_Fail";

								}
							} else {
								sAssert.fail("Unable to register correct Mobile Number, getting error on UI");
								actualResult = "Signup_fail";
							}
						}
					}

				}
			} else {
				System.out.println("SignUpFail_InputField_Not_Visible");
				test.log(LogStatus.FAIL, "SignUpFail_InputField_Not_Visible");
				sAssert.fail("SignUpFail_InputField_Not_Visible");
				actualResult = "";

			}
			assertEquals(actualResult, expectedResult, "Verifying SignUp Test Functionality", sAssert);
			sp.takeScreenShot();
			sAssert.assertAll();

		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Getting Exception :" + e.getMessage());
		}
	}
}
