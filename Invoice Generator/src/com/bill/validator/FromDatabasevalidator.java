package com.bill.validator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


import com.bill.dao.FromDatabase;
import javafx.scene.control.TextField;

public class FromDatabasevalidator {
	
	
	/*
	 * this method will return the products information from database
	 */
	public static Map<String, Float[]> getProductDetails(){
		
		Map<String, Float[]> productInfo = new HashMap<>();
		try {
			ResultSet resultSet = FromDatabase.getProductDetails();
			while(resultSet.next()){
				Float[] rateAndTax = new Float[3];
				rateAndTax[0] = Float.valueOf(resultSet.getString("rate"));
				rateAndTax[1] = Float.valueOf(resultSet.getFloat("sgst"));
				rateAndTax[2] = Float.valueOf(resultSet.getFloat("cgst"));
				productInfo.put(resultSet.getString("prodName"), rateAndTax);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return productInfo;
	}

	/*
	 * this method will return the last invoice number
	 */
	public static String getLastInvoiceNumber() {
		try {
			ResultSet resultSet = FromDatabase.getLastInvoiceNumber();
			String[] invoiceNumber = resultSet.getString("max(invoiceNumber)").split("-");
			return invoiceNumber[0] + "-" + (Integer.parseInt(invoiceNumber[1])+1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/*
	 * 
	 */
	public static ResultSet getDataToCreateExcel(int fromDateDiff, int toDateDiff) {
		 ResultSet resultSet = FromDatabase.getDataToCreateExcel(fromDateDiff, toDateDiff);
		 return resultSet;
	}
	
	/*
	 * this method will return the last product id
	 */
	public static String getLastProductId() {
		String prodId = "";
		try {
			ResultSet resultSet = FromDatabase.getLastProductId();
			prodId = Integer.toString(resultSet.getInt("max(prodId)") + 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prodId;
	}
	/*
	 * 
	 */
	public static void getProduct(int prodId, TextField updateProdName, TextField updateUnitRate, TextField updateSgst, TextField updateCgst) {

			try {
				ResultSet resultSet = FromDatabase.getProduct(prodId);
				
				updateProdName.setText(resultSet.getString("prodName"));
				updateUnitRate.setText(resultSet.getString("rate"));
				updateSgst.setText(resultSet.getString("sgst"));
				updateCgst.setText(resultSet.getString("cgst"));
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
	}
}
