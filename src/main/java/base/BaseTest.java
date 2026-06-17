package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import drivers.DriverFactory;
import reports.ExtentManager;
import utils.ConfigReader;
import utils.ScreenShotUtils;

public class BaseTest {
	private static final Logger log= LogManager.getLogger(BaseTest .class);
	@BeforeSuite
	public void suiteSetUp() {
		log.info("Suite Initialised");
		ExtentManager.getInstance();
	}
	@BeforeMethod
	public void setUp() {
		String browser=ConfigReader.getProperty("browser");
		String url=ConfigReader.getProperty("url");
		
		//driver.get("www.sauceDemo")
		System.out.println(">>>setting browser"+browser+"--Url"+url);
		DriverFactory.initDriver(browser);
		getDriver().get(url);
		
		
	}
	@AfterMethod
	public void tearDown() {
		System.out.println(">>TearDown Method...Closing Browser");
		DriverFactory.quitDriver();
	}
	
	@AfterSuite
	public void suiteTearDown() {
		log.info("Suite TearDown----Flush Reports");
		ExtentManager.flush();
	}
		protected WebDriver getDriver() {
			return DriverFactory.getDriver();
		}
		
	}


