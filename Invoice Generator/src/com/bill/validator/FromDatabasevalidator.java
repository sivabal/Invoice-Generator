package com.bill.validator;

import com.bill.dao.FromDatabase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class FromDatabasevalidator {
	
	/*
	 * this method will return the products information from database
	 */
	public static Map<String, Float[]> getProductDetails(){
		ResultSet resultSet = FromDatabase.getProductDetails();
		Map<String, Float[]> productInfo = new HashMap<>();
		
		try {
			while(resultSet.next()){
				Float[] rateAndTax = new Float[3];
				rateAndTax[0] = resultSet.getFloat("rate");
				rateAndTax[1] = resultSet.getFloat("sgst");
				rateAndTax[2] = resultSet.getFloat("cgst");
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
		ResultSet resultSet = FromDatabase.getLastInvoiceNumber();
		return null;
	}
}
