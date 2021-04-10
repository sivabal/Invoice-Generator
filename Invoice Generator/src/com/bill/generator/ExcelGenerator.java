package com.bill.generator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bill.exception.ExcelGeneratorException;
import com.bill.popus.ShowPopups;
import com.bill.utility.Utility;
import com.bill.validator.FromDatabasevalidator;

import javafx.scene.control.Alert.AlertType;

public class ExcelGenerator {

	static XSSFWorkbook workbook;
	static XSSFSheet invoiceDataSheet;
	static XSSFRow row;
	static XSSFCell cell;
	static XSSFCellStyle cellStyle;
	static XSSFFont font;
	static String invoiceNumber;
	static int invoiceDataRow, firstRow;
	static XSSFDataFormat format;
	static String amountExTax, sgstTotal, cgstTotal, amount;
	
	/*
	 * 
	 */
	public static void generateExcel(LocalDate fromDate, LocalDate toDate) {
		
		try {
			int fromDateDiff = (int)Utility.startDate.until(fromDate, ChronoUnit.DAYS);
			int toDateDiff = (int)Utility.startDate.until(toDate, ChronoUnit.DAYS);

			initializeStaticVariables();
			
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
			
			cell = row.createCell(4);cell.setCellValue("Product Name");
			cellStyle = workbook.createCellStyle();
			font = workbook.createFont();
			font.setBold(true);
			cellStyle.setFont(font);cell.setCellStyle(cellStyle);
			
			cell = row.createCell(5);cell.setCellValue("Quantity(Kg)");
			cellStyle = workbook.createCellStyle();
			font = workbook.createFont();
			font.setBold(true);
			cellStyle.setFont(font);cell.setCellStyle(cellStyle);
			
			cell = row.createCell(6);cell.setCellValue("Unit Rate(Rs)");
			cellStyle = workbook.createCellStyle();
			font = workbook.createFont();
			font.setBold(true);
			cellStyle.setFont(font);cell.setCellStyle(cellStyle);
			
			cell = row.createCell(7);cell.setCellValue("Sgst(%)");
			cellStyle = workbook.createCellStyle();
			font = workbook.createFont();
			font.setBold(true);
			cellStyle.setFont(font);cell.setCellStyle(cellStyle);
			
			cell = row.createCell(8);cell.setCellValue("Cgst(%)");
			cellStyle = workbook.createCellStyle();
			font = workbook.createFont();
			font.setBold(true);
			cellStyle.setFont(font);cell.setCellStyle(cellStyle);
			
			cell = row.createCell(9);cell.setCellValue("Amount Ex. Tax(Rs)");
			cellStyle = workbook.createCellStyle();
			font = workbook.createFont();
			font.setBold(true);
			cellStyle.setFont(font);cell.setCellStyle(cellStyle);
			
			cell = row.createCell(10);cell.setCellValue("Sgst(Rs)");
			cellStyle = workbook.createCellStyle();
			font = workbook.createFont();
			font.setBold(true);
			cellStyle.setFont(font);cell.setCellStyle(cellStyle);
			
			cell = row.createCell(11);cell.setCellValue("Cgst(Rs)");
			cellStyle = workbook.createCellStyle();
			font = workbook.createFont();
			font.setBold(true);
			cellStyle.setFont(font);cell.setCellStyle(cellStyle);
			
			cell = row.createCell(12);cell.setCellValue("Amount(Rs)");
			cellStyle = workbook.createCellStyle();
			font = workbook.createFont();
			font.setBold(true);
			cellStyle.setFont(font);cell.setCellStyle(cellStyle);
			
			
			FromDatabasevalidator.getDataToCreateExcel(fromDateDiff, toDateDiff);
			
			row = invoiceDataSheet.createRow(invoiceDataRow);
			totalRow();
			invoiceDataRow++;
			invoiceDataSheet.addMergedRegion(new CellRangeAddress(firstRow, invoiceDataRow, 0, 0));
			
			invoiceDataSheet.autoSizeColumn(0); 
			invoiceDataSheet.autoSizeColumn(1);
			invoiceDataSheet.autoSizeColumn(2);
			invoiceDataSheet.autoSizeColumn(3);
			invoiceDataSheet.autoSizeColumn(4);
			invoiceDataSheet.autoSizeColumn(5);
			invoiceDataSheet.autoSizeColumn(6);
			invoiceDataSheet.autoSizeColumn(7);
			invoiceDataSheet.autoSizeColumn(8);
			invoiceDataSheet.autoSizeColumn(9);
			invoiceDataSheet.autoSizeColumn(10);
			invoiceDataSheet.autoSizeColumn(11);
			invoiceDataSheet.autoSizeColumn(12);
	
			workbook.write(new FileOutputStream(Utility.excelSheetPath + "Report_" + fromDate.format(DateTimeFormatter.ofPattern("dd-MMM-uu")) + 
					"_to_" + toDate.format(DateTimeFormatter.ofPattern("dd-MMM-uu")) +".xlsx"));
			
			workbook.close();
			
			
			ShowPopups.showPopups(AlertType.INFORMATION, "Success....", "Excel is generated Successfully....");
			
		}catch (ExcelGeneratorException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.getMessage(), "");
		}catch (FileNotFoundException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}catch (IOException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}
	
	/*
	 * 
	 */
	public static void writeContent(ResultSet resultSet) {
			try {
				row = invoiceDataSheet.createRow(invoiceDataRow);
				
				if(!invoiceNumber.equals(resultSet.getString("invoiceNumber"))) {
					if(!invoiceNumber.equals("")) {
						totalRow();
						invoiceDataRow++;
						invoiceDataSheet.addMergedRegion(new CellRangeAddress(firstRow, invoiceDataRow, 0, 0));
						firstRow = ++invoiceDataRow;
						row = invoiceDataSheet.createRow(invoiceDataRow);
					}
				
					amountExTax = resultSet.getString("orderAmount");
					sgstTotal = resultSet.getString("sgst");
					cgstTotal = resultSet.getString("cgst");
					amount = resultSet.getString("total");
					
					invoiceNumber = resultSet.getString("invoiceNumber");
					
					cell = row.createCell(0);
					cell.setCellValue(invoiceNumber.replace("INV-", Utility.invoiceFormat));
					cellStyle = workbook.createCellStyle();
					cellStyle.setAlignment(HorizontalAlignment.CENTER);
					cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
					cell.setCellStyle(cellStyle);
					
					row.createCell(1).setCellValue(resultSet.getString("date"));
					row.createCell(2).setCellValue(resultSet.getString("billFrom"));
					row.createCell(3).setCellValue(resultSet.getString("billTo"));
					
				}
			
				row.createCell(4).setCellValue(resultSet.getString("productName"));
				row.createCell(5).setCellValue(resultSet.getInt("qty"));
				
				cell = row.createCell(6);
				cell.setCellValue(Float.valueOf(resultSet.getString("unitRate")));
				cellStyle = workbook.createCellStyle();
				cellStyle.setDataFormat(format.getFormat("0.00"));
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(7);
				cell.setCellValue(Float.valueOf(resultSet.getString("sgstPercentage")));
				cellStyle = workbook.createCellStyle();
				cellStyle.setDataFormat(format.getFormat("0.00"));
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(8);
				cell.setCellValue(Float.valueOf(resultSet.getString("cgstPercentage")));
				cellStyle = workbook.createCellStyle();
				cellStyle.setDataFormat(format.getFormat("0.00"));
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(9);
				cell.setCellValue(Float.valueOf(resultSet.getString("amountExTax")));
				cellStyle = workbook.createCellStyle();
				cellStyle.setDataFormat(format.getFormat("0.00"));
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(10);
				cell.setCellValue(Float.valueOf(resultSet.getString("sgstTotal")));
				cellStyle = workbook.createCellStyle();
				cellStyle.setDataFormat(format.getFormat("0.00"));
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(11);
				cell.setCellValue(Float.valueOf(resultSet.getString("cgstTotal")));
				cellStyle = workbook.createCellStyle();
				cellStyle.setDataFormat(format.getFormat("0.00"));
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(12);
				cell.setCellValue(Float.valueOf(resultSet.getString("amount")));
				cellStyle = workbook.createCellStyle();
				cellStyle.setDataFormat(format.getFormat("0.00"));
				cell.setCellStyle(cellStyle);
				
				invoiceDataRow++;
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
	}
	
	/*
	 * 
	 */
	public static void totalRow() {
		
		cell = row.createCell(8);cell.setCellValue("Total(Rs)");
		cellStyle = workbook.createCellStyle();
		font = workbook.createFont();
		font.setBold(true);
		cellStyle.setFont(font);cell.setCellStyle(cellStyle);
		
		cell = row.createCell(9);cell.setCellValue(Float.valueOf(amountExTax));
		cellStyle = workbook.createCellStyle();
		cellStyle.setDataFormat(format.getFormat("0.00"));
		font = workbook.createFont();
		font.setBold(true);
		cellStyle.setFont(font);cell.setCellStyle(cellStyle);
		
		cell = row.createCell(10);cell.setCellValue(Float.valueOf(sgstTotal));
		cellStyle = workbook.createCellStyle();
		cellStyle.setDataFormat(format.getFormat("0.00"));
		font = workbook.createFont();
		font.setBold(true);
		cellStyle.setFont(font);cell.setCellStyle(cellStyle);
		
		cell = row.createCell(11);cell.setCellValue(Float.valueOf(cgstTotal));
		cellStyle = workbook.createCellStyle();
		cellStyle.setDataFormat(format.getFormat("0.00"));
		font = workbook.createFont();
		font.setBold(true);
		cellStyle.setFont(font);cell.setCellStyle(cellStyle);
		
		cell = row.createCell(12);cell.setCellValue(Float.valueOf(amount));
		cellStyle = workbook.createCellStyle();
		cellStyle.setDataFormat(format.getFormat("0.00"));
		font = workbook.createFont();
		font.setBold(true);
		cellStyle.setFont(font);cell.setCellStyle(cellStyle);
		
	}

	/*
	 * 
	 */
	public static void initializeStaticVariables() {
		
		workbook = new XSSFWorkbook();
		invoiceDataSheet = workbook.createSheet("Invoice Data");
		row = null;
		cell = null;
		cellStyle = null;
		font = null;
		invoiceNumber = "";
		invoiceDataRow = 1; firstRow = 1;
		format = workbook.createDataFormat();
		amountExTax = null; sgstTotal = null; cgstTotal = null; amount = null;
	}
}
