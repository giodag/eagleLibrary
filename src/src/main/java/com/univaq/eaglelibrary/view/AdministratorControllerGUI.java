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
import com.univaq.eaglelibrary.controllerImpl.TranscriptionControllerImpl;
import com.univaq.eaglelibrary.controllerImpl.UserControllerImpl;
import com.univaq.eaglelibrary.dto.LiteraryWorkDTO;
import com.univaq.eaglelibrary.dto.LiteraryWorkListDTO;
import com.univaq.eaglelibrary.dto.LiteraryWorkListFilterDTO;
import com.univaq.eaglelibrary.dto.ModuleDTO;
import com.univaq.eaglelibrary.dto.PageDTO;
import com.univaq.eaglelibrary.dto.TranscriptionDTO;
import com.univaq.eaglelibrary.dto.UserDTO;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
	private TableColumn<UserDTO, Integer> levelColumnAA;
	
	@FXML
	private TableColumn<UserDTO, String> usernameColumnA,usernameColumnAA,usernameColumn,usernameColumnT;

	@FXML
	private TableColumn<TranscriptionTable, String> pageColumnA,yearColumnA,authorColumnA,titleColumnA,
	statusColumnC,pageColumnC,yearColumnC,authorColumnC,titleColumnC,statusColumnT,pageColumnT,yearColumnT,authorColumnT,titleColumnT;

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
		ModuleControllerImpl moduleControllerImpl = (ModuleControllerImpl)context.getBean("moduleControllerImpl");
		ModuleDTO moduleDTO = new ModuleDTO();
		moduleDTO.setStatus("ACCEPTED");
		try {
			moduleControllerImpl.validateModule(moduleDTO);
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

		UserControllerImpl userControllerImpl = (UserControllerImpl)context.getBean("userControllerImpl");
		user.setPermission(Permission.TRANSCRIBER);
		try {
			userControllerImpl.registration(user);
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
	}


	@FXML
	void rejectForm(ActionEvent event) {
		ModuleControllerImpl moduleControllerImpl = (ModuleControllerImpl)context.getBean("moduleControllerImpl");
		ModuleDTO moduleDTO = new ModuleDTO();
		moduleDTO.setStatus("REJECTED");
		try {
			moduleControllerImpl.validateModule(moduleDTO);
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
		TranscriptionControllerImpl transcriptionControllerImpl = (TranscriptionControllerImpl)context.getBean("transcriptionControllerImpl");
		//	TODO VEDI BEENE lo user e quale trascrizione usare
		TranscriptionDTO transcriptionDTO = new TranscriptionDTO();
		//		transcriptionDTO.setUsername();
		try {
			transcriptionControllerImpl.saveTranscription(transcriptionDTO);
		} catch (MandatoryFieldException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText(e.getMessage());
			alert.showAndWait();
			e.printStackTrace();
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
							if(pageDTO.getTranscriptionDTO() != null) {
								pageDTO.getTranscriptionDTO().setLiteraryWork(literaryWorkDTO);
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
	}

	public void init(Stage stage) {
		ownStage = stage;
		user = (UserDTO) stage.getUserData();
		initializeTable();
	}

	private void initializeTable() {
		initilizeTranscriberTable();
		initilizeTranscriptionTable();
		setTableRowDoubleClick();

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
							pageDTO.getTranscriptionDTO().setLiteraryWork(literaryWorkDTO);
							TranscriptionTable transcriptionTable = new TranscriptionTable(pageDTO.getTranscriptionDTO());
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
	}


	private void initilizeTranscriberTable() {
		buildTranscriberColumn();
		ObservableList<UserDTO> transcriber = FXCollections.observableArrayList();
		//chiamata per la lista di trascrittori
//		trascriptorTable1;
		
	}


	private void buildTranscriberColumn() {
		usernameColumnA.setCellValueFactory(new PropertyValueFactory<UserDTO,String>("username"));
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
							//TODO
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
							TranscriptionTable rowData = row.getItem();
							TranscriptionDTO transcriptionDTO = readTranscription(rowData);
							ObservableList<UserDTO> userListDTO = FXCollections.observableArrayList();
							for (UserDTO userDTO : transcriptionDTO.getUserList()) {
								userListDTO.add(userDTO);
							}
							transcriptorTableAA.setItems(userListDTO);
							//TODO leggere la lista di transcriber
							List<UserDTO> list = new ArrayList<UserDTO>();
							list.removeAll(transcriptionDTO.getUserList());
							ObservableList<UserDTO> userAvailableListDTO = FXCollections.observableArrayList();
							for (UserDTO userDTO : transcriptionDTO.getUserList()) {
								userAvailableListDTO.add(userDTO);
							}
							transcriptorTableA.setItems(userAvailableListDTO);
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
