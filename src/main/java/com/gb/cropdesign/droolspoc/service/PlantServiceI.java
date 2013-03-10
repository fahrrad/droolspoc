package com.gb.cropdesign.droolspoc.service;

import java.util.List;

import com.gb.cropdesign.droolspoc.Plant;

public interface PlantServiceI {
	
	public List<Plant> getAllPlants();
	
	public Plant getPlant( String plantName );

}
