package com.extramarks_website_pages;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.extramarks_website_utils.Constants;
import com.google.common.io.Files;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class BasePage {
	static WebDriver driver;
	static ExtentTest test;
	public static boolean isLoggedIn;
	public BasePage(WebDriver dr, ExtentTest t) throws Exception {
		driver = dr;// single place in framework where driver is init
		test = t; // screenshot
		// defaultRun();

	}

	public static void defaultRun() throws Exception {
		try {

			// boolean alertFeedBack = feedBackPopup();
			boolean loader = loader();
			// boolean goToSchoolPopup = goToSchoolPopup();
		} catch (Exception e) {
			// takeScreenShot();
		}

	}

	public boolean fillData(WebElement element, String name, String text) {
		try {
			Thread.sleep(1000);
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.visibilityOf(element));
			test.log(LogStatus.INFO, "Entering " + name + " ->" + text);
			System.out.println("Entering " + name + " ->" + text);
			Reporter.log("Entering " + name + " ->" + text);
			element.clear();
			Thread.sleep(1000);
			element.sendKeys(text);
			return true;
		} catch (Exception e) {

			test.log(LogStatus.INFO, "Element " + name + " not found, unable to enter " + text);
			System.out.println("Element " + name + " not found, unable to enter " + text);
			Reporter.log("Element " + name + " not found, unable to enter " + text);
			return false;
		}

	}

	public boolean fillData(List<WebElement> element, int position, String name, String text) {
		try {
			Thread.sleep(1000);
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.visibilityOfAllElements(element));
			test.log(LogStatus.INFO, "Entering " + name + " ->" + text);
			System.out.println("Entering " + name + " ->" + text);
			Reporter.log("Entering " + name + " ->" + text);
			element.get(position).clear();
			Thread.sleep(1000);
			element.get(position).sendKeys(text);
			return true;
		} catch (Exception e) {
			test.log(LogStatus.INFO,
					"Element " + name + " not found, at position " + position + " unable to enter " + text);
			System.out.println("Element " + name + " not found, at position " + position + " unable to enter " + text);
			Reporter.log("Element " + name + " not found, at position " + position + " unable to enter " + text);
			return false;
		}

	}

	public boolean fillData(By locator, String name, String text) {
		try {
			Thread.sleep(1000);
			WebElement element = driver.findElement(locator);
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.visibilityOf(element));
			test.log(LogStatus.INFO, "Entering " + name + " ->" + text);
			System.out.println("Entering " + name + " ->" + text);
			Reporter.log("Entering " + name + " ->" + text);
			element.clear();
			Thread.sleep(1000);
			element.sendKeys(text);
			return true;
		} catch (Exception e) {
			test.log(LogStatus.INFO, "Element " + name + " not found, unable to enter " + text);
			System.out.println("Element " + name + " not found, unable to enter " + text);
			Reporter.log("Element " + name + " not found, unable to enter " + text);
			return false;
		}

	}

	public boolean fillData(By locator, int position, String name, String text) {
		try {
			Thread.sleep(1000);
			List<WebElement> element = driver.findElements(locator);
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.visibilityOfAllElements(element));
			test.log(LogStatus.INFO, "Entering " + name + " ->" + text);
			System.out.println("Entering " + name + " ->" + text);
			Reporter.log("Entering " + name + " ->" + text);
			element.get(position).clear();
			Thread.sleep(1000);
			element.get(position).sendKeys(text);
			return true;
		} catch (Exception e) {
			test.log(LogStatus.INFO,
					"Element " + name + " not found, at position " + position + " unable to enter " + text);
			System.out.println("Element " + name + " not found, at position " + position + " unable to enter " + text);
			Reporter.log("Element " + name + " not found, at position " + position + " unable to enter " + text);
			return false;
		}

	}

	public boolean fileUpload(WebElement element, String text) {
		try {
			Thread.sleep(1000);
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.visibilityOf(element));
			test.log(LogStatus.INFO, "Uploading  File ->" + text);
			System.out.println("Uploading File  ->" + text);
			Reporter.log("Uploading File  ->" + text);
			Thread.sleep(1000);
			Actions ac = new Actions(driver);
			ac.moveToElement(element).build().perform();
			element.sendKeys(text);
			return true;
		} catch (Exception e) {

			test.log(LogStatus.INFO, "Element " + "FileUpload" + " not found, unable to upload " + text);
			System.out.println("Element " + "FileUpload" + " not found, unable to upload " + text);
			Reporter.log("Element " + "FileUpload" + " not found, unable to upload " + text);
			return false;
		}

	}

	public static boolean clickElement(WebElement element, String name) {
		boolean isclickElement = false;
		int time = 30;
		try {
			WebDriverWait wt = new WebDriverWait(driver, time);
			wt.until(ExpectedConditions.elementToBeClickable(element));
			Actions ac = new Actions(driver);
			ac.moveToElement(element).build().perform();
			element.click();
			isclickElement = true;
			test.log(LogStatus.INFO, "Clicking on : "+name);
			System.out.println("Clicking on : "+name);
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
						element.sendKeys("");
						element.click();
						isclickElement = true;
						test.log(LogStatus.INFO, "Clicking on : "+name);
						System.out.println("Clicking on : "+name);
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
				element.get(position).sendKeys("");
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

	public boolean selectData(List<WebElement> element, String name, String data, JavascriptExecutor js) {
		try {
			for (WebElement ele : element) {
				// Actions ac=new Actions(driver);
				// ac.clickAndHold(ele).build().perform();
				test.log(LogStatus.INFO, "Selecting Element : " + name + "  for value : " + data);
				Reporter.log("Selecting Element : " + name + "  for value : " + data);
				System.out.println("Selecting Element : " + name + "  for value : " + data);
				if (ele.getText().trim().equalsIgnoreCase(data)) {
					// click(ele,name);
					js.executeScript("arguments[0].click();", ele);
					break;
				}
			}
			return true;
		} catch (Exception e) {
			test.log(LogStatus.INFO, "Unable to select Element : " + name + "  for value : " + data);
			Reporter.log("Unable to select Element : " + name + "  for value : " + data);
			System.out.println("Unable to select Element : " + name + "  for value : " + data);
			return false;
		}

	}

	public boolean selectData(List<WebElement> element, String name) {
		try {
			for (WebElement ele : element) {
				Actions ac = new Actions(driver);
				ac.moveToElement(ele).build().perform();
				;
				clickElement(ele, name);
				break;
			}
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public String selectData(WebElement element, String name, int index) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			if (element.getAttribute("display") != "visible") {
				js.executeScript("arguments[0].setAttribute('style', 'display:visible')", element);
			}
			Select sel = new Select(element);
			sel.selectByIndex(index);
			Thread.sleep(1000);
			String selectedVal = sel.getFirstSelectedOption().getText().trim();
			test.log(LogStatus.INFO, "Selecting Element : " + name + "  at index : " + index);
			Reporter.log("Selecting Element : " + name + "  at index : " + index);
			System.out.println("Selecting Element : " + name + "  at index : " + index);
			return selectedVal;
		} catch (Exception e) {
			test.log(LogStatus.INFO, "Unable to select Element : " + name + "  at index : " + index);
			Reporter.log("Unable to select Element : " + name + "  at index : " + index);
			System.out.println("Unable to select Element : " + name + "  at index : " + index);
			return "";
		}

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

	public static boolean fluentWaitIsDisplay(final List<WebElement> element, final int position, int timout,
			String name) throws Exception {
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
			test.log(LogStatus.INFO, "Element : " + name + " is not found after waiting for time - " + timout);
			Reporter.log("Element : " + name + " is not found after waiting for time - " + timout);
		}
		return isdisplay;
	}

	public static boolean fluentWaitIsDisplay(final By locator, int timout, String name) {
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

	public static boolean fluentWaitIsDisplay(final By locator, final int position, int timout, String name) {
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
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	public String clickAndHoldMoveByOffset(WebDriver driver, By locator, int position, int height, int width,
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

	public String clickAndHoldMoveByOffset(WebDriver driver, By locator, int height, int width, String name) {
		String values = "";

		try {
			WebElement element = driver.findElement(locator);
			Actions ac = new Actions(driver);
			ac.clickAndHold(element).moveByOffset(height, width).release().build().perform();
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
					WebElement element = driver.findElement(locator);
					Actions ac = new Actions(driver);
					ac.clickAndHold(element).moveByOffset(height, width).release().build().perform();
					Thread.sleep(2000);
					break;
				} catch (Exception e2) {

				}
			}
			System.out.println("Element : " + name + " is not attached to DOM");
		}
		return values;
	}

	public String clickAndHoldMoveByOffset(WebDriver driver, List<WebElement> element, int position, int height,
			int width, String name) {
		String values = "";

		try {
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

	public void invisibilityOf(WebDriver driver, By locator, int timeout, String name) {

		try {
			WebDriverWait wt = new WebDriverWait(driver, timeout);
			wt.until(ExpectedConditions.invisibilityOfElementLocated(locator));
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoSuchElementException e) {
			System.out.println("Element : " + name + " not found");
			Reporter.log("Element : " + name + " not found");
		} catch (TimeoutException e) {
			System.out.println("Element : " + name + " TimedOut");
			Reporter.log("Element : " + name + " TimedOut");
		} catch (StaleElementReferenceException e) {
			for (int i = 0; i <= 2; i++) {
				try {
					WebDriverWait wt = new WebDriverWait(driver, timeout);
					wt.until(ExpectedConditions.invisibilityOfElementLocated(locator));
					Thread.sleep(2000);
					break;
				} catch (Exception e2) {

				}
			}
			System.out.println("Element : " + name + " is not attached to DOM");
			Reporter.log("Element : " + name + " is not attached to DOM");
		}
	}

	public static void takeScreenShot() {
		// decide name - time stamp
		Date d = new Date();
		String screenshotFile = d.toString().replace(":", "_").replace(" ", "_") + ".png";
		String path = Constants.SCREENSHOT_PATH;
		File file = new File(path);
		if (!file.exists()) {
			System.out.println("File created " + file);
			file.mkdirs();
		} else {
			// file.delete();
			// file.mkdir();
		}
		// take screenshot
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			// FileUtils.copyFile(srcFile, new File(path));
			Files.copy(srcFile, new File(path + screenshotFile));
			// embed
			test.log(LogStatus.INFO, test.addScreenCapture("Screenshots/" + screenshotFile));

		} catch (IOException e) {
			System.out.println("An exception occured while taking screenshot " + e.getMessage());
		}

	}

	public void takeFullScreenshot() throws IOException {
		Date d = new Date();
		String screenshotFile = d.toString().replace(":", "_").replace(" ", "_") + ".png";
		String path = Constants.SCREENSHOT_PATH + screenshotFile;
		File file = new File(path);
		if (!file.exists()) {
			System.out.println("File created " + file);
			file.mkdirs();
		} else {
			// file.delete();
			// file.mkdir();
		}
		// take screenshot
		Screenshot fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
				.takeScreenshot(driver);
		try {
			ImageIO.write(fpScreenshot.getImage(), "png", new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.log(LogStatus.INFO, test.addScreenCapture("Screenshots/" + screenshotFile));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollTo(0, -document.body.scrollHeight)");
	}

	public void takeContentScreenshot(WebElement eleemnt) throws IOException {
		Date d = new Date();
		String screenshotFile = d.toString().replace(":", "_").replace(" ", "_") + ".png";
		String path = Constants.SCREENSHOT_PATH;
		String contentPath = Constants.CONTENT_SCREENSHOT_PATH;
		File file = new File(contentPath);
		if (!file.exists()) {
			System.out.println("File created " + file);
			file.mkdirs();
		} else {
			// file.delete();
			// file.mkdir();
		}
		// take screenshot
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			// FileUtils.copyFile(srcFile, new File(path));
			Files.copy(srcFile, new File(path + screenshotFile));
			// embed
		} catch (IOException e) {
			System.out.println("An exception occured while taking screenshot " + e.getMessage());
		}
		BufferedImage fullImg = null;
		try {
			fullImg = ImageIO.read(new File(path + screenshotFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Get the location of element on the page
		Point point = eleemnt.getLocation();

		// Get width and height of the element
		int eleWidth = eleemnt.getSize().getWidth();
		int eleHeight = eleemnt.getSize().getHeight();

		// Crop the entire page screenshot to get only element screenshot
		BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);

		try {
			ImageIO.write(eleScreenshot, "png", new File(contentPath + screenshotFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Copy the element screenshot to disk
		test.log(LogStatus.INFO, "");

	}

	public void pause(int i) {
		try {
			Thread.sleep(1000 * i);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static boolean feedBackPopup() {
		try {
			WebDriverWait wt = new WebDriverWait(driver, 10);
			wt.until(ExpectedConditions
					.visibilityOfAllElementsLocatedBy(By.xpath("//iframe[@class='survey-monkey-iframe']")));
			driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@class='survey-monkey-iframe']")));
			Thread.sleep(1000);
			driver.findElement(By.xpath("//*[@id=\"feedback-message\"]//a[@class='btn btn-warning no-thanks']"))
					.click();
			driver.switchTo().defaultContent();
			test.log(LogStatus.INFO, "FeedBack Popup present");
			System.out.println("FeedBack Popup present");
			Reporter.log("FeedBack Popup present");
			takeScreenShot();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean loader() {
		int time = 30;
		try {
			Thread.sleep(1000);
			WebDriverWait wt = new WebDriverWait(driver, time);
			wt.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='preloader']")));
			Thread.sleep(1000);
			return true;
		} catch (Exception e) {
			test.log(LogStatus.INFO, "Loader  is present on page, loader not removed in   " + time + " Second");
			System.out.println("Loader  is present on page, loader not removed in   " + time + " Second");
			Reporter.log("Loader  is present on page, loader not removed in   " + time + " Second");
			takeScreenShot();
			return false;
		}
	}

	public static boolean goToSchoolPopup() {
		try {
			Thread.sleep(1000);
			int framePesent = driver.findElements(By.xpath("//iframe[@id='__ta_notif_frame_1']")).size();
			if (framePesent != 0) {
				driver.switchTo().frame("__ta_notif_frame_1");
				clickElement(driver.findElement(By.xpath("//div[@class='close']")), "GoToschoolPopup");
				driver.switchTo().defaultContent();
			}
			return true;
		} catch (Exception e) {
			test.log(LogStatus.INFO, "GoToschoolPopup is present on page, GoToschoolPopup not removed");
			System.out.println("GoToschoolPopup is present on page, GoToschoolPopup not removed");
			Reporter.log("GoToschoolPopup is present on page, GoToschoolPopup not removed");
			takeScreenShot();
			return false;
		}
	}
}
