package atransfer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Main {

	public static void main(String[] args) {

		InfoXLS infoXls = null;
		List<String> inserts = null;

		Map<Integer, String> columns = new HashMap<>();
		columns.put(1,"id");
		columns.put(2,"name");
		columns.put(3,"group_name");
		columns.put(4,"zone_id");
		columns.put(5,"zone_name");
		columns.put(6,"latitude");
		columns.put(7,"longitude");

		infoXls = new InfoXLS("c:/generate/mp.xlsx",
				"jdbc:mysql://10.28.79.30:3306/w2m", "w2m", "w2md3s", "meetings_points", columns);

		try {

			Map<Integer, Map<String, Object>> results = ExcelReader.readXLS(infoXls);
			inserts = ExcelReader.extractInserts(infoXls.getTableName(), results);
			console(results);  //LOG de resultados

		} catch (Exception e) {
			System.out.println("ERROR: Error en lectura del XLS : " + e.getMessage());
			System.exit(-1);
		}

		try {
			DatabaseExecutor.executeInserts(infoXls, inserts);
		} catch (Exception e) {
			System.out.println("ERROR: Error realizando los INSERT : " + e.getMessage());
			System.exit(-1);
		}
		System.out.println("INFO: FINALIZADO CON EXITO");
	}

	private static void printInsert(String tableName, Map<String, Object> row) {
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
		System.out.println(sql);
	}

	private static void console(Map<Integer, Map<String, Object>> results) {

		Iterator it = results.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<Integer, Map<String, Object>> entry = (Map.Entry) it.next();
			printInsert("meetings_points", entry.getValue());
		}

	}

}
