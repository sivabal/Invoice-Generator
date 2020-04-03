package com.bill.validator;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.bill.beans.Address;
import com.bill.beans.BilledProducts;
import com.bill.dao.FromDatabase;
import com.bill.dao.ToDatabase;
import com.bill.exception.OutOfStockException;
import com.bill.exception.SalesReturnException;
import com.bill.utility.Regex;
import com.bill.utility.Utility;

import javafx.collections.ObservableList;

public class ToDatabaseValidator {
	
	public static void insertInvoiceDataAndBilledProducts(ObservableList<BilledProducts> billrow, String invoiceNumber, LocalDate date, Address fromAddress, Address toAddress,
			String billFrom, String billTo,Float orderAmount, Float sgst, Float cgst, Integer total) throws Exception{
		
		try {
			int daysDifference = (int)Utility.startDate.until(date, ChronoUnit.DAYS);
			ToDatabase.insertInvoiceData(invoiceNumber, date.format(DateTimeFormatter.ofPattern("dd/MM/uuuu")), daysDifference,
					billFrom + ((!fromAddress.getGstNo().equals(""))?"  GST No: " +fromAddress.getGstNo():""),
					billTo+ ((toAddress != null && !toAddress.getGstNo().equals(""))?"  GST No: " +toAddress.getGstNo():""), 
					orderAmount, sgst, cgst, total);
			ToDatabase.insertBilledProducts(billrow, invoiceNumber);
			
			
		}catch (SQLException e) {
			throw e;
		}catch (Exception e) {
			throw e;
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
		}
	}
	
	/*
	 * 
	 */
	public static void insertProduct(String prodName, String unitRate, String sgst, String cgst) throws Exception{
		try {
			ToDatabase.insertProduct(prodName, unitRate, sgst, cgst);
		} catch (SQLException e) {
			throw e;
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
		}
	}
	
	/*
	 * 
	 */
	public static void deleteFromAddress(String shopName) throws Exception {
		try {
			ToDatabase.deleteFromAddress(shopName);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/*
	 * 
	 */
	public static void deleteToAddress(String shopName) throws Exception {
		try {
			ToDatabase.deleteToAddress(shopName);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/*
	 * 
	 */
	public static void deleteProduct(String prodName)throws Exception {
		try {
			ToDatabase.deleteProduct(Utility.productInfo.get(prodName)[3].intValue());
		} catch (Exception e) {
			throw e;
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
		}
	}
	
	public static void addInventory(String itemName, String quantity, String price, String cgst, String sgst,
			String igst, String transportCharge, String totalAmount, LocalDate date) throws Exception{
		
		try {
			
			ToDatabase.addInventory(itemName, quantity, price, cgst.equals("")?"0":cgst, sgst.equals("")?"0":sgst, igst.equals("")?"0":igst, 
					transportCharge.equals("")?"0":transportCharge, totalAmount, date.format(DateTimeFormatter.ofPattern("dd/MM/uuuu")));
			
		} catch (SQLException e) {
			throw e;
		}
	}
	
	public static void addGoodsProduced(String itemName, LocalDate date, String lotNo, String goodsProduced) throws Exception{
		
		try {
			
			ToDatabase.addGoodsProduced(itemName, date.format(DateTimeFormatter.ofPattern("dd/MM/uuuu")), lotNo, Float.parseFloat(goodsProduced) + FromDatabase.getGoodsProducedForItem(itemName));
			
		} catch (SQLException e) {
			throw e;
		}
	}
	
	public static void addSalesMade(String itemName, LocalDate date, String lotNo, String goodsTaken, String salesManName) throws Exception{
		
		try {
			salesManName = salesManName.toLowerCase();
			
			Float totalGoodsProduced = FromDatabase.getGoodsProducedForItem(itemName);
			Float takenForSale = Float.parseFloat(goodsTaken);
			
			if(takenForSale > totalGoodsProduced) throw new OutOfStockException("Out of Stock! Available stock is " + totalGoodsProduced + " Kg");
			
			ToDatabase.updateGoodsProducedForItem(totalGoodsProduced - takenForSale , itemName);
			
			ToDatabase.addSalesMade(itemName, date.format(DateTimeFormatter.ofPattern("dd/MM/uuuu")), lotNo, goodsTaken, salesManName);
			
		} catch (SQLException e) {
			throw e;
		}catch (OutOfStockException e) {
			throw e;
		}
	}
	
	public static void updateSalesReturn(String itemName, String date, String salesmanName, String goodsTakenForSale,String prevGoodsReturn, String currGoodsReturn) throws Exception{
		
		try {
			Float takenForSale = Float.parseFloat(goodsTakenForSale);
			Float prevGoodsReturned = Float.parseFloat(prevGoodsReturn);
			Float currGoodsReturned = Float.parseFloat(currGoodsReturn);
			
			if(!Regex.regexQantity.matcher(currGoodsReturn).matches())
				throw new SalesReturnException("Please Provide Valid Returns.");
			if(currGoodsReturned > takenForSale)
				throw new SalesReturnException("'Goods Return' cannot be higher than 'Goods Taken for Sale' !!");
				
			Float totalGoodsProduced = FromDatabase.getGoodsProducedForItem(itemName);
			
			
			ToDatabase.updateGoodsProducedForItem(totalGoodsProduced + (currGoodsReturned - prevGoodsReturned), itemName);
			
			ToDatabase.updateSalesMade(itemName, date, salesmanName, takenForSale - currGoodsReturned, currGoodsReturned);
			
			
		} catch (SQLException e) {
			throw e;
		}catch (SalesReturnException e) {
			throw e;
		}
	}
}
