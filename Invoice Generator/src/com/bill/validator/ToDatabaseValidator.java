package com.bill.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.bill.beans.BilledProducts;
import com.bill.dao.ToDatabase;
import com.bill.utility.Utility;

import javafx.collections.ObservableList;

public class ToDatabaseValidator {
	
	public static void insertInvoiceDataAndBilledProducts(ObservableList<BilledProducts> billrow, String invoiceNumber, String date, String billFrom, String billTo,
			Float orderAmount, Float sgst, Float cgst, Integer total) {
		
		try {
			int daysDifference = (int)Utility.startDate.until(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/uuuu")), ChronoUnit.DAYS);
			ToDatabase.insertInvoiceData(invoiceNumber, date, daysDifference, billFrom, billTo, orderAmount, sgst, cgst, total);
			ToDatabase.insertBilledProducts(billrow, invoiceNumber);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
		}
		
	}
	
	/*
	 * 
	 */
	public static void updateProduct(String oldProdName, String newProdName, String unitRate, String sgst, String cgst) {
		try {
			ToDatabase.updateProduct(Utility.productInfo.get(oldProdName)[3].intValue(), newProdName, unitRate, sgst, cgst);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 
	 */
	public static void insertProduct(int prodId, String prodName, String unitRate, String sgst, String cgst) {
		try {
			ToDatabase.insertProduct(prodId, prodName, unitRate, sgst, cgst);
		} catch (Exception e) {
		}
	}
	
	/*
	 * 
	 */
	public static void insertFromAddress(String shopName, String addressLine1, String addressLine2, String city, String district,
			String state, String pincode, String telephone, String mobile, String gstNo) {
		
		try {
			ToDatabase.insertFromAddress(shopName, addressLine1, addressLine2 ,city, district,state,pincode, telephone,mobile ,gstNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/*
	 * 
	 */
	public static void updateFromAddress(String oldShopName, String newShopName, String addressLine1, String addressLine2, String city, String district,
			String state, String pincode, String telephone, String mobile, String gstNo) {
		
		try {
			ToDatabase.updateFromAddress(oldShopName, newShopName, addressLine1, addressLine2,city, district,state,pincode, telephone,mobile ,gstNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * 
	 */
	public static void insertToAddress(String shopName, String addressLine1, String addressLine2, String city, String district,
			String state, String pincode, String telephone, String mobile, String gstNo) {
		
		try {
			ToDatabase.insertToAddress(shopName, addressLine1, addressLine2,city , district,state, pincode,telephone,mobile ,gstNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * 
	 */
	public static void updateToAddress(String oldShopName, String newShopName, String addressLine1, String addressLine2, String city, String district,
			String state, String pincode, String telephone, String mobile, String gstNo) {
		
		try {
			ToDatabase.updateToAddress(oldShopName, newShopName, addressLine1, addressLine2,city, district,state,pincode, telephone,mobile ,gstNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 
	 */
	public static void deleteFromAddress(String shopName) {
		try {
			ToDatabase.deleteFromAddress(shopName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 
	 */
	public static void deleteToAddress(String shopName) {
		try {
			ToDatabase.deleteToAddress(shopName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 
	 */
	public static void deleteProduct(String prodName) {
		try {
			ToDatabase.deleteProduct(Utility.productInfo.get(prodName)[3].intValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
