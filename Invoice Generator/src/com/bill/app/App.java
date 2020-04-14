package com.bill.app;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;



public class App extends Application {

	    
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		Parent parent = FXMLLoader.load(getClass().getResource("/app.fxml"));		
		Scene scene = new Scene(parent);
		stage.getIcons().add(new Image("/icon.png"));
		stage.setMaximized(false);
		stage.setResizable(false);
		stage.setTitle("SKP Foods & Masalas");

		stage.setScene(scene);
		stage.show();

	}
}
