package com.bill.excelwindow;

import com.bill.excelgenerator.ExcelGenerator;
import com.bill.popus.ShowPopups;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert.AlertType;

public class ExcelController {
	
	@FXML private DatePicker fromDate;	
	@FXML private DatePicker toDate;
	
	@FXML
	public void generateExcel() {
		
		if(fromDate.getValue().isAfter(toDate.getValue())) {
			ShowPopups.showPopups(AlertType.ERROR, "Error....", "From date should not come after To date..");
			return;
		}
		
		ExcelGenerator.generateExcel(fromDate.getValue(), toDate.getValue());
	}

}
