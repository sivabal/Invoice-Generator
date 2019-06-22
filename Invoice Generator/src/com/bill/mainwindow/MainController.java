package com.bill.mainwindow;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.bill.dao.GetConnection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class MainController implements Initializable {
	
	@FXML private Label dbStatus;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			GetConnection.getConnection();
			dbStatus.setText(((GetConnection.connection.isClosed())?"Disconnected":"Connected"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	@FXML
	public void printInvoice(ActionEvent event) {		
		
		try {
			
			Stage stage = new Stage();
			Parent parent = FXMLLoader.load(getClass().getResource("../invoicewindow/invoice.fxml"));
			Scene scene = new Scene(parent);
			stage.setTitle("SKP bill");
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
			stage.setTitle("Generate Excel");
			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

}
