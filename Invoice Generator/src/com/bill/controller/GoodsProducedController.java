package com.bill.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.bill.beans.GoodsInStock;
import com.bill.dao.ToDatabase;
import com.bill.exception.GoodsProducedException;
import com.bill.popus.ShowPopups;
import com.bill.utility.Regex;
import com.bill.utility.Utility;
import com.bill.validator.FromDatabasevalidator;
import com.bill.validator.ToDatabaseValidator;
import com.bill.validator.ValidateUserInputs;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class GoodsProducedController implements Initializable {

	@FXML private ComboBox<String> itemName;
	@FXML private DatePicker date;
	@FXML private TextField lotNo;
	@FXML private TextField goodsProduced;
	
	@FXML private TableView<GoodsInStock> tableView;
	
	@FXML private TableColumn<GoodsInStock, String> itemNameCol;
	@FXML private TableColumn<GoodsInStock, String> lotNoCol;
	@FXML private TableColumn<GoodsInStock, String> goodsInStock;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			itemName.getItems().addAll(Utility.productInfo.keySet().toArray(new String[Utility.productInfo.size()]));
			date.setValue(LocalDate.now());
			
			itemNameCol.setCellValueFactory(new PropertyValueFactory<GoodsInStock, String>("itemName"));
			lotNoCol.setCellValueFactory(new PropertyValueFactory<GoodsInStock, String>("lotNo"));
			goodsInStock.setCellValueFactory(new PropertyValueFactory<GoodsInStock, String>("goodsInStock"));
			
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}
	
	@FXML
	public void getGoodsProduced() {
		try {
			
			tableView.setItems(FromDatabasevalidator.getGoodsInStock());
			
		}catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}
	
	@FXML
	public void addGoodsProduced() {
		try {
			if(ValidateUserInputs.validateGoodsProducedInfo(itemName.getValue(), date.getValue(), lotNo.getText().trim(), goodsProduced.getText().trim())) {
				
				ToDatabaseValidator.addGoodsProduced(itemName.getValue(), date.getValue().format(DateTimeFormatter.ofPattern("dd/MM/uuuu")), lotNo.getText(), goodsProduced.getText());
				
				ShowPopups.showPopups(AlertType.INFORMATION, "Data added Successfully.", "");
			}
			
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}
	
	@FXML
	public void updateGoodsProduced() {
		
		try {
			GoodsInStock selectedRow = tableView.getSelectionModel().getSelectedItem();
			if(selectedRow == null) throw new GoodsProducedException("Please Select any Item.");
			String goodsProduced = ShowPopups.getValue("Update Goods Produced", "Please Enter Goods Produced");
			
			if(goodsProduced != null) {
				if(!Regex.regexQantity.matcher(goodsProduced).matches())
					throw new GoodsProducedException("Please Provide Valid Goods Produced.");
				
				ToDatabase.updateGoodsProducedForItem(Float.parseFloat(goodsProduced), selectedRow.getItemName());
				getGoodsProduced();
				ShowPopups.showPopups(AlertType.INFORMATION, "Data Updated Successfully.", "");
				
			}
			
		} catch (GoodsProducedException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.getMessage(), "");
		}catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}

}
