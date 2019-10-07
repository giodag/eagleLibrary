package com.univaq.eaglelibrary.view;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.univaq.eaglelibrary.controllerImpl.LiteraryWorkControllerImpl;
import com.univaq.eaglelibrary.controllerImpl.ModuleControllerImpl;
import com.univaq.eaglelibrary.controllerImpl.ProfileControllerImpl;
import com.univaq.eaglelibrary.controllerImpl.TranscriptionControllerImpl;
import com.univaq.eaglelibrary.dto.LiteraryWorkDTO;
import com.univaq.eaglelibrary.dto.LiteraryWorkListDTO;
import com.univaq.eaglelibrary.dto.LiteraryWorkListFilterDTO;
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
	private Label l_profile,l_search,l_transcription,l_module,l_logout,l_upload,l_yearOfStudy,l_yearUpload,l_year;

	@FXML
	private AnchorPane top,a_module,a_searchOpera,a_upload,a_trascription,a_profile;

	@FXML
	private Button b_chooseFile,b_send,b_saerchOpera,saveProfile,uploadOpera;

	@FXML
	private TextField t_year,t_author,t_title,p_name,p_lastname,p_username,p_email,p_matnumber,p_dateBirth,
		p_address,p_degree,formYear,t_yearUpload,t_authorUpload,t_titleUpload,t_chooseFile;

	@FXML
	private TextArea t_partOfText,formComment;

	@FXML
	private ComboBox<String> co_category,co_categoryUpload;

	@FXML
	private CheckBox c_trascription;
	
    @FXML
    private TableColumn<TranscriptionTable, String> titleColumn,authorColumn,yearColumn,statusColumn,
    	titleColumnC,authorColumnC,yearColumnC,statusColumnC;

    @FXML
    private TableView<TranscriptionTable> trascriptionTable,trascriptionTableClosed;

	private ApplicationContext context;
	private UserDTO user;
	final FileChooser fileChooser = new FileChooser();
	//errorNumber mi serve per capire se sono stati inseriti dati sbagliati oppure nulli
	private Boolean errorFormat;

	@FXML
    void uploadOpera(ActionEvent event) {
		LiteraryWorkDTO literaryWorkDTO = buildLiteraryWork(); 
		if(!errorFormat) {
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

	private LiteraryWorkDTO buildLiteraryWork() {
		LiteraryWorkDTO literaryWorkDTO = new LiteraryWorkDTO();
		literaryWorkDTO.setAuthor(t_authorUpload.getText());
		literaryWorkDTO.setTitle(t_titleUpload.getText());
		literaryWorkDTO.setYear(integerController(t_yearUpload,l_yearUpload));
		literaryWorkDTO.setCategory(co_categoryUpload.getValue());
		return literaryWorkDTO;
	}

	@FXML
	void chooseFile(MouseEvent event) {

		File file = fileChooser.showOpenDialog(l_logout.getScene().getWindow());

		//TODO call service to store file 
	}
	
    @FXML
    void searchLiteraryWork(ActionEvent event) {
    	LiteraryWorkListFilterDTO literaryWorkListFilterDTO = buildLiteraryWorkFilter();
    	if(!errorFormat) {
    		LiteraryWorkControllerImpl literaryWorkControllerImpl = (LiteraryWorkControllerImpl)context.getBean("literaryWorkControllerImpl");
        	LiteraryWorkListDTO literaryWorkListRead = literaryWorkControllerImpl.getLiteraryWork(literaryWorkListFilterDTO);
         	//TODO open a new window a lato di quella principale con la lista delle opere
    	}
    }
	
	private LiteraryWorkListFilterDTO buildLiteraryWorkFilter() {
		LiteraryWorkListFilterDTO literaryWorkListFilterDTO = new LiteraryWorkListFilterDTO();
		literaryWorkListFilterDTO.setAuthor(StringUtils.isEmpty(t_author.getText()) ? null : t_author.getText());
		literaryWorkListFilterDTO.setCategory(StringUtils.isEmpty(co_category.getValue()) ? null : co_category.getValue());
		literaryWorkListFilterDTO.setPartOfText(StringUtils.isEmpty(t_partOfText.getText()) ? null : t_partOfText.getText());
		literaryWorkListFilterDTO.setTitle(StringUtils.isEmpty(t_title.getText()) ? null : t_title.getText());
		literaryWorkListFilterDTO.setYear(integerController(t_year,l_year));
		return literaryWorkListFilterDTO;
	}

	@FXML
	void sendForm(ActionEvent event) {
		ModuleDTO module = buildModule();
		if(!errorFormat) {	
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

	private ModuleDTO buildModule() {
		ModuleDTO module = new ModuleDTO();
		module.setComment(formComment.getText());
		module.setCreationDate(new Date());
		module.setUser(user);
		module.setUsername(user.getUsername());
		module.setYearOfTheStudy(integerController(formYear,l_yearOfStudy));
		//TODO check date
		module.setStatus("OPEN");
		//TODO generating code TODO
		module.setCode("001");
		return module;
	}

	@FXML
	private void saveProfile(ActionEvent event) {
		ProfileDTO profile = new ProfileDTO();
		profile.setUser(user);
		decorateProfileToSave(profile);
		if(!errorFormat) {
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
			if(profile.getDateOfBirth() != null) {
				p_dateBirth.setText(formatDate(profile.getDateOfBirth()));
			}
			p_degree.setText(profile.getDegreeCourse());
			p_email.setText(user.getEmail());
			p_matnumber.setText(profile.getMatriculationNumber());
		}
		p_lastname.setText(user.getLastName());
		p_name.setText(user.getFirstName());
		p_username.setText(user.getUsername());
	}
	
	private void decorateProfileToSave(ProfileDTO profile) {
		profile.setAddress(p_address.getText());
		profile.setDateOfBirth(formatDate(p_dateBirth.getText()));
		profile.setDegreeCourse(p_degree.getText());
		profile.setMatriculationNumber(p_matnumber.getText());
		user.setEmail(p_email.getText());
		user.setFirstName(p_name.getText());
		user.setUsername(p_username.getText());
		profile.setUser(user);
	}
	
	private Integer integerController(TextField textField, Label label) {
		Integer number = null;
		if(!StringUtils.isEmpty(textField.getText())) {
			try {
				number = Integer.valueOf(textField.getText());
			} catch(Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Number not valid: "+label.getText());
				alert.showAndWait();
				e.printStackTrace();
				errorFormat = true;
			}
		} else {
			errorFormat = false;
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
		statusColumn.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("status"));
		authorColumn.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("author"));
		titleColumn.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("title"));
		yearColumn.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("year"));
		statusColumnC.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("status"));
		authorColumnC.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("author"));
		titleColumnC.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("title"));
		yearColumnC.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("year"));
	}
	
	private Date formatDate(String date) {  
	    Date dated = null;
	    if(date != null) {
	    	try {
				dated = new SimpleDateFormat("dd/MM/yyyy").parse(date);
			} catch (ParseException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Date not valid");
				alert.showAndWait();
				e.printStackTrace();
				errorFormat = true;
			}  
	    } else {
	    	errorFormat = false;
	    }
		
	    return dated;
	}
	
	private String formatDate(Date date) {  
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
	    String strDate = dateFormat.format(date);  
	    return strDate;
	}
    
}

