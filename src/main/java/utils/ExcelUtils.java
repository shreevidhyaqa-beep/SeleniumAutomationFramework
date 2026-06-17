package utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * Returns the string value for specific cell
 */

public class ExcelUtils {

	public static String getCellData(String sheetName, int rowNum, int colmn) throws Exception {
		// get the excel path
		String filePath = ConfigReader.getProperty("testdata.path");
		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {
			Sheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				throw new IllegalArgumentException("Sheet not found::");
			}
			Row row = sheet.getRow(rowNum);
			if (row == null) {
				return "";
			}
			Cell cell = row.getCell(colmn);
			if (cell == null)
				return "";

			// DataFormatter
			DataFormatter formatter = new DataFormatter();
			return formatter.formatCellValue(cell).trim();
		} catch (IOException e) {
			throw new Exception("cannot read File");
		}
	}

	public static int getRowCount(String sheetName) throws Exception {
		String filePath = ConfigReader.getProperty("testdata.path");
		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {
			Sheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				throw new IllegalArgumentException("Sheet not found::");
			}
			// get the last row
			return sheet.getLastRowNum();
		}

		catch (IOException e) {
			throw new Exception("cannot read File");

		}
	}

}