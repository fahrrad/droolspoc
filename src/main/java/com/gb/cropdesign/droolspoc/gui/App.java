package com.gb.cropdesign.droolspoc.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App extends Application{
	
	private TableView table = new TableView();

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/rules-application-context.xml");
		// PlantResultServiceI plantResultService = context.getBean(PlantResultServiceI.class);
		
		Scene scene = new Scene(new Group());
		stage.setTitle("Pcr Results viewer");
		stage.setWidth(300);
		stage.setHeight(500);
		
		final Label label = new Label("Plants");
		label.setFont(new Font("Arial", 20));
		
		table.setEditable(false);
		
		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		
		TableColumn plantName = new TableColumn("Plant Name");
		TableColumn pcrTarger = new TableColumn("Pcr Target");
		TableColumn isTestGenePresent = new TableColumn("Result");
		
		table.getColumns().addAll(plantName, pcrTarger, isTestGenePresent);
		
		//table.setItems(plantResultService.getPlantResults());
		
		vbox.getChildren().addAll(label, table);
		((Group) scene.getRoot()).getChildren().addAll(vbox);
		
		stage.setScene(scene);
		stage.show();
	}

}
