package com.univaq.eaglelibrary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.univaq.eaglelibrary.controller.PersistenceService;
import com.univaq.eaglelibrary.controller.database.MySQLConnection;
import com.univaq.eaglelibrary.view.GUI;
import com.univaq.eaglelibrary.view.UserInterface;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application{

	private static final Logger log = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {	
		try {
		launch(args);
		}catch(Exception e) {
			log.error("The error occurred is  :",e);
		}
	}  

	@Override
	public void start(Stage stage) throws Exception {
		
		PersistenceService persistenceService = new MySQLConnection("admin", "danimetu", "localhost", 3306, "javafx");
		UserInterface gui = new GUI(persistenceService);
		
		//--Run view
		gui.run();
		
	}
}
