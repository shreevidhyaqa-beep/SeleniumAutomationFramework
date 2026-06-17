package utils;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.LoginPage;

public class WaitUtils {
	private static final Logger log = LogManager.getLogger(LoginPage.class);
	//read the timeout from config.properties, if key is not found fall back to 15 sec
	private static final int DFAULT_TIMEOUT=getDefaultTimeOut();
	
	

	private static int getDefaultTimeOut() {
		try {
		return ConfigReader.getInt("explicit.wait");
		}
		catch(Exception e) {
			return 15;
		}
		
	}
	//checking visibility of an element
	public static WebElement waitForVisibility(WebDriver driver,WebElement element) {
		log.debug("waiting for Visibility");
		return new WebDriverWait(driver,Duration.ofSeconds(DFAULT_TIMEOUT))
				.until(ExpectedConditions.visibilityOf(element));
	}

	//wait for element to be clickable
	public static WebElement waitForClickability(WebDriver driver,WebElement element) {
		log.debug("waiting for clickability");
		return new WebDriverWait(driver,Duration.ofSeconds(DFAULT_TIMEOUT))
				.until(ExpectedConditions.elementToBeClickable(element));
	}
}
