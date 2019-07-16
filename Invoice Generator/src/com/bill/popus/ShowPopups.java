package com.bill.popus;

import java.util.Optional;

import com.bill.exception.EditProductException;
import com.bill.utility.Utility;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;

public class ShowPopups {

	private static Alert alert; 
	
	public static boolean showPopups(AlertType alertType, String header, String content) {
		
		alert = new Alert(alertType);
		alert.setTitle(alertType.name());
		alert.setHeaderText(header);
		alert.setContentText(content);
		Optional<ButtonType> optional =  alert.showAndWait();
		if(optional.isPresent() && optional.get().getText().equals("OK"))
			return true;
		return false;
	}
	
	public static boolean passwordPopUp() throws Exception{
		
			Dialog<String> passwordDialog = new Dialog<>();
			passwordDialog.setTitle("Password");
			passwordDialog.setHeaderText("Please enter your password..");
			passwordDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
			
			PasswordField passField = new PasswordField();
			HBox content = new HBox();
			content.setFocusTraversable(true);
			content.getChildren().add(passField);	
			
			passwordDialog.getDialogPane().setContent(content);
			passwordDialog.setResultConverter(x -> {
				if(x.equals(ButtonType.OK))
					return passField.getText();
				return null;
			});
		
		Optional<String> optional = passwordDialog.showAndWait();
		if(!optional.isPresent())
			return false;
		if(optional.get().equals(Utility.password))
			return true;
		else
			throw new EditProductException("Invalid Password..");
	}

	public static Alert loadingPopUp(AlertType alertType, String header, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(alertType.name());
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.show();
		return alert;
	}
	

}
