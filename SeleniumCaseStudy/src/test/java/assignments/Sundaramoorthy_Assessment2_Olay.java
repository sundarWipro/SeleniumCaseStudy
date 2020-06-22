package assignments;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.concurrent.*; 

import bsh.util.Util;
import mainMethods.Utilities;

public class Sundaramoorthy_Assessment2_Olay extends Utilities {

	WebDriver driver = null;
	String strReusableVar, strFileDir, strEmailId, strXpath, phoneNumber;
	Select select;
	WebElement webElement;
	boolean subscribe;
	
	
   @BeforeClass(alwaysRun = true) 
  	public void setUpDriver() { 
	   //Setup Driver driver =
	   driver = setBrowserDriver();
	   //Implicit Wait
	   driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
 
	@AfterClass(alwaysRun = true)
	public void closeBrowser() {
		//Close Browser
		//closeBrowser(driver);
	}
	
	@Test(groups= {"olayWebsite", "assignment_2", "runAllAssignment"}, dataProvider = "getSiteCountry", alwaysRun = true)
    public void olayWebsiteRegistrationLogin(String siteCountry, String siteURL) throws InterruptedException {
		
		//Launch App URL
		launchApplicationOlay(driver, siteURL);
	    //Verify HomePage is Opened
		if(!driver.findElement(By.xpath(getElementLocator("OlayHomePage"))).isDisplayed())
			Assert.fail("Olay Home Page is Not Opened");
		//Accept All Cookies
		if(!siteCountry.equalsIgnoreCase("China")) {
			webElement = driver.findElement(By.xpath(getElementLocator("AcceptAllCookies")));
			if(webElement.isDisplayed())
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", webElement);
		}
	    //Click Register Button
		driver.findElement(By.xpath(getElementLocator("ClickRegisterLink"))).click();
		//Verify Register Page is Opened Or Not
		if(!driver.findElement(By.xpath(getElementLocator("CreateProfileOlay"))).isDisplayed())
			Assert.fail("Olay Register Page is Not Opened");
		//Click on Male Image
		if(siteCountry.equalsIgnoreCase("Germany") || siteCountry.equalsIgnoreCase("Spain"))
			driver.findElement(By.xpath(getElementLocator("ClickMaleRegister"))).click();
		//Enter Email Id
		strEmailId = getEmailId();
		driver.findElement(By.xpath(getElementLocator("EmailFieldOlay"))).sendKeys(strEmailId);
		if(siteCountry.equalsIgnoreCase("UK") || siteCountry.equalsIgnoreCase("Germany")) {
			driver.findElement(By.xpath(getElementLocator("PasswordOlay"))).sendKeys((String)jsonObject.get("PasswordOlay"));
			driver.findElement(By.xpath(getElementLocator("ConfmPasswordOlay"))).sendKeys((String)jsonObject.get("PasswordOlay"));
		}
		else if(siteCountry.equalsIgnoreCase("China") | siteCountry.equalsIgnoreCase("Spain")) {
			driver.findElement(By.xpath(getElementLocator("PasswordOlay"))).sendKeys(TestData.get("OlayPassword"));
			driver.findElement(By.xpath(getElementLocator("ConfmPasswordOlay"))).sendKeys(TestData.get("OlayPassword"));
		}
		if(siteCountry.equalsIgnoreCase("UK") || siteCountry.equalsIgnoreCase("Germany") || siteCountry.equalsIgnoreCase("Spain")) {
			//Select Birth Date
			select = new Select(driver.findElement(By.xpath(getElementLocator("SelectBirthDay"))));
			select.selectByValue((String)jsonObject.get("BirthDayOlay"));
			select = new Select(driver.findElement(By.xpath(getElementLocator("SelectBirthMonth"))));
			select.selectByValue((String)jsonObject.get("BirthMonthOlay"));
			select = new Select(driver.findElement(By.xpath(getElementLocator("SelectBirthYear"))));
			select.selectByValue((String)jsonObject.get("BirthYearOlay"));
		}
		else if(siteCountry.equalsIgnoreCase("China")) {
			//Select Birth Date
			select = new Select(driver.findElement(By.xpath(getElementLocator("SelectBirthMonth"))));
			select.selectByValue(TestData.get("BirthMonthChina"));
			select = new Select(driver.findElement(By.xpath(getElementLocator("SelectBirthYear"))));
			select.selectByValue(TestData.get("BirthYearChina"));
		}
		if(siteCountry.equalsIgnoreCase("China") || siteCountry.equalsIgnoreCase("Germany") || siteCountry.equalsIgnoreCase("Spain")) {
			driver.findElement(By.xpath(getElementLocator("FirstNameOlay"))).sendKeys(TestData.get("FirstNameOlay"));
			driver.findElement(By.xpath(getElementLocator("LastNameOlay"))).sendKeys(TestData.get("LastNameOlay"));
			driver.findElement(By.xpath(getElementLocator("AgreeConditionOlay"))).click();
		}
		if(siteCountry.equalsIgnoreCase("China") || siteCountry.equalsIgnoreCase("Spain")) {
			phoneNumber = String.valueOf(System.currentTimeMillis());
			phoneNumber = "+86" + phoneNumber.substring(0, 8);
			driver.findElement(By.xpath(getElementLocator("MobileInputField"))).sendKeys(phoneNumber);
		}
		if(siteCountry.equalsIgnoreCase("China"))
			driver.findElement(By.xpath(getElementLocator("ChinaAddressInput"))).sendKeys(TestData.get("ChinaAddress"));
		
		if(siteCountry.equalsIgnoreCase("Germany")) {
			//Select Country
			select = new Select(driver.findElement(By.xpath(getElementLocator("SelectCountryGer"))));
			select.selectByValue("DEU");
			driver.findElement(By.xpath(getElementLocator("StreetHomeAddress"))).sendKeys((String)jsonObject.get("StreeAndHouseGer"));
			driver.findElement(By.xpath(getElementLocator("CityInputAddress"))).sendKeys((String)jsonObject.get("LocationGer"));
			driver.findElement(By.xpath(getElementLocator("PostalCodeAddress"))).sendKeys((String)jsonObject.get("PostalCodeGer"));
		}
		//Click Create Account
		driver.findElement(By.xpath(getElementLocator("CreateProfileOlay"))).click();
		if(siteCountry.equalsIgnoreCase("UK"))
			subscriptionPageUK();
		//Password Verification
		invalidPasswordVerification(strEmailId);
		//ForgetPassword
		forgetPassword(strEmailId);
		//SignIn To Application
		if(siteCountry.equalsIgnoreCase("UK") || siteCountry.equalsIgnoreCase("Germany"))
			loginOlay(strEmailId, (String)jsonObject.get("PasswordOlay"));
		else if(siteCountry.equalsIgnoreCase("China") | siteCountry.equalsIgnoreCase("Spain"))
			loginOlay(strEmailId, TestData.get("OlayPassword"));
	}

	public void loginOlay(String emailId, String strPassword) {
		//ClickSignIn
		driver.findElement(By.xpath(getElementLocator("ClickSignInOlaySite"))).click();
		driver.findElement(By.xpath(getElementLocator("SignInEmailOlay"))).sendKeys(emailId);
		driver.findElement(By.xpath(getElementLocator("SignInPasswordOlay"))).sendKeys(strPassword);
		driver.findElement(By.xpath(getElementLocator("ClickSignInOlay"))).click();
		driver.findElement(By.xpath(getElementLocator("SignInSuccessOlay"))).isDisplayed();
	}

	public void subscriptionPageUK() {
		try {
			subscribe = driver.findElement(By.xpath(getElementLocator("SignInPageOlay"))).isDisplayed();
		  } catch (NoSuchElementException e) {
			  subscribe = false;
		  }
		if(!subscribe) {
			driver.findElement(By.xpath(getElementLocator("FirstNameOlay"))).sendKeys((String)jsonObject.get("FirstNameUK"));
			driver.findElement(By.xpath(getElementLocator("LastNameOlay"))).sendKeys((String)jsonObject.get("LastNameUK"));
			driver.findElement(By.xpath(getElementLocator("StreetHomeAddress"))).sendKeys((String)jsonObject.get("StreeAndHouseUK"));
			driver.findElement(By.xpath(getElementLocator("CityInputAddress"))).sendKeys((String)jsonObject.get("CityUK"));
			driver.findElement(By.xpath(getElementLocator("PostalCodeAddress"))).sendKeys((String)jsonObject.get("PostalCodeUK"));
			driver.findElement(By.xpath(getElementLocator("AddToMyProfileUK"))).click();
			//Verify Registration Completed
			driver.findElement(By.xpath(getElementLocator("RegCompletedUK"))).isDisplayed();
		}
	}
	
	public void invalidPasswordVerification(String emailId) {
		//Validate InValid Password Verification
		driver.findElement(By.xpath(getElementLocator("SignInEmailOlay"))).sendKeys(emailId);
		driver.findElement(By.xpath(getElementLocator("SignInPasswordOlay"))).sendKeys("Qwerty@12345pL");
		driver.findElement(By.xpath(getElementLocator("ClickSignInOlay"))).click();
		//InvalidPasswordErrMsg
		driver.findElement(By.xpath(getElementLocator("InvalidPasswordErr"))).isDisplayed();	
	}
	
	public void forgetPassword(String emailId) {
		//Click Forget Password Link
		driver.findElement(By.xpath(getElementLocator("ClickForgetPassword"))).click();
		driver.findElement(By.xpath(getElementLocator("ForgetPasswordPage"))).isDisplayed();
		driver.findElement(By.xpath(getElementLocator("SignInEmailOlay"))).sendKeys(strEmailId);
		driver.findElement(By.xpath(getElementLocator("NextForgetPassword"))).click();
		//PasswordRecoveryMail Validation
		driver.findElement(By.xpath(getElementLocator("RecoveryPasswordMsg"))).isDisplayed();
	}
	
	@DataProvider(name = "getSiteCountry")
	public Object[][] getSiteCountry() {
		return new Object[][]{
			{"UK", "https://www.olay.co.uk/en-gb"},
			{"China", "https://www.olay.com.hk/zh-hk"},
			{"Germany", "https://www.olaz.de/de-de"},
			{"Spain", "https://www.olay.es/es-es"}
		};
	}
	
}