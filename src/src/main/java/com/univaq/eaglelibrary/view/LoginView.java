package com.univaq.eaglelibrary.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

@Component("loginView")
public class LoginView {
	
	private final Logger logger = LoggerFactory.getLogger(LoginView.class);
	
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
		stage.getIcons().add(new Image(LoginView.class.getClassLoader().getResourceAsStream("images/libro.png")));
		Scene scene = new Scene(rootNode);
		stage.setScene(scene);
		stage.show();
	}
}
