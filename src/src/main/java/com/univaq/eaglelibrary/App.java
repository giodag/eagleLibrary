package com.univaq.eaglelibrary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{

	private static final Logger log = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {	
		launch(args);
	}  

	@Override
	public void start(Stage stage) throws Exception {

		String fxmlFile = "/fxml/first_GUI.fxml";

		FXMLLoader loader = new FXMLLoader();
		Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));

		Scene scene = new Scene(rootNode);
		stage.setScene(scene);
		stage.show();
	}
}
