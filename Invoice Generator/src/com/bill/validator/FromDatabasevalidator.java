package com.bill.validator;

import java.util.Map;

import com.bill.beans.Address;
import com.bill.dao.FromDatabase;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

public class FromDatabasevalidator {
	
	
	/*
	 * this method will return the products information from database
	 */
	public static Map<String, Float[]> getProductDetails(){
		
		Map<String, Float[]> productInfo = FromDatabase.getProductDetails();
		return productInfo;
		
		
		
	}

	/*
	 * this method will return the last invoice number
	 */
	public static String getLastInvoiceNumber() {

		String[] invoiceNumber = FromDatabase.getLastInvoiceNumber().split("-");
		return invoiceNumber[0] + "-" + (Integer.parseInt(invoiceNumber[1])+1);
		
	}
	
	/*
	 * 
	 */
	public static void getDataToCreateExcel(int fromDateDiff, int toDateDiff) {
		 FromDatabase.getDataToCreateExcel(fromDateDiff, toDateDiff);
	}
	
	/*
	 * this method will return the last product id
	 */
	public static String getLastProductId() {
		return Integer.toString(FromDatabase.getLastProductId() + 1);
	}
	/*
	 * 
	 */
	public static void getProduct(int prodId, TextField updateProdName, TextField updateUnitRate, TextField updateSgst, TextField updateCgst) {

		FromDatabase.getProduct(prodId, updateProdName, updateUnitRate, updateSgst, updateCgst);
			
	}
	
	/*
	 * 
	 */
	public static ObservableList<String> getFromAddressShopNames(){
		 return FromDatabase.getFromAddressShopNames();
			
	}
	
	/*
	 * 
	 */
	public static ObservableList<String> getToAddressShopNames(){
		
		return FromDatabase.getToAddressShopNames();
	}
	
	/*
	 * 
	 */
	public static Address getFromAddress(String shopName){
	
		return FromDatabase.getFromAddress(shopName); 
	}
	
	/*
	 * 
	 */
	public static Address getToAddress(String shopName){

		return FromDatabase.getToAddress(shopName);

	}
}
