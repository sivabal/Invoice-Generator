package com.bill.excelwindow;

import com.bill.excelgenerator.ExcelGenerator;
import com.bill.validator.ValidateUserInputs;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

public class ExcelController {
	
	@FXML private DatePicker fromDate;	
	@FXML private DatePicker toDate;
	
	@FXML
	public void generateExcel() {
		if(ValidateUserInputs.validateDates(fromDate.getValue(), toDate.getValue())) {
			ExcelGenerator.generateExcel(fromDate.getValue(), toDate.getValue());
		}
	}

}
