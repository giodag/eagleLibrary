package com.univaq.eaglelibrary.view;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.univaq.eaglelibrary.controllerImpl.UserControllerImpl;
import com.univaq.eaglelibrary.dto.UserDTO;
import com.univaq.eaglelibrary.persistence.exceptions.CreateUserException;
import com.univaq.eaglelibrary.persistence.exceptions.MandatoryFieldException;
import com.univaq.eaglelibrary.utility.Permission;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistrationControllerGUI {
	
	@FXML
    private Button registration;

    @FXML
    private TextField name;

    @FXML
    private TextField lastname;

    @FXML
    private TextField email;

    @FXML
    private TextField username;
    
    @FXML
    private PasswordField password;
    
    @FXML
    void registration(ActionEvent event) {
    	
    	ApplicationContext context = getContext();
    	UserControllerImpl userControllerImpl = (UserControllerImpl)context.getBean("userControllerImpl");
    	//TODO inserire messaggi dialog
    	UserDTO userDTO = decorateUserDTO();
    	try {
			userControllerImpl.registration(userDTO);
		} catch (MandatoryFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CreateUserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    		
    }

	private UserDTO decorateUserDTO() {
		
		UserDTO userDTO = new UserDTO();
		
		userDTO.setFirstName(name.getText());
		userDTO.setLastName(lastname.getText());
		userDTO.setEmail(email.getText());
		userDTO.setUsername(username.getText());
		userDTO.setPassword(password.getText());
		userDTO.setPermission(Permission.USER);
		
		return userDTO;
	}
	
	private ApplicationContext getContext() {
    	ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
		return context;
    }
}
