package com.univaq.eaglelibrary.view;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.univaq.eaglelibrary.controllerImpl.LiteraryWorkControllerImpl;
import com.univaq.eaglelibrary.controllerImpl.TranscriptionControllerImpl;
import com.univaq.eaglelibrary.dto.LiteraryWorkDTO;
import com.univaq.eaglelibrary.dto.TranscriptionDTO;
import com.univaq.eaglelibrary.dto.UserDTO;
import com.univaq.eaglelibrary.exceptions.MandatoryFieldException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class TranscriptionAdministratorControllerGUI implements Initializable{
	
    @FXML
    private TextArea t_transcription;

    @FXML
    private ImageView viewPage;
    
    @FXML
    private Button home,reject;

    @FXML
    private Button accept;
    
	private UserDTO user;
	private ApplicationContext context;
	private TranscriptionDTO transcription;

	@FXML
    void reject(ActionEvent event) {
    	TranscriptionControllerImpl transcriptionControllerImpl = (TranscriptionControllerImpl)context.getBean("transcriptionControllerImpl");
    	transcription.setTranscription(t_transcription.getText());
    	transcription.setStatus("REJECT");
		transcriptionControllerImpl.validateTranscription(transcription);
		Alert alertOK = new Alert(AlertType.CONFIRMATION);
		alertOK.setHeaderText("Trascrizione rigettata correttamente");
		alertOK.showAndWait();
	}
	
    @FXML
    void comeBackHome(ActionEvent event) {
    	Stage stage = (Stage) home.getScene().getWindow();
        stage.close();
        
    	String fxmlFile = "/fxml/administrator.fxml";

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
		stage.setUserData(user);
		AdministratorControllerGUI controller = (AdministratorControllerGUI)loader.getController();
		controller.init(stage);
		stage.show();
    }

    @FXML
    void accept(ActionEvent event) {
    	transcription.setTranscription(t_transcription.getText());
    	transcription.setStatus("CLOSED");
    	TranscriptionControllerImpl transcriptionControllerImpl = (TranscriptionControllerImpl)context.getBean("transcriptionControllerImpl");
	    transcriptionControllerImpl.validateTranscription(transcription);
	    Alert alertOK = new Alert(AlertType.CONFIRMATION);
		alertOK.setHeaderText("Trascrizione validata correttamente");
		alertOK.showAndWait();
    }

	public void init(Stage stage) {
		user = (UserDTO) stage.getUserData();
		initializeGUI();
	}

	private void initializeGUI() {
		transcription = readTranscription();
		if(transcription != null && transcription.getPage() != null && transcription.getPage().getImage() != null) {
			t_transcription.setText(transcription.getTranscription());
			Image img = new Image(new ByteArrayInputStream(transcription.getPage().getImage()));
			viewPage.setImage(img);
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("La trascrizione è corrotta, chiamare l'assistenza");
			alert.showAndWait();
		}
		
	}

	private TranscriptionDTO readTranscription() {
		LiteraryWorkControllerImpl literaryWorkControllerImpl = (LiteraryWorkControllerImpl)context.getBean("literaryWorkControllerImpl");
		LiteraryWorkDTO literaryWorkDTO = new LiteraryWorkDTO();
		literaryWorkDTO.setAuthor(user.getTranscriptionTable().getAuthor());
		literaryWorkDTO.setTitle(user.getTranscriptionTable().getTitle());
		literaryWorkDTO.setYear(Integer.valueOf(user.getTranscriptionTable().getYear()));
		literaryWorkDTO = literaryWorkControllerImpl.getLiteraryWork(literaryWorkDTO);
		TranscriptionDTO transcriptionDTORead = null;
		if(literaryWorkDTO != null && literaryWorkDTO.getPageList() != null && !literaryWorkDTO.getPageList().isEmpty()
				&& user.getTranscriptionTable().getPage() != null && literaryWorkDTO.getPageList().get(Integer.valueOf(user.getTranscriptionTable().getPage())) != null) {
			transcriptionDTORead = literaryWorkDTO.getPageList().get(Integer.valueOf(user.getTranscriptionTable().getPage())).getTranscriptionDTO();
			transcriptionDTORead.setPage(literaryWorkDTO.getPageList().get(Integer.valueOf(user.getTranscriptionTable().getPage())));
			return transcriptionDTORead;
		}
		return null;
	}

	public void initialize(URL location, ResourceBundle resources) {
		getContext();
	}
	
	private ApplicationContext getContext() {
		context = new ClassPathXmlApplicationContext("spring-context.xml");
		return context;
	}
}

