package com.univaq.eaglelibrary.view;



import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class HomepageControllerGUI {

    @FXML
    private Label l_profile,l_search,l_transcription,l_module,l_logout,l_upload;

    @FXML
    private AnchorPane top,a_module,a_searchOpera,a_upload,a_trascription,a_profile;
    
    @FXML
    private Button b_chooseFile;

    final FileChooser fileChooser = new FileChooser();
    
    @FXML
    void chooseFile(MouseEvent event) {
    	
    	File file = fileChooser.showOpenDialog(l_logout.getScene().getWindow());
    	
    	//TODO call service to store file 
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

}

