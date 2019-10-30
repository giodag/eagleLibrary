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
import com.univaq.eaglelibrary.controllerImpl.ModuleControllerImpl;
import com.univaq.eaglelibrary.controllerImpl.ProfileControllerImpl;
import com.univaq.eaglelibrary.controllerImpl.TranscriptionControllerImpl;
import com.univaq.eaglelibrary.controllerImpl.UserControllerImpl;
import com.univaq.eaglelibrary.dto.AssignTranscriptionRequestDTO;
import com.univaq.eaglelibrary.dto.AssignTranscriptionResponseDTO;
import com.univaq.eaglelibrary.dto.LiteraryWorkDTO;
import com.univaq.eaglelibrary.dto.LiteraryWorkListDTO;
import com.univaq.eaglelibrary.dto.LiteraryWorkListFilterDTO;
import com.univaq.eaglelibrary.dto.ModuleDTO;
import com.univaq.eaglelibrary.dto.PageDTO;
import com.univaq.eaglelibrary.dto.ProfileDTO;
import com.univaq.eaglelibrary.dto.TranscriptionDTO;
import com.univaq.eaglelibrary.dto.UnassignTranscriptionRequestDTO;
import com.univaq.eaglelibrary.dto.UnassignTranscriptionResponseDTO;
import com.univaq.eaglelibrary.dto.UserDTO;
import com.univaq.eaglelibrary.dto.UserFilterDTO;
import com.univaq.eaglelibrary.dto.UserListDTO;
import com.univaq.eaglelibrary.exceptions.CannotUpdateModuleException;
import com.univaq.eaglelibrary.exceptions.CreateUserException;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AdministratorControllerGUI implements Initializable{

	@FXML
	private TextArea t_comment;

	@FXML
	private TableView<UserDTO>formTable,transcriptorTableA,transcriptorTableAA,trascriptorTable1;

	@FXML
	private TableView<TranscriptionTable> assignTable,trascriptionTable,trascriptionTable2;

	@FXML
	private TableColumn<UserDTO, Integer> levelColumnAA,levelColumnA;

	@FXML
	private TableColumn<UserDTO, String> usernameColumnA,usernameColumnAA,usernameColumn,usernameColumnT;

	@FXML
	private TableColumn<TranscriptionTable, String> pageColumnA,yearColumnA,authorColumnA,titleColumnA,statusColumnA,
	statusColumnC,pageColumnC,yearColumnC,authorColumnC,titleColumnC,statusColumnT,pageColumnT,yearColumnT,authorColumnT,titleColumnT;

	@FXML
	private Button saveOpera,savePage,discardPage,search,b_chooseFile,rejectForm,uploadPage,b_acceptForm,saveTranscriptor;

	@FXML
	private Label l_yearUpload,l_upload,l_logout,l_yearSearch,l_titleUpload,l_yearOfStudy,l_assign,l_transcriptor,l_transcription,l_module,l_livel;

	@FXML
	private AnchorPane top,a_uploadPage,a_upload,a_assign,a_trascription,a_module,a_transcriptor;

	@FXML
	private TextField t_yearUpload,t_authorUpload,t_titleUpload,t_chooseFile,t_yearSearch,t_authorSearch,
	t_titleSearch,t_degree,t_lastName,t_name,t_yearStudy,t_usernameT,t_lastNameT,t_nameT,t_level;

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
	private String username;
	private TranscriptionDTO transcription;
	//errorNumber mi serve per capire se sono stati inseriti dati sbagliati oppure nulli
	private boolean errorFormat;


	@FXML
	void acceptForm(ActionEvent event) {
		ModuleControllerImpl moduleControllerImpl = (ModuleControllerImpl)context.getBean("moduleControllerImpl");
		ModuleDTO moduleDTO = new ModuleDTO();
		moduleDTO.setUsername(username);
		moduleDTO.setStatus("ACCEPTED");
		try {
			moduleControllerImpl.validateModule(moduleDTO);
			UserControllerImpl userControllerImpl = (UserControllerImpl)context.getBean("userControllerImpl");
			UserDTO userDTO = new UserDTO();
			userDTO.setUsername(username);
			userDTO = userControllerImpl.getUser(userDTO);
			userDTO.setPermission(Permission.TRANSCRIBER);
			try {
				userControllerImpl.registration(userDTO);
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setHeaderText("Modulo accettato");
				alert.showAndWait();
				//aggiornamento della tabella
				initializeFormTable();
				resetPage();
			} catch (MandatoryFieldException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText(e.getMessage());
				alert.showAndWait();
				e.printStackTrace();
			} catch (CreateUserException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Errore nella modifica dei permessi");
				alert.showAndWait();
				e.printStackTrace();
			}
		} catch (MandatoryFieldException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText(e.getMessage());
			alert.showAndWait();
			e.printStackTrace();
		} catch (CannotUpdateModuleException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Errore nel'accettazione del modulo");
			alert.showAndWait();
			e.printStackTrace();
		}
	}


	@FXML
	void rejectForm(ActionEvent event) {
		ModuleControllerImpl moduleControllerImpl = (ModuleControllerImpl)context.getBean("moduleControllerImpl");
		ModuleDTO moduleDTO = new ModuleDTO();
		moduleDTO.setUsername(username);
		moduleDTO.setStatus("REJECTED");
		try {
			moduleControllerImpl.validateModule(moduleDTO);
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Modulo rigettato");
			alert.showAndWait();
			//aggiornamento della tabella
			initializeFormTable();
			resetPage();
		} catch (MandatoryFieldException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText(e.getMessage());
			alert.showAndWait();
			e.printStackTrace();
		} catch (CannotUpdateModuleException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Errore nel rifiuto del modulo");
			alert.showAndWait();
			e.printStackTrace();
		}
	}

	@FXML
	void saveTranscriptor(ActionEvent event) {
		if(!errorFormat) {
			UserControllerImpl userControllerImpl = (UserControllerImpl)context.getBean("userControllerImpl");
			UserDTO userDTO = new UserDTO();
			userDTO.setUsername(t_usernameT.getText());
			userDTO = userControllerImpl.getUser(userDTO);
			if(userDTO != null) {
				userDTO.setLevel(integerController(t_level, l_livel));
				try {
					userControllerImpl.updateUser(userDTO);
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setHeaderText("Trascrittore salvato correttamente");
					alert.showAndWait();
				} catch (MandatoryFieldException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setHeaderText(e.getMessage());
					alert.showAndWait();
					e.printStackTrace();
				}
			}
		} else {
			errorFormat = false;
		}
		
	}

	@FXML
	void search(ActionEvent event) {
		LiteraryWorkListFilterDTO literaryWorkListFilterDTO = buildLiteraryWorkFilter();
		if(!errorFormat) {
			LiteraryWorkControllerImpl literaryWorkControllerImpl = (LiteraryWorkControllerImpl)context.getBean("literaryWorkControllerImpl");
			LiteraryWorkListDTO literaryWorkListRead = literaryWorkControllerImpl.getLiteraryWork(literaryWorkListFilterDTO);
			List<TranscriptionDTO> transcriptionToShow = new ArrayList<TranscriptionDTO>();
			if(literaryWorkListRead != null && literaryWorkListRead.getLiteraryWorkList() != null 
					&& !literaryWorkListRead.getLiteraryWorkList().isEmpty()) {
				for (LiteraryWorkDTO literaryWorkDTO : literaryWorkListRead.getLiteraryWorkList()) {
					if(literaryWorkDTO.getPageList() != null && !literaryWorkDTO.getPageList().isEmpty()) {
						for (PageDTO pageDTO : literaryWorkDTO.getPageList()) {
							if(pageDTO.getTranscriptionDTO() != null && pageDTO.getTranscriptionDTO().getStatus() != null
									&& !pageDTO.getTranscriptionDTO().getStatus().equalsIgnoreCase("closed") ) {
								pageDTO.getTranscriptionDTO().setLiteraryWork(literaryWorkDTO);
								pageDTO.getTranscriptionDTO().setPage(pageDTO);
								transcriptionToShow.add(pageDTO.getTranscriptionDTO());
							}
						}
					}
				}
				if(!transcriptionToShow.isEmpty()) {
					buildSearchList(transcriptionToShow);
				} else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setHeaderText("Nessuna trascrizione corrisponde ai filtri inseriti");
					alert.showAndWait();
				}
			}
		} else {
			errorFormat = false;
		}
	}

	private void buildSearchList(List<TranscriptionDTO> transcriptionList) {
		buildSearchColumn();
		ObservableList<TranscriptionTable> operaList = FXCollections.observableArrayList();
		for (TranscriptionDTO transcription : transcriptionList) {
			TranscriptionTable transcriptionTable = new TranscriptionTable(transcription);
			operaList.add(transcriptionTable);
		}
		trascriptionTable.setItems(operaList);
	}

	private void buildSearchColumn() {
		statusColumnC.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("status"));
		authorColumnC.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("author"));
		titleColumnC.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("title"));
		yearColumnC.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("year"));
		pageColumnC.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("page"));
	}

	private LiteraryWorkListFilterDTO buildLiteraryWorkFilter() {
		LiteraryWorkListFilterDTO literaryWorkListFilterDTO = new LiteraryWorkListFilterDTO();
		literaryWorkListFilterDTO.setAuthor(StringUtils.isEmpty(t_authorSearch.getText()) ? null : t_authorSearch.getText());
		literaryWorkListFilterDTO.setCategory(StringUtils.isEmpty(co_categorySearch.getValue()) ? null : co_categorySearch.getValue());
		literaryWorkListFilterDTO.setTitle(StringUtils.isEmpty(t_titleSearch.getText()) ? null : t_titleSearch.getText());
		literaryWorkListFilterDTO.setYear(integerController(t_yearSearch,l_yearSearch));
		return literaryWorkListFilterDTO;
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
		username = null;
		t_name.setText(null);
		t_lastName.setText(null);
		t_degree.setText(null);
		t_yearStudy.setText(null);
		t_comment.setText(null);
		transcription = null;
		trascriptionTable2.setItems(null);
	}

	public void init(Stage stage) {
		ownStage = stage;
		user = (UserDTO) stage.getUserData();
		initializeTable();
	}

	private void initializeTable() {
		initilizeTranscriberTable();
		initilizeTranscriptionTable();
		initializeFormTable();
		setTableRowDoubleClick();

	}


	private void initializeFormTable() {
		ModuleControllerImpl moduleControllerImpl = (ModuleControllerImpl)context.getBean("moduleControllerImpl");
		List<ModuleDTO> modulesRead = moduleControllerImpl.getAllModules();
		ObservableList<UserDTO> userForm = FXCollections.observableArrayList();
		for (ModuleDTO moduleDTO : modulesRead) {
			if(moduleDTO.getStatus() != null && moduleDTO.getStatus().equalsIgnoreCase("open")){
				userForm.add(moduleDTO.getUser());
			}
		}
		formTable.setItems(userForm);
	}


	private void initilizeTranscriptionTable() {
		buildTranscriptionColumn();
		ObservableList<TranscriptionTable> transcription = FXCollections.observableArrayList();
		LiteraryWorkControllerImpl literaryWorkControllerImpl = (LiteraryWorkControllerImpl)context.getBean("literaryWorkControllerImpl");
		LiteraryWorkListFilterDTO literaryWorkListFilterDTO = new LiteraryWorkListFilterDTO();
		LiteraryWorkListDTO literaryWorkListRead = literaryWorkControllerImpl.getLiteraryWork(literaryWorkListFilterDTO );
		if(literaryWorkListRead != null && literaryWorkListRead.getLiteraryWorkList() != null 
				&& !literaryWorkListRead.getLiteraryWorkList().isEmpty()) {
			for (LiteraryWorkDTO literaryWorkDTO : literaryWorkListRead.getLiteraryWorkList()) {
				if(literaryWorkDTO.getPageList() != null && !literaryWorkDTO.getPageList().isEmpty()) {
					for (PageDTO pageDTO : literaryWorkDTO.getPageList()) {
						if(pageDTO.getTranscriptionDTO() != null) {
							if(pageDTO.getTranscriptionDTO().getStatus() != null && !pageDTO.getTranscriptionDTO().getStatus().equalsIgnoreCase("closed")) {
								pageDTO.getTranscriptionDTO().setLiteraryWork(literaryWorkDTO);
								pageDTO.getTranscriptionDTO().setPage(pageDTO);
								TranscriptionTable transcriptionTable = new TranscriptionTable(pageDTO.getTranscriptionDTO());
								transcription.add(transcriptionTable);
							}
						} else {
							TranscriptionTable transcriptionTable = new TranscriptionTable(literaryWorkDTO);
							if(pageDTO.getPageNumber() != null) {
								transcriptionTable.setPage(pageDTO.getPageNumber().toString());
							}
							transcriptionTable.setStatus("TO ASSIGN");
							transcription.add(transcriptionTable);
						}
					}
				}
			}
		}
		assignTable.setItems(transcription);
	}


	private void buildTranscriptionColumn() {
		pageColumnA.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("page"));
		yearColumnA.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("year"));
		authorColumnA.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("author"));
		titleColumnA.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("title"));
		statusColumnC.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("status"));
		pageColumnC.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("page"));
		yearColumnC.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("year"));
		authorColumnC.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("author"));
		titleColumnC.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("title"));
		statusColumnT.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("status"));
		pageColumnT.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("page"));
		yearColumnT.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("year"));
		authorColumnT.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("author"));
		titleColumnT.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("title"));
		statusColumnA.setCellValueFactory(new PropertyValueFactory<TranscriptionTable,String>("status"));
	}


	private void initilizeTranscriberTable() {
		buildTranscriberColumn();
		ObservableList<UserDTO> transcriber = FXCollections.observableArrayList();
		UserControllerImpl userControllerImpl = (UserControllerImpl)context.getBean("userControllerImpl");
		UserFilterDTO userFilterDTO = new UserFilterDTO();
		userFilterDTO.setTranscriber(true);
		UserListDTO transcriberList = userControllerImpl.getUserList(userFilterDTO);
		if(transcriberList != null && transcriberList.getListUserDTO() != null && !transcriberList.getListUserDTO().isEmpty()) {
			for (UserDTO userDTO : transcriberList.getListUserDTO()) {
				transcriber.add(userDTO);
			}
		}
		trascriptorTable1.setItems(transcriber);
	}


	private void buildTranscriberColumn() {
		usernameColumnA.setCellValueFactory(new PropertyValueFactory<UserDTO,String>("username"));
		levelColumnA.setCellValueFactory(new PropertyValueFactory<UserDTO,Integer>("level"));
		levelColumnAA.setCellValueFactory(new PropertyValueFactory<UserDTO,Integer>("level"));
		usernameColumnAA.setCellValueFactory(new PropertyValueFactory<UserDTO,String>("username"));
		usernameColumn.setCellValueFactory(new PropertyValueFactory<UserDTO,String>("username"));
		usernameColumnT.setCellValueFactory(new PropertyValueFactory<UserDTO,String>("username"));
	}


	private void setTableRowDoubleClick() {
		trascriptionTable.setRowFactory( new Callback<TableView<TranscriptionTable>, TableRow<TranscriptionTable>>() {
			public TableRow<TranscriptionTable> call(TableView<TranscriptionTable> tv) {
				final TableRow<TranscriptionTable> row = new TableRow<TranscriptionTable>();
				row.setOnMouseClicked(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent event) {
						if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
							TranscriptionTable rowData = row.getItem();
							Stage stage = (Stage) l_upload.getScene().getWindow();
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
							stage.setScene(scene);
							user.setTranscriptionTable(rowData);
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
		trascriptorTable1.setRowFactory( new Callback<TableView<UserDTO>, TableRow<UserDTO>>() {
			public TableRow<UserDTO> call(TableView<UserDTO> tv) {
				final TableRow<UserDTO> row = new TableRow<UserDTO>();
				row.setOnMouseClicked(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent event) {
						if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
							UserDTO rowData = row.getItem();
							resetPage();
							UserControllerImpl userControllerImpl = (UserControllerImpl)context.getBean("userControllerImpl");
							UserDTO transcriber = userControllerImpl.getUser(rowData);
							t_nameT.setText(transcriber.getFirstName());
							t_lastNameT.setText(transcriber.getLastName());
							t_usernameT.setText(transcriber.getUsername());
							if(transcriber.getLevel() != null) {
								t_level.setText(transcriber.getLevel().toString());
							}
							if(transcriber.getTranscriptionList() != null) {
								ObservableList<TranscriptionTable> transcriptionTables = FXCollections.observableArrayList();
								for (TranscriptionDTO transcription : transcriber.getTranscriptionList()) {
									LiteraryWorkControllerImpl literaryWorkControllerImpl = (LiteraryWorkControllerImpl)context.getBean("literaryWorkControllerImpl");
									LiteraryWorkDTO literaryWorkDTO = new LiteraryWorkDTO();
									if(transcription.getPage() != null && transcription.getPage().getIdLiteraryWork() != null) {
										literaryWorkDTO.setId(transcription.getPage().getIdLiteraryWork());
										transcription.setLiteraryWork(literaryWorkControllerImpl.getLiteraryWork(literaryWorkDTO));
									}
									TranscriptionTable transcriptionTable = new TranscriptionTable(transcription);
									transcriptionTables.add(transcriptionTable);
								}
								trascriptionTable2.setItems(transcriptionTables);
							}
						}
					}
				});
				return row ;
			}
		});
		assignTable.setRowFactory( new Callback<TableView<TranscriptionTable>, TableRow<TranscriptionTable>>() {
			public TableRow<TranscriptionTable> call(TableView<TranscriptionTable> tv) {
				final TableRow<TranscriptionTable> row = new TableRow<TranscriptionTable>();
				row.setOnMouseClicked(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent event) {
						if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
							transcriptorTableA.setItems(null);
							transcriptorTableAA.setItems(null);
							TranscriptionTable rowData = row.getItem();
							TranscriptionDTO transcriptionDTO = readTranscription(rowData);
							if(transcriptionDTO != null) {
								transcription = transcriptionDTO;
								ObservableList<UserDTO> userListDTO = FXCollections.observableArrayList();
								if(transcriptionDTO.getUserList() != null) {
									for (UserDTO userDTO : transcriptionDTO.getUserList()) {
										userListDTO.add(userDTO);
									}
								}
								transcriptorTableAA.setItems(userListDTO);
							} else {
								LiteraryWorkControllerImpl literaryWorkControllerImpl = (LiteraryWorkControllerImpl)context.getBean("literaryWorkControllerImpl");
								LiteraryWorkDTO literaryWorkDTO = new LiteraryWorkDTO();
								literaryWorkDTO.setAuthor(rowData.getAuthor());
								literaryWorkDTO.setTitle(rowData.getTitle());
								literaryWorkDTO.setYear(Integer.valueOf(rowData.getYear()));
								literaryWorkDTO = literaryWorkControllerImpl.getLiteraryWork(literaryWorkDTO);
								if(literaryWorkDTO != null && literaryWorkDTO.getPageList() != null && !literaryWorkDTO.getPageList().isEmpty()) {
									transcription = new TranscriptionDTO();
									transcription.setPage(literaryWorkDTO.getPageList().get(Integer.valueOf(rowData.getPage())));
								}
							}
							UserControllerImpl userControllerImpl = (UserControllerImpl)context.getBean("userControllerImpl");
							UserFilterDTO userFilterDTO = new UserFilterDTO();
							userFilterDTO.setTranscriber(true);
							UserListDTO transcriberList = userControllerImpl.getUserList(userFilterDTO);
							List<UserDTO> transcriberFree = new ArrayList<UserDTO>();
							if(transcriberList != null && transcriberList.getListUserDTO() != null && !transcriberList.getListUserDTO().isEmpty()) {
								transcriberFree.addAll(transcriberList.getListUserDTO());
								for (UserDTO userAllTranscriberDTO : transcriberList.getListUserDTO()) {
									if(transcriptionDTO != null && transcriptionDTO.getUserList() != null) {
										for (UserDTO userTranscriberDTO : transcriptionDTO.getUserList()) {
											if(userAllTranscriberDTO.getId()==userTranscriberDTO.getId()) {
												transcriberFree.remove(userAllTranscriberDTO);
											}
										}
									} 
								}
								ObservableList<UserDTO> userAvailableListDTO = FXCollections.observableArrayList();
								for (UserDTO userDTO : transcriberFree) {
									userAvailableListDTO.add(userDTO);
								}
								transcriptorTableA.setItems(userAvailableListDTO);
							}
						}
					}
				});
				return row ;
			}
		});
		formTable.setRowFactory( new Callback<TableView<UserDTO>, TableRow<UserDTO>>() {
			public TableRow<UserDTO> call(TableView<UserDTO> tv) {
				final TableRow<UserDTO> row = new TableRow<UserDTO>();
				row.setOnMouseClicked(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent event) {
						if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
							UserDTO rowData = row.getItem();
							ProfileControllerImpl profileControllerImpl = (ProfileControllerImpl)context.getBean("profileControllerImpl");
							ProfileDTO profileDTO = new ProfileDTO();
							profileDTO.setUser(rowData);
							profileDTO = profileControllerImpl.getProfile(profileDTO);
							if(profileDTO.getUser() != null) {
								t_name.setText(profileDTO.getUser().getFirstName());
								t_lastName.setText(profileDTO.getUser().getLastName());
							}
							t_degree.setText(profileDTO.getDegreeCourse());
							ModuleControllerImpl moduleControllerImpl = (ModuleControllerImpl)context.getBean("moduleControllerImpl");
							ModuleDTO moduleDTO = new ModuleDTO();
							moduleDTO.setUser(rowData);
							moduleDTO = moduleControllerImpl.getModule(moduleDTO);
							if(moduleDTO != null) {
								t_yearStudy.setText(moduleDTO.getYearOfTheStudy().toString());
								t_comment.setText(moduleDTO.getComment());
							}
							username = rowData.getUsername();
						}
					}
				});
				return row ;
			}
		});
		transcriptorTableA.setRowFactory( new Callback<TableView<UserDTO>, TableRow<UserDTO>>() {
			public TableRow<UserDTO> call(TableView<UserDTO> tv) {
				final TableRow<UserDTO> row = new TableRow<UserDTO>();
				row.setOnMouseClicked(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent event) {
						if (event.getClickCount() == 2 && (!row.isEmpty()) ) {
							UserDTO rowData = row.getItem();
							TranscriptionControllerImpl transcriptionControllerImpl = (TranscriptionControllerImpl)context.getBean("transcriptionControllerImpl");
							AssignTranscriptionRequestDTO assignDTO = new AssignTranscriptionRequestDTO();
							assignDTO.setUsername(rowData.getUsername());
							List<PageDTO> pageAssignList = new ArrayList<PageDTO>();
							pageAssignList.add(transcription.getPage());
							assignDTO.setPageList(pageAssignList);
							AssignTranscriptionResponseDTO response = new AssignTranscriptionResponseDTO();
							try {
								response = transcriptionControllerImpl.assignTrascription(assignDTO);
							} catch (MandatoryFieldException e) {
								Alert alert = new Alert(AlertType.ERROR);
								alert.setHeaderText(e.getMessage());
								alert.showAndWait();
								e.printStackTrace();
							}
							if(response.getAssigned() != null) {
								Alert alert = new Alert(AlertType.CONFIRMATION);
								alert.setHeaderText("Trascrizione assegnata correttamente");
								alert.showAndWait();
								initilizeTranscriptionTable();
								transcriptorTableA.setItems(null);
								transcriptorTableAA.setItems(null);
							} else {
								Alert alert = new Alert(AlertType.ERROR);
								alert.setHeaderText("Assegnamento fallito");
								alert.showAndWait();
							}
						}
					}
				});
				return row ;
			}
		});
		transcriptorTableAA.setRowFactory( new Callback<TableView<UserDTO>, TableRow<UserDTO>>() {
			public TableRow<UserDTO> call(TableView<UserDTO> tv) {
				final TableRow<UserDTO> row = new TableRow<UserDTO>();
				row.setOnMouseClicked(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent event) {
						if (event.getClickCount() == 2 && (!row.isEmpty()) ) {
							UserDTO rowData = row.getItem();
							TranscriptionControllerImpl transcriptionControllerImpl = (TranscriptionControllerImpl)context.getBean("transcriptionControllerImpl");
							UnassignTranscriptionRequestDTO assignDTO = new UnassignTranscriptionRequestDTO();
							assignDTO.setUsername(rowData.getUsername());
							List<PageDTO> pageAssignList = new ArrayList<PageDTO>();
							pageAssignList.add(transcription.getPage());
							assignDTO.setPageList(pageAssignList);
							UnassignTranscriptionResponseDTO response = new UnassignTranscriptionResponseDTO();
							try {
								response = transcriptionControllerImpl.unassignTranscription(assignDTO);
							} catch (MandatoryFieldException e) {
								Alert alert = new Alert(AlertType.ERROR);
								alert.setHeaderText(e.getMessage());
								alert.showAndWait();
								e.printStackTrace();
							}
							if(response.getAssigned() != null) {
								Alert alert = new Alert(AlertType.CONFIRMATION);
								alert.setHeaderText("Trascrizione de-assegnata correttamente");
								alert.showAndWait();
								transcriptorTableA.setItems(null);
								transcriptorTableAA.setItems(null);
							} else {
								Alert alert = new Alert(AlertType.ERROR);
								alert.setHeaderText("De-assegnamento fallito");
								alert.showAndWait();
							}
						}
					}
				});
				return row ;
			}
		});
	}

	private TranscriptionDTO readTranscription(TranscriptionTable rowData) {
		LiteraryWorkControllerImpl literaryWorkControllerImpl = (LiteraryWorkControllerImpl)context.getBean("literaryWorkControllerImpl");
		LiteraryWorkDTO literaryWorkDTO = new LiteraryWorkDTO();
		literaryWorkDTO.setAuthor(rowData.getAuthor());
		literaryWorkDTO.setTitle(rowData.getTitle());
		literaryWorkDTO.setYear(Integer.valueOf(rowData.getYear()));
		literaryWorkDTO = literaryWorkControllerImpl.getLiteraryWork(literaryWorkDTO);
		TranscriptionDTO transcriptionDTORead = null;
		if(literaryWorkDTO != null && literaryWorkDTO.getPageList() != null && !literaryWorkDTO.getPageList().isEmpty() && rowData.getPage() != null) {
			for (PageDTO page : literaryWorkDTO.getPageList()) {
				if(page.getPageNumber() != null && rowData.getPage().equals(page.getPageNumber().toString())) {
					transcriptionDTORead = page.getTranscriptionDTO();
					if(transcriptionDTORead != null) {
						transcriptionDTORead.setPage(page);
					}
				}
			}
			return transcriptionDTORead;
		}
		return null;
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
