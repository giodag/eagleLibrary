package com.univaq.eaglelibrary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.univaq.eaglelibrary.controller.UserController;
import com.univaq.eaglelibrary.controllerImpl.UserControllerImpl;
import com.univaq.eaglelibrary.dto.UserDTO;
import com.univaq.eaglelibrary.repository.UserRepository;
import com.univaq.eaglelibrary.utility.Permission;
import com.univaq.eaglelibrary.view.GUI;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

	private static final Logger log = LoggerFactory.getLogger(App.class);

	private static GUI gui;

	@Autowired
	private UserControllerImpl userControllerImpl;

	public static void main(String[] args) {

		try {
			launch(args);
		} catch (Exception e) {
			log.error("The error occurred is  :", e);
		}
	}

	@Override
	public void start(Stage stage) throws Exception {

		ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
		UserControllerImpl userControllerImpl = (UserControllerImpl)context.getBean("userControllerImpl");
		
		UserDTO userDTO = new UserDTO();
		userDTO.setActivated(Boolean.TRUE);
		userDTO.setEmail("sabasoda@hotmail.it");
		userDTO.setFirstName("Sabato");
		userDTO.setLastName("Sodano");
		userDTO.setPassword("password_default");
		userDTO.setUsername("sabasoda");
		userDTO.setPermission(Permission.USER);
		userControllerImpl.registration(userDTO);
	}
}
