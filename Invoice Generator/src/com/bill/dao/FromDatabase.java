package com.bill.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FromDatabase {
	
	static PreparedStatement preparedStmt = null;
	static ResultSet resultSet = null;
	
	public static ResultSet getProductDetails(){
		
		
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
		String query = "select max(invoiceNumber) from invoiceData";
		try {
			preparedStmt = GetConnection.connection.prepareStatement(query);
			resultSet = preparedStmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}
	
	/*
	 * 
	 */
	public static ResultSet getDataToCreateExcel(int fromDateDiff, int toDateDiff) {
		String query = "SELECT ID.invoiceNumber, date, billFrom, billTo, orderAmount, sgst, cgst, total, productName, "
				+ "qty, unitRate, sgstTotal, sgstPercentage, cgstTotal, cgstPercentage, amount "
				+ "FROM (SELECT * FROM invoiceData where daysDifference >= ? and daysDifference <= ?) as ID "
				+ "INNER JOIN billedProducts as BP "
				+ "ON ID.invoiceNumber = BP.invoiceNumber";
		try{
			preparedStmt = GetConnection.connection.prepareStatement(query);
			preparedStmt.setInt(1, fromDateDiff);
			preparedStmt.setInt(2, toDateDiff);
			resultSet = preparedStmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}
	
	/*
	 * 
	 */
	public static ResultSet getLastProductId() {
		String query = "select max(prodId) from productDetails";
		try{
			preparedStmt = GetConnection.connection.prepareStatement(query);
			resultSet = preparedStmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}
	
	/*
	 * 
	 */
	public static ResultSet getProduct(int prodId) {
		String query = "select prodName, rate, sgst, cgst from productDetails where prodId = ?";
		try{
			preparedStmt = GetConnection.connection.prepareStatement(query);
			preparedStmt.setInt(1, prodId);
			resultSet = preparedStmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}
}
