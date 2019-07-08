package com.bill.edittoaddress;

import java.net.URL;
import java.util.ResourceBundle;

import com.bill.beans.Address;
import com.bill.popus.ShowPopups;
import com.bill.utility.Utility;
import com.bill.validator.FromDatabasevalidator;
import com.bill.validator.ToDatabaseValidator;
import com.bill.validator.ValidateUserInputs;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class EditToAddressController implements Initializable {

	@FXML private TextField insertShopName;
	@FXML private TextField insertAddressLine1;
	@FXML private TextField insertAddressLine2;
	@FXML private TextField insertCity;
	@FXML private TextField insertDistrict;
	@FXML private TextField insertState;
	@FXML private TextField insertPincode;
	@FXML private TextField insertTelephone;
	@FXML private TextField insertMobile;
	@FXML private TextField insertGstNo;
	
	
	@FXML private ComboBox<String> updateOldShopName;
	@FXML private TextField updateNewShopName;
	@FXML private TextField updateAddressLine1;
	@FXML private TextField updateAddressLine2;
	@FXML private TextField updateCity;
	@FXML private TextField updateDistrict;
	@FXML private TextField updateState;
	@FXML private TextField updatePincode;
	@FXML private TextField updateTelephone;
	@FXML private TextField updateMobile;
	@FXML private TextField updateGstNo;
	@FXML private Button updateAddressBtn;
	
	@FXML private ComboBox<String> deleteShopName;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		updateOldShopName.getItems().addAll(Utility.toAddressShopNames);
		deleteShopName.getItems().addAll(Utility.toAddressShopNames);
		
		updateNewShopName.setDisable(true);
		updateAddressLine1.setDisable(true);
		updateAddressLine2.setDisable(true);
		updateCity.setDisable(true);
		updateDistrict.setDisable(true);
		updateState.setDisable(true);
		updatePincode.setDisable(true);
		updateTelephone.setDisable(true);
		updateMobile.setDisable(true);
		updateGstNo.setDisable(true);
		updateAddressBtn.setDisable(true);
	}
	
	@FXML
	public void getAddress() {
		
		if(ValidateUserInputs.validateShopName(updateOldShopName.getValue())) {
			
			Address address = FromDatabasevalidator.getToAddress(updateOldShopName.getValue());
			
			updateNewShopName.setText(address.getShopName());
			updateAddressLine1.setText(address.getAddressLine1());
			updateAddressLine2.setText(address.getAddressLine2());
			updateCity.setText(address.getCity());
			updateDistrict.setText(address.getDistrict());
			updateState.setText(address.getState());
			updatePincode.setText(address.getPincode());
			updateTelephone.setText(address.getTelephone());
			updateMobile.setText(address.getMobile());
			updateGstNo.setText(address.getGstNo());
			
			updateNewShopName.setDisable(false);
			updateAddressLine1.setDisable(false);
			updateAddressLine2.setDisable(false);
			updateCity.setDisable(false);
			updateDistrict.setDisable(false);
			updateState.setDisable(false);
			updatePincode.setDisable(false);
			updateTelephone.setDisable(false);
			updateMobile.setDisable(false);
			updateGstNo.setDisable(false);
			updateAddressBtn.setDisable(false);
		}
	}
	
	@FXML
	public void updateAddress() {
		
		if(ValidateUserInputs.validateAddressDetails(updateNewShopName.getText(), updateAddressLine1.getText(),
				updateAddressLine2.getText(), updateCity.getText(), updateDistrict.getText(), updateState.getText(), 
				updatePincode.getText(), updateTelephone.getText(), updateMobile.getText(), updateGstNo.getText())) {
			
			ToDatabaseValidator.updateToAddress(updateOldShopName.getValue(), updateNewShopName.getText(), updateAddressLine1.getText(),
					updateAddressLine2.getText(), updateCity.getText(), updateDistrict.getText(), updateState.getText(), 
					updatePincode.getText(), updateTelephone.getText(), updateMobile.getText(), updateGstNo.getText());
			
			ShowPopups.showPopups(AlertType.INFORMATION, "Success....", "Address Updated Successfully....");
		}
	}
	
	@FXML
	public void insertAddress() {
		
		if(ValidateUserInputs.validateAddressDetails(insertShopName.getText(), insertAddressLine1.getText(),
				insertAddressLine2.getText(), insertCity.getText(), insertDistrict.getText(), insertState.getText(), 
				insertPincode.getText(), insertTelephone.getText(), insertMobile.getText(), insertGstNo.getText())) {
			
			ToDatabaseValidator.insertToAddress(insertShopName.getText(), insertAddressLine1.getText(),
					insertAddressLine2.getText(), insertCity.getText(), insertDistrict.getText(), insertState.getText(), 
					insertPincode.getText(), insertTelephone.getText(), insertMobile.getText(), insertGstNo.getText());
			
			ShowPopups.showPopups(AlertType.INFORMATION, "Success....", "Address Inserted to Database Successfully....");
	
		}
	}
	
	@FXML
	public void deleteAddress() {
		
		if(ValidateUserInputs.validateShopName(deleteShopName.getValue())) {
			
			ToDatabaseValidator.deleteToAddress(deleteShopName.getValue());
			ShowPopups.showPopups(AlertType.INFORMATION, "Success....", "Address Deleted from the Database Successfully....");
		}
	}
}
