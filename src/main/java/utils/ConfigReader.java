package utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {
	
	static Properties prop;
	
	static {
		try {
			//read the inputstream
			FileInputStream fis=new FileInputStream("src/main/resources/config.properties");
			//Properties is a java class 
			//behaves like map or dictionary
			prop=new Properties();
			prop.load(fis);
			
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	//return the value for the specifies key
	public static String getProperty(String key) {
		return prop.getProperty(key);
	}
	
	public static int getInt(String key) {
		return Integer.parseInt(getProperty(key));
	}
}
