package dataProviders;

import org.testng.annotations.DataProvider;

import utils.ExcelUtils;

public class LoginDataProvider {
	@DataProvider(name="validLoginData")
	public static Object[][] getValidLoginData() throws Exception
	{
		int rowCount=ExcelUtils.getRowCount("ValidLogin");
		Object[][]data=new Object[rowCount][2];
		
		for(int i=0;i<rowCount;i++) {
			data[i][0]=ExcelUtils.getCellData("ValidLogin", i+1, 0);//userName
			data[i][1]=ExcelUtils.getCellData("ValidLogin", i+1, 1);//password
		}
		System.out.println("Data provider accessed"+rowCount);
		
		return data;
		
	}
	@DataProvider(name="invalidLoginData")
	public static Object[][] getInValidLoginData() throws Exception
	{
		int rowCount=ExcelUtils.getRowCount("InValidLogin");
		Object[][]data=new Object[rowCount][3];
		
		for(int i=0;i<rowCount;i++) {
			data[i][0]=ExcelUtils.getCellData("InValidLogin", i+1, 0);//userName
			data[i][1]=ExcelUtils.getCellData("InValidLogin", i+1, 1);//password
			data[i][2]=ExcelUtils.getCellData("InValidLogin", i+1, 2);//Expected error
		}
		System.out.println("Data provider accessed"+rowCount);
		
		return data;
		
	}

}
