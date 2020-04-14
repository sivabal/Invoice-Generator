package com.bill.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.bill.beans.SalesMade;
import com.bill.dao.FromDatabase;
import com.bill.exception.SalesReturnException;
import com.bill.popus.ShowPopups;
import com.bill.validator.ToDatabaseValidator;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class SalesReturnController implements Initializable{


	@FXML private DatePicker date;
	
	@FXML private TableView<SalesMade> tableView;
	
	@FXML private TableColumn<SalesMade, String> itemName;
	@FXML private TableColumn<SalesMade, String> lotNo;
	@FXML private TableColumn<SalesMade, String> dateCol;
	@FXML private TableColumn<SalesMade, String> salesmanName;
	@FXML private TableColumn<SalesMade, String> goodsTaken;
	@FXML private TableColumn<SalesMade, String> goodsReturned;
	@FXML private TableColumn<SalesMade, String> goodsSold;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			date.setValue(LocalDate.now());
			
			itemName.setCellValueFactory(new PropertyValueFactory<SalesMade, String>("itemName"));
			lotNo.setCellValueFactory(new PropertyValueFactory<SalesMade, String>("lotNo"));
			dateCol.setCellValueFactory(new PropertyValueFactory<SalesMade, String>("date"));
			salesmanName.setCellValueFactory(new PropertyValueFactory<SalesMade, String>("salesmanName"));
			goodsTaken.setCellValueFactory(new PropertyValueFactory<SalesMade, String>("goodsTaken"));
			goodsReturned.setCellValueFactory(new PropertyValueFactory<SalesMade, String>("goodsReturned"));
			goodsSold.setCellValueFactory(new PropertyValueFactory<SalesMade, String>("goodsSold"));
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
		
	}

	@FXML
	public void getDetails() {
		try {
			
			ObservableList<SalesMade> salesMade = FromDatabase.getSalesMade(date.getValue().format(DateTimeFormatter.ofPattern("dd/MM/uuuu")));
			
			tableView.setItems(salesMade);
			
		}catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}
	
	@FXML
	public void update() {
		try {
			
			SalesMade selectedRow = tableView.getSelectionModel().getSelectedItem();
			
			if(selectedRow == null) throw new SalesReturnException("Please Select any Item.");
			
			String salesReturn = ShowPopups.getValue("Sales Return", "Please Enter Sales Return");
			
			if(salesReturn != null) {
				
				ToDatabaseValidator.updateSalesReturn(selectedRow.getItemName(), selectedRow.getDate(), selectedRow.getSalesmanName(),
						selectedRow.getGoodsTaken(), selectedRow.getGoodsReturned(), salesReturn);
				getDetails();
				
				ShowPopups.showPopups(AlertType.INFORMATION, "Data Updated Successfully.", "");
				
			}
			
			
		}catch (SalesReturnException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.getMessage(), "");
		}catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}
}
