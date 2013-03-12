package com.gb.cropdesign.droolspoc;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;


public class PlantTableModel {
	private final SimpleStringProperty  name;
	
	private final SimpleBooleanProperty conform;
	
	private final SimpleBooleanProperty transgene;
	
	private final SimpleStringProperty rpd;
	
	public PlantTableModel(String plantName, String rpd, boolean conform, boolean transgene){
		this.name = new SimpleStringProperty(plantName);
		this.conform = new SimpleBooleanProperty(conform);
		this.transgene = new SimpleBooleanProperty(transgene);
		this.rpd = new SimpleStringProperty(rpd);
	}
	
	public String getName(){
		return name.get();
	}
	
	public boolean isConform(){
		return conform.get();
	}
	
	public boolean isTransgene(){
		return transgene.get();
	}
	
	
	public void setName(String name){
		this.name.set(name);
	}
	
	public void setConform(boolean conform){
		this.conform.set(conform);
	}
	
	public void setTransgene(boolean transgene){
		this.transgene.set(transgene);
	}

	
	public String getRpd() {
		return rpd.get();
	}
	
	public void setRpd(String rpd){
		this.rpd.set(rpd);
	}
}
