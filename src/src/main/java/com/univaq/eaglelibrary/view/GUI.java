package com.univaq.eaglelibrary.view;

public class GUI extends UserInterface{
	

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
		LoginView loginView = new LoginView();
		loginView.startupLogin();
	}

}
