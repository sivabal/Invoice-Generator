package com.bill.mainwindow;

import com.bill.dao.GetConnection;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class BillGenerator extends Application {

	    
	public static void main(String[] args) {
		GetConnection.getConnection();
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		Parent parent = FXMLLoader.load(getClass().getResource("application.fxml"));		
		Scene scene = new Scene(parent);
		stage.setTitle("Bill Generator");
		stage.setScene(scene);
		stage.show();

	}

}
