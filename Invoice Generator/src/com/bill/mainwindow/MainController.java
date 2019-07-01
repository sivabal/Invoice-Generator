package com.bill.mainwindow;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.bill.dao.GetConnection;
import com.bill.popus.ShowPopups;
import com.bill.utility.Utility;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;


public class MainController implements Initializable {
	
	@FXML private Label dbStatus;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			GetConnection.getConnection();
			dbStatus.setText(((GetConnection.connection.isClosed())?"Disconnected":"Connected"));
			Utility.getDetailsReady();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void refreshData() {
		Utility.getDetailsReady();
		ShowPopups.showPopups(AlertType.INFORMATION, "Success....","Data Refreshed Successfully....");
	}
	
	@FXML
	public void printInvoice(ActionEvent event) {		
		
		try {
			
			Stage stage = new Stage();
			Parent parent = FXMLLoader.load(getClass().getResource("../invoicewindow/invoice.fxml"));
			Scene scene = new Scene(parent);
			stage.setTitle("Invoice Window");
			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	  }

	@FXML
	public void generateExcel(ActionEvent event) {
		
		try {
			
			Stage stage = new Stage();
			Parent parent = FXMLLoader.load(getClass().getResource("../excelwindow/excel.fxml"));
			Scene scene = new Scene(parent);
			stage.setTitle("Excel Generator Window");
			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void editProductDetails() {
		
		try {
			Stage stage = new Stage();
			Parent parent = FXMLLoader.load(getClass().getResource("../editproductdetails/editproductdetails.fxml"));
			Scene scene = new Scene(parent);
			stage.setTitle("Edit Product Details");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void editfromaddress() {
		try {
			Stage stage = new Stage();
			Parent parent = FXMLLoader.load(getClass().getResource("../editfromaddress/editfromaddress.fxml"));
			Scene scene = new Scene(parent);
			stage.setTitle("Edit 'Bill From' Address");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void edittoaddress() {
		try {
			Stage stage = new Stage();
			Parent parent = FXMLLoader.load(getClass().getResource("../edittoaddress/edittoaddress.fxml"));
			Scene scene = new Scene(parent);
			stage.setTitle("Edit 'Bill To' Address");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
