package com.univaq.eaglelibrary.view;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.univaq.eaglelibrary.controllerImpl.LiteraryWorkControllerImpl;
import com.univaq.eaglelibrary.dto.LiteraryWorkDTO;
import com.univaq.eaglelibrary.dto.PageDTO;
import com.univaq.eaglelibrary.dto.UserDTO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ViewOperaTranscriptionControllerGUI implements Initializable{

	@FXML
    private Label l_previous,l_next;

    @FXML
    private Button b_home;
    
    @FXML
    private ImageView opera;
    
	@FXML
	private TextArea t_transcription;
    
    private UserDTO user;
	private ApplicationContext context;
	private LiteraryWorkDTO literaryWorkDTO;
	private Integer i;
	
	@FXML
	private void handleButtonAction(MouseEvent eventMuose) {
		if(eventMuose.getSource() == l_previous) {
			i--;
		} else {
			i++;
		}
		if(literaryWorkDTO.getPageList().get(i).getImage() != null) {
			Image img = new Image(new ByteArrayInputStream(literaryWorkDTO.getPageList().get(i).getImage()));
			opera.setImage(img);
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Immagine non disponibile");
			alert.showAndWait();
		}
		if(StringUtils.isEmpty(literaryWorkDTO.getPageList().get(i).getTranscriptionDTO().getTranscription()) || 
				(literaryWorkDTO.getPageList().get(i).getTranscriptionDTO().getStatus() != null && 
				!literaryWorkDTO.getPageList().get(i).getTranscriptionDTO().getStatus().equals("CLOSED"))) {
			t_transcription.setText("Trascrizione non disponibile");
		} else {
			t_transcription.setText(literaryWorkDTO.getPageList().get(i).getTranscriptionDTO().getTranscription());
		}
		checkPage(literaryWorkDTO.getPageList());
	}
	
	@FXML
	void comeBackHome(ActionEvent event) {
		Stage stage = (Stage) b_home.getScene().getWindow();
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
		stage.getIcons().add(new Image(LoginView.class.getClassLoader().getResourceAsStream("images/libro.png")));
		stage.setScene(scene);
		stage.setUserData(user);
		HomepageControllerGUI controller = (HomepageControllerGUI)loader.getController();
		controller.init(stage);
		stage.show();
	}

	public void init(Stage stage) {
		user = (UserDTO) stage.getUserData();
		initializeGUI();	
	}

	
	private void initializeGUI() {
		LiteraryWorkControllerImpl literaryWorkControllerImpl = (LiteraryWorkControllerImpl)context.getBean("literaryWorkControllerImpl");
		literaryWorkDTO = new LiteraryWorkDTO();
		literaryWorkDTO.setAuthor(user.getTranscriptionTable().getAuthor());
		literaryWorkDTO.setTitle(user.getTranscriptionTable().getTitle());
		literaryWorkDTO.setYear(Integer.valueOf(user.getTranscriptionTable().getYear()));
		literaryWorkDTO = literaryWorkControllerImpl.getLiteraryWork(literaryWorkDTO);
		if(literaryWorkDTO != null && literaryWorkDTO.getPageList() != null && !literaryWorkDTO.getPageList().isEmpty()) {
			i=0;
			if(literaryWorkDTO.getPageList().get(i).getImage() != null) {
				Image img = new Image(new ByteArrayInputStream(literaryWorkDTO.getPageList().get(i).getImage()));
				opera.setImage(img);
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Immagine non disponibile");
				alert.showAndWait();
			}
			checkPage(literaryWorkDTO.getPageList());
			t_transcription.setText(literaryWorkDTO.getPageList().get(i).getTranscriptionDTO().getTranscription());
		}
	}

	private void checkPage(List<PageDTO> pageList) {
		try {
			literaryWorkDTO.getPageList().get(i+1);
		} catch(IndexOutOfBoundsException e) {
			l_next.setVisible(false);
		}
		try {
			literaryWorkDTO.getPageList().get(i-1);
		} catch(IndexOutOfBoundsException e) {
			l_previous.setVisible(false);
		}
	}

	public void initialize(URL location, ResourceBundle resources) {
		getContext();
	}

	private ApplicationContext getContext() {
		context = new ClassPathXmlApplicationContext("spring-context.xml");
		return context;
	}

}
