package com.gb.cropdesign.droolspoc.gui;

import java.net.URL;
import java.util.*;

import com.gb.cropdesign.droolspoc.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.util.Callback;
import org.drools.KnowledgeBase;
import org.drools.runtime.StatefulKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.gb.cropdesign.droolspoc.service.PcrResultServiceI;
import com.gb.cropdesign.droolspoc.service.PlantResultServiceI;
import com.gb.cropdesign.droolspoc.service.PlantServiceI;

import javax.swing.*;

@Service
public class MainWindowController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainWindowController.class);

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

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab summaryTab;

    @FXML
    private TableView<PlantSummary> plantSummaryTableView;



	private ObservableList<PlantResult> plantResultItems;

	private ObservableList<PlantTableModel> plantItems;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:/spring/application-context.xml");

		plantResultService = context.getBean(PlantResultServiceI.class);
		plantModelService = context.getBean(PlantServiceI.class);
		kbase = context.getBean(KnowledgeBase.class);
		pcrResultService = context.getBean(PcrResultServiceI.class);
		plantService = context.getBean(PlantServiceI.class);

		assert plants_table != null;
		assert pcr_results_table != null;

		initialisePcrResultTable();
		initialisePlantTable();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initialiseSummaryTable(calculateSummary(plantResultService.getPlantResults()));
            }
        });

		calculate_button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        StatefulKnowledgeSession ksession = kbase
                                .newStatefulKnowledgeSession();

                        for (PlantTableModel plant : plantItems) {
                            ksession.insert(plant);
                        }

                        for (Plant plant : plantModelService.getAllPlants()) {
                            ksession.insert(plant);
                        }

                        for (PcrResult pcrResult : pcrResultService.getAllPcrResults()) {
                            ksession.insert(pcrResult);
                        }

                        ksession.fireAllRules();
                    }
                });
			}
		});

        assert tabPane != null;
        assert summaryTab != null;
        //summaryTab.setDisable(true);

	}

	private void initialisePcrResultTable() {
		TableColumn<PlantResult, String> plantNameColumn = new TableColumn<PlantResult, String>(
				"Plant Name");
		plantNameColumn.setMinWidth(100);
		plantNameColumn
				.setCellValueFactory(new PropertyValueFactory<PlantResult, String>(
						"plantName"));

		TableColumn<PlantResult, String> pcrTargetColumn = new TableColumn<PlantResult, String>(
				"Pcr Target");
		pcrTargetColumn.setMinWidth(100);
		pcrTargetColumn
				.setCellValueFactory(new PropertyValueFactory<PlantResult, String>(
						"pcrTarget"));

		TableColumn<PlantResult, Boolean> isTestGenePresentColumn = new TableColumn<PlantResult, Boolean>(
				"Result");
		isTestGenePresentColumn.setMinWidth(80);
		isTestGenePresentColumn
				.setCellValueFactory(new PropertyValueFactory<PlantResult, Boolean>(
						"result"));

		pcr_results_table.getColumns().addAll(plantNameColumn, pcrTargetColumn,
				isTestGenePresentColumn);
		plantResultItems = plantResultService.getPlantResults();
		pcr_results_table.setItems(plantResultItems);

	}

	private void initialisePlantTable() {
		TableColumn<PlantTableModel, String> plantNameColumn = new TableColumn<PlantTableModel, String>(
				"Plant Name");
		plantNameColumn.setMinWidth(100);
		plantNameColumn
				.setCellValueFactory(new PropertyValueFactory<PlantTableModel, String>(
						"name"));

		TableColumn<PlantTableModel, String> rpdColumn = new TableColumn<PlantTableModel, String>(
				"RPD");
		rpdColumn.setMinWidth(100);
		rpdColumn
				.setCellValueFactory(new PropertyValueFactory<PlantTableModel, String>(
						"rpd"));

		TableColumn<PlantTableModel, Boolean> transgeneColumn = new TableColumn<PlantTableModel, Boolean>(
				"Transgene");
		transgeneColumn.setMinWidth(100);
		transgeneColumn
				.setCellValueFactory( new PropertyValueFactory<PlantTableModel, Boolean>(
						"transgene"));

		TableColumn<PlantTableModel, Boolean> conformColumn = new TableColumn<PlantTableModel, Boolean>(
				"Conform");
		conformColumn.setMinWidth(100);
		conformColumn
				.setCellValueFactory( new PropertyValueFactory<PlantTableModel, Boolean>(
						"conform"));

		plants_table.getColumns().addAll(plantNameColumn, transgeneColumn,
				conformColumn, rpdColumn);
		plantItems = plantModelService.getAllPlantTableModels();
		plants_table.setItems(plantItems);

	}

    private void initialiseSummaryTable(Collection<PlantSummary> plantSummaries){
        Set<String> columnNames = new HashSet<String>();

        long s = System.currentTimeMillis();
        for(PlantSummary plantSummary : plantSummaries){

            columnNames.addAll(plantSummary.getTargets());
        }
        LOGGER.trace("looped through targets in {}", (System.currentTimeMillis() - s) );


        TableColumn<PlantSummary, String> nameCol = new TableColumn<PlantSummary, String>("plant name");
        nameCol.setCellValueFactory(new PropertyValueFactory<PlantSummary, String>("name"));
        plantSummaryTableView.getColumns().add(nameCol);



        for(final String columnName : columnNames)     {
            TableColumn<PlantSummary, String> column = new TableColumn<PlantSummary, String>(columnName);
            plantSummaryTableView.getColumns().add(column);

            column.setMinWidth(column.widthProperty().intValue());

            // TODO make this cell look good!
            column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PlantSummary, String>, ObservableValue<String>>() {

                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<PlantSummary, String> plantSummaryStringCellDataFeatures) {
                    Boolean b = plantSummaryStringCellDataFeatures.getValue().getResult(columnName);

                    String v = b == null ? "?" : b ? "+" : "-";
                    return new SimpleStringProperty(v);
                }
            });
        }

        plantSummaryTableView.setItems( FXCollections.observableArrayList(new ArrayList(plantSummaries)));

        //summaryTab.setDisable(false);
    }


    private Collection<PlantSummary> calculateSummary(List<PlantResult> plantResults){
        Map<String, PlantSummary> plantSummaryMap = new HashMap<String, PlantSummary>();

        for(PlantResult plantResuls : plantResults){
            PlantSummary plantSummary = plantSummaryMap.get(plantResuls.getPlantName());
            if(plantSummary == null){
                plantSummary = new PlantSummary(plantResuls.getPlantName());

                plantSummaryMap.put(plantSummary.getName(), plantSummary);
            }

            plantSummary.addTest(plantResuls.getPcrTarget().toString(), plantResuls.getResult());
        }

        return plantSummaryMap.values();
    }
}
