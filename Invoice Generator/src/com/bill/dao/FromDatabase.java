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

	public static ResultSet getLastInvoiceNumber() {
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		String query = "select max(invoiceNumber) from invoiceData";
		try {
			preparedStmt = GetConnection.connection.prepareStatement(query);
			resultSet = preparedStmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}
	
	public static ResultSet getIvoiceData(int fromDateDiff, int toDateDiff) {
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		String query = "select * from invoiceData where daysDifference >= ? and daysDifference <= ?";
		try {
			preparedStmt = GetConnection.connection.prepareStatement(query);
			preparedStmt.setInt(1, fromDateDiff);
			preparedStmt.setInt(2, toDateDiff);
			resultSet = preparedStmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}
}
