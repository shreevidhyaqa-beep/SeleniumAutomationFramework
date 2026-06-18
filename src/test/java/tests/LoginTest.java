package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import dataProviders.LoginDataProvider;
import pages.LoginPage;

public class LoginTest extends BaseTest {
	
	
	@Test(groups = { "smoke" })
	public void tc_LT_001_validLogin() 
	{
		LoginPage  loginPage =new LoginPage(getDriver());
		loginPage.login("standard_user","secret_sauce");
		Assert.assertTrue(getDriver().getCurrentUrl().contains("inventory"),"Login Failed");
		
	}
	@Test
	public void tc_LT_002_lockedOutUser() {
		LoginPage  loginPage =new LoginPage(getDriver());
		loginPage.login("locked_out_user","secret_sauce");
		Assert.assertTrue(loginPage.isErrorDisplayed(),"Error message Should Appear");
		Assert.assertTrue(loginPage.getErrorMsg().contains("Sorry, this user has been locked out"),"Error message "
				+ loginPage.getErrorMsg());
		
		
	}
	@Test(dataProvider="validLoginData",dataProviderClass=LoginDataProvider.class,
			groups= {"regression"})
	public void tc_LT_003_dataDriverValidLogin(String userName,String password) {
		LoginPage  loginPage =new LoginPage(getDriver());
		loginPage.login(userName,password);
		Assert.assertTrue(getDriver().getCurrentUrl().contains("inventory"),"Login Failed");
	}
	
	@Test(dataProvider="invalidLoginData",dataProviderClass=LoginDataProvider.class)
	public void tc_LT_003_dataDriverInValidLogin(String userName,String password,String expectedError) {
		LoginPage  loginPage =new LoginPage(getDriver());
		loginPage.login(userName,password);
		Assert.assertTrue(loginPage.getErrorMsg().contains(expectedError),"Error message "
				+ loginPage.getErrorMsg());
	}
	
	
	

}
