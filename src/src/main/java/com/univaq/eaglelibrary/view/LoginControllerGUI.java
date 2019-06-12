package com.univaq.eaglelibrary.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginControllerGUI {

	@FXML
    private Button login;

    @FXML
    private Button registration;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    void login(ActionEvent event) {
    	//TODO
    }

    @FXML
    void registration(ActionEvent event) {
    	
    	String fxmlFile = "/fxml/registration.fxml";

		FXMLLoader loader = new FXMLLoader();
		Parent rootNode = null;
		try {
			rootNode = loader.load(getClass().getResourceAsStream(fxmlFile));
		} catch (Exception e) {
			e.printStackTrace();
		}	

		Stage stage = new Stage();
		Scene scene = new Scene(rootNode);
		stage.setScene(scene);
		stage.show();
    }

}



