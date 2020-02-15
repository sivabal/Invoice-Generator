package com.bill.generator;

import java.text.DecimalFormat;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.bill.beans.Address;
import com.bill.beans.BilledProducts;
import com.bill.utility.Utility;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;
import be.quodlibet.boxable.Row;
import javafx.collections.ObservableList;

public class PDFGenerator {
	
	
	public static float drawTitleTable(String name, Address fromAddress, PDDocument document, PDPage page) throws Exception {
		
		Row<PDPage> row = null;
		Cell<PDPage> cell = null;
		float yPosition = 0;
		try {
			
				float margin = 50;
			// starting y position is whole page height subtracted by top and bottom margin
			    float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
			// we want table across whole page width (subtracted by left and right margin ofcourse)
			    float tableWidth = page.getMediaBox().getWidth() - (2 * margin);

			    boolean drawContent = true;
			    boolean drawLine = false;
			    float bottomMargin = 0;
			// y position is your coordinate of top left corner of the table
			    yPosition =  760;

			    BaseTable table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, page, drawLine, drawContent);
			    
			   
			    row = table.createRow(8f);  
			    cell = row.createCell(100, "TAX INVOICE");
			    cell.setFontSize(14);
			    cell.setAlign(HorizontalAlignment.RIGHT);
			    cell.setFont(PDType1Font.COURIER_BOLD);
			    cell.setBottomPadding(0);
			    cell.setTopPadding(0);
			    
			    row = table.createRow(5f);  
			    cell = row.createCell(50, name);
			    cell.setFont(PDType1Font.COURIER_BOLD);
			    cell.setFontSize(9);
			    cell.setBottomPadding(0);
			    cell.setTopPadding(0);
			    
				row = table.createRow(5f);  
				cell = row.createCell(50, fromAddress.getAddressLine1());
				cell.setFont(PDType1Font.COURIER);
				cell.setFontSize(9);
				cell.setBottomPadding(0);
				cell.setTopPadding(0);
			    
			    
			    if(fromAddress.getAddressLine2() != "") {
				    row = table.createRow(5f);  
				    cell = row.createCell(50, fromAddress.getAddressLine2());
				    cell.setFont(PDType1Font.COURIER);
				    cell.setFontSize(9);
				    cell.setBottomPadding(0);
				    cell.setTopPadding(0);
			    }
			    

				row = table.createRow(5f);  
				cell = row.createCell(50, fromAddress.getCity());
				cell.setFont(PDType1Font.COURIER);
				cell.setFontSize(9);
				cell.setBottomPadding(0);
				cell.setTopPadding(0);
			    
			    row = table.createRow(5f);  
			    cell = row.createCell(50, fromAddress.getDistrict() + " - " + fromAddress.getPincode());
			    cell.setFont(PDType1Font.COURIER);
			    cell.setFontSize(9);
			    cell.setBottomPadding(0);
			    cell.setTopPadding(0);

			    row = table.createRow(5f);  
				cell = row.createCell(50, fromAddress.getState());
				cell.setFont(PDType1Font.COURIER);
				cell.setFontSize(9);
				cell.setBottomPadding(0);
				cell.setTopPadding(0);
			    

				row = table.createRow(5f);  
				cell = row.createCell(50, (fromAddress.getTelephone() != "")?fromAddress.getTelephone()+" / "+fromAddress.getMobile():fromAddress.getMobile());
				cell.setFont(PDType1Font.COURIER);
				cell.setFontSize(9);
				cell.setBottomPadding(0);
				cell.setTopPadding(0);
			   
				if(fromAddress.getGstNo() != "") {
				    row = table.createRow(5f);  
				    cell = row.createCell(50, fromAddress.getGstNo());
				    cell.setFont(PDType1Font.COURIER);
				    cell.setFontSize(9);
				    cell.setBottomPadding(0);
				    cell.setTopPadding(0);
				}
			    
			    yPosition = table.draw();
			    
			    
		} catch (Exception e) {
			throw e;
		}
		
		return yPosition;
		
	}
	
	
	public static float drawInvoiceTable(String ShopName, Address toAddress, String invoiceNumber, String date, String placeOfSupply, PDDocument document, PDPage page, float yPosition) throws Exception {
		//yPosition -= 5;
		float YPositionLeft = yPosition, YPositionRight = yPosition-20;
		
		try {
			PDPageContentStream contentStream = new PDPageContentStream(document, page, AppendMode.APPEND, false) ;
			
			contentStream.beginText();contentStream.setFont(PDType1Font.COURIER, 10);contentStream.newLineAtOffset(55, YPositionLeft);
			contentStream.showText("____________________________________________________________________________________");
			contentStream.endText();YPositionLeft-=20;
			
			contentStream.beginText();contentStream.setFont(PDType1Font.COURIER, 10);contentStream.newLineAtOffset(55, YPositionLeft);
			contentStream.showText("Invoice Number :");
			contentStream.endText();
			
			contentStream.beginText();contentStream.setFont(PDType1Font.COURIER_BOLD, 10);contentStream.newLineAtOffset(170, YPositionLeft);
			contentStream.showText(invoiceNumber);
			contentStream.endText();YPositionLeft-=20;
			
			contentStream.beginText();contentStream.setFont(PDType1Font.COURIER, 10);contentStream.newLineAtOffset(55, YPositionLeft);
			contentStream.showText("Invoice Date :");
			contentStream.endText();
			
			contentStream.beginText();contentStream.setFont(PDType1Font.COURIER_BOLD, 10);contentStream.newLineAtOffset(170, YPositionLeft);
			contentStream.showText(date);
			contentStream.endText();YPositionLeft-=20;
			
			contentStream.beginText();contentStream.setFont(PDType1Font.COURIER, 10);contentStream.newLineAtOffset(55, YPositionLeft);
			contentStream.showText("Place of Supply :");
			contentStream.endText();
			
			contentStream.beginText();contentStream.setFont(PDType1Font.COURIER_BOLD, 10);contentStream.newLineAtOffset(170, YPositionLeft);
			contentStream.showText(placeOfSupply);
			contentStream.endText();
			
			// printing "to Address" starts here
			contentStream.beginText();contentStream.setFont(PDType1Font.COURIER, 9);contentStream.newLineAtOffset(300, YPositionRight);
			contentStream.showText("Billing Address :");
			contentStream.endText();
			
				
			contentStream.beginText();contentStream.setFont(PDType1Font.COURIER_BOLD, 10);contentStream.newLineAtOffset(400, YPositionRight);
			contentStream.showText(ShopName);
			contentStream.endText();
			
			if(!ShopName.equals("Counter Sales")) {
				
				YPositionRight -= 10;
				contentStream.beginText();contentStream.setFont(PDType1Font.COURIER, 9);contentStream.newLineAtOffset(400, YPositionRight);
				contentStream.showText(toAddress.getAddressLine1());
				contentStream.endText();YPositionRight -= 10;
				
				if(toAddress.getAddressLine2() != "") {
					contentStream.beginText();contentStream.setFont(PDType1Font.COURIER, 9);contentStream.newLineAtOffset(400, YPositionRight);
					contentStream.showText(toAddress.getAddressLine2());
					contentStream.endText();YPositionRight -= 10;
				}
				
				contentStream.beginText();contentStream.setFont(PDType1Font.COURIER, 9);contentStream.newLineAtOffset(400, YPositionRight);
				contentStream.showText(toAddress.getCity());
				contentStream.endText();YPositionRight -= 10;
				
				contentStream.beginText();contentStream.setFont(PDType1Font.COURIER, 9);contentStream.newLineAtOffset(400, YPositionRight);
				contentStream.showText(toAddress.getDistrict() + " - " + toAddress.getPincode());
				contentStream.endText();YPositionRight -= 10;
				
				contentStream.beginText();contentStream.setFont(PDType1Font.COURIER, 9);contentStream.newLineAtOffset(400, YPositionRight);
				contentStream.showText(toAddress.getState());
				contentStream.endText();YPositionRight -= 10;
				
				contentStream.beginText();contentStream.setFont(PDType1Font.COURIER, 9);contentStream.newLineAtOffset(400, YPositionRight);
				contentStream.showText((toAddress.getTelephone() != "")?toAddress.getTelephone()+" / "+toAddress.getMobile():toAddress.getMobile());
				contentStream.endText();
				
				if(toAddress.getGstNo() != "") {
					YPositionRight -= 10;
					contentStream.beginText();contentStream.setFont(PDType1Font.COURIER, 9);contentStream.newLineAtOffset(400, YPositionRight);
					contentStream.showText(toAddress.getGstNo());
					contentStream.endText();
				}
			}
			
			
			contentStream.close();
			
		} catch (Exception e) {
			throw e;
		}
		
		return (YPositionLeft < YPositionRight)?YPositionLeft:YPositionRight;
	}
	public static float drawProductsTable(ObservableList<BilledProducts> billRow, PDDocument document, PDPage page, float yPosition) throws Exception {
		
		/*
		 * Content Area in pdf --> Left to Right : 70-500	, Top to Bottom : 730-70 	
		 */
		yPosition -= 10;
		Cell<PDPage> cell = null;
		try {
			

				float margin = 50;
			// starting y position is whole page height subtracted by top and bottom margin
			    float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
			// we want table across whole page width (subtracted by left and right margin ofcourse)
			    float tableWidth = page.getMediaBox().getWidth() - (2 * margin);

			    boolean drawContent = true;
			    boolean drawLine = true;
			    float bottomMargin = 70;
			// y position is your coordinate of top left corner of the table
			   

			    BaseTable table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, page, drawLine, drawContent);
			    
			    Row<PDPage> headerRowOne = table.createRow(10f);
			    headerRowOne.createCell(41, "");
			    cell = headerRowOne.createCell(7, "Qty");cell.setFontSize(9);cell.setAlign(HorizontalAlignment.CENTER);cell.setFont(PDType1Font.COURIER_BOLD);
			    cell = headerRowOne.createCell(9, "Rate");cell.setFontSize(9);cell.setAlign(HorizontalAlignment.CENTER);cell.setFont(PDType1Font.COURIER_BOLD);
			    cell = headerRowOne.createCell(16, "SGST");cell.setFontSize(9);cell.setAlign(HorizontalAlignment.CENTER);cell.setFont(PDType1Font.COURIER_BOLD);
			    cell = headerRowOne.createCell(16, "CGST");cell.setFontSize(9);cell.setAlign(HorizontalAlignment.CENTER);cell.setFont(PDType1Font.COURIER_BOLD);
			    cell = headerRowOne.createCell(11, "Amount");cell.setFontSize(9);cell.setAlign(HorizontalAlignment.CENTER);cell.setFont(PDType1Font.COURIER_BOLD);
			    yPosition -= 20;
			    
			    
			    Row<PDPage> headerRowTwo = table.createRow(10f);
			    cell = headerRowTwo.createCell(7, "S.no");cell.setFontSize(9);cell.setAlign(HorizontalAlignment.CENTER);cell.setFont(PDType1Font.COURIER_BOLD);
			    cell = headerRowTwo.createCell(34, "Item Name");cell.setFontSize(9);cell.setAlign(HorizontalAlignment.CENTER);cell.setFont(PDType1Font.COURIER_BOLD);
			    cell = headerRowTwo.createCell(7, "(Kg)");cell.setFontSize(9);cell.setAlign(HorizontalAlignment.CENTER);cell.setFont(PDType1Font.COURIER_BOLD);
			    cell = headerRowTwo.createCell(9, "(Rs)");cell.setFontSize(9);cell.setAlign(HorizontalAlignment.CENTER);cell.setFont(PDType1Font.COURIER_BOLD);
			    cell = headerRowTwo.createCell(7, "(%)");cell.setFontSize(9);cell.setAlign(HorizontalAlignment.CENTER);cell.setFont(PDType1Font.COURIER_BOLD);
			    cell = headerRowTwo.createCell(9, "(Rs)");cell.setFontSize(9);cell.setAlign(HorizontalAlignment.CENTER);cell.setFont(PDType1Font.COURIER_BOLD);
			    cell = headerRowTwo.createCell(7, "(%)");cell.setFontSize(9);cell.setAlign(HorizontalAlignment.CENTER);cell.setFont(PDType1Font.COURIER_BOLD);
			    cell = headerRowTwo.createCell(9, "(Rs)");cell.setFontSize(9);cell.setAlign(HorizontalAlignment.CENTER);cell.setFont(PDType1Font.COURIER_BOLD);
			    cell = headerRowTwo.createCell(11, "(Rs)");cell.setFontSize(9);cell.setAlign(HorizontalAlignment.CENTER);cell.setFont(PDType1Font.COURIER_BOLD);
			    yPosition -= 20;

			    for(BilledProducts billedProducts : billRow) {
			    	Row<PDPage> productRow = table.createRow(10f);
			    	cell = productRow.createCell(7, billedProducts.getSno()+"");cell.setFontSize(8);cell.setFont(PDType1Font.COURIER);
			    	cell = productRow.createCell(34, billedProducts.getItemName().getText());cell.setFontSize(8);cell.setFont(PDType1Font.COURIER);
			    	cell = productRow.createCell(7, billedProducts.getQuantity().getText());cell.setFontSize(8);cell.setFont(PDType1Font.COURIER);
			    	cell = productRow.createCell(9, billedProducts.getUnitRate().getText());cell.setFontSize(8);cell.setFont(PDType1Font.COURIER);
			    	cell = productRow.createCell(7, billedProducts.getSgst().getText());cell.setFontSize(8);cell.setFont(PDType1Font.COURIER);
			    	cell = productRow.createCell(9, new DecimalFormat("#.##").format(billedProducts.getSgstTotal()));cell.setFontSize(8);cell.setFont(PDType1Font.COURIER);
			    	cell = productRow.createCell(7, billedProducts.getCgst().getText());cell.setFontSize(8);cell.setFont(PDType1Font.COURIER);
			    	cell = productRow.createCell(9, new DecimalFormat("#.##").format(billedProducts.getCgstTotal()));cell.setFontSize(8);cell.setFont(PDType1Font.COURIER);
			    	cell = productRow.createCell(11, billedProducts.getAmount().getText());cell.setFontSize(8);cell.setFont(PDType1Font.COURIER);
			    	 yPosition -= 20;
			    }

	
			   table.draw();
			    
		} catch (Exception e) {
			throw e;
		}
		
		 return yPosition;
	}
	
	public static void drawTotalTable(Float orderAmount, Float sgstTotal, Float cgstTotal, Integer total,
			PDDocument document, PDPage page, float yPosition) throws Exception {
		
		Row<PDPage> row = null;
		Cell<PDPage> cell = null;
	
		try {
			yPosition -= 20;
			
			PDPageContentStream contentStream = new PDPageContentStream(document, page, AppendMode.APPEND, false) ;
			

			contentStream.beginText();contentStream.setFont(PDType1Font.COURIER, 10);contentStream.newLineAtOffset(408, yPosition);
			contentStream.showText("Order Amount :");
			contentStream.endText();
			
			contentStream.beginText();contentStream.setFont(PDType1Font.COURIER, 10);contentStream.newLineAtOffset(500, yPosition);
			contentStream.showText("Rs." + new DecimalFormat("#.##").format(orderAmount));
			contentStream.endText();yPosition -= 20;
			
			contentStream.beginText();contentStream.setFont(PDType1Font.COURIER, 10);contentStream.newLineAtOffset(450, yPosition);
			contentStream.showText("SGST  :");
			contentStream.endText();
			
			contentStream.beginText();contentStream.setFont(PDType1Font.COURIER, 10);contentStream.newLineAtOffset(500, yPosition);
			contentStream.showText("Rs." + new DecimalFormat("#.##").format(sgstTotal));
			contentStream.endText();yPosition -= 20;
			
			contentStream.beginText();contentStream.setFont(PDType1Font.COURIER, 10);contentStream.newLineAtOffset(450, yPosition);
			contentStream.showText("CGST  :");
			contentStream.endText();
			
			contentStream.beginText();contentStream.setFont(PDType1Font.COURIER, 10);contentStream.newLineAtOffset(500, yPosition);
			contentStream.showText("Rs." + new DecimalFormat("#.##").format(cgstTotal));
			contentStream.endText();yPosition -= 20;
			
			contentStream.beginText();contentStream.setFont(PDType1Font.COURIER_BOLD, 10);contentStream.newLineAtOffset(450, yPosition);
			contentStream.showText("Total :");
			contentStream.endText();
			
			contentStream.beginText();contentStream.setFont(PDType1Font.COURIER_BOLD, 10);contentStream.newLineAtOffset(500, yPosition);
			contentStream.showText("Rs." + total.toString());
			contentStream.endText();
			
			
			
			
			float margin = 50;
			float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
			float tableWidth = 350;
			boolean drawContent = true;
			boolean drawLine = false;
			float bottomMargin = 70;
			   
			BaseTable table = new BaseTable(yPosition+30, yStartNewPage, bottomMargin, tableWidth, margin, document, page, drawLine, drawContent);
			    
			row = table.createRow(10f);
			cell = row.createCell(100, "Total in Words : ");cell.setFontSize(9);cell.setFont(PDType1Font.COURIER);
			
			row = table.createRow(10f);
			cell = row.createCell(100, Utility.rupeeInWords(total));cell.setFontSize(10);cell.setFont(PDType1Font.COURIER_BOLD);
			
			table.draw();
			
			yPosition -= 40;
			contentStream.beginText();contentStream.setFont(PDType1Font.COURIER, 10);contentStream.newLineAtOffset(420, yPosition);
			contentStream.showText(" _________________");
			contentStream.endText();yPosition -= 15;
			
			contentStream.beginText();contentStream.setFont(PDType1Font.COURIER, 10);contentStream.newLineAtOffset(420, yPosition);
			contentStream.showText("Authorized Signature");
			contentStream.endText();
			
			contentStream.close();
			
		} catch (Exception e) {
			throw e;
		}
	}

}
