package com.bill.validator;

import com.bill.exception.EditProductException;
import com.bill.popus.ShowPopups;
import com.bill.utility.Regex;
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
		}catch (NumberFormatException e) {
			e.printStackTrace();
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

	public static boolean ValidateAddressDetails(String newShopName, String addressLine1, String addressLine2, String city, String district,
			String state, String pincode, String telephone, String mobile, String gstNo) {
		
		try {
			if(newShopName.trim().equals(""))
				throw new EditProductException("Please Provide valid Shop Name.");
			if(addressLine1.trim().equals(""))
				throw new EditProductException("Please Provide valid Address Line 1.");
			if(city.trim().equals(""))
				throw new EditProductException("Please Provide valid City.");
			if(district.trim().equals(""))
				throw new EditProductException("Please Provide valid District.");
			if(state.trim().equals(""))
				throw new EditProductException("Please Provide valid State.");
			if(pincode.trim().equals(""))
				throw new EditProductException("Please Provide valid Pincode.");
			if(mobile.trim().equals(""))
				throw new EditProductException("Please Provide valid Mobile Number.");
			
			return true;
			
		}catch (EditProductException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.getMessage(), "");
		}catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		return false;
		
	}

	public static boolean ValidateShopName(String oldShopName) {
		
		try {
			if(oldShopName == null)
				throw new EditProductException("Please Select the Shop Name.");
			
			return true;
			
		}catch (EditProductException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.getMessage(), "");
		}catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
