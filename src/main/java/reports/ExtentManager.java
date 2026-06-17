package reports;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import pages.LoginPage;

public class ExtentManager {
	/**
	 * SingleTon Pattern--create extentreports instance only once for all tests
	 *  ThreadLocal---Each test creates its own node in report
	 *  in parallel execution multiple tests runs simultaneously
	 *  Thread local ensures seperate thread for each test
	 *  
	 *  
	 *  Life Cycle
	 *  ExtentManager.getInstance()---- called in @BeforeSuite()
	 *  ExtentManager.createTest()-----called in testListner onteststart
	 *  ExtentManager.getTest().pass()---called in TestListner onTestSuccess
	 *  ExtentManager.flush-----called in @aftersuite
	 *  
	 */
	
	private static final Logger log = LogManager.getLogger(ExtentManager.class);
	
	private static final String REPORT_PATH="reports/extent-reports/ExtentReport.html";
	
	//singleton instance
	private static ExtentReports extentReports;
	
	//one node per thread
	private static final ThreadLocal<ExtentTest>testThreadLocal=new ThreadLocal<>();
	
	private ExtentManager() {} ;
	
	//Synchronized---thread safe---two parallel threads wont create two instance
	public static synchronized ExtentReports getInstance() {
		if(extentReports== null) {
			extentReports=createInstance();
		}
		return extentReports;
	}

	private static ExtentReports createInstance() {
		//craete reports directory
		new File("reports/extent-reports/").mkdirs();
		//spark reporter==generating html file
		ExtentSparkReporter sparkReporter=new ExtentSparkReporter(REPORT_PATH);
		sparkReporter.config().setTheme(Theme.DARK);
		sparkReporter.config().setDocumentTitle("Hybrid Framework--Test Results");
		sparkReporter.config().setReportName("Automation Execution Report");
		sparkReporter.config().setEncoding("UTF-8");
		sparkReporter.config().setTimeStampFormat("dd-mm-yyyy HH:mm:ss");
		
		ExtentReports reports= new ExtentReports();
		reports.attachReporter(sparkReporter);
		
		
		//system info the report
		
		reports.setSystemInfo("OS", System.getProperty("os.name"));
		reports.setSystemInfo("Java", System.getProperty("java.version"));
		reports.setSystemInfo("Environment", "SauceDemo QA");
		reports.setSystemInfo("FrameWork", "Selenium4+TestNg+Hybrid POM");
		reports.setSystemInfo("Tester", System.getProperty("user.name"));
		
		log.info("ExtentReport created report path::{}", REPORT_PATH );
		return reports;
		
		
	}
	
	//create test---new test node in the report
	
	public static void createTest(String testName) {
		ExtentTest test=getInstance().createTest(testName);
		testThreadLocal.set(test);
		log.debug("Extent node created::{}", testName);
				
	
	}
	
	public static ExtentTest getTest() {
		return testThreadLocal.get();
	}
	//prevent memory leaks release the thread after test
	public static void removeTest() {
		testThreadLocal.remove();
	}
	
	//write all the report to HTML
	public static synchronized void flush() {
		if(extentReports != null) {
			extentReports.flush();
			log.info("Extent report flushed. {}", REPORT_PATH);
		}
		
	}
	
}
