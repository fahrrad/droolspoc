package com.gb.cropdesign.droolspoc.gui;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gb.cropdesign.droolspoc.PlantResult;
import com.gb.cropdesign.droolspoc.service.PlantResultServiceI;

public class App extends Application {

	private TableView<PlantResult> table = new TableView<PlantResult>();


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}


	@Override
	public void start(Stage stage) throws Exception {
		ApplicationContext context =
				new ClassPathXmlApplicationContext("classpath:/spring/rules-application-context.xml");
		PlantResultServiceI plantResultService = context.getBean(PlantResultServiceI.class);

		Scene scene = new Scene(new Group());
		stage.setTitle("Pcr Results viewer");
		stage.setWidth(550);
		stage.setHeight(500);

		final Label label = new Label("Plants");
		label.setFont(new Font("Arial", 20));

		table.setEditable(false);
		table.setMinSize(500, 450);
		
		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));

		TableColumn<PlantResult, String> plantNameColumn = new TableColumn<PlantResult, String>("Plant Name");
		plantNameColumn.setMinWidth(100);
		plantNameColumn.setCellValueFactory(new PropertyValueFactory<PlantResult, String>("plantName"));

		TableColumn<PlantResult, String> pcrTargetColumn = new TableColumn<PlantResult, String>("Pcr Target");
		pcrTargetColumn.setMinWidth(100);
		pcrTargetColumn.setCellValueFactory(new PropertyValueFactory<PlantResult, String>("pcrTarget"));

		TableColumn<PlantResult, Boolean> isTestGenePresentColumn = new TableColumn<PlantResult, Boolean>("Result");
		isTestGenePresentColumn.setMinWidth(40);
		isTestGenePresentColumn.setCellValueFactory(new PropertyValueFactory<PlantResult, Boolean>("result"));

		table.getColumns().addAll(plantNameColumn, pcrTargetColumn, isTestGenePresentColumn);

		ObservableList<PlantResult> items = plantResultService.getPlantResults();

		table.setItems(items);

		vbox.getChildren().addAll(label, table);
		((Group) scene.getRoot()).getChildren().addAll(vbox);

		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
	}

}
