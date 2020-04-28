package com.bill.validator;

import java.util.Map;

import com.bill.beans.Address;
import com.bill.beans.GoodsInStock;
import com.bill.beans.Inventory;
import com.bill.dao.FromDatabase;
import com.bill.exception.DatabaseException;
import com.bill.exception.ExcelGeneratorException;
import com.bill.popus.ShowPopups;
import com.bill.utility.Utility;

import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class FromDatabasevalidator {

	/*
	 * this method will return the products information from database
	 */
	public static Map<String, Float[]> getProductDetails() {

		Map<String, Float[]> productInfo = null;
		try {
			productInfo = FromDatabase.getProductDetails();
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}

		return productInfo;

	}

	/*
	 * this method will return the last invoice number
	 */
	public static String getLastInvoiceNumber() {

		String invoiceNumber = "";
		try {

			String[] invoiceNumberArray = FromDatabase.getLastInvoiceNumber().split("-");
			invoiceNumber = invoiceNumberArray[0] + (invoiceNumberArray[1].startsWith("0")?"-0":"-") 
					+ (Integer.parseInt(invoiceNumberArray[1]) + 1);

		} catch (DatabaseException e) {
			invoiceNumber = "INV-6000";
		} catch (NullPointerException e) {
			ShowPopups.showPopups(AlertType.ERROR, "Null Pointer Exception Occured..", e.toString());
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}

		return invoiceNumber;
	}

	/*
	 * 
	 */
	public static void getDataToCreateExcel(int fromDateDiff, int toDateDiff) throws ExcelGeneratorException {
		try {
			FromDatabase.getDataToCreateExcel(fromDateDiff, toDateDiff);
		} catch (ExcelGeneratorException e) {
			throw e;
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}

	/*
	 * this method will return the last product id
	 */
	public static String getLastProductId() {
		String productId = "";
		try {
			productId = Integer.toString(FromDatabase.getLastProductId() + 1);
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
		return productId;
	}

	/*
	 * 
	 */
	public static void getProduct(String updateOldProdName, TextField updateNewProdName, TextField updateUnitRate,
			TextField updateSgst, TextField updateCgst) {

		try {
			FromDatabase.getProduct(Utility.productInfo.get(updateOldProdName)[3].intValue(), updateNewProdName,
					updateUnitRate, updateSgst, updateCgst);
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}

	}

	/*
	 * 
	 */
	public static ObservableList<String> getFromAddressShopNames() {
		ObservableList<String> shopNames = null;
		try {
			shopNames = FromDatabase.getFromAddressShopNames();
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
		return shopNames;
	}

	/*
	 * 
	 */
	public static ObservableList<String> getToAddressShopNames() {
		ObservableList<String> shopNames = null;
		try {
			shopNames = FromDatabase.getToAddressShopNames();
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
		return shopNames;
	}

	/*
	 * 
	 */
	public static Address getFromAddress(String shopName) {
		Address address = null;
		try {
			address = FromDatabase.getFromAddress(shopName);
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
		return address;
	}

	/*
	 * 
	 */
	public static Address getToAddress(String shopName) {
		Address address = null;
		try {
			address = FromDatabase.getToAddress(shopName);
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
		return address;

	}

	/*
	 * 
	 */
	public static ObservableList<String> getInvoiceNumbers() {

		ObservableList<String> invoice = null;
		try {
			invoice = FromDatabase.getInvoiceNumbers();
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
		return invoice;
	}

	public static ObservableList<GoodsInStock> getGoodsInStock() {

		ObservableList<GoodsInStock> goodsInStock = null;
		try {
			goodsInStock = FromDatabase.getGoodsInStock();

		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
		return goodsInStock;
	}
	
	public static ObservableList<GoodsInStock> getGoodsTracker() {

		ObservableList<GoodsInStock> goodsInStock = null;
		try {
			goodsInStock = FromDatabase.getGoodsTracker();

		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
		return goodsInStock;
	}
	
	public static ObservableList<Inventory> getInventory() {

		ObservableList<Inventory> inventory = null;
		try {
			inventory= FromDatabase.getInventory();

		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
		return inventory;
	}
	
}
