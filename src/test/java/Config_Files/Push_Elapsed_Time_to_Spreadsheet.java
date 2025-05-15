package Config_Files;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Push_Elapsed_Time_to_Spreadsheet {
	public static void add_values_to_spreadsheet(List<String> values,int rowcount) throws IOException
	{
		System.out.println("Rowcount is: "+rowcount);
		String currentDir=System.getProperty("user.dir");
		FileInputStream fis2 = new FileInputStream(currentDir+"\\test-output\\Config_Spreadsheet.xlsx");
		XSSFWorkbook workbook1 = new XSSFWorkbook(fis2);
		XSSFSheet Spreadsheet = workbook1.getSheet("Execution Details");
		Font headerFont = workbook1.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 11);

		XSSFCellStyle headerCellStyle = (XSSFCellStyle) workbook1.createCellStyle();

		headerCellStyle.setFont(headerFont);
		Row headerRow = Spreadsheet.createRow(rowcount);

		for(int index=0;index<values.size();index++)
		{
			Cell cell = headerRow.createCell(index);
			cell.setCellValue(values.get(index));
			cell.setCellStyle(headerCellStyle);
			
		}

	

		for(int index=0;index<values.size();index++)
			Spreadsheet.autoSizeColumn(index);

		FileOutputStream fileOut = new FileOutputStream(".//test-output//Config_Spreadsheet.xlsx");
		workbook1.write(fileOut);

		fis2.close();


	}

}
