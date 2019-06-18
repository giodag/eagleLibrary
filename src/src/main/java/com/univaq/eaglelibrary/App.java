package com.univaq.eaglelibrary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.univaq.eaglelibrary.persistence.MySQLConnection;
import com.univaq.eaglelibrary.persistence.PersistenceService;
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
		
		ResultSet rsObj = null;
		Connection connObj = null;
		PreparedStatement pstmtObj = null;
		PersistenceService persistenceService = new MySQLConnection("root", "danimetu", "localhost", 3306, "eaglelibraryapp");
		persistenceService.connect();
		
		
		try {	
			
			//--Run view
			UserInterface gui = new GUI(persistenceService );
			gui.run();
					
		} catch(Exception sqlException) {
			sqlException.printStackTrace();
		} finally {
			try {
				// Closing ResultSet Object
				if(rsObj != null) {
					rsObj.close();
				}
				// Closing PreparedStatement Object
				if(pstmtObj != null) {
					pstmtObj.close();
				}
				// Closing Connection Object
				if(connObj != null) {
					connObj.close();
				}
			} catch(Exception sqlException) {
				sqlException.printStackTrace();
			}
		}
	}
}
