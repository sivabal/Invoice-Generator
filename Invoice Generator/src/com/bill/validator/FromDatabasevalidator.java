package com.bill.validator;

import com.bill.dao.FromDatabase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

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
		try {
			ResultSet resultSet = FromDatabase.getLastInvoiceNumber();
			String[] invoiceNumber = resultSet.getString("max(invoiceNumber)").split("-");
			return invoiceNumber[0] + "-" + (Integer.parseInt(invoiceNumber[1])+1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void getInvoiceData(XSSFSheet invoiceDataSheet, int fromDateDiff, int toDateDiff) {
		ResultSet resultSet = FromDatabase.getIvoiceData(fromDateDiff, toDateDiff);
		int rowCount = 1;
		try {
			while(resultSet.next()){
				XSSFRow row = invoiceDataSheet.createRow(rowCount);
				row.createCell(0).setCellValue(resultSet.getString("invoiceNumber"));
				row.createCell(1).setCellValue(resultSet.getString("date"));
				row.createCell(2).setCellValue(resultSet.getString("billFrom"));
				row.createCell(3).setCellValue(resultSet.getString("billTo"));
				row.createCell(4).setCellValue(resultSet.getDouble("orderAmount"));
				row.createCell(5).setCellValue(resultSet.getDouble("sgst"));
				row.createCell(6).setCellValue(resultSet.getDouble("cgst"));
				row.createCell(7).setCellValue(resultSet.getInt("total"));
				rowCount++;
			}
			
			invoiceDataSheet.autoSizeColumn(0);
			invoiceDataSheet.autoSizeColumn(1);
			invoiceDataSheet.autoSizeColumn(2);
			invoiceDataSheet.autoSizeColumn(3);
			invoiceDataSheet.autoSizeColumn(4);
			invoiceDataSheet.autoSizeColumn(5);
			invoiceDataSheet.autoSizeColumn(6);
			invoiceDataSheet.autoSizeColumn(7);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
