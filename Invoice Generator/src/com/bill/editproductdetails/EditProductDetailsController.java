package com.bill.editproductdetails;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

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

public class EditProductDetailsController implements Initializable{
	
	
	@FXML private TextField insertProdId;
	@FXML private TextField insertProdName;
	@FXML private TextField insertUnitRate;
	@FXML private TextField insertSgst;
	@FXML private TextField insertCgst;
	
	@FXML private Button updateProductBtn;
	
	@FXML private ComboBox<String> updateOldProdName;
	@FXML private TextField updateNewProdName;
	@FXML private TextField updateUnitRate;
	@FXML private TextField updateSgst;
	@FXML private TextField updateCgst;
	
	@FXML private ComboBox<String> deleteProductName;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		updateProductBtn.setDisable(true);
		updateNewProdName.setDisable(true);
		updateUnitRate.setDisable(true);
		updateSgst.setDisable(true);
		updateCgst.setDisable(true);
		
		updateOldProdName.getItems().addAll(Utility.productInfo.keySet().toArray(new String[Utility.productInfo.size()]));
		deleteProductName.getItems().addAll(updateOldProdName.getItems());
		insertProdId.setText(FromDatabasevalidator.getLastProductId());
	}
	
	@FXML
	public void insertProduct() {
		
		try {
			if(ValidateUserInputs.validateProductDetails(insertProdName.getText(),
					insertUnitRate.getText(), insertSgst.getText(), insertCgst.getText())
					&& ShowPopups.showPopups(AlertType.CONFIRMATION, "Are you sure want to insert this Product..", "")) {
				
				ToDatabaseValidator.insertProduct(Integer.parseInt(insertProdId.getText()), insertProdName.getText(),
						insertUnitRate.getText(), insertSgst.getText(), insertCgst.getText());
					
				ShowPopups.showPopups(AlertType.INFORMATION,"Product Inserted to Database Successfully....", "");
			}
		} catch (SQLException e) {
			if(e.getMessage().contains("UNIQUE")) {
				if(e.getMessage().contains("prodId"))
					ShowPopups.showPopups(AlertType.ERROR, "Product id is same as the existing one. Please close the window and try again.", "");
				if(e.getMessage().contains("prodName"))
					ShowPopups.showPopups(AlertType.ERROR, "Product Name is same as the existing one. Please provide a new product name.", "");
			}
			else
				ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		} catch (NumberFormatException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
			
		
	}
	
	@FXML
	public void getProductDetails() {

		try {
			if(ValidateUserInputs.validateProuctName(updateOldProdName.getValue())) {
				FromDatabasevalidator.getProduct(updateOldProdName.getValue(), updateNewProdName, updateUnitRate, updateSgst, updateCgst);
				
				updateNewProdName.setDisable(false);
				updateUnitRate.setDisable(false);
				updateSgst.setDisable(false);
				updateCgst.setDisable(false);
				updateProductBtn.setDisable(false);
			}
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}

	}
	
	@FXML
	public void updateProduct() {
	
		try {
			if(ValidateUserInputs.validateProductDetails(updateNewProdName.getText(),
					updateUnitRate.getText(), updateSgst.getText(), updateCgst.getText())
					&& ShowPopups.showPopups(AlertType.CONFIRMATION, "Are you sure want to update this Product..", "")) {
				
				ToDatabaseValidator.updateProduct(updateOldProdName.getValue(), updateNewProdName.getText(),
						updateUnitRate.getText(), updateSgst.getText(), updateCgst.getText());
				
				ShowPopups.showPopups(AlertType.INFORMATION, "Product Details Updated Successfully....", "");
			}
		} catch (SQLException e) {
			if(e.getMessage().contains("UNIQUE")) {
				if(e.getMessage().contains("prodName"))
					ShowPopups.showPopups(AlertType.ERROR, "Product Name is same as the existing one. Please provide a new product name.", "");
				}
				else
					ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}
		
	@FXML
	public void deleteProduct() {
		try {
			if(ValidateUserInputs.validateProuctName(deleteProductName.getValue())
					&& ShowPopups.showPopups(AlertType.CONFIRMATION, "Are you sure want to delete this Product..", "")) {
				
				ToDatabaseValidator.deleteProduct(deleteProductName.getValue());
				ShowPopups.showPopups(AlertType.INFORMATION, "Product details Successfully deleted from the database....", "");
			}
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
		
	}
	
	@FXML
	public void disableFields() {
		updateProductBtn.setDisable(true);
		updateNewProdName.setDisable(true);
		updateUnitRate.setDisable(true);
		updateSgst.setDisable(true);
		updateCgst.setDisable(true);
	}
	

}
