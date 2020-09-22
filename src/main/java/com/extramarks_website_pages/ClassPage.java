package com.extramarks_website_pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.relevantcodes.extentreports.ExtentTest;



public class ClassPage extends BasePage {

	public ClassPage(WebDriver dr, ExtentTest t) throws Exception {
		super(dr, t);
	}

	public int getTotalSub() {
		List<WebElement> SubjectLinks = driver
				.findElements(By.xpath("//a[contains(@href,'subject')]"));
		int links = SubjectLinks.size();
		// System.out.println("Total Subjects: "+links);
		return links;

	}

	public List<WebElement> getSubjectLinks() {
		List<WebElement> SubjectsLinks = driver
				.findElements(By.xpath("//a[contains(@href,'subject')]"));
		return SubjectsLinks;

	}
	@FindBy(xpath = "//*[@id='lptmaincontroller']//a[@ng-click='goBack()']")
	public List<WebElement> BackToChapter;
}
