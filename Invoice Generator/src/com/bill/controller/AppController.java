package com.bill.controller;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.bill.dao.GetConnection;
import com.bill.exception.EditProductException;
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


public class AppController implements Initializable {
	
	@FXML private Label dbStatus;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			GetConnection.getConnection();
			dbStatus.setText(((GetConnection.connection.isClosed())?"Disconnected":"Connected"));
			Utility.getDetailsReady();
		} catch (SQLException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
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
			Parent parent = FXMLLoader.load(getClass().getResource("/com/bill/ui/invoice.fxml"));
			Scene scene = new Scene(parent);
			stage.setTitle("Invoice Window");
			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	  }

	@FXML
	public void generateExcel(ActionEvent event) {
		
		try {
			
			Stage stage = new Stage();
			Parent parent = FXMLLoader.load(getClass().getResource("/com/bill/ui/excel.fxml"));
			Scene scene = new Scene(parent);
			stage.setTitle("Excel Generator Window");
			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}
	
	@FXML
	public void editProductDetails() {
		
		try {
			
			if(ShowPopups.passwordPopUp()) {
				Stage stage = new Stage();
				Parent parent = FXMLLoader.load(getClass().getResource("/com/bill/ui/editproductdetails.fxml"));
				Scene scene = new Scene(parent);
				stage.setTitle("Edit Product Details");
				stage.setScene(scene);
				stage.show();
			}
		} catch (IOException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}catch (EditProductException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.getMessage(), "");
		}catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}
	
	@FXML
	public void editfromaddress() {
		try {
			if(ShowPopups.passwordPopUp()) {
				Stage stage = new Stage();
				Parent parent = FXMLLoader.load(getClass().getResource("/com/bill/ui/editfromaddress.fxml"));
				Scene scene = new Scene(parent);
				stage.setTitle("Edit 'Bill From' Address");
				stage.setScene(scene);
				stage.show();
			}
		} catch (IOException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}catch (EditProductException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.getMessage(), "");
		}catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}
	
	@FXML
	public void edittoaddress() {
		try {
			if(ShowPopups.passwordPopUp()) {
				Stage stage = new Stage();
				Parent parent = FXMLLoader.load(getClass().getResource("/com/bill/ui/edittoaddress.fxml"));
				Scene scene = new Scene(parent);
				stage.setTitle("Edit 'Bill To' Address");
				stage.setScene(scene);
				stage.show();
			}
		} catch (IOException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}catch (EditProductException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.getMessage(), "");
		}catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}
	
	@FXML
	public void deleteInvoice() {
		try {
			if(ShowPopups.passwordPopUp()) {
				Stage stage = new Stage();
				Parent parent = FXMLLoader.load(getClass().getResource("/com/bill/ui/deleteinvoice.fxml"));
				Scene scene = new Scene(parent);
				stage.setTitle("Delete Invoice");
				stage.setScene(scene);
				stage.show();
			}
		} catch (IOException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}catch (EditProductException e) {
			ShowPopups.showPopups(AlertType.ERROR, e.getMessage(), "");
		}catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}
	
	

}
