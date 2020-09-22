package com.extramarks_website_pages;

import org.testng.ITestContext;

import com.extramarks_website_utils.Constants;
import com.extramarks_website_utils.ExtentManager;
import com.extramarks_website_utils.Xls_Reader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public interface BaseClass {

	public static Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);

	public static void defineExtent(ITestContext iTestContext) {
		// public ExtentReports rep=null;
		ExtentReports rep = ExtentManager.getInstance();
		ExtentTest parentTest = rep.startTest(iTestContext.getCurrentXmlTest().getClass().getSimpleName());
		parentTest.log(LogStatus.INFO, "Total Number of TestCases : " + iTestContext.getAllTestMethods().length);
		parentTest.assignCategory("Functional Testing");
	}

}
