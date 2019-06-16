package com.bill.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FromDatabase {
	
	public static ResultSet getProductDetails(){
		
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		String query = "select * from productDetails";
		try {
			preparedStmt = GetConnection.connection.prepareStatement(query);
			resultSet = preparedStmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

}
