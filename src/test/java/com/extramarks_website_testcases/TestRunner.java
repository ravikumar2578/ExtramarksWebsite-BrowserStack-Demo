package com.extramarks_website_testcases;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

public class TestRunner {

	public static void main(String[] args) {

		List<String> suites = new ArrayList<String>();
		TestNG testNG = new TestNG();
		testNG.setTestClasses(new Class[] { SignUpTest.class, StudentLoginTest.class, BuyPackageTest.class,
				MentorAssessmentTest.class, MentorDashboardTest.class, MentorStudentTest.class, NotificationTest.class,
				ParentChildSubscriptionTest.class, ParentDashboardTest.class, ParentMyChildTest.class,
				StudentAssessmentTest.class, StudentDashboardTest.class, StudentMentorTest.class,
				StudentNotesTest.class, StudentReportTest.class, StudentScheduleTest.class, ForgotPasswordTest.class,
				SettingTest.class });

		testNG.run();

	}
}
