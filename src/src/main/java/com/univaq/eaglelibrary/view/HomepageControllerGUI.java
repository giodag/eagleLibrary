package com.univaq.eaglelibrary.view;



import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class HomepageControllerGUI {

    @FXML
    private Label l_profile,l_search,l_transcription,l_module,l_logout,l_upload;

    @FXML
    private AnchorPane top,module,a_searchOpera,upload,trascription,a_profile;

    @FXML
    private void handleButtonAction(MouseEvent eventMuose) {
    
    	if(eventMuose.getSource() == l_profile) {
    		a_profile.setVisible(true);
    		a_searchOpera.setVisible(false);
    	} else {
    		if(eventMuose.getSource() == l_search) {
    			a_searchOpera.setVisible(true);
    			a_profile.setVisible(false);
    		}
    	}
    }

}

