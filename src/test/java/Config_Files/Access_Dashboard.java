package Config_Files;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;

import Config_Files.Select_Credentials_from_TestData;
import Config_Files.Push_Elapsed_Time_to_Snowflake;
//import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;

public class Access_Dashboard {

	public static WebDriver driver;
	public static String select_email_id;
	static DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	static ArrayList<String> arraylist = new ArrayList<String>();
	public static int rowcount = 1;
	static Select_Credentials_from_TestData credentials = new Select_Credentials_from_TestData();

	public static int get_dashboard_list() throws Exception {
		//WebDriverManager.chromedriver().setup();
		//WebDriver driver = new ChromeDriver();
		System.out.println("Welcome...");
		String os=System.getProperty("os.name").toLowerCase();
		if(os.contains("win")){
		System.setProperty("webdriver.chrome.driver","/usr/local/bin/chromedriver.exe" );
		}else{
		System.setProperty("webdriver.chrome.driver","/usr/local/bin/chromedriver");
		}

		ChromeOptions options = new ChromeOptions();
		boolean isHeadless = true;
		if(isHeadless){
			options.addArguments ("--headless=new");
			options.addArguments ("--no-sandbox");
			options.addArguments ("--disable-dev-shm-usage");
		}	
					      
		driver=new ChromeDriver(options);
		try {
			System.out.println("Entered into get_dashboard_list");
			// launch_chrome
			Thread.sleep(5000);
			driver.manage().window().maximize();
			driver.get("https://app.powerbi.com/");

			// Login_Process
			Thread.sleep(5000);
			
			//access power Bi dashboard using application id and secret key value
			

			// TDD Authentication
			select_email_id = credentials.get_config_non_user_credentials(0, 0);
			driver.findElement(By.id("email")).sendKeys(select_email_id);
			WebElement submit = driver.findElement(By.id("submitBtn"));
			submit.click();
			
			
			Thread.sleep(40000);

			int dashboard_list_row_value = 1;

			// get total numbers of rows from spreadsheet
			int total_rows = credentials.get_total_rows();
			
			try {
			for (dashboard_list_row_value = 1; dashboard_list_row_value < total_rows; dashboard_list_row_value++) {

				String dashboard_name = credentials.get_dashboard_list(dashboard_list_row_value, 0);
				String dashboard_link = credentials.get_dashboard_list(dashboard_list_row_value, 1);
				String dashboard_target_value = credentials.get_dashboard_list(dashboard_list_row_value, 2);

				// print values
				System.out.println("Dashboard Name: " + dashboard_name);
				System.out.println("Dashboard Link: " + dashboard_link);
				System.out.println("Dashboard Target Value: " + dashboard_target_value);

				driver.get(dashboard_link);

				Date start_date = new Date();
				long startTime = System.currentTimeMillis();
				String Start_Date_Time = dateFormat.format(start_date);
				System.out.println("Start Time: " + Start_Date_Time);

				Thread.sleep(20000);

				new WebDriverWait(driver, Duration.ofSeconds(30)).until(
						webDriver ->((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

				System.out.println("Current URL:" +driver.getCurrentUrl());
				System.out.println("Current Title:" +driver.getTitle());
				System.out.println("Looking for xpath:");

				List<WebElement> elements = driver.findElements(By.xpath("//*[contains(./text(),'" + dashboard_target_value + "')]"));
				System.out.println("Elements found: " + elements.size()+" elements");
				
				File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(srcFile, new File("screenshot.png")); 
				
				// To_find_response_time_of_SPOG_RCM (End_time)
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
				WebElement Dest_Element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(./text(),'" + dashboard_target_value + "')]")));
				// exit if destination element is not found
				if (Dest_Element == null) {
					System.out.println("Destination element not found. Exiting.");
					// return 0;
				}
				// Fit_to_page_to_capture_full_page
				WebElement Fit_to_Page = driver.findElement(By.xpath("//button[@id='fitToPageButton']"));
				Fit_to_Page.click();

				// To_find_response_time_of_SPOG_RCM (End_time)
				Date end_date = new Date();
				long endTime = System.currentTimeMillis();
				String End_Date_Time = dateFormat.format(end_date);
				System.out.println("End Time: " + End_Date_Time);

				// Capture Screenshot
				String Bridge = dashboard_name;
				getScreenhot(driver, Bridge);

				// To_find_response_time
				long totalTime = endTime - startTime;

				System.out.println("Total Page " + dashboard_name + " Load Time: " + totalTime + " milliseconds");

				// Create_Alerts_when_page_loading_is_exceeding_morethan_one_minute
				if (totalTime > 60000) {
					System.out.println(dashboard_name + "is loading more than 1 minute");

				}

				// Adding values to arraylist to send it to Spreadsheet
				arraylist.add(dashboard_name);
				arraylist.add(Start_Date_Time);
				arraylist.add(End_Date_Time);
				String totaltime = String.valueOf(totalTime);
				arraylist.add(totaltime);
				if (totalTime > 60000) {
					arraylist.add("Page load time exceeded more than a minute");
					System.out.println("Page load time exceeded more than a minute");
				} else {
					arraylist.add("SUCCESS");
					System.out.println("Page load time is within limit");
				}

				// print arraylist values
				System.out.println("ArrayList values after added: " + arraylist);

				// push arraylist values to spreadsheet
				Push_Elapsed_Time_to_Spreadsheet.add_values_to_spreadsheet(arraylist, rowcount++);
				// push arraylist to Snowflake table
				Push_Elapsed_Time_to_Snowflake.add_values_to_snowflake_table(arraylist);
				arraylist.clear();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.close();
		}
		return 1;
	}

	// send email with spreadsheet
	public static void send_email() throws Exception {

	}

	public static void getScreenhot(WebDriver driver, String Bridge) throws Exception {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/Config_Screenshots/" + Bridge + dateName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		// return destination;
	}

	public static void main(String[] args) {
		try {
			int ans1 = get_dashboard_list();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
