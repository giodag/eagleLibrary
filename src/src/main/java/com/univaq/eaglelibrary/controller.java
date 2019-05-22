package com.univaq.eaglelibrary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.univaq.eaglelibrary.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class controller {

	    @FXML
	    private TextField name;

	    @FXML
	    private Button save;

	    @FXML
	    void saveName(ActionEvent event) {
	    	User user = new User();
	    	user.setFirstName(name.getText());
	    	
	    	try{  
				Class.forName("com.mysql.jdbc.Driver");  
				Connection con=DriverManager.getConnection(  
						"jdbc:mysql://localhost:3306/javafx?serverTimezone=UTC","admin","danimetu");  
				//here sonoo is database name, root is username and password  
				Statement stmt=con.createStatement();  
				stmt.executeUpdate("INSERT INTO user(id,first_name) VALUES ('1','"+user.getFirstName()+"');");    
				con.close();  
			}catch(Exception e){ System.out.println(e);} 
	    }
}
