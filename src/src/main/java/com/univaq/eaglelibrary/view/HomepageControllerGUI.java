package com.univaq.eaglelibrary.view;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.univaq.eaglelibrary.controllerImpl.LiteraryWorkControllerImpl;
import com.univaq.eaglelibrary.controllerImpl.ModuleControllerImpl;
import com.univaq.eaglelibrary.controllerImpl.ProfileControllerImpl;
import com.univaq.eaglelibrary.controllerImpl.TranscriptionControllerImpl;
import com.univaq.eaglelibrary.dto.LiteraryWorkDTO;
import com.univaq.eaglelibrary.dto.ModuleDTO;
import com.univaq.eaglelibrary.dto.ProfileDTO;
import com.univaq.eaglelibrary.dto.TranscriptionDTO;
import com.univaq.eaglelibrary.dto.UserDTO;
import com.univaq.eaglelibrary.persistence.exceptions.CreateModuleException;
import com.univaq.eaglelibrary.persistence.exceptions.MandatoryFieldException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class HomepageControllerGUI implements Initializable{

	@FXML
	private Label l_profile,l_search,l_transcription,l_module,l_logout,l_upload,l_yearOfStudy,l_yearUpload;

	@FXML
	private AnchorPane top,a_module,a_searchOpera,a_upload,a_trascription,a_profile;

	@FXML
	private Button b_chooseFile,b_send,b_saerchOpera,saveProfile,uploadOpera;

	@FXML
	private TextField t_year,t_author,t_title,p_name,p_lastname,p_username,p_email,p_datebirth,p_matnumber,
		p_address,p_degree,formYear,t_yearUpload,t_authorUpload,t_titleUpload,t_chooseFile;

	@FXML
	private TextArea t_partOfText,formComment;

	@FXML
	private ComboBox<String> co_category,co_categoryUpload;

	@FXML
	private CheckBox c_trascription;
	
    @FXML
    private TableColumn<TranscriptionTable, String> titleColumn,authorColumn,yearColumn,stateColumn;

    @FXML
    private TableView<TranscriptionTable> trascriptionTable,trascriptionTableClosed;

	private ApplicationContext context;
	private UserDTO user;
	final FileChooser fileChooser = new FileChooser();

	@FXML
    void uploadOpera(ActionEvent event) {
		LiteraryWorkDTO literaryWorkDTO = new LiteraryWorkDTO();
		literaryWorkDTO.setAuthor(t_authorUpload.getText());
		literaryWorkDTO.setTitle(t_titleUpload.getText());
		literaryWorkDTO.setYear(integerController(t_yearUpload,l_yearUpload));
		literaryWorkDTO.setCategory(co_categoryUpload.getValue());
		if(literaryWorkDTO.getYear() != null) {
			LiteraryWorkControllerImpl literaryWorkControllerImpl = (LiteraryWorkControllerImpl)context.getBean("literaryWorkControllerImpl");
			try {
				literaryWorkControllerImpl.createUpdateLiteraryWork(literaryWorkDTO);
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
    }

	@FXML
	void chooseFile(MouseEvent event) {

		File file = fileChooser.showOpenDialog(l_logout.getScene().getWindow());

		//TODO call service to store file 
	}
	
	@FXML
	void sendForm(ActionEvent event) {
		ModuleDTO module = new ModuleDTO();
		module.setComment(formComment.getText());
		module.setCreationDate(new Date());
		module.setUser(user);
		module.setUsername(user.getUsername());
		module.setYearOfTheStudy(integerController(formYear,l_yearOfStudy));
		module.setStatus("OPEN");
		// generating code TODO
		module.setCode("001");
		if(module.getYearOfTheStudy() != null) {	
			ModuleControllerImpl moduleControllerImpl = (ModuleControllerImpl)context.getBean("moduleControllerImpl");
			try {
				moduleControllerImpl.submitModule(module);
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setHeaderText("Modulo inviato correttamente");
				alert.showAndWait();
			} catch (MandatoryFieldException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText(e.getMessage());
				alert.showAndWait();
				e.printStackTrace();
			} catch (CreateModuleException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText(e.getMessage());
				alert.showAndWait();
				e.printStackTrace();
			}
		}
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
		user = (UserDTO) stage.getUserData();
		ProfileDTO profile = getProfile(context,user);
		decorateProfileInfo(profile,user);
		initializeTrascriptionTable();
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
	
	private Integer integerController(TextField textField, Label label) {
		Integer number = null;
		try {
			number = Integer.valueOf(textField.getText());
		} catch(Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Number not valid: "+label.getText());
			alert.showAndWait();
			e.printStackTrace();
		}
		return number;
	}

	private ApplicationContext getContext() {
		context = new ClassPathXmlApplicationContext("spring-context.xml");
		return context;
	}

	public void initialize(URL location, ResourceBundle resources) {
		getContext();
		initializeGenre();
	}

	private void initializeGenre() {
		ObservableList<String> category = FXCollections.observableArrayList("Narrativo","Fantasy","Horror","Giallo","Romanzo","Storico","Fantascienza");
		co_category.setItems(category);
		co_categoryUpload.setItems(category);
	}

	private void initializeTrascriptionTable() {
		TranscriptionControllerImpl transcriptionControllerImpl = (TranscriptionControllerImpl)context.getBean("transcriptionControllerImpl");
		TranscriptionDTO transcriptionDTO = new TranscriptionDTO();
		transcriptionDTO.setUsername(user.getUsername());
		List<TranscriptionDTO> transcriptions = transcriptionControllerImpl.getTrascriptionList(transcriptionDTO);
		if(transcriptions != null && !transcriptions.isEmpty()) {
			buildTransactionTable(transcriptions);
		}	
	}

	private void buildTransactionTable(List<TranscriptionDTO> transcriptions) {
		buildColumn();
		ObservableList<TranscriptionTable> trascriptionOpen = FXCollections.observableArrayList();
		ObservableList<TranscriptionTable> trascriptionClosed = FXCollections.observableArrayList();
		for (TranscriptionDTO transcriptionRead : transcriptions) {
			TranscriptionTable transcriptionTable = new TranscriptionTable(transcriptionRead);
			if(transcriptionRead.getStatus() != null && transcriptionRead.getStatus().equals("OPEN")) {
				trascriptionOpen.add(transcriptionTable);
			} else {
				trascriptionClosed.add(transcriptionTable);
			}
		}
		trascriptionTable.setItems(trascriptionOpen);
		trascriptionTableClosed.setItems(trascriptionClosed);
		
	}

	private void buildColumn() {
		stateColumn.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("status"));
		authorColumn.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("author"));
		titleColumn.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("title"));
		yearColumn.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("year"));
		
	}

}

