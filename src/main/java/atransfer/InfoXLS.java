package atransfer;

import java.util.HashMap;
import java.util.Map;

public class InfoXLS {
	
	private String xlsFile;
	private String urlConnection;
	private String user;
	private String password;
	private String tableName;
	private Map<Integer, String> columns = new HashMap<>();	
	
	public InfoXLS(String xlsFile, String urlConnection, String user, String password, String tableName,
			Map<Integer, String> columns) {
		super();
		this.xlsFile = xlsFile;
		this.urlConnection = urlConnection;
		this.user = user;
		this.password = password;
		this.tableName = tableName;
		this.columns = columns;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getXlsFile() {
		return xlsFile;
	}
	public void setXlsFile(String xlsFile) {
		this.xlsFile = xlsFile;
	}
	public String getUrlConnection() {
		return urlConnection;
	}
	public void setUrlConnection(String urlConnection) {
		this.urlConnection = urlConnection;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public Map<Integer, String> getColumns() {
            return columns;
	}
	public void setColumns(Map<Integer, String> columns) {
            this.columns = columns;
	}
	
	public int getNumberColumns() {
		return columns.size();
	}

}
