package com.bill.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.bill.beans.BilledProducts;

import javafx.collections.ObservableList;

public class ToDatabase {

	public static void insertInvoiceData(String invoiceNumber, String date, int daysDifference, String billFrom, String billTo,
			Float orderAmount, Float sgst, Float cgst, Integer total) {
		
		PreparedStatement preparedStmt = null;
		String query = "insert into invoiceData values(?,?,?,?,?,?,?,?,?)";
		try {
			preparedStmt = GetConnection.connection.prepareStatement(query);
			preparedStmt.setString(1, invoiceNumber);
			preparedStmt.setString(2, date);
			preparedStmt.setInt(3, daysDifference);
			preparedStmt.setString(4, billFrom);
			preparedStmt.setString(5, billTo);
			preparedStmt.setFloat(6, orderAmount);
			preparedStmt.setFloat(7, sgst);
			preparedStmt.setFloat(8, cgst);
			preparedStmt.setInt(9, total);
			
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void insertBilledProducts(ObservableList<BilledProducts> billrow, String invoiceNumber) {
		PreparedStatement preparedStmt = null;
		String query = "insert into billedProducts values(?,?,?,?,?,?,?,?,?)";
		try {
			preparedStmt = GetConnection.connection.prepareStatement(query);
			for(BilledProducts b: billrow) {
				preparedStmt.setString(1, invoiceNumber);
				preparedStmt.setString(2, b.getItemName().getText());
				preparedStmt.setInt(3, Integer.parseInt(b.getQuantity().getText()));
				preparedStmt.setFloat(4, Float.valueOf(b.getUnitRate().getText()));
				preparedStmt.setFloat(5, b.getSgstTotal());
				preparedStmt.setString(6, b.getSgst().getText());
				preparedStmt.setFloat(7, b.getCgstTotal());
				preparedStmt.setString(8, b.getCgst().getText());
				preparedStmt.setFloat(9, Float.valueOf(b.getAmount().getText()));
				preparedStmt.executeUpdate();
			}			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
