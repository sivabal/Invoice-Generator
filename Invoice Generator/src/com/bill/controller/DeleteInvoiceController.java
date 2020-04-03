package com.bill.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.bill.popus.ShowPopups;
import com.bill.validator.FromDatabasevalidator;
import com.bill.validator.ToDatabaseValidator;
import com.bill.validator.ValidateUserInputs;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;

public class DeleteInvoiceController implements Initializable{

	@FXML private ComboBox<String> deleteInvoice;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			deleteInvoice.getItems().addAll(FromDatabasevalidator.getInvoiceNumbers());
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}

	@FXML
	public void deleteInvoice() {
		try {
			if(ValidateUserInputs.validateInvoiceNumber(deleteInvoice.getValue())
					&& ShowPopups.showPopups(AlertType.CONFIRMATION, "Are you sure want to delete this Invoice..", "")) {
				
				ToDatabaseValidator.deleteInvoice(deleteInvoice.getValue());
				
				deleteInvoice.getItems().clear();
				deleteInvoice.getItems().addAll(FromDatabasevalidator.getInvoiceNumbers());
				
				ShowPopups.showPopups(AlertType.INFORMATION, "Invoice Successfully deleted from the database....", "");
			}
		} catch (SQLException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
		
	}
	
}
