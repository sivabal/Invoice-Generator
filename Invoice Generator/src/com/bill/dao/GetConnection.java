package com.bill.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.bill.utility.Utility;

public class GetConnection {
	
	public static Connection connection;
	
	public static void getConnection() {
		
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(Utility.databaseConnectionString);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
