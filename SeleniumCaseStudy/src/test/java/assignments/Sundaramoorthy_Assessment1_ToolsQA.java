package assignments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import mainMethods.Utilities;

public class Sundaramoorthy_Assessment1_ToolsQA extends Utilities{

	String itemsName = null, menuName = null;
	WebDriver driver = null;
	
	
   @BeforeClass(alwaysRun = true) 
  	public void setUpDriver() { 
	   //Setup Driver driver =
	   driver = setBrowserDriver(); 
    }
 
	@BeforeMethod(alwaysRun = true)
	public void launchURL() {
		//Launch App URL
		launchApplication(driver, "Assignment1_URL");
	}
	
	@AfterClass(alwaysRun = true)
	public void closeBrowser() {
		//Close Browser
		closeBrowser(driver);
	}
	
	@Test(groups= {"selectableItem", "assignment_1", "runAllAssignment"}, alwaysRun = true)
    public void selectableItems() {
	    //Verify HomePage is Opened
		if(!driver.findElement(By.xpath(getElementLocator("ToolsQaHomePage"))).isDisplayed())
			Assert.fail("Tools QA Home Page is Not Opened");
	    //Navigate To Selectable Page
	    driver.findElement(By.xpath(getElementLocator("ClickSelectItem"))).click();
	    //Verify Selectable Item Page is Opened
	    if(!driver.findElement(By.xpath(getElementLocator("SelectItemsHome"))).isDisplayed())
			Assert.fail("Selectable Item Page is Not Opened");
	    //Get Selectable Items Link
	    List<WebElement> itemsLink = driver.findElements(By.xpath(getElementLocator("TotalItemsLink")));
	    for(int i=1;i<=itemsLink.size();i++) {
	    	driver.findElement(By.xpath(getElementLocator("SelectItemsLink")+i+"')]")).click();
	    	itemsName = driver.findElement(By.xpath(getElementLocator("SelectedItemsText"))).getText();
		    itemsNameValidations(i);
		 }
	}

	@Test(groups= {"htmlForm", "assignment_1", "runAllAssignment"}, alwaysRun = true)
    public void htmlContactForm() {
		// TODO Auto-generated method stub
	    //Verify HomePage is Opened
		if(!driver.findElement(By.xpath(getElementLocator("ToolsQaHomePage"))).isDisplayed())
			Assert.fail("Tools QA Home Page is Not Opened");
	    //Navigate To Html Contact Form Page
	    driver.findElement(By.xpath(getElementLocator("ClickHtmlForm"))).click();
	    //Verify Html Contact Form Page is Opened
	    if(!driver.findElement(By.xpath(getElementLocator("HtmlFormHome"))).isDisplayed())
			Assert.fail("Html Contact Form Page is Not Opened");
	    //Navigate To Google Links New Tab
	    String clickLink = Keys.chord(Keys.CONTROL, Keys.ENTER);
	    driver.findElement(By.partialLinkText(getElementLocator("GoogleLinkHtml"))).sendKeys(Keys.CONTROL, Keys.ENTER);
	    driver.findElement(By.partialLinkText(getElementLocator("GoogleLinkHere"))).sendKeys(clickLink);
	    //Close the Tab Opened
	    ArrayList<String> newTabs = new ArrayList<String>(driver.getWindowHandles());
	    driver.switchTo().window(newTabs.get(2));
	    String getTitle = driver.getTitle();
	    if(!getTitle.trim().equals("Google"))
	    	Assert.fail("New Page Title is Not getting as Expected"); 
	    driver.close();
	    driver.switchTo().window(newTabs.get(1)); 
	    getTitle = driver.getTitle();
	    if(!getTitle.trim().equals("Google"))
	    	Assert.fail("New Page Title is Not getting as Expected"); 
	    driver.close();
	    //Navigate Back To Tools QA Page 
	    driver.switchTo().window(newTabs.get(0));
	    //Fill The HTML Form Details
	    driver.findElement(By.cssSelector(getElementLocator("InputFirstName"))).sendKeys(TestData.get("FirstName"));
		driver.findElement(By.cssSelector(getElementLocator("InputLastName"))).sendKeys(TestData.get("LastName"));
		driver.findElement(By.cssSelector(getElementLocator("InputCountry"))).sendKeys(TestData.get("Country"));
		driver.findElement(By.xpath(getElementLocator("InputSubject"))).sendKeys(TestData.get("Subject")); 
		//Click Submit Button
		driver.findElement(By.xpath(getElementLocator("ClickSubmitForm"))).click();
	    if(!driver.findElement(By.xpath(getElementLocator("FormSubmittedPage"))).isDisplayed()) 
		  Assert.fail("Submitted Page is Not Opened");
	}
	
	@Test(groups= {"droppable", "assignment_1", "runAllAssignment"}, alwaysRun = true)
    public void droppable() {
		// TODO Auto-generated method stub
	    //Verify HomePage is Opened
		if(!driver.findElement(By.xpath(getElementLocator("ToolsQaHomePage"))).isDisplayed())
			Assert.fail("Tools QA Home Page is Not Opened");
	    //Navigate To Droppable Page
	    driver.findElement(By.xpath(getElementLocator("ClickDroppable"))).click();
	    //Verify Droppable Page is Opened
	    if(!driver.findElement(By.xpath(getElementLocator("DroppableHome"))).isDisplayed())
			Assert.fail("Droppable Page is Not Opened");
	    //Drop The Source Into Target
	    Actions move = new Actions(driver);
	    WebElement source = driver.findElement(By.xpath(getElementLocator("DroppableSource")));
	    WebElement target = driver.findElement(By.xpath(getElementLocator("DroppableTarget")));
	    move.dragAndDrop(source, target).build().perform();
	    //Verify Source is Dropped Into Target
	    if(!driver.findElement(By.xpath(getElementLocator("DroppableDropped"))).isDisplayed())
			Assert.fail("Source Elemet is Not Dropped Into Target");
	}
	
	@Test(groups= {"datepicker", "assignment_1", "runAllAssignment"}, alwaysRun = true)
    public void datepicker() throws ParseException {
		// TODO Auto-generated method stub
		String strYear, strMonth, xpathDate, selectedDate;
		int appMonthNum, monthNum;
	    //Verify HomePage is Opened
		if(!driver.findElement(By.xpath(getElementLocator("ToolsQaHomePage"))).isDisplayed())
			Assert.fail("Tools QA Home Page is Not Opened");
	    //Navigate To Datepicker Page
	    driver.findElement(By.xpath(getElementLocator("ClickDatepicker"))).click();
	    //Verify Datepicker Page is Opened
	    if(!driver.findElement(By.xpath(getElementLocator("DatepickerHome"))).isDisplayed())
			Assert.fail("Datepicker Page is Not Opened");
	    //Click In The Date Input Field
	    driver.findElement(By.cssSelector(getElementLocator("ClickDateInput"))).click();
	    //Explicit Wait
	    WebDriverWait wait = new WebDriverWait(driver, 5);
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getElementLocator("CalendarModule"))));
	    //Verify DatePicker Module is Opened
	    if(!driver.findElement(By.xpath(getElementLocator("CalendarModule"))).isDisplayed())
			Assert.fail("DatePicker Module is Not Opened");
	    //Select My BirthDay Date
	    strYear = driver.findElement(By.xpath(getElementLocator("GetDatepickerYear"))).getText();
	    while(!strYear.trim().equalsIgnoreCase(TestData.get("BirthYear"))) {
	    	if(Integer.parseInt(strYear)>Integer.parseInt(TestData.get("BirthYear")))
	    		//Previous Year
	    		driver.findElement(By.xpath(getElementLocator("ClickPrevButton"))).click();
	    	else
	    		//Next Year
	    		driver.findElement(By.xpath(getElementLocator("ClickNextButton"))).click();
	    	//Get Year
	    	strYear = driver.findElement(By.xpath(getElementLocator("GetDatepickerYear"))).getText();
	    }
	    Calendar cal = Calendar.getInstance();
	    strMonth = driver.findElement(By.xpath(getElementLocator("GetDatepickerMonth"))).getText();
	    Date appMonth = new SimpleDateFormat("MMMM").parse(strMonth.trim());
		cal.setTime(appMonth);
		appMonthNum = cal.get(Calendar.MONTH);
		Date appMonthTest = new SimpleDateFormat("MMMM").parse(TestData.get("BirthMonth"));
		cal.setTime(appMonthTest);
		monthNum = cal.get(Calendar.MONTH);
		while(monthNum+1 != appMonthNum+1) {
	    	if(monthNum+1 < appMonthNum+1)
	    		//Previous Year
	    		driver.findElement(By.xpath(getElementLocator("ClickPrevButton"))).click();
	    	else
	    		//Next Year
	    		driver.findElement(By.xpath(getElementLocator("ClickNextButton"))).click();
	    	//Get Month
	    	strMonth = driver.findElement(By.xpath(getElementLocator("GetDatepickerMonth"))).getText();
		    appMonth = new SimpleDateFormat("MMMM").parse(strMonth.trim());
			cal.setTime(appMonth);
			appMonthNum = cal.get(Calendar.MONTH);
	    }
		//Select Birth Date
		xpathDate = "//div[@id='ui-datepicker-div']/table[@class='ui-datepicker-calendar']/tbody/tr/td[@data-year='"+TestData.get("BirthYear")+"']/a[text()='"+TestData.get("BirthDay")+"']";
		driver.findElement(By.xpath(xpathDate)).click();
		//Verify Date is Selected Or Not
		//Click In The Date Input Field
	    driver.findElement(By.cssSelector("input#datepicker")).click();
		selectedDate = "//div[@id='ui-datepicker-div']/table[@class='ui-datepicker-calendar']/tbody/tr/td[@data-year='"+TestData.get("BirthYear")+"']/a[@class='ui-state-default ui-state-active' and text()='"+TestData.get("BirthDay")+"']";
		//Verify HomePage is Opened
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getElementLocator("CalendarModule"))));
	    if(!driver.findElement(By.xpath(selectedDate)).isDisplayed())
			Assert.fail("Selected Date is Not Displayed");
	}
	
	@Test(groups= {"selectMenu", "assignment_1", "runAllAssignment"}, alwaysRun = true)
    public void selectMenu() {
		// TODO Auto-generated method stub
		String xpathMenu;
	    //Verify HomePage is Opened
	    if(!driver.findElement(By.xpath(getElementLocator("ToolsQaHomePage"))).isDisplayed())
			Assert.fail("Tools QA Home Page is Not Opened");
	    //Navigate To selectMenu Page
	    driver.findElement(By.xpath(getElementLocator("ClickSelectMenu"))).click();
	    //Verify selectMenu Page is Opened
	    if(!driver.findElement(By.xpath(getElementLocator("SelectMenuHome"))).isDisplayed())
			Assert.fail("selectMenu Page is Not Opened");
	    //Explicit Wait
	    WebDriverWait wait = new WebDriverWait(driver, 3);
	    //Select Speed Menu
	    for(int i=1; i<=5; i++) {
	      //Select a Speed - Select Menu
		  driver.findElement(By.xpath(getElementLocator("ClickSelectSpeed"))).click();
		  xpathMenu = getElementLocator("SelectSpeedMenu")+"["+i+"]";
	      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMenu)));
	      driver.findElement(By.xpath(xpathMenu)).click();
	      selectSpeedValidations(i);
	    }
	    //Select File Menu
	    for(int i=1; i<=4; i++) {
	      //Select a File - Select Menu
		  driver.findElement(By.xpath(getElementLocator("ClickSelectFile"))).click();
		  xpathMenu = getElementLocator("SelectFileMenu")+"["+i+"]";
	      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMenu)));
	      driver.findElement(By.xpath(xpathMenu)).click();
	      selectFileValidations(i);
	    }
	    //Select Number Menu
	    for(int i=1; i<=19; i++) {
	      //Select a Number - Select Menu
		  driver.findElement(By.xpath(getElementLocator("ClickSelectNumber"))).click();
		  xpathMenu = getElementLocator("SelectNumberMenu")+"["+i+"]";
		  //Scroll Till Element Displayed
		  WebElement xpathElement = driver.findElement(By.xpath(xpathMenu));
		  JavascriptExecutor jse = (JavascriptExecutor)driver;
		  jse.executeScript("arguments[0].scrollIntoView();", xpathElement);
	      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMenu)));
		  driver.findElement(By.xpath(xpathMenu)).click();
	      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getElementLocator("GetTextNumberMenu"))));
	      menuName = driver.findElement(By.xpath(getElementLocator("GetTextNumberMenu"))).getText();
	      menuName = menuName.trim();
	      System.out.println(menuName);
	      if(!menuName.equals(String.valueOf(i)))
				Assert.fail("Menu Name is Not "+i);	
	    }
	    //Select File Menu
	    for(int i=1; i<=5; i++) {
	      //Select a Speed - Select Menu
		  driver.findElement(By.xpath(getElementLocator("ClickSelectTitle"))).click();
		  xpathMenu = getElementLocator("SelectTitleMenu")+"["+i+"]";
	      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMenu)));
	      driver.findElement(By.xpath(xpathMenu)).click();
	      selectTitleValidations(i);
	    }
	    
	}
	
	@Test(groups= {"controlGroup", "assignment_1", "runAllAssignment"}, alwaysRun = true)
    public void controlGroup() {
		// TODO Auto-generated method stub
	    //Verify HomePage is Opened
		if(!driver.findElement(By.xpath(getElementLocator("ToolsQaHomePage"))).isDisplayed())
			Assert.fail("Tools QA Home Page is Not Opened");
	    //Navigate To ControlGroup Page
	    driver.findElement(By.xpath(getElementLocator("ClickControlGroup"))).click();
	    //Verify ControlGroup Page is Opened
	    if(!driver.findElement(By.xpath(getElementLocator("ControlGroupHome"))).isDisplayed())
			Assert.fail("ControlGroup Page is Not Opened");
	    //Select Rental Car
	    driver.findElement(By.cssSelector(getElementLocator("ClickCarChoose"))).click();
	    //Explicit Wait
	    WebDriverWait wait = new WebDriverWait(driver, 3);
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getElementLocator("ChooseCarModule"))));
	    if(!driver.findElement(By.xpath(getElementLocator("ChooseCarModule"))).isDisplayed())
			Assert.fail("Choose Car Module is Not Opened");
	    String xpathCar = getElementLocator("ChooseCarType")+TestData.get("ChooseCarModel")+"']";
	    driver.findElement(By.xpath(xpathCar)).click();
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getElementLocator("GetSelectedCarText"))));
	    String carText = driver.findElement(By.xpath(getElementLocator("GetSelectedCarText"))).getText();
	    if(!carText.trim().equals(TestData.get("ChooseCarModel")))
	    	Assert.fail("Selected Car Model is Not Displayed");
	    //Select Car Type - Standard or Automatic
	    xpathCar = getElementLocator("CarTypeStdAuto") + TestData.get("CarType") + "')]";
	    driver.findElement(By.xpath(xpathCar)).click();
	    if(!driver.findElement(By.xpath(getElementLocator("InsuranceCheckBox"))).isSelected())
	    	driver.findElement(By.xpath(getElementLocator("InsuranceCheckBox"))).click();
	    //Number of Cars
	    driver.findElement(By.xpath(getElementLocator("NumberCarInput"))).sendKeys(TestData.get("NumberOfCars"));
	    //Click Book Now
	    driver.findElement(By.xpath(getElementLocator("ClickBookNow"))).click();
	}
	
	public void itemsNameValidations(int item) {
		//Get Items Name
		itemsName = driver.findElement(By.xpath("//div[@class='demo-frame']/ol[@id='selectable']/li[@class='ui-widget-content ui-selectee ui-selected']")).getText();
		itemsName = itemsName.trim();
		System.out.println("****Printing Items Names****");
		System.out.println(itemsName);
		switch(item) {
		case 1:
			if(!itemsName.equals("Item 1"))
				Assert.fail("Item Name is Not Item 1");
			break;
		case 2:
			if(!itemsName.equals("Item 2"))
				Assert.fail("Item Name is Not Item 2");
			break;
		case 3:
			if(!itemsName.equals("Item 3"))
				Assert.fail("Item Name is Not Item 3");
			break;
		case 4:
			if(!itemsName.equals("Item 4"))
				Assert.fail("Item Name is Not Item 4");
			break;
		case 5:
			if(!itemsName.equals("Item 5"))
				Assert.fail("Item Name is Not Item 5");
			break;
		case 6:
			if(!itemsName.equals("Item 6"))
				Assert.fail("Item Name is Not Item 6");
			break;
		case 7:
			if(!itemsName.equals("Item 7"))
				Assert.fail("Item Name is Not Item 7");
			break;		
		}
	}

	public void selectSpeedValidations(int position) {
		//Get Selected Menu Name
		WebDriverWait wait = new WebDriverWait(driver, 2);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getElementLocator("GetTextSpeedMenu"))));
		menuName = driver.findElement(By.xpath(getElementLocator("GetTextSpeedMenu"))).getText();
		menuName = menuName.trim();
		System.out.println("****Validating Selected Menu Names****");
		System.out.println(menuName);
		switch(position) {
		case 1:
			if(!menuName.equals("Slower"))
				Assert.fail("Menu Name is Not Slower");
			break;
		case 2:
			if(!menuName.equals("Slow"))
				Assert.fail("Menu Name is Not Slow");
			break;
		case 3:
			if(!menuName.equals("Medium"))
				Assert.fail("Menu Name is Not Medium");
			break;
		case 4:
			if(!menuName.equals("Fast"))
				Assert.fail("Menu Name is Not Fast");
			break;
		case 5:
			if(!menuName.equals("Faster"))
				Assert.fail("Menu Name is Not Faster");
			break;
		}
	}

	public void selectFileValidations(int position) {
		//Get Selected Menu Name
		WebDriverWait wait = new WebDriverWait(driver, 2);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getElementLocator("GetTextFileMenu"))));
		menuName = driver.findElement(By.xpath(getElementLocator("GetTextFileMenu"))).getText();
		menuName = menuName.trim();
		System.out.println("****Validating Selected Menu Names****");
		System.out.println(menuName);
		switch(position) {
		case 1:
			if(!menuName.equals("jQuery.js"))
				Assert.fail("Menu Name is Not 'jQuery.js'");
			break;
		case 2:
			if(!menuName.equals("ui.jQuery.js"))
				Assert.fail("Menu Name is Not 'ui.jQuery.js'");
			break;
		case 3:
			if(!menuName.equals("Some unknown file"))
				Assert.fail("Menu Name is Not 'Some unknown file'");
			break;
		case 4:
			if(!menuName.equals("Some other file with a very long option text"))
				Assert.fail("Menu Name is Not 'Some other file with a very long option text'");
			break;
		}
	}
	
	public void selectTitleValidations(int position) {
		//Get Selected Menu Name
		WebDriverWait wait = new WebDriverWait(driver, 2);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getElementLocator("GetTextTitleMenu"))));
		menuName = driver.findElement(By.xpath(getElementLocator("GetTextTitleMenu"))).getText();
		menuName = menuName.trim();
		System.out.println("****Validating Selected Menu Names****");
		System.out.println(menuName);
		switch(position) {
		case 1:
			if(!menuName.equals("Mr."))
				Assert.fail("Menu Name is Not Mr.");
			break;
		case 2:
			if(!menuName.equals("Mrs."))
				Assert.fail("Menu Name is Not Mrs.");
			break;
		case 3:
			if(!menuName.equals("Dr."))
				Assert.fail("Menu Name is Not Dr.");
			break;
		case 4:
			if(!menuName.equals("Prof."))
				Assert.fail("Menu Name is Not Prof.");
			break;
		case 5:
			if(!menuName.equals("Other"))
				Assert.fail("Menu Name is Not Other");
			break;
		}
	}

}
