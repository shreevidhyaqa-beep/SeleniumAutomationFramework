package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.WaitUtils;

public class LoginPage {
	private static final Logger log = LogManager.getLogger(LoginPage.class);
	WebDriver driver;
	//constuctor
	public LoginPage(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}

	//locators
		@FindBy(name="user-name")
		WebElement txt_userName;
		
		@FindBy(id="password")
		WebElement txt_pwd;
		
		@FindBy(id="login-button")
		WebElement btn_login;
		
		@FindBy(css="[data-test='error']")
		WebElement errorMsgContainer;
		
	
	//Actions
		public void enterUserName(String userName) {
			log.info("Entering userName:{}",userName );
			WaitUtils.waitForVisibility(driver,txt_userName);
			txt_userName.clear();
			txt_userName.sendKeys(userName);
				
			}
			public void enterPassword(String pwd) {
				log.info("Entering password:{}",pwd );
				WaitUtils.waitForVisibility(driver,txt_pwd);
				txt_pwd.clear();
				txt_pwd.sendKeys(pwd);
				
			}
			public void clickLogin() {
				WaitUtils.waitForClickability(driver, btn_login);
				log.info("clicking login Button");
				btn_login.click();
//				log.info("adding line");
				
			}
			
			public void login(String userName,String password) {
				log.info("logging as user {}",userName);
				enterUserName(userName);
				enterPassword(password);
				clickLogin();
			}
			
			public boolean isErrorDisplayed() {
				try {
				return errorMsgContainer.isDisplayed();}
				catch(Exception e)
				{
				log.error("Unable to locate container",e);
				return false;
				}
			}
			public String getErrorMsg() {
				return errorMsgContainer.getText();
			}
}
