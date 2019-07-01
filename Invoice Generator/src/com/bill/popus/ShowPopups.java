package com.bill.popus;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ShowPopups {

	private static Alert alert; 
	
	public static void showPopups(AlertType alertType, String header, String content) {
		
		alert = new Alert(alertType);
		alert.setTitle(alertType.name());
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
