package Utility;

import org.testng.TestNG;

public class Config_Runnable {

	public static void main(String[] args) throws Exception
	{
		Thread.sleep(5000);
		TestNG testng = new TestNG();
		testng.setTestClasses(new Class[] { Utility.Config_Report.class });
		testng.run();

	}
}
