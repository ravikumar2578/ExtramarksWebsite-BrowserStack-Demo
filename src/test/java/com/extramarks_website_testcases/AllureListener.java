package com.extramarks_website_testcases;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.LogStatus;

import io.qameta.allure.Attachment;

public class AllureListener extends BaseTest implements ITestListener {

	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}
	
	  @Attachment(value = "Page screenshot", type = "image/png")
	public byte[] saveFailureScreenShot(WebDriver driver) {
		return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
	}
	
	@Attachment(value = "{0}", type = "text/plain")
	public static String saveTextLog(String message) {
		return message;
	}
		
	
	@Override
	public void onStart(ITestContext iTestContext) {
		
		System.out.println("Starting Scenario "
				);
		iTestContext.setAttribute("WebDriver", BaseTest.getDriver());
	}

	@Override
	public void onFinish(ITestContext iTestContext) {
		System.out.println("Ending scenario");
	}

	@Override
	public void onTestStart(ITestResult iTestResult) {
		System.out.println("Starting testcase " + getTestMethodName(iTestResult));
		//test = rep.startTest(
				//method.getAnnotation(Test.class).testName() + " --> " + method.getAnnotation(Test.class).description());
		//parentTest.appendChild(test);
	}

	@Override
	public void onTestSuccess(ITestResult iTestResult) {
		System.out.println("Test  : " + getTestMethodName(iTestResult) + " succeed");
	}

	@Override
	public void onTestFailure(ITestResult iTestResult) {
		System.out.println("Test : " + getTestMethodName(iTestResult) + " failed");
		Object testClass = iTestResult.getInstance();
		WebDriver driver = BaseTest.getDriver();
		// Allure ScreenShot and SaveTestLog
		if (driver instanceof WebDriver) {
			System.out.println("Screenshot captured for :" + getTestMethodName(iTestResult));
		test.log(LogStatus.FAIL, "Test :" + getTestMethodName(iTestResult)+" Failed");
			saveFailureScreenShot(driver);
		}
		saveTextLog(getTestMethodName(iTestResult) + " failed and screenshot taken!");	
	}

	
	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		System.out.println("Test " + getTestMethodName(iTestResult) + " skipped");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
		System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
	}

}
