package com.extramarks_website_testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class WebRTC extends BaseTest {

	@Test(invocationCount = 50)
	public void open() throws Exception {
		openBrowser("Chrome");

		driver.get("https://webrtc.extramarks.com/live_classroom/webrtc/join/2");
		//driver.findElement(By.id("joinBtn")).click();
		driver.get("https://webrtc.extramarks.com/live_classroom/webrtc/join/2");
	}

}
