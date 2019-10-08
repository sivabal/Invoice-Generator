package com.bill.mainwindow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class BillGenerator extends Application {

	    
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		Parent parent = FXMLLoader.load(getClass().getResource("/com/bill/mainwindow/application.fxml"));		
		Scene scene = new Scene(parent);
		stage.setTitle("Invoice Application");

		stage.setScene(scene);
		stage.show();

	}
}
