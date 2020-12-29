package atransfer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	
	public static Map<Integer, Map<String, Object>> readXLS(InfoXLS infoXls) throws IOException {

		Map<Integer, Map<String, Object>> result = new HashMap<Integer, Map<String, Object>>();
		
		FileInputStream inputStream = new FileInputStream(new File(infoXls.getXlsFile()));		
		Workbook workbook = getRelevantWorkbook(inputStream, infoXls.getXlsFile());
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();
						
		int lineCount = 0;
		while (iterator.hasNext()) {
			Row nextRow = iterator.next();

			if (lineCount == 0) {
				lineCount++;
				continue;
			}
			Map<String, Object> insertRow = getValuesRow(nextRow, infoXls);
			result.put(new Integer(lineCount), insertRow);
			lineCount++;
		}

		workbook.close();
		inputStream.close();
		return result;
	}
	
	
	public static List<String> extractInserts(String tableName, Map<Integer, Map<String, Object>> results) {

		List<String> inserts = new ArrayList<String>();
		Iterator it = results.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<Integer, Map<String, Object>> entry = (Map.Entry) it.next();
			inserts.add(extractInsert(tableName, entry.getValue()));
		}

		return inserts;
	}
	
	private static String extractInsert(String tableName, Map<String, Object> row) {
		String sql = "INSERT INTO " + tableName + "(";
		String sqlValues = " VALUES (";
		Iterator it = row.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			sql += pair.getKey().toString();
			
			if (pair.getValue() instanceof String) {
				sqlValues += "'" + pair.getValue().toString() + "'";
			} else {
				sqlValues += pair.getValue().toString();	
			}
			
			
			if (it.hasNext()) {
				sql += ", ";
				sqlValues += ", ";
			}
		}
		sql += ") " + sqlValues + ");";
		return sql;
	}
	
	private static Map<String, Object> getValuesRow(Row nextRow, InfoXLS infoXls) {
		
		Map<String, Object> result = new HashMap<String, Object>();		
		Iterator<Cell> cellIterator = nextRow.cellIterator();
		
		int columnIndex = 0;
		while (cellIterator.hasNext() && columnIndex < infoXls.getNumberColumns()) {
			
			if (columnIndex < infoXls.getNumberColumns()) {
				Cell cell = cellIterator.next();
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					
					String cadena = cell.getStringCellValue();
					cadena = cadena.replaceAll("�", "g");
					cadena = cadena.replaceAll("'", "m");
					cadena = cadena.replaceAll("\"", "s");
					
					result.put(infoXls.getColumns().get(columnIndex), cadena);					
					break;
				case Cell.CELL_TYPE_NUMERIC:
					result.put(infoXls.getColumns().get(columnIndex), new Double(cell.getNumericCellValue()).intValue());
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					result.put(infoXls.getColumns().get(columnIndex), cell.getBooleanCellValue());					
					break;
				default:
					break;
				}				
			}	
			columnIndex++;
		}
		return result;
	}
	
	private static Workbook getRelevantWorkbook(FileInputStream inputStream, String excelFilePath) throws IOException
	 {
	     Workbook workbook = null;
	  
	     if (excelFilePath.endsWith("xls")) {
	         workbook = new HSSFWorkbook(inputStream);
	     } else if (excelFilePath.endsWith("xlsx")) {
	         workbook = new XSSFWorkbook(inputStream);
	     } else {
	         throw new IllegalArgumentException("Incorrect file format");
	     }
	  
	     return workbook;
	 }

	
	
}
