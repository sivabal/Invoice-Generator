package com.bill.editproductdetails;

import java.net.URL;
import java.util.ResourceBundle;
import com.bill.exception.EditProductException;
import com.bill.popus.ShowPopups;
import com.bill.utility.Utility;
import com.bill.validator.FromDatabasevalidator;
import com.bill.validator.ToDatabaseValidator;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class EditProductDetailsController implements Initializable{
	
	
	@FXML private TextField insertProdId;
	@FXML private TextField insertProdName;
	@FXML private TextField insertUnitRate;
	@FXML private TextField insertSgst;
	@FXML private TextField insertCgst;
	
	@FXML private Button updateProductBtn;
	
	@FXML private TextField updateProdId;
	@FXML private TextField updateProdName;
	@FXML private TextField updateUnitRate;
	@FXML private TextField updateSgst;
	@FXML private TextField updateCgst;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		updateProductBtn.setDisable(true);
		updateProdName.setDisable(true);
		updateUnitRate.setDisable(true);
		updateSgst.setDisable(true);
		updateCgst.setDisable(true);
		
		insertProdId.setText(FromDatabasevalidator.getLastProductId());
	}
	
	@FXML
	public void insertProduct() {
		
		try {
			
			if(!Utility.regexProdId.matcher(insertProdId.getText()).matches())
				throw new EditProductException("Please Provide a Three digit(minimum) Number for Product Id.");
			
			
			ToDatabaseValidator.insertProduct(Integer.parseInt(insertProdId.getText()), insertProdName.getText(),
					insertUnitRate.getText(), insertSgst.getText(), insertCgst.getText());
			
			ShowPopups.showPopups(AlertType.INFORMATION,"Product Inserted to Database Successfully....", "");
			
		} catch (EditProductException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.getMessage(), "");
		}catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void getProductDetails() {

		FromDatabasevalidator.getProduct(Integer.parseInt(updateProdId.getText()), updateProdName,updateUnitRate, updateSgst, updateCgst);
			
		updateProdName.setDisable(false);
		updateUnitRate.setDisable(false);
		updateSgst.setDisable(false);
		updateCgst.setDisable(false);
		updateProductBtn.setDisable(false);

	}
	
	@FXML
	public void updateProduct() {
	
		ToDatabaseValidator.updateProduct(Integer.parseInt(updateProdId.getText()), updateProdName.getText(),
				updateUnitRate.getText(), updateSgst.getText(), updateCgst.getText());
		
		ShowPopups.showPopups(AlertType.INFORMATION, "Success....", "Product Details Updated Successfully....");
	}

	

}
