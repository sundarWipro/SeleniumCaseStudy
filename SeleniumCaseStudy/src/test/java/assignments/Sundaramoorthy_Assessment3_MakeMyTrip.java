package assignments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import mainMethods.Utilities;

public class Sundaramoorthy_Assessment3_MakeMyTrip extends Utilities{

	WebDriver driver = null;
	String strReusableVar, strXpath, airwaysName, getFlightNum, getDepartureTime, getArrivalTime, getDepartureCity, getArrivalCity;
	WebElement webElement;
	
	
   @BeforeClass(alwaysRun = true) 
  	public void setUpDriver() { 
	   //Setup Driver driver =
	   driver = setBrowserDriver(); 
    }
 
	@BeforeMethod(alwaysRun = true)
	public void launchURL() {
		//Launch App URL
		launchApplication(driver, "Assignment3_URL");
	}
	
	@AfterClass(alwaysRun = true)
	public void closeBrowser() {
		//Close Browser
		closeBrowser(driver);
	}
	
	@Test(groups= {"makeMyTrip", "assignment_3", "runAllAssignment"}, alwaysRun = true)
    public void makeMyTripBookingAndReviewing() throws InterruptedException {
	    
		//Verify HomePage is Opened
		if(!driver.findElement(By.xpath(getElementLocator("MakeMyTripLogo"))).isDisplayed())
			Assert.fail("MakeMyTrip Home Page is Not Opened");
		//SelecteJourneyDetails
		selecteJourneyDetails();
		//Select Flight
		selectFlight();
		//Window_Handles
		ArrayList<String> Tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(Tabs.get(1));
		//Review_Booking Details
		reviewBookingDetails();
		//Add Traveller Info
		travellerAdOns();
		
		try {
			driver.findElement(By.xpath(getElementLocator("FlightSeatSelect"))).isDisplayed();
			webElement = driver.findElement(By.xpath(getElementLocator("SkipAdOnsMMT")));
			if(webElement.isDisplayed())
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", webElement);
			TimeUnit.SECONDS.sleep(10L);
			driver.findElement(By.xpath(getElementLocator("PaymentPageMMT"))).isDisplayed();
		  } catch (NoSuchElementException e) {
			//Verify Payment Page is Opened
			driver.findElement(By.xpath(getElementLocator("PaymentPageMMT)"))).isDisplayed();
		}
	}

	public void reviewBookingDetails() throws InterruptedException {
		
		TimeUnit.SECONDS.sleep(7L);
		driver.findElement(By.xpath(getElementLocator("ReviewBookingPage"))).isDisplayed();
		strReusableVar = driver.findElement(By.xpath(getElementLocator("ReviewGetAirwayName"))).getText().trim();
		if(!strReusableVar.equals(airwaysName))
			Assert.fail("Airway Name is Showing Incorrectly");
		strReusableVar = driver.findElement(By.xpath(getElementLocator("ReviewGetAirwaysNum"))).getText().trim();
		if(!strReusableVar.equals(getFlightNum))
			Assert.fail("Airway Number is Showing Incorrectly");
		strReusableVar = driver.findElement(By.xpath(getElementLocator("ReviewDepartureTime"))).getText().trim();
		if(!strReusableVar.equals(getDepartureTime))
			Assert.fail("Airway Departure Time is Showing Incorrectly");
		strReusableVar = driver.findElement(By.xpath(getElementLocator("ReviewArrivalTime"))).getText().trim();
		if(!strReusableVar.equals(getArrivalTime))
			Assert.fail("Airway Arrival Time is Showing Incorrectly");
		//Secure Your Trip
		try {
			driver.findElement(By.xpath(getElementLocator("SecureYourTrip"))).click();
		  } catch (NoSuchElementException e) {
			driver.findElement(By.xpath(getElementLocator("SecureYourTripAdOn"))).click();
		}
		TimeUnit.SECONDS.sleep(4L);
		//Click Continue Button
		driver.findElement(By.xpath(getElementLocator("ReviewContinueBtn"))).click();
		TimeUnit.SECONDS.sleep(7L);
	}
	
	public void selectFlight() throws InterruptedException {
		
		TimeUnit.SECONDS.sleep(7L);
		try {
			driver.findElement(By.xpath(getElementLocator("PriceDownArrow"))).isDisplayed();
		  } catch (NoSuchElementException e) {
			 driver.findElement(By.xpath(getElementLocator("PriceUpArrow"))).click();
		}
		//Verify Departure and Arrival City
		getDepartureCity = driver.findElement(By.xpath(getElementLocator("DepartureCity"))).getText().trim();
		if(!getDepartureCity.equals("Mumbai"))
			Assert.fail("Departure City is showing Incorrect");
		getArrivalCity = driver.findElement(By.xpath(getElementLocator("ArrivalCity"))).getText().trim();
		if(!getArrivalCity.equals("Chennai"))
			Assert.fail("Arrival City is showing Incorrect");
		//GetAirways Name
		airwaysName = driver.findElement(By.xpath(getElementLocator("GetAirWaysName"))).getText().trim();
		getFlightNum = driver.findElement(By.xpath(getElementLocator("GetFlightNumber"))).getText().trim();
		getDepartureTime = driver.findElement(By.xpath(getElementLocator("GetDepartureTime"))).getText().trim();
		getArrivalTime = driver.findElement(By.xpath(getElementLocator("GetArrivalTime"))).getText().trim();
		//Click View Fare
		try {
			driver.findElement(By.xpath(getElementLocator("ClickViewFare"))).isDisplayed();
			driver.findElement(By.xpath(getElementLocator("ClickViewFare"))).click();
			driver.findElement(By.xpath(getElementLocator("ClickBookNowMMT"))).click();
		  } catch (NoSuchElementException e) {
			driver.findElement(By.xpath(getElementLocator("ClickBookNowMMT"))).click();
		  }
	}
	
	public void selecteJourneyDetails() throws InterruptedException {
		
		strReusableVar = driver.findElement(By.xpath(getElementLocator("OneWayMMT"))).getAttribute("class");
		//Choose From To Departure Date 
	    if(!strReusableVar.trim().equals("selected"))
	    	driver.findElement(By.xpath(getElementLocator("OneWayMMT"))).click();
	    //Click On From Input Box
	    driver.findElement(By.xpath(getElementLocator("FromInputBox"))).click();
	    driver.findElement(By.xpath(getElementLocator("FromToSuggestion"))).isDisplayed();
	    driver.findElement(By.xpath(getElementLocator("FromInputField"))).sendKeys("Mumbai");
	    driver.findElement(By.xpath(getElementLocator("FromInputField"))).clear();
	    driver.findElement(By.xpath(getElementLocator("FromInputField"))).sendKeys("Mumbai");
	    Thread.sleep(4000);
	    driver.findElement(By.xpath(getElementLocator("FromInputField"))).sendKeys(Keys.ARROW_DOWN);
	    int i=0;
	    strXpath = "//li[@id='react-autowhatever-1-section-0-item-"+i+"']//p[@class='font14 appendBottom5 blackText']";
	    while(driver.findElement(By.xpath(strXpath)).isDisplayed()) {
	    	strReusableVar = driver.findElement(By.xpath(strXpath)).getText();
	    	if(strReusableVar.trim().equals("Mumbai, India")) {
	    		driver.findElement(By.xpath(getElementLocator("FromInputField"))).sendKeys(Keys.ENTER);
	    		Thread.sleep(2000);
	    		strReusableVar = driver.findElement(By.xpath(getElementLocator("GetTextSelectedFrom"))).getText();
	    		if(!strReusableVar.trim().equals("BOM, Chhatrapati Shivaji International Airport India"))
	    			Assert.fail("Selected City is Not Displayed in From Field");
	    		break;
	    	}
	    	driver.findElement(By.xpath(getElementLocator("FromInputField"))).sendKeys(Keys.ARROW_DOWN);
	    	Thread.sleep(2000);
	    	i++;
	    	strXpath = "//li[@id='react-autowhatever-1-section-0-item-"+i+"']//p[@class='font14 appendBottom5 blackText']";
	    }
	    
	    //To Field Automatically Opened Verification
	    driver.findElement(By.xpath(getElementLocator("FromToSuggestion"))).isDisplayed();
	    driver.findElement(By.xpath(getElementLocator("ToInputField"))).sendKeys("Chennai");
	    driver.findElement(By.xpath(getElementLocator("ToInputField"))).clear();
	    driver.findElement(By.xpath(getElementLocator("ToInputField"))).sendKeys("Chennai");
	    Thread.sleep(4000);
	    driver.findElement(By.xpath(getElementLocator("ToInputField"))).sendKeys(Keys.ARROW_DOWN);
	    i=0;
	    strXpath = "//li[@id='react-autowhatever-1-section-0-item-"+i+"']//p[@class='font14 appendBottom5 blackText']";
	    while(driver.findElement(By.xpath(strXpath)).isDisplayed()) {
	    	strReusableVar = driver.findElement(By.xpath(strXpath)).getText();
	    	if(strReusableVar.trim().equals("Chennai, India")) {
	    		driver.findElement(By.xpath(getElementLocator("ToInputField"))).sendKeys(Keys.ENTER);
	    		Thread.sleep(2000);
	    		strReusableVar = driver.findElement(By.xpath(getElementLocator("GetTextSelectedTo"))).getText();
	    		if(!strReusableVar.trim().equals("MAA, Chennai International Airport India"))
	    			Assert.fail("Selected City is Not Displayed in From Field");
	    		break;
	    	}
	    	driver.findElement(By.xpath(getElementLocator("ToInputField"))).sendKeys(Keys.ARROW_DOWN);
	    	Thread.sleep(2000);
	    	i++;
	    	strXpath = "//li[@id='react-autowhatever-1-section-0-item-"+i+"']//p[@class='font14 appendBottom5 blackText']";
	    }
	    
	    driver.findElement(By.xpath(getElementLocator("SelecteDateSugg"))).isDisplayed();
	    //Select Date
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
		Calendar cal = Calendar.getInstance();
		int getDate = Integer.parseInt(dateFormat.format(cal.getTime()).toString());
		int date = getDate + 3;
		strXpath = "//div[@class='DayPicker-Day']/div[@class='dateInnerCell']/p[text()='"+date+"']";
		if(date==31) {
		try {
			driver.findElement(By.xpath(getElementLocator("strXpath"))).isDisplayed();
		  } catch (NoSuchElementException e) {
			  date = 1;
			  strXpath = "//div[@class='DayPicker-Day']/div[@class='dateInnerCell']/p[text()='"+date+"']";
		  }
		}
		driver.findElement(By.xpath(strXpath)).click();
		strXpath = "//p[@data-cy='departureDate']/span[text()='"+date+"']";
		//Verify Date is Selected
		driver.findElement(By.xpath(strXpath)).isDisplayed();
	    //Click Search Button
		driver.findElement(By.xpath(getElementLocator("ClickSearchButton"))).click();
	}
	
	public void travellerAdOns() throws InterruptedException {
		
		driver.findElement(By.xpath(getElementLocator("TravellerAdOnsPage"))).isDisplayed();
		driver.findElement(By.xpath(getElementLocator("ClickAddAdultIcon"))).click();
		driver.findElement(By.xpath(getElementLocator("AddAdultExpand"))).isDisplayed();
		driver.findElement(By.xpath(getElementLocator("AdultFirstName"))).sendKeys(TestData.get("FirstNameMMT"));
		driver.findElement(By.xpath(getElementLocator("AdultSecondName"))).sendKeys(TestData.get("SecondNameMMT"));
		driver.findElement(By.xpath(getElementLocator("SelectMaleMMT"))).click();
		driver.findElement(By.xpath(getElementLocator("MobileNumMMT"))).sendKeys(TestData.get("PhoneNumberMMT"));
		driver.findElement(By.xpath(getElementLocator("EnterEmailMMT"))).sendKeys(getEmailId());
		driver.findElement(By.xpath(getElementLocator("AgreeConditionMMT"))).click();
		TimeUnit.SECONDS.sleep(4L);
		//Click Continue Button
		driver.findElement(By.xpath(getElementLocator("ClkContinueTrvlrBtn"))).click();
		TimeUnit.SECONDS.sleep(7L);
	}

}
