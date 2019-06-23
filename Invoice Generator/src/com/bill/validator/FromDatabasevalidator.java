package com.bill.validator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bill.dao.FromDatabase;

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
		return null;
	}
	
	/*
	 * 
	 */
	public static List<String> getInvoiceData(XSSFWorkbook workbook, XSSFSheet invoiceDataSheet, XSSFDataFormat format,
			int fromDateDiff, int toDateDiff) {
		
		ResultSet resultSet = FromDatabase.getIvoiceData(fromDateDiff, toDateDiff);
		String invoiceNumber = null;
		List<String> invoiceNumArray = new ArrayList<>();
		int rowCount = 1;
		XSSFCell cell = null;
		XSSFCellStyle cellStyle = null;
		try {
			while(resultSet.next()){
				XSSFRow row = invoiceDataSheet.createRow(rowCount);
				invoiceNumber = resultSet.getString("invoiceNumber");
				invoiceNumArray.add(invoiceNumber);
				row.createCell(0).setCellValue(invoiceNumber);
				
				row.createCell(1).setCellValue(resultSet.getString("date"));
				row.createCell(2).setCellValue(resultSet.getString("billFrom"));
				row.createCell(3).setCellValue(resultSet.getString("billTo"));
				
				cell = row.createCell(4);
				cell.setCellValue(Float.parseFloat(resultSet.getString("orderAmount")));
				cellStyle = workbook.createCellStyle();
				cellStyle.setDataFormat(format.getFormat("0.00"));
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(5);
				cell.setCellValue(Float.parseFloat(resultSet.getString("sgst")));
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(6);
				cell.setCellValue(Float.parseFloat(resultSet.getString("cgst")));
				cell.setCellStyle(cellStyle);
				
				row.createCell(7).setCellValue(resultSet.getInt("total"));
				rowCount++;
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return invoiceNumArray;
	}
	
	/*
	 * 
	 */
	public static ResultSet getDataToCreateExcel(int fromDateDiff, int toDateDiff) {
		ResultSet resultSet = FromDatabase.getDataToCreateExcel(fromDateDiff, toDateDiff);
		return resultSet;
	}
}
