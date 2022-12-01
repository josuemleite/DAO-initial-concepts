package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnectionFactory {
	
	private static final String JDBC_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	
	private static final String DATABASE_URL = "jdbc:mysql://127.0.0.1/facebook";

	static final String USER = "root";
	static final String PASSWORD = "";

	public Connection getConnection() {
		try {
			Class.forName(JDBC_DRIVER_NAME);
			
			return DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
		} catch (ClassNotFoundException e) {
			
		} catch (SQLException e) {
			
		}
		
		return null;
	}
}
