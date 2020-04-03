package com.bill.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.bill.popus.ShowPopups;
import com.bill.utility.Utility;

import javafx.scene.control.Alert.AlertType;

public class GetConnection {
	
	public static Connection connection;
	
	public void getConnection() {
		
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(Utility.databaseConnectionString);
		} catch (ClassNotFoundException | SQLException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "Database Connection Error.");
		}catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "Database Connection Error.");
		}
	}

}
