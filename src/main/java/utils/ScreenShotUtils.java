package utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenShotUtils {
	private static final Logger log= LogManager.getLogger(ScreenShotUtils.class);
	private static final String SCREENSHOT_DIR=ConfigReader.getProperty("screenshot.path");
	private static final String TIMESTAMP_FMT="yyyyMMdd_HHmmss";
	
	private ScreenShotUtils() {}
	
	//create a method to capture screen shot
	
	public static String captureScreenShot(WebDriver driver,String testName) {
		if(driver==null) {
			log.warn("Screen shot Skipped--driver is null Test::{}", testName);
			return null;
		}
		
		//Build the file name: TestName_20260412_120233.png
		
		String timestamp=new SimpleDateFormat(TIMESTAMP_FMT).format(new Date());
		String fileName= testName+"_"+ timestamp+".png";
		String filePath = SCREENSHOT_DIR + fileName;
		try {
			//if directory doesnot exist then create it
			File directory= new File(SCREENSHOT_DIR);
			if(!directory.exists()) {
				directory.mkdir();
				log.info("Created Directory:{},", SCREENSHOT_DIR);
			}
			
			//capture screen shot---temp file
			File srcFile=((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			
			//copy it to destination file---reports folder
			File destFile=new File(filePath);
			FileUtils.copyFile(srcFile, destFile);
			
			log.info("Screen shot saved::{}", destFile.getAbsolutePath());
			return destFile.getAbsolutePath();
		}
		catch(IOException e) {
			log.error("Failed to save screenshot::'{}':{}", testName,e.getMessage());
			return null;
		}
		
	}
	

}
