package com.bill.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.bill.beans.Address;
import com.bill.excelgenerator.ExcelGenerator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

public class FromDatabase {
	
	
	public static Map<String, Float[]> getProductDetails(){
		
		Map<String, Float[]> productInfo = new HashMap<>();
		String query = "select * from productDetails";
		try (PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query);
				ResultSet resultSet = preparedStmt.executeQuery();){
			
			while(resultSet.next()){
				Float[] rateAndTax = new Float[4];
				rateAndTax[0] = Float.valueOf(resultSet.getString("rate"));
				rateAndTax[1] = Float.valueOf(resultSet.getString("sgst"));
				rateAndTax[2] = Float.valueOf(resultSet.getString("cgst"));
				rateAndTax[3] = Float.valueOf(resultSet.getString("prodId"));
				productInfo.put(resultSet.getString("prodName"), rateAndTax);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return productInfo;
	}

	public static String getLastInvoiceNumber() {
		String query = "select max(invoiceNumber) from invoiceData";
		try (PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query);
				ResultSet resultSet = preparedStmt.executeQuery();){
			
				return resultSet.getString("max(invoiceNumber)");
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * 
	 */
	public static void getDataToCreateExcel(int fromDateDiff, int toDateDiff) {
		String query = "SELECT ID.invoiceNumber, date, billFrom, billTo, ID.orderAmount, sgst, cgst, total, productName, "
				+ "qty, unitRate, BP.orderAmount as amountExTax, sgstTotal, sgstPercentage, cgstTotal, cgstPercentage, amount "
				+ "FROM (SELECT * FROM invoiceData where daysDifference >= ? and daysDifference <= ?) as ID "
				+ "INNER JOIN billedProducts as BP "
				+ "ON ID.invoiceNumber = BP.invoiceNumber";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query);){
			
			preparedStmt.setInt(1, fromDateDiff);
			preparedStmt.setInt(2, toDateDiff);
			ResultSet resultSet = preparedStmt.executeQuery();
			
			while(resultSet.next()) {
				ExcelGenerator.writeContent(resultSet);
			}
			
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 
	 */
	public static Integer getLastProductId() {
		String query = "select max(prodId) from productDetails";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query);
				ResultSet resultSet = preparedStmt.executeQuery();){
			
			return resultSet.getInt("max(prodId)");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * 
	 */
	public static void getProduct(int prodId, TextField updateProdName, TextField updateUnitRate,
			TextField updateSgst, TextField updateCgst) {
		String query = "select prodName, rate, sgst, cgst from productDetails where prodId = ?";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query);){
			
			preparedStmt.setInt(1, prodId);
			
			ResultSet resultSet = preparedStmt.executeQuery();
			
			updateProdName.setText(resultSet.getString("prodName"));
			updateUnitRate.setText(resultSet.getString("rate"));
			updateSgst.setText(resultSet.getString("sgst"));
			updateCgst.setText(resultSet.getString("cgst"));
			
			resultSet.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 
	 */
	public static ObservableList<String> getFromAddressShopNames(){
		
		ObservableList<String> shopNames = FXCollections.observableArrayList();
		String query = "select shopName from fromAddress";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query);
				ResultSet resultSet = preparedStmt.executeQuery();){
			
			while(resultSet.next()){
				shopNames.add(resultSet.getString("shopName"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return shopNames;
	}
	
	/*
	 * 
	 */
	public static ObservableList<String> getToAddressShopNames(){
		
		ObservableList<String> shopNames = FXCollections.observableArrayList();
		shopNames.add("Counter Sales");
		
		String query = "select shopName from toAddress";
		
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query);
				ResultSet resultSet = preparedStmt.executeQuery();){
			
			while(resultSet.next()){
				shopNames.add(resultSet.getString("shopName"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return shopNames;
	}
	
	/*
	 * 
	 */
	public static Address getFromAddress(String shopName){
		
		Address address = new Address();
		String query = "select * from fromAddress where shopName = ?";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query);){
			
			preparedStmt.setString(1, shopName);
			ResultSet resultSet = preparedStmt.executeQuery();
			resultSet = preparedStmt.executeQuery();
			
			address.setShopName(resultSet.getString("shopName"));
			address.setAddressLine1(resultSet.getString("addressLine1"));
			address.setAddressLine2(resultSet.getString("addressLine2"));
			address.setCity(resultSet.getString("city"));
			address.setDistrict(resultSet.getString("district"));
			address.setState(resultSet.getString("state"));
			address.setPincode(resultSet.getString("pincode"));
			address.setTelephone(resultSet.getString("telephone"));
			address.setMobile(resultSet.getString("mobile"));
			address.setGstNo(resultSet.getString("gstNo"));
			
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return address;
	}
	
	/*
	 * 
	 */
	public static Address getToAddress(String shopName){
		
		Address address = new Address();
		String query = "select * from toAddress where shopName = ?";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query);){
			preparedStmt.setString(1, shopName);
			ResultSet resultSet = preparedStmt.executeQuery();
			
			address.setShopName(resultSet.getString("shopName"));
			address.setAddressLine1(resultSet.getString("addressLine1"));
			address.setAddressLine2(resultSet.getString("addressLine2"));
			address.setCity(resultSet.getString("city"));
			address.setDistrict(resultSet.getString("district"));
			address.setState(resultSet.getString("state"));
			address.setPincode(resultSet.getString("pincode"));
			address.setTelephone(resultSet.getString("telephone"));
			address.setMobile(resultSet.getString("mobile"));
			address.setGstNo(resultSet.getString("gstNo"));
			
			resultSet.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return address;
	}
}
