package com.bill.validator;

import java.time.LocalDate;

import com.bill.beans.BilledProducts;
import com.bill.exception.DeleteInvoiceException;
import com.bill.exception.EditAddressException;
import com.bill.exception.EditProductException;
import com.bill.exception.ExcelGeneratorException;
import com.bill.exception.InvoiceException;
import com.bill.popus.ShowPopups;
import com.bill.utility.Regex;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;

public class ValidateUserInputs {

	public static boolean validateProductDetails(String prodName, String unitRate, String sgst, String cgst) {
		
		try {
			if( prodName.equals("") || unitRate.equals("") || sgst.equals("") || cgst.equals(""))
				throw new EditProductException("Please Provide values for all the fields.");
			if(prodName.trim().equals(""))
				throw new EditProductException("Please Provide Valid Product Name.");
			if(!Regex.regexProdRate.matcher(unitRate).matches())
				throw new EditProductException("Please Provide Valid Unit Rate. Eg. 0.53, 500, 3.7");
			if(!Regex.regexProdSgst.matcher(sgst).matches())
				throw new EditProductException("Please Provide Valid Sgst Percentage. Eg. 0.53, 500, 3.7");
			if(!Regex.regexProdCgst.matcher(cgst).matches())
				throw new EditProductException("Please Provide Valid Cgst Percentage. Eg. 0.53, 500, 3.7");
			
			return true;
		}catch (EditProductException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.getMessage(), "");
		}
		
		return false;
		
	}
	
	public static boolean validateProuctName(String prodName) {

		try {
			
			if(prodName == null)
				throw new EditProductException("Please Select any Product Name.");
			
			return true;
			
		}catch (EditProductException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.getMessage(), "");
		}
		
		return false;
	}

	public static boolean validateAddressDetails(String oldShopName , String newShopName, String addressLine1, String addressLine2, String city, String district,
			String state, String pincode, String telephone, String mobile, String gstNo) {
		
		try {
			
			//For 'Bill TO Address' only
			if(oldShopName.equals("Counter Sales"))
				throw new EditAddressException("'Counter Sales' Cannot be Updated, Inserted or Deleted...");
			
			if(newShopName.trim().equals(""))
				throw new EditAddressException("Please Provide valid Shop Name.");
			if(addressLine1.trim().equals(""))
				throw new EditAddressException("Please Provide valid Address Line 1.");
			if(city.trim().equals(""))
				throw new EditAddressException("Please Provide valid City.");
			if(district.trim().equals(""))
				throw new EditAddressException("Please Provide valid District.");
			if(state.trim().equals(""))
				throw new EditAddressException("Please Provide valid State.");
			if(pincode.trim().equals(""))
				throw new EditAddressException("Please Provide valid Pincode.");
			if(mobile.trim().equals(""))
				throw new EditAddressException("Please Provide valid Mobile Number.");
			
			return true;
			
		}catch (EditAddressException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.getMessage(), "");
		}
		
		return false;
		
	}

	public static boolean validateShopName(String oldShopName) {
		try {
			if(oldShopName == null)
				throw new EditAddressException("Please Select the Shop Name.");
			
			//For 'Bill TO Address' only
			if(oldShopName.equals("Counter Sales"))
				throw new EditAddressException("'Counter Sales' Cannot be Updated, Inserted or Deleted...");
			
			return true;
		}catch (EditAddressException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.getMessage(), "");
		}
		
		return false;
	}
	
	public static boolean validateInvoiceDetails(String fromComboBox, String toComboBox, String invoiceNumber) {
		
		try {
			if(fromComboBox == null)
				throw new InvoiceException("Please Select 'Bill From' Address.");
			if(toComboBox == null)
				throw new InvoiceException("Please Select 'Bill To' Address.");
			if(!Regex.regexInvoiceNumber.matcher(invoiceNumber).matches())
				throw new InvoiceException("Please provide valid invoice number, starting with 'INV-' followed by valid four"
						+ " digit number above. Eg. INV-0001, INV-0245");
			
			return true;
			
		}catch (InvoiceException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.getMessage(), "");
		}
		
		return false;
		
	}

	public static boolean validateBilledRow(ObservableList<BilledProducts> billRow) {
		
		try {
			
			for(BilledProducts b: billRow) {
				if(b.getItemName().getText().equals("") || b.getQuantity().getText().equals("")) 
					throw new InvoiceException("Blank entries are not Allowed...");
				if(!Regex.regexQantity.matcher(b.getQuantity().getText()).matches()) 
					throw new InvoiceException("Incorrect Quantity for the product '" + b.getItemName().getText() + "'");
			
			}
			
			return true;
			
		}catch (InvoiceException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.getMessage(), "");
		}
		return false;
	}

	public static boolean validateDates(LocalDate fromDate, LocalDate toDate) {
		
		try {
			
			if(fromDate == null || toDate == null) 
				throw new ExcelGeneratorException("Please Povide 'From date' and 'To date'...");
			if(fromDate.isAfter(toDate))
				throw new ExcelGeneratorException("'From date' Can't come after 'To date'...");
			
			return true;
			
		}catch (ExcelGeneratorException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.getMessage(), "");
		}
		return false;
		
	}

	public static boolean validateInvoiceNumber(String invoice) {
		try {
			if(invoice == null)
				throw new DeleteInvoiceException("Please Select the Invoice.");
			
			return true;
		}catch (DeleteInvoiceException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.getMessage(), "");
		}
		
		return false;
	}

}
