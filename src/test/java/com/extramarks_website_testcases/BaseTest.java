package com.extramarks_website_testcases;

import java.io.FileReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import com.extramarks_website_pages.BasePage;
import com.extramarks_website_pages.DashBoardPage;
import com.extramarks_website_pages.HomePage;
import com.extramarks_website_pages.LaunchPage;
import com.extramarks_website_pages.LoginPage;
import com.extramarks_website_utils.Constants;
import com.extramarks_website_utils.DataUtil;
import com.extramarks_website_utils.ExtentManager;
import com.extramarks_website_utils.Xls_Reader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest  extends BrowserStackTestNGTest{
	@BeforeMethod(alwaysRun = true)
	@org.testng.annotations.Parameters(value = {"config", "environment"})
	@SuppressWarnings("unchecked")

	@BeforeSuite()
	public void reportSetup() throws Exception {
		rep = ExtentManager.getInstance();
		parentTest = rep.startTest(this.getClass().getSimpleName());
		parentTest.assignCategory("Regression Testing");
	}

	@BeforeClass()
	public void initClass() throws Exception {
		childTest = rep.startTest(this.getClass().getSimpleName());
		//test.log(LogStatus.INFO, "Total Number of TestCases"+method.getAnnotation(Test.Class));
		parentTest.appendChild(childTest);
	//	driver = openBrowser("Chrome");
	//	LaunchPage launch = new LaunchPage(driver, test);
	//	launch.goToHomePage();

	}

	@AfterClass
	public void logOut() throws Exception {
		rep.endTest(childTest);
		rep.flush();
		DashBoardPage dp = new DashBoardPage(driver, test);
		dp.logout();
		if (driver != null) {
			driver.quit();
			driver = null;
		}
		
	}

	@AfterSuite
	public void tearDown() {
		rep.endTest(parentTest);
		rep.flush();
		if (driver != null) {
			driver.quit();
			driver = null;
		}

	}

	public static ExtentReports rep;

	public static ExtentTest test;
	public static ExtentTest parentTest;
	public static ExtentTest childTest;

	/* public static WebDriver driver; */
	public static Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);
	public static ThreadLocal<WebDriver> tdriver = new ThreadLocal<WebDriver>();
	
	
	  @BeforeMethod(alwaysRun = true)
	  
	  @org.testng.annotations.Parameters(value = {"config", "environment"})
	  
	  @SuppressWarnings("unchecked")
	 
	public void setUp(String config_file, String environment) throws Exception {

	
	  JSONParser parser = new JSONParser(); JSONObject config = (JSONObject) parser
	  .parse(new FileReader("E:\\Em Website Gts\\extramarks-website\\" +
	  config_file)); JSONObject envs = (JSONObject) config.get("environments");
	  
	  DesiredCapabilities capabilities = new DesiredCapabilities();
	  
	  capabilities.setCapability("browserstack.debug", "true");
	  
	  Map<String, String> envCapabilities = (Map<String, String>)
	  envs.get(environment); Iterator it = envCapabilities.entrySet().iterator();
	  while (it.hasNext()) { Map.Entry pair = (Map.Entry) it.next();
	  capabilities.setCapability(pair.getKey().toString(),
	 pair.getValue().toString()); }
	 
	  Map<String, String> commonCapabilities = (Map<String, String>)
	  config.get("capabilities"); it = commonCapabilities.entrySet().iterator();
	 while (it.hasNext()) { Map.Entry pair = (Map.Entry) it.next(); if
	 (capabilities.getCapability(pair.getKey().toString()) == null) {
	  capabilities.setCapability(pair.getKey().toString(),
	  pair.getValue().toString()); } }
	  
	 String username = System.getenv("BROWSERSTACK_USERNAME"); if (username ==
	  null) { username = (String) config.get("user"); }
	  
	  String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY"); if (accessKey ==
	 null) { accessKey = (String) config.get("key"); }
	 
	  if (capabilities.getCapability("browserstack.local") != null &&
	 capabilities.getCapability("browserstack.local") == "true") {
	  
	  Map<String, String> options = new HashMap<String, String>();
	  options.put("key", accessKey);
	 
	  }
	  
	  driver = new RemoteWebDriver( new URL("https://" + username + ":" + accessKey
	  + "@" + config.get("server") + "/wd/hub"), capabilities);
	 
	  }
	 
	@AfterMethod(alwaysRun = true)
	public void tearDown2() throws Exception {
		driver.quit();

	}
	public static synchronized WebDriver getDriver() {
		return tdriver.get();
	}

	public WebDriver initialize_driver() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		tdriver.set(driver);
		return getDriver();
	}

	public WebDriver openBrowser(String browser) {
		try {
			// normal machine
			// if (driver == null) {
			if (browser.equals("Mozilla")) {
				driver = new FirefoxDriver();
			} else if (browser.equals("Chrome")) {
				System.setProperty("webdriver.chrome.driver", Constants.CHROME_PATH);
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--disable-popup-blocking");
				options.addArguments("--disable-web-security");
				options.addArguments("--allow-running-insecure-content");
				Map<String, Object> prefs = new HashMap<>();
				// Enable Flash
				prefs.put("profile.default_content_setting_values.plugins", 1);
				prefs.put("profile.content_settings.plugin_whitelist.adobe-flash-player", 1);
				prefs.put("profile.content_settings.exceptions.plugins.*,*.per_resource.adobe-flash-player", 1);
				prefs.put("profile.default_content_setting_values.notifications", 1);
				options.setExperimentalOption("prefs", prefs);
				// Headless driver -- comment it , if not required
				// options.addArguments("headless");
				// Maximize windows scrolling to mentioned dimention - - Coment
				// it , if not required.

				options.addArguments("window-size=1366x768");
				// options.addArguments("incognito");
				// capabilities.setCapability("download.prompt_for_download",
				// true);
				driver = new ChromeDriver(options);
				System.out.println("Opening Browser");
				Reporter.log("Opening Browser");
				// test.log(LogStatus.INFO, "Opening Browser");
				String browser_version = null;
				Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
				String browserName = cap.getBrowserName();
				String browserVersion = cap.getVersion();
				System.out.println("Browser name : " + browserName + " and version: " + browserVersion);
				Reporter.log("Browser name : " + browserName + " and version: " + browserVersion);
				// test.log(LogStatus.INFO, "Browser name : " + browserName + "
				// and version: " +
				// browserVersion);
				driver.manage().window().maximize();
				driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				driver.manage().timeouts().setScriptTimeout(400, TimeUnit.MILLISECONDS);

			} else if (browser.equals("ie")) {
				// System.setProperty("webdriver.ie.driver",
				// Constants.IEDRIVER_PATH);
				driver = new InternetExplorerDriver();
			}
			// driver.manage().window().maximize(); -------This is not working
			// when
			// executing with Jenkins
			// Dimension d = new Dimension(1382, 744);// --------------working
			// with jenkins
			// Resize the current window to the given dimension
			// driver.manage().window().setSize(d);
			// tdriver.set(driver);
			// }
		} catch (Exception e) {
			System.out.println("Unable to Open Browser");
			Reporter.log("Unable to Open Browser");
			test.log(LogStatus.FAIL, "Unable to Open Browser");
			Assert.fail("Unable to Open Browser");
		}
		tdriver.set(driver);
		return getDriver();
	}

	public Object defaultLogin(Hashtable<String, String> data) {
		try {
			if(BasePage.isLoggedIn=true){
				DashBoardPage dp=new DashBoardPage(driver,test);
				dp.logout();
			}
			HomePage hp = new HomePage(driver, test);
			Object loginResultPage = null;
			String userName = "";
			if (data.get("email") == null || data.get("email") == "") {
				if (data.get("mobile") == null || data.get("mobile") == "") {
					test.log(LogStatus.FAIL, "Data is blank !");
					// sAssert.fail("Data is blank !");
				} else {
					if (data.get("mobile").length() != 10) {
						test.log(LogStatus.FAIL, "Mobile Number is Invalid !");
						// sAssert.fail("Mobile Number is Invalid !");
					} else {
						Object clickSignObject = hp.clickSignIn();
						if (clickSignObject instanceof LoginPage) {
							LoginPage lp = (LoginPage) clickSignObject;
							WebElement code = driver.findElement(By.xpath(
									"//*[@id='go-to-school-initiative']//*[@class='login-box newlogin mt90']//form/div//a"));
							clickElement(code, "code");
							Thread.sleep(1000);
							loginResultPage = lp.doLogin(data.get("mobile"), "123456");
						}
					}
				}
			} else {
				if (DataUtil.isValidEmail(data.get("email"))) {
					Object clickSignObject = hp.clickSignIn();
					if (clickSignObject instanceof LoginPage) {
						LoginPage lp = (LoginPage) clickSignObject;
						Thread.sleep(1000);
						WebElement code = driver.findElement(By.xpath(
								"//*[@id='go-to-school-initiative']//*[@class='login-box newlogin mt90']//form/div//a"));
						clickElement(code, "code");
						Thread.sleep(1000);
						loginResultPage = lp.doLogin(data.get("email"), "123456");
					}
				} else {
					test.log(LogStatus.FAIL, "Email is Invalid !");
					// sAssert.fail("Email is Invalid !");
				}
			}
			BasePage.isLoggedIn = true;
			return loginResultPage;
		} catch (Exception e) {
			return this;
		}

	}

	public boolean defaultLogin(String browser, String userName, String password, String Board, String Class)
			throws Exception {
		try {
			LoginPage lp = new LoginPage(driver, test);
			Thread.sleep(5000);
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//li/a[@class='signin']")));
			WebElement signin = driver
					.findElement(By.xpath("//*[@id='navbar']//*[@id='navigation-top']//li/a[@class='signin']"));
			clickElement(signin, "Signin");
			Thread.sleep(1000);
			WebElement code = driver.findElement(
					By.xpath("//*[@id='go-to-school-initiative']//*[@class='login-box newlogin mt90']//form/div//a"));
			clickElement(code, "code");
			Thread.sleep(1000);
			Object resultPage = lp.doLogin(userName, password);
			if (resultPage instanceof DashBoardPage) {
				BasePage.isLoggedIn = true;
				DashBoardPage dp = (DashBoardPage) resultPage;
				Object resultPage2 = dp.changeClass(Board, Class);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			test.log(LogStatus.INFO, "Error on login page " + message(e));
			System.out.println("Error on login page " + message(e));
			Reporter.log("Error on login page " + message(e));
			return false;
		}
	}

	public boolean defaultLogin(String userName,String password) throws Exception {
		try {
			LoginPage lp = new LoginPage(driver, test);
			Thread.sleep(5000);
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//li/a[@class='signin']")));
			WebElement signin = driver
					.findElement(By.xpath("//*[@id='navbar']//*[@id='navigation-top']//li/a[@class='signin']"));
			clickElement(signin, "Signin");
			Thread.sleep(1000);
			WebElement code = driver.findElement(
					By.xpath("//*[@id='go-to-school-initiative']//*[@class='login-box newlogin mt90']//form/div//a"));
			clickElement(code, "code");
			Thread.sleep(1000);
			Object resultPage = lp.doLogin(userName, password);
			if (resultPage instanceof DashBoardPage) {
				BasePage.isLoggedIn = true;
				DashBoardPage dp = (DashBoardPage) resultPage;
				Object resultPage2 = dp.changeClass(Constants.BOARD, Constants.CLASS);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			test.log(LogStatus.INFO, "Error on login page " + message(e));
			System.out.println("Error on login page " + message(e));
			Reporter.log("Error on login page " + message(e));
			throw new Exception(e.getMessage());
		}
	}

	public boolean clickElement(WebElement element, String name) {
		boolean isclickElement = false;
		int time = 30;
		try {
			WebDriverWait wt = new WebDriverWait(driver, time);
			wt.until(ExpectedConditions.elementToBeClickable(element));
			Actions ac = new Actions(driver);
			ac.moveToElement(element).build().perform();
			element.click();
			isclickElement = true;
		} catch (TimeoutException e) {
			try {
				Actions ac = new Actions(driver);
				ac.moveToElement(element).build().perform();
				element.click();
			} catch (StaleElementReferenceException e2) {
				for (int i = 0; i <= 2; i++) {
					try {
						Actions ac = new Actions(driver);
						ac.moveToElement(element).build().perform();
						element.click();
						isclickElement = true;
						break;
					} catch (Exception e3) {
						if (i == 2) {
							System.out.println("Element : " + name + " is not attached to DOM");
							test.log(LogStatus.INFO, "Element : " + name + " is not attached to DOM");
						}
					}
				}
			} catch (NoSuchElementException e4) {
				System.out.println("Element : " + name + " not found");
				test.log(LogStatus.INFO, "Element : " + name + " not found");
			} catch (Exception e5) {
				System.out.println("Element : " + name + " not found/interactable");
				test.log(LogStatus.INFO, "Element : " + name + " not found/interactable");
			}

		} catch (NoSuchElementException e6) {
			System.out.println("Element : " + name + " not found");
			test.log(LogStatus.INFO, "Element : " + name + " not found");
		} catch (Exception e7) {
			System.out.println("Element : " + name + " not found/interactable");
			test.log(LogStatus.INFO, "Element : " + name + " not found/interactable");
		}
		return isclickElement;
	}

	public boolean clickElement(By locator, String name) {

		WebElement element = driver.findElement(locator);
		boolean isclickElement = false;
		int time = 30;
		try {
			WebDriverWait wt = new WebDriverWait(driver, time);
			wt.until(ExpectedConditions.elementToBeClickable(element));
			Actions ac = new Actions(driver);
			ac.moveToElement(element).build().perform();
			element.click();
			isclickElement = true;
		} catch (TimeoutException e) {
			try {
				Actions ac = new Actions(driver);
				ac.moveToElement(element).build().perform();
				element.click();
			} catch (StaleElementReferenceException e2) {
				for (int i = 0; i <= 2; i++) {
					try {
						Actions ac = new Actions(driver);
						ac.moveToElement(element).build().perform();
						element.click();
						isclickElement = true;
						break;
					} catch (Exception e3) {
						if (i == 2) {
							System.out.println("Element : " + name + " is not attached to DOM");
							test.log(LogStatus.INFO, "Element : " + name + " is not attached to DOM");
						}
					}
				}
			} catch (NoSuchElementException e4) {
				System.out.println("Element : " + name + " not found");
				test.log(LogStatus.INFO, "Element : " + name + " not found");
			} catch (Exception e5) {
				System.out.println("Element : " + name + " not found/interactable");
				test.log(LogStatus.INFO, "Element : " + name + " not found/interactable");
			}

		} catch (NoSuchElementException e5) {
			System.out.println("Element : " + name + " not found");
			test.log(LogStatus.INFO, "Element : " + name + " not found");
		} catch (Exception e6) {
			System.out.println("Element : " + name + " not found/interactable");
			test.log(LogStatus.INFO, "Element : " + name + " not found/interactable");
		}
		return isclickElement;
	}

	public boolean clickElement(By locator, int position, String name) {

		List<WebElement> element = driver.findElements(locator);
		boolean isclickElement = false;
		int time = 30;
		try {
			WebDriverWait wt = new WebDriverWait(driver, time);
			wt.until(ExpectedConditions.elementToBeClickable(element.get(position)));
			Actions ac = new Actions(driver);
			ac.moveToElement(element.get(position)).build().perform();
			element.get(position).click();
			isclickElement = true;
		} catch (TimeoutException e) {
			try {
				Actions ac = new Actions(driver);
				ac.moveToElement(element.get(position)).build().perform();
				element.get(position).click();
			} catch (StaleElementReferenceException e2) {
				for (int i = 0; i <= 2; i++) {
					try {
						element.get(position).click();
						isclickElement = true;
						break;
					} catch (Exception e3) {
						if (i == 2) {
							System.out.println("Element : " + name + " is not attached to DOM");
							test.log(LogStatus.INFO, "Element : " + name + " is not attached to DOM");
						}
					}
				}
			} catch (NoSuchElementException e4) {
				System.out.println("Element : " + name + " not found");
				test.log(LogStatus.INFO, "Element : " + name + " not found");
			} catch (Exception e5) {
				System.out.println("Element : " + name + " not found/interactable");
				test.log(LogStatus.INFO, "Element : " + name + " not found/interactable");
			}

		} catch (NoSuchElementException e5) {
			System.out.println("Element : " + name + " not found");
			test.log(LogStatus.INFO, "Element : " + name + " not found");
		} catch (Exception e6) {
			System.out.println("Element : " + name + " not found/interactable");
			test.log(LogStatus.INFO, "Element : " + name + " not found/interactable");
		}
		return isclickElement;
	}

	public boolean clickElement(List<WebElement> element, int position, String name) {
		boolean isclickElement = false;
		int time = 30;
		try {
			WebDriverWait wt = new WebDriverWait(driver, time);
			wt.until(ExpectedConditions.elementToBeClickable(element.get(position)));
			Actions ac = new Actions(driver);
			ac.moveToElement(element.get(position)).build().perform();
			element.get(position).click();
			isclickElement = true;
		} catch (TimeoutException e) {
			try {
				Actions ac = new Actions(driver);
				ac.moveToElement(element.get(position)).build().perform();
				;
				element.get(position).click();
			} catch (StaleElementReferenceException e2) {
				for (int i = 0; i <= 2; i++) {
					try {
						element.get(position).click();
						isclickElement = true;
						break;
					} catch (Exception e3) {
						if (i == 2) {
							System.out.println("Element : " + name + " is not attached to DOM");
							test.log(LogStatus.INFO, "Element : " + name + " is not attached to DOM");
						}
					}
				}
			} catch (NoSuchElementException e4) {
				System.out.println("Element : " + name + " not found");
				test.log(LogStatus.INFO, "Element : " + name + " not found");
			} catch (Exception e5) {
				System.out.println("Element : " + name + " not found/interactable");
				test.log(LogStatus.INFO, "Element : " + name + " not found/interactable");
			}

		} catch (NoSuchElementException e5) {
			System.out.println("Element : " + name + " not found");
			test.log(LogStatus.INFO, "Element : " + name + " not found");
		} catch (Exception e6) {
			System.out.println("Element : " + name + " not found/interactable");
			test.log(LogStatus.INFO, "Element : " + name + " not found/interactable");
		}
		return isclickElement;
	}

	public boolean fluentWaitIsDisplay(final WebElement element, int timout, String name) {
		boolean isdisplay = false;
		try {

			FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver);
			fluentWait.withTimeout(timout, TimeUnit.SECONDS);
			fluentWait.pollingEvery(2, TimeUnit.SECONDS);
			fluentWait.ignoring(NoSuchElementException.class);
			fluentWait.ignoring(TimeoutException.class);
			fluentWait.until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					Actions ac = new Actions(driver);
					ac.moveToElement(element).build().perform();
					WebElement element2 = element;
					return element2.isDisplayed();
				}
			});
			isdisplay = true;
		} catch (Exception e) {
			System.out.println("Element : " + name + " is not found after waiting for time - " + timout);
			test.log(LogStatus.INFO, "Element : " + name + " is not found after waiting for time - " + timout);
			Reporter.log("Element : " + name + " is not found after waiting for time - " + timout);
		}
		return isdisplay;
	}

	public boolean fluentWaitIsDisplay(final List<WebElement> element, final int position, int timout, String name) {
		boolean isdisplay = false;

		try {

			FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver);
			fluentWait.withTimeout(timout, TimeUnit.SECONDS);
			fluentWait.pollingEvery(2, TimeUnit.SECONDS);
			fluentWait.ignoring(NoSuchElementException.class);
			fluentWait.ignoring(TimeoutException.class);
			fluentWait.until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					Actions ac = new Actions(driver);
					ac.moveToElement(element.get(position)).build().perform();
					WebElement element2 = element.get(position);
					return element2.isDisplayed();
				}
			});
			isdisplay = true;
		} catch (Exception e) {
			System.out.println("Element : " + name + " is not found after waiting for time - " + timout);
			test.log(LogStatus.FAIL, "Element : " + name + " is not found after waiting for time - " + timout);
			Reporter.log("Element : " + name + " is not found after waiting for time - " + timout);
		}
		return isdisplay;
	}

	public boolean fluentWaitIsDisplay(final By locator, int timout, String name) {
		boolean isdisplay = false;
		try {

			FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver);
			fluentWait.withTimeout(timout, TimeUnit.SECONDS);
			fluentWait.pollingEvery(2, TimeUnit.SECONDS);
			fluentWait.ignoring(NoSuchElementException.class);
			fluentWait.ignoring(TimeoutException.class);
			fluentWait.until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					Actions ac = new Actions(driver);
					ac.moveToElement(driver.findElement(locator)).build().perform();
					return driver.findElement(locator).isDisplayed();
				}
			});
			isdisplay = true;
		} catch (Exception e) {
			System.out.println("Element : " + name + " is not found after waiting for time - " + timout);
			test.log(LogStatus.INFO, "Element : " + name + " is not found after waiting for time - " + timout);
			Reporter.log("Element : " + name + " is not found after waiting for time - " + timout);

		}
		return isdisplay;
	}

	public boolean fluentWaitIsDisplay(final By locator, final int position, int timout, String name) {
		boolean isdisplay = false;
		try {

			FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver);
			fluentWait.withTimeout(timout, TimeUnit.SECONDS);
			fluentWait.pollingEvery(2, TimeUnit.SECONDS);
			fluentWait.ignoring(NoSuchElementException.class);
			fluentWait.ignoring(TimeoutException.class);
			fluentWait.until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					Actions ac = new Actions(driver);
					ac.moveToElement(driver.findElements(locator).get(position)).build().perform();
					return driver.findElements(locator).get(position).isDisplayed();
				}
			});
			isdisplay = true;
		} catch (Exception e) {
			System.out.println("Element : " + name + " is not found after waiting for time - " + timout);
			test.log(LogStatus.INFO, "Element : " + name + " is not found after waiting for time - " + timout);
			Reporter.log("Element : " + name + " is not found after waiting for time - " + timout);
		}
		return isdisplay;
	}

	public String getAttribute(WebDriver driver, List<WebElement> element, int position, String attributeName,
			String name) {
		String values = "";

		try {
			Thread.sleep(3000);
			values = element.get(position).getAttribute(attributeName);
		} catch (NoSuchElementException e) {
			System.out.println("Element : " + name + " not found");
		} catch (TimeoutException e) {
			System.out.println("Element : " + name + " TimedOut");
		} catch (StaleElementReferenceException e) {
			for (int i = 0; i <= 2; i++) {
				try {
					values = element.get(position).getAttribute(attributeName);
					break;
				} catch (Exception e2) {

				}
			}
			System.out.println("Element : " + name + " is not attached to DOM");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return values;
	}

	public String getAttribute(WebDriver driver, By locator, int position, String attributeName, String name) {
		String values = "";

		try {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<WebElement> element = driver.findElements(locator);
			values = element.get(position).getAttribute(attributeName);
		} catch (NoSuchElementException e) {
			System.out.println("Element : " + name + " not found");
		} catch (TimeoutException e) {
			System.out.println("Element : " + name + " TimedOut");
		} catch (StaleElementReferenceException e) {
			for (int i = 0; i <= 2; i++) {
				try {
					List<WebElement> element = driver.findElements(locator);
					values = element.get(position).getAttribute(attributeName);
					break;
				} catch (Exception e2) {

				}
			}
			System.out.println("Element : " + name + " is not attached to DOM");
		}
		return values;
	}

	public String clickElementAndHoldMoveByOffset(WebDriver driver, By locator, int position, int height, int width,
			String name) {
		String values = "";

		try {
			List<WebElement> element = driver.findElements(locator);
			Actions ac = new Actions(driver);
			ac.clickAndHold(element.get(position)).moveByOffset(height, width).release().build().perform();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoSuchElementException e) {
			System.out.println("Element : " + name + " not found");
		} catch (TimeoutException e) {
			System.out.println("Element : " + name + " TimedOut");
		} catch (StaleElementReferenceException e) {
			for (int i = 0; i <= 2; i++) {
				try {
					List<WebElement> element = driver.findElements(locator);
					Actions ac = new Actions(driver);
					ac.clickAndHold(element.get(position)).moveByOffset(height, width).release().build().perform();
					Thread.sleep(2000);
					break;
				} catch (Exception e2) {

				}
			}
			System.out.println("Element : " + name + " is not attached to DOM");
		}
		return values;
	}

	public String clickElementAndHoldMoveByOffset(WebDriver driver, By locator, int height, int width, String name) {
		String values = "";

		try {
			WebElement element = driver.findElement(locator);
			Actions ac = new Actions(driver);
			ac.clickAndHold(element).moveByOffset(height, width).release().build().perform();
			Thread.sleep(2000);
		} catch (NoSuchElementException e) {
			System.out.println("Element : " + name + " not found");
		} catch (TimeoutException e) {
			System.out.println("Element : " + name + " TimedOut");
		} catch (StaleElementReferenceException e) {
			for (int i = 0; i <= 2; i++) {
				try {
					WebElement element = driver.findElement(locator);
					Actions ac = new Actions(driver);
					ac.clickAndHold(element).moveByOffset(height, width).release().build().perform();
					Thread.sleep(2000);
					break;
				} catch (Exception e2) {

				}
			}
			System.out.println("Element : " + name + " is not attached to DOM");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return values;
	}

	public String clickElementAndHoldMoveByOffset(WebDriver driver, List<WebElement> element, int position, int height,
			int width, String name) throws InterruptedException {
		String values = "";

		try {
			Actions ac = new Actions(driver);
			ac.clickAndHold(element.get(position)).moveByOffset(height, width).release().build().perform();
			Thread.sleep(2000);
		} catch (NoSuchElementException e) {
			System.out.println("Element : " + name + " not found");
		} catch (TimeoutException e) {
			System.out.println("Element : " + name + " TimedOut");
		} catch (StaleElementReferenceException e) {
			for (int i = 0; i <= 2; i++) {
				try {
					Actions ac = new Actions(driver);
					ac.clickAndHold(element.get(position)).moveByOffset(height, width).release().build().perform();
					Thread.sleep(2000);
					break;
				} catch (Exception e2) {

				}
			}
			System.out.println("Element : " + name + " is not attached to DOM");
		}
		return values;
	}

	
	public void assertEquals(String actual, String expected, String message, SoftAssert sAssert) throws Exception {
		BasePage bp = new BasePage(driver, test);
		if (actual.equals(expected)) {
			sAssert.assertEquals(actual, expected,
					message + " Actual Result : " + actual + " Expected Result : " + expected);
			Reporter.log(message + " Actual Result : " + actual + " Expected Result : " + expected);
			test.log(LogStatus.PASS, message + " Actual Result : " + actual + " Expected Result : " + expected);
		} else {
			sAssert.assertEquals(actual, expected, message);
			test.log(LogStatus.FAIL, message + " Actual Result : " + actual + " Expected Result : " + expected);
			// Reporter.log(message + " Actual Result : " + actual + " Expected
			// Result : " +
			// expected);
		}
		bp.takeScreenShot();
	}

	public void assertEquals(Double actual, Double expected, String message, SoftAssert sAssert) throws Exception {
		BasePage bp = new BasePage(driver, test);
		if (actual.equals(expected)) {
			sAssert.assertEquals(actual, expected,
					message + " Actual Result : " + actual + " Expected Result : " + expected);
			Reporter.log(message + " Actual Result : " + actual + " Expected Result : " + expected);
			test.log(LogStatus.PASS, message + " Actual Result : " + actual + " Expected Result : " + expected);
		} else {
			sAssert.assertEquals(actual, expected, message);
			test.log(LogStatus.FAIL, message + " Actual Result : " + actual + " Expected Result : " + expected);

			// Reporter.log(message+"Actual Result : "+actual+"Expected Result :
			// "+expected);
		}
		bp.takeScreenShot();
	}

	public void assertContains(String actual, String expected, String message, SoftAssert sAssert) throws Exception {
		BasePage bp = new BasePage(driver, test);

		if (actual.contains(expected)) {
			sAssert.assertTrue((actual.contains(expected)),
					message + " Actual Result : " + actual + " Expected Result : " + expected);
			Reporter.log(message + " Actual Result : " + actual + " Expected Result : " + expected);
			test.log(LogStatus.PASS, message + " Actual Result : " + actual + " Expected Result : " + expected);
		} else {
			sAssert.assertTrue((actual.contains(expected)),
					message + " Actual Result : " + actual + " Expected Result : " + expected);
			test.log(LogStatus.FAIL, message + " Actual Result : " + actual + " Expected Result : " + expected);

			// Reporter.log(message+"Actual Result : "+actual+"Expected Result :
			// "+expected);
		}
		bp.takeScreenShot();

	}

	public void LogStatus(ITestResult result) throws Exception {
		// BasePage bp = new BasePage(driver, test);
		try {
			if (result.getStatus() == ITestResult.FAILURE) {
				test.log(LogStatus.FAIL, "Test Case : " + result.getName() + "  is Fail, getting error : "
						+ result.getThrowable().getMessage().substring(0, 210));
				Reporter.log("Test Case : " + result.getName() + " is Fail, getting error : "
						+ result.getThrowable().getMessage().substring(0, 210));
				// bp.takeScreenShot();
				result.setStatus(1);
			} else if (result.getStatus() == ITestResult.SKIP) {
				test.log(LogStatus.SKIP, "Test Case : " + result.getName() + "  is Skip");
				Reporter.log("Test Case : " + result.getName() + "  is Skip");
			} else if (result.getStatus() == ITestResult.SUCCESS) {
				test.log(LogStatus.PASS, "Test Case : " + result.getName() + "  is Pass");
				Reporter.log("Test Case : " + result.getName() + "  is Pass");
			}

		} catch (Exception e) {
			// bp.takeScreenShot();
		}
	}

	public String message(Exception e) {
		if (e.getMessage().contains("no such element")) {
			String[] msg = e.getMessage().split("Element info");
			return "Element is not Found : +msg[1]";
		} else if (e.getMessage().contains("element not interactable")) {
			String[] msg = e.getMessage().split("Element info");
			return "Element is not present in DOM but not interactable at line number : ";
		} else if (e.getMessage().contains("StaleElementReferenceException")) {
			String[] msg = e.getMessage().split("Element info");
			return "Element is present in DOM but It is staled at line number : ";
		} else if (e.getMessage().contains("null")) {
			return "Null pointer Exception at line number : ";
		} else if (e.getMessage().contains("Timed out")) {
			String[] msg = e.getMessage().split("->")[1].split("Build");
			return "Getting Timeout out after waiting for a element : " + msg[0];
		} else if (e.getMessage().contains("NoSuchWindowException")) {
			return "Unable to find the window";
		} else if (e.getMessage().contains("OutOfBounds")) {
			String[] msg = e.getMessage().split("Element info");
			return "Element is not found with given size";
		} else {
			return e.getMessage();
		}
	}

	public boolean feedBackAlert() {
		try {
			WebDriverWait wt = new WebDriverWait(driver, 10);
			wt.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			test.log(LogStatus.INFO, "Found FeedBack Alert : " + alert.getText());
			System.out.println("Found FeedBack Alert : " + alert.getText());
			Reporter.log("Found FeedBack Alert : " + alert.getText());
			alert.accept();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
