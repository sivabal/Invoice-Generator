package com.bill.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;

import com.bill.beans.BilledProducts;

import javafx.collections.ObservableList;

public class ToDatabase {
	
	private static DecimalFormat format = new DecimalFormat("#.##");

	public static void insertInvoiceData(String invoiceNumber, String date, int daysDifference, String billFrom, String billTo,
			Float orderAmount, Float sgst, Float cgst, Integer total) {
		
		String query = "insert into invoiceData values(?,?,?,?,?,?,?,?,?)";
		try (PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query)){
			
			preparedStmt.setString(1, invoiceNumber);
			preparedStmt.setString(2, date);
			preparedStmt.setInt(3, daysDifference);
			preparedStmt.setString(4, billFrom);
			preparedStmt.setString(5, billTo);
			preparedStmt.setString(6, format.format(orderAmount));
			preparedStmt.setString(7, format.format(sgst));
			preparedStmt.setString(8, format.format(cgst));
			preparedStmt.setInt(9, total);
			
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void insertBilledProducts(ObservableList<BilledProducts> billrow, String invoiceNumber) {
		String query = "insert into billedProducts values(?,?,?,?,?,?,?,?,?)";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query)) {
			
			for(BilledProducts b: billrow) {
				preparedStmt.setString(1, invoiceNumber);
				preparedStmt.setString(2, b.getItemName().getText());
				preparedStmt.setString(3, b.getQuantity().getText());
				preparedStmt.setString(4, b.getUnitRate().getText());
				preparedStmt.setString(5, format.format(b.getSgstTotal()));
				preparedStmt.setString(6, b.getSgst().getText());
				preparedStmt.setString(7, format.format(b.getCgstTotal()));
				preparedStmt.setString(8, b.getCgst().getText());
				preparedStmt.setString(9, b.getAmount().getText());
				preparedStmt.executeUpdate();
			}			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 
	 */
	public static void updateProduct(int prodId, String prodName, String unitRate, String sgst, String cgst) {
		
		String query = "update productDetails set prodName = ?, rate = ?, sgst = ?, cgst = ? where prodId = ?";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query)) {
			preparedStmt.setString(1, prodName);
			preparedStmt.setString(2, unitRate);
			preparedStmt.setString(3, sgst);
			preparedStmt.setString(4, cgst);
			preparedStmt.setInt(5, prodId);
			preparedStmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 
	 */
	public static void insertProduct(int prodId, String prodName, String unitRate, String sgst, String cgst) {
		
		String query = "insert into productDetails values(?,?,?,?,?)";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query)) {
			preparedStmt.setInt(1, prodId);
			preparedStmt.setString(2, prodName);
			preparedStmt.setString(3, unitRate);
			preparedStmt.setString(4, sgst);
			preparedStmt.setString(5, cgst);
			
			preparedStmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	/*
	 * 
	 */
	public static void insertFromAddress(String shopName, String addressLine1, String addressLine2, String city, String district,
			String state, String pincode, String telephone, String mobile, String gstNo) {
		
		String query = "insert into fromAddress values(?,?,?,?,?,?,?,?,?,?)";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query)) {
			preparedStmt.setString(1, shopName);
			preparedStmt.setString(2, addressLine1);
			preparedStmt.setString(3, addressLine2);
			preparedStmt.setString(4, city);
			preparedStmt.setString(5, district);
			preparedStmt.setString(6, state);
			preparedStmt.setString(7, pincode);
			preparedStmt.setString(8, telephone);
			preparedStmt.setString(9, mobile);
			preparedStmt.setString(10, gstNo);
			
			preparedStmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 
	 */
	public static void updateFromAddress(String oldShopName, String newShopName, String addressLine1, String addressLine2, String city, String district,
			String state, String pincode, String telephone, String mobile, String gstNo) {
		
		String query = "update fromAddress set shopName = ?, addressLine1 = ?, addressLine2 = ? , city = ?, district = ?, state = ?, "
				+ "pincode = ?, telephone = ?, mobile = ?, gstNo = ? where shopName = ?";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query)) {
			preparedStmt.setString(1, newShopName);
			preparedStmt.setString(2, addressLine1);
			preparedStmt.setString(3, addressLine2);
			preparedStmt.setString(4, city);
			preparedStmt.setString(5, district);
			preparedStmt.setString(6, state);
			preparedStmt.setString(7, pincode);
			preparedStmt.setString(8, telephone);
			preparedStmt.setString(9, mobile);
			preparedStmt.setString(10, gstNo);
			preparedStmt.setString(11, oldShopName);
			
			preparedStmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 
	 */
	public static void insertToAddress(String shopName, String addressLine1, String addressLine2, String city, String district,
			String state, String pincode, String telephone, String mobile, String gstNo) {
		
		String query = "insert into toAddress values(?,?,?,?,?,?,?,?,?,?)";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query)) {
			preparedStmt.setString(1, shopName);
			preparedStmt.setString(2, addressLine1);
			preparedStmt.setString(3, addressLine2);
			preparedStmt.setString(4, city);
			preparedStmt.setString(5, district);
			preparedStmt.setString(6, state);
			preparedStmt.setString(7, pincode);
			preparedStmt.setString(8, telephone);
			preparedStmt.setString(9, mobile);
			preparedStmt.setString(10, gstNo);
			
			preparedStmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 
	 */
	public static void updateToAddress(String oldShopName, String newShopName, String addressLine1, String addressLine2, String city, String district,
			String state, String pincode, String telephone, String mobile, String gstNo) {
		
		String query = "update toAddress set shopName = ?, addressLine1 = ?, addressLine2 = ? , city = ?, district = ?, state = ?, "
				+ "pincode = ?, telephone = ?, mobile = ?, gstNo = ? where shopName = ?";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query)) {
			preparedStmt.setString(1, newShopName);
			preparedStmt.setString(2, addressLine1);
			preparedStmt.setString(3, addressLine2);
			preparedStmt.setString(4, city);
			preparedStmt.setString(5, district);
			preparedStmt.setString(6, state);
			preparedStmt.setString(7, pincode);
			preparedStmt.setString(8, telephone);
			preparedStmt.setString(9, mobile);
			preparedStmt.setString(10, gstNo);
			preparedStmt.setString(11, oldShopName);
			
			preparedStmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
