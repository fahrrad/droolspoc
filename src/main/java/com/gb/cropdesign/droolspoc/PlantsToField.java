package com.gb.cropdesign.droolspoc;

import java.util.ArrayList;
import java.util.List;

public class PlantsToField {
	
	private List<Plant> plantList;
	
	private String fieldName;

	public PlantsToField( String fieldName) {
		super();
		this.plantList = new ArrayList<Plant>();
		this.fieldName = fieldName;
	}

	public List<Plant> getPlantList() {
		return plantList;
	}

	public void setPlantList(List<Plant> plantList) {
		this.plantList = plantList;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	

	
	
}
