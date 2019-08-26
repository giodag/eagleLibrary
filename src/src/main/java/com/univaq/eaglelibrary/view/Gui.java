package com.univaq.eaglelibrary.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class Gui extends UserInterface{
	
	@Autowired
	@Qualifier("loginView")
	private LoginView loginView;
	
	@Override
	public void run() {
		this.open();
	}
	
	/**
	 * In questo metodo dobbiamo sostituire la chiamata al getUser con lo startup della
	 * GUI della nostra applicazione andando a richiamare i file FXML e sviluppando tutta la parte frontend;
	 * Valutare l'utilizzo di altre classoi fatte per pagine dell'applicazione (e.g. UploaderLiteraryWorkView)
	 */
	private void open() {
		loginView.startupLogin();
	}

}
