package com.univaq.eaglelibrary.view;

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
import com.univaq.eaglelibrary.dto.LockTranscriptionRequestDTO;
import com.univaq.eaglelibrary.dto.ModuleDTO;
import com.univaq.eaglelibrary.dto.PageDTO;
import com.univaq.eaglelibrary.dto.ProfileDTO;
import com.univaq.eaglelibrary.dto.TranscriptionDTO;
import com.univaq.eaglelibrary.dto.UserDTO;
import com.univaq.eaglelibrary.exceptions.CannotUpdateModuleException;
import com.univaq.eaglelibrary.exceptions.CreateModuleException;
import com.univaq.eaglelibrary.exceptions.MandatoryFieldException;
import com.univaq.eaglelibrary.utility.Permission;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class HomepageControllerGUI implements Initializable{

	@FXML
	private Label l_profile,l_search,l_transcription,l_module,l_logout,l_yearOfStudy,l_year,l_admin;

	@FXML
	private AnchorPane top,a_module,a_searchOpera,a_trascription,a_profile,a_searchList;

	@FXML
	private Button b_send,b_saerchOpera,saveProfile;

	@FXML
	private TextField t_year,t_author,t_title,p_name,p_lastname,p_username,p_email,p_matnumber,p_dateBirth,
	p_address,p_degree,formYear,t_yearUpload,t_authorUpload,t_titleUpload,t_chooseFile;

	@FXML
	private TextArea t_partOfText,formComment;

	@FXML
	private ComboBox<String> co_category;

	@FXML
	private CheckBox c_trascription;

	@FXML
	private TableColumn<TranscriptionTable, String> titleColumn,authorColumn,yearColumn,statusColumn,pageColumn,
	titleColumnC,authorColumnC,yearColumnC,statusColumnC,pageColumnC,titleSearchColumn,authorSearchColumn,yearSearchColumn;

	@FXML
	private TableView<TranscriptionTable> trascriptionTable,trascriptionTableClosed,searchTable;

	@FXML
	private ImageView pageView;

	private Stage ownStage;
	private ApplicationContext context;
	private UserDTO user;
	//errorNumber mi serve per capire se sono stati inseriti dati sbagliati oppure nulli
	private boolean errorFormat;

	@FXML
	void searchLiteraryWork(ActionEvent event) {
		LiteraryWorkListFilterDTO literaryWorkListFilterDTO = buildLiteraryWorkFilter();
		if(!errorFormat) {
			LiteraryWorkControllerImpl literaryWorkControllerImpl = (LiteraryWorkControllerImpl)context.getBean("literaryWorkControllerImpl");
			LiteraryWorkListDTO literaryWorkListRead = literaryWorkControllerImpl.getLiteraryWork(literaryWorkListFilterDTO);
			if(literaryWorkListRead != null && literaryWorkListRead.getLiteraryWorkList() != null 
					&& !literaryWorkListRead.getLiteraryWorkList().isEmpty()) {
				buildSearchList(literaryWorkListRead.getLiteraryWorkList());
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setHeaderText("Nessuna opera corrisponde ai filtri inseriti");
				alert.showAndWait();
			}
			a_searchList.setVisible(true);
			ownStage.setHeight(774);
		} else {
			errorFormat = false;
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
	void sendForm(ActionEvent event) throws CannotUpdateModuleException {
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
		} else {
			errorFormat = false;
		}
	}

	private ModuleDTO buildModule() {
		ModuleDTO module = new ModuleDTO();
		module.setComment(formComment.getText());
		module.setCreationDate(new Date());
		module.setUser(user);
		module.setUsername(user.getUsername());
		module.setYearOfTheStudy(integerController(formYear,l_yearOfStudy));
		module.setStatus("OPEN");
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
		} else {
			errorFormat = false;
		}
	}

	@FXML
	private void admin(MouseEvent eventMuose) {
		Stage stage = (Stage) l_profile.getScene().getWindow();
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
		stage.getIcons().add(new Image(LoginView.class.getClassLoader().getResourceAsStream("images/libro.png")));
		stage.setScene(scene);
		stage.setUserData(user);
		AdministratorControllerGUI controller = (AdministratorControllerGUI)loader.getController();
		controller.init(stage); 
		stage.show();
	}
	
	@FXML
	private void handleButtonAction(MouseEvent eventMuose) {
		if(eventMuose.getSource() == l_profile) {
			a_profile.setVisible(true);
			a_searchOpera.setVisible(false);
			a_module.setVisible(false);
			a_trascription.setVisible(false);
			a_searchList.setVisible(false);
			ownStage.setHeight(570);
			ownStage.setWidth(703);
		} else {
			if(eventMuose.getSource() == l_search) {
				a_searchOpera.setVisible(true);
				a_profile.setVisible(false);
				a_module.setVisible(false);
				a_trascription.setVisible(false);
				a_searchList.setVisible(false);
				ownStage.setHeight(570);
				ownStage.setWidth(703);
			} else {
				if(eventMuose.getSource() == l_module) {
					a_profile.setVisible(false);
					a_searchOpera.setVisible(false);
					a_module.setVisible(true);
					a_trascription.setVisible(false);
					a_searchList.setVisible(false);
					ownStage.setHeight(570);
					ownStage.setWidth(703);
				} else {
					if(eventMuose.getSource() == l_transcription) {
						a_profile.setVisible(false);
						a_searchOpera.setVisible(false);
						a_module.setVisible(false);
						a_trascription.setVisible(true);
						a_searchList.setVisible(false);
						initializeTrascriptionTable();
						ownStage.setHeight(570);
						ownStage.setWidth(703);
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


	public void init(Stage stage) {
		ownStage = stage;
		user = (UserDTO) stage.getUserData();
		if(user.getPermission().equals(Permission.ADMIN)) {
			l_admin.setVisible(true);
		}
		ProfileDTO profile = getProfile();
		decorateProfileInfo(profile);
		initializeTrascriptionTable();
	}

	private ProfileDTO getProfile() {
		ProfileDTO profile = new ProfileDTO();
		profile.setUser(user);
		ProfileControllerImpl profileControllerImpl = (ProfileControllerImpl)context.getBean("profileControllerImpl");
		ProfileDTO profileRead = profileControllerImpl.getProfile(profile);
		return profileRead;
	}

	private void decorateProfileInfo(ProfileDTO profile) {

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
	}

	private void initializeTrascriptionTable() {
		setTableRowDoubleClick();
		TranscriptionControllerImpl transcriptionControllerImpl = (TranscriptionControllerImpl)context.getBean("transcriptionControllerImpl");
		TranscriptionDTO transcriptionDTO = new TranscriptionDTO();
		transcriptionDTO.setUsername(user.getUsername());
		List<TranscriptionDTO> transcriptions = transcriptionControllerImpl.getTrascriptionList(transcriptionDTO);
		if(transcriptions != null && !transcriptions.isEmpty()) {
			buildTransactionTable(transcriptions);
		}	
	}

	private void setTableRowDoubleClick() {
		trascriptionTable.setRowFactory( new Callback<TableView<TranscriptionTable>, TableRow<TranscriptionTable>>() {
			public TableRow<TranscriptionTable> call(TableView<TranscriptionTable> tv) {
				final TableRow<TranscriptionTable> row = new TableRow<TranscriptionTable>();
				row.setOnMouseClicked(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent event) {
						if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
							TranscriptionTable rowData = row.getItem();
							TranscriptionControllerImpl transcriptionControllerImpl = (TranscriptionControllerImpl)context.getBean("transcriptionControllerImpl");
							LockTranscriptionRequestDTO lockTranscriptionRequestDTO = new LockTranscriptionRequestDTO();
							user.setTranscriptionTable(rowData);
							lockTranscriptionRequestDTO.setUsername(user.getUsername());
							TranscriptionDTO transcriptionToLock = readTranscription();
							lockTranscriptionRequestDTO.setTranscription(transcriptionToLock);
							transcriptionControllerImpl.lockTranscription(lockTranscriptionRequestDTO);
							
							Stage stage = (Stage) l_profile.getScene().getWindow();
							stage.close();

							String fxmlFile = "/fxml/transcriptionPage.fxml";

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
							TranscriptionControllerGUI controller = (TranscriptionControllerGUI)loader.getController();
							controller.init(stage); 
							stage.show();
						}
					}
				});
				return row ;
			}
		});
		searchTable.setRowFactory( new Callback<TableView<TranscriptionTable>, TableRow<TranscriptionTable>>() {
			public TableRow<TranscriptionTable> call(TableView<TranscriptionTable> tv) {
				final TableRow<TranscriptionTable> row = new TableRow<TranscriptionTable>();
				row.setOnMouseClicked(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent event) {
						if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
							TranscriptionTable rowData = row.getItem();
							Stage stage = (Stage) l_profile.getScene().getWindow();
							stage.close();
							String fxmlFile = null;
							if(c_trascription.isSelected()) {
								fxmlFile = "/fxml/viewOperaTranscription.fxml";
							} else {
								fxmlFile = "/fxml/viewOpera.fxml";
							}

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
							user.setTranscriptionTable(rowData);
							stage.setUserData(user);
							if(c_trascription.isSelected()) {
								ViewOperaTranscriptionControllerGUI controller = (ViewOperaTranscriptionControllerGUI)loader.getController();
								controller.init(stage); 
							} else {
								ViewOperaControllerGUI controller = (ViewOperaControllerGUI)loader.getController();
								controller.init(stage); 
							}
							stage.show();

						}
					}
				});
				return row ;
			}
		});
	}

	private void buildTransactionTable(List<TranscriptionDTO> transcriptions) {
		buildColumn();
		ObservableList<TranscriptionTable> trascriptionOpen = FXCollections.observableArrayList();
		ObservableList<TranscriptionTable> trascriptionClosed = FXCollections.observableArrayList();
		for (TranscriptionDTO transcriptionRead : transcriptions) {
			TranscriptionTable transcriptionTable = new TranscriptionTable(transcriptionRead);
			if(transcriptionRead.getStatus() != null && (transcriptionRead.getStatus().equals("OPEN")) || transcriptionRead.getStatus().equals("REJECTED")) {
				trascriptionOpen.add(transcriptionTable);
			} else {
				trascriptionClosed.add(transcriptionTable);
			}
		}
		trascriptionTable.setItems(trascriptionOpen);
		trascriptionTableClosed.setItems(trascriptionClosed);

	}

	private void buildSearchList(List<LiteraryWorkDTO> literaryWorkList) {
		buildSearchColumn();
		ObservableList<TranscriptionTable> operaList = FXCollections.observableArrayList();
		for (LiteraryWorkDTO literaryWorkRead : literaryWorkList) {
			TranscriptionTable transcriptionTable = new TranscriptionTable(literaryWorkRead);
			if(c_trascription.isSelected()) {
				if(isTranscribed(literaryWorkRead))
					operaList.add(transcriptionTable);
			} else {
				operaList.add(transcriptionTable);
			}
		}
		searchTable.setItems(operaList);
	}

	private boolean isTranscribed(LiteraryWorkDTO literaryWorkRead) {
		if(literaryWorkRead.getPageList() != null && !literaryWorkRead.getPageList().isEmpty()) {
			for (PageDTO pageDTO : literaryWorkRead.getPageList()) {
				if(pageDTO.getTranscriptionDTO() != null && pageDTO.getTranscriptionDTO().getStatus().equals("CLOSED")) {
					return true;
				}
			}
		}
		return false;
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
	
	private void buildColumn() {
		statusColumn.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("status"));
		authorColumn.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("author"));
		titleColumn.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("title"));
		yearColumn.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("year"));
		pageColumn.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("page"));
		statusColumnC.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("status"));
		authorColumnC.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("author"));
		titleColumnC.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("title"));
		yearColumnC.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("year"));
		pageColumnC.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("page"));
	}

	private void buildSearchColumn() {
		authorSearchColumn.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("author"));
		titleSearchColumn.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("title"));
		yearSearchColumn.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("year"));
	}

	private Date formatDate(String date) {  
		Date dated = null;
		if(date != null) {
			try {
				dated = new SimpleDateFormat("dd/MM/yyyy").parse(date);
			} catch (ParseException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Formato della data non valido");
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

