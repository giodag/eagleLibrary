package com.univaq.eaglelibrary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.univaq.eaglelibrary.dto.ProfileDTO;
import com.univaq.eaglelibrary.dto.UserDTO;
import com.univaq.eaglelibrary.hanlder.ProfileHandler;
import com.univaq.eaglelibrary.utility.Permission;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainTest extends Application {

	private static final Logger log = LoggerFactory.getLogger(MainTest.class);
	
	public static void main(String[] args) {

		try {
			launch(args);
		} catch (Exception e) {
			log.error("The error occurred is  :", e);
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		try {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
		ProfileHandler profileHandler = (ProfileHandler)context.getBean("profileHandler");
		
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername("er");
		userDTO.setId(1L);
		
		ProfileDTO prof = new ProfileDTO();
		
		prof.setAddress("bc");
		prof.setUser(userDTO);
		
		profileHandler.readProfile(prof);

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
