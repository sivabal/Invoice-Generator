package com.bill.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.bill.beans.SalesMade;
import com.bill.dao.FromDatabase;
import com.bill.dao.ToDatabase;
import com.bill.exception.OutOfStockException;
import com.bill.exception.SalesMadeException;
import com.bill.popus.ShowPopups;
import com.bill.utility.Utility;
import com.bill.validator.ToDatabaseValidator;
import com.bill.validator.ValidateUserInputs;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class SalesMadeController implements Initializable{

	@FXML private ComboBox<String> itemName;
	@FXML private DatePicker date;
	@FXML private TextField lotNo;
	@FXML private TextField goodsTaken;
	@FXML private TextField salesManName;
	
	@FXML private TableView<SalesMade> tableView;
	
	@FXML private TableColumn<SalesMade, String> itemNameCol;
	@FXML private TableColumn<SalesMade, String> lotNoCol;
	@FXML private TableColumn<SalesMade, String> dateCol;
	@FXML private TableColumn<SalesMade, String> salesmanName;
	@FXML private TableColumn<SalesMade, String> goodsTakenCol;
	@FXML private TableColumn<SalesMade, String> goodsReturned;
	@FXML private TableColumn<SalesMade, String> goodsSold;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			itemName.getItems().addAll(Utility.productInfo.keySet().toArray(new String[Utility.productInfo.size()]));
			date.setValue(LocalDate.now());
			
			itemNameCol.setCellValueFactory(new PropertyValueFactory<SalesMade, String>("itemName"));
			lotNoCol.setCellValueFactory(new PropertyValueFactory<SalesMade, String>("lotNo"));
			dateCol.setCellValueFactory(new PropertyValueFactory<SalesMade, String>("date"));
			salesmanName.setCellValueFactory(new PropertyValueFactory<SalesMade, String>("salesmanName"));
			goodsTakenCol.setCellValueFactory(new PropertyValueFactory<SalesMade, String>("goodsTaken"));
			goodsReturned.setCellValueFactory(new PropertyValueFactory<SalesMade, String>("goodsReturned"));
			goodsSold.setCellValueFactory(new PropertyValueFactory<SalesMade, String>("goodsSold"));
			
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}

	@FXML
	public void getDetails() {
		try {
			
			ObservableList<SalesMade> salesMade = FromDatabase.getSalesMade();
			tableView.setItems(salesMade);
			
		}catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}
	
	@FXML
	public void addSalesMade() {
		try {
			if(ValidateUserInputs.validateSalesMadeInfo(itemName.getValue(), date.getValue(), lotNo.getText().trim(), goodsTaken.getText().trim()
					, salesManName.getText().trim())) {
				
				ToDatabaseValidator.addSalesMade(itemName.getValue(), date.getValue(), lotNo.getText(), goodsTaken.getText(), salesManName.getText());
				
				ShowPopups.showPopups(AlertType.INFORMATION, "Data added Successfully.", "");
			}
			
		} catch (OutOfStockException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.getMessage(), "");
		}catch (Exception e) {
			if(e.getMessage().contains("UNIQUE"))
				ShowPopups.showPopups(AlertType.ERROR, "Sales man [" + salesManName.getText() + "] has already took the product ["
						+ itemName.getValue() + "] for sales on " + date.getValue().format(DateTimeFormatter.ofPattern("dd/MM/uuuu")), "");
			else
				ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}
	
	@FXML
	public void deleteSalesMade() {
		try {
			SalesMade selectedRow = tableView.getSelectionModel().getSelectedItem();
			if(selectedRow == null) throw new SalesMadeException("Please Select any Item.");
			
			if(ShowPopups.showPopups(AlertType.CONFIRMATION, "Are you sure want to delete this Sales..", "")) {
				ToDatabase.deleteSalesMade(selectedRow);
				ToDatabaseValidator.addGoodsProduced(selectedRow.getItemName(), selectedRow.getDate(), selectedRow.getLotNo(), selectedRow.getGoodsSold());
				getDetails();
				ShowPopups.showPopups(AlertType.INFORMATION, "Data Deleted Successfully.", "");
			}
		} catch (SalesMadeException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.getMessage(), "");
		}catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}
}
