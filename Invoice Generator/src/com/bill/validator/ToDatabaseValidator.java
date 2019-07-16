package com.bill.validator;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.bill.beans.Address;
import com.bill.beans.BilledProducts;
import com.bill.dao.ToDatabase;
import com.bill.popus.ShowPopups;
import com.bill.utility.Utility;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;

public class ToDatabaseValidator {
	
	public static void insertInvoiceDataAndBilledProducts(ObservableList<BilledProducts> billrow, String invoiceNumber, String date, Address fromAddress, Address toAddress,
			String billFrom, String billTo,Float orderAmount, Float sgst, Float cgst, Integer total) throws Exception{
		
		try {
			int daysDifference = (int)Utility.startDate.until(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/uuuu")), ChronoUnit.DAYS);
			ToDatabase.insertInvoiceData(invoiceNumber, date, daysDifference,
					billFrom + ((fromAddress.getGstNo() != "")?"  GST No: " +fromAddress.getGstNo():""),
					billTo+ ((toAddress != null && toAddress.getGstNo() != "")?"  GST No: " +toAddress.getGstNo():""), 
					orderAmount, sgst, cgst, total);
			ToDatabase.insertBilledProducts(billrow, invoiceNumber);
		}catch (SQLException e) {
			throw e;
		}catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
		
	}
	
	/*
	 * 
	 */
	public static void updateProduct(String oldProdName, String newProdName, String unitRate, String sgst, String cgst)throws Exception {
		try {
			ToDatabase.updateProduct(Utility.productInfo.get(oldProdName)[3].intValue(), newProdName, unitRate, sgst, cgst);
		} catch (SQLException e) {
			throw e;
		}catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}
	
	/*
	 * 
	 */
	public static void insertProduct(int prodId, String prodName, String unitRate, String sgst, String cgst) throws Exception{
		try {
			ToDatabase.insertProduct(prodId, prodName, unitRate, sgst, cgst);
		} catch (SQLException e) {
			throw e;
		}catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}
	
	/*
	 * 
	 */
	public static void insertFromAddress(String shopName, String addressLine1, String addressLine2, String city, String district,
			String state, String pincode, String telephone, String mobile, String gstNo)throws Exception {
		
		try {
			ToDatabase.insertFromAddress(shopName, addressLine1, addressLine2 ,city, district,state,pincode, telephone,mobile ,gstNo);
		} catch (SQLException e) {
			throw e;
		}catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
		
	}
	
	
	/*
	 * 
	 */
	public static void updateFromAddress(String oldShopName, String newShopName, String addressLine1, String addressLine2, String city, String district,
			String state, String pincode, String telephone, String mobile, String gstNo)throws Exception {
		
		try {
			ToDatabase.updateFromAddress(oldShopName, newShopName, addressLine1, addressLine2,city, district,state,pincode, telephone,mobile ,gstNo);
		} catch (SQLException e) {
			throw e;
		}catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}
	/*
	 * 
	 */
	public static void insertToAddress(String shopName, String addressLine1, String addressLine2, String city, String district,
			String state, String pincode, String telephone, String mobile, String gstNo)throws Exception {
		
		try {
			ToDatabase.insertToAddress(shopName, addressLine1, addressLine2,city , district,state, pincode,telephone,mobile ,gstNo);
		} catch (SQLException e) {
			throw e;
		}catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
		
	}
	
	/*
	 * 
	 */
	public static void updateToAddress(String oldShopName, String newShopName, String addressLine1, String addressLine2, String city, String district,
			String state, String pincode, String telephone, String mobile, String gstNo)throws Exception {
		
		try {
			ToDatabase.updateToAddress(oldShopName, newShopName, addressLine1, addressLine2,city, district,state,pincode, telephone,mobile ,gstNo);
		}catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}
	
	/*
	 * 
	 */
	public static void deleteFromAddress(String shopName) {
		try {
			ToDatabase.deleteFromAddress(shopName);
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}
	
	/*
	 * 
	 */
	public static void deleteToAddress(String shopName) {
		try {
			ToDatabase.deleteToAddress(shopName);
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}
	
	/*
	 * 
	 */
	public static void deleteProduct(String prodName) {
		try {
			ToDatabase.deleteProduct(Utility.productInfo.get(prodName)[3].intValue());
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}
	
	/*
	 * 
	 */
	public static void deleteInvoice(String invoice) throws Exception{
		
		try {
			
			ToDatabase.deleteBilledProducts(invoice);
			ToDatabase.deleteInvoice(invoice);
			
		} catch (SQLException e) {
			throw e;
		}catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}
}
