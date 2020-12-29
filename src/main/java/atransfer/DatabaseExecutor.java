package atransfer;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseExecutor {

    private static Connection connection = null;

    private static void createConnection(String url, String username, String password) throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
    }

    public static boolean testConnection(String url, String username, String password) throws SQLException {
        boolean result = false;
        createConnection(url, username, password);
        result = connection.isValid(5);
        closeConnection();
        return result;
    }

    public static List<String> getTableNamesFromDatabase(String url, String username, String password) throws SQLException {
        createConnection(url, username, password);
        List<String> tables = new ArrayList<>();
        DatabaseMetaData md = connection.getMetaData();
        ResultSet rs = md.getTables(null, null, "%", null);
        while (rs.next()) {
            tables.add(rs.getString(3));
        }
        closeConnection();
        return tables;
    }

    public static List<String> getColumnsFromTable(String url, String username, String password, String tablename) throws SQLException {
        List<String> columns = new ArrayList<>();
        createConnection(url, username, password);
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        ResultSet rs = databaseMetaData.getColumns(null, null, tablename, null);
        while (rs.next()) {
            columns.add(rs.getString("COLUMN_NAME"));
        }
        closeConnection();
        return columns;

    }

    private static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
        connection = null;
    }

    public static List<String> executeInserts(InfoXLS infoXls, List<String> inserts) {
        List<String> result = new ArrayList<>();
         Statement st;
        try {
            createConnection(infoXls.getUrlConnection(), infoXls.getUser(), infoXls.getPassword());
            st = connection.createStatement();
        } catch (SQLException ex) {
            result.add("Se ha perdido la comunicación con el HOST de base de datos : " + ex.getMessage());
            return result;
        }
        
        for (String insert : inserts) {
            try {
                st.executeUpdate(insert);                
            } catch (SQLException e) {
                result.add(insert + " -> " + e.getMessage());                                        
            }
        }
        try {
            st.close();
            closeConnection();
        } catch (SQLException ex) {
            result.add("Error al cerrar la conexión : " + ex.getMessage());            
        }
        return result;
    }

}
