package com.univaq.eaglelibrary.view;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.univaq.eaglelibrary.dto.UserDTO;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class TranscriptionControllerGUI implements Initializable{
	
    @FXML
    private TextArea transcription;

    @FXML
    private ImageView viewPage;
    
	private UserDTO user;
	private ApplicationContext context;

	public void init(Stage stage) {
		//ownStage = stage;
		user = (UserDTO) stage.getUserData();
		initializeGUI();
	}

	private void initializeGUI() {
//		viewPage.setImage();
		
	}
	
	public void initialize(URL location, ResourceBundle resources) {
		getContext();
	}
	
	private ApplicationContext getContext() {
		context = new ClassPathXmlApplicationContext("spring-context.xml");
		return context;
	}
}
