package com.bill.excelwindow;

import com.bill.excel.ExcelGenerator;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

public class ExcelController {
	
	@FXML private DatePicker fromDate;	
	@FXML private DatePicker toDate;
	
	@FXML
	public void generateExcel() {
		ExcelGenerator.generateExcel(fromDate.getValue(), toDate.getValue());
	}

}
