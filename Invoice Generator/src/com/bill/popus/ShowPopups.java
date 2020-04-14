package com.bill.popus;

import java.util.Optional;

import com.bill.exception.InvalidPasswordException;
import com.bill.utility.Utility;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ShowPopups {

	private static Alert alert;

	public static boolean showPopups(AlertType alertType, String header, String content) {

		alert = new Alert(alertType);
		alert.setTitle(alertType.name());
		alert.setHeaderText(header);
		alert.setContentText(content);
		Optional<ButtonType> optional = alert.showAndWait();
		if (optional.isPresent() && optional.get().getText().equals("OK"))
			return true;
		return false;
	}

	public static boolean passwordPopUp() throws Exception {

		Dialog<String> passwordDialog = new Dialog<>();
		passwordDialog.setTitle("Authentication");
		passwordDialog.setHeaderText("Please Enter Your Password");

		passwordDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

		PasswordField passField = new PasswordField();

		passwordDialog.getDialogPane().setContent(passField);
		
		Platform.runLater(() -> passField.requestFocus());
		
		passwordDialog.setGraphic(passField);
		passwordDialog.setResultConverter(dialogButton -> {
			if (dialogButton.equals(ButtonType.OK))
				return passField.getText();
			return null;
		});

		Optional<String> optional = passwordDialog.showAndWait();

		if (!optional.isPresent())
			return false;
		if (optional.get().equals(Utility.password))
			return true;
		else
			throw new InvalidPasswordException("Invalid Password..");
	}
	
	public static String getValue(String title, String header) throws Exception {

		Dialog<String> textDialog = new Dialog<>();
		textDialog.setTitle(title);
		textDialog.setHeaderText(header);

		textDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

		TextField textField = new TextField();

		textDialog.getDialogPane().setContent(textField);
		
		Platform.runLater(() -> textField.requestFocus());
		
		textDialog.setGraphic(textField);
		textDialog.setResultConverter(dialogButton -> {
			if (dialogButton.equals(ButtonType.OK))
				return textField.getText();
			return null;
		});

		Optional<String> optional = textDialog.showAndWait();

		if (optional.isPresent())
			return optional.get();
		else
			return null;
	
	}


}
