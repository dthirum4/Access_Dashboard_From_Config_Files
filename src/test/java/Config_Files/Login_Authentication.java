package Config_Files;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;

import Config_Files.Select_Credentials_from_TestData;


public class Login_Authentication {
	
	
	public static WebDriver driver;
	public static String select_email_id;
	
	public static int login_authentication() throws Exception, InterruptedException
	{
		
    //set chromedriver to match the installed browser version
	WebDriverManager.chromedriver().setup();
	WebDriver driver=new ChromeDriver();
		
	
	try {
	//launch_chrome
	Thread.sleep(5000);
	driver.manage().window().maximize();
	driver.get("https://app.powerbi.com/");
	
	//Login_Process	
	Thread.sleep(5000);
	
	//TDD Authentication
	select_email_id=Select_Credentials_from_TestData.get_config_non_user_credentials(0, 0);
	driver.findElement(By.id("email")).sendKeys(select_email_id);
	WebElement submit=driver.findElement(By.id("submitBtn")); 
	submit.click();
	
	//if login is not successful, then exit
	if(driver.getCurrentUrl().contains("https://app.powerbi.com/"))
    {
        return 1;
    }
    else
    {
        System.out.println("Login Unsuccessful");
        driver.quit();
        return 0;
	}
	}
    catch(Exception e)
    {
        e.printStackTrace();
    }
	return 1;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			login_authentication();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
