package drivers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
//used to set and delete Webdriver object
//ThreadLocal---parallel test gets their own Driver
//Without thread local--Thread1 and Thread2 share the same driver---chaos
public class DriverFactory {
	
	//Thread local driver ---one driver per thread
	private static ThreadLocal<WebDriver> driverThreadLocal=new ThreadLocal<>();
	
	private DriverFactory() {}
	
	public static void initDriver(String browser) {
		WebDriver driver;
		
		switch(browser.toLowerCase().trim()) {
		case "chrome":
			ChromeOptions options=new ChromeOptions();
			options.addArguments("--start-maximized");
			options.addArguments("--disable-notifications");
			driver=new ChromeDriver(options);
			break;
		
		case "edge":
			driver=new EdgeDriver();
			driver.manage().window().maximize();
			break;
			
		default:
			throw new IllegalArgumentException("Browser Not Supported");
			
		}
		//store driver for this thread
		driverThreadLocal.set(driver);
	}
		// get driver object
		public static WebDriver getDriver() {
			return driverThreadLocal.get();
			
		}
		//quit the driver or destroy the object
		public static void quitDriver() {
			if(driverThreadLocal.get()!=null) {
				driverThreadLocal.get().quit();
				driverThreadLocal.remove();//memoryLeaks
			
			
		}
	}
	

}
