package com.extramarks_website_pages;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.extramarks_website_utils.Constants;
import com.extramarks_website_utils.DataUtil;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver, ExtentTest test) throws Exception {
		super(driver, test);
		PageFactory.initElements(driver, this);
		// defaultRun();
	}

	@FindBy(id = Constants.LOGIN_USERNAME)
	public WebElement usernameField;

	@FindBy(id = Constants.LOGIN_PASSWORD)
	public WebElement passwordField;

	@FindBy(id = Constants.LOGIN_BUTTON)
	public WebElement Submit_button;

	@FindBy(id = "errorusernameLogin")
	public List<WebElement> Error_Username;

	@FindBy(id = "errorpasswdLogin")
	public WebElement Error_Password;

	@FindBy(xpath = "//*[@id='navbar']//ul[@id='navigation-top']//a[@class='pl-user-info dropdown-toggle']")
	public List<WebElement> isLogin;

	@FindBy(xpath = "//*[@id='navigation-top']//a[contains(text(),'LOGOUT')]")
	public List<WebElement> isLoginLogout;

	@FindBy(xpath = "loginFailed")
	public List<WebElement> LoginFailed;

	public Object doLogin(String username, String password) throws Exception {
		try {
			WebDriverWait wt = new WebDriverWait(driver, 15);
			// Entering username
			if (username.contains("@")) {
				fillData(this.usernameField, "UserName", username);
			} else {
				fillData(this.usernameField, "UserName", String.valueOf(Math.round(Double.parseDouble(username))));
			}
			// Entering Password
			if (DataUtil.isChar(password)) {
				fillData(this.passwordField, "Password", password);
			} else {

				fillData(this.passwordField, "Password", String.valueOf(Math.round(Double.parseDouble(password))));
			}

			// Click on sign button
			clickElement(this.Submit_button, "Submit_button");
			Thread.sleep(3000);
			// Waiting for Alert Presence
			try {
				wt.until(ExpectedConditions.alertIsPresent());
				Alert alert = driver.switchTo().alert();
				if (alert.getText().equalsIgnoreCase(
						"Your last session was not logged out properly. With this login we are destroying all your previous login.")) {
					test.log(LogStatus.INFO, "Your last session was not logged out properly");
					System.out.println("Your last session was not logged out properly");
					Reporter.log("Your last session was not logged out properly");
				}
				alert.accept();
			} catch (Exception e) {

			}
			DashBoardPage dashBoardPage = new DashBoardPage(driver, test);
			WebDriverWait wt2 = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.visibilityOfAllElements(dashBoardPage.Dashboard));
			Thread.sleep(2000);
			PageFactory.initElements(driver, dashBoardPage);
			dashBoardPage.takeScreenShot();
			return dashBoardPage;
		} catch (Exception e) {
			this.takeScreenShot();
			return new LoginPage(driver, test);

		}
	}
}
