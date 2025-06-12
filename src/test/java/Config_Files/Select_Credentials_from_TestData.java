package Config_Files;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Select_Credentials_from_TestData {
	
	public static String get_snowflake_credentials(int row, int column) throws IOException, Exception {
		String Snowflake_cred_path = new File("TestData/Snowflake_Credentials.xlsx").getAbsolutePath();
		FileInputStream fis = new FileInputStream(Snowflake_cred_path);
		//FileInputStream fis = new FileInputStream(".\\TestData\\Snowflake_Credentials.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(0);
		return sheet.getRow(row).getCell(column).getStringCellValue();
	}
	
	public static String get_config_non_user_credentials(int row, int column)
			throws IOException, Exception {
		//String path=Paths.get("TestData","Config_Non_User_Credentials.xlsx").toAbsolutePath().toString();
		//FileInputStream fis = new FileInputStream(".\\TestData\\Config_Non_User_Credentials.xlsx");
		//FileInputStream fis = new FileInputStream(path);
		String Non_user_cred_path = new File("TestData/Config_Non_User_Credentials.xlsx").getAbsolutePath();
		FileInputStream fis = new FileInputStream(Non_user_cred_path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(0);
		return sheet.getRow(row).getCell(column).getStringCellValue();
	}

	public static String get_dashboard_list(int row, int column) throws IOException, Exception {
		String dashboard_list_path = new File("TestData/Dashboard_List.xlsx").getAbsolutePath();
		FileInputStream fis = new FileInputStream(dashboard_list_path);
		//FileInputStream fis = new FileInputStream(".\\TestData\\Dashboard_List.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(0);
		return sheet.getRow(row).getCell(column).getStringCellValue();
	}
	
	public static int get_total_rows() throws IOException, Exception {

		String dashboard_list_path = new File("TestData/Dashboard_List.xlsx").getAbsolutePath();
		FileInputStream fis = new FileInputStream(dashboard_list_path);
		//FileInputStream fis = new FileInputStream(".\\TestData\\Dashboard_List.xlsx");
		try {
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(0);
		return sheet.getPhysicalNumberOfRows();
	} catch (IOException e) {
		e.printStackTrace();
		return 0;
	}
	}
	
	public static String get_email_alert_credentials(int row, int column)
			throws IOException, Exception {
		String Email_alert_cred_path = new File("TestData/Email_Alert_Credentials.xlsx").getAbsolutePath();
		FileInputStream fis = new FileInputStream(Email_alert_cred_path);
		//FileInputStream fis = new FileInputStream(".\\TestData\\Email_Alert_Credentials.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(0);
		return sheet.getRow(row).getCell(column).getStringCellValue();
	}
}


