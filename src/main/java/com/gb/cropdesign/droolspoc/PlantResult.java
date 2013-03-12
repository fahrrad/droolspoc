package com.gb.cropdesign.droolspoc;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class PlantResult {

	private final SimpleStringProperty plantName;
	private final SimpleStringProperty pcrTarget;
	private final SimpleBooleanProperty result;

	public PlantResult(String plantName,
			String pcrTarget, Boolean result) {
		super();
		this.plantName = new SimpleStringProperty(plantName);
		this.pcrTarget = new SimpleStringProperty(pcrTarget);
		this.result = new SimpleBooleanProperty(result);
	}

	public String getPlantName() {
		return plantName.get();
	}

	public String getPcrTarget() {
		return pcrTarget.get();
	}

	public Boolean getResult() {
		return result.get();
	}
	
	public void setPlantName(String plantName) {
		this.plantName.set(plantName);
	}

	public void setPcrTarget(String pcrTarget) {
		this.pcrTarget.set(pcrTarget);
	}

	public void setResult(Boolean result) {
		this.result.set(result);
	}

}
