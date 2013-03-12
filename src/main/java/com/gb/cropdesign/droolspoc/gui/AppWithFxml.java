package com.gb.cropdesign.droolspoc.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class AppWithFxml extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("main.fxml"));
			Scene scene = new Scene(page);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Pcr Result Drools POC");
			primaryStage.show();
		} catch (Exception ex) {
			Logger.getLogger(AppWithFxml.class.getName()).log(Level.FATAL, ex);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);

	}

}
