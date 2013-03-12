package com.gb.cropdesign.droolspoc.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import org.drools.KnowledgeBase;
import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.gb.cropdesign.droolspoc.PcrResult;
import com.gb.cropdesign.droolspoc.Plant;
import com.gb.cropdesign.droolspoc.PlantResult;
import com.gb.cropdesign.droolspoc.PlantTableModel;
import com.gb.cropdesign.droolspoc.service.PcrResultServiceI;
import com.gb.cropdesign.droolspoc.service.PlantResultServiceI;
import com.gb.cropdesign.droolspoc.service.PlantServiceI;

@Service
public class MainWindowController implements Initializable {

	PlantResultServiceI plantResultService;

	PlantServiceI plantModelService;
	
	PcrResultServiceI pcrResultService;
	
	PlantServiceI plantService;
	
	KnowledgeBase kbase;

	@FXML
	private TableView<PlantTableModel> plants_table;

	@FXML
	private TableView<PlantResult> pcr_results_table;
	
	@FXML
	private Button calculate_button;
	
	private ObservableList<PlantResult> plantResultItems;
	
	private ObservableList<PlantTableModel> plantItems;


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ApplicationContext context =
				new ClassPathXmlApplicationContext("classpath:/spring/rules-application-context.xml");

		plantResultService = context.getBean(PlantResultServiceI.class);
		plantModelService = context.getBean(PlantServiceI.class);
		kbase = context.getBean(KnowledgeBase.class);
		pcrResultService = context.getBean(PcrResultServiceI.class);
		plantService = context.getBean(PlantServiceI.class);

		assert plants_table != null;
		assert pcr_results_table != null;
		
		initialisePcrResultTable();
		initialisePlantTable();
		
		calculate_button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
				
				for(PlantTableModel plant : plantItems){
					ksession.insert(plant);
				}
				
				for(Plant plant : plantModelService.getAllPlants()){
					ksession.insert(plant);
				}
				
				for(PcrResult pcrResult : pcrResultService.getAllPcrResults()){
					ksession.insert(pcrResult);
				}
				
				ksession.fireAllRules();				
			}
		});

	}


	private void initialisePcrResultTable() {
		TableColumn<PlantResult, String> plantNameColumn = new TableColumn<PlantResult, String>("Plant Name");
		plantNameColumn.setMinWidth(100);
		plantNameColumn.setCellValueFactory(new PropertyValueFactory<PlantResult, String>("plantName"));

		TableColumn<PlantResult, String> pcrTargetColumn = new TableColumn<PlantResult, String>("Pcr Target");
		pcrTargetColumn.setMinWidth(100);
		pcrTargetColumn.setCellValueFactory(new PropertyValueFactory<PlantResult, String>("pcrTarget"));

		TableColumn<PlantResult, Boolean> isTestGenePresentColumn = new TableColumn<PlantResult, Boolean>("Result");
		isTestGenePresentColumn.setMinWidth(80);
		isTestGenePresentColumn.setCellValueFactory(new PropertyValueFactory<PlantResult, Boolean>("result"));

		pcr_results_table.getColumns().addAll(plantNameColumn, pcrTargetColumn, isTestGenePresentColumn);
		plantResultItems = plantResultService.getPlantResults();
		pcr_results_table.setItems(plantResultItems);
	}


	private void initialisePlantTable() {
		TableColumn<PlantTableModel, String> plantNameColumn = new TableColumn<PlantTableModel, String>("Plant Name");
		plantNameColumn.setMinWidth(100);
		plantNameColumn.setCellValueFactory(new PropertyValueFactory<PlantTableModel, String>("name"));
		
		TableColumn<PlantTableModel, String> rpdColumn = new TableColumn<PlantTableModel, String>("RPD");
		rpdColumn.setMinWidth(100);
		rpdColumn.setCellValueFactory(new PropertyValueFactory<PlantTableModel, String>("rpd"));

		TableColumn<PlantTableModel, Boolean> transgeneColumn = new TableColumn<PlantTableModel, Boolean>("Transgene");
		transgeneColumn.setMinWidth(100);
		transgeneColumn.setCellValueFactory(new PropertyValueFactory<PlantTableModel, Boolean>("transgene"));

		TableColumn<PlantTableModel, Boolean> conformColumn = new TableColumn<PlantTableModel, Boolean>("Conform");
		conformColumn.setMinWidth(100);
		conformColumn.setCellValueFactory(new PropertyValueFactory<PlantTableModel, Boolean>("conform"));

		plants_table.getColumns().addAll(plantNameColumn, transgeneColumn, conformColumn, rpdColumn);
		plantItems = plantModelService.getAllPlantTableModels();
		plants_table.setItems(plantItems);

	}
}
