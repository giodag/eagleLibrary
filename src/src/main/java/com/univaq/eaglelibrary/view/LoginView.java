package com.univaq.eaglelibrary.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.univaq.eaglelibrary.controllerImpl.UserControllerImpl;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.dto.UserDTO;
import com.univaq.eaglelibrary.persistence.PersistenceService;

public class LoginView extends GUI {
	
	private final Logger logger = LoggerFactory.getLogger(LoginView.class);
	private PersistenceService persistenceService;
	
	public LoginView(PersistenceService persistenceService) {
		super(persistenceService);
		this.persistenceService = persistenceService;
	}
	
	//il costruttore aggiunto
	public LoginView() {
	}
	
	public void startupLogin() {
		
		//String fxmlFile = "/fxml/login.fxml";

//		FXMLLoader loader = new FXMLLoader();
//		Parent rootNode = null;
//		try {
//			rootNode = loader.load(getClass().getResourceAsStream(fxmlFile));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}	

//		Stage stage = new Stage();
//		Scene scene = new Scene(rootNode);
//		stage.setScene(scene);
//		stage.show();
		
		UserDTO userDTO = new UserDTO();
//		FileInputStream fis;
//		Properties properties = new Properties();
		
		UserControllerImpl userProva = new UserControllerImpl(this.persistenceService);
		ResultDTO resultDTO = userProva.registration(userDTO);
		
		if(resultDTO != null && resultDTO.getSuccessfullyOperation()) { 
			System.out.println("Read ok");
		}else {
			System.out.println("Read no ok");
		}
		
		
//		try {
//			fis = new FileInputStream( "D:\\Università\\EagleLibrary\\eagleLibrary\\src\\src\\main\\resources\\properties\\db.properties" );
//			properties.load(fis);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	//QUESTO è IL METODO CHE HO AGGIUNTO
	void startup() {
		UserDTO userDTO = new UserDTO();
		FileInputStream fis;
		Properties properties = new Properties();
		
		UserControllerImpl userProva = new UserControllerImpl(this.persistenceService);
		userProva.registration(userDTO);
		
		
		
		
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
	
	}
}
