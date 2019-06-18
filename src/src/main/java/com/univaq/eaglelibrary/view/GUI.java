package com.univaq.eaglelibrary.view;

import com.univaq.eaglelibrary.persistence.PersistenceService;
import com.univaq.eaglelibrary.persistence.exceptions.DatabaseException;


public class GUI extends UserInterface{
	
	private PersistenceService persistenceService;

	public GUI(PersistenceService persistenceService) {
		super(persistenceService);

		//--Vedere se funziona
		this.persistenceService = persistenceService;
		try{
			persistenceService.connect();
		} catch (DatabaseException e) {
			e.getStackTrace();
		}
	}

	public GUI() {}
	
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
		LoginView loginView = new LoginView(persistenceService);
		loginView.startupLogin();
	}

}
