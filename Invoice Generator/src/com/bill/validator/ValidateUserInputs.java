package com.bill.validator;

import com.bill.exception.EditProductException;
import com.bill.popus.ShowPopups;
import com.bill.utility.Regex;
import javafx.scene.control.Alert.AlertType;

public class ValidateUserInputs {

	public static boolean validateProductDetails(String prodId, String prodName, String unitRate, String sgst, String cgst) {
		
		try {
			
			if(!Regex.regexProdId.matcher(prodId).matches())
				throw new EditProductException("Please Provide a Three digit(minimum) Number for Product Id.");
			if(!Regex.regexProdName.matcher(prodName).matches())
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
	
	public static boolean validateProuctId(String prodId) {

		try {
			
			if(!Regex.regexProdId.matcher(prodId).matches())
				throw new EditProductException("Please Provide a Three digit(minimum) Number for Product Id.");
			
			return true;
			
		}catch (EditProductException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.getMessage(), "");
		}
		
		return false;
	}
}
