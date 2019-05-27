package com.univaq.eaglelibrary.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.univaq.eaglelibrary.controller.exceptions.DatabaseException;
import com.univaq.eaglelibrary.controller.hanlder.LiteraryWorkHanlder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
/**
 * 
 * infine questa classe dovrà essere buttata perchè ci saranno tutti i controller ad hoc
 *
 */
public class ControllerExample {

	//private Stage stage;

	private LiteraryWorkHanlder literaryWorkHanlder;
	private final Logger logger = LoggerFactory.getLogger(ControllerExample.class);

	public ControllerExample(PersistenceService persistenceService) {
		this.literaryWorkHanlder = new LiteraryWorkHanlder(persistenceService);
	}
	
	public void getUser() {
//		User user = new User();
//		user.setFirstName(name.getText());
		Set<Map<String, String>> result;
		String resultString = null;
		result = literaryWorkHanlder.getLiteraryWork();

		if (result != null && !result.isEmpty()) {
			for (Map<String, String> map : result) {
				if (map != null && !map.isEmpty()) {
					for (Map.Entry<String, String> entry : map.entrySet()) {
						resultString = entry.getValue();
						logger.debug("Result is {}", resultString);
					}
				}

			}
		}
	}
	

	@FXML
	void saveName(ActionEvent event) throws IOException {
		// --Codice che dobbiamo portare nelle classi view
		String fxmlFile = "/fxml/first_GUI.fxml";

		FXMLLoader loader = new FXMLLoader();
		Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));

		Scene scene = new Scene(rootNode);
//		this.stage.setScene(scene);
//		this.stage.show();


		// try {
		// Class.forName("com.mysql.jdbc.Driver");
		// Connection con =
		// DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx?serverTimezone=UTC",
		// "admin", "danimetu");
		//
		// // here sonoo is database name, root is username and password
		// results = this.persistenceService.search("user", "first_name = sab'");
		// System.out.println("Result of query : " + results);
		//
		// con.close();
		// } catch (Exception e) {
		// System.out.println(e);
		// }
	}
	
	public Set<Map<String, String>> getLiteraryWork() throws DatabaseException{
		return this.literaryWorkHanlder.getLiteraryWork();
	}
	
}
