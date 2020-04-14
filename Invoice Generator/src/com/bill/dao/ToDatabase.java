package com.bill.dao;

import java.sql.PreparedStatement;
import java.text.DecimalFormat;

import com.bill.beans.BilledProducts;
import com.bill.beans.Inventory;
import com.bill.beans.SalesMade;

import javafx.collections.ObservableList;

public class ToDatabase {
	
	private static DecimalFormat format = new DecimalFormat("#.##");

	public static void insertInvoiceData(String invoiceNumber, String date, int daysDifference, String billFrom, String billTo,
			Float orderAmount, Float sgst, Float cgst, Integer total) throws Exception{
		
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
		}
		
	}
	
	public static void insertBilledProducts(ObservableList<BilledProducts> billrow, String invoiceNumber) throws Exception{
		String query = "insert into billedProducts values(?,?,?,?,?,?,?,?,?,?)";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query)) {
			
			for(BilledProducts b: billrow) {
				preparedStmt.setString(1, invoiceNumber);
				preparedStmt.setString(2, b.getItemName().getText());
				preparedStmt.setString(3, b.getQuantity().getText());
				preparedStmt.setString(4, b.getUnitRate().getText());
				preparedStmt.setString(5, format.format(b.getOrderAmount()));
				preparedStmt.setString(6, format.format(b.getSgstTotal()));
				preparedStmt.setString(7, b.getSgst().getText());
				preparedStmt.setString(8, format.format(b.getCgstTotal()));
				preparedStmt.setString(9, b.getCgst().getText());
				preparedStmt.setString(10, b.getAmount().getText());
				preparedStmt.executeUpdate();
			}			
			
		} 
	}
	
	/*
	 * 
	 */
	public static void updateProduct(int prodId, String prodName, String unitRate, String sgst, String cgst) throws Exception{
		
		String query = "update productDetails set prodName = ?, rate = ?, sgst = ?, cgst = ? where prodId = ?";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query)) {
			preparedStmt.setString(1, prodName);
			preparedStmt.setString(2, unitRate);
			preparedStmt.setString(3, sgst);
			preparedStmt.setString(4, cgst);
			preparedStmt.setInt(5, prodId);
			preparedStmt.executeUpdate();
			
		}
	}
	
	/*
	 * 
	 */
	public static void insertProduct(String prodName, String unitRate, String sgst, String cgst)throws Exception {
		
		String query = "INSERT INTO productDetails (prodName, rate, sgst, cgst) VALUES(?,?,?,?)";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query)) {
			preparedStmt.setString(1, prodName);
			preparedStmt.setString(2, unitRate);
			preparedStmt.setString(3, sgst);
			preparedStmt.setString(4, cgst);
			
			preparedStmt.executeUpdate();
			
		}
	}
	

	/*
	 * 
	 */
	public static void insertFromAddress(String shopName, String addressLine1, String addressLine2, String city, String district,
			String state, String pincode, String telephone, String mobile, String gstNo) throws Exception{
		
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
			
		}
	}
	
	/*
	 * 
	 */
	public static void updateFromAddress(String oldShopName, String newShopName, String addressLine1, String addressLine2, String city, String district,
			String state, String pincode, String telephone, String mobile, String gstNo)throws Exception {
		
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
			
		}
	}
	
	/*
	 * 
	 */
	public static void insertToAddress(String shopName, String addressLine1, String addressLine2, String city, String district,
			String state, String pincode, String telephone, String mobile, String gstNo)throws Exception {
		
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
			
		}
	}
	
	/*
	 * 
	 */
	public static void updateToAddress(String oldShopName, String newShopName, String addressLine1, String addressLine2, String city, String district,
			String state, String pincode, String telephone, String mobile, String gstNo)throws Exception  {
		
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
			
		} 
	}
	
	/*
	 * 
	 */
	public static void deleteFromAddress(String shopName)throws Exception {
		
		String query = "DELETE FROM fromAddress WHERE shopName = ?";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query)) {
			preparedStmt.setString(1, shopName);
			
			preparedStmt.executeUpdate();
			
		} 
	}
	
	/*
	 * 
	 */
	public static void deleteToAddress(String shopName) throws Exception{
		
		String query = "DELETE FROM toAddress WHERE shopName = ?";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query)) {
			preparedStmt.setString(1, shopName);
			
			preparedStmt.executeUpdate();
			
		}
	}
	
	/*
	 * 
	 */
	public static void deleteProduct(int prodId) throws Exception{
		String query = "DELETE FROM productDetails WHERE prodId = ?";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query)) {
			preparedStmt.setInt(1, prodId);
			
			preparedStmt.executeUpdate();
			
		}
	}
	
	/*
	 * 
	 */
	public static void deleteInvoice(String invoice) throws Exception{
		String query = "DELETE FROM invoiceData WHERE invoiceNumber = ?";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query)) {
			preparedStmt.setString(1, invoice);
			
			preparedStmt.executeUpdate();
			
		}
	}
	

	/*
	 * 
	 */
	public static void deleteBilledProducts(String invoice) throws Exception{
		String query = "DELETE FROM billedProducts WHERE invoiceNumber = ?";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query)) {
			preparedStmt.setString(1, invoice);
			
			preparedStmt.executeUpdate();
			
		}
	}
	
	public static void addInventory(String itemName, String quantity, String price, String cgst, String sgst,
		String igst, String transportCharge, String totalAmount, String date)throws Exception  {
		
		String query = "INSERT INTO inventory (itemName, qty, price, cgst, sgst, igst, transportCharge, totalAmount, date) VALUES(?,?,?,?,?,?,?,?,?)";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query)) {
			preparedStmt.setString(1, itemName);
			preparedStmt.setString(2, quantity);
			preparedStmt.setString(3, price);
			preparedStmt.setString(4, cgst);
			preparedStmt.setString(5, sgst);
			preparedStmt.setString(6, igst);
			preparedStmt.setString(7, transportCharge);
			preparedStmt.setString(8, totalAmount);
			preparedStmt.setString(9, date);
			
			preparedStmt.executeUpdate();
			
		} 
	}
	
	public static void deleteInventory(Inventory inventory)throws Exception  {
			
			String query = "DELETE FROM inventory WHERE itemName = ? AND date = ?";
			try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query)) {
				preparedStmt.setString(1, inventory.getItemName());
				preparedStmt.setString(2, inventory.getDate());
				
				preparedStmt.executeUpdate();
				
			} 
		}
	
	public static void addGoodsProduced(String itemName, String date, String lotNo, Float goodsProduced) throws Exception  {
			
			String insertQuery = "INSERT INTO goodsProduced (itemName, date, lotNo, goodsProduced) VALUES(?,?,?,?)";
			String updateQuery = "UPDATE goodsProduced SET goodsProduced = ? WHERE itemName = ?";
			
			PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(updateQuery);
			
			try {
				preparedStmt.setFloat(1, goodsProduced);
				preparedStmt.setString(2, itemName);
				
				int rowCount = preparedStmt.executeUpdate();
				
				if(rowCount == 0) {
					
					preparedStmt = GetConnection.connection.prepareStatement(insertQuery);
					
					preparedStmt.setString(1, itemName);
					preparedStmt.setString(2, date);
					preparedStmt.setString(3, lotNo);
					preparedStmt.setFloat(4, goodsProduced);
					
					preparedStmt.executeUpdate();
				}
				
		}finally {
			preparedStmt.close();
		}
	}
	
	public static void updateGoodsProducedForItem(Float goodsProduced, String itemName) throws Exception  {
		
		String query = "update goodsProduced set goodsProduced = ? where itemName = ?";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query)) {

			preparedStmt.setFloat(1, goodsProduced);
			preparedStmt.setString(2, itemName);
			preparedStmt.executeUpdate();
			
		} 
	}

	public static void addSalesMade(String itemName, String date, String lotNo, String goodsTaken, String salesManName) throws Exception  {
		
		String query = "INSERT INTO salesMade (itemName, date, lotNo, salesManName, goodsTakenForSale, goodsSold) VALUES(?,?,?,?,?,?)";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query)) {
			preparedStmt.setString(1, itemName);
			preparedStmt.setString(2, date);
			preparedStmt.setString(3, lotNo);
			preparedStmt.setString(4, salesManName);
			preparedStmt.setString(5, goodsTaken);
			preparedStmt.setString(6, goodsTaken);

			
			preparedStmt.executeUpdate();
			
		} 
	}
	
	public static void updateSalesMade(String itemName, String date, String salesManName, Float goodsSold, Float goodsReturn) throws Exception  {
		
		String query = "UPDATE salesMade SET goodsReturned = ?, goodsSold = ? WHERE itemName = ? AND date = ? AND salesManName = ?";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query)) {
			preparedStmt.setFloat(1, goodsReturn);
			preparedStmt.setFloat(2, goodsSold);
			preparedStmt.setString(3, itemName);
			preparedStmt.setString(4, date);
			preparedStmt.setString(5, salesManName);
			
			preparedStmt.executeUpdate();
			
		} 
	}
	
	public static void deleteSalesMade(SalesMade salesMade) throws Exception  {
		
		String query = "DELETE FROM salesMade WHERE itemName = ? AND date = ? AND salesManName = ?";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query)) {
			preparedStmt.setString(1, salesMade.getItemName());
			preparedStmt.setString(2, salesMade.getDate());
			preparedStmt.setString(3, salesMade.getSalesmanName());
			
			preparedStmt.executeUpdate();
			
		} 
	}
	
	public static void updatePassword(String password) throws Exception  {
		
		String insertQuery = "INSERT INTO credentials (key, value) VALUES(?,?)";
		String updateQuery = "UPDATE credentials SET value = ? WHERE key = ?";
		
		PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(updateQuery);
		
		try {
			preparedStmt.setString(1, password);
			preparedStmt.setString(2, "password");
			
			int rowCount = preparedStmt.executeUpdate();
			
			if(rowCount == 0) {
				
				preparedStmt = GetConnection.connection.prepareStatement(insertQuery);
				
				preparedStmt.setString(1, "password");
				preparedStmt.setString(2, password);
				
				preparedStmt.executeUpdate();
			}
			
	}finally {
		preparedStmt.close();
	}
}
	
	
}
