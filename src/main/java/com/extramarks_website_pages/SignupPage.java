package com.extramarks_website_pages;

import java.sql.SQLException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.extramarks_website_utils.DataUtil;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class SignupPage extends BasePage {
	public SignupPage(WebDriver dr, ExtentTest t) throws Exception {
		super(dr, t);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@class='register']")
	WebElement SignUp;

	@FindBy(xpath = "//*[@id='student']/a")
	WebElement student;

	@FindBy(xpath = "//*[@id='stuparent']/a")
	WebElement parent;

	@FindBy(xpath = "//*[@id='stuteacher']/a")
	WebElement mentor;

	@FindBy(xpath = "//*[@id='mobilediv']//div[@title]")
	WebElement country;

	@FindBy(xpath = "//*[@id='mobilediv']//ul/li")
	List<WebElement> countrydropdown;

	@FindBy(id = "y_1")
	WebElement CheckboxYes;

	@FindBy(id = "display_name")
	WebElement Name;

	@FindBy(id = "mobile")
	WebElement MobileNo;


	@FindBy(id = "s2id_city")
	List<WebElement> City;

	@FindBy(id = "s2id_autogen1_search")
	WebElement CitySendkeys;

	@FindBy(xpath = "//*[@id='cityDropdown']//*[@class='select-create-schedule']/select")
	WebElement CityDropDwn;

	@FindBy(xpath = "//*[@class='home_banner modal-open']//ul[@class='select2-results']/li")
	List<WebElement> CityDropDwn2;

	@FindBy(id = "cityArea")
	WebElement Locality;

	@FindBy(xpath = "//button[@name='submit']")
	WebElement Submit;

	@FindBy(xpath = "//a[@id='reg_otp-verify']")
	WebElement VerifyBtn;

	@FindBy(xpath = "//div[@class='pincode-input-container']")
	public WebElement otp;

	@FindBy(xpath = "//*[@id='errorMessage_mobile']")
	public WebElement dupUserError;

	@FindBy(xpath = "//*[@id='otp-verify']//*[@class='pincode-input-container']/input")
	public List<WebElement> otpInput;

	@FindBy(xpath = "//*[@id='reg_otp-verify']")
	public List<WebElement> otpVerify;

	public boolean signup(String name, String mobile, String city, String userType, String code, String locality)
			throws InterruptedException, SQLException, ClassNotFoundException {
		try {
			SignUp.click();
			Thread.sleep(3000);
			WebElement codes = driver.findElement(
					By.xpath("//*[@id='go-to-school-initiative']//*[@class='login-box newlogin mt90']//form/div//a"));
			clickElement(codes,"code");
			Thread.sleep(3000);
			test.log(LogStatus.INFO, "Selecting UserTypes: " + userType);
			if (userType.equals("Student")) {
				student.click();
				Thread.sleep(3000);
				CheckboxYes.click();
			} else if (userType.equals("Parent")) {
				parent.click();
				Thread.sleep(3000);
			} else {
				mentor.click();

				Thread.sleep(3000);
			}

			test.log(LogStatus.INFO, "Entering name: " + name);
			Name.sendKeys(name);

			test.log(LogStatus.INFO, "Selecting Country Code: " + code);
			country.click();

			for (WebElement codes2 : countrydropdown) {
				if (codes2.getAttribute("data-dial-code").equalsIgnoreCase(code)) {
					System.out.println(code);
					codes2.click();
					break;
				}
			}
			test.log(LogStatus.INFO, "Entering mobile no. " +  String.valueOf(DataUtil.generateRandom()));
			MobileNo.sendKeys(String.valueOf(DataUtil.generateRandom()));
			if (City.size() != 0) {
				test.log(LogStatus.INFO, "Selecting City " + city);
				// first way

				// City.get(0).click();
				// CitySendkeys.sendKeys(city);
				// CitySendkeys.sendKeys(Keys.ENTER);

				// second way

				// click(City,0,"City");
				// boolean sele=select(this.CityDropDwn2,"City",city);

				// third way

			//	JavascriptExecutor js = (JavascriptExecutor) driver;
				//js.executeScript("arguments[0].setAttribute('style', 'display:visible')", this.CityDropDwn);
				//Select citySelect = new Select(CityDropDwn);
			//	citySelect.selectByIndex(1);
				selectData(CityDropDwn,"City",1);

			}
			Thread.sleep(5000);
			selectData(Locality,"Locality",1);
			Submit.click();
			Thread.sleep(5000);
			takeScreenShot();
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.visibilityOfAllElements(otpInput));
			return true;
		} catch (Exception e) {
			test.log(LogStatus.INFO, "Sign up Unsuccessful");
			System.out.println("Sign up Unsuccessful");
			Reporter.log("Sign up Unsuccessful");
			return false;
		}

	}
}
