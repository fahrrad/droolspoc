package com.gb.cropdesign.droolspoc.service;

import java.util.List;

import com.gb.cropdesign.droolspoc.PcrResult;

public interface PcrResultServiceI {
	
	public List<PcrResult> getAllPcrResults();
	
	public List<PcrResult> getPcrResultForPlantName( String plantName );

}
