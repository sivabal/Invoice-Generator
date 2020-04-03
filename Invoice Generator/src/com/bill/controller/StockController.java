package com.bill.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.bill.beans.GoodsInStock;
import com.bill.popus.ShowPopups;
import com.bill.validator.FromDatabasevalidator;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class StockController implements Initializable{

	
	@FXML private TableView<GoodsInStock> tableView;
	
	@FXML private TableColumn<GoodsInStock, String> itemName;
	@FXML private TableColumn<GoodsInStock, String> lotNo;
	@FXML private TableColumn<GoodsInStock, String> goodsInStock;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
	
			itemName.setCellValueFactory(new PropertyValueFactory<GoodsInStock, String>("itemName"));
			lotNo.setCellValueFactory(new PropertyValueFactory<GoodsInStock, String>("lotNo"));
			goodsInStock.setCellValueFactory(new PropertyValueFactory<GoodsInStock, String>("goodsInStock"));
			
			tableView.setItems(FromDatabasevalidator.getGoodsInStock());
			
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
		
	}

}
