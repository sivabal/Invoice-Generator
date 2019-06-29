package com.bill.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.bill.beans.BilledProducts;
import com.bill.dao.ToDatabase;

import javafx.collections.ObservableList;

public class ToDatabaseValidator {
	
	public static void insertInvoiceDataAndBilledProducts(ObservableList<BilledProducts> billrow, String invoiceNumber, String date, String billFrom, String billTo,
			Float orderAmount, Float sgst, Float cgst, Integer total) {
		
		int daysDifference = (int)LocalDate.of(2019,06,01).until(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/uuuu")), ChronoUnit.DAYS);
		ToDatabase.insertInvoiceData(invoiceNumber, date, daysDifference, billFrom, billTo, orderAmount, sgst, cgst, total);
		ToDatabase.insertBilledProducts(billrow, invoiceNumber);
		
	}
	
	/*
	 * 
	 */
	public static void updateProduct(int prodId, String prodName, String unitRate, String sgst, String cgst) {
		ToDatabase.updateProduct(prodId, prodName, unitRate, sgst, cgst);
	}
	
	/*
	 * 
	 */
	public static void insertProduct(int prodId, String prodName, String unitRate, String sgst, String cgst) {
		ToDatabase.insertProduct(prodId, prodName, unitRate, sgst, cgst);
	}
}
