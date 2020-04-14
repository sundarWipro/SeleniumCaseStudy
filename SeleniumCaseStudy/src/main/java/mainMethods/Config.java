package mainMethods;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class Config {

	@BeforeSuite(alwaysRun = true)
	public void beforeTestSuite() {
		System.out.println("*****Starting Test Suite Execution*****");
		//Read TestData from Excel
		Utilities.loadTestData();
	}
	
	@AfterSuite(alwaysRun = true)
	public void afterTestSuite() {
		System.out.println("******Test Suite Execution Completed******");
	}
	
}
