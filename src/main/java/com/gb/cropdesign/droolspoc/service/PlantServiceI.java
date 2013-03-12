package com.gb.cropdesign.droolspoc.service;

import java.util.List;

import javafx.collections.ObservableList;

import com.gb.cropdesign.droolspoc.Plant;
import com.gb.cropdesign.droolspoc.PlantTableModel;

public interface PlantServiceI {
	
	public List<Plant> getAllPlants();
	
	public Plant getPlant( String plantName );
	
	public ObservableList<PlantTableModel> getAllPlantTableModels();

}
