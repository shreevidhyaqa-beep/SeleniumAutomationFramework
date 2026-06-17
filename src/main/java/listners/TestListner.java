package listners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;

import drivers.DriverFactory;
import reports.ExtentManager;
import utils.ScreenShotUtils;

public class TestListner implements ITestListener{
	private static final Logger log = LogManager.getLogger(TestListner.class);
	
	//Suite level
	@Override
	public void onStart(ITestContext context) {
		log.info("Suite started:{}", context.getName());
		//Initialise extent report object before any test runs
		ExtentManager.getInstance();
		
	}
	
	@Override
	public void onFinish(ITestContext context) {
		log.info("Suite Completed:{} |Pass={} Fail={} Skip={}",context.getName(),
				context.getPassedTests().size(),
				context.getFailedTests().size(),
				context.getSkippedTests().size());
		
				ExtentManager.flush();
	}
	
	//Test Level
	@Override
	public void onTestStart(ITestResult result) {
		String testName=result.getMethod().getMethodName();
		log.info("Test started:{}", testName);
		
		// create a node in HTML
		ExtentManager.createTest(testName);
		ExtentManager.getTest().log(Status.INFO, "Test Started");
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String testName=result.getMethod().getMethodName();
		log.info("PASS:{}", testName);
		ExtentManager.getTest().log(Status.PASS, "Test Started");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String testName=result.getMethod().getMethodName();
		String errorMsg=result.getThrowable().getMessage();
		log.error("Fail:{}|{}", testName,errorMsg);
		
		ExtentManager.getTest().log(Status.FAIL,"Test Failed"+errorMsg);
		
		//screen shot--embedded in  Extent Report
		
		String screenshotPath=ScreenShotUtils.captureScreenShot(DriverFactory.getDriver(), testName);
		//Embedded the screenshot to report
		if(screenshotPath!=null && ExtentManager.getTest()!=null) {
			try {
			ExtentManager.getTest().addScreenCaptureFromPath(screenshotPath,"Failure ScreenShot");
			log.info("Screenshot attached:{}", screenshotPath);
			
		}
			catch(Exception e){
				log.error("Screenshot not taken::{}",e.getMessage());
				
				}
			}
		ExtentManager.removeTest();
		
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String testName=result.getMethod().getMethodName();
		log.warn("Skiiped Test {}", testName);
		ExtentManager.getTest().log(Status.SKIP,"Test Skipped");
	}

	

	

	

	
	
	

}
