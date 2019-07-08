package com.bill.validator;

import java.util.Map;

import com.bill.beans.Address;
import com.bill.dao.FromDatabase;
import com.bill.exception.DatabaseException;
import com.bill.popus.ShowPopups;
import com.bill.utility.Utility;

import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

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

		String invoiceNumber = "";
		try {
			
			String[] invoiceNumberArray = FromDatabase.getLastInvoiceNumber().split("-");
			invoiceNumber = invoiceNumberArray[0] + "-" + (Integer.parseInt(invoiceNumberArray[1])+1);
			
		} catch (DatabaseException e) {
			invoiceNumber = "INV-6000";
		}catch (NullPointerException e) {
			ShowPopups.showPopups(AlertType.ERROR, "Null Pointer Exception Occured..",e.toString());
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(),"");
		}
		
		return invoiceNumber;
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
	public static void getProduct(String updateOldProdName, TextField updateNewProdName, TextField updateUnitRate, TextField updateSgst, TextField updateCgst) {

		FromDatabase.getProduct(Utility.productInfo.get(updateOldProdName)[3].intValue(), updateNewProdName, updateUnitRate, updateSgst, updateCgst);
			
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
