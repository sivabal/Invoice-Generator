package com.bill.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.bill.beans.Inventory;
import com.bill.dao.ToDatabase;
import com.bill.exception.InventoryException;
import com.bill.popus.ShowPopups;
import com.bill.validator.FromDatabasevalidator;
import com.bill.validator.ToDatabaseValidator;
import com.bill.validator.ValidateUserInputs;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class InventoryController implements Initializable{

	@FXML private TextField itemName;
	@FXML private TextField quantity;
	@FXML private TextField price;
	@FXML private TextField cgst;
	@FXML private TextField sgst;
	@FXML private TextField igst;
	@FXML private TextField transportCharge;
	@FXML private TextField totalAmount;
	@FXML private DatePicker date;
	@FXML private Button add;
	
	@FXML private TableView<Inventory> tableView;
	
	@FXML private TableColumn<Inventory, String> itemNameCol;
	@FXML private TableColumn<Inventory, String> quantityCol;
	@FXML private TableColumn<Inventory, String> priceCol;
	@FXML private TableColumn<Inventory, String> cgstCol;
	@FXML private TableColumn<Inventory, String> sgstCol;
	@FXML private TableColumn<Inventory, String> igstCol;
	@FXML private TableColumn<Inventory, String> tpChargeCol;
	@FXML private TableColumn<Inventory, String> totalAmountCol;
	@FXML private TableColumn<Inventory, String> dateCol;
	

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			date.setValue(LocalDate.now());
			
			itemNameCol.setCellValueFactory(new PropertyValueFactory<Inventory, String>("itemName"));
			quantityCol.setCellValueFactory(new PropertyValueFactory<Inventory, String>("quantity"));
			priceCol.setCellValueFactory(new PropertyValueFactory<Inventory, String>("price"));
			cgstCol.setCellValueFactory(new PropertyValueFactory<Inventory, String>("cgst"));
			sgstCol.setCellValueFactory(new PropertyValueFactory<Inventory, String>("sgst"));
			igstCol.setCellValueFactory(new PropertyValueFactory<Inventory, String>("igst"));
			tpChargeCol.setCellValueFactory(new PropertyValueFactory<Inventory, String>("tpCharge"));
			totalAmountCol.setCellValueFactory(new PropertyValueFactory<Inventory, String>("totalAmount"));
			dateCol.setCellValueFactory(new PropertyValueFactory<Inventory, String>("date"));
		}catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
		
	}
	
	@FXML
	public void add() {
		try {
			if(ValidateUserInputs.validateInventoryInfo(itemName.getText().trim(), quantity.getText().trim(), price.getText().trim(), cgst.getText().trim(),
					sgst.getText().trim(),igst.getText().trim(), transportCharge.getText().trim(), totalAmount.getText().trim(), date.getValue())) {
				
				ToDatabaseValidator.addInventory(itemName.getText(), quantity.getText(), price.getText(), cgst.getText(), sgst.getText(), igst.getText(),
						transportCharge.getText(), totalAmount.getText(), date.getValue());
				
				ShowPopups.showPopups(AlertType.INFORMATION, "Successfully added to Inventory.", "");
				
			}
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
		
	}
	
	@FXML
	public void show() {
		try {
			
			tableView.setItems(FromDatabasevalidator.getInventory());
			
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
		
	}
	
	@FXML
	public void deleteInventory() {
		try {
			Inventory selectedRow = tableView.getSelectionModel().getSelectedItem();
			
			if(selectedRow == null) throw new InventoryException("Please Select any Item.");
			
			if(ShowPopups.showPopups(AlertType.CONFIRMATION, "Are you sure want to delete this Inventory..", "")) {
				ToDatabase.deleteInventory(selectedRow);
				show();
				ShowPopups.showPopups(AlertType.INFORMATION, "Inventory Deleted Successfully..", "");
			}
		}catch (InventoryException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.getMessage(), "");
		}catch(Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}

}
