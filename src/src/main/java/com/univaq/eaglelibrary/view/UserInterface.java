package com.univaq.eaglelibrary.view;

import com.univaq.eaglelibrary.controller.UserController;
import com.univaq.eaglelibrary.controllerImpl.UserControllerImpl;
import com.univaq.eaglelibrary.persistence.PersistenceService;

public abstract class UserInterface {

	protected UserController userController;
	
	public UserInterface(PersistenceService persistenceService) {
		
		//--Come dovranno essere tutti gli startup dei controller
		userController = new UserControllerImpl(persistenceService);
	}
	
	public UserInterface(){}
	public abstract void run();

}
