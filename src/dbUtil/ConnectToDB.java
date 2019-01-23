package dbUtil;

import java.sql.*;

public class ConnectToDB {
	
	private static final String URL = "jdbc:mysql://localhost/pc_store";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	
	public static Connection connectToDataBase() {
		Connection CONN;
		try {
			CONN = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("Class ConnectToDB: Connection successful established.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Class ConnectToDB: Connection cannot be established. Error: " + e.getMessage());
			return null;
		}
		return CONN;
	}
	
}
