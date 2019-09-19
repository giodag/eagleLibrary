package com.univaq.eaglelibrary.view;

import java.io.File;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.univaq.eaglelibrary.controllerImpl.ProfileControllerImpl;
import com.univaq.eaglelibrary.dto.ProfileDTO;
import com.univaq.eaglelibrary.dto.UserDTO;
import com.univaq.eaglelibrary.persistence.exceptions.MandatoryFieldException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class HomepageControllerGUI{

	@FXML
	private Label l_profile,l_search,l_transcription,l_module,l_logout,l_upload;

	@FXML
	private AnchorPane top,a_module,a_searchOpera,a_upload,a_trascription,a_profile;

	@FXML
	private Button b_chooseFile,b_send,b_saerchOpera,saveProfile;

	@FXML
	private TextField t_year,t_author,t_title,p_name,p_lastname,p_username,p_email,p_datebirth,p_matnumber,p_address,p_degree;

	@FXML
	private TextArea t_partOfText;

	@FXML
	private ComboBox<?> co_category;

	@FXML
	private CheckBox c_trascription;


	private ApplicationContext context;
	private UserDTO user;
	final FileChooser fileChooser = new FileChooser();

	@FXML
	void chooseFile(MouseEvent event) {

		File file = fileChooser.showOpenDialog(l_logout.getScene().getWindow());

		//TODO call service to store file 
	}

	@FXML
	private void saveProfile(ActionEvent event) {
		ProfileDTO profile = new ProfileDTO();
		profile.setUser(user);
		decorateProfileToSave(profile);
		ProfileControllerImpl profileControllerImpl = (ProfileControllerImpl)context.getBean("profileControllerImpl");
		try {
			profileControllerImpl.createUpdateProfile(profile);
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Profilo salvato correttamente");
			alert.showAndWait();
		} catch (MandatoryFieldException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText(e.getMessage());
			alert.showAndWait();
			e.printStackTrace();
		}
	}

	@FXML
	private void handleButtonAction(MouseEvent eventMuose) {
		if(eventMuose.getSource() == l_profile) {
			a_profile.setVisible(true);
			a_searchOpera.setVisible(false);
			a_module.setVisible(false);
			a_trascription.setVisible(false);
			a_upload.setVisible(false);
		} else {
			if(eventMuose.getSource() == l_search) {
				a_searchOpera.setVisible(true);
				a_profile.setVisible(false);
				a_module.setVisible(false);
				a_trascription.setVisible(false);
				a_upload.setVisible(false);
			} else {
				if(eventMuose.getSource() == l_module) {
					a_profile.setVisible(false);
					a_searchOpera.setVisible(false);
					a_module.setVisible(true);
					a_trascription.setVisible(false);
					a_upload.setVisible(false);
				} else {
					if(eventMuose.getSource() == l_upload) {
						a_profile.setVisible(false);
						a_searchOpera.setVisible(false);
						a_module.setVisible(false);
						a_trascription.setVisible(false);
						a_upload.setVisible(true);
					} else {
						if(eventMuose.getSource() == l_transcription) {
							a_profile.setVisible(false);
							a_searchOpera.setVisible(false);
							a_module.setVisible(false);
							a_trascription.setVisible(true);
							a_upload.setVisible(false);
						} else {
							if(eventMuose.getSource() == l_logout) {
								Stage stage = (Stage) l_logout.getScene().getWindow();
								stage.close();
							}
						}
					}
				}
			}
		}
	}

	public void init(Stage stage) {
		ApplicationContext context = getContext();
		user = (UserDTO) stage.getUserData();
		ProfileDTO profile = getProfile(context,user);
		decorateProfileInfo(profile,user);		
	}

	private ProfileDTO getProfile(ApplicationContext context, UserDTO user) {
		ProfileDTO profile = new ProfileDTO();
		profile.setUser(user);
		ProfileControllerImpl profileControllerImpl = (ProfileControllerImpl)context.getBean("profileControllerImpl");
		ProfileDTO profileRead = profileControllerImpl.getProfile(profile);
		return profileRead;
	}

	private void decorateProfileInfo(ProfileDTO profile, UserDTO user) {

		if(profile != null) {
			p_address.setText(profile.getAddress());
			//TODO RIGURDARE LA DATA
			Date date = new Date();
			p_datebirth.setText(date.toString());
			p_degree.setText(profile.getDegreeCourse());
			p_email.setText(profile.getEmail());
			p_matnumber.setText(profile.getMatriculationNumber());
		}
		p_lastname.setText(user.getLastName());
		p_name.setText(user.getFirstName());
		p_username.setText(user.getUsername());
	}
	
	private void decorateProfileToSave(ProfileDTO profile) {
		profile.setAddress(p_address.getText());
		//TODO DATE
		profile.setDateOfBirth(new Date());
		profile.setDegreeCourse(p_degree.getText());
		profile.setEmail(p_email.getText());
		profile.setMatriculationNumber(p_matnumber.getText());
		profile.setUser(user);
	}

	private ApplicationContext getContext() {
		context = new ClassPathXmlApplicationContext("spring-context.xml");
		return context;
	}

}

