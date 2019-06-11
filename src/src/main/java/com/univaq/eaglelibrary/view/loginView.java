package com.univaq.eaglelibrary.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.univaq.eaglelibrary.controllerImpl.UserControllerImpl;
import com.univaq.eaglelibrary.dto.UserDTO;
import com.univaq.eaglelibrary.persistence.MySQLConnection;
import com.univaq.eaglelibrary.persistence.PersistenceService;

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

	//QUESTO è IL METODO CHE HO AGGIUNTO
	@FXML
	void startup() {
		UserDTO userDTO = new UserDTO();
		userDTO.setFirstName(name.getText());
		FileInputStream fis;
		Properties properties = new Properties();
		try {
			fis = new FileInputStream( "D:\\Università\\EagleLibrary\\eagleLibrary\\src\\src\\main\\resources\\properties\\db.properties" );
			properties.load(fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PersistenceService persistenceService = new MySQLConnection(properties.getProperty("root"),properties.getProperty("admin"),
				properties.getProperty("localhost"), Integer.parseInt(properties.getProperty("port")), properties.getProperty("eaglelibraryapp"));
		UserControllerImpl user = new UserControllerImpl(persistenceService);
		user.registration(userDTO);
	}
}
