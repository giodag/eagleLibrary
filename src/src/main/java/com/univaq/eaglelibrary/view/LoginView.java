package com.univaq.eaglelibrary.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.univaq.eaglelibrary.controllerImpl.UserControllerImpl;
import com.univaq.eaglelibrary.dto.UserDTO;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@Component("loginView")
public class LoginView {
	
	private final Logger logger = LoggerFactory.getLogger(LoginView.class);
	
	//il costruttore aggiunto
	public LoginView() {/**empty method**/}
	
	public void startupLogin() {
		
		String fxmlFile = "/fxml/login.fxml";

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
	
	void startup() {
		UserDTO userDTO = new UserDTO();
		
		UserControllerImpl userProva = new UserControllerImpl();
		userProva.registration(userDTO);
		
	
	}
}
