package com.bill.mainwindow;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainController {
	
	
	
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

	

}
