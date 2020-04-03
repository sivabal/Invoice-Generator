package com.bill.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.bill.popus.ShowPopups;
import com.bill.utility.Utility;
import com.bill.validator.ToDatabaseValidator;
import com.bill.validator.ValidateUserInputs;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class GoodsProducedController implements Initializable {

	@FXML private ComboBox<String> itemName;
	@FXML private DatePicker date;
	@FXML private TextField lotNo;
	@FXML private TextField goodsProduced;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		itemName.getItems().addAll(Utility.productInfo.keySet().toArray(new String[Utility.productInfo.size()]));
		date.setValue(LocalDate.now());
	}
	
	@FXML
	public void addGoodsProduced() {
		try {
			if(ValidateUserInputs.validateGoodsProducedInfo(itemName.getValue(), date.getValue(), lotNo.getText().trim(), goodsProduced.getText().trim())) {
				
				ToDatabaseValidator.addGoodsProduced(itemName.getValue(), date.getValue(), lotNo.getText(), goodsProduced.getText());
				
				ShowPopups.showPopups(AlertType.INFORMATION, "Data added Successfully.", "");
			}
			
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}

}
