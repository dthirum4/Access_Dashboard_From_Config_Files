package Utility;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import ConfigFiles.Access_Dashboard;
import ConfigFiles.Login_Authentication;

public class Config_Report extends Login_Authentication {

	public ExtentReports extent;
	public ExtentTest logger;
	public static int return_value;
	
	@BeforeTest
	public void startReport() {
		
		String path= System.getProperty("user.dir") +"\\test_report\\Config_ExtentReport.html";
		extent = new ExtentReports(path, true);
		extent.addSystemInfo("User Name", System.getProperty("user.name"));
		extent.addSystemInfo("Operating System", System.getProperty("os.name"));
		extent.addSystemInfo("Java Version", System.getProperty("java.version"));
		extent.addSystemInfo("Host Name", "Power BI Dashboard");
		extent.addSystemInfo("Environment", "Automation Testing");
		extent.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));
	}
	@BeforeMethod
	public void openBrowser() 
	{
	}
	@Test(priority=1)
	
	
	public void dashboards() throws Exception {
		//values.clear();
		try {
		logger = extent.startTest("DASHBOARD_ACCESS");
		Access_Dashboard dashboard=new Access_Dashboard();
		dashboard.get_dashboard_list();
		if(return_value==0)
        {
            logger.log(LogStatus.FAIL, "Test Case Failed");
            Assert.assertTrue(false);
        }
        else
        {
		logger.log(LogStatus.PASS, "Test Case Passed");
	    Assert.assertTrue(true);
        }
		}
		catch(Exception e)
		{e.printStackTrace();}
	    }
	
	
	public  String getScreenhot(WebDriver driver, String Bridge) throws Exception 
	{
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/Config_Screenshots/"+Bridge+dateName+".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}
	
	

	@AfterTest
	public void endReport() {
	
	extent.flush();
	extent.close();
	}

	

}
