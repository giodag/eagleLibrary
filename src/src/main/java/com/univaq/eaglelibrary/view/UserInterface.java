package com.univaq.eaglelibrary.view;

import com.univaq.eaglelibrary.controller.ControllerExample;
import com.univaq.eaglelibrary.controller.PersistenceService;
import com.univaq.eaglelibrary.controller.UserController;
import com.univaq.eaglelibrary.controller.UserControllerImpl;

public abstract class UserInterface {

	protected ControllerExample controller;
	protected UserController userController;
	
	public UserInterface(PersistenceService persistenceService) {
		
		//--TODO be remove
		controller = new ControllerExample(persistenceService); //--TODO to be removed
		
		
		//--Come dovranno essere tutti gli startup dei controller
		userController = new UserControllerImpl(persistenceService);
	}
	
	public UserInterface()
	{}
	public abstract void run();

}
