package com.univaq.eaglelibrary.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.univaq.eaglelibrary.controller.PersistenceService;
import com.univaq.eaglelibrary.controller.UserControllerImpl;
import com.univaq.eaglelibrary.dto.UserDTO;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class loginView extends GUI {
	
	private final Logger logger = LoggerFactory.getLogger(loginView.class);
	
	@FXML
	private TextField name;

	@FXML
	private Button save;
	
	public loginView(PersistenceService persistenceService) {
		super(persistenceService);
	}
	
	//il costruttore aggiunto
	public loginView() {
	}
	
	public void startupLogin() {
		
		// --Codice che dobbiamo portare nelle classi view
		String fxmlFile = "/fxml/first_GUI.fxml";

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

	//QUESTO � IL METODO CHE HO AGGIUNTO
	@FXML
	void startup() {
		UserDTO userDTO = new UserDTO();
		userDTO.setFirstName(name.getText());
		userController.registration(userDTO);
	}
}