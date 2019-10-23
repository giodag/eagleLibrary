package com.univaq.eaglelibrary.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.univaq.eaglelibrary.controllerImpl.LiteraryWorkControllerImpl;
import com.univaq.eaglelibrary.dto.LiteraryWorkDTO;
import com.univaq.eaglelibrary.dto.PageDTO;
import com.univaq.eaglelibrary.dto.UserDTO;
import com.univaq.eaglelibrary.exceptions.MandatoryFieldException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AdministratorControllerGUI implements Initializable{

	@FXML
	private TextArea t_comment;

	@FXML
	private TableView<?> transcriptorTableA,transcriptorTableAA,assignTable,trascriptionTable,formTable,trascriptionTable2,trascriptorTable1;

	@FXML
	private TableColumn<?, ?> usernameColumnA,levelColumnAA,usernameColumnAA,statusColumnA,pageColumnA,
	yearColumnA,authorColumnA,titleColumnA,statusColumnC,pageColumnC,yearColumnC,authorColumnC,titleColumnC,
	usernameColumn,statusColumnT,pageColumnT,yearColumnT,authorColumnT,titleColumnT,usernameColumnT;

	@FXML
	private Button saveOpera,savePage,discardPage,search,b_chooseFile,rejectForm,uploadPage,b_acceptForm,saveTranscriptor;

	@FXML
	private Label l_yearUpload,l_upload,l_logout,l_yearSearch,l_titleUpload,l_yearOfStudy,l_assign,l_transcriptor,l_transcription,l_module;

	@FXML
	private AnchorPane top,a_uploadPage,a_upload,a_assign,a_trascription,a_module,a_transcriptor;

	@FXML
	private TextField t_yearUpload,t_authorUpload,t_titleUpload,t_chooseFile,t_yearSearch,t_authorSearch,
	t_titleSearch,t_degree,t_lastName,t_name,t_yearStudy,t_degreeT,t_lastNameT,t_nameT,t_level;

	@FXML
	private ImageView viewPage;

	@FXML
	private ComboBox<String> co_categoryUpload,co_categorySearch;

	private Stage ownStage;
	private ApplicationContext context;
	private UserDTO user;
	private File page;
	private List<File> pageList;
	final FileChooser fileChooser = new FileChooser();
	//errorNumber mi serve per capire se sono stati inseriti dati sbagliati oppure nulli
	private boolean errorFormat;


    @FXML
    void acceptForm(ActionEvent event) {

    }
    

    @FXML
    void rejectForm(ActionEvent event) {

    }
    
    @FXML
    void saveTranscriptor(ActionEvent event) {

    }
    
    @FXML
    void search(ActionEvent event) {

    }
    
	@FXML
	void saveOpera(ActionEvent event) {
		LiteraryWorkDTO literaryWorkDTO = buildLiteraryWork(); 
		if(!errorFormat) {
			LiteraryWorkControllerImpl literaryWorkControllerImpl = (LiteraryWorkControllerImpl)context.getBean("literaryWorkControllerImpl");
			try {
				literaryWorkControllerImpl.createUpdateLiteraryWork(literaryWorkDTO);
				pageList = null;
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setHeaderText("Opera salvata correttamente");
				alert.showAndWait();
				resetPage();
			} catch (MandatoryFieldException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText(e.getMessage());
				alert.showAndWait();
				e.printStackTrace();
			}
		} else {
			errorFormat = false;
		}
	}

	private LiteraryWorkDTO buildLiteraryWork() {
		LiteraryWorkDTO literaryWorkDTO = new LiteraryWorkDTO();
		literaryWorkDTO.setAuthor(t_authorUpload.getText());
		literaryWorkDTO.setTitle(t_titleUpload.getText());
		literaryWorkDTO.setYear(integerController(t_yearUpload,l_yearUpload));
		literaryWorkDTO.setCategory(co_categoryUpload.getValue());
		literaryWorkDTO.setPageList(buildPageList());
		return literaryWorkDTO;
	}

	private List<PageDTO> buildPageList() {
		List<PageDTO> pageListToStore = null;
		if(pageList != null && !pageList.isEmpty()) {
			pageListToStore = new ArrayList<PageDTO>();
			Integer pageNumber = 0;
			for (File filePage : pageList) {
				pageListToStore.add(buildPageDTO(filePage, pageNumber));
				pageNumber += 1;
			}
		}
		return pageListToStore;
	}

	private PageDTO buildPageDTO(File filePage,Integer pageNumber){
		PageDTO pageToStore = new PageDTO();
		byte[] fileContent = null;
		try {
			fileContent = Files.readAllBytes(filePage.toPath());
		} catch (IOException e) {
			// TODO gestire l'errore
			e.printStackTrace();
		}
		pageToStore.setImage(fileContent);
		pageToStore.setPageNumber(pageNumber);
		return pageToStore;
	}

	@FXML
	void chooseFile(MouseEvent event) {
		page = fileChooser.showOpenDialog(l_yearUpload.getScene().getWindow());
		if(page != null) {
			t_chooseFile.setText(page.getName());
		}
	}

	@FXML
	void uploadPage(ActionEvent event) {
		if(page != null) {
			a_uploadPage.setVisible(true);
			ownStage.setWidth(1240);
			Image image = new Image(page.toURI().toString());
			viewPage.setImage(image);
		}
	}
	
	@FXML
    void discardPage(ActionEvent event) {
    	resetPage();
    }
   
    @FXML
    void savePage(ActionEvent event) {
    	if(pageList == null) {
    		pageList = new ArrayList<File>();
    	}
    	pageList.add(page);
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("Pagina salvata correttamente");
		alert.showAndWait();
		resetPage();
    }

	private Integer integerController(TextField textField, Label label) {
		Integer number = null;
		if(!StringUtils.isEmpty(textField.getText())) {
			try {
				number = Integer.valueOf(textField.getText());
			} catch(Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Formato del numero non valido: "+label.getText());
				alert.showAndWait();
				e.printStackTrace();
				errorFormat = true;
			}
		} else {
			errorFormat = false;
		}
		return number;
	}

	@FXML
	private void handleButtonAction(MouseEvent eventMuose) {
		if(eventMuose.getSource() == l_assign) {
			a_assign.setVisible(true);
			a_transcriptor.setVisible(false);
			a_module.setVisible(false);
			a_trascription.setVisible(false);
			a_upload.setVisible(false);
			a_uploadPage.setVisible(false);
			ownStage.setHeight(570);
			ownStage.setWidth(703);
			resetPage();
		} else {
			if(eventMuose.getSource() == l_upload) {
				a_assign.setVisible(false);
				a_transcriptor.setVisible(false);
				a_module.setVisible(false);
				a_trascription.setVisible(false);
				a_upload.setVisible(true);
				a_uploadPage.setVisible(false);
				ownStage.setHeight(570);
				ownStage.setWidth(703);
				resetPage();
			} else {
				if(eventMuose.getSource() == l_transcriptor) {
					a_assign.setVisible(false);
					a_transcriptor.setVisible(true);
					a_module.setVisible(false);
					a_trascription.setVisible(false);
					a_upload.setVisible(false);
					a_uploadPage.setVisible(false);
					ownStage.setHeight(570);
					ownStage.setWidth(703);
					resetPage();
				} else {
					if(eventMuose.getSource() == l_transcription) {
						a_assign.setVisible(false);
						a_transcriptor.setVisible(false);
						a_module.setVisible(false);
						a_trascription.setVisible(true);
						a_upload.setVisible(false);
						a_uploadPage.setVisible(false);
						ownStage.setHeight(570);
						ownStage.setWidth(703);
						resetPage();
					} else {
						if(eventMuose.getSource() == l_module) {
							a_assign.setVisible(false);
							a_transcriptor.setVisible(false);
							a_module.setVisible(true);
							a_trascription.setVisible(false);
							a_upload.setVisible(false);
							a_uploadPage.setVisible(false);
							ownStage.setHeight(570);
							ownStage.setWidth(703);
							resetPage();
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

	private void resetPage() {
		page = null;
		viewPage.setImage(null);
		t_chooseFile.setText(null);
	}

	public void init(Stage stage) {
		ownStage = stage;
		user = (UserDTO) stage.getUserData();
		//initializeTable();
	}

	public void initialize(URL location, ResourceBundle resources) {
		getContext();
		initializeGenre();
	}

	private ApplicationContext getContext() {
		context = new ClassPathXmlApplicationContext("spring-context.xml");
		return context;
	}

	private void initializeGenre() {
		ObservableList<String> category = FXCollections.observableArrayList("Narrativo","Fantasy","Horror","Giallo","Romanzo","Storico","Fantascienza");
		co_categoryUpload.setItems(category);
	}
}
