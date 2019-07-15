package com.univaq.eaglelibrary.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.univaq.eaglelibrary.controllerImpl.UserControllerImpl;
import com.univaq.eaglelibrary.dto.UserDTO;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginView extends GUI {
	
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

		
//		UserControllerImpl userProva = new UserControllerImpl();
//		ResultDTO resultDTO = userProva.registration(userDTO);
//		
//		if(resultDTO != null && resultDTO.getSuccessfullyOperation()) { 
//			System.out.println("Read ok");
//		}else {
//			System.out.println("Read no ok");
//		}
		
//		FileInputStream fis;
//		Properties properties = new Properties();
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
	
	void startup() {
		UserDTO userDTO = new UserDTO();
		
		UserControllerImpl userProva = new UserControllerImpl();
		userProva.registration(userDTO);
		
	
	}
}
