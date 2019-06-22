package com.bill.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bill.validator.FromDatabasevalidator;

public class ExcelGenerator {

	public static void generateExcel(LocalDate fromDate, LocalDate toDate) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet invoiceDataSheet = workbook.createSheet("Invoice Data");
		
		int fromDateDiff = (int)LocalDate.of(2019,06,01).until(fromDate, ChronoUnit.DAYS);
		int toDateDiff = (int)LocalDate.of(2019,06,01).until(toDate, ChronoUnit.DAYS);
		
		XSSFRow row = invoiceDataSheet.createRow(0);
		row.createCell(0).setCellValue("Invoice Number");
		row.createCell(1).setCellValue("Date");
		row.createCell(2).setCellValue("Bill From");
		row.createCell(3).setCellValue("Bill To");
		row.createCell(4).setCellValue("Order Amount(Rs)");
		row.createCell(5).setCellValue("Sgst Amount(Rs)");
		row.createCell(6).setCellValue("Cgst Amount(Rs)");
		row.createCell(7).setCellValue("Total Amount(Rs)");
		
		FromDatabasevalidator.getInvoiceData(invoiceDataSheet, fromDateDiff, toDateDiff);
		try {
			workbook.write(new FileOutputStream("C:\\Users\\welcome\\Desktop\\excel\\Excel.xlsx"));
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
