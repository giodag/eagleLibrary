package com.univaq.eaglelibrary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.univaq.eaglelibrary.view.Gui;
import com.univaq.eaglelibrary.view.UserInterface;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

	private static final Logger log = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {

		try {
			launch(args);
		} catch (Exception e) {
			log.error("The error occurred is  :", e);
		}
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
			UserInterface ui = (Gui) context.getBean("gui");
			ui.run();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
