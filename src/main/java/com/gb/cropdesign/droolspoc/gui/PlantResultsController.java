package com.gb.cropdesign.droolspoc.gui;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import com.gb.cropdesign.droolspoc.PlantResult;
import com.gb.cropdesign.droolspoc.service.PlantResultServiceI;

@Service
public class PlantResultsController implements Initializable {
	
	@Autowired
	PlantResultServiceI plantResultService;
	
	@FXML
	private TableView plants_table;
	
	@FXML 
	private TableView pcr_results_table;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		ApplicationContext context =
				new ClassPathXmlApplicationContext("classpath:/spring/rules-application-context.xml");

		plantResultService = context.getBean(PlantResultServiceI.class);
		
		assert plants_table != null;
		assert pcr_results_table != null;
		initialisePcrResultTable();
		
	}
	
	private void initialisePcrResultTable(){
		TableColumn<PlantResult, String> plantNameColumn = new TableColumn<PlantResult, String>("Plant Name");
		plantNameColumn.setMinWidth(100);
		plantNameColumn.setCellValueFactory(new PropertyValueFactory<PlantResult, String>("plantName"));

		TableColumn<PlantResult, String> pcrTargetColumn = new TableColumn<PlantResult, String>("Pcr Target");
		pcrTargetColumn.setMinWidth(100);
		pcrTargetColumn.setCellValueFactory(new PropertyValueFactory<PlantResult, String>("pcrTarget"));

		TableColumn<PlantResult, Boolean> isTestGenePresentColumn = new TableColumn<PlantResult, Boolean>("Result");
		isTestGenePresentColumn.setMinWidth(40);
		isTestGenePresentColumn.setCellValueFactory(new PropertyValueFactory<PlantResult, Boolean>("result"));

		pcr_results_table.getColumns().addAll(plantNameColumn, pcrTargetColumn, isTestGenePresentColumn);

		ObservableList<PlantResult> items = plantResultService.getPlantResults();
		
		pcr_results_table.setItems(items);
	}

}
