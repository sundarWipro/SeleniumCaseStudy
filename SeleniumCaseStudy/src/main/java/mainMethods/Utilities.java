package mainMethods;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;


public class Utilities {

	public static HashMap<String, String> TestData = new HashMap<String, String>();
	public static JSONObject jsonObject;
	static String strFileDir = System.getProperty("user.dir");
	
    public static void loadTestData() {
        System.out.println("Load the TestData in HashMap");
        //Reading TestData Using POI API
        try {
    		FileInputStream inputStream = new FileInputStream(strFileDir +"/src/test/resources/Resources/TestData.xlsx");
    	    Workbook bookData = new XSSFWorkbook(inputStream);
    	    Sheet bookSheet = bookData.getSheet("Env_Data");
    	    int totalRows = bookSheet.getPhysicalNumberOfRows();
    	    for (int i=0; i<totalRows; i++) {
    	        Row row = bookSheet.getRow(i);
    	        TestData.put(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue());
    	        //System.out.println(TestData.get(row.getCell(0).getStringCellValue()));
    	     }
    	    bookData.close();
    	    
    	    //Read Json File
    	     JSONParser parser = new JSONParser();
    	     Object obj = parser.parse(new FileReader(strFileDir +"/src/test/resources/Resources/Registration.json"));
	         jsonObject = (JSONObject)obj;
	         System.out.println("TestData Loaded Successfully");
        }
        catch(FileNotFoundException e) {
        	e.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
    }
    
    public static WebDriver setBrowserDriver() {
    	WebDriver driver = null;
    	if(TestData.get("Browser").equalsIgnoreCase("Chrome")) {
    		 WebDriverManager.chromedriver().version("80.0.3987.106").setup();
    		 driver = new ChromeDriver();
    		 System.out.println("Chrome Browser Opened Successfully");
    	}
    	if(TestData.get("Browser").equalsIgnoreCase("Firefox")) {
    		 WebDriverManager.firefoxdriver().setup();
    		 driver = new FirefoxDriver();
    		 System.out.println("Firefox Browser Opened Successfully");
    	}
    	return driver;
    }
    
    public static void launchApplication(WebDriver driver, String strAppURL) {
    	 //Launch The App URL
    	driver.get(TestData.get(strAppURL));
    	System.out.println("Application URL Launched Successfully");
    	driver.manage().window().maximize();
    	//Delete Cookies
    	driver.manage().deleteAllCookies();
    	System.out.println("Site Cookies Deleted Successfully");
    	//Launch The App URL
    	driver.get(TestData.get(strAppURL));
    	System.out.println("Application URL Launched Successfully");
	   }
	  
    public static void closeBrowser(WebDriver driver) {
        //Close the Browser
    	driver.quit();
    	System.out.println("Browser Closed Successfully");
   }

    public static String getElementLocator(String key) {
    	
    	String locator = null;
    	try {
    		Properties prop = new Properties();
			InputStream inputStream = new FileInputStream(strFileDir +"/src/test/resources/Resources/ObjectRepository.properties");
			prop.load(inputStream);
			locator = prop.getProperty(key);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return locator;
    	
    }
   
    public String getEmailId() {
		
		return "register"+System.currentTimeMillis()+"@gmail.com";
	}

}
