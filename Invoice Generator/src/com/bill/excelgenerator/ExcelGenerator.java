package com.bill.excelgenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bill.popus.ShowPopups;
import com.bill.validator.FromDatabasevalidator;

import javafx.scene.control.Alert.AlertType;

public class ExcelGenerator {

	static XSSFWorkbook workbook = new XSSFWorkbook();
	static XSSFSheet invoiceDataSheet = workbook.createSheet("Invoice Data");
	static XSSFSheet billedProductsSheet = workbook.createSheet("Billed Products");
	static XSSFRow row = null;
	static XSSFCell cell = null;
	static XSSFCellStyle cellStyle = null;
	static XSSFFont font = null;
	static String invoiceNumber = "";
	static int invoiceDataRow = 1, billedProdRow = 1, firstRow = 1;
	static XSSFDataFormat format = workbook.createDataFormat();
	
	public static void generateExcel(LocalDate fromDate, LocalDate toDate) {
		
		try {
			int fromDateDiff = (int)LocalDate.of(2019,06,01).until(fromDate, ChronoUnit.DAYS);
			int toDateDiff = (int)LocalDate.of(2019,06,01).until(toDate, ChronoUnit.DAYS);
			
			row = invoiceDataSheet.createRow(0);
			cell = row.createCell(0);cell.setCellValue("Invoice Number");
			cellStyle = workbook.createCellStyle();
			font = workbook.createFont();
			font.setBold(true);
			cellStyle.setFont(font);cell.setCellStyle(cellStyle);
			
			cell = row.createCell(1);cell.setCellValue("Date");
			cellStyle = workbook.createCellStyle();
			font = workbook.createFont();
			font.setBold(true);
			cellStyle.setFont(font);cell.setCellStyle(cellStyle);
			
			cell = row.createCell(2);cell.setCellValue("Bill From");
			cellStyle = workbook.createCellStyle();
			font = workbook.createFont();
			font.setBold(true);
			cellStyle.setFont(font);cell.setCellStyle(cellStyle);
			
			cell = row.createCell(3);cell.setCellValue("Bill To");
			cellStyle = workbook.createCellStyle();
			font = workbook.createFont();
			font.setBold(true);
			cellStyle.setFont(font);cell.setCellStyle(cellStyle);
			
			cell = row.createCell(4);cell.setCellValue("Order Amount(Rs)");
			cellStyle = workbook.createCellStyle();
			font = workbook.createFont();
			font.setBold(true);
			cellStyle.setFont(font);cell.setCellStyle(cellStyle);
			
			cell = row.createCell(5);cell.setCellValue("Sgst Amount(Rs)");
			cellStyle = workbook.createCellStyle();
			font = workbook.createFont();
			font.setBold(true);
			cellStyle.setFont(font);cell.setCellStyle(cellStyle);
			
			cell = row.createCell(6);cell.setCellValue("Cgst Amount(Rs)");
			cellStyle = workbook.createCellStyle();
			font = workbook.createFont();
			font.setBold(true);
			cellStyle.setFont(font);cell.setCellStyle(cellStyle);
			
			cell = row.createCell(7);cell.setCellValue("Total Amount(Rs)");
			cellStyle = workbook.createCellStyle();
			font = workbook.createFont();
			font.setBold(true);
			cellStyle.setFont(font);cell.setCellStyle(cellStyle);
	
			row = billedProductsSheet.createRow(0);
			cell = row.createCell(0);cell.setCellValue("Invoice Number");
			cellStyle = workbook.createCellStyle();
			font = workbook.createFont();
			font.setBold(true);
			cellStyle.setFont(font);cell.setCellStyle(cellStyle);
			
			cell = row.createCell(1);cell.setCellValue("Product Name");
			cellStyle = workbook.createCellStyle();
			font = workbook.createFont();
			font.setBold(true);
			cellStyle.setFont(font);cell.setCellStyle(cellStyle);
			
			cell = row.createCell(2);cell.setCellValue("Quantity(Kg)");
			cellStyle = workbook.createCellStyle();
			font = workbook.createFont();
			font.setBold(true);
			cellStyle.setFont(font);cell.setCellStyle(cellStyle);
			
			cell = row.createCell(3);cell.setCellValue("Unit Rate(Rs)");
			cellStyle = workbook.createCellStyle();
			font = workbook.createFont();
			font.setBold(true);
			cellStyle.setFont(font);cell.setCellStyle(cellStyle);
			
			cell = row.createCell(4);cell.setCellValue("Sgst(%)");
			cellStyle = workbook.createCellStyle();
			font = workbook.createFont();
			font.setBold(true);
			cellStyle.setFont(font);cell.setCellStyle(cellStyle);
			
			cell = row.createCell(5);cell.setCellValue("Sgst(Rs)");
			cellStyle = workbook.createCellStyle();
			font = workbook.createFont();
			font.setBold(true);
			cellStyle.setFont(font);cell.setCellStyle(cellStyle);
			
			cell = row.createCell(6);cell.setCellValue("Cgst(%)");
			cellStyle = workbook.createCellStyle();
			font = workbook.createFont();
			font.setBold(true);
			cellStyle.setFont(font);cell.setCellStyle(cellStyle);
			
			cell = row.createCell(7);cell.setCellValue("Cgst(Rs)");
			cellStyle = workbook.createCellStyle();
			font = workbook.createFont();
			font.setBold(true);
			cellStyle.setFont(font);cell.setCellStyle(cellStyle);
			
			cell = row.createCell(8);cell.setCellValue("Amount(Rs)");
			cellStyle = workbook.createCellStyle();
			font = workbook.createFont();
			font.setBold(true);
			cellStyle.setFont(font);cell.setCellStyle(cellStyle);
			
			
			FromDatabasevalidator.getDataToCreateExcel(fromDateDiff, toDateDiff);
			
			billedProductsSheet.addMergedRegion(new CellRangeAddress(firstRow, billedProdRow, 0, 0));
			
			invoiceDataSheet.autoSizeColumn(0); 
			invoiceDataSheet.autoSizeColumn(1);
			invoiceDataSheet.autoSizeColumn(2);
			invoiceDataSheet.autoSizeColumn(3);
			invoiceDataSheet.autoSizeColumn(4);
			invoiceDataSheet.autoSizeColumn(5);
			invoiceDataSheet.autoSizeColumn(6);
			invoiceDataSheet.autoSizeColumn(7);
		
			billedProductsSheet.autoSizeColumn(0); 
			billedProductsSheet.autoSizeColumn(1);
			billedProductsSheet.autoSizeColumn(2);
			billedProductsSheet.autoSizeColumn(3);
			billedProductsSheet.autoSizeColumn(4);
			billedProductsSheet.autoSizeColumn(5);
			billedProductsSheet.autoSizeColumn(6);
			billedProductsSheet.autoSizeColumn(7);
			billedProductsSheet.autoSizeColumn(8);
	
			workbook.write(new FileOutputStream("C:\\Users\\welcome\\Desktop\\excel\\Excel.xlsx"));
			workbook.close();
			
			ShowPopups.showPopups(AlertType.INFORMATION, "Success....", "Excel is generated Successfully....");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 
	 */
	public static void writeContent(ResultSet resultSet) {
			try {
				if(!invoiceNumber.equals(resultSet.getString("invoiceNumber"))) {
					if(!invoiceNumber.equals("")) {
						billedProductsSheet.addMergedRegion(new CellRangeAddress(firstRow, billedProdRow, 0, 0));
						firstRow = ++billedProdRow;
					}
				
					invoiceNumber = resultSet.getString("invoiceNumber");
					row = invoiceDataSheet.createRow(invoiceDataRow);
					
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
					cellStyle = workbook.createCellStyle();
					cellStyle.setDataFormat(format.getFormat("0.00"));
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(6);
					cell.setCellValue(Float.parseFloat(resultSet.getString("cgst")));
					cellStyle = workbook.createCellStyle();
					cellStyle.setDataFormat(format.getFormat("0.00"));
					cell.setCellStyle(cellStyle);
					
					row.createCell(7).setCellValue(resultSet.getInt("total"));
					invoiceDataRow++;
				}
				
				row = billedProductsSheet.createRow(billedProdRow);
				cell = row.createCell(0);
				cell.setCellValue(invoiceNumber);
				cellStyle = workbook.createCellStyle();
				cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				cell.setCellStyle(cellStyle);
				
				row.createCell(1).setCellValue(resultSet.getString("productName"));
				row.createCell(2).setCellValue(resultSet.getInt("qty"));
				
				cell = row.createCell(3);
				cell.setCellValue(Float.valueOf(resultSet.getString("unitRate")));
				cellStyle = workbook.createCellStyle();
				cellStyle.setDataFormat(format.getFormat("0.00"));
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(4);
				cell.setCellValue(Float.valueOf(resultSet.getString("sgstPercentage")));
				cellStyle = workbook.createCellStyle();
				cellStyle.setDataFormat(format.getFormat("0.00"));
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(5);
				cell.setCellValue(Float.valueOf(resultSet.getString("sgstTotal")));
				cellStyle = workbook.createCellStyle();
				cellStyle.setDataFormat(format.getFormat("0.00"));
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(6);
				cell.setCellValue(Float.valueOf(resultSet.getString("cgstPercentage")));
				cellStyle = workbook.createCellStyle();
				cellStyle.setDataFormat(format.getFormat("0.00"));
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(7);
				cell.setCellValue(Float.valueOf(resultSet.getString("cgstTotal")));
				cellStyle = workbook.createCellStyle();
				cellStyle.setDataFormat(format.getFormat("0.00"));
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(8);
				cell.setCellValue(Float.valueOf(resultSet.getString("amount")));
				cellStyle = workbook.createCellStyle();
				cellStyle.setDataFormat(format.getFormat("0.00"));
				cell.setCellStyle(cellStyle);
				
				billedProdRow++;
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
	}
}
