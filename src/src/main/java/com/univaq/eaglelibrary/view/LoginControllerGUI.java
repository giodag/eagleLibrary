package com.univaq.eaglelibrary.view;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mysql.cj.util.StringUtils;
import com.univaq.eaglelibrary.controllerImpl.UserControllerImpl;
import com.univaq.eaglelibrary.dto.LoginRequestDTO;
import com.univaq.eaglelibrary.persistence.exceptions.MandatoryFieldException;
import com.univaq.eaglelibrary.persistence.exceptions.UserNotFoundException;
import com.univaq.eaglelibrary.persistence.exceptions.WrongPasswordException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class LoginControllerGUI{
	
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
    	
    	LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUser(username.getText());
        loginRequestDTO.setPassword(password.getText());

    	try {
    		ApplicationContext context = getContext();
        	UserControllerImpl userControllerImpl = (UserControllerImpl)context.getBean("userControllerImpl");
        	userControllerImpl.login(loginRequestDTO);
        	
        	Stage stage = (Stage) login.getScene().getWindow();
            stage.close();
            
        	String fxmlFile = "/fxml/homePage.fxml";

    		FXMLLoader loader = new FXMLLoader();
    		Parent rootNode = null;
    		try {
    			rootNode = loader.load(getClass().getResourceAsStream(fxmlFile));
    		} catch (Exception e) {
    			e.printStackTrace();
    		}	
    		stage = new Stage();
    		Scene scene = new Scene(rootNode);
    		stage.setScene(scene);
    		stage.setUserData("ciao");
    		HomepageControllerGUI controller = (HomepageControllerGUI)loader.getController();
    		controller.init(stage);
    		stage.show();
		} catch (UserNotFoundException e1) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText(e1.getMessage());
			errorAlert.showAndWait();
			e1.printStackTrace();
		} catch (MandatoryFieldException e1) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText(e1.getMessage());
			errorAlert.showAndWait();
			e1.printStackTrace();
		} catch (WrongPasswordException e1) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText(e1.getMessage());
			errorAlert.showAndWait();
			e1.printStackTrace();
		}
    	
    }

    @FXML
    void registration(ActionEvent event) {
    	
    	Stage stage = (Stage) registration.getScene().getWindow();
        stage.close();
        
    	String fxmlFile = "/fxml/registration.fxml";

		FXMLLoader loader = new FXMLLoader();
		Parent rootNode = null;
		try {
			rootNode = loader.load(getClass().getResourceAsStream(fxmlFile));
		} catch (Exception e) {
			e.printStackTrace();
		}	

		stage = new Stage();
		Scene scene = new Scene(rootNode);
		stage.setScene(scene);
		stage.show();
    }
	
	private ApplicationContext getContext() {
    	ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
		return context;
    }

}



